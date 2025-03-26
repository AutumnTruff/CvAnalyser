package org.example;

import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

public class Recruiter extends User {

    public Recruiter(String name, int userID, String email, String hashedPassword, String userType) {
        super(name, userID, email, hashedPassword, userType);
    }


    public static void recruiterMainMenu() {
        boolean running = true;

        while (running) {
            StartupMenu.clearScreen();
            printMenuOptions();

            try {
                int choice = userInputScanner.getInt();
                running = handleMenuChoice(choice);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                userInputScanner.getInt();

            }

            pauseForUser();
        }
    }

    public static void printMenuOptions() {
        System.out.println("Recruiter Homepage:");
        System.out.println("1. Modify Jobs");
        System.out.println("2. View Candidates");
        System.out.println("3. Exit program");
    }

    private static boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Selected: Modify Jobs");
                JobPosting.RecruiterHelperMenu();
                break;
            case 2:
                System.out.println("Selected: View Candidates");
                viewCandidates();
                break;
            case 3:
                exitingProgram();
                return false;
            default:
                System.out.println("Invalid option. Please choose again.");
                break;
        }
        return true;
    }

    public static void exitingProgram() {
        System.out.println("Selected: Exit");
        System.out.println("See you soon!");
        ApplicationScanner.getScanner().close();
        System.exit(0);
    }

    public static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        ApplicationScanner.getScanner().nextLine();
    }

    public static void viewCandidates() {
        //test


    }
}