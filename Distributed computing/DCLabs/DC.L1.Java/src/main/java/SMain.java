import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author is flystyle
 *  Created on 17.02.16.
 */
public class SMain {

    public static AtomicInteger counter;
    public static int semaphore;

    public static boolean t1s, t2s;

    public Thread t1, t2, t3, t4;

    static
    {
        counter = new AtomicInteger(100);
        semaphore = 0;
        t1s = false;
        t2s = false;
    }

    public SMain() {
        t1 = new Thread(() -> {
            while (true)
                if (counter.get() < Main.upperValue) {
                    counter.compareAndSet(counter.get(), counter.getAndAdd(1));
                    System.out.println(counter);
                }
        });

        t2 = new Thread(() -> {
            while (true)
                if (counter.get() > Main.lowerValue) {
                    counter.compareAndSet(counter.get(), counter.getAndAdd(-1));
                    System.out.println(counter);
                }
        });

        t3 = new Thread(() -> {
            while (t1s) {
                counter.compareAndSet(counter.get(), Main.lowerValue);
                System.out.println(counter);
            }
        });

        t4 = new Thread(() -> {
            while (t2s) {
                counter.compareAndSet(counter.get(), Main.upperValue);
                System.out.println(counter);
            }

        });
    }

}
