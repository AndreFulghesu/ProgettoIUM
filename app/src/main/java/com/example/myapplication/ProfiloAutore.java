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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ProfiloAutore extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView nomeCognome, usernameAuthor,visiteLibro, valutazioneUtente;
    User user_author;
    ListView lista_libri;
    ImageView star;
    private ArrayList<Book> libri_autore ;
    final int classValue = 2;
    private float valutation;
    private int num_visual;

    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView, navHeader;
    ImageView profileImage;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final UserSession userSession = new UserSession(this);
        user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());

        /**Gestione del tema dell'applicazione**/
        if (userSession.getTheme() == false) {
            setTheme(R.style.AppTheme);
            System.out.println("TEMA NORMALE");
        } else {
            setTheme(R.style.darkTheme);
            System.out.println("TEMA SCURO");
        }

        setContentView(R.layout.drawer_authorprofile);
        drawer = findViewById(R.id.drawerAuthorProfile);

        /**Gestione del layout della Toolbar**/

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.authorToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profilo Autore");
        if (userSession.getTheme() == false) {
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
        navigationView = (NavigationView) findViewById(R.id.nav_menu_profilo_autore);
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
                    Intent changeTheme = new Intent (getApplicationContext(), ProfiloAutore.this.getClass());
                    startActivity(changeTheme);
                }
                else {
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.setTheme(false);
                    Intent changeTheme = new Intent (getApplicationContext(), ProfiloAutore.this.getClass());
                    startActivity(changeTheme);
                }
            }
        });
        /**Fine gestione switch per il cambio tema**/

        navHeader = navigationView.getHeaderView(0);
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

        nomeCognome = findViewById(R.id.NomeCognome);
        usernameAuthor = findViewById(R.id.UsernameAuthor);
        lista_libri = findViewById(R.id.listaLibri);
        valutazioneUtente = findViewById(R.id.valutazioneUtente);
        visiteLibro = findViewById(R.id.visiteLibro);
        star = findViewById(R.id.stella_valutazione);


        try {
            user_author = UserFactory.getInstance().getUserByUsername(userSession.getUsernameAuthor());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        libri_autore = BookFactory.getInstance().getBookByUser(user_author);

        valutation= BookFactory.getInstance().getValutationTotalBookUser(user_author);
        nomeCognome.setText(user_author.getNome()+user_author.getCognome());
        usernameAuthor.setText(user_author.getUsername());
        valutazioneUtente.setText(""+roundDown5(valutation));
        setStarColor(valutation,star);

        visiteLibro.setText(""+sommaView(libri_autore));



        /**Gestione dell'adapter per la ListView dei libri**/

        libri_autore.clear();
        BookAdapterSearch adapter = new BookAdapterSearch(ProfiloAutore.this, R.layout.book_searched, libri_autore);
        libri_autore = BookFactory.getInstance().getBookByUser(user_author);
        adapter.clear();
        adapter.addAll(libri_autore);
        adapter.notifyDataSetChanged();
        lista_libri.setAdapter(adapter);


        /**Gestione del comportamento del sistema alla pressione da parte
         * dell'utente su un elemento della ListView**/
        lista_libri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book bk = (Book) lista_libri.getItemAtPosition(position);
                Intent readBook = new Intent (ProfiloAutore.this, ChapterList.class);
                userSession.setCallingActivity(classValue);
                userSession.setBookId(bk.getId());
                startActivity(readBook);
            }
        });


    }


    public static double roundDown5(float d) {
        return Math.floor(d * 1e2) / 1e2;
    }

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


    /**Gestione del cambio di activity quando l'utente preme il tasto indietro**/
    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 1);
        if (callingActivity != null) {
            Intent goBack = new Intent(this.getApplicationContext(), callingActivity);
            startActivity(goBack);
        }
    }

    public int sommaView (ArrayList<Book> libri){

        int views=0;
        for (Book b: libri){

            views = views + b.getViews();
        }

        return views;

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
