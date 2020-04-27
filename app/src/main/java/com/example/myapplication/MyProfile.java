package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class MyProfile extends AppCompatActivity {

    User user;
    TextView nome_cognome,email,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.myprofilebar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Il mio Profilo");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(MyProfile.this, Home.class);
                goBack.putExtra("User", user);
                startActivity(goBack);
            }
        });

        Intent intent = getIntent();
        Serializable objUser = intent.getSerializableExtra("User");

        if (objUser!= null) {
            user = (User) objUser;
        }

        nome_cognome = findViewById(R.id.NomeCognome);
        email = findViewById(R.id.Email);
        username = findViewById(R.id.UsernameProfile);

        nome_cognome.setText(user.getNome() + " " + user.getCognome());
        username.setText(user.getUsername());
        email.setText(user.getEmail());


    }
}
