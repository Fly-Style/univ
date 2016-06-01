package A;

/**
 * @Author is flystyle
 * Created on 09.03.16.
 */
public class Bee implements Runnable {
    public void run() {
        try {
            TrueBees.bank.Add();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
