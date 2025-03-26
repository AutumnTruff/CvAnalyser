package org.example;

import java.util.ArrayList;
import java.util.List;

public class JobApplicationDatabase {
    //links the candidates application to the job applied for
    private static final List<JobApplication> applications = new ArrayList<>();

    public static void addApplication(JobApplication app) {
        applications.add(app);
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
}
