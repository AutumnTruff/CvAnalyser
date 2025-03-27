package org.example;
import java.io.Serializable;

/*
 Represents a job application with a unique job ID and candidate ID.
 Implements Serializable to allow instances to be serialized for storage.
 */
public class JobApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    // Used for serialization compatibility
    private final int jobId;
    // Unique identifier for the job
    private final int candidateId;
    // Unique identifier for the candidate
    private String cvText;

    public JobApplication(int jobId, int candidateId, String cvText) {
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.cvText = cvText;
    }

    public int getJobId() {
        return jobId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getCvText(){
        return cvText;
    }

    public void setCvText(String cvText){
        this.cvText = cvText;
    }
}