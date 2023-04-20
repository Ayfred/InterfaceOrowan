package com.example.backend;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Metronome {
    public static InputOrowan inputOrowan;
    public static ImportDataset importDataset;
    public static DatabaseManager manager = null;
    public static MeanCalculator mc = null;

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {

        System.out.println(new File("").getAbsolutePath());

        //Create a csv file from the existing text file and saves it to the database
        importDataset = new ImportDataset("1939351_F2");
        importDataset.convertTxt2Csv();

        //Create a csv file containing only the information Orowan needs
        inputOrowan = new InputOrowan();
        inputOrowan.csvToOrowan();

        //Calls Orowan calculator
        CallOrowan cal = new CallOrowan();
        cal.call();

        Thread.sleep(1000);//sinon bug avec MeanCalculator

        //Calcul de la moyenne
        mc = MeanCalculator.getInstance();
        mc.computeMean();


        //Database Manager
        manager = DatabaseManager.getInstance();
        manager.openDBConnection();

        DatabaseManager.deleteTable("INITIAL_VALUES");
        //Check for existing table in database
        if(DatabaseManager.checkTable(manager.getDbConnection(), "INITIAL_VALUES")){

            System.out.println("Table déjà existante");
            DatabaseManager.addInitialData();
        }
        else{
            //Create the table otherwise
            System.out.println("Table non existante");
            DatabaseManager.addInitialTable();
            DatabaseManager.addInitialData();
        }

        DatabaseManager.deleteTable("OROWAN_OUTPUT");
        //Check for existing table in database
        if(DatabaseManager.checkTable(manager.getDbConnection(), "OROWAN_OUTPUT")){

            System.out.println("Table déjà existante");

            DatabaseManager.addOutputOrowanData();
        }
        else{
            //Create the table otherwise
            System.out.println("Table non existante");
            DatabaseManager.addOutputOrowanTable();
            DatabaseManager.addOutputOrowanData();
        }

        DatabaseManager.deleteTable("MEAN_OROWAN");
        //Check for existing table in database
        if(DatabaseManager.checkTable(manager.getDbConnection(), "MEAN_OROWAN")){

            System.out.println("Table déjà existante");
            DatabaseManager.addMeanData();
        }
        else{
            //Create the table otherwise
            System.out.println("Table non existante");
            DatabaseManager.addMeanTable();
            DatabaseManager.addMeanData();
        }

    }
}
