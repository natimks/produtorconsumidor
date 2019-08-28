package br.edu.ifsc.exe1;


public class SemaphoreCircularBuffer implements Buffer {
	private int[] buffer;
	int currentValue = 0;

	int posicoesUsadas = 0;
	int posicaoRemocao = 0; // posGet
	int posicaoInsercao = 0; // posSet
	
	private Semaforo sem;

	public SemaphoreCircularBuffer(int max) {
		buffer = new int[max];
		sem = new Semaforo(buffer.length);
	}

	// place value into buffer
	public void set(int value) {
		try {
			sem.acquire();
			while (posicoesUsadas == buffer.length) {
				sem.release();
				sem.acquire();
			}
			buffer[posicaoInsercao] = value;
			System.out.printf("Producer writes\t%2d", value);
			posicoesUsadas++;
			posicaoInsercao = (posicaoInsercao + 1) % buffer.length;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sem.release();
		}
	} // end method set

	// return value from buffer
	public int get() {
		int value = -1;
		try {
			sem.acquire();;
			while (posicoesUsadas == 0) {
				sem.release();
				sem.acquire();
			}
			

			posicoesUsadas--;

			System.out.printf("Consumer reads\t%2d", buffer[posicaoRemocao]);
			value = buffer[posicaoRemocao];
			posicaoRemocao = (posicaoRemocao + 1) % buffer.length;

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sem.release();
		}
		return value;
	} // end method get
}
