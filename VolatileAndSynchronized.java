package multithreading;

public class VolatileAndSynchronized {
	
	public static class MyRunnable implements Runnable
	{
		
		volatile int counter;
		//int counter;

		@Override
		public void run() {
			synchronized (this) {
				for(int i=1;i<=10;i++) {
					this.counter++;
				}
				
			}
		}
		
	}
	
	public static void main(String []args) {
		MyRunnable v = new MyRunnable();
		Thread t = new Thread(v);
		Thread t1 = new Thread(v);
		Thread t2 = new Thread(v);
		Thread t3 = new Thread(v);
		t.start();
		t1.start();
		t2.start();
		t3.start();
		
		System.out.println(v.counter);
		
	}

}
