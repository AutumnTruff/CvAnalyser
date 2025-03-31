package org.example;

import java.io.Serializable;
import java.util.*;

public class JobPosting implements Serializable {
    private int id;
    protected String title;
    protected String description;
    protected String status;
    protected List<String> requirements;

    public JobPosting(int id, String title, String description, String status, List<String> requirements) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.requirements = requirements;
    }

    // Getters
    public int getId() {
        return id;
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
        return "Job [" + id + "] - " + title + " (" + status + ")\n" +
                "Description: " + description + "\n" +
                "Requirements: " + String.join(", ", requirements);
    }

    // Menu for recruiter actions
    public static void recruiterMenu() {
        Scanner scanner = ApplicationScanner.getScanner();

        while (true) {
            System.out.println("\n--- Recruiter Menu ---");
            System.out.println("1. Add Job");
            System.out.println("2. Edit Job");
            System.out.println("3. Remove Job");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");

            int choice = userInputScanner.getInt();

            switch (choice) {
                case 1 -> addJob();
                case 2 -> editJob();
                case 3 -> removeJob();
                case 4 -> {
                    System.out.println("Returning to main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please enter a number between 1 and 4.");
            }
        }
    }


    public static void addJob() {
        Scanner scanner = ApplicationScanner.getScanner();
        JobDatabase.JobManager manager = new JobDatabase.JobManager();

        int newId = manager.getNextJobId();

        System.out.print("Enter job title: ");
        String title = scanner.nextLine();

        System.out.print("Enter job description: ");
        String description = scanner.nextLine();

        String status;
        while (true) {
            System.out.print("Enter status (open/closed): ");
            status = scanner.nextLine().trim().toLowerCase();
            if (status.equals("open") || status.equals("closed")) break;
            System.out.println("Invalid input. Please enter 'open' or 'closed'.");
        }

        System.out.print("Enter requirements (comma-separated): ");
        String[] reqArray = scanner.nextLine().split(",");
        List<String> requirements = Arrays.stream(reqArray)
                .map(String::trim)
                .filter(r -> !r.isEmpty())
                .toList();

        JobPosting job = new JobPosting(newId, title, description, status, requirements);
        manager.addJob(job);

        System.out.println("\nJob successfully added:");
        System.out.println(job);
    }


    private static void editJob() {
        Scanner scanner = ApplicationScanner.getScanner();
        JobDatabase.JobManager manager = new JobDatabase.JobManager();

        JobDatabase.JobManager.printAllJobs();

        System.out.print("Enter the job ID to edit: ");
        int jobId = userInputScanner.getInt();
        JobPosting job = manager.getJob(jobId);

        if (job == null) {
            System.out.println("No job found with ID: " + jobId);
            return;
        }

        boolean editing = true;
        while (editing) {
            System.out.println("\nWhat would you like to edit?");
            System.out.println("Options: title, description, status, requirements, exit");
            System.out.print("Enter field to edit: ");
            String field = scanner.nextLine().toLowerCase();

            switch (field) {
                case "title" -> {
                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine();
                    if (!newTitle.isBlank()) job.title = newTitle;
                }
                case "description" -> {
                    System.out.print("New description: ");
                    String newDesc = scanner.nextLine();
                    if (!newDesc.isBlank()) job.description = newDesc;
                }
                case "status" -> {
                    System.out.print("New status (open/closed): ");
                    String newStatus = scanner.nextLine().toLowerCase();
                    if (newStatus.equals("open") || newStatus.equals("closed")) {
                        job.status = newStatus;
                    } else {
                        System.out.println("Invalid status. Must be 'open' or 'closed'.");
                    }
                }
                case "requirements" -> {
                    System.out.print("Enter new requirements (comma-separated): ");
                    String[] reqs = scanner.nextLine().split(",");
                    job.requirements = Arrays.stream(reqs)
                            .map(String::trim)
                            .filter(r -> !r.isEmpty())
                            .toList();
                }
                case "exit" -> editing = false;
                default -> System.out.println("Unknown field. Try again.");
            }
        }

        manager.addJob(job); // overwrite the old entry
        System.out.println("Job updated:");
        System.out.println(job);
    }


    public static void removeJob() {
        Scanner scanner = ApplicationScanner.getScanner();
        JobDatabase.JobManager manager = new JobDatabase.JobManager();

        JobDatabase.JobManager.printAllJobs();

        System.out.print("Enter the job ID to remove: ");
        int jobId = userInputScanner.getInt();
        JobPosting job = manager.getJob(jobId);

        if (job == null) {
            System.out.println("No job found with ID: " + jobId);
            return;
        }

        System.out.println("Are you sure you want to delete this job?");
        System.out.println(job);
        System.out.print("Type 'yes' to confirm: ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            manager.removeJob(jobId);
            System.out.println("Job removed.");
        } else {
            System.out.println("Job deletion cancelled.");
        }
    }
}