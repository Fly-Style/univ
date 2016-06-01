package B;

/**
 * @Author is flystyle
 * Created on 10.03.16.
 */
public class SynchronizedSingleton {
    public static volatile SynchronizedSingleton instance;

    private SynchronizedSingleton() {}

    public static SynchronizedSingleton getInstance() {
        if (instance == null)
            synchronized (SynchronizedSingleton.class) {
                if (instance == null)
                    instance = new SynchronizedSingleton();
            }

        return instance;
    }
}
