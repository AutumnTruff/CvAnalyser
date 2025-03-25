package org.example;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

import static org.example.Validation.getValidatedString;

public class EnteringProgram {

    static {
        Database.AccountInfo.loadDatabase();
    }

    public static void login() {
        System.out.println("Welcome to the Automated CV program");

        // Setting up scanner object for the login program
        Scanner scanner = ApplicationScanner.getScanner();

        // Collecting username input with validation
        String username = getValidatedString("Enter Username: ", scanner, false).trim().toLowerCase();

        // Handle validation failure (e.g., too many failed attempts)
        if ("ERROR".equals(username)) {
            System.out.println("Too many failed attempts. Exiting login.");
            return;
        }

        // Retrieve user data
        User user = Database.AccountInfo.getUser(username);

        if (user != null) {
            // Ask for password input with validation
            String password = getValidatedString("Enter Password: ", scanner, false).trim();

            if ("ERROR".equals(password)) {
                System.out.println("Too many failed attempts. Exiting login.");
                return;
            }

            System.out.println("DEBUG - Entered password: [" + password + "]");
            System.out.println("DEBUG - Stored hash: [" + user.getHashedPassword() + "]");
            System.out.println("DEBUG - Hash matches: " + BCrypt.checkpw(password, user.getHashedPassword()));

            // Verify password using BCrypt
            if (BCrypt.checkpw(password, user.getHashedPassword())) {
                // Use instance method for user object
                System.out.println("Login successful! Welcome, " + user.getName());
                //navigating to correct fork of the progrma
                if(user.getUserType().equals("recruiter")){
                    Recruiter.recruiterMainMenu();}

                if(user.getUserType().equals("employee")){
                    Employee.EmployeeMainMenu();
                }
            } else {
                System.out.println("Error: Incorrect password!");
            }

        } else {
            System.out.println("Error: User not found!");
        }
    }

    public static void register() {
        Scanner scanner = ApplicationScanner.getScanner();
        System.out.println("Selected: Register");

        // Collecting and validating user details
        String username = getValidatedString("Enter Username:", scanner, false);

        // Handle validation failure
        if ("ERROR".equals(username)) {
            System.out.println("Too many failed attempts. Exiting registration.");
            return;
        }

        // Normalize username to lowercase for consistency
        username = username.toLowerCase();

        // Email input validation
        String email = getValidatedString("Enter Email:", scanner, true);

        if ("ERROR".equals(email)) {
            System.out.println("Too many failed attempts. Exiting registration.");
            return;
        }

        // Securely hash the password
        String plainPassword = getValidatedString("Enter Password:", scanner, false).trim();

        if ("ERROR".equals(plainPassword)) {
            System.out.println("Too many failed attempts. Exiting registration.");
            return;
        }

        String hashedPassword = hashPassword(plainPassword);
        System.out.println("DEBUG - Hashed password for storage: [" + hashedPassword + "]");

        // Generate a new unique user ID
        int userID = generateUserID();

        // User type input
        String userType = getValidatedString("Enter Account Type:", scanner, false);
        if ("ERROR".equals(userType)) {
            System.out.println("Too many failed attempts. Exiting registration.");
            return;
        }

        // Check if the username already exists
        if (Database.AccountInfo.getUser(username) != null) {
            System.out.println("Error: Username already exists! Choose a different username.");
            return;
        }

        // Create a new User object and add it to the database
        User newUser = new User(username, userID, email, hashedPassword, userType);
        Database.AccountInfo.addUser(newUser);

        // Confirm registration
        System.out.println("\nUser registered successfully!");
        System.out.println("Retrieved User: " + Database.AccountInfo.getUser(username)); // For verification
    }

    // Securely hash the password using BCrypt
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Generate a unique user ID
    public static int generateUserID() {
        int userID;
        do {
            userID = (int) (Math.random() * 1000000); // Generate ID (6 digits)
        } while (isUserIDTaken(userID)); // Ensure uniqueness
        return userID;
    }

    // Helper method to check if a user ID already exists (ensures uniqueness)
    private static boolean isUserIDTaken(int userID) {
        return Database.AccountInfo.getAllUsers().stream()
                .anyMatch(user -> user.getUserID() == userID);
    }
}