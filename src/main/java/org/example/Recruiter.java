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
        System.out.println("1. View Candidates");
        System.out.println("2. Add Candidates");
        System.out.println("3. Modify Jobs");
        System.out.println("4. Exit program");
    }

    private static boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Selected: View Candidates");
                viewCandidates();
                break;
            case 2:
                System.out.println("Selected: Add Candidates");
                addCandidatesRecruiter();
                break;
            case 3:
                System.out.println("Selected: Modify Jobs");
                JobPosting.RecruiterHelperMenu();
                break;
            case 4:
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
        List<JobPosting> jobList = JobDatabase.JobManager.getAllJobs();
        if (jobList.isEmpty()) {
            System.out.println("No jobs available.");
            return;
        }

        System.out.println("Select a job to view candidates:");
        for (int i = 0; i < jobList.size(); i++) {
            System.out.println((i + 1) + ". " + jobList.get(i).getTitle());
        }

        int selected = 0;
        do {
            selected = userInputScanner.getInt();
        } while (selected < 1 || selected > jobList.size());

        JobPosting selectedJob = jobList.get(selected - 1);
        int jobId = selectedJob.getJobId();


        // collecting all applications
        List<JobApplication> applications = JobApplicationDatabase.getAllApplications();
        List<Candidate> allCandidates = new ArrayList<>();

        for (JobApplication app : applications) {
            if (app.getJobId() == jobId) {
                Candidate candidate = CandidateDatabase.findCandidateById(app.getCandidateId());
                if (candidate != null) {
                    allCandidates.add(candidate);
                }
            }
        }



        //filtering candidates to show the best fit
        System.out.println("View (1) Top Candidates or (2) All Candidates?");
        int viewChoice;
        do {
            viewChoice = userInputScanner.getInt();
        } while (viewChoice != 1 && viewChoice != 2);

        List<Candidate> candidatesToDisplay = new ArrayList<>();
        if (viewChoice == 1) {
            for (Candidate c : allCandidates) {
                if (c.getRating() >= 4.0) {
                    candidatesToDisplay.add(c);
                }
            }
            System.out.println("Top Candidates (Rating >= 4.0) for: " + selectedJob.getTitle());
        } else {
            candidatesToDisplay = allCandidates;
            System.out.println("All Candidates for: " + selectedJob.getTitle());
        }

        // shows candidates to user
        if (candidatesToDisplay.isEmpty()) {
            System.out.println("No candidates to display for this job.");
        } else {
            for (Candidate c : candidatesToDisplay) {
                System.out.println("- " + c.getName() + " (Email: " + c.getEmail() + ", Rating: " + c.getRating() + ")");
            }
        }
    }

    public static String addCandidatesRecruiter(){
        //displaying available jobs and getting selection from the user
        List<JobPosting> jobList = JobDatabase.JobManager.getAllJobs();
        if (jobList.isEmpty()) {
            System.out.println("No jobs available.");
            return "no Jobs are currently listed";
        }

        System.out.println("Select a job to Add candidates:");
        for (int i = 0; i < jobList.size(); i++) {
            System.out.println((i + 1) + ". " + jobList.get(i).getTitle());
        }

        int selected = 0;
        do {
            selected = userInputScanner.getInt();
        }
        while (selected < 1 || selected > jobList.size());

        JobPosting selectedJob = jobList.get(selected - 1);
        int jobId = selectedJob.getJobId();

        //getting cv from user
        System.out.println("Paste the candidate's CV (then press Enter):");
        String cvText = userInputScanner.getCVInput();

        //Parse the CV using NLPProcessor
        Candidate parsedCandidate = NLPProcessor.pullCandidateInfo(cvText);
        if (parsedCandidate == null) {
            return "Failed to parse CV. Please try again.";
        }

        // Assign a unique ID
        int newId = CandidateDatabase.getAllCandidates().size() + 1000;
        parsedCandidate.setUserID(newId);

        // Save candidate
        CandidateDatabase.addCandidate(parsedCandidate);

        // Link to job
        JobApplication application = new JobApplication(jobId, parsedCandidate.getUserID());
        JobApplicationDatabase.addApplication(application);

        return "Candidate \"" + parsedCandidate.getName() + "\" successfully added and applied to job \"" + selectedJob.getTitle() + "\".";


    }
}