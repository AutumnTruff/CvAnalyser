package org.example;
import java.util.Scanner;

public class ApplicationScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner(){
        return scanner;
    }
}