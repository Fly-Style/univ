package A;

/**
 * @Author is flystyle
 * Created on 09.03.16.
 */
public class Bank {
    private int capacity;
    private int currentSize;

    public Bank(int capacity) {
        this.currentSize = 0;
        this.capacity = capacity;
    }

    public boolean getCurrentSize() {
        return currentSize == capacity;
    }

    public synchronized void Annihilate() {
        while (!TrueBees.filled) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.currentSize = 0;
        System.out.println("Filled, Annihilated");
        TrueBees.filled = false;
        notifyAll();
    }

    public synchronized void Add() throws InterruptedException {
        while (TrueBees.sema > 0) {
            wait();
        }

        TrueBees.sema++;
        this.currentSize++;
        System.out.println("Added : " + currentSize);
        TrueBees.sema--;

        TrueBees.filled = getCurrentSize();
        System.out.println(TrueBees.filled);
        notifyAll();
    }

}
