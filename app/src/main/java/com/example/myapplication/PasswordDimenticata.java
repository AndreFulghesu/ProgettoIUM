package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class PasswordDimenticata extends AppCompatActivity {

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    Button richiestaPass;
    EditText email,telephoneNumber;
    TextView alertPass;
    private boolean permission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_dimenticata);

        /**Associazioni variabili con elementi del layout*/
        richiestaPass = findViewById(R.id.richiestaPassword);
        email = findViewById(R.id.richiestaEmail);
        telephoneNumber = findViewById(R.id.numeroTelefono);
        alertPass = findViewById(R.id.alertPass);

        /**Gestione richiesta nuova password con
         * controllo sull'input utente
         */
        richiestaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    Snackbar.make(v, "Attenzione!\n Tutti i campi devono essere compilati", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    alertPass.setVisibility(View.GONE);
                }else{
                    if (checkEmail(email.getText().toString())){

                        Snackbar.make(v, "La richiesta è stata inoltrata.\nControlla la tua casella email", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        permission = true;
                        alertPass.setVisibility(View.GONE);
                    }else {
                        email.setError("L'email inserita non corrisponde a nessun utente");
                    }
                }
            }
        });
    }

    /**Metodi ausuliari per il controllo dell'input utente*/
    private Boolean checkInput() {
        int errors = 0;

        if (email.getText() == null || email.getText().length() == 0) {
            errors++;
            email.setError("Inserisci Email");
        } else {
            email.setError(null);
        }

        if (telephoneNumber.getText() == null || telephoneNumber.getText().length() == 0) {
            errors++;
            telephoneNumber.setError("Inserisci il Numero di Telefono");
        } else {
            telephoneNumber.setError(null);
        }
        return (errors==0);
    }

    private boolean checkEmail (String email){

        List<User> users = UserFactory.getInstance().getUsers();
        for(User u : users){
            if (u.getEmail().equals(email.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    /**Gestione dell'uscita dall'applicazione nel caso in cui si torni indietro**/
    @Override
    public void onBackPressed() {

        if(!permission){
            new AlertDialog.Builder(PasswordDimenticata.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("(Y)ouBook")
                    .setMessage("Sei sicuro di voler tornare indietro senza procedere?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }else{
            Intent goLogin = new Intent(PasswordDimenticata.this, Login.class);
            startActivity(goLogin);
        }

    }
}
