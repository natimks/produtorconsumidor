package br.edu.ifsc.exe1;

import java.util.ArrayDeque;

public class DoubleBuffer implements Buffer {
	private volatile ArrayDeque <Integer> readBuffer, writeBuffer;
	private int tamanho;
	public DoubleBuffer(int tamanho) {
		this.tamanho = tamanho;
		readBuffer = new ArrayDeque<Integer>();
		writeBuffer = new ArrayDeque<Integer>();
	}
	@Override
	public void set(int value) {
		System.out.printf("Producer writes\t%2d", value);
		writeBuffer.add(value);
		if( writeBuffer.size() == tamanho) {
			while(!readBuffer.isEmpty());
			ArrayDeque<Integer> temp = writeBuffer;
			writeBuffer = readBuffer;
			readBuffer=temp;
		}
	}

	@Override
	public int get() {
		int value = -1;
		while(readBuffer.isEmpty());
		value = readBuffer.remove();
		System.out.printf("Consumer reads\t%2d", value);
		
		return value;
	}
	

}
