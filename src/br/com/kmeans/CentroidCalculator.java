package br.com.kmeans;

public class CentroidCalculator {

	//Calcula a posição do centroid e retorna true se há mudança em algum centroid
	public boolean calculateCentroid(Centroid centroid, DataSet dataSet) {

		boolean centroidChanged = false;
		int numCentroids = 0;
		Data[] datas = dataSet.getDatas();
		double[] sumArray = new double[datas[0].getValues().length];
		for(Data data:datas) {
			if(centroid == data.getCentroid()) {
				numCentroids++;
				for(int i=0;i<sumArray.length;i++) {
					sumArray[i]+= data.getValues()[i];
				}
			}
		}
		if(numCentroids == 0) {
			System.out.println("centroid "+ centroid.getId() + " Sem dados");
			//return false;
			
		}else {
			
			for(int i=0;i<sumArray.length;i++) {
				sumArray[i] = sumArray[i]/numCentroids;
				if(sumArray[i]!= centroid.getValues()[i]) {
					
					centroidChanged = true;
				}
				
			}
			centroid.setValues(sumArray);
		}

		return centroidChanged;
	}
}
