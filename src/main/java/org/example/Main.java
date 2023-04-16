package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Get info from /data/lines.txt.
        File file = new File("/Users/hamishburke/Desktop/Uni/Year 2/COMP261/Assignment 2/COMPAssignment2/src/main/data/lines.txt");
        System.out.println("File exists: " + file.exists());
        System.out.println("File size: " + file.length());


    }
}

