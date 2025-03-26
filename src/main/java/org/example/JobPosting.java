package org.example;

import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import java.util.Scanner;

public class JobPosting implements Serializable {
    private int jobId;
    protected String title;
    protected String description;
    protected String status;
    protected List<String> requirements;

    public JobPosting(int jobId, String title, String description, String status, List<String> requirements) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.requirements = requirements;
    }

    //getters
    public int getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getRequirements() {
        return requirements;
    }


    @Override
    public String toString() {
        return "JobPosting{" +
                "jobId=" + jobId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", requirements=" + requirements +
                '}';
    }



    static void RecruiterHelperMenu() {
        while (true) {
            System.out.println("Select an option: \n1. Add Job\n2. Edit Job\n3. Remove Job\n4. Return to main screen");
            int menuOption = userInputScanner.getInt();
            switch (menuOption) {
                case 1:
                    System.out.println("Selected: Add Job");
                    JobPosting.addJob();
                    break;
                case 2:
                    System.out.println("Selected: Edit Job");
                    JobPosting.editJob();
                    break;
                case 3:
                    System.out.println("Selected: Remove Job");
                    JobPosting.removeJob();
                    break;
                case 4:
                    System.out.println("Selected: Return to main screen");
                    Recruiter.recruiterMainMenu();
                    break;
                default:
                    System.out.println("Input a correct option");
                    break;
            }
        }
    }


    public static void addJob() {
        Scanner scanner = ApplicationScanner.getScanner();
        JobDatabase.JobManager jobManager = new JobDatabase.JobManager();

        System.out.println("Selected: Add New Job");

        // Auto-generate job ID
        int jobId = jobManager.getNextJobId();

        // Collect job info
        String title = Validation.getValidatedString("Enter Job Title: ", scanner, false);
        String description = Validation.getValidatedString("Enter Job Description:", scanner, false);

        // Validate status
        String status = "";
        boolean valid = false;
        while (!valid) {
            status = Validation.getValidatedString("Enter Job Status (Open/Closed): ", scanner, false);
            status = status.toLowerCase();
            if (status.equals("open") || status.equals("closed")) {
                valid = true;
            } else {
                System.out.println("Please choose between 'Open' or 'Closed'");
            }
        }

        // Requirements input
        System.out.print("Enter Requirements (comma-separated): ");
        String[] reqArray = scanner.nextLine().split(",");
        List<String> requirements = Arrays.stream(reqArray).map(String::trim).toList();

        // Create and store the job
        JobPosting newJob = new JobPosting(jobId, title, description, status, requirements);
        jobManager.addJob(newJob);

        System.out.println("\nJob added successfully!");
        System.out.println("Stored Job: " + newJob);
    }


    private static void editJob() {
        //creating needed objects for method
        Scanner scanner = ApplicationScanner.getScanner();
        JobDatabase.JobManager jobManager = new JobDatabase.JobManager();
        JobDatabase.JobManager.printAllJobs();

        //determining what job to edit
        System.out.println("Enter the jobID of the job you wish to modify: ");
        int jobId = userInputScanner.getInt();
        JobPosting job = jobManager.getJob(jobId);

        if (job == null) {
            System.out.println("no job found with the  ID" + jobId);
            return;
        }

        boolean editing = true;
        while (editing) {
            String jobElement = Validation.getValidatedString(
                "What element of the job would you like to edit? (title, description, status, requirements, exit): ",
                scanner,
                false
            ).toLowerCase();

            switch (jobElement) {
                case "title":
                    System.out.println("Selected: Title");
                    System.out.println("Current title: " + job.getTitle());
                    String newTitle = Validation.getValidatedString("Enter new title (or press Enter to keep current):", scanner, false);
                    if (!newTitle.isBlank()) {
                        job.title = newTitle;
                    }
                    break;
                case "description":
                    System.out.println("Selected: Description");
                    System.out.println("Current Description: " + job.getDescription());
                    String newDescription = Validation.getValidatedString("Enter new description (or press Enter to keep current):", scanner, false);
                    if (!newDescription.isBlank()) {
                        job.description = newDescription;
                    }
                    break;
                case "status":
                    System.out.println("Current Status: " + job.getStatus());
                    String newStatus = "";
                    boolean validStatus = false;
                    while (!validStatus) {
                        newStatus = Validation.getValidatedString("Enter new status (Open/Closed or Enter to keep current):", scanner, false);
                        if (newStatus.isBlank()) break; // keep current
                        newStatus = newStatus.toLowerCase();
                        if (newStatus.equals("open") || newStatus.equals("closed")) {
                            job.status = newStatus;
                            validStatus = true;
                        } else {
                            System.out.println("Please enter either 'Open' or 'Closed'.");
                        }
                    }
                    break;
                case "requirements":
                    System.out.println("Current Requirements: " + job.getRequirements());
                    System.out.print("Enter new requirements (comma-separated) or press Enter to keep current: ");
                    String newReqInput = scanner.nextLine();
                    if (!newReqInput.isBlank()) {
                        List<String> newRequirements = Arrays.stream(newReqInput.split(","))
                                .map(String::trim)
                                .toList();
                        job.requirements = newRequirements;
                    }
                    break;
                case "exit":
                    editing = false;
                    break;
                default:
                    System.out.println("Input a correct option");
                    break;
            }
        }
        jobManager.addJob(job); // re-add job to overwrite
        System.out.println("\nJob updated successfully!");
        System.out.println("Updated Job: " + job);
    }

    public static void removeJob() {
        Scanner scanner = ApplicationScanner.getScanner();
        JobDatabase.JobManager jobManager = new JobDatabase.JobManager();

        JobDatabase.JobManager.printAllJobs();

        System.out.println("Enter the Job ID to remove:");
        int jobId = userInputScanner.getInt();

        JobPosting job = jobManager.getJob(jobId);

        if (job == null) {
            System.out.println("No job found with ID: " + jobId);
            return;
        }

        System.out.println("Are you sure you want to delete this job?");
        System.out.println(job);
        System.out.print("Type 'yes' to confirm: ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            jobManager.removeJob(jobId);
            System.out.println(" Job removed successfully!");
        } else {
            System.out.println(" Job deletion cancelled.");
        }
    }
}
