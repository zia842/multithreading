package multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author zia84
 *
 * 1. Fixed Thread Pool -   	Fixed number of Threads in Pool
 * 2. Cached Thread Pool -  	If there are 10 task and 10 threads in pool and all threads are busy.
 * 								If 11th task arrives it creates new thread and places it in pool.
 * 3. Scheduled Thread Pool -   Schedule the task to run based on time / delay (and retrigger for Fixed Rate / Fixed Delay)
 * 4. Single Thread Executor -  Only one thread in pool fetches one task from queue and executes it. 
 * 								Recreates Thread if killed because of the task error / exception. 
 * 								Task are always executed in same order bcz only 1 thread in execution
 * 
 */

/**
 * 
 *  ExecutorService executorService = Executors.newFixedThreadPool(coreCount);
 *  executorService.shutdown();	 Initiates shut down and finishes all the assigned task 
 *  							 No new task are accepted
 *  							 Previously submitted tasks are executed
 *  
 *  executorService.shutdownNow(); Will Initiate and return all queued task
 *  							   No new task are accepted	
 *  							   Previously submitted tasks waiting in queue are returned
 *  							   Task being run by thread(s) are attempted to stop (No Guarantee)
 *  executorService.isShutdown();  Will Return true since shut down has begin
 *  executorService.isTerminated(); Will Return true if all tasks are completed included queued ones
 *  executorService.awaitTermination(1, TimeUnit.SECONDS); Blocks until all tasks are completed or if timeout occurs
 *  
 *  
 *  To stop thread(s) in execution use interrupts, volatile / Atomic Boolean
 *  
 *  Thread t1 = new Thread() -> {
 *  	while(!Thread.currentThread().isInterrupted()){ //Keep checking for Interrupted we can use volatile/AtomicBoolean variable also 
 *  		System.out.println("Stop Thread");
 *  		return;
 *  	}
 *  });
 *  
 *  t1.interrupt(); //ExecutorService.shutdownNow(), ExecutorService.shutdown(); uses thread.interrupt 
 *  Future<?> future =  executorService.submit(new ValidateTaskCallable());
 *  future.cancel(true) 
 *
 * 	Timeouts
 *	Thread.sleep(100);
 *	ScheduledExecutorService scheduler = Executors.newFixedThreadPool(3);
 *	scheduler.schedule (() -> {
 *	task.stop();
 *	}, 10, TimeUnit.Minutes);
 *	
 *	future.get(10, TimeUnit.Minutes);
 *
 * 
 * 
 */

public class ThreadPoolExample {

	public static void main(String []args) {
		int coreCount = Runtime.getRuntime().availableProcessors();
		System.out.println("Core Count " + coreCount);
		ExecutorService executorService = Executors.newFixedThreadPool(coreCount);
		CountDownLatch countDown = new CountDownLatch(coreCount);
		AtomicInteger count = new AtomicInteger(0);
		for(int i=0;i<100;i++) {
			executorService.submit(new ValidateTask(i, count, countDown));
		}
		try {
			countDown.await();
			//countDown.await(1, TimeUnit.SECONDS);
			//executorService.awaitTermination(0, TimeUnit.SECONDS);
			//System.out.println("Awaited Count " + countDown.getCount());
			//countDown.await(100, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdown();
		//executorService.shutdownNow();
	}

}

class ValidateTask implements Runnable{

	int number;
	
	AtomicInteger count;
	
	CountDownLatch countDown;

	public ValidateTask() {

	}

	public ValidateTask(int number, AtomicInteger count, CountDownLatch countDown) {
		this.number = number;
		this.count = count;
		this.countDown = countDown;
	}

	@Override
	public void run() {
		long result = this.number * this.number;
		System.out.println("Thread Name : " + Thread.currentThread().getName() + " Number " + this.number + " Result is " + result + " Counter " + count.incrementAndGet());
		countDown.countDown();
	}
}