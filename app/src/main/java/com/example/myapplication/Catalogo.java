package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

public class Catalogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(Registrazione.USER_EXTRA);

        if (obj != null) {
            User newUser = (User) obj;
            UserFactory.getInstance().addUser(newUser);
            System.out.println(newUser.getUsername());
            UserFactory.getInstance().printUsers();
        }

    }
}
