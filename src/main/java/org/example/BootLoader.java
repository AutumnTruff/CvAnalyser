package org.example;

public class BootLoader {
    public static void StartupMethods(){
        // Load database from file
        Database.AccountInfo.loadDatabase();
        // Load user accounts from file
        JobDatabase.JobManager.loadDatabase();
        // Load jobs
        AdminAccountCheck.adminAccountCheck();
        // Check if admin exists

    }
}
