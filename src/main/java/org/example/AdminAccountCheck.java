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
            EnteringProgram.register(); // This will let you manually create an "admin" user
        }
    }
}
