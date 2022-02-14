package multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolCallableExample {
	
	public static void main(String []args) {
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
		List<Future> allFutureList = new ArrayList<>();
		for(int i=0;i<100;i++) {
			Future<?> future =  executorService.submit(new ValidateTaskCallable());
			allFutureList.add(future);
		}
		
		System.out.println("Performing some task...........");
		
		for(int i=0;i<100;i++) {
			Future<Integer> future = allFutureList.get(i);
			try {
				Integer r = future.get();
				System.out.println("Result is " + r);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		try {
			executorService.awaitTermination(0, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdown();
	}

}

class ValidateTaskCallable implements Callable<Integer>{
	
	@Override
	public Integer call() throws Exception{
		Thread.sleep(2000);
		return new Random().nextInt();
	}
	
}
