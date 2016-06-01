package C;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Author is flystyle
 * Created on 20.03.16.
 */
public class TaskC {
    private final int threads = 3;
    private CyclicBarrier barrier;
    private ExecutorService threadPool = Executors.newFixedThreadPool(threads);
    private Random random = new Random(System.currentTimeMillis());

    private List<Integer> valuesStack = new ArrayList<>();
    private Stack<Integer> stack = new Stack<>();
   // private Stack<Integer> stackTwo = new Stack<>();

    private int[][] arr = {{10, 20, 30}, {12, 18, 29}, {13, 22, 25}};

    //public int first, second;

    private void Increment(int id) {
        arr[random.nextInt(arr[id].length - 1)][id]++;
    }

    private void Decrement(int id) {
        arr[random.nextInt(arr[id].length - 1)][id]--;
    }

    private class ArrayPlayer implements Runnable {

        private int[] array;
        private CyclicBarrier barrier;
        public final int id;

        public ArrayPlayer(CyclicBarrier barriers, int[] array, int id) {
            this.array = array;
            this.barrier = barriers;
            this.id = id;
        }
        @Override
        public void run() {
            int summ = 0;
            while (true) {
                System.out.println(Thread.currentThread() + " running");
                try {
                    for (int i = 0; i < array.length; i++) {
                        summ += array[i];
                    }
                    valuesStack.set(id, summ);
                    System.out.println(Thread.currentThread() + " summ added ");
                    stack.push(id);
                    System.out.println(Thread.currentThread() + " trying to await the barrier");
                    barrier.await();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public TaskC() {
        this.barrier = new CyclicBarrier(threads - 1, () -> {
            System.out.println("Checkpoint");
            if (FullChecker())
                threadPool.shutdown();
            else StackChecker();
            System.out.println("Norm");
        });

        for (int i = 0; i < threads; i++) {
            valuesStack.add(0);
        }

        threadPool.submit(new ArrayPlayer(barrier, arr[0], 0));
        threadPool.submit(new ArrayPlayer(barrier, arr[1], 1));
        threadPool.submit(new ArrayPlayer(barrier, arr[2], 2));
    }

    private boolean FullChecker() {
            long count =  valuesStack.stream().filter(valuesStack.get(0)::equals).count();
            if (count == valuesStack.size())
                return true;

            return false;
        }

    private void StackChecker() {
       if (!stack.empty()) {
           Integer first = stack.pop();
           Integer second = stack.pop();
           if (!valuesStack.get(first).equals(valuesStack.get(second))) {
               if (valuesStack.get(first).compareTo(valuesStack.get(second)) > 0)
                    Decrement(first);
               else Increment(first);
               //return false;
           }
       }
        //return true;
    }

    public static void main(String[] args) {
        TaskC task = new TaskC();
    }
}
