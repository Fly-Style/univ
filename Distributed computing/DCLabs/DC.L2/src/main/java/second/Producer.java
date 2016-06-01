package second;

import java.util.Random;

/**
 * @Author is flystyle
 * Created on 28.02.16.
 */
public class Producer extends Thread {
    private Buffer buffer;
    private String number;

    private Random random = new Random(System.currentTimeMillis());

    public Producer(Buffer buffer, String number) {
        this.buffer = buffer;
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int produced = random.nextInt(30);
            buffer.put(produced);
            System.out.println("Producer " + this.number + " put: " + produced);
        }
    }
}
