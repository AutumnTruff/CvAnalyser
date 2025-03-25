package org.example;
import java.util.InputMismatchException;


public class StartupMenu {

    public static void runMenu(){
        boolean running = true;

        while(running){
            clearScreen();
            displayMenu();

            try{
                int choice = getMenuChoice();
                running = handleMenuChoice(choice);
            }
            catch(InputMismatchException e){
                System.out.println("PLease enter a valid number!");
                userInputScanner.getInt();
            }
            pauseForUser();
        }
    }

    private static void displayMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("4. View user accounts");
        System.out.print("Select an option: ");
    }

    //getting menu choice
    private static boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                loginUser();
                return true;
            case 2:
                registerUser();
                return true;
            case 3:
                exitProgram();
                return false;
            case 4:
                viewAllUsers();
                return true;
            default:
                System.out.println("Invalid option! Please try again.");
                return true;
        }
    }


    //calling appropriate method based on user input
    private static int getMenuChoice() throws InputMismatchException {
        return userInputScanner.getInt();
    }

    private static void loginUser() {
        EnteringProgram.login();
        new Database.AccountInfo().printAllUsers();
    }

    private static void registerUser() {
        EnteringProgram.register();
        new Database.AccountInfo().printAllUsers();
    }

    private static void viewAllUsers() {
        System.out.println("=== User Accounts ===");
        new Database.AccountInfo().printAllUsers();
        // Display all users from the database
    }

    private static void exitProgram() {
        System.out.println("See you soon!");
    }




    //Utility
    private static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        userInputScanner.getInput();

    }

    public static void clearScreen() {
        try {
            // Try Windows clear
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Fallback for macOS/Linux
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
