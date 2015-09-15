#include <iostream>
#include <math.h>
#include <fstream>


using namespace std;

double **matrix;
static int counter = 0;

double Norm(double *val, int n)
{
    double norm = 0;
    for (auto i = 0; i < n; i++)
        norm += pow(val[i], 2);
    norm = sqrt(norm);

    return norm;
}

void EigenValues (double **val, float eps, int n)
{
    double *y0, *y;
    y0 = new double[n];
    y = new double [n];
    int p, k = 0;
    double l = 0, L0 = 0, e = 1.f;

    cin >> p;

    for (size_t i = 0; i < n; i++)
        y0[i] = 1;
    do
    {
        double norm = Norm(y0, n);
        for (size_t  i = 0; i < n; i++)
        {
            y[i] = 0;
            for (size_t j = 0; j < n; j++)
                y[i] = y[i] + val[i][j] * y0[j];
            
            cout << y[i] << " ";
        }
        if (k == 0)
        {
            L0 = y[p] / y0[p];
            k++;
            cout << endl;
        }
        else
        {
            l = y[p] / y0[p];
            cout << endl;
            cout << "L: " << l << " ";
            cout << "L0: " <<  L0 << " ";
            e = fabs(l - L0);
            cout  << "E: " << e << endl;
            L0 = l;
        }
        for (size_t i = 0; i < n; i++)
        {
            y0[i] = y[i];
            if (counter > 0)
                    y0[i] /= norm;
        }

        counter++;
    }
    while (e > eps);
}



int main()
{

    float eps;
    int n;
    fstream fin;
    fin.open("C:\\Users\\MakeYouHappy\\ClionProjects\\DM.L3. Eigen Values\\matrix.txt");

    fin >> n;
    fin >> eps;

    matrix = new double *[n];
    for (auto i = 0; i < n; i++)
        matrix[i] = new double [n];

    for (size_t i = 0; i < n; i++)
        for (size_t j = 0; j < n; j++)
            fin >> matrix[i][j];

    EigenValues(matrix, eps, n);
    fin.close();
    system("PAUSE");
    return 0;
}
