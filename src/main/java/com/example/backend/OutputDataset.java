package com.example.backend;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class OutputDataset {

    //private static final String name_file = "outputOrowan";
    //public static File file = new File("resource/" + name_file + ".csv");
    private static DatabaseManager manager; //Penser à créer une instance de DatabaseManager qlq part

    public boolean callAddToDB(File file, String name) throws SQLException, IOException {
        manager.add(file, name);
        return file.delete();
    }

}
