package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.core.util.Pair;

public class User implements Serializable {

    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private ArrayList<Pair<Book, Chapter>> continuaLetturaList = new ArrayList<>();
    private HashMap<Comment,Boolean> commentiLike = new HashMap<>();

    public User (String nome, String cognome, String username, String email, String password ) {
        this.setUsername(username);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);
        this.setPassword(password);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome (String nome) {this.nome = nome;}

    public String getNome () { return this.nome; }

    public void setEmail (String email) { this.email = email; }

    public String getEmail () { return this.email; }

    public void setCognome (String cognome) { this.cognome = cognome; }

    public String getCognome () { return this.cognome; }

    public void addLibroIniziato (Book bk, Chapter chap) {
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

    public ArrayList<Pair<Book, Chapter>> getListaLibriIziziati(){
        return continuaLetturaList;
    }

    /*
    public void setCommentiLike (){

        ArrayList<Comment> elenco_Commenti = CommentFactory.getInstance().getComments();
        for (Comment c : elenco_Commenti){
            this.commentiLike.put(c,false);
        }

    }

     */

    public void addLike (Comment c,Boolean b){

        this.commentiLike.put(c,b);
    }

    /*
    public void deleteLike (Comment c){
        this.commentiLike.remove(c);
        UserFactory.getInstance().addUserModified(this);
    }

     */



    public boolean getLike (Comment c) {

        if( !this.commentiLike.isEmpty()) {
            return this.commentiLike.get(c);
        }

        return false;
    }

    public HashMap<Comment,Boolean> getMapLikes (){ return this.commentiLike; }
    public void setMapLike (HashMap<Comment,Boolean> nuoviLike){ this.commentiLike =nuoviLike; }
}
