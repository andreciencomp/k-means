package br.com.kmeans;

public class ThreadClassCalculator extends Thread {

	private KMeans kmeans;
	private int indexInf;
	private int indexSup;

	public ThreadClassCalculator(KMeans kmeans, int indexInf, int indexSup) {
		this.kmeans = kmeans;
		this.indexInf = indexInf;
		this.indexSup = indexSup;
	}

	@Override
	public void run() {
		kmeans.calculateClass(indexInf, indexSup);
	}

}
