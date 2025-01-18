package br.com.kmeans;

import java.io.*;
import java.util.Objects;

public class Main {

	public static void main(String[] args) {

		String argumentHelp = ArgumentGetter.getArgumentValue(args, "-help");
		if(argumentHelp != null){
			printHelp();
			return;
		}
		DataFetcher dt = new DataFetcher();
		System.out.println("Obtendo dataset do arquivo");
		double[][] dataset=null;
		try{
			String file = ArgumentGetter.getArgumentValue(args,"-file");
			dataset = dt.getDataset(file);

		}catch (FileNotFoundException e1){
			System.out.println("O arquivo contendo os dados não foi encontrado. " +
					"Insira o caminho do arquivo com a extensão .txt");
			System.out.println("Use a opção: -file<filename");
		}catch (IOException e2){
			System.out.println("Um erro ocorreu ao ler o arquivo.");
		}
		System.out.println("Fim de leitura do dataset");

		String argumentK = ArgumentGetter.getArgumentValue(args, "-k");
		int k = 0;
		if(argumentK != null){
			k = Integer.parseInt(Objects.requireNonNull(ArgumentGetter.getArgumentValue(args, "-k")));
		}else{
			System.out.println("é necessário informar o valor de k.");
			System.out.println("Use a opção: -k=<cluster_amount>");
			return;
		}

		KMeans kmeans = new KMeans(k, dataset);
		String argumentConcurrent = ArgumentGetter.getArgumentValue(args, "-concurrent");
		if(argumentConcurrent != null){
			int numThreads = Integer.parseInt(argumentConcurrent);
			kmeans.startConcurrent(numThreads);
		}else{
			kmeans.startSerial();
		}
		System.out.println("------------------------------");
		System.out.println("FIM DO ALGORÍTIMO");
		System.out.println("TEMPO: " + (kmeans.getElapsedTime() / 1000.0) + " segundos.");

		// kmeans.getDataSet().sort();
		 kmeans.getDataSet().print();

		kmeans.printSummary();
		// generateFileFloatArray(dataset);
		// System.out.println("Gerando arquivo de saída");
		// generateFile(kmeans.getDataSet(), "output/ot2 - serial.txt");
		// System.out.println("Arquivo gerado com sucesso");
		System.out.println("Imprimindo centroids");
		kmeans.printCentroids();

	}

	private static void generateFile(DataSet dataset, String fileName) {

		File file = new File(fileName);
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			for (int i = 0; i < dataset.getDatas().length; i++) {
				double[] valuesData = dataset.getDatas()[i].getValues();
				for (int j = 0; j < valuesData.length; j++) {
					writer.write(valuesData[j] + " ");

				}
				writer.write("c: " + dataset.getDatas()[i].getCentroid().getId());
				writer.newLine();
			}
			writer.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private static void printHelp(){
		System.out.println("* List of arguments *");
		System.out.println("-file=<filename>");
		System.out.println("-k=<cluster_amount>");
		System.out.println("[-concurrent=<threads_amount>]");
		System.out.println("[-help]");


	}

}
