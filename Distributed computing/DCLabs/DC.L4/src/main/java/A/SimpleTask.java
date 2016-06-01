package A;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author is flystyle
 * Created on 13.03.16.
 */
public class SimpleTask {
    static File file = new File("/Users/flystyle/Documents/6 semester/Distributed computing/DCLabs/DC.L4/src/main/java/A/file.txt");
    ExecutorService service = Executors.newFixedThreadPool(4);

    ReadersWritersLock lock = new ReadersWritersLock();

    public void addStr (List<String> str) {
        str.add("Lol");
        str.add("Kek");
        str.add("Alpha");
        str.add("tralala");
    }

    public static void main(String[] args) {

        Random random = new Random(System.currentTimeMillis());

        List<String> lines = new ArrayList<>();  ///Arrays.asList("Name " + random.nextInt(10));
        List<String> strings = new ArrayList<>();

        SimpleTask task = new SimpleTask();

        task.addStr(strings);

        task.service.submit(() -> {
            for (int i = 0; i < 4; i++) {
                try {
                    task.lock.lockRead();

                    try {
                        System.out.println("Reading by : " + Thread.currentThread().toString());
                        Files.lines(file.toPath()).filter(value -> value.equals(strings.get(1)))
                             .forEach(System.out::println);
                        Files.lines(file.toPath())
                             .forEach(System.out::println);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Readed");
                    task.lock.unlockRead();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            });

        task.service.submit(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    task.lock.lockWrite();
                    try {
                        System.out.println("Writing by : " + Thread.currentThread().toString());
                        lines.add(strings.get(random.nextInt(strings.size())) + random.nextInt(10));
                        Files.write(file.toPath(), lines);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("Writed");
                    task.lock.unlockWrite();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        task.service.shutdown();
    }
}