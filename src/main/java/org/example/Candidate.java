package org.example;

public class Candidate extends User{
    private double rating;

    public Candidate(String name, int userID, String email, String hashedPassword, String userType, double rating) {
        super(name, userID, email, hashedPassword, userType);
        this.rating = rating;
    }
    public void setUserID(int userID) {
        super.setUserID(userID);
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }



}
