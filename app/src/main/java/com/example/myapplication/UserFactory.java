package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    private static UserFactory singleton;


    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> usersModified = new ArrayList<>();
    private ArrayList<User> usersLikeSession = new ArrayList<>();

    User user1 = new User ("Faber", "Sau", "Faber123", "fabrizio@gmail.com", "12345", User.Sesso.MALE);
    User user2 = new User ("Andre","Fulghesu","Andre97","fulghesu@gmail.it","98765", User.Sesso.MALE);
    User user3 = new User ("Giorgio","Fragazzi","Gio34","giorgino@gmail.com","giorgio1234", User.Sesso.UNDEFINED);
    User user4 = new User ("Gianna", "Vincenzini", "GiaNan66", "gianna@nan.nini.dark", "13579", User.Sesso.FEMALE);


    private UserFactory(){
    }

    public static UserFactory getInstance(){
        if(singleton == null){
            singleton = new UserFactory();
        }
        return singleton;
    }

    public ArrayList<User> getUsers(){
        ///User admin = new User ("Admin", null, "Admin", null, null);
        users.clear();

        ///users.add(admin);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        if (!usersModified.isEmpty()){
            for (int i =0; i<usersModified.size();i++) {
                for (int k = 0; k < users.size(); k++) {
                    if (users.get(k).getUsername().equals(usersModified.get(i).getUsername())) {
                        users.remove(k);
                        users.add(k, usersModified.get(i));
                    }
                }
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

    public void addUserModifiedLike (User u){

        this.usersModified.add(u);

    }

}
