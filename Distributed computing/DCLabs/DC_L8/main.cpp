#include <mpi.h>
#include <iostream>
#include <stdlib.h>
#include <iomanip>
#include <memory>
#include <math.h>

using namespace std;

class Matrix
{
        int* d;
        int rs;
        int cs;
    public:
        Matrix()
        {
            rs = cs = 0;
            d = 0;
        }

        Matrix(int rows, int cols = 0)
        {
            rs = rows;
            cs = cols > 0 ? cols : rs;

            d = new int[rs*cs];
        }

        int get(int i, int j) const
        {
            return d[i*cs + j];
        }

        void set(int i, int j, int val)
        {
            d[i*cs + j] = val;
        }

        int rows() const
        {
            return rs;
        }

        int cols() const
        {
            return cs;
        }

        void print()
        {
            for (int i = 0; i < rs; i++)
            {
                for (int j = 0; j < cs; j++)
                    cout << d[i*cs + j] << " ";

                cout << endl;
            }
        }

        void resize(int rows, int cols)
        {
            if (d)
                delete[] d;

            rs = rows;
            cs = cols > 0? cols: rs;
            d = new int[rs*cs];
        }

        void transpose()
        {
            int tmp;

            for (int i=0; i < rs; i++)
            {
                for (int j = i+1; j < cs; j++)
                {
                    tmp = d[i*cs+j];
                    d[i*cs+j] = d[j*cs+i];
                    d[j*cs+i] = tmp;
                }
            }
        }

        int* &data()
        {
            return d;
        }

        void setSize(int rows, int cols)
        {
            rs = rows;
            cs = cols > 0?cols:rs;
        }
};

class MatrixAlgo
{
    protected:
        Matrix& m1;
        Matrix& m2;
        double time;
        virtual void calc(Matrix& r) = 0;

    public:
        MatrixAlgo(Matrix& m1, Matrix& m2):
            m1(m1), m2(m2)
        {
        }

        Matrix calculate()
        {
            Matrix r(m2.rows(), m1.cols());
            time = MPI_Wtime();
            calc(r);
            time = MPI_Wtime() - time;
            return r;
        }

        double getTime()
        {
            return time;
        }

        Matrix& getM2() const
        {
            return m2;
        }

        void setM2(Matrix &val)
        {
            m2 = val;
        }

        Matrix& getM1() const
        {
            return m1;
        }

        void setM1(Matrix &val)
        {
            m1 = val;
        }
};

class Fox: public MatrixAlgo
{
    protected:
        int procs;
        int rank;

        int row;
        int col;
        int gridSize;

        MPI_Comm gridComm;
        MPI_Comm rowComm;
        MPI_Comm colComm;

        virtual void splitMatrix(Matrix& mat, Matrix& block);
        virtual void mergeMatrix(Matrix& mat, Matrix& block);
        void mult(Matrix& m1, Matrix& m2, Matrix& r);
    public:
        Fox(Matrix& m1, Matrix& m2);

        virtual void calc(Matrix& r);
};


Fox::Fox(Matrix& m1, Matrix& m2):MatrixAlgo(m1, m2)
{
    MPI_Comm_size(MPI_COMM_WORLD, &procs);

    gridSize = (int)sqrt(procs);

    int dims[2] = {gridSize, gridSize};
    int periods[2] = {1, 1};
    int coords[2];

    MPI_Cart_create(MPI_COMM_WORLD, 2, dims, periods, true, &gridComm);

    if (gridComm != MPI_COMM_NULL)
    {

        MPI_Comm_rank(gridComm, &rank);
        MPI_Cart_coords(gridComm, rank, 2, coords);

        row = coords[0];
        col = coords[1];

        int remain_dims[2] = {0, 1};
        MPI_Cart_sub(gridComm, remain_dims, &rowComm);

        remain_dims[0] = 1;
        remain_dims[1] = 0;
        MPI_Cart_sub(gridComm, remain_dims, &colComm);
    }
}


void Fox::splitMatrix(Matrix &mat, Matrix &block)
{
    int size = mat.rows();
    int blockSize = block.rows();
    int* rows = new int [blockSize*size];

    if (col == 0)
        MPI_Scatter(mat.data(), blockSize*size, MPI_INT, rows, blockSize*size, MPI_INT, 0, colComm);

    for (int i = 0; i < blockSize; i++)
    {
        MPI_Scatter(&rows[i*size], blockSize, MPI_INT, &block.data()[i*blockSize], blockSize, MPI_INT, 0, rowComm);
    }

    delete [] rows;
}

