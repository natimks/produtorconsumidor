package br.edu.ifsc.exe1;

import java.util.concurrent.ArrayBlockingQueue;

public class ABQBuffer implements Buffer{
	private ArrayBlockingQueue<Integer> queue;
	
	public ABQBuffer(int capacidade) {
		queue  = new ArrayBlockingQueue<Integer>(capacidade);
	}
	@Override
	public void set(int value) {
		try {
			System.out.printf("Producer writes\t%2d", value);
			queue.put(value);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int get() {
		int value=-1;
		try {
			value= queue.take();
			System.out.printf("Consumer reads\t%2d", value);
			return value;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

}
