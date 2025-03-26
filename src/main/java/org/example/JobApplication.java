package org.example;
import java.io.Serializable;

public class JobApplication implements Serializable {
    private static final long serialVersionUID = 1L;
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