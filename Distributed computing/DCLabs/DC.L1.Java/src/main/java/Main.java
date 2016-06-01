import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author @flystyle on 16.02.16.
 */
public class Main {

    public static volatile AtomicInteger counter;
    public static final int lowerValue = 0;
    public static final int upperValue = 240;

    private Queue<Runnable> threadQueue;

    static
    {
        counter = new AtomicInteger(50);
    }

    public Main() {

        threadQueue = new LinkedBlockingQueue<>(2);

        threadQueue.add(new Runnable() {
            @Override
            public void run() {
                while (true)
                if (counter.get() < upperValue) {
                    counter.compareAndSet(counter.get(), counter.getAndAdd(1));
                    System.out.println(counter);
                }
            }
        });
        threadQueue.add(new Runnable() {
            @Override
            public void run() {
                while (true)
                    if (counter.get() > lowerValue) {
                        counter.compareAndSet(counter.get(), counter.getAndAdd(-1));
                        System.out.println(counter);
                    }
            }
        });

       Thread t1 = new Thread(threadQueue.poll());
       Thread t2 = new Thread(threadQueue.poll());
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();

    }

    public static void main(String[] args) {
        Main program = new Main();
    }
}
