package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    final int classValue = 3;
    Dialog plotDialog;
    ArrayList<Chapter> chapters = ChapterFactory.getInstance().getChapters();
    User user;
    int bookId;
    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView;

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
        setContentView(R.layout.drawer_chapterlist);
        drawer = findViewById(R.id.drawerChapterList);
        plotDialog = new Dialog(this);


        Intent intent = getIntent();

        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        /***if (obj != null) {
            bookId = (int) obj;
        }***/
        try {
            bookId = userSession.getBookId();
            Book book = BookFactory.getInstance().getBookById(bookId);
        } catch (NullPointerException e) {
            System.out.println("Errore passaggio bookId in sessione.");
            startActivity(new Intent (getApplicationContext(), Home.class));
        }
        System.out.println("Utente Loggato " + user.getNome()+ " " + user.getCognome());



        System.out.println("Views del libro sono in chapter list : "+BookFactory.getInstance().getBookById(bookId).getViews());



        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.chapterlistbar);
        setSupportActionBar(toolbar);
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_menu_chapterlist);
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

        final ListView chapterList = findViewById(R.id.chapterlist);
        getSupportActionBar().setTitle("Capitoli: " + BookFactory.getInstance().getBookById(bookId).getTitle());


        CustomChapterAdapter adapter = new CustomChapterAdapter(this, R.layout.chapteritem, chapters);
        chapters.clear();
        chapters = ChapterFactory.getInstance().getChaptersByBookId(bookId);
        adapter.clear();
        adapter.addAll(chapters);
        adapter.notifyDataSetChanged();
        chapterList.setAdapter(adapter);

        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent readBook = new Intent(ChapterList.this, LeggiLibro.class);
                userSession.setCallingActivity(classValue);
                userSession.setChapId(position + 1);
                startActivity(readBook);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bookPlot:
                Intent seePlot = new Intent(ChapterList.this, PlotPopUp.class);
                seePlot.putExtra("bookId", bookId);
                seePlot.putExtra("User", user);
                startActivity(seePlot);
                break;
            case R.id.menuprofilo:
                Intent myProfile = new Intent(ChapterList.this, MyProfile.class);
                myProfile.putExtra("User",user);
                myProfile.putExtra("riferimento",1);
                myProfile.putExtra("bookId",bookId);
                startActivity(myProfile);
                break;
            case R.id.report:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 1);
        if (callingActivity != null) {
            Intent goBack = new Intent(getApplicationContext(), callingActivity);
            startActivity(goBack);
        }
    }
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
