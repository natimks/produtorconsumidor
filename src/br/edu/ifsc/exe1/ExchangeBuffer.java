package br.edu.ifsc.exe1;

import java.util.ArrayDeque;
import java.util.concurrent.Exchanger;

public class ExchangeBuffer implements Buffer {
	private ArrayDeque <Integer> readBuffer, writeBuffer;
	private Exchanger<ArrayDeque<Integer>> exchanger;
	private int tamanho;
	public ExchangeBuffer(int tamanho) {
		this.tamanho = tamanho;
		readBuffer = new ArrayDeque<Integer>();
		writeBuffer = new ArrayDeque<Integer>();
		exchanger = new Exchanger<ArrayDeque<Integer>>();
	}
	@Override
	public void set(int value)  {
		System.out.printf("Producer writes\t%2d", value);
		writeBuffer.add(value);
		if( writeBuffer.size() == tamanho) {
			try {
				writeBuffer = exchanger.exchange(writeBuffer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int get() {
		int value = -1;	
		if(readBuffer.isEmpty()) {
			try {
				readBuffer = exchanger.exchange(readBuffer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		value = readBuffer.remove();
		System.out.printf("Consumer reads\t%2d", value);	
		
		return value;
	}
	
}
