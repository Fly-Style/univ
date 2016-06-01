import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author is flystyle
 * Created on 22.04.16.
 */
public class HelpToSlava {

    private static long[] prime(int n) {
        long[] array = new long[n - 1];

        long p = array[0] = 2;
        for (int i = 1; i < array.length; i++)
            array[i] = array[i - 1] + 1;

        while (p*p <= n) {
            for (int i = 0; i < array.length; i++) {
                if(array[i] >= 2*p && array[i] % p == 0)
                    array[i] = -1;
            }

            for (long a : array) {
                if(a != -1 && a > p) {
                    p = a;
                    break;
                }
            }
        }

        int primeCount = 0;
        for (long a : array) {
            if(a != -1)
                primeCount++;
        }

        long[] result = new long[primeCount];
        int j = 0;
        for (long a : array)
            if (a != -1)
                result[j++] = a;

        return result;
    }

    public static void outputDigits (final int n) {
        int digit = Math.abs(n);
        Stack<Integer> digitsStack = new Stack<>();
        while (digit / 10 > 0) {
            digitsStack.push(digit % 10);
            digit /= 10;
        }
        digitsStack.push(digit % 10);

        if (n < 0)
            System.out.print("- ");


       while (!digitsStack.empty())
            System.out.print(digitsStack.pop() + " ");
    }

    public static void printInRadix (final int n, final int radix) {
        if (n < 0)
            throw new IllegalArgumentException();

        int value = n;
        List<Integer> digList = new ArrayList<>();

        while (value > 0) {
            digList.add(value % radix);
            value /= radix;
        }
        System.out.println("");
        for (int j = digList.size() - 1; j >= 0; --j) {
            System.out.print(digList.get(j));
        }
    }

    public static void printPrimes(final int from, final int to) {
        if (from > to)
            throw new IllegalArgumentException();

        long [] digs = prime(to);
        int ind = 0;
        System.out.println("");

        for (int i = 0; i < digs.length; i++) {
            if (digs[i] > from) {
                ind = i;
                break;
            }
        }

        for (int i = ind; i < digs.length; i++) {
            System.out.print(digs[i] + ",");
        }
    }

    public static void digitsSum(int from, int to) {
        if (from > to)
            throw new IllegalArgumentException();

        else if (from == to) {
            outputDigits(from);
            return;
        }
        int res = 0;
        for (int i  = from; i < to; ++i)
            res += i;

        System.out.println("");
        System.out.println(res);
    }

    public static void main(String[] args) {
        outputDigits(-147292);
        printInRadix(16, 2);
        printPrimes(1, 11);
        digitsSum(4, 6);
    }

}
