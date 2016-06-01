package Lines;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import mpi.*;

public class Lab7 {

    final static private int N = 1000;

    static double[] getcolumn(double[][] matrix, int k) {
        double[] column = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][k];
        }
        return column;
    }

    static void printMatrix(double[][] matr) {
        NumberFormat dec = NumberFormat.getInstance();
        dec.setMaximumFractionDigits(2);
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr[0].length; j++) {
                System.out.print(matr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------");
    }

    static double[][] getrowstrip(double[][] matrix, int number, int size) {
        double[][] strip = new double[size][matrix[0].length];
        for (int i = number * size; i < (number + 1) * size; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                strip[i - number * size][j] = matrix[i][j];
            }
        }
        return strip;
    }

    static double[][] getcolumnstrip(double[][] matrix, int number, int size) {
        double[][] strip = new double[matrix.length][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = number * size; j < (number + 1) * size; j++) {
                strip[i][j - number * size] = matrix[i][j];
            }
        }
        return strip;
    }

    static double columnMultiply(double[][] a, int n, double[][] b, int m) {
        double result = 0;
        for (int i = 0; i < a[0].length; i++) {
            result += a[n][i] * b[i][m];
        }
        return result;
    }

    static double[][] multiply(double[][] a, double[][] b) {

        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                result[i][j] = columnMultiply(a, i, b, j);
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        long start = System.nanoTime();

        MPI.Init(args);
        int mainThread = 0;
        int rank = MPI.COMM_WORLD.Rank();

        int size = MPI.COMM_WORLD.Size();
        int st_size = N / (size - 1);
        int rowTag = 1;
        int colTag = 2;
        int resTag = 3;
        int peer;
        double[][] row = null;
        double[][] column = null;
        if (rank == mainThread) {
            double[][] a = new double[N][N];
            double[][] b = new double[N][N];
            Random rand = new Random();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
//                    a[i][j] = rand.nextDouble();
//                    b[i][j] = rand.nextDouble();
                    a[i][j] = (i+j)%10;
                    b[i][j] = (i+j)%10;
                }
            }
            //printMatrix(a);
            //printMatrix(b);
            for (int i = 1; i < size; i++) {
                peer = i;
                Object[] sendobject = new Object[1];
                sendobject[0] = (Object) getrowstrip(a, peer - 1, st_size);
                MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, peer, rowTag);
                sendobject[0] = (Object) getcolumnstrip(b, peer - 1, st_size);
                MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, peer, colTag);
            }
            double[][] result = new double[N][N];
            for (int i = 1; i < size; i++) {
                Object[] recvobject = new Object[1];
                MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, i, resTag);
                double[][] c = (double[][]) recvobject[0];
                for (int n = 0; n < c.length; n++) {
                    for (int m = 0; m < c[0].length; m++) {
                        result[(i - 1) * st_size + n][m] = c[n][m];
                    }
                }
            }
            //printMatrix(result);
        } else {
            peer = 0;
            double[][] c = new double[st_size][N];
            Object[] recvobject = new Object[1];
            MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, peer, rowTag);

            int gotrank = rank;
            row = (double[][]) recvobject[0];
            MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, peer, colTag);
            column = (double[][]) recvobject[0];
            double[][] res = multiply(row, column);
            for (int s = 0; s < N / st_size; s++) {
                res = multiply(row, column);
                for (int i = 0; i < st_size; i++) {
                    for (int j = 0; j < st_size; j++) {
                        c[i][j + (gotrank - 1) * st_size] = res[i][j];
                    }
                }
                Object[] sendobject = new Object[1];
                sendobject[0] = (Object) column;
                if (rank != size - 1) {
                    MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, rank + 1, colTag);
                } else {
                    MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, 1, colTag);
                }
                if (rank != 1) {
                    MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, rank - 1, colTag);
                    column = (double[][]) recvobject[0];
                } else {
                    MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, size - 1, colTag);
                    column = (double[][]) recvobject[0];
                }
                if (gotrank > 1) {
                    gotrank--;
                } else {
                    gotrank = size - 1;
                }
            }
            Object[] sendobject = new Object[1];
            sendobject[0] = (Object) c;
            MPI.COMM_WORLD.Send(sendobject, 0, 1, MPI.OBJECT, mainThread, resTag);
        }
        MPI.Finalize();
        if (rank == mainThread) {
            long end = System.nanoTime();
            System.out.println("Took " + (end - start)/1000000000.0 + " s");
        }
    }

}
