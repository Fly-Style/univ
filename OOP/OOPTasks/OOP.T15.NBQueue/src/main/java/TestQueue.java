import java.util.Random;

public class TestQueue {

	public class Getter extends Thread {
		Queue queue;

		public Getter(Queue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				System.out.println(queue.getObject());
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public class Setter extends Thread {
		Queue<Integer> queue;
		Random r = new Random();
		public Setter(Queue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				queue.putObject(r.nextInt(100));
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void main(String[] args) {
		Queue<Integer> q = new Queue<>();
		TestQueue test = new TestQueue();
		Getter g = test.new Getter(q);
		Setter s = test.new Setter(q);

		s.start();
		g.start();
	}

}
