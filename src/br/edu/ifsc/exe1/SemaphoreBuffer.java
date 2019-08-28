package br.edu.ifsc.exe1;

import java.util.concurrent.Semaphore;

public class SemaphoreBuffer implements Buffer{
	private int buffer = -1;
	private Semaphore sem = new Semaphore(1);
	private Semaphore cond = new Semaphore(0);
	@Override
	public void set(int value) {
		try {
			sem.acquire();

			while (buffer != -1) {
				sem.release();
				cond.acquire();
				sem.acquire();
			}

			System.out.printf("Producer writes\t%2d", value);
			buffer = value;
			cond.release();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sem.release();
		}
	}

	@Override
	public int get() {
		int value = -1;
		try {
			sem.acquire();
			while (buffer == -1) {
				sem.release();
				cond.acquire();
				sem.acquire();
			}
			value = buffer;
			buffer = -1;
			System.out.printf("Consumer reads\t%2d", value);
			cond.release();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sem.release();
		}
		return value;
	}

}
