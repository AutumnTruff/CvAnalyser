package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationDatabase {
    //links the candidates application to the job applied for
    private static final List<JobApplication> applications = new ArrayList<>();

    public static void addApplication(JobApplication app) {
        applications.add(app);
        saveDatabase();
    }

    public static List<JobApplication> getAllApplications() {
        return applications;
    }

    public static List<Candidate> getCandidatesForJob(int jobId) {
        List<Candidate> result = new ArrayList<>();
        for (JobApplication app : applications) {
            if (app.getJobId() == jobId) {
                Candidate currentCandidate = CandidateDatabase.findCandidateById(app.getCandidateId());
                if (currentCandidate != null) result.add(currentCandidate);
            }
        }
        return result;
    }
    public static void saveDatabase() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("JobApplications.ser"))) {
            out.writeObject(applications);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDatabase() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("JobApplications.ser"))) {
            List<JobApplication> loaded = (List<JobApplication>) in.readObject();
            applications.clear();
            applications.addAll(loaded);
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if file not found on first run
        }
    }
}
