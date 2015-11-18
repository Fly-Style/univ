package com.univ.javatasks.cyclicbarrier;
/**
 * Created by flystyle on 04.11.15.
 */

public class BrokenBarrierExeption extends Exception {

    public BrokenBarrierExeption() {
    }

    public BrokenBarrierExeption(String message) {
        super(message);
    }

    public BrokenBarrierExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
