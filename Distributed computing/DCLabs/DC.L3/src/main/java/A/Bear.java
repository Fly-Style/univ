package A;

/**
 * @Author is flystyle
 * Created on 09.03.16.
 */

public class Bear extends Thread {
    @Override
    public void run() {
        System.out.println("Run Bear");
        TrueBees.bank.Annihilate();
    }
}
