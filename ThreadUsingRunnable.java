package multithreading;

public class ThreadUsingRunnable {
	

	public static class MyRunnable implements Runnable
	{
		int n;
		
		public MyRunnable(int n) {
			this.n = n;
		}
		
		@Override
		public void run() {
			int result = 1;
			for(int i=1;i<=this.n;i++) {
				result *= i;
			}
			System.out.println("Thread Name is " + Thread.currentThread().getName());
			System.out.println("Factorial Result is "  + result);
		}
	}
	
	public static void main(String []args) {
		MyRunnable myRunnable = new MyRunnable(5);
		Thread t = new Thread(myRunnable);
		Thread t1 = new Thread(new MyRunnable(6));
		Thread t2 = new Thread(new MyRunnable(8));
		t2.start();
		t1.start();
		t.start();
		//System.out.println(t.getState().);
		
	}

}
