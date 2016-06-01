package second;

/**
 * @Author is flystyle
 * Created on 29.02.16.
 */
public class Consumer extends Thread {
    private Buffer buffer;
    private String number;
    private int loot = 0;

    public Consumer(Buffer buffer, String number) {
        this.buffer = buffer;
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {
            Integer getted = buffer.get();
            System.out.println("Consumer " + this.number + " get: " + getted);
            loot += getted;
            System.out.println("Summ of stealed goods : " + getLoot());
        }

    }

    public int getLoot() {
        return loot;
    }
}
