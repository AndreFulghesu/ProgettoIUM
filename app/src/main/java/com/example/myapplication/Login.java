package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/

    EditText username, password;
    Button accedi;
    String formUsername, formPassword;
    TextView registrazione,passDimenticata;
    User user;
    Boolean debugLogin = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Gestione della sessione nel caso in cui l'utente sia ancora loggato**/


        final UserSession userSession = new UserSession(this);

        if (userSession.isLogged() && UserFactory.getInstance().findUserByName(userSession.getUserSession())) {
            System.out.println("In login " + UserFactory.getInstance().getUserByUsername(userSession.getUserSession()));
            Intent sessionLogin = new Intent(Login.this, Home.class);
            startActivity(sessionLogin);
        }



        /**Gestione elementi interfaccia**/

        username= findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        accedi = findViewById(R.id.accedi);
        registrazione = findViewById(R.id.registrazione);
        passDimenticata = findViewById(R.id.passwordDimenticata);

        /**Gestione controllo degli input dei dati utente in caso di pressione del bottone Accedi**/

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
                            userSession.saveUserSession(formUsername);
                            startActivity(home);
                        } else {
                            username.setText(formUsername = username.getText().toString());
                            password.setText("");
                            password.setError("Password errata.");
                        }
                    } else {
                        username.setError ("Utente non esistente.");
                    }
                }
            }
        });
        /**Gestione camnbio activity alla pressione della TextView Registrazione**/
        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrazione = new Intent(Login.this, Registrazione.class);
                startActivity(registrazione);
            }
        });


        passDimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passDim = new Intent (Login.this, PasswordDimenticata.class);
                startActivity(passDim);

            }
        });


    }
    /**Controllo e gestione di mancato input nei campi username e password**/
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
    /**Gestione dell'uscita dall'applicazione nel caso in cui si torni indietro**/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("(Y)ouBook")
                .setMessage("Sei sicuro di voler uscire dall'applicazione?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
