package org.example;

public class JobApplication {
    private final int jobId;
    private final int candidateId;

    public JobApplication(int jobId, int candidateId) {
        this.jobId = jobId;
        this.candidateId = candidateId;
    }

    public int getJobId() {
        return jobId;
    }

    public int getCandidateId() {
        return candidateId;
    }
}