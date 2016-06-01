package B;

import com.sun.org.apache.xalan.internal.lib.ExsltBase;

import java.util.concurrent.*;

/**
 * @Author is flystyle
 * Created on 20.03.16.
 */
public class TaskB {

    private final int threads = 4;

    private CyclicBarrier barrier;
    private ExecutorService threadPool = Executors.newFixedThreadPool(threads);

    //public boolean done = false;

    private class StringChanger implements Runnable {

        private CyclicBarrier barrier;

        private String str;
        private String symbol;
        private String otherSymbol;

        private boolean reverse = false;

        public StringChanger(CyclicBarrier barrier, String str, String otherSymbol, String symbol) {
            this.barrier = barrier;
            this.otherSymbol = otherSymbol;
            this.str = str;
            this.symbol = symbol;
        }


        public void run() {
            while (true) {
                try {
                    System.out.println(Thread.currentThread() + " before changing string : " + str);

                    if (!reverse) {
                        str = str.replaceAll(symbol, otherSymbol);
                        reverse = true;
                    }
                    else {
                        str = str.replaceAll(otherSymbol, symbol);
                        reverse = false;
                    }

                    System.out.println(Thread.currentThread() + " after changing string : " + str);

                    TimeUnit.SECONDS.sleep(1L);
                    barrier.await();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean TaskCheck () {
        return 1 == 1;
    }

    public void Work() {
        threadPool.submit(new StringChanger(barrier, "ACDABDBCD", "A", "C"));
        threadPool.submit(new StringChanger(barrier, "ACDABDBCD", "B", "D"));
        threadPool.submit(new StringChanger(barrier, "ACDABDBCD", "C", "A"));
        threadPool.submit(new StringChanger(barrier, "ACDABDBCD", "D", "B"));
    }

    public TaskB() {
        this.barrier = new CyclicBarrier(threads, () -> {
            System.out.println("New Generation");
        });

    }

    public static void main(String[] args) {
        TaskB task = new TaskB();
        task.Work();
    }
}
