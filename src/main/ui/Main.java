package ui;


import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new ThriftStoreApp();
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, file not found");
        }
    }
}


