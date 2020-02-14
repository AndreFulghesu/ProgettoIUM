package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class Login extends AppCompatActivity {

    public static final String USER_EXTRA ="com.example.faber.bonusIum";

    User user= null;
    EditText username, password;
    Button accedi;
    String formUsername, formPassword;
    TextView registrazione;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(Registrazione.USER_EXTRA);

        if (obj != null) {
            User newUser = (User) obj;
            UserFactory.getInstance().addUser(newUser);
            System.out.println(newUser.getUsername());
            UserFactory.getInstance().printUsers();
        }

        username= findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        accedi = findViewById(R.id.accedi);
        registrazione = findViewById(R.id.registrazione);
        errorText = findViewById(R.id.errortext);

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()){
                    formUsername = username.getText().toString();
                    formPassword = password.getText().toString();

                    if (UserFactory.getInstance().findUserByName(formUsername)) {
                        if (UserFactory.getInstance().getUser(formUsername, formPassword) != null) {
                            user = UserFactory.getInstance().getUser(formUsername, formPassword);
                            Intent home = new Intent(Login.this, Home.class);
                            home.putExtra(USER_EXTRA, user);
                            startActivity(home);
                        } else {
                            password.setError("Password errata.");
                        }
                    } else {
                        errorText.setText ("Utente non esistente.");
                    }
                }
            }
        });
        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrazione = new Intent(Login.this, Registrazione.class);
                startActivity(registrazione);
            }
        });
    }
    private boolean checkInput(){

        int errors=0;

        if (username.getText() == null || username.getText().length()==0) {
            errors++;
            username.setError("Inserisci Username");
        }
        else
            username.setError(null);

        if (password.getText() == null || password.getText().length()==0) {
            errors++;
            password.setError("Inserisci Password");
        }
        else
            password.setError(null);

        return errors==0; // ritorna true se non ci sono errori

    }
}