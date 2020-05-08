package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    DrawerLayout drawer;
    private static int contator;
    final int classValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final UserSession userSession = new UserSession(this);
        if (userSession.getTheme() == false) {
            setTheme(R.style.AppTheme);
            System.out.println("TEMA NORMALE");
        } else {
            setTheme(R.style.darkTheme);
            System.out.println("TEMA SCURO");

        }
        setContentView(R.layout.drawer_profile);

        drawer = findViewById(R.id.drawerProfile);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.myprofilebar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Il mio Profilo");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        if (userSession.getTheme() == false) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }


        getSupportActionBar().setTitle("Mio Profilo");
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
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




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                Intent intent = new Intent (MyProfile.this, Home.class);
                UserSession uSes = new UserSession(getApplicationContext());
                uSes.setCallingActivity(classValue);
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent myProfile = new Intent (MyProfile.this, MyProfile.class);
                UserSession uSes2 = new UserSession(getApplicationContext());
                startActivity(myProfile);
                break;
            case R.id.report:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
