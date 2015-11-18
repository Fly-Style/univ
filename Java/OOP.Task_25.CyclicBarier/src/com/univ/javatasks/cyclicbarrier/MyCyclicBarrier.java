package com.univ.javatasks.cyclicbarrier;


import java.io.PrintStream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by flystyle on 04.11.15.
 */
public class MyCyclicBarrier {

    private int count;

    private final int parties;
    private final Runnable barrierCommand;
    private Generation generation = new Generation();


    private static class Generation {
        boolean broken = false;
    }

        /**
         * Not naked notify
         */
    private void nextGeneration() {

        synchronized (this) {
            notifyAll();
        }
        count = parties;
        generation = new Generation();
    }

        /**
         * Not naked notify
         */
    private void breakBarrier() {

        generation.broken = true;
        count = parties;
        synchronized (this) {
            notifyAll();
        }
    }


    public MyCyclicBarrier (int parties, Runnable command) {

        if (parties <= 0) {
            throw new IllegalArgumentException();
        }
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = command;

    }

    public MyCyclicBarrier (int parties) {
        this(parties, null);
    }

    public int getParties () {
        return parties;
    }

    public synchronized boolean isBroken () {
        return generation.broken;
    }

    public synchronized void Reset () {
        this.breakBarrier();
        generation = new Generation();
    }

    public synchronized int GetWaitingCount () {
        return parties - count;
    }

    public synchronized void await() throws BrokenBarrierExeption, InterruptedException {
        final Generation g = generation;
        if (g.broken) {
            throw new BrokenBarrierExeption();
        }

        if (Thread.interrupted()) {
            breakBarrier();
            throw new InterruptedException();
        }

        int index = --count;

        if (index == 0) {
            boolean runAction = false;
            try {
                final Runnable command = barrierCommand;
                if (command != null) {
                    command.run();
                }
                runAction = true;
                nextGeneration();
            }

            finally {
                if (!runAction) {
                    breakBarrier();
                }
            }
        }

        for(;;) {
            if (g.broken) {
                throw new BrokenBarrierExeption();
            }

            if (g != generation)
                return;
            wait();
        }
    }

    public static void main(String[] args) {
        AtomicInteger counter =  new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        MyCyclicBarrier barrier = new MyCyclicBarrier(2, ()-> System.out.println("Thread : " + counter.incrementAndGet()));

        Future <?> result1 = executorService.submit(() -> {

                while (counter.get() < 2) {
                    System.out.println ("Thread incoming ... " + Thread.currentThread().getName());

                    try {
                        barrier.await();
                    }

                    catch (BrokenBarrierExeption | InterruptedException exception) {}

                    System.out.println("Completed barrier by " + Thread.currentThread().getName());
                }

        });


        Future <?> result2 = executorService.submit(() -> {
                while (counter.get() < 2) {
                    System.out.println ("Thread incoming ... " + Thread.currentThread().getName());

                    try {
                        TimeUnit.SECONDS.sleep(3);
                    }

                    catch (InterruptedException ignoring) {}

                    try {
                        barrier.await();
                    }

                    catch (BrokenBarrierExeption | InterruptedException exception) {}

                    System.out.println("Completed barrier by " + Thread.currentThread().getName());
                }

        });

        while (!result1.isDone() && !result2.isDone()) {
            executorService.shutdown();
            //System.out.println("DONE");
        }

    }


}
