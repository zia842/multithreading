package multithreading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BoundedBlockingQueueUsingArray {
	
	public static void main(String []args) {
		
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
		
		//Producer
		final Runnable producer = () -> {
			while(true) {
				try {
					queue.put(new Random().nextInt());
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
					System.out.println(queue.take());
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
