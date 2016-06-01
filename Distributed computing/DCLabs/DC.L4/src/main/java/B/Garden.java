package B;

import A.ReadersWritersLock;
import Jama.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author is flystyle
 * Created on 13.03.16.
 */
public class Garden {
    static int size = 5;
    static Matrix garden = new Matrix(size,size); // zero values matrix

    static ReadersWritersLock locker = new ReadersWritersLock();
    static Random random = new Random(System.currentTimeMillis());
    ExecutorService service = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        Garden gardens = new Garden();

        gardens.service.submit(new Nature());
        gardens.service.submit(new Gardener ());

        gardens.service.submit(() -> {
            while (true) {
                try {
                    locker.lockRead();
                    garden.print(NumberFormat.INTEGER_FIELD, size);
                    System.out.println("Printing worked");
                    locker.unlockRead();
                    TimeUnit.SECONDS.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gardens.service.submit(() -> {
            PrintWriter writer = null;
            while (true) {
                try {
                    try {
                        writer = new PrintWriter(new File("/Users/flystyle/Documents/6 semester/Distributed computing/DCLabs/DC.L4/src/main/java/B/fileB.txt"));
                        locker.lockRead();
                        garden.print(writer, NumberFormat.INTEGER_FIELD, size);
                        System.out.println("Printing worked");
                        locker.unlockRead();
                        TimeUnit.SECONDS.sleep(7L);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gardens.service.shutdown();

    }

}
