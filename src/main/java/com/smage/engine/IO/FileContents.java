package com.smage.engine.IO;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileContents {
    public static final int notFound = -1234234645;
    HashMap<String, String> fileEntries = new HashMap<>();
    public File filePassed = null;

    public FileContents(File file) {
        this.filePassed = file;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains(":")) {
                    String[] string = line.split(":");
                    fileEntries.put(string[0], string[1]);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("The file " + file.getName() + " was not found in location " + file.getAbsolutePath());
        }
    }

    public FileContents(InputStreamReader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            String temp;
            temp = br.readLine();
            while (temp != null) {
                if(temp.contains(":")) {
                    String[] string = temp.split(":");
                    fileEntries.put(string[0], string[1]);
                }
                temp = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String name, boolean spaces) {
        if(spaces) {
            return fileEntries.get(name);
        } else {
            return fileEntries.get(name).replaceAll(" ", "");
        }
    }

    public int getInt(String name) {
        try {
            return Integer.parseInt(fileEntries.get(name).replaceAll(" ", ""));
        } catch(NumberFormatException | NullPointerException e){
            e.printStackTrace();
        }
        return notFound;
    }

    public boolean getBoolean(String name) {
        String booleanAsString = getString(name, false);

        if(booleanAsString == null)
            return false;
        if(booleanAsString.isEmpty())
            return false;

        switch(booleanAsString.toLowerCase()){
            case "true":
            case "t":
            case "tru":
                return true;
            case "false":
            case "null":
            case "f":
                return false;
        }
        return false;
    }
}
