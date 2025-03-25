package org.example;

import java.util.Scanner;

public class userInputScanner {
    private static final Scanner scanner = new Scanner(System.in);


    public static void getInput(){
        scanner.nextLine();
    }

    public static int getInt(){
        int counter = 0;

        int input = 0;
        do {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine();
                break;

            } else {
                System.out.println("Invalid input, try again");
                counter++;
                scanner.next();
            }

        } while (counter < 4);
        if(counter == 4){
            System.out.println("Too many invalid inputs, Returning...");
        }
        return input;
    }

    public static float getFloat(){
        return(scanner.nextFloat());
    }

    public static boolean getBoolean(){
        return(scanner.nextBoolean());
    }
}
