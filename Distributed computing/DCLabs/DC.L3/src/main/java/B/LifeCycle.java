package B;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author is flystyle
 * Created on 11.03.16.
 */
public class LifeCycle {

    public Hairdresser hairdresser = new Hairdresser();
    static Semaphore locker = new Semaphore();

    public static void main(String[] args) {

        LifeCycle lifeCycle = new LifeCycle();
        lifeCycle.hairdresser.start();


    }

}
