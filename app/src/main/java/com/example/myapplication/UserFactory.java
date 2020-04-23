package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    private static UserFactory singleton;


    private List<User> users = new ArrayList<>();


    private UserFactory(){
    }

    public static UserFactory getInstance(){
        if(singleton == null){
            singleton = new UserFactory();
        }
        return singleton;
    }

    public List<User> getUsers(){

        User admin = new User ("Admin", null, "Admin", null, null);
        User user1 = new User ("Faber", "Sau", "Faber123", "fabrizio@gmail.com", "12345");
        User user2 = new User ("Andre","Fulghesu","Andre97","fulghesu@gmail.it","98765");
        User user3 = new User ("Giorgio","Fragazzi","Gio34","giorgino@gmail.com","giorgio1234");

        users.add(admin);
        users.add(user1);
        users.add(user2);
        users.add(user3);


        return this.users;
    }

    public User getUser(String username, String password){
        List<User> users = this.getUsers();
        for(User u : users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }
    public boolean findUserByName (String username) {
        List<User> users = this.getUsers();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByName (String name) {
        List<User> users = this.getUsers();
        for (User u : users) {
            if (u.getUsername().equals(name)) {
                return u;
            }
        }
        return null;
    }

    public void addUser(User user){

        this.users.add(user);
    }

    public void printUsers() {

        for (User u : this.users) {
            System.out.println(u.getUsername());
        }
    }

}
