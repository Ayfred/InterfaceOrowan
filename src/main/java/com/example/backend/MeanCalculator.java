package com.example.backend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MeanCalculator {
    private final String csvFile;
    private final String csvDelimiter;
    private final String outputFileName;
    private static MeanCalculator mc = null;

    public MeanCalculator() {
        this.csvFile = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/outputOrowan.csv";
        this.csvDelimiter = "\t";
        this.outputFileName = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/meanData.csv";
    }

    public static MeanCalculator getInstance() {
        if (mc == null) {
            mc = new MeanCalculator();
        }
        return mc;
    }

    public void computeMean() {
        File file = new File(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/meanData.csv");
        if (file.exists()) {
            file.delete();
        }
        BufferedReader br = null;
        String line = "";
        String header = "";
        String cvsSplitBy = csvDelimiter;
        List<double[]> data = new ArrayList<double[]>();
        int rowCount = 0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            header = br.readLine();
            String[] rowheader = header.split(cvsSplitBy);
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                double[] rowData = new double[row.length-2];
                int count = 0;

                for (int i = 0; i < row.length-2; i++) {
                    if("Errors".equals(rowheader[i])) count++;
                    if (!"Errors".equals(rowheader[i]) && !"Has_Converged".equals(rowheader[i])) {
                        rowData[i] = Double.parseDouble(row[i+count]);
                    }
                }

                data.add(rowData);
                //System.out.println(data.get(0).length);
                //System.out.println(data.get(0).length);
                rowCount++;

                if (rowCount % 5 == 0) {
                    this.computeMean(data, outputFileName);
                    data.clear();
                }
            }
            // Calculer les moyennes pour la dernière partie des données
            if (!data.isEmpty()) {
                this.computeMean(data, outputFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void computeMean(List<double[]> data, String outputFileName) {
        int nbCols = data.get(0).length;
        //System.out.printf("nb Cols : %s %n", nbCols);
        double[] moyennes = new double[nbCols];


        for (int i = 0; i < nbCols; i++) {
            double somme = 0.0;
            for (double[] datum : data) {
                somme += datum[i];
            }
            moyennes[i] = somme / data.size();
        }

        // Enregistrer les moyennes dans un fichier CSV
        try {
            FileWriter writer = new FileWriter(outputFileName, true);
            for (double moyenne : moyennes) {
                writer.append(String.valueOf(moyenne)).append(",");
            }
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
