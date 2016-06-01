package B;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author is flystyle
 * Created on 10.03.16.
 */

public class Hairdresser extends Thread {

    static Queue<Visitor> visitorQueue = new LinkedBlockingQueue<>();

    public Hairdresser() {
        for (int i = 0; i < 10; i++) {
            visitorQueue.add(VisitorsFabric.produce("V" + i));
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized (visitorQueue) {
                if (visitorQueue.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                else {
                    Visitor v = visitorQueue.poll();
                    if (v != null) {
                        LifeCycle.locker.lock();
                        System.out.println("New Client Incoming");
                        v.dressed = true;
                        System.out.println("Client Dressed : " + v.getName());
                        LifeCycle.locker.unlock();
                        notify();
                    }
                }
            }

        }
    }
}
