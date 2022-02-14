package multithreading;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class BoundedBlockingQueue<E> {
	
	Deque<E> q;
	int capacity;

	public BoundedBlockingQueue(int capacity) {
		this.capacity = capacity;
		this.q = new ArrayDeque<E>();
	}

	public synchronized void put(E element) throws InterruptedException {
		while(this.q.size() == this.capacity) {
			this.wait();
		}
		this.q.addFirst(element);
		this.notifyAll();
	}

	public synchronized E take() throws InterruptedException {
		while(this.q.isEmpty()) {
			this.wait();
		}
		E e = this.q.removeLast();
		this.notifyAll();
		return e;
	}

	public int size() {
		return this.q.size();
	}
	
	public static void main(String []args) {
		BoundedBlockingQueue<Integer> q = new BoundedBlockingQueue<Integer>(10);
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
