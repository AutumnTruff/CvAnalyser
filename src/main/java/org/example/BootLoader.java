package org.example;

public class BootLoader {
    public static void StartupMethods(){

        //DO NOT EDIT THIS CLASS/METHODS I WILL CRY
        //DO NOT EDIT THIS CLASS/METHODS I WILL CRY
        // Load database from file

        Database.AccountInfo.loadDatabase();
        // Load user accounts from file

        JobDatabase.JobManager.loadDatabase();
        // Load jobs

        CandidateDatabase.loadDatabase();
        //loads candidate database
        JobApplicationDatabase.loadDatabase();
        //loads application database

        AdminAccountCheck.adminAccountCheck();
        // Check if admin exists

    }
}
//Holds any methods that need to be executed at start up, hence the name bootLoader
//prevents errors from anything not being setup before its needed


