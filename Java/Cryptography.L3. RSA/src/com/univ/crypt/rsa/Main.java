package com.univ.crypt.rsa;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by flystyle on 14.11.15.
 */
public class Main {

    private SimpleDigitsGenerator generator = new SimpleDigitsGenerator();
    private final int bitsNumber = 16;

    private BigInteger nSumm = generator.getFirstNumber().multiply(generator.getLastNumber());
    private BigInteger mSumm = (generator.getFirstNumber().subtract(BigInteger.ONE)).multiply(generator.getLastNumber().subtract(BigInteger.ONE));

    private BigInteger secretExponent;
    private BigInteger publicExponent;

    private BigInteger Temp = BigInteger.ZERO;

    private BigInteger GCD(BigInteger divided, BigInteger divider)       //greatest common divisor
    {
        if (divided.equals(BigInteger.ZERO))
            return divided;
        return GCD(divider, divided.mod(divider));
    }

    private BigInteger Rest (BigInteger divide, BigInteger divider) {
        BigInteger temp = BigInteger.ZERO;

        System.out.println(divide + " " + divider);

        while (divide.compareTo(temp) > 0) {
            temp = temp.add(divider);

            if (divide.compareTo(temp) <= 0) {
                if (divide.compareTo(temp) == 0) {
                    Temp = BigInteger.ZERO;
                    return BigInteger.ZERO;
                }
                else {
                    temp = temp.subtract(divider);
                    this.Temp = temp;
                    return divide.subtract(temp);
                }
            }

        }
        return null;
    }

    public BigInteger WiderEuclidGCD (BigInteger a, BigInteger b) {

        BigInteger tempA = new BigInteger(a.toString());
        BigInteger tempB = new BigInteger(b.toString());
        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ONE;
        BigInteger result = BigInteger.ZERO;

        BigInteger q, r, x1, x2, y1, y2;

        if (b.equals(BigInteger.ZERO)) {
            result = tempA;
            return result;
        }

        x2 = BigInteger.ONE;
        x1 = BigInteger.ZERO;
        y2 = BigInteger.ZERO;
        y1 = BigInteger.ONE;

        while (tempB.compareTo(BigInteger.ZERO) > 0) {
            q = tempA.divide(tempB);
            r = tempA.subtract(q.multiply(tempB));
            x = x2.subtract(q.multiply(x1));
            y = y2.subtract(q.multiply(y1));
            tempA = tempB;
            tempB = r;

            x2 = x1;
            x1 = x;
            y2 = y1;
            y1 = y;
        }

        result = tempA;
//        x = x2;
//        y = y2;
        return result;
    }

    public BigInteger FindSimply (BigInteger a, BigInteger b) {

        BigInteger tempA = new BigInteger(a.toString());
        BigInteger tempB = new BigInteger(b.toString());

        LinkedList<BigInteger> E = new LinkedList<BigInteger>();
        E.add(BigInteger.ONE);   /// E[0][0] = a11
        E.add(BigInteger.ZERO);  /// E[1][0] = a21
        E.add(BigInteger.ZERO);  /// E[0][1] = a12
        E.add(BigInteger.ONE);   /// E[1][1] or Y;

        BigInteger r = Rest(tempA, tempB);
        System.out.println("Rest is : " + r);
        while (!r.equals(BigInteger.ZERO)) {
            BigInteger t1 = E.get(2).multiply(BigInteger.ONE);
            BigInteger t2 = (E.get(0).multiply(BigInteger.ONE)).add(E.get(2).multiply(Temp.negate()));
            BigInteger t3 = E.get(3).multiply(BigInteger.ONE);
            BigInteger t4 = (E.get(2).multiply(BigInteger.ONE)).add(E.get(3).multiply(Temp.negate()));
            tempA = tempB;
            tempB = r;

            E.clear();
            E.add(t1);
            E.add(t2);
            E.add(t3);
            E.add(t4);

            r = Rest(tempA, tempB);
        }
        return E.get(3);
    }

    public Main() {

        this.publicExponent = new BigInteger("65537");
        this.secretExponent = this.publicExponent.modInverse(this.getmSumm());
        //this.secretExponent = FindSimply(mSumm, publicExponent);

    }

    public BigInteger getnSumm() {
        return nSumm;
    }

    public BigInteger getmSumm() {
        return mSumm;
    }

    public BigInteger getPublicExponent() {
        return publicExponent;
    }

    public BigInteger getSecretExponent() {
        return secretExponent;
    }

    public static void main(String[] args) {
        Main thisMain = new Main();

        BigInteger publicKey = thisMain.getPublicExponent();
        BigInteger privateKey = thisMain.getSecretExponent();

        System.out.println("Public Key : " + publicKey );
        System.out.println("Secret Key : " + privateKey );
        System.out.println("N : " + thisMain.getnSumm());
        System.out.println("M : " + thisMain.getmSumm());

        BigInteger message = new BigInteger("5186654");

        RSACoder encoder = new RSACoder (message, publicKey, thisMain.getnSumm());
        BigInteger encoded = encoder.Encode();

        System.out.println(encoded);

        RSACoder decoder = new RSACoder (encoded, privateKey, thisMain.getnSumm());
        BigInteger decoded = decoder.Encode();

        System.out.println(decoded);



    }
}
