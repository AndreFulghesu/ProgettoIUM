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

        User user1 = new User ("Faber", "1234", "Cagliari", "01/01/1111");
        User user2 = new User ("User", "1234", "Cagliari", "01/01/1111");
        User user3 = new User ("User2", "1234", "Cagliari", "01/01/1111");
        User user4 = new User ("User3", "1234", "Cagliari", "01/01/1111");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

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

