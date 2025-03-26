package org.example;
import java.io.*;
import java.util.*;

class Database implements Serializable {
    static class AccountInfo implements Serializable{
        static LinkedHashMap<String, User> userAccountData = new LinkedHashMap<>();
        //Linked hash map for storing Account information while the program is running
        //linked hashmap (differed from the original plan) used due to it being synchronized by nature, important for when im implementing multi-threading


        public  static void addUser(User user){
            userAccountData.put(user.getName().toLowerCase(), user);
            saveDatabase();
        }

        public static User getUser(String name) {
            return userAccountData.get(name.toLowerCase());
            //converting input to lowercase allows for a more streamlined user experience and searching within the program
        }

        // Method to print all users
        public void printAllUsers() {
            for (User user : userAccountData.values()) {
                System.out.println(user);
                //advanced for loop used for simple and clean presentation of account data
            }
        }
        public static int getUserCount(){
            return userAccountData.size();
            //allows for easy construction of loops when needed  within the program
        }
        public static List<User> getAllUsers() {
            return new ArrayList<>(userAccountData.values());
            //displays all the user accounts for the administrator and verifying certain accounts exist
        }
        public static void saveDatabase(){
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("UserDatabase.ser"))){
                out.writeObject(userAccountData);
                System.out.println("Database saved successfully ");}
            //writing to file
            catch (IOException e){
                e.printStackTrace();
                //allows for modifications to be saved as they are made to prevent loss of data in the case of a crash or power outage
                //try catch used as a preventative measure, in the case it runs into issues such as not being able to write to the file
                //and handle it nicely with an error code if it does

            }
        }
        public static void loadDatabase() {
            File file = new File("UserDatabase.ser");
            if (!file.exists()) {
                System.out.println("No existing user database found. Starting fresh.");
                userAccountData = new LinkedHashMap<>();
                return;
            }

            try (FileInputStream fileIn = new FileInputStream(file);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {

                userAccountData = (LinkedHashMap<String, User>) in.readObject();
                System.out.println("User database loaded successfully.");
                // Optional: new Database.AccountInfo().printAllUsers(); // If you want to see loaded users
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Failed to load user database: " + e.getMessage());
                userAccountData = new LinkedHashMap<>();
            }
        }
            //used at program start up, makes sure the database is correctly read from the file so the program runs as it should


    }

}
