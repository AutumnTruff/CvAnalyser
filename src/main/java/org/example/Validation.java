package org.example;

import java.util.Scanner;

public class Validation {
    public static String getValidatedString(String prompt, Scanner scanner, boolean isEmail) {
        String input;
        boolean valid = false;
        int counter = 0;

        while (!valid) {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            // Trim whitespace

            if (isEmail) {
                // Validate email format
                if (input.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    valid = true;
                    return input;
                } else {
                    System.out.println("Invalid email format. Try again.");
                }
            } else {
                // Regular text validation (non-empty, no only spaces)
                if (!input.isEmpty()) {
                    valid = true;
                    return input;
                } else {
                    System.out.println("Invalid input. Cannot be empty.");
                }
            }

            counter++;

            if (counter >= 3) {
                System.out.println("Too many failed attempts. Returning to the home screen.");
                return "ERROR";
                // Return an error flag for the input validation
            }
        }

        return "ERROR";
        // Preventative measure
    }
}