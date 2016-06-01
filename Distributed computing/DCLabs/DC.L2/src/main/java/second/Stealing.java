package second;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author is flystyle
 * Created on 28.02.16.
 */
public class Stealing {


    public static void main(String[] args) {
        Buffer stealBuffer = new Buffer();
        Buffer calculateBuffer = new Buffer();

        Producer Ivanov = new Producer(stealBuffer, "Ivanoff");
        ConsumerProducer Petrov = new ConsumerProducer(stealBuffer, calculateBuffer, "Petroff");
        Consumer Nechipor = new Consumer(calculateBuffer, "Nechipioruk");

        Ivanov.start();
        Petrov.start();
        Nechipor.start();

        System.out.println(Nechipor.getLoot());
    }
}
