package multithreading;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhilosophers {

	static int philosophersNumber = 5;
	static Philosopher philosophers[] = new Philosopher[philosophersNumber];
	static Fork forks[] = new Fork[philosophersNumber];


	static class Fork {

		public Semaphore semaphore = new Semaphore(1);

		void grab() {
			try {
				semaphore.acquire();
			}
			catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		void release() {
			semaphore.release();
		}

		boolean isFree() {
			return semaphore.availablePermits() > 0;
		}
	}

	static class Philosopher extends Thread {

		public int number;
		public Fork leftFork;
		public Fork rightFork;

		Philosopher(int num, Fork left, Fork right) {
			number = num;
			leftFork = left;
			rightFork = right;
		}

		public void run(){
			System.out.println("Hi! I'm philosopher #" + number);

			while (true) {
				leftFork.grab();
				System.out.println("Philosopher #" + number + " grabs left fork.");
				rightFork.grab();
				System.out.println("Philosopher #" + number + " grabs right fork.");
				eat();
				leftFork.release();
				System.out.println("Philosopher #" + number + " releases left fork.");
				rightFork.release();
				System.out.println("Philosopher #" + number + " releases right fork.");
			}
		}

		void eat() {
			try {
				int sleepTime = ThreadLocalRandom.current().nextInt(0, 10);
				System.out.println("Philosopher #" + " eats for " + sleepTime);
				Thread.sleep(sleepTime);
			}
			catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

	}


	public static void main(String argv[]) {
		System.out.println("Dining philosophers problem.");
		DiningPhilosophers d = new DiningPhilosophers();
		d.detectLocks();

		for (int i = 0; i < philosophersNumber; i++) {
			forks[i] = new Fork();
		}

		for (int i = 0; i < philosophersNumber; i++) {
			philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % philosophersNumber]);
			philosophers[i].start();
		}

		/*while (true) {
			try {
				// sleep 1 sec
				Thread.sleep(1000);

				// check for deadlock
				boolean deadlock = true;
				for (Fork f : forks) {
					if (f.isFree()) {
						deadlock = false;
						break;
					}
				}
				if (deadlock) {
					Thread.sleep(1000);
					System.out.println("Hurray! There is a deadlock!");
					break;
				}
			}
			catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		System.out.println("Bye!");
		System.exit(0); */
	}
	
	public void detectLocks() {
		new Thread(this::deadLockDetection).start();
	}
	
	private void deadLockDetection() {
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		long threadIDs[] = threadBean.findDeadlockedThreads();
		boolean deadLock = threadIDs!=null && threadIDs.length > 0;
		System.out.println("Dead Lock Found " + deadLock);
	}


}
