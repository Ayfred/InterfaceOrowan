package com.example.backend;

import java.io.*;

public class ImportDataset {
    private final String filePath;
    private final File headerData;
    private final String fileName;

    /**
     * Constructor
     * @param fileName import the txt file according to the name of file
     */
    public ImportDataset(String fileName ) {
        this.fileName = fileName;
        this.filePath = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/" + fileName + ".txt";
        File rawData = new File(filePath);
        this.headerData = new File(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/Columns_header.txt");

        convertTxt2Csv();
        File rawDataCsv = new File(new File("").getAbsolutePath() + "/src/main/resources/"+ fileName +".csv");
        File headerDataCsv = new File(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/Columns_header.csv");
        File outputDataCsv = new File(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/output.csv");

    }

    /**
     * Method that call the convertTxt2Csv method and load the csv files
     */
    public void convertTxt2Csv(){
        convertTxt2Csv(this.headerData, new File(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/Columns_header.csv"), "\t", "\t");
        convertTxt2Csv(new File(filePath), new File(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/"+ fileName +".csv"), ";", "/");
        convertTxt2Csv(new File(filePath), new File(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/output.csv"), "\t", ",");
    }

    /**
     * Converts a text file to a CSV file with a specified delimiter.
     * @param inputFile the text file to be converted
     * @param outputFile  the CSV file to be created
     * @param delimiter the delimiter used in the input text file
     * @param newDelimiter the new delimiter to be used in the output CSV file
     */
    private void convertTxt2Csv(File inputFile, File outputFile, String delimiter, String newDelimiter) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(outputFile);

            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(delimiter);
                for (int i = 0; i < values.length; i++) {
                    writer.append(values[i]);
                    if (i != values.length - 1) {
                        writer.append(newDelimiter);
                    }
                }
                writer.append("\n");
                line = reader.readLine();
            }

            reader.close();
            writer.close();
            System.out.println("Conversion complete!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


