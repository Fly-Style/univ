
/**
 * @Author is flystyle
 * Created on 14.03.16.
 */
package C;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import scala.Tuple2;

public class SmokingGuys {

    private enum Components {
        match(0),
        paper(1),
        tobacco(2);

        int id;

        Components(int id) {
            this.id = id;
        }

        static Tuple2<Components,Components> map(int id) {
            switch (id) {
                case 0:
                    return new Tuple2<>(match,paper);
                case 1:
                    return new Tuple2<>(match,tobacco);
                case 2:
                    return new Tuple2<>(paper,tobacco);
                default:
                    return null;
            }
        }

        static boolean check (Components component,Tuple2<Components,Components> tuple) {
            return component != tuple._1() && component != tuple._2();
        }

        static Stream<Components> stream() {
            return Stream.of(match, paper, tobacco);
        }
    }

    private ExecutorService dealer;
    private ExecutorService smokers;
    private TransferQueue<Tuple2<Components,Components>> queue;

    final Random random = new Random();

    public SmokingGuys() {
        dealer = Executors.newSingleThreadExecutor();

        smokers = Executors.newFixedThreadPool(3);

        queue = new LinkedTransferQueue<>();
    }

    private final Supplier<Callable<Void>> dealerAction = () -> (Callable<Void>) () -> {
        while (true) {

            final Tuple2<Components, Components> map = Components.map(random.nextInt(3));

            System.out.println("Boss give : " + map.toString());
            queue.transfer(map);
            System.out.println("Boss climbs his pockets climbs pockets");
        }
    };

    private Callable<Void> produceSmoker(Components parts) {
        return () -> {
            while (true) {
                final Tuple2<Components, Components> peek = queue.peek();

                if (peek == null)
                    continue;

                if (Components.check(parts, peek)) {
                    TimeUnit.SECONDS.sleep(2L);
                    System.out.println("Smoker with " + parts.toString() + " smoking");
                    queue.remove();
                }
            }
        };
    }

    public void startProcess() throws InterruptedException {
        final List<Callable<Void>> collect = Components.stream()
                .map(this::produceSmoker)
                .collect(Collectors.toList());

        dealer.submit(dealerAction.get());

        smokers.invokeAll(collect);
    }


    public static void main(String[] args) throws InterruptedException {
        SmokingGuys taskC = new SmokingGuys();
        taskC.startProcess();
    }
}
