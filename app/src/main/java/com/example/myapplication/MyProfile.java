package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.Serializable;

public class MyProfile extends AppCompatActivity {

    User user;
    TextView nome_cognome,email,username,password;
    int riferimento,bookId;
    CheckBox show;
    private static int contator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.myprofilebar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Il mio Profilo");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        Intent intent = getIntent();
        Serializable objUser = intent.getSerializableExtra("User");
        Serializable rifer = intent.getSerializableExtra("riferimento");
        Serializable obj3 = intent.getSerializableExtra("booId");

        final UserSession userSession = new UserSession(this);
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        if (rifer != null){
            riferimento = (int)rifer;
            System.out.println(riferimento);
        }

        if (obj3 !=null){
            bookId = (int)obj3;
        }


        nome_cognome = findViewById(R.id.NomeCognome);
        email = findViewById(R.id.Email);
        username = findViewById(R.id.UsernameProfile);
        password = findViewById(R.id.PasswordProfile);
        show = findViewById(R.id.showPass);

        nome_cognome.setText(user.getNome() + " " + user.getCognome());
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        password.setText(user.getPassword());


        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (riferimento) {

                    case 0:
                        Intent goBack0 = new Intent(MyProfile.this, Catalogo.class);
                        goBack0.putExtra("User", user);
                        startActivity(goBack0);
                        break;

                    default:

                        Intent goBack1 = new Intent(MyProfile.this, Home.class);
                        goBack1.putExtra("User", user);
                        startActivity(goBack1);
                        break;


                }

            }
        });


    }
}
