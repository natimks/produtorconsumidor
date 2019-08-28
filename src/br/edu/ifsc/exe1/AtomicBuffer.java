package br.edu.ifsc.exe1;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicBuffer implements Buffer {
	private int[] buffer;

	int posGet = 0;
	int posSet = 0; 
	private AtomicInteger tamanho;

	public AtomicBuffer(int max) {
		tamanho = new AtomicInteger(0);
		buffer = new int[max];
	}

	
	public  void set(int value) {
		while(buffer.length == tamanho.addAndGet(0));
		buffer[posSet] = value;
		System.out.printf("Producer writes\t%2d", value);
		tamanho.incrementAndGet();
		posSet = (posSet + 1) % buffer.length;
	}

	
	public  int get() {
		while (tamanho.addAndGet(0) == 0);
		tamanho.decrementAndGet();
		int value = -1;
		System.out.printf("Consumer reads\t%2d", buffer[posGet]);
		value = buffer[posGet];
		posGet = (posGet + 1) % buffer.length;

		return value;
	} 
}
