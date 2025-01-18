package br.com.kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataFetcher {

    public double[][] getDataset(String filename) throws FileNotFoundException, IOException {
        File file = new File(filename);
        System.out.println("-----------");
        System.out.println(file.getAbsolutePath());
        FileInputStream fs;
        int numDatas;
        int numAtributes;

        fs = new FileInputStream(file);
        InputStreamReader sr = new InputStreamReader(fs);
        BufferedReader reader = new BufferedReader(sr);
        String line;
        String[] headers = null;
        headers = reader.readLine().split("\\s");
        numDatas = Integer.parseInt(headers[0]);
        numAtributes = Integer.parseInt(headers[1]);
        double[][] dataset = new double[numDatas][numAtributes];
        int i = 0;
        while ((line = reader.readLine()) != null) {
            String[] dataLine = line.split("\\s");
            for (int j = 0; j < numAtributes; j++) {
                dataset[i][j] = Double.parseDouble(dataLine[j]);
            }
            i++;
        }
        reader.close();
        return dataset;


    }

}
