package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobApplicationDatabase {

    private static final List<JobApplication> applications = new ArrayList<>();

    public void addApplication(JobApplication app) {
        if (app != null) {
            applications.add(app);
            saveDatabase();
        } else {
            throw new IllegalArgumentException("Application cannot be null");
        }
    }

    public static List<JobApplication> getAllApplications() {
        return Collections.unmodifiableList(new ArrayList<>(applications));
    }

    public List<Candidate> getCandidatesForJob(int jobId) {
        List<Candidate> result = new ArrayList<>();
        for (JobApplication app : applications) {
            if (app.getJobId() == jobId) {
                Candidate currentCandidate = CandidateDatabase.findCandidateById(app.getCandidateId());
                if (currentCandidate != null) result.add(currentCandidate);
            }
        }
        return result;
    }

    private void saveDatabase() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("JobApplications.ser"))) {
            out.writeObject(applications);
        } catch (IOException e) {
            System.err.println("Failed to save job applications: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadDatabase() {
        File file = new File("JobApplications.ser");
        if (!file.exists()) {
            System.out.println("No previous job applications found. Creating a new one.");
            applications.clear();
            return;
        }

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            List<JobApplication> loaded = (List<JobApplication>) in.readObject();
            applications.clear();
            applications.addAll(loaded);
            System.out.println("Loaded job applications successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load job applications: " + e.getMessage());
            applications.clear();
        }
    }
}