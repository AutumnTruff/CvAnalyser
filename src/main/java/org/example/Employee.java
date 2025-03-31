package org.example;

import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;

public class Employee extends User{
    public Employee(String name, int userID, String email, String hashedPassword, String userType) {
        super(name, userID, email, hashedPassword, userType);
        //Reuses parent user class constructors
    }

    public static void EmployeeMainMenu(Candidate currentUser) {
        boolean running = true;

        while (running) {
            StartupMenu.clearScreen();
            printMenuOptions();

            try {
                int choice = userInputScanner.getInt();
                running = handleMenuChoice(choice, currentUser);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                userInputScanner.getInt();
                //collects users menu choice for use within the menu method,
                //try catch used as a form of input validation against invalid input
            }

            pauseForUser();
        }
    }

    public static void printMenuOptions() {
        System.out.println("Employer Homepage:");
        System.out.println("1. View Jobs");
        System.out.println("2. Apply For Jobs");
        System.out.println("3. View Applied Jobs");
        System.out.println("4. Exit Program");
        //simple little method to break the code up into more readable chunks
    }

    private static boolean handleMenuChoice(int choice, Candidate currentUser) {
        switch (choice) {
            case 1:
                System.out.println("Selected: View Jobs");
                viewJobs();
                break;
            case 2:
                System.out.println("Selected: Apply for jobs");
                submitApplication(currentUser);
                break;
            case 3:
                System.out.println("Selected: View Applied Jobs");
                viewAppliedJobs(currentUser);
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
        System.out.println("Goodbye!");
        ApplicationScanner.getScanner().close();
        System.exit(0);

    }


    public static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        ApplicationScanner.getScanner().nextLine();
    }


    public static void viewJobs() {
        System.out.println("Available Job Listings:");
        List<JobPosting> openJobs = new ArrayList<>();
        // Lists all open jobs and allows the employee to view one
        for (JobPosting job : JobDatabase.JobManager.getAllJobs()) {
            if ("open".equalsIgnoreCase(job.getStatus())) {
                openJobs.add(job);
            }
        }

        if (openJobs.isEmpty()) {
            System.out.println("No open job postings found.");
            return;
        }

        for (int i = 0; i < openJobs.size(); i++) {
            JobPosting job = openJobs.get(i);
            System.out.println((i + 1) + ". " + job.getTitle() + " - " + job.getDescription());
        }

        System.out.println("Select a job by number:");
        int selectedJob = 0;
        do {
            selectedJob = userInputScanner.getInt();
        } while (selectedJob < 1 || selectedJob > openJobs.size());

        JobPosting selected = openJobs.get(selectedJob - 1);
        System.out.println("You have selected the job: " + selected.getTitle());
    }


    public static String submitApplication(Candidate candidate) {
        // Gets all job postings so the user can pick one
        List<JobPosting> jobs = JobDatabase.JobManager.getAllJobs();


        if (jobs.isEmpty()) {
            System.out.println("No jobs available.");
            return null;
        }


        System.out.println("Select a job to apply for:");

        for (int i = 0; i < jobs.size(); i++) {
            System.out.println((i + 1) + ". " + jobs.get(i).getTitle());
        }

        int selection = 0;

        do {
            selection = userInputScanner.getInt();

        }
        while (selection < 1 || selection > jobs.size());


        JobPosting selectedJob = jobs.get(selection - 1);
        int jobId = selectedJob.getId();


        JobApplicationDatabase jobAppDb = new JobApplicationDatabase();
        for (JobApplication app : jobAppDb.getAllApplications()) {
            if (app.getJobId() == jobId && app.getCandidateId() == candidate.getUserID()) {
                System.out.println("You’ve already applied to this job.");
                return null;
                // Just making sure they don't spam apply the same job
            }
        }


        System.out.println("Paste your CV below (then press Enter):");
        String cvText = userInputScanner.getCVInput();

        // Asks for CV input (will get parsed by NLP)
        Candidate parsedCandidate = NLPProcessor.pullCandidateInfo(cvText);
        if (parsedCandidate == null) {
            return "Failed to parse CV. Please try again.";
            // Try to extract info from the cv
        }


        int newId = CandidateDatabase.getAllCandidates().size() + 1000;
        parsedCandidate.setUserID(newId);
        // Generate a new ID for the parsed data to be used later


        // Create and save the application
        JobApplication application = new JobApplication(jobId, candidate.getUserID(), cvText);
        jobAppDb.addApplication(application);
        // Link the original logged-in candidate to this job (not the parsed one)

        System.out.println("Successfully applied to: " + selectedJob.getTitle());


        NLPThreadCreator.createThread(application);
        //calling the nlp
        return cvText;
    }


    public static void viewAppliedJobs(Candidate candidate) {
        JobApplicationDatabase jobAppDb = new JobApplicationDatabase();
        List<JobApplication> allApplications = jobAppDb.getAllApplications();
        List<Integer> appliedJobIds = new ArrayList<>();
        // Grab all job applications from the system

        for (JobApplication app : allApplications) {
            if (app.getCandidateId() == candidate.getUserID()) {
                appliedJobIds.add(app.getJobId());
            }
            //filter the applications to just the relevant candidates applications
        }

        if (appliedJobIds.isEmpty()) {
            System.out.println("You have not applied to any jobs yet.");

            return;
        }

        System.out.println("Jobs you've applied to:");
        for (JobPosting job : JobDatabase.JobManager.getAllJobs()) {
            if (appliedJobIds.contains(job.getId())) {
                System.out.println("- " + job.getTitle() + " (" + job.getDescription() + ")");

            }
        }
    }
}
