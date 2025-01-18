package br.com.kmeans;

import java.util.Arrays;

public  class Centroid {

	private int id;
	private double[] values;
	private final Object lockA = new Object();
	private final Object lockB = new Object();
	private double[] valuesTemp;//Usado para o cálculo da posição do centroide
	private int numDatas; //Usado para o cálculo da posição do centroide
	
	public  Centroid(int id, double[] values) {
		this.id = id;
		this.values = values;
		this.valuesTemp = new double[values.length];
		this.numDatas = 0;
        Arrays.fill(valuesTemp, 0);
	
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}
	
	public synchronized int getNumDatas() {
		return this.numDatas;
	}
	
	public double[] getValuesTemps(){
		return valuesTemp;
	}
	
	public void resetValuesTemp() {
		numDatas = 0;
		Arrays.fill(valuesTemp, 0);
	}

	//Usado para atualizar a nova posição do centroid
	public void incrementValuesTemp(double[] newValues) {
		synchronized(lockB) {
			for(int i = 0; i < valuesTemp.length; i++) {
				valuesTemp[i] = valuesTemp[i] + newValues[i];
			}
		}
	}
	
	//Usado para atualizar a nova posição do centroid
	public void incrementNumDatas() {
		synchronized(lockA) {
			numDatas = numDatas + 1;
		}
	}
	
	//Atualiza a nova posição do centroid. Retorna true se a posição é alterada
	public boolean updateNewPosition() {
		boolean valueChanged = false;
		double newPos;
		for(int i=0; i < values.length; i++) {
			newPos = valuesTemp[i]/(double)numDatas;
			if(newPos != values[i]) {
				valueChanged = true;
				values[i] = newPos;
			}
		}

		return valueChanged;
	}

	public void print() {
		System.out.print("[classe: " + id + " ");
		for(int i=0;i<values.length;i++) {
			if(i==0) {
				System.out.print("(");
			}
			System.out.print(values[i] + " ");
			if(i==values.length-1) {
				System.out.print(")");
			}
		}
		System.out.print("]");

	}

}
