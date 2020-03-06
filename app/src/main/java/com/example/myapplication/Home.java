package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button continuaLettura = findViewById(R.id.continuaLettura);
        Button catalogo = findViewById(R.id.catalogo);
        Button myBook = findViewById(R.id.myLibro);

        continuaLettura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent continuaLettura = new Intent(Home.this, LeggiLibro.class);
                startActivity(continuaLettura);
            }
        });
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        }
}
