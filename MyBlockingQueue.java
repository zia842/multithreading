package multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<E> {

	private Queue<E> queue;
	private int max = 16;
	private ReentrantLock lock = new ReentrantLock(true);
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();

	public MyBlockingQueue(Queue<E> queue, int max) {
		super();
		this.queue = new LinkedList<>();
		this.max = max;
	}


	public void put(E e) throws InterruptedException {
		lock.lock();
		try {
			while(this.queue.size() == max) {
				notFull.await();
			}
			this.queue.add(e);
			notEmpty.signalAll();
		}
		finally {
			lock.unlock();
		}

	}

	public E take() throws InterruptedException {
		lock.lock();
		E item = null;
		try {
			while(this.queue.size() == 0) {
				notEmpty.await();
			}
			item = queue.remove();
			notFull.signalAll();
			return item;
		}
		finally {
			lock.unlock();
		}
	}

	public static void main(String []args) {
		MyBlockingQueue<Integer> q = new MyBlockingQueue<Integer>(new LinkedList<>(), 10);
		//Producer
		final Runnable producer = () -> {
			while(true) {
				try {
					q.put(new Random().nextInt());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		//Multiple Producers
		new Thread(producer).start();
		new Thread(producer).start();

		//Consumer
		final Runnable consumer = () -> {
			while(true) {
				try {
					System.out.println(q.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		//Multiple Producers
		new Thread(consumer).start();
		new Thread(consumer).start();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
