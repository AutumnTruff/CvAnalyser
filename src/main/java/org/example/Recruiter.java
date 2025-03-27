package org.example;

import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

public class Recruiter extends User {

    public Recruiter(String name, int userID, String email, String hashedPassword, String userType) {
        super(name, userID, email, hashedPassword, userType);
    }

    // Main menu loop for recruiters — handles interaction flow
    public static void recruiterMainMenu() {
        boolean running = true;

        while (running) {
            StartupMenu.clearScreen();  // Clear console for better readability
            printMenuOptions();

            try {
                int choice = userInputScanner.getInt();
                running = handleMenuChoice(choice);
            } catch (InputMismatchException e) {
                // Gracefully handle invalid input (e.g. letters instead of numbers)
                System.out.println("Please enter a valid number!");
                userInputScanner.getInt(); // Clear out invalid token
            }

            pauseForUser();  // Give user time to read before continuing
        }
    }

    // Displays main options available to recruiters
    public static void printMenuOptions() {
        System.out.println("Recruiter Homepage:");
        System.out.println("1. View Candidates");
        System.out.println("2. Add Candidates");
        System.out.println("3. Modify Jobs");
        System.out.println("4. Exit program");
    }

    // Routes recruiter’s menu selection to corresponding functionality
    private static boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Selected: View Candidates");
                viewCandidates();
                break;
            case 2:
                System.out.println("Selected: Add Candidates");
                System.out.println(addCandidatesRecruiter());
                break;
            case 3:
                System.out.println("Selected: Modify Jobs");
                JobPosting.recruiterMenu();
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

    // Cleanly exits the application
    public static void exitingProgram() {
        System.out.println("Selected: Exit");
        System.out.println("See you soon!");
        ApplicationScanner.getScanner().close();  // Free up scanner resource
        System.exit(0);
    }

    // Pauses to let the user digest output
    public static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        ApplicationScanner.getScanner().nextLine();
    }

    // Allows recruiter to select a job and view its candidates
    public static void viewCandidates() {
        List<JobPosting> jobList = JobDatabase.JobManager.getAllJobs();
        if (jobList.isEmpty()) {
            System.out.println("No jobs available.");
            return;
        }

        // Display all job postings
        System.out.println("Select a job to view candidates:");
        for (int i = 0; i < jobList.size(); i++) {
            System.out.println((i + 1) + ". " + jobList.get(i).getTitle());
        }

        int selected = getValidatedMenuChoice(1, jobList.size());
        JobPosting selectedJob = jobList.get(selected - 1);
        int jobId = selectedJob.getId();

        JobApplicationDatabase db = new JobApplicationDatabase();
        List<JobApplication> applications = db.getAllApplications();
        List<Candidate> allCandidates = new ArrayList<>();

        // Gather all candidates who applied for the selected job
        for (JobApplication app : applications) {
            if (app.getJobId() == jobId) {
                Candidate candidate = CandidateDatabase.findCandidateById(app.getCandidateId());
                if (candidate != null) {
                    allCandidates.add(candidate);
                }
            }
        }

        // Give recruiter an option to filter for top-rated candidates
        System.out.println("View (1) Top Candidates or (2) All Candidates?");
        int viewChoice = getValidatedMenuChoice(1, 2);

        List<Candidate> candidatesToDisplay = new ArrayList<>();
        if (viewChoice == 1) {
            // Filter only high-rated candidates
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

        // Show results
        if (candidatesToDisplay.isEmpty()) {
            System.out.println("No candidates to display for this job.");
        } else {
            for (Candidate c : candidatesToDisplay) {
                System.out.println("- " + c.getName() + " (Email: " + c.getEmail() + ", Rating: " + c.getRating() + ")");
            }
        }
    }

    // Lets recruiter add a new candidate based on a CV input
    public static String addCandidatesRecruiter() {
        List<JobPosting> jobList = JobDatabase.JobManager.getAllJobs();
        if (jobList.isEmpty()) {
            System.out.println("No jobs available.");
            return "no Jobs are currently listed";
        }

        // Display jobs to associate the candidate with
        System.out.println("Select a job to Add candidates:");
        for (int i = 0; i < jobList.size(); i++) {
            System.out.println((i + 1) + ". " + jobList.get(i).getTitle());
        }

        int selected = getValidatedMenuChoice(1, jobList.size());
        JobPosting selectedJob = jobList.get(selected - 1);
        int jobId = selectedJob.getId();

        // Prompt recruiter to paste CV content
        System.out.println("Paste the candidate's CV (then press Enter):");
        String cvText = userInputScanner.getCVInput();

        // Use NLP to extract candidate info from raw CV
        Candidate parsedCandidate = NLPProcessor.pullCandidateInfo(cvText);
        if (parsedCandidate == null) {
            return "Failed to parse CV. Please try again.";
        }

        // Generate a unique user ID for the new candidate
        int newId = CandidateDatabase.getAllCandidates().size() + 1000;
        parsedCandidate.setUserID(newId);

        // Avoid duplicates by checking email (not needed for the recruiter as they will be uploading lots of cv's )
       // if (CandidateDatabase.getAllCandidates().stream().anyMatch(c -> c.getEmail().equals(parsedCandidate.getEmail()))) {
          //  return "Candidate with this email already exists.";
        //}

        CandidateDatabase.addCandidate(parsedCandidate);
        CandidateDatabase.saveDatabase();

        // Create a job application linking the candidate to the selected job
        JobApplication application = new JobApplication(jobId, parsedCandidate.getUserID(), cvText);
        JobApplicationDatabase db = new JobApplicationDatabase();
        db.addApplication(application);

        NLPThreadCreator.createThread(application);
        //calling the nlp side of the program to process the uploaded cv in the background

        return "Candidate \"" + parsedCandidate.getName() + "\" successfully added and applied to job \"" + selectedJob.getTitle() + "\".";
    }

    // Utility method to get user input within a valid range
    private static int getValidatedMenuChoice(int min, int max) {
        int choice;
        do {
            choice = userInputScanner.getInt();
            if (choice < min || choice > max) {
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            }
        } while (choice < min || choice > max);
        return choice;
    }
}