package com.example.backend;

import java.io.*;

public class CallOrowan {
    private final String exeFilePath;
    private final String [] command;

    /**
     * CallOrowan constructor will instanciate by default the Orowan_x64.exe file and the commands for the console.
     */
    public CallOrowan() {
        this.exeFilePath = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/Orowan_x64.exe";
        String absPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\com.example.backend\\";
        this.command = new String[]{"d", "c", absPath + "output.txt", absPath + "outputOrowan.csv"};
    }

    /**
     * Executes the Orowan_x64.exe executable file and sends a series of commands to it.
     * The method uses a ProcessBuilder object to execute the file, and sends the commands
     * to the process's input stream using a BufferedWriter object.
     * The output of the process is read using an InputStreamReader object and printed to the console.
     */
    public void call(){
        ProcessBuilder pb = new ProcessBuilder(exeFilePath);

        try {
            Process p = pb.start();

            //Input
            InputStreamReader input = new InputStreamReader(p.getInputStream());
            BufferedInputStream br = new BufferedInputStream(p.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(br));

            //Outprint
            OutputStream output = p.getOutputStream();
            BufferedWriter b = new BufferedWriter(new OutputStreamWriter(output));

            //Entrer les commandes sur Orowan
            for (String s : command) {
                b.write(s);
                b.newLine();
                b.flush();
            }

            System.out.println("Executable finished with exit code ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
