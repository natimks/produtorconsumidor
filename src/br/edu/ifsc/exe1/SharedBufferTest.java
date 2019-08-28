package br.edu.ifsc.exe1;

// Fig 23.10: SharedBufferTest.java
// Application shows two threads manipulating an unsynchronized buffer.
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharedBufferTest {
	public static void main(String[] args) {
		// create new thread pool with two threads
		ExecutorService application = Executors.newFixedThreadPool(2);

		
		// create UnsynchronizedBuffer to store ints
		//Buffer sharedLocation = new SemaforoBuffer();
		//Buffer sharedLocation = new ConditionBuffer();
		//Buffer sharedLocation = new ConditionCircularBuffer(3);
		//Buffer sharedLocation = new LockBuffer();
		//Buffer sharedLocation = new LockCircularBuffer(3);
		//Buffer sharedLocation = new SemaphoreBuffer();
		//Buffer sharedLocation = new SynchronizesCircularBuffer(3);
		//Buffer sharedLocation = new SyncronizedBuffer();
		//Buffer sharedLocation = new UnsynchronizedBuffer();
		
		//Buffer sharedLocation = new SemaphoreCircularBuffer(3);
		// create UnsynchronizedBuffer to store ints
		//Buffer sharedLocation = new ABQBuffer(3);
		//Buffer sharedLocation = new DoubleBuffer(2);
		Buffer sharedLocation = new ExchangeBuffer(2);
		
		System.out.println("Action\t\tValue\tProduced\tConsumed");
		System.out.println("------\t\t-----\t--------\t--------\n");

		// try to start producer and consumer giving each of them access
		// to sharedLocation
		try {
			application.execute(new Producer(sharedLocation));
			application.execute(new Consumer(sharedLocation));
		} // end try
		catch (Exception exception) {
			exception.printStackTrace();
		} // end catch

		application.shutdown(); // terminate application when threads end
	} // end main
} // end class SharedBufferTest
