package second;

/**
 * @Author is flystyle
 * Created on 28.02.16.
 */
public class Buffer {
    private int buffer = 0;
    private boolean bufferBusy = false;

    public synchronized void put(int value) {
        while (bufferBusy) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer = value;
        bufferBusy = true;
        notifyAll();
    }

    public synchronized int get() {
        while (!bufferBusy) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bufferBusy = false;
        notifyAll();
        return buffer;
    }

}
