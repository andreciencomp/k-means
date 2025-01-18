package br.com.kmeans;

public class ThreadClassCalculator extends Thread {

	private final KMeans kmeans;
	private final int indexInf;
	private final int indexSup;

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
