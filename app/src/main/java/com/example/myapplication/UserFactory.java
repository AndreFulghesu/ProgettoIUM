package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    private static UserFactory singleton;


    private List<User> users = new ArrayList<>();
    private List<User> usersModified = new ArrayList<>();

    User user1 = new User ("Faber", "Sau", "Faber123", "fabrizio@gmail.com", "12345");
    User user2 = new User ("Andre","Fulghesu","Andre97","fulghesu@gmail.it","98765");
    User user3 = new User ("Giorgio","Fragazzi","Gio34","giorgino@gmail.com","giorgio1234");


    private UserFactory(){
    }

    public static UserFactory getInstance(){
        if(singleton == null){
            singleton = new UserFactory();
        }
        return singleton;
    }

    public List<User> getUsers(){

        ///User admin = new User ("Admin", null, "Admin", null, null);
        users.clear();

        ///users.add(admin);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        for (int i =0; i<usersModified.size();i++){
            if (users.get(i).getUsername().equals(usersModified.get(i).getUsername())) {
                users.remove(i);
                users.add(usersModified.get(i));
            }
        }

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

    public User getUserByUsername (String name) {
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
    public void addLibroIniziato (User user, Book bk,  Chapter chap) {
        boolean check = false;
        //l'utente passato come parametro aggiunge il nuovo libro nella lista
        user.addLibroIniziato(bk, chap);

        //se l'utente passato come argomento trova riscrontro nella lista allora effettua modifica
        for (int i =0 ; i<usersModified.size();i++){
            if (user.getUsername().equals(usersModified.get(i).getUsername())){
                usersModified.remove(i);
                usersModified.add(user);
                check = true;
            }
        }

        //altrimento l'utente semplicemente aggiunto nella lista
        if (!check) {
            usersModified.add(user);
        }
    }

}
