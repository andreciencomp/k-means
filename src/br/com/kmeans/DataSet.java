package br.com.kmeans;

import java.util.Arrays;

public class DataSet {
	
	private Data[] datas;
	private int numDatas;
	private final double[] sumValuesData;
	
	public DataSet(double[][] matrixValues) {
		numDatas = matrixValues.length;
		sumValuesData = new double[matrixValues[0].length];
        Arrays.fill(sumValuesData, 0f);
		
		datas = new Data[this.numDatas];
		for (int i = 0; i < numDatas;i++) {
			Data data = new Data();
			data.setValues(matrixValues[i]);
			this.datas[i] = data;
			double[] vData = data.getValues();
			
			for(int j = 0;j < data.getValues().length;j++) {
				sumValuesData[j] = sumValuesData[j] + vData[j];
			}
		}
	}

	public Data[] getDatas() {
		return datas;
	}
	
	public int getNumDatas() {
		return this.numDatas;
	}

	public void setDatas(Data[] data) {
		this.numDatas = data.length;
		this.datas = data;
	}

	public double[] getSumValuesData() {
		return sumValuesData;
	}
	
	public void print() {
		int index = 0;
		for(Data data: datas){
			System.out.print("i: " + index + " | ");
			index++;
			data.printData();
			System.out.println();
		}
	}
	
	public void sort() {
		for(int i=0;i < this.datas.length-1;i++) {
			for(int j=i+1;j < this.datas.length;j++) {
				
				if(datas[i].getCentroid().getId() > datas[j].getCentroid().getId()) {
					Data temp = datas[i];
					datas[i] = datas[j];
					datas[j] = temp;
				}	
			}
		}
	}
	
	public boolean checkDoubles() {
		for(int i=0;i<datas.length-1;i++) {
			for(int j=i+1;j<datas.length;j++) {
				if(datas[i].equals(datas[j])) {
					return true;
				}
			}
		}
		return false;
	}
}
