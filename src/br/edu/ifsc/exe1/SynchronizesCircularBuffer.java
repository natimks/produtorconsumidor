package br.edu.ifsc.exe1;

public class SynchronizesCircularBuffer implements Buffer {
	private int[] buffer;
	int currentValue = 0;

	int posicoesUsadas = 0;
	int posicaoRemocao = 0; // posGet
	int posicaoInsercao = 0; // posSet

	public SynchronizesCircularBuffer(int max) {
		buffer = new int[max];
	}

	// place value into buffer
	public synchronized void set(int value) {
		while (posicoesUsadas == buffer.length) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer[posicaoInsercao] = value;
		System.out.printf("Producer writes\t%2d", value);
		posicoesUsadas++;
		posicaoInsercao = (posicaoInsercao + 1) % buffer.length;
		notifyAll();
	} // end method set

	// return value from buffer
	public synchronized int get() {
		while (posicoesUsadas == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		posicoesUsadas--;

		int value = -1;
		System.out.printf("Consumer reads\t%2d", buffer[posicaoRemocao]);
		value = buffer[posicaoRemocao];
		posicaoRemocao = (posicaoRemocao + 1) % buffer.length;

		notifyAll();
		return value;
	} // end method get
} // end class UnsynchronizedBuffer