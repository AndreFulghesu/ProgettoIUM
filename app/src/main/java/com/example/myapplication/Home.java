package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageView;

import java.io.Serializable;

public class Home extends AppCompatActivity
{
    DrawerLayout drawer;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_home);


        drawer = findViewById(R.id.drawerHome);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.catalogoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HomePage");
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


        final UserSession userSession = new UserSession(this);
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }
        System.out.println("Utente nello Shared " + userSession.getUserSession());

        ImageView continuaLettura = findViewById(R.id.continuaLettura);
        ImageView catalogo = findViewById(R.id.catalogo);
        ImageView myProfile = findViewById(R.id.myProfile);
        //ImageView logout = findViewById(R.id.homeLogout);

        /**continuaLettura.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent continuaLettura = new Intent(Home.this, LeggiLibro.class);
        startActivity(continuaLettura);
        }
        });**/

        System.out.println("Utente Loggato " + user.getNome()+ " " + user.getCognome());


        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCatalogo = new Intent(Home.this, Catalogo.class);
                startActivity(gotoCatalogo);
            }
        });
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Home.this, MyProfile.class);
                profile.putExtra("riferimento",1);
                startActivity(profile);

            }
        });
        /*
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent login = new Intent(Home.this, Login.class);
                userSession.invalidateSession();
                startActivity(login);
            }
        });

         */


    }
}
