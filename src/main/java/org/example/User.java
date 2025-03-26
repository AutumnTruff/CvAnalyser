package org.example;
import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;

public class User implements Serializable{
    // initialising field variables
    private static int idCounter = 1;
    private  String name;
    private int userID;
    private  String email;
    private  String hashedPassword;
    private String userType;

    // constructor
    public User(String name, int userID, String email, String plainPassword, String userType) {
        this.name = name;
        this.email = email;
        this.userID =idCounter++;
        this.hashedPassword = plainPassword;
        this.userType = userType;
    }
//getter methods

    public String getName() {
        return name;
    }

    public  int getUserID() {
        return userID;
    }

    public  String getEmail(){
        return email;
    }

    public  String getHashedPassword() {
        return hashedPassword;
    }
    public String getUserType(){
        return userType;
    }
    //salt hashing
    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String plainPassword){
        return BCrypt.checkpw(plainPassword, this.hashedPassword);
    }
    // Override toString() to display user details properly
    @Override
    public String toString() {
        return "User{name='" + name + "', userID=" + userID + ", email='" + email + "', hashedPassword= '" + hashedPassword +", userType= "+ userType+ "'}";
    }

    //setters
    public  void setName(String input) {
        if (!this.name.equals(input)){
            this.name = input;
        }
        else{
            System.out.println("No change, same name");
        }
    }

    public void setEmail( String input){
        if(!this.email.equals(input)){
            this.email = input;
        }
        else{
            System.out.println("No change, same email");
        }
    }
    public void setUserID(int input) {
        if (this.userID != input) {
            this.userID = input;
        } else {
            System.out.println("No change, same userID");
        }
    }


    public void setUserType(String input){
        int counter = 0;
        do{if(!this.userType.equals(input)){
            input = input.toLowerCase();
            if(input.equals("recruiter")|| input.equals("employee")){
                this.userType  = input;
                break;}

            else{
                System.out.println("please choose from recruiter or employee");
                counter++;
            }
        }
        else{
            System.out.println("No change same role");
            break;
        }}while (counter <= 3);
        if (counter >= 4){
            throw new IllegalArgumentException("too many rejections, going back to register");


        }
    }

    public void setHashedPassword(String input){
        if(!this.hashedPassword.equals(input)){
            this.hashedPassword = input;
        }
        else{
            System.out.println("No change, same password");
        }}
    }



