package org.example;

import java.util.InputMismatchException;

public class Employee extends User{
    public Employee(String name, int userID, String email, String hashedPassword, String userType) {
        super(name, userID, email, hashedPassword, userType);
    }


    public static void EmployeeMainMenu() {
        boolean running = true;

        while (running) {
            StartupMenu.clearScreen(); // Optional: Clears terminal output
            printMenuOptions();

            try {
                int choice = userInputScanner.getInt(); // Your custom scanner method
                running = handleMenuChoice(choice);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                userInputScanner.getInt();
            }

            pauseForUser();
        }
    }

    public static void printMenuOptions() {
        System.out.println("Employer Homepage:");
        System.out.println("1. View Jobs");
        System.out.println("2. View Applied Jobs");
        System.out.println("3. Exit Program");

    }

    private static boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Selected: View Jobs");
                viewJobs(); // Method to be implemented
                break;
            case 2:
                System.out.println("Selected: View Applied Jobs");
                viewAppliedJobs(); // Method to be implemented
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
        System.out.println("Goodbye!");
        ApplicationScanner.getScanner().close();
        System.exit(0);
    }

    public static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        ApplicationScanner.getScanner().nextLine();
    }


    public static void viewJobs() {
        int numberOfJobs = JobDatabase.JobManager.getJobCount();
        System.out.println("Available jobs:");

        for (int i = 1; i<=numberOfJobs;i++){
            System.out.println(i+". "+ JobDatabase.JobManager.getJob(i-1));
        }

    }

    public static void viewAppliedJobs() {
        System.out.println("[TODO] Display candidates who applied to your jobs.");
    }
}

