package com.example.backend;

import java.io.*;

public class InputOrowan {

    private final String filePath;

    public InputOrowan(){
        this.filePath = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/output.csv";
    }

    public void csvToOrowan() throws IOException {
        // open the CSV file for reading
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        // open the TXT file for writing
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/output.txt"));

        // read the first line of the CSV file to skip the header row
        String line = reader.readLine();

        writer.write("Cas" + "\t" + "He" + "\t" + "Hs" + "\t" + "Te" + "\t" + "Ts" + "\t" + "Diam_WR" + "\t" +
                "WRyoung" + "\t" + "offset ini" + "\t" + "mu_ini" + "\t" + "Force" + "\t" + "G" + "\t");
        writer.newLine();

        int i = 0; int j = 1;

        // iterate over the remaining lines of the CSV file and extract the desired columns
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(";"); // assuming the CSV file uses commas as delimiter
            int i1 = i / 5;
            j += i1;
            i = i%5+1;
            String column1 = String.valueOf(j);
            String column2 = columns[4].replace(',', '.');
            String column3 = columns[5].replace(',', '.');
            String column4 = columns[6].replace(',', '.');
            String column5 = columns[7].replace(',', '.');
            String column6 = columns[10].replace(',', '.');
            String column7 = columns[12].replace(',', '.');
            String column8 = columns[17].replace(',', '.');
            String column9 = columns[15].replace(',', '.');
            String column10 = columns[8].replace(',', '.');
            String column11 = columns[9].replace(',', '.');

            // write the extracted columns to the TXT file
            writer.write(column1 + "\t" + column2 + "\t" + column3 + "\t" + column4 + "\t" + column5 + "\t" +
                    column6 + "\t" + column7 + "\t" + column8 + "\t" + column9 + "\t" + column10 + "\t" + column11 + "\t");
            writer.newLine();
        }

        // close the reader and writer
        reader.close();
        writer.close();
    }
}
