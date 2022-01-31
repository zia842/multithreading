package multithreading;

public class ThreadUsingLambda {
	

	public static void main(String []args) {
		Runnable runnable = () -> {
			System.out.println("Running...........");
			System.out.println("Thread Started........");
		};
		
		Thread t = new Thread(runnable);
		t.start();
	}


}