void Fox::mult(Matrix &m1, Matrix &m2, Matrix &r)
{
    for (int i = 0; i < m1.rows(); i++)
        for (int j = 0; j < m2.cols(); j++)
        {
            int sum = 0;

            for (int k = 0; k < m1.cols(); k++)
                sum += m1.get(i, k)*m2.get(k, j);

            r.set(i, j , r.get(i,j) + sum);
        }
}

void Fox::mergeMatrix(Matrix &mat, Matrix &block)
{
    int size = mat.rows();
    int blockSize = block.rows();

    int* rows = new int [blockSize*size];

    for (int i = 0; i < blockSize; i++)
    {
        MPI_Gather(&block.data()[i*blockSize], blockSize, MPI_INT, &rows[i*size], blockSize, MPI_INT, 0, rowComm);
    }

    if (col == 0)
    {
        MPI_Gather(rows, blockSize*size, MPI_INT, mat.data(), blockSize*size, MPI_INT, 0, colComm);
    }

    delete [] rows;
}

void Fox::calc(Matrix& r)
{
    if (gridComm == MPI_COMM_NULL)
        return;

    int size = m1.rows();

    int blockSize = size/gridSize;

    MPI_Status status;

    Matrix blockM1(blockSize);
    Matrix blockM2(blockSize);
    Matrix blockRes(blockSize);
    Matrix tmpM1(blockSize);

    memset(blockRes.data(), 0, blockSize*blockSize);
    splitMatrix(m1, blockM1);
    splitMatrix(m2, blockM2);

    int nextProc = (row + 1) % gridSize;
    int prevProc = (row + gridSize - 1) % gridSize;

    for (int i = 0; i < gridSize; i++)
    {
        int sender = (row + i) % gridSize;

        if (sender == col)
        {
            MPI_Bcast(blockM1.data(), blockSize*blockSize, MPI_INT, sender, rowComm);
            mult(blockM1, blockM2, blockRes);
        }
        else
        {
            MPI_Bcast(tmpM1.data(), blockSize*blockSize, MPI_INT, sender, rowComm);
            mult(tmpM1, blockM2, blockRes);
        }


        MPI_Sendrecv_replace(blockM2.data(), blockSize*blockSize, MPI_INT, prevProc, 0, nextProc, 0, colComm, &status);
    }

    mergeMatrix(r, blockRes);

}

class Cannon: public Fox
{
public:
    Cannon(Matrix& m1, Matrix& m2);
    ~Cannon();

    void calc (Matrix& r) override;
};

Cannon::Cannon(Matrix &m1, Matrix &m2) : Fox(m1, m2)
{
}

void Cannon::calc(Matrix &r)
{
    if (gridComm == MPI_COMM_NULL)
        return;

    int size = m1.rows();
    int blockSize = size/gridSize;
    MPI_Status status;

    Matrix blockM1(blockSize);
    Matrix blockM2(blockSize);
    Matrix blockRes(blockSize);

    memset(blockRes.data(), 0, blockSize*blockSize);
    splitMatrix(m1, blockM1);
    splitMatrix(m2, blockM2);

    int nextCol = (col + 1)%gridSize;
    int prevCol = (col + gridSize - 1)%gridSize;
    int nextRow = (row + 1)%gridSize;
    int prevRow = (row + gridSize - 1)%gridSize;

    if (row > 0)
        MPI_Sendrecv_replace(blockM1.data(), blockSize*blockSize, MPI_INT, prevCol, 0, nextCol, 0, rowComm, &status);

    if (col > 0)
        MPI_Sendrecv_replace(blockM2.data(), blockSize*blockSize, MPI_INT, prevRow, 1, nextRow, 1, colComm, &status);

    mult(blockM1, blockM2, blockRes);

    for (int i = 1; i < gridSize; i++)
    {

        MPI_Sendrecv_replace(blockM1.data(), blockSize*blockSize, MPI_INT, prevCol, 0, nextCol, 0, rowComm, &status);
        MPI_Sendrecv_replace(blockM2.data(), blockSize*blockSize, MPI_INT, prevRow, 1, nextRow, 1, colComm, &status);

        mult(blockM1, blockM2, blockRes);
    }

    mergeMatrix(r, blockRes);
}

