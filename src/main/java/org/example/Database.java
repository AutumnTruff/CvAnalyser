package org.example;
import java.io.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

class Database implements Serializable {
    static class AccountInfo implements Serializable{
        static LinkedHashMap<String, User> userAccountData = new LinkedHashMap<>();

        public  static void addUser(User user){
            userAccountData.put(user.getName().toLowerCase(), user);
            saveDatabase();
        }

        public static User getUser(String name) {
            return userAccountData.get(name.toLowerCase());

        }

        // Method to print all users
        public void printAllUsers() {
            for (User user : userAccountData.values()) {
                System.out.println(user);
            }
        }
        public static int getUserCount(){
            return userAccountData.size();
        }
        public static List<User> getAllUsers() {
            return new ArrayList<>(userAccountData.values());
        }
        public static void saveDatabase(){
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("UserDatabase.ser"))){
                out.writeObject(userAccountData);
                System.out.println("Database saved successfully ");}
            catch (IOException e){
                e.printStackTrace();

            }
        }
        public static void loadDatabase(){
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("UserDatabase.ser"))){
                userAccountData = (LinkedHashMap<String, User>) in.readObject();
                System.out.println("Database Loaded successfully ");}
            catch(IOException| ClassNotFoundException e){
                System.out.println("No previous database found. creating fresh database");
                userAccountData = new LinkedHashMap<>();
            }

        }
    }

}
