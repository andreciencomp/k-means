package br.com.kmeans;

import java.util.Arrays;

public class KMeans {
	private DataSet dataSet;
	private Centroid[] centroids;
	private int[] objectsPerClass;
	private long timeStart;
	private long timeFinished;
	private long elapsedTime;
	private double[] sumValuesData;
	private final int numCentroids;
	private KmeansExecutionMode executionMode;

	// Este construtor gera k centroides
	public KMeans(int k, double[][] valuesDataSet) {
		numCentroids = k;
		this.centroids = new Centroid[k];
		this.objectsPerClass = new int[k];
		for (int i = 0; i < k; i++) {
			Centroid centroid = new Centroid(i + 1, valuesDataSet[i].clone());
			centroids[i] = centroid;
		}
		this.dataSet = new DataSet(valuesDataSet);
		this.sumValuesData = dataSet.getSumValuesData();
	}

	public DataSet getDataSet() {
		return this.dataSet;
	}

	public void printDataSet() {

		this.dataSet.print();
	}

	public void printCentroids() {
		for (Centroid c : centroids) {
			c.print();
		}
	}

	public void printSummary() {
		double totalPercent = 0;
		int totalDatas = 0;
		for (int i = 0; i < centroids.length; i++) {
			double percent = (100.0 * ((double) objectsPerClass[i])) / ((double) this.dataSet.getDataAmount());
			totalPercent += percent;
			totalDatas += objectsPerClass[i];
			System.out.println("Classe " + (i + 1) + ": " + objectsPerClass[i] + " dados - " + percent + "%");
		}
		System.out.printf("Quantidade de dados: %d %.3f%% \n",totalDatas, totalPercent);
	}

	private void resetObjectsPerClass() {
        Arrays.fill(this.objectsPerClass, 0);
	}

	public void startSerial() {
		executionMode = KmeansExecutionMode.SERIAL;
		timeStart = System.currentTimeMillis();
		System.out.println("*MODO SERIAL INICIADO*");
		int numberOfIterations = 0;
		boolean centroidChanged = true;
		while (centroidChanged) {
			resetObjectsPerClass();
			numberOfIterations++;
			System.out.print("Iteração " + numberOfIterations  + ": ");

			calculateClass(0, dataSet.getDataAmount() - 1);

			centroidChanged = false;
			int centroidsModificados = 0;
			for (int i = 0; i < this.centroids.length; i++) {
				boolean changed = centroids[i].updateNewPosition();
				objectsPerClass[i] = centroids[i].getNumDatas();
				if (changed) {
					centroidChanged = true;
					centroidsModificados++;
				}
			}
			System.out.println(centroidsModificados + " centroids modificados");

			for (Centroid c : centroids) {
				c.resetValuesTemp();
			}

		}
		System.out.println();
		timeFinished = System.currentTimeMillis();
		elapsedTime = timeFinished - timeStart;
	}

	public Centroid[] getCentroids() {
		return this.centroids;
	}

	public void startConcurrent(int numberOfThreads) {
		executionMode = KmeansExecutionMode.CONCURRENT;
		int numberOfInteractions = 0;
		System.out.println("--- MODO CONCORRENTE INICIADO---");
		timeStart = System.currentTimeMillis();
		int dataPerThread = (int) Math.floor((float) dataSet.getDataAmount() / (float) numberOfThreads);
		boolean centroidChanged = true;
		ThreadClassCalculator[] classifierThreads = new ThreadClassCalculator[numberOfThreads];
		while (centroidChanged) {
			numberOfInteractions++;
			System.out.print("Iteração " + numberOfInteractions);
			resetObjectsPerClass();
			int idxInf = 0;
			int idxSup = 0;
			for (int i = 0; i < numberOfThreads; i++) {
				if (i == numberOfThreads - 1) {
					idxSup = dataSet.getDataAmount() - 1;
				} else {
					idxSup = idxInf + dataPerThread - 1;
				}
				ThreadClassCalculator classifier = new ThreadClassCalculator(this, idxInf, idxSup);
				classifierThreads[i] = classifier;
				idxInf = idxSup + 1;
			}

            for (ThreadClassCalculator classifierThread : classifierThreads) {
                classifierThread.start();
			}
            for (ThreadClassCalculator classifierThread : classifierThreads) {
                try {
                    classifierThread.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

			centroidChanged = false;
			int centroidsModificados = 0;
			for (int i = 0; i < this.centroids.length; i++) {

				boolean changed = centroids[i].updateNewPosition();
				objectsPerClass[i] = centroids[i].getNumDatas();

				if (changed) {
					centroidChanged = true;
					centroidsModificados++;
				}

			}
			System.out.printf(" %d centroids modificados\n", centroidsModificados);
			for (Centroid c : this.centroids) {
				c.resetValuesTemp();
			}
		}
		timeFinished = System.currentTimeMillis();
		elapsedTime = timeFinished - timeStart;
	}

	public void calculateClass(int indexInf, int indexSup) {

		for (int i = indexInf; i <= indexSup; i++) {
			int idx = 0;
			Data data = dataSet.getDatas()[i];
			double minDistance = Util.calculateDistance(data.getValues(), centroids[0].getValues());
			for (int j = 1; j < numCentroids; j++) {
				double distance = Util.calculateDistance(data.getValues(), centroids[j].getValues());
				if (distance < minDistance) {
					minDistance = distance;
					idx = j;
				}
			}
			centroids[idx].incrementNumDatas();
			centroids[idx].incrementValuesTemp(data.getValues());
			data.setCentroid(centroids[idx]);
		}
	}

	public long getElapsedTime(){
		return elapsedTime;
	}
}
