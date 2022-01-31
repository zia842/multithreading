package multithreading;

class MyObject{
	
}

public class ExtendingThreadClass{
	
	

	public static class MyThread extends Thread {
		int n;
		int output;

		public MyThread(int n){
			this.n = n;
		}

		public void run() {
			int result = 1;
			for(int i=1;i<=this.n;i++) {
				result *= i;
			}
			MyObject m = new MyObject();
			System.out.println("Thread Name is " + Thread.currentThread().getName());
			System.out.println("Factorial Result is "  + result);
			this.output = result;
			System.out.println(m.toString());
		}

	}


	public static void main(String []args) {
		MyThread t = new MyThread(5);
		t.start();
		MyThread t1 = new MyThread(10);
		t1.start();
		try 
		{
			t.join();
			t1.join(); //Use Join if u want thread to complete processing 
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("End " + t.output);
		System.out.println("End " + t1.output);
	}

}
