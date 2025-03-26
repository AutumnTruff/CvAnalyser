package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDatabase {
    private static final List<Candidate> candidates = new ArrayList<>();

    public static void addCandidate(Candidate candidate) {
        candidates.add(candidate);
        saveDatabase();
        //adds and saves a candidate to the applications file for processing
    }

    public static List<Candidate> getAllCandidates(){
        return new ArrayList<>(candidates);
        //returns a list of all the candidates for use by the recruiter to see their options
    }

    public static Candidate findCandidateById(int userId) {
        for (Candidate candidate : candidates) {
            if (candidate.getUserID() == userId) {
                return candidate;
            }
        }
        return null;
        // Finds a candidate by ID — used to display applicant info for a specific job.
    }
    public static void saveDatabase() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CandidateDatabase.ser"))) {
            out.writeObject(candidates);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //saves the database to prevent against loosing data
    }

    public static void loadDatabase() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("CandidateDatabase.ser"))) {
            List<Candidate> loaded = (List<Candidate>) in.readObject();
            candidates.clear();
            candidates.addAll(loaded);
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if file not found on first run
        }
        //used at program start up, makes sure the database is correctly read from the file so the program runs as it should
    }

}
