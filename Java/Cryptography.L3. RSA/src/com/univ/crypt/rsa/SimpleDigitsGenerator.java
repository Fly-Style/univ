package com.univ.crypt.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by flystyle on 14.11.15.
 */

public class SimpleDigitsGenerator {

    private static ArrayList<BigInteger> variants = new ArrayList<>();
    private BigInteger firstNumber;
    private BigInteger lastNumber;

    static
    {
        variants.add(new BigInteger("883"));
        variants.add(new BigInteger("1213"));
        variants.add(new BigInteger("1999"));
        variants.add(new BigInteger("2447"));
        variants.add(new BigInteger("2671"));
        variants.add(new BigInteger("3089"));
        variants.add(new BigInteger ("3137"));
        variants.add(new BigInteger ("3557"));
        variants.add(new BigInteger ("7561"));
        variants.add(new BigInteger ("12821"));
        variants.add(new BigInteger ("28657"));
        variants.add(new BigInteger ("33461"));
        variants.add(new BigInteger ("96557"));
    }



    SimpleDigitsGenerator () {
        Random random = new Random(System.currentTimeMillis());
        //this.firstNumber = variants.get(random.nextInt(variants.size()-1));
        //this.lastNumber = variants.get(random.nextInt(variants.size()-1));
        this.firstNumber = BigInteger.probablePrime(16, random);
        this.lastNumber = BigInteger.probablePrime(16, random);
        //while (lastNumber.equals(firstNumber))
        //    this.lastNumber = variants.get(random.nextInt(variants.size()-1));

        System.out.println("Digits : " + firstNumber + " , " + lastNumber);

    }


    public BigInteger getFirstNumber() {
        return firstNumber;
    }

    public BigInteger getLastNumber() {
        return lastNumber;
    }
}
