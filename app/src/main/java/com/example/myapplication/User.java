package com.example.myapplication;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private String citta;
    private String dataDiNascita;


    public User (String username, String password, String citta, String dataDiNascita) {
        this.setUsername(username);
        this.setPassword(password);
        this.setCitta(citta);
        this.setDataDiNascita(dataDiNascita);
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

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(String dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }
}
