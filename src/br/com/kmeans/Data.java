package br.com.kmeans;

public class Data {

	private double[] values;
	
	private Centroid centroid;

	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public Centroid getCentroid() {
		return centroid;
	}

	public void setCentroid(Centroid centroid) {
		
		this.centroid = centroid;
	}
	
	public void printData() {
        for (double value : values) {
            System.out.print(value + " ");
        }
		if(centroid != null) {
			centroid.print();
		}
	}
	@Override
	public boolean equals(Object other) {
		for(int i=0;i<values.length;i++) {
			if(this.values[i] != ((Data)other).getValues()[i]) {
				return false;
			}
		}
		return true;
	}
}
