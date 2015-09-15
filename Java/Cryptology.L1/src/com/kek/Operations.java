package com.kek;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by MakeYouHappy on 04.09.2015.
 */

public class Operations {
    private static int fermaTestsQ = 100;
    private static int millersRounds = 500;
    private static final double LOG2 = Math.log(2.0);

    private Integer zero = new Integer(0);
    private Integer one = new Integer(1);
    private BigInteger two = BigInteger.ONE.add(BigInteger.ONE);

    private BigInteger GCD(BigInteger divided, BigInteger divider)       //greatest common divisor
    {
        if (divided.equals(BigInteger.ZERO))
            return divided;
        return GCD(divider, divided.mod(divider));
    }

    private static double LogBigInteger(BigInteger val)
    {
        int blex = val.bitLength() - 256;// - 1022; // any value in 60..1023 is ok
        if (blex > 0)
            val = val.shiftRight(blex);
        double res = Math.log(val.doubleValue());
        return blex > 0 ? res + blex * LOG2 : res;
    }

    private BigInteger FastMultiply(BigInteger A, BigInteger B, BigInteger module)
    {
        if (B.equals(one))
            return A;

        if (B.mod(two).equals(zero)) {
            BigInteger temp = this.FastMultiply(A, B.divide(two), module);
            return (temp.multiply(two).mod(module));
        }
        return (FastMultiply(A, B.subtract(BigInteger.ONE), module).add(A)).mod(module);
    }

    private BigInteger FastBinaryPow(BigInteger A, BigInteger B, BigInteger module)
    {
        if (B.equals(BigInteger.ZERO))
            return BigInteger.ONE;

        if (B.mod(two).equals(BigInteger.ZERO)) {
            BigInteger t = FastBinaryPow(A, B.divide(two), module);
            return FastMultiply(t, t, module).mod(module);
        }
        return (FastMultiply(FastBinaryPow(A, B.subtract(BigInteger.ONE), module), A, module)).mod(module);
    }

    public boolean FermasTest(BigInteger suspectValue) {
        int bitsNumber = 512;
        BigInteger Number = new BigInteger("2");
        Number.pow(bitsNumber);

        if (suspectValue.mod(BigInteger.ONE.add(BigInteger.ONE)).equals(BigInteger.ZERO)) {
            suspectValue.add(BigInteger.ONE);
            System.out.println("Парное, добавим единичку");
        }
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < fermaTestsQ; i++) {
            BigInteger current;
            do {
                current = new BigInteger(Number.bitLength(), random);
            }
            while (current.compareTo(Number) >= 0);


            if (!suspectValue.gcd(current).equals(BigInteger.ONE))
                return false;

            if (!FastBinaryPow(current, suspectValue.subtract(BigInteger.ONE), suspectValue).equals(1))
                return false;
        }

        return true;

    }

    public boolean MillersTest(BigInteger suspectValue) {
        if (suspectValue.equals(BigInteger.ONE.add(BigInteger.ONE)) ||
                suspectValue.equals(BigInteger.ONE.add(BigInteger.ONE.add(BigInteger.ONE))))
            return true;

        if (suspectValue.mod(BigInteger.ONE.add(BigInteger.ONE)).equals(BigInteger.ZERO))
            return false;


        long counter = 0;
        BigInteger temp = suspectValue.subtract(BigInteger.ONE);

        BigInteger x = BigInteger.ZERO;

        BigInteger two = BigInteger.ONE.add(BigInteger.ONE);
        BigInteger r1 = two;
        BigInteger r2 = temp.subtract(BigInteger.ONE);

        int rounds = 10;
        while(!temp.equals(BigInteger.ZERO) && temp.mod(two).equals(BigInteger.ZERO))
        {
            counter++;
            temp = temp.divide(two);
        }


        for(int i = 0; i < rounds; i++) {

            Random random = new Random(System.currentTimeMillis());
            BigInteger randNext = new BigInteger(512, random);
            randNext = randNext.mod(r2.subtract(r1));


            BigInteger tmp = r1.add(randNext);

            x = tmp.modPow(temp, suspectValue);

            if(x.equals(BigInteger.ONE) || x.equals(suspectValue.subtract(BigInteger.ONE)))
                continue;

            for(long j = 0; j < counter-1; j++)
            {
                x = x.modPow(two, suspectValue);

                if(x.equals(BigInteger.ONE))
                    return false;
                if(x.equals(suspectValue.subtract(BigInteger.ONE)))
                    break;
            }

            if (x.equals(suspectValue.subtract(BigInteger.ONE)))
                continue;

            return false;
        }
        return true;
    }
    public BigInteger CaratsubaMultiply()
    {
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Operations.class.getClassLoader().loadClass("com.kek.Operations");
        Random rand = new Random(System.currentTimeMillis());
        int randomBitsValue = rand.nextInt(512);
        BigInteger someValue = new BigInteger(randomBitsValue, rand);
        String stringValue = someValue.toString();
        System.out.println(stringValue);

        Operations operations = new Operations();
        boolean isSimpleFerma = operations.FermasTest(someValue);
        System.out.println("Ferma says : " + isSimpleFerma);

        boolean isSimpleMiller = operations.MillersTest(someValue);
        System.out.println("Miller says : " + isSimpleMiller);



    }


}
