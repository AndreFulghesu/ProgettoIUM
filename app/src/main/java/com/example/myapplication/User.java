package com.example.myapplication;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;



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


}
