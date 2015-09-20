import java.lang.*;
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

    public BigInteger WiderEuclidGCD (BigInteger A, BigInteger B, BigInteger X, BigInteger Y)
    {
        if (A.equals(BigInteger.ZERO))
        {
            X = BigInteger.ZERO;
            Y = BigInteger.ONE;
            return B;
        }
        BigInteger x1 = BigInteger.ZERO, y1 = BigInteger.ZERO;
        BigInteger res =  WiderEuclidGCD (B.mod(A), A, x1, y1);
        X = y1.subtract(B.divide(A)).multiply(x1);
        Y = x1;
        return res;
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

            if (!current.modPow(suspectValue.subtract(BigInteger.ONE), suspectValue).equals(1))
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

    public BigInteger CaratsubaMultiply(BigInteger x, BigInteger y)   {
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 256) return x.multiply(y);

        N = (N / 2) + (N % 2);

        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        BigInteger ac = CaratsubaMultiply(a, c);
        BigInteger bd = CaratsubaMultiply(b, d);
        BigInteger abcd = CaratsubaMultiply(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
    }

    public BigInteger MontgomeryMultiplie (BigInteger x,  BigInteger y, BigInteger module) {

        // N ~ 2^counter;

        int counter = 0;
        BigInteger temp = BigInteger.ONE;
        while(temp.compareTo(module) < 0)
        {
            counter++;
            temp = temp.multiply(two);
            if (temp.compareTo(module) > 0)
                temp.divide(two);
        }

//        1) Вычисление числа R, которое является ближайшей сверху к N степенью двойки.
//        2) Перевод сомножителей в арифметику Монтгомери (в остаточные классы по
//                другому) A`=A*R mod N; B`=B*R mod N.
//        3) Вычисление мультипликативно-аддитивно-обратного к N относительно R
//          (N1=GCD(N,R) — это будет мультипликативно-обратное; N`=R-N1 — это аддитивно-обратное)
//        4) Вычисление мультипликативно-обратного к R относительно N (R`=GCD(R,N))


        BigInteger X = FastMultiply(x, temp, module);
        BigInteger Y = FastMultiply(y, temp, module);

        BigInteger reverseModule = module.mod(temp);        // multiplicative reverse by MODULE;
        BigInteger reverseAdditive = temp.subtract(module); // additive reverse;
        BigInteger reverseTemp = temp.mod(module);          // multiplicative reverse by TEMP;

//      5) T=A`*B`
//      6) M=T*N` mod R (т.к. R — степень 2 — это не деление)
//      7) T=T+M*N
//      8) T=T/R
//
//      9) Result=T*R` mod N

        BigInteger tmp = X.multiply(Y);
        BigInteger mtp = FastMultiply(tmp, reverseAdditive, temp);
        tmp = tmp.add(mtp.multiply(module));
        tmp = tmp.divide(temp);

        BigInteger res = FastMultiply(tmp, reverseTemp, module);
        return res;
    }


    public static void main(String[] args)
    {
        Random rand = new Random(System.currentTimeMillis());
        int randomBitsValue = rand.nextInt(512);
        BigInteger someValue = new BigInteger(randomBitsValue, rand);
        BigInteger karatsubaMultiplier = new BigInteger(128, rand);
        String stringValue = someValue.toString();
        System.out.println(stringValue);

        Operations operations = new Operations();
        boolean isSimpleFerma = operations.FermasTest(someValue);
        System.out.println("Ferma says : " + isSimpleFerma);

        boolean isSimpleMiller = operations.MillersTest(someValue);
        System.out.println("Miller says : " + isSimpleMiller);

        BigInteger karatsubaResult = operations.CaratsubaMultiply(someValue, karatsubaMultiplier);
        System.out.println("Miller multiplie : " + someValue + " * " + karatsubaMultiplier + karatsubaResult);



    }


}
