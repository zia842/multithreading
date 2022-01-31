package multithreading;

public class RunnableAnonymous {
	
	public static void main(String []args) {
		Runnable runnable = new Runnable() {
			public void run() {
				System.out.println("Running...........");
			}
		};
		
		Thread t = new Thread(runnable);
		t.start();
	}

}
