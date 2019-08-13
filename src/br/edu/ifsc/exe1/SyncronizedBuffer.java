package br.edu.ifsc.exe1;

public class SyncronizedBuffer implements Buffer {
	private int buffer = -1; // shared by producer and consumer threads

	// place value into buffer
	public synchronized void set(int value) {
		while (buffer != -1) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notifyAll();
		System.out.printf("Producer writes\t%2d", value);
		buffer = value;

	} // end method set

	// return value from buffer
	public synchronized int get() {
		while (buffer == -1) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int value = -1;
		System.out.printf("Consumer reads\t%2d", buffer);
		value = buffer;
		buffer = -1;
		notifyAll();
		return value;

	} // end method get
} // end class UnsynchronizedBuffer