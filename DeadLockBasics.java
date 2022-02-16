package multithreading;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * jvisualvm, jstack we can download thread dump process id
 *   where it can show deadlock in the stack trace
 *   
 *   To prevent deadlocks use timeouts in tryLock
 *   Be cautious about ordering
 *   Global ordering of locks can be tricky
 *   
 *   Deadlock occurs when a thread is waiting for a lock held by other thread and vice versa
 *   Difficult to detect due to multiple lock types and thread sources
 *   Detect at runtime using thread dumps
 *   Consistent ordering of lock acquistion helps avoid deadlock
 *   Using timeouts for deadlocks can also help 
 * 
 * @author zia84
 *
 */
public class DeadLockBasics {
	
	private Lock lockA = new ReentrantLock();
	private Lock lockB = new ReentrantLock();
	
	public void execute() {
		for(int i=1;i<=10;i++) {
			new Thread(this::processThis).start();
			new Thread(this::processThat).start();
		}
		
	}
	
	public void processThis() {
		lockA.lock();
		lockB.lock();
		
		System.out.println("processThis -> Accquired Both the Locks "+ Thread.currentThread().getName());
		
		lockA.unlock();
		lockB.unlock();
	}
	
	public void processThat() {
		lockB.lock();
		lockA.lock();
		
		System.out.println("processThat -> Accquired Both the Locks "+ Thread.currentThread().getName());
		
		lockA.unlock();
		lockB.unlock();
	}
	
	public static void main(String []args) {
		DeadLockBasics d = new DeadLockBasics();
		d.detectLocks();
		d.execute();
	}
	
	public void detectLocks() {
		new Thread(this::deadLockDetection).start();
	}
	
	private void deadLockDetection() {
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		long threadIDs[] = threadBean.findDeadlockedThreads();
		boolean deadLock = threadIDs!=null && threadIDs.length > 0;
		System.out.println("Dead Lock Found " + deadLock);
	}

}
