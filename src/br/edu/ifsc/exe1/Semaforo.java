package br.edu.ifsc.exe1;

public class Semaforo {
	private int cont;
	
	public Semaforo(int cont) {
		this.cont=cont;
	}
	
	public synchronized void acquire() throws InterruptedException {
		while(cont <= 0) {
			wait();
		}
		cont--;
	}
	public synchronized void release() {
		cont++;
		notifyAll();
	}
}
