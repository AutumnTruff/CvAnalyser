package org.example;

import java.util.ArrayList;
import java.util.List;

public class CandidateDatabase {
    private static final List<Candidate> candidates = new ArrayList<>();

    public static void addCandidate(Candidate candidate) {
        candidates.add(candidate);
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
}
