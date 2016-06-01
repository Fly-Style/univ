package B;

import java.util.concurrent.TimeUnit;

/**
 * @Author is flystyle
 * Created on 13.03.16.
 */
public class Nature implements Runnable {
    @Override
    public void run() {

        while (true) {
            int i = Garden.random.nextInt(Garden.size);
            int j = Garden.random.nextInt(Garden.size);
            System.out.println(i + "," + j);

            try {
                Garden.locker.lockWrite();
                System.out.println("Nature begin work");
                Garden.garden.set(i, j, 1.0);
                System.out.println("Nature worked, value [" + i + "," + j + "]" + " changed to 1");

                Garden.locker.unlockWrite();
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
