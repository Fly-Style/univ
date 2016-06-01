package first;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author is flystyle
 * Created on 28.02.16.
 */
public class VinnieExecution {

    static boolean isFind;
    final static int value = 90;
    public static Vector<Integer> vector = new Vector<Integer>(value);


    public static void main(String[] args) {

        for (int i = 0; i < value; i++)
            VinnieExecution.vector.add(0);

        Random random = new Random(System.currentTimeMillis());
        int element = random.nextInt(value);
        System.out.println(element);

        VinnieExecution.vector.set(element, 1);

        System.out.println(vector.toString());

        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < value/10; i++) {
            if (isFind)
                break;
            service.execute(new Task(i * 10, (i+1) * 10));
        }

        service.shutdown();
    }

}