class Tape: public MatrixAlgo
{
    protected:
        int procs;
        int procRank;

        void mult(Matrix& m1, Matrix& m2, Matrix& res);
    public:
        Tape(Matrix& m1, Matrix& m2);

        void calc(Matrix& r) override;
};



Tape::Tape(Matrix& m1, Matrix& m2) : MatrixAlgo(m1, m2)
{
    MPI_Comm_size(MPI_COMM_WORLD, &procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &procRank);
}

void Tape::mult(Matrix &m1, Matrix &m2, Matrix &r)
{
    for (int i = 0; i < m1.rows(); i++)
        for (int j = 0; j < m2.rows(); j++)
        {
            int sum = 0;
            for (int k = 0; k < m1.cols(); k++)
                sum += m1.get(i, k)*m2.get(j, k);
            r.set(i, j, sum);
        }
}

void Tape::calc(Matrix& r)
{
    int size = m1.rows();

    MPI_Status status;
    int partSize = size/procs;
    int partElemCount = partSize*size;

    Matrix t1(partSize, size);
    Matrix t2(partSize, size);
    Matrix t3(partSize, size);

    int *resData = t3.data();

    if (procRank == 0)
        m2.transpose();

    MPI_Scatter(m1.data(), partElemCount, MPI_INT, t1.data(), partElemCount, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(m2.data(), partElemCount, MPI_INT, t2.data(), partElemCount, MPI_INT, 0, MPI_COMM_WORLD);

    t3.data() = resData + partSize*procRank;
    mult(t1, t2, t3);

    int nextProc = (procRank + 1)%procs;;
    int prevProc = (procRank + procs - 1)%procs;

    for(int p = 1; p < procs; p++)
    {
        MPI_Sendrecv_replace(t2.data(), partElemCount, MPI_INT, nextProc, 0, prevProc, 0, MPI_COMM_WORLD, &status);

        if (procRank - p >= 0 )
            t3.data() = resData + (procRank - p)*partSize;
        else
            t3.data() = resData + (procs - p + procRank)*partSize;

        mult(t1, t2, t3);
    }

    MPI_Gather(resData, partElemCount, MPI_INT, r.data(), partElemCount, MPI_INT, 0, MPI_COMM_WORLD);
}

class Sequential: public MatrixAlgo
{
    public:
        Sequential(Matrix& m1, Matrix& m2):MatrixAlgo(m1, m2)
        {}

        virtual void calc(Matrix& r);
};

void Sequential::calc(Matrix& r)
{

    for (int i = 0; i < m1.rows(); i++)
        for (int j = 0; j < m2.cols(); j++)
        {
            int sum = 0;

            for (int k = 0; k < m1.cols(); k++)
                sum += m1.get(i, k)*m2.get(k, j);

            r.set(i, j , sum);
        }
}

void fill(Matrix& m)
{
    for (int i = 0; i < m.rows(); i++)
        for (int j = 0; j < m.cols(); j++)
            m.set(i, j, rand() % 100);
}

int main(int argc, char* argv[])
{
    MPI_Init(&argc, &argv);

    int algoNum = atoi(argv[1]);
    int size = atoi(argv[2]);
    //int procs;
    int procRank;
    //MPI_Comm_size(MPI_COMM_WORLD, &procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &procRank);

    srand(100);

    Matrix m1(size), m2(size), r;
    fill(m1);
    fill(m2);

    MatrixAlgo *algo = 0;

    switch (algoNum)
    {
        case 0:
            algo = new Sequential(m1, m2);
            break;

        case 1:
            algo = new Tape(m1, m2);
            break;

        case 2:
            algo = new Fox(m1, m2);
            break;

        case 3:
            algo = new Cannon(m1, m2);
            break;

        default:
            break;
    }

    r = algo->calculate();


    if (procRank == 0)
    {
//        m1.print();
//        cout << "-------" << endl;
//        m2.print();
//        cout << "-------" << endl;
//        r.print();

        cout << resetiosflags(ios::left) << setiosflags(ios::fixed);
        cout << setw(10) << setprecision(6) << algo->getTime() << endl;
    }

    MPI_Finalize();

    return 0;
}
