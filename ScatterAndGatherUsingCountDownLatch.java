package multithreading;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Retrieve prices from N sources waiting for max 3 seconds
 * From your application call multiple services/urls
 * consolidate the response and send it over.
 * 
 * 1. Option Serial -- Very Slow
 * 2. Parallel calls consolidate response and send it over
 * 
 * Trigger, wait for N task 
 * Add Timeout
 *  
 * 
 * @author zia84
 *
 */

public class ScatterAndGatherUsingCountDownLatch {
	
	public static void main(String []args) throws InterruptedException {
		ScatterAndGatherUsingCountDownLatch s = new ScatterAndGatherUsingCountDownLatch();
		Set<Integer> prices = s.getPrices(123);
		
	} 
	
	ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); //Will Provide number of cores / processors
	
	public Set<Integer> getPrices(int productID) throws InterruptedException{
		Set<Integer> prices = Collections.synchronizedSet(new HashSet<Integer>()); //Thread Safe as multiple threads can access it and save the response
		CountDownLatch latch = new CountDownLatch(3);
		
		threadPool.submit(new HttpTask("https://www.google.com", productID, prices, latch));
		threadPool.submit(new HttpTask("https://www.yahoo.com", productID, prices, latch));
		threadPool.submit(new HttpTask("https://www.amazon.com", productID, prices, latch));
		
		//latch.await(); Wait for all task to be done/finished
		latch.await(3, TimeUnit.SECONDS); //Wait for task to get done but with time out restriction; If task is done come out else timeout will occur
		
		return prices;
	}

}

class HttpTask implements Runnable {
	
	private String url;
	private int productID;
	private Set<Integer> prices; //responses
	private CountDownLatch latch;
	
	public HttpTask(String url, int productID, Set<Integer> prices, CountDownLatch latch) {
		super();
		this.url = url;
		this.productID = productID;
		this.prices = prices;
		this.latch = latch;
	}
	
	@Override
	public void run() {
		int price = 0;
		//Make Http Call
		this.prices.add(price);
		
		latch.countDown(); //My Job is done
	}
	
	
}
