package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDatabase {
    private static final List<Candidate> candidates = new ArrayList<>();

    public static void addCandidate(Candidate candidate) {
        candidates.add(candidate);
        saveDatabase();

    }

    public static List<Candidate> getAllCandidates(){
        return new ArrayList<>(candidates);

    }

    public static Candidate findCandidateById(int userId) {
        for (Candidate candidate : candidates) {
            if (candidate.getUserID() == userId) {
                return candidate;
            }
        }
        return null;

    }
    public static void saveDatabase() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CandidateDatabase.ser"))) {
            out.writeObject(candidates);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadDatabase() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("CandidateDatabase.ser"))) {
            List<Candidate> loaded = (List<Candidate>) in.readObject();
            candidates.clear();
            candidates.addAll(loaded);
        } catch (IOException | ClassNotFoundException e) {
            //During the first run of the program the file may not exist just yet, this is safe to ignore if that is the case

        }

    }

}
