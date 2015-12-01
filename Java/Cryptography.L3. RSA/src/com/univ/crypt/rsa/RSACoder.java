package com.univ.crypt.rsa;

import java.math.BigInteger;

/**
 * Created by flystyle on 14.11.15.
 */
public class RSACoder {

    private BigInteger data;
    private BigInteger key;
    private BigInteger fullDigit;

    public RSACoder(BigInteger data, BigInteger secretKey, BigInteger full) {
        this.data = data;
        this.fullDigit = full;
        this.key = secretKey;
    }

    public BigInteger Encode () {
        BigInteger result = this.data.modPow(key, fullDigit);
        return result;

    }
}
