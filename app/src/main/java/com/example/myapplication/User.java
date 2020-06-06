package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.core.util.Pair;

public class User implements Serializable {

    /** Definizione attributi della classe */
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Sesso sex;
    private ArrayList<Pair<Book, Chapter>> continuaLetturaList = new ArrayList<>();
    private HashMap<Comment,Boolean> commentiLike = new HashMap<>();

    /**Definizione costruttore*/
    public User (String nome, String cognome, String username, String email, String password, Sesso sex) {
        this.setUsername(username);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);
        this.setPassword(password);
        this.setSex(sex);

    }
    /** Definizione metodi getter e setter dei vari attributi*/
    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void setNome(String nome) {this.nome = nome;}

    public String getNome () { return this.nome; }

    private void setEmail(String email) { this.email = email; }

    String getEmail() { return this.email; }


    private void setCognome(String cognome) { this.cognome = cognome; }

    String getCognome() { return this.cognome; }

    void addLibroIniziato(Book bk, Chapter chap) {
        boolean check = false;
        Pair<Book, Chapter> pair = new Pair<>(bk, chap);
        for (int i = 0; i < continuaLetturaList.size(); i++) {
            if (bk.getId() == continuaLetturaList.get(i).first.getId()) {
                continuaLetturaList.remove(i);
                continuaLetturaList.add(pair);
                check = true;
            }
        }
        if (!check) {
            continuaLetturaList.add(pair);
        }
    }

    ArrayList<Pair<Book, Chapter>> getListaLibriIziziati(){
        return continuaLetturaList;
    }

    void addLikeComments(Comment c, Boolean b){
        this.commentiLike.put(c,b);
        UserFactory.getInstance().addUserModifiedLike(this);
    }

    Boolean getLike(Comment c) {
        if (!this.commentiLike.isEmpty()) {
            return this.commentiLike.get(c);
        }
        return false;
    }

    public HashMap<Comment,Boolean> getMapLikes (){ return this.commentiLike; }

    public void setMapLike (HashMap<Comment,Boolean> nuoviLike){ this.commentiLike =nuoviLike; }

    public void setSex(Sesso sex) {
        this.sex = sex;
    }

    public Sesso getSex() {
        return sex;
    }

    public enum Sesso {
        MALE, FEMALE, UNDEFINED
    }
}
