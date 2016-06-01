package B;

import java.util.concurrent.locks.Lock;

/**
 * @Author is flystyle
 * Created on 11.03.16.
 */
public class Semaphore {
    private boolean lock;

    public Semaphore() {
        this.lock = false;
    }

    public void lock() {
        this.lock = true;
    }

    public void unlock() {
        this.lock = false;
    }

    public boolean isLock() {
        return lock;
    }
}
