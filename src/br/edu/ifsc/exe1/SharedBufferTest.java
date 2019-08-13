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
		Buffer sharedLocation = new LockBuffer();

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
