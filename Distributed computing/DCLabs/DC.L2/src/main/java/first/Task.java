package first;

/**
 * @Author is flystyle
 * Created on 28.02.16.
 */
public class Task implements Runnable {

    private int start, end;

    public Task(int start, int end) {
        this.end = end;
        this.start = start;
    }

    public void run() {
        for (int i = start; i < end; i++) {
            if (VinnieExecution.vector.elementAt(i).equals(1)) {
                System.out.println("FIND");
                VinnieExecution.isFind = true;
                return;
            }
        }
    }

}
