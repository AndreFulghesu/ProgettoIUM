package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;

import java.io.Serializable;

public class Home extends AppCompatActivity
{

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Serializable objUser = intent.getSerializableExtra("User");

        if (objUser!= null) {
            user = (User) objUser;
        }

        Button continuaLettura = findViewById(R.id.continuaLettura);
        Button catalogo = findViewById(R.id.catalogo);
        Button myBook = findViewById(R.id.myLibro);
        Button logout = findViewById(R.id.homeLogout);

        /**continuaLettura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent continuaLettura = new Intent(Home.this, LeggiLibro.class);
                startActivity(continuaLettura);
            }
        });**/
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCatalogo = new Intent(Home.this, Catalogo.class);
                gotoCatalogo.putExtra("User", user);
                startActivity(gotoCatalogo);
            }
        });
        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent login = new Intent(Home.this, Login.class);
                startActivity(login);
            }
        });


        }
}
