package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registrazione extends AppCompatActivity {

    public static final String USER_EXTRA ="com.example.faber.bonusIum";

    EditText nome,cognome,email,password,second_password,username;
    Button registrati;
    User user;
    String nome1,cognome1,email1,password1,second_password1,username1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registazione);


        nome = findViewById(R.id.NomeReg1);
        cognome = findViewById(R.id.CognomeReg2);
        email = findViewById(R.id.EmailReg2);
        password = findViewById(R.id.PassworReg2);
        second_password = findViewById(R.id.ConfermaPassworReg2);
        username = findViewById(R.id.UsernameReg2);
        registrati = findViewById(R.id.buttonRegistrazione);


        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInput()) {

                    nome1 = nome.getText().toString();
                    cognome1 = cognome.getText().toString();
                    email1 = email.getText().toString();
                    password1 = password.getText().toString();
                    username1 = username.getText().toString();

                    user = new User (nome1,cognome1,username1,email1,password1);

                    Intent login = new Intent(Registrazione.this, Login.class);
                    login.putExtra(USER_EXTRA, user);
                    startActivity(login);
                } else {
                    password.setError("Le due password non corrispondono.");
                }

            }

        });

    }

    public boolean checkInput() {
        int errors = 0;

        if (nome.getText() == null | nome.getText().length()==0) {
            errors++;
            nome.setError("Inserire uno Nome.");
        }

        if (cognome.getText() == null | cognome.getText().length()==0) {
            errors++;
            cognome.setError("Inserire un Cognome.");
        }
        if (password.getText() == null | second_password.getText().length() == 0) {
            errors++;
            password.setError("Inserire una password.");
        }
        if (second_password.getText() == null | second_password.getText().length() == 0) {
            errors++;
            second_password.setError("Confermare la password.");
        }

        return errors == 0;
    }
}