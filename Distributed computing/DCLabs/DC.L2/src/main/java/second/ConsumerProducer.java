package second;

import java.util.Random;
import java.util.RandomAccess;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author is flystyle
 * Created on 28.02.16.
 */
public class ConsumerProducer extends Thread{
    private Buffer bufferConsume;
    private Buffer bufferProduce;
    private String number;


    public ConsumerProducer(Buffer Cbuffer, Buffer Pbuffer, String number) {
        this.bufferConsume = Cbuffer;
        this.bufferProduce = Pbuffer;
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {
            int getted = bufferConsume.get();
            System.out.println("Consumer " + this.number + " get: " + getted);
            bufferProduce.put(getted);
            System.out.println("Consumer " + this.number + " put: " + getted);
        }
    }
}
