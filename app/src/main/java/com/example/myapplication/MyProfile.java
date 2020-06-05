package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.Objects;

public class MyProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    User user;
    TextView nome_cognome,email,username,password,averageUser;
    ImageView starAverageUser, myProfileImage;
    CheckBox show;
    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView, navHeader;
    ImageView profileImage;
    TextView welcomeHeader;

    final int classValue = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Gestione richiesta sessione dalla classe
         * ed eventualmente gestione dell'eccezione lanciata**/
        final UserSession userSession = new UserSession(this);
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        /**Gestione del tema dell'applicazione**/
        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }

        setContentView(R.layout.drawer_profile);

        drawer = findViewById(R.id.drawerProfile);

        /**Gestione del layout della Toolbar**/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.myprofilebar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Il mio Profilo");
        if (!userSession.getTheme()) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }

        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        /**Gestione dello switch per il cambio tema dell'applicazione, presente nel menu laterale**/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_menu_myprofile);
        navigationView.setNavigationItemSelectedListener(this);
        drawerMenu = navigationView.getMenu();
        menuItem = drawerMenu.findItem(R.id.nav_darkmode);
        actionView = MenuItemCompat.getActionView(menuItem);

        dmSwitch = actionView.findViewById(R.id.darkmode_switch);
        if (userSession.getTheme()){
            dmSwitch.setChecked(true);
        } else {
            dmSwitch.setChecked(false);
        }
        dmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userSession.getTheme()){
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.setTheme(true);
                    Intent changeTheme = new Intent (getApplicationContext(), userSession.getActivityFromValue(classValue));
                    startActivity(changeTheme);
                }
                else {
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.setTheme(false);
                    Intent changeTheme = new Intent (getApplicationContext(), userSession.getActivityFromValue(classValue));
                    startActivity(changeTheme);
                }
            }
        });
        /**Fine gestione switch per il cambio tema**/

        /**Gestione visualizzazione immagine del profilo nello header del drawerMenu*/
        navHeader = navigationView.getHeaderView(0);
        welcomeHeader = navHeader.findViewById(R.id.welcomeHeader);
        welcomeHeader.setText("Ciao, "+ user.getNome() + "!");
        profileImage = navHeader.findViewById(R.id.headerProfileImg);
        switch (user.getSex()){
            case MALE:
                profileImage.setImageResource(R.drawable.bananaicon);
                break;
            case FEMALE:
                profileImage.setImageResource(R.drawable.peachicon);
                break;
            case UNDEFINED:
                profileImage.setImageResource(R.drawable.blackholeicon);
                break;
            default:
                profileImage.setImageResource(R.drawable.ic_person_black_24dp);
        }

        /**Associazione variabili d'istanza con elementi del layout*/
        nome_cognome = findViewById(R.id.NomeCognome);
        email = findViewById(R.id.Email);
        username = findViewById(R.id.UsernameProfile);
        password = findViewById(R.id.PasswordProfile);
        show = findViewById(R.id.showPass);
        averageUser = findViewById(R.id.averageUser);
        starAverageUser = findViewById(R.id.starAverageUser);
        myProfileImage = findViewById(R.id.myProfileImg);

        /**Popolamento dei form della pagina con gli appositi dati,
         * prelevati dall'utente salvato in sessione
         */
        nome_cognome.setText(user.getNome() + " " + user.getCognome());
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        float valutation= BookFactory.getInstance().getValutationTotalBookUser(user);
        averageUser.setText(""+roundDown5(valutation));
        switch (user.getSex()){
            case MALE:
                myProfileImage.setImageResource(R.drawable.bananaicon);
                break;
            case FEMALE:
                myProfileImage.setImageResource(R.drawable.peachicon);
                break;
            case UNDEFINED:
                myProfileImage.setImageResource(R.drawable.blackholeicon);
                break;
            default:
                myProfileImage.setImageResource(R.drawable.ic_person_black_24dp);
        }
        setStarColor(valutation,starAverageUser);

        /**Gestione della visibilità della password in base all'input utente
         * sulla checkBox
         */
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

    /**Settaggio colore immagine stella in base al punteggio totale dell'utente*/
    public void setStarColor (float valutation, ImageView star){
        if(valutation==5){
            star.setColorFilter(getResources().getColor(R.color.blue));

        }else {
            if (valutation > 4.2f) {
                star.setColorFilter(getResources().getColor(R.color.green));
            } else {
                if (valutation >= 3.2f && valutation <= 4.2f) {
                    star.setColorFilter(getResources().getColor(R.color.yellow));
                } else {
                    if (valutation > 2f && valutation < 3.2f) {
                        star.setColorFilter(getResources().getColor(R.color.orange));
                    } else {
                        if (valutation > 0f && valutation < 2) {
                            star.setColorFilter(getResources().getColor(R.color.red));
                        }
                    }
                }
            }
        }
    }

    public static double roundDown5(float d) {
        return Math.floor(d * 1e2) / 1e2;
    }

    /**Gestione del comportamento del sistema alla pressione di uno
     * degli elementi del menu laterale**/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_report:
                Intent report = new Intent(getApplicationContext(), Report.class);
                startActivity(report);
                break;
            case R.id. nav_darkmode:
                break;
            case R.id.nav_myprofile:
                Intent myProfile = new Intent(getApplicationContext(), MyProfile.class);
                startActivity(myProfile);
                break;
            case R.id.nav_logout:
                Intent logOut = new Intent (getApplicationContext(), Login.class);
                UserSession session = new UserSession(this);
                session.invalidateSession();
                startActivity(logOut);
                break;
            case R.id.nav_aboutus:
                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
        return true;
    }

}
