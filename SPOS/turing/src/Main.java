/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
public class Main
{

    public static void main(String[] args)
    {
        TuringMachine TM1 = MachinesLibrary.EqualBinaryWords();

        boolean done = TM1.Run("010000110101#010000110101", false);
        if (done) {
            System.out.println("The input was accepted.");
        }
        else {
            System.out.println("The input was rejected.");
        }

        TuringMachine TM2 = MachinesLibrary.Additional();

        boolean done2 = TM2.Run("1111101111", false);
        if (done2) System.out.println("K USPEHU PRISHEL");
        else System.out.println("PALITE PARNI");
    }

}