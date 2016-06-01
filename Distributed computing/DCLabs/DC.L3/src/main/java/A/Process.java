package A;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author is flystyle
 * Created on 09.03.16.
 */
public class Process {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void Action () {
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Bee());
        }
        return;
    }
}

