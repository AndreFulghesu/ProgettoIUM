package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class Registrazione extends AppCompatActivity {

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    EditText nome,cognome,email,password,second_password,username;
    Button registrati;
    TextView alertPass;
    CheckBox terminiServizio;
    RadioGroup radioGroupRegistrazione;
    RadioButton lastRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registazione);

        /**Associazioni variabili con elementi del layout*/
        nome = findViewById(R.id.NomeReg1);
        cognome = findViewById(R.id.CognomeReg2);
        email = findViewById(R.id.EmailReg2);
        password = findViewById(R.id.PassworReg2);
        second_password = findViewById(R.id.ConfermaPassworReg2);
        username = findViewById(R.id.UsernameReg2);
        registrati = findViewById(R.id.buttonRegistrazione);
        alertPass = findViewById(R.id.alertPass);
        terminiServizio = findViewById(R.id.terminiServizio);
        radioGroupRegistrazione = findViewById(R.id.radiogroup_registrazione);
        lastRadioButton = findViewById(R.id.undefined_radiobutton);

        /**Gestione richiesta creazione nuovo utente con
         * controllo sull'input utente
         */
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!terminiServizio.isChecked()){
                    Snackbar.make(v, "Devi accettare i termini di Servizio", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    alertPass.setVisibility(View.GONE);
                }else {
                    if (checkUsername()) {
                        username.setError("Lo username esiste già");
                    } else {
                        if (!checkInput()) {
                            Snackbar.make(v, "Attenzione!\n Tutti i campi devono essere compilati", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            alertPass.setVisibility(View.GONE);
                        } else {
                            if (checkPassword()) {
                                Snackbar.make(v, "La richiesta di registrazione è stata inoltrata.\nControlla la tua casella email", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                alertPass.setVisibility(View.GONE);
                            } else {
                                alertPass.setError("Le password devono corrispondere");
                            }
                        }
                    }
                }
            }
        });
    }

    /**Metodi ausuliari per il controllo dell'input utente*/
    public boolean checkInput() {
        int errors = 0;

        if (nome.getText() == null | nome.getText().length()==0) {
            errors++;
            nome.setError("Inserire uno Nome.");
        }
        if (email.getText() == null | email.getText().length()==0){
            errors++;
            email.setError("Inserire una mail valida.");
        }
        if (username.getText() == null | username.getText().length()==0){
            errors++;
            username.setError("Inserire uno username.");
        }
        if (cognome.getText() == null | cognome.getText().length()==0) {
            errors++;
            cognome.setError("Inserire un Cognome.");
        }
        if (radioGroupRegistrazione.getCheckedRadioButtonId() != R.id.male_radiobutton || radioGroupRegistrazione.getCheckedRadioButtonId() != R.id.female_radiobutton ||
                radioGroupRegistrazione.getCheckedRadioButtonId() != R.id.undefined_radiobutton) {
            errors++;
            lastRadioButton.setError("Selezionare il proprio sesso.");
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

    public boolean checkPassword (){
        return password.getText().toString().equals(second_password.getText().toString());
    }

    public boolean checkUsername (){
        List<User> users = UserFactory.getInstance().getUsers();
        for(User u : users){
            if (u.getUsername().equals(username.getText().toString())){
                return true;
            }
        }
        return false;
    }
}