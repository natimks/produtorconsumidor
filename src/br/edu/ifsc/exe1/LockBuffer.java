package br.edu.ifsc.exe1;

import java.util.concurrent.locks.ReentrantLock;

public class LockBuffer implements Buffer {
	private int buffer = -1; // shared by producer and consumer threads
	private ReentrantLock mutex = new ReentrantLock();

	// place value into buffer
	public synchronized void set(int value) {
		try {

			mutex.lock();

			while (buffer != -1) {
				get();
			}

			System.out.printf("Producer writes\t%2d", value);
			buffer = value;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
	} // end method set

	// return value from buffer
	public synchronized int get() {
		int value = -1;
		try {
			mutex.lock();
			while (buffer == -1) {
				set(1);
			}

			System.out.printf("Consumer reads\t%2d", buffer);
			value = buffer;
			buffer = -1;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
		return value;
	} // end method get
} // end class UnsynchronizedBuffer