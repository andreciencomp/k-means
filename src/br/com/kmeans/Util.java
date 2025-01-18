package br.com.kmeans;

public class Util {

	public  static double calculateDistance(double[] valueA, double[] valueB) {
		double sum = 0;
		for(int i=0;i < valueA.length;i++) {
			sum+= Math.pow((valueA[i] - valueB[i]),2);
		}
		return Math.sqrt(sum);
		
	}
}
