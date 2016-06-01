package Cannon;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.NumberFormat;
import java.util.Random;
import mpi.*;

public class Lab7Cannon {

    final static private int N = 500;
    final static private int amountOfBlocks = 1;

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

    static double[][] getblock(double[][] matrix, int n, int m, int size) {
        double[][] block = new double[size][size];
        for (int i = n * size; i < n * size + size; i++) {
            for (int j = m * size; j < m * size + size; j++) {
                block[i - n * size][j - m * size] = matrix[i][j];
            }
        }
        return block;
    }

    static void setblock(double[][] matrix, double[][] block, int n, int m) {
        int size = block.length;
        for (int i = n * size; i < n * size + size; i++) {
            for (int j = m * size; j < m * size + size; j++) {
                matrix[i][j] = block[i - n * size][j - m * size];
            }
        }
    }

    static int[][] markProcesses(int amOfBl) {
        int[][] procMap = new int[amOfBl][amOfBl];
        int counter = 1;
        for (int i = 0; i < amOfBl; i++) {
            for (int j = 0; j < amOfBl; j++) {
                procMap[i][j] = counter;
                counter++;
            }
        }
        return procMap;
    }

    static int[] getProcCoord(int[][] procMap, int id) {
        int[] res = new int[2];
        for (int i = 0; i < procMap.length; i++) {
            for (int j = 0; j < procMap[0].length; j++) {
                if (procMap[i][j] == id) {
                    res[0] = i;
                    res[1] = j;
                }
            }
        }
        return res;
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

    static void shiftRow(double[][] a, int row, int num, int size) {
        double[] newrow = new double[a[0].length];
        for (int i = 0; i < newrow.length; i++) {
            if (i + num < a[0].length) {
                newrow[i] = a[row][i + num];
            } else {
                newrow[i] = a[row][num - (a[0].length - i)];
            }
        }
        a[row] = newrow;
    }

    static void shiftColumn(double[][] a, int column, int num, int size) {
        double[] newcolumn = new double[a.length];
        for (int i = 0; i < newcolumn.length; i++) {
            if (i + num < a[0].length) {
                newcolumn[i] = a[i + num][column];
            } else {
                newcolumn[i] = a[num - (a[0].length - i)][column];
            }
        }
        for (int i = 0; i < a.length; i++) {
            a[i][column] = newcolumn[i];
        }
    }

    static double[][] plus(double[][] a, double[][] b) {
        double[][] res = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        MPI.Init(args);
        int master = 0;

        int sizeOfBlock = N / amountOfBlocks;
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int tagA = 10;
        int tagB = 20;
        int tagres = 30;
        int peer;
        int[][] procMap = markProcesses(amountOfBlocks);
        double[][] aBlock = null;
        double[][] bBlock = null;
        if (rank == master) {
            double[][] a = new double[N][N];
            double[][] b = new double[N][N];
            Random rand = new Random();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
//                    a[i][j] = rand.nextDouble();
//                    b[i][j] = rand.nextDouble();
                    a[i][j] = (i + j) % 10;
                    b[i][j] = (i + j) % 10;
                }
            }
            //printMatrix(a);
            //printMatrix(b);
            Object[] sendobject = new Object[1];
            double[][] result = new double[N][N];
            double[][] newA = new double[N][N];
            for (int m = 0; m < amountOfBlocks; m++) {
                for (int j = 0; j < amountOfBlocks; j++) {
                    if (j + m >= amountOfBlocks) {
                        setblock(newA, getblock(a, m, m - (amountOfBlocks - j), sizeOfBlock), m, j);
                    } else {
                        setblock(newA, getblock(a, m, j + m, sizeOfBlock), m, j);
                    }
                }
            }

            a = newA;
            double[][] newB = new double[N][N];
            for (int m = 0; m < amountOfBlocks; m++) {
                for (int j = 0; j < amountOfBlocks; j++) {
                    if (j + m >= amountOfBlocks) {
                        setblock(newB, getblock(b, m - (amountOfBlocks - j), m, sizeOfBlock), j, m);
                    } else {
                        setblock(newB, getblock(b, j + m, m, sizeOfBlock), j, m);
                    }
                }
            }
            b = newB;

            for (int i = 0; i < amountOfBlocks; i++) {
                for (int j = 0; j < amountOfBlocks; j++) {
                    peer = i;
                    sendobject[0] = (Object) getblock(a, i, j, sizeOfBlock);
                    MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, procMap[i][j], tagA);
                    sendobject[0] = (Object) getblock(b, i, j, sizeOfBlock);
                    MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, procMap[i][j], tagB);
                }
            }
            Object[] recvobject = new Object[1];
            for (int i = 0; i < amountOfBlocks; i++) {
                for (int j = 0; j < amountOfBlocks; j++) {
                    MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, procMap[i][j], tagres);
                    setblock(result, (double[][]) recvobject[0], i, j);
                }
            }
            //printMatrix(result);
        } else {
            peer = 0;
            double[][] c = new double[sizeOfBlock][sizeOfBlock];
            Object[] recvobject = new Object[1];
            Object[] sendobject = new Object[1];
            MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, peer, tagA);
            aBlock = (double[][]) recvobject[0];
            MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, peer, tagB);           
            bBlock = (double[][]) recvobject[0];
            for (int i = 0; i < amountOfBlocks; i++) {
                c = plus(c, multiply(aBlock, bBlock));
                
                if (i != amountOfBlocks - 1) {
                    int[] rowCol = getProcCoord(procMap, rank);
                    sendobject[0] = (Object) bBlock;
                    if (rowCol[0] > 0) {
                        MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, procMap[rowCol[0] - 1][rowCol[1]], tagB);
                    } else {
                        MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, procMap[amountOfBlocks - 1][rowCol[1]], tagB);
                    }
                    if (rowCol[0] < amountOfBlocks - 1) {
                        MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, procMap[rowCol[0] + 1][rowCol[1]], tagB);
                    } else {
                        MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, procMap[0][rowCol[1]], tagB);
                    }
                    bBlock = (double[][]) recvobject[0];
                    sendobject[0] = (Object) aBlock;
                    if (rowCol[1] > 0) {
                        MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, procMap[rowCol[0]][rowCol[1] - 1], tagA);
                    } else {
                        MPI.COMM_WORLD.Isend(sendobject, 0, 1, MPI.OBJECT, procMap[rowCol[0]][amountOfBlocks - 1], tagA);
                    }
                    if (rowCol[1] < amountOfBlocks - 1) {
                        MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, procMap[rowCol[0]][rowCol[1] + 1], tagA);
                    } else {
                        MPI.COMM_WORLD.Recv(recvobject, 0, 1, MPI.OBJECT, procMap[rowCol[0]][0], tagA);
                    }
                    aBlock = (double[][]) recvobject[0];
                }
            }

            sendobject[0] = (Object) c;
            MPI.COMM_WORLD.Send(sendobject, 0, 1, MPI.OBJECT, master, tagres);
        }
        MPI.Finalize();
        if (rank == master) {
            long endTime = System.nanoTime();
            System.out.println("Took " + (endTime - startTime) / 1000000000.0 + " s");
        }
    }

}
