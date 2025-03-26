package org.example;

public class Candidate extends User{
    private double rating;

    public Candidate(String name, int userID, String email, String hashedPassword, String userType, double rating) {
        super(name, userID, email, hashedPassword, userType);
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }



}
