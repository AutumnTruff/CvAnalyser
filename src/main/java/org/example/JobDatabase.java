package org.example;

import java.io.*;
import java.util.LinkedHashMap;

public class JobDatabase implements Serializable {
    private static final long serialVersionUID = 1L;

    //  Nested manager class handles storage and logic
    public static class JobManager implements Serializable {
        private static final long serialVersionUID = 1L;

        private static LinkedHashMap<Integer, JobPosting> jobPostings = new LinkedHashMap<>();

        public void addJob(JobPosting job) {
            jobPostings.put(job.getJobId(), job);
            saveDatabase();
        }

        public static JobPosting getJob(int jobId) {
            return jobPostings.get(jobId);
        }

        public static void printAllJobs() {
            for (JobPosting job : jobPostings.values()) {
                System.out.println(job);
            }
        }

        // Save jobs to file
        public static void saveDatabase() {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("jobs.ser"))) {
                out.writeObject(jobPostings);
                System.out.println("Job database saved!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //Load jobs from file
        public static void loadDatabase() {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("jobs.ser"))) {
                jobPostings = (LinkedHashMap<Integer, JobPosting>) in.readObject();
                System.out.println("Job database loaded!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No previous job database found. Creating a new one.");
                jobPostings = new LinkedHashMap<>();
            }
        }
        public static int getJobCount(){
            return jobPostings.size();
        }
        public int getNextJobId() {
            return jobPostings.keySet().stream()
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(0) + 1;
        }

        public void removeJob(int jobId) {
            if (jobPostings.containsKey(jobId)) {
                jobPostings.remove(jobId);
                saveDatabase();
            }
        }
    }
}