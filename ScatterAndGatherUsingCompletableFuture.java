package multithreading;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ScatterAndGatherUsingCompletableFuture {
	
	public static void main(String []args) throws InterruptedException, ExecutionException, TimeoutException {
		ScatterAndGatherUsingCompletableFuture s = new ScatterAndGatherUsingCompletableFuture();
		Set<Integer> prices = s.getPrices(123);
		
	} 
	
	
	public Set<Integer> getPrices(int productID) throws InterruptedException, ExecutionException, TimeoutException{
		Set<Integer> prices = Collections.synchronizedSet(new HashSet<Integer>()); //Thread Safe as multiple threads can access it and save the response
		
		CompletableFuture<Void> task1 = CompletableFuture.runAsync(new HttpTask2("https://www.google.com", productID, prices));
		CompletableFuture<Void> task2 = CompletableFuture.runAsync(new HttpTask2("https://www.yahoo.com", productID, prices));
		CompletableFuture<Void> task3 = CompletableFuture.runAsync(new HttpTask2("https://www.amazon.com", productID, prices));
		
		CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3); 
		allTasks.get(3, TimeUnit.SECONDS); //Wait for all taks to finish but max of 3 seconds
		
		return prices;
	}

}

class HttpTask2 implements Runnable {
	
	private String url;
	private int productID;
	private Set<Integer> prices; //responses
	
	public HttpTask2(String url, int productID, Set<Integer> prices) {
		super();
		this.url = url;
		this.productID = productID;
		this.prices = prices;
	}
	
	@Override
	public void run() {
		int price = 0;
		//Make Http Call
		this.prices.add(price);
		
	}
	
	
}