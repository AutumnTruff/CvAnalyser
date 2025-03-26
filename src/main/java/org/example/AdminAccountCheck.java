package org.example;

public class AdminAccountCheck {
    public static void adminAccountCheck() {
        boolean adminExists = false;

        for (User user : Database.AccountInfo.getAllUsers()) {
            if (user.getName().equalsIgnoreCase("admin")) {
                adminExists = true;
                break;
            }
        }

        if (!adminExists) {
            System.out.println("No admin account detected. Starting registration...");
            EnteringProgram.register();
        }
    }
}
//allows the program to check if the program has an admin account, and in the case of one not existing, creates one