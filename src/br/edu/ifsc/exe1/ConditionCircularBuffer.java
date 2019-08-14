package br.edu.ifsc.exe1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionCircularBuffer implements Buffer {
	private ReentrantLock mutex = new ReentrantLock();
	private Condition podeLer = mutex.newCondition();
	private Condition podeEscrever = mutex.newCondition();
	private int[] buffer;
	int currentValue = 0;

	int posicoesUsadas = 0;
	int posicaoRemocao = 0; // posGet
	int posicaoInsercao = 0; // posSet

	public ConditionCircularBuffer(int max) {
		buffer = new int[max];
	}

	// place value into buffer
	public void set(int value) {
		try {
			mutex.lock();
			while (posicoesUsadas == buffer.length) {
				podeEscrever.await();
			}
			buffer[posicaoInsercao] = value;
			System.out.printf("Producer writes\t%2d", value);
			posicoesUsadas++;
			posicaoInsercao = (posicaoInsercao + 1) % buffer.length;
			podeLer.signal();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
	} // end method set

	// return value from buffer
	public int get() {
		int value = -1;
		try {
			mutex.lock();
			while (posicoesUsadas == 0) {
				podeLer.await();
			}
			posicoesUsadas--;
			System.out.printf("Consumer reads\t%2d", buffer[posicaoRemocao]);
			value = buffer[posicaoRemocao];
			posicaoRemocao = (posicaoRemocao + 1) % buffer.length;
			podeEscrever.signal();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
		return value;
	} // end method get
} // end class UnsynchronizedBuffer
