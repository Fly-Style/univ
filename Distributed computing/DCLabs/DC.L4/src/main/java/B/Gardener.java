package B;

import java.util.concurrent.TimeUnit;

/**
 * @Author is flystyle
 * Created on 13.03.16.
 */
public class Gardener implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Gardener begin work");
                    Garden.locker.lockWrite();

                    for (int i = 0; i < Garden.size; i++) {
                        for (int j = 0; j < Garden.size; j++) {
                            if (Garden.garden.get(i, j) > 0) {
                                Garden.garden.set(i, j, 0.0);
                                System.out.println("Gardener changed [" + i + "][" + j + "]" );
                            }
                        }
                    }
                    System.out.println("Gardener worked");

                    Garden.locker.unlockWrite();
                    TimeUnit.SECONDS.sleep(2L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}
