package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class ContinuaLettura extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    static int classValue = 8;

    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    MaterialSearchView searchView;
    View actionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**Gestione richiesta sessione dalla classe**/
        final UserSession userSession = new UserSession(this);

        /**Gestione del tema dell'applicazione**/
        if (userSession.getTheme() == false) {
            setTheme(R.style.AppTheme);
            System.out.println("TEMA NORMALE");
        } else {
            setTheme(R.style.darkTheme);
            System.out.println("TEMA SCURO");
        }
        setContentView(R.layout.drawer_continua_lettura);

        drawer = findViewById(R.id.drawerContinuaLettura);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.continuaLetturaToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Continua a leggere...");
        if (userSession.getTheme() == false) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));

        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);

        /**Gestione del sistema nel caso in cui non esista la sessione**/
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }
        /**Gestione dello switch per il cambio tema dell'applicazione, presente nel menu laterale**/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_menu_continua_lettura);
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
                    Intent changeTheme = new Intent (getApplicationContext(), ContinuaLettura.this.getClass());
                    startActivity(changeTheme);
                }
                else {
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.setTheme(false);
                    Intent changeTheme = new Intent (getApplicationContext(), ContinuaLettura.this.getClass());
                    startActivity(changeTheme);
                }
            }
        });
        /**Fine gestione switch per il cambio tema**/

        final ListView continueLst= findViewById(R.id.continuebooklist);
        ArrayList<Pair<Book, Chapter>> books = user.getListaLibriIziziati();
        ContinuaLetturaAdapter adapter = new ContinuaLetturaAdapter(this, R.layout.continua_lettura_item, books);
        continueLst.setAdapter(adapter);

        continueLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pair<Book, Chapter>  element = (Pair<Book, Chapter>) continueLst.getItemAtPosition(position);
                Intent readBook = new Intent (getApplicationContext(), LeggiLibro.class);
                userSession.setCallingActivity(classValue);
                userSession.setBookId(element.first.getId());
                userSession.setChapId(element.second.getChaptNum());
                startActivity(readBook);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu3, menu);
        return true;
    }
    /**Gestione del comportamento del sistema alla pressione da parte
     * dell'utente su un elemento del menu nella toolbar**/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuprofilo) {
            Intent myProfile = new Intent(getApplicationContext(), MyProfile.class);
            myProfile.putExtra("riferimento", 0);
            startActivity(myProfile);
        }
        return super.onOptionsItemSelected(item);
    }
    /**Gestione del comportamento del sistema alla pressione di uno
     * degli elementi del menu laterale**/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_report:
                break;
            case R.id. nav_darkmode:
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