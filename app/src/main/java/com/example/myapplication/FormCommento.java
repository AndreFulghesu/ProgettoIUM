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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class FormCommento extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    final int classValue = 6;
    int bookId, chapId;
    User user;
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
        setContentView(R.layout.drawer_formcommento);
        drawer = findViewById(R.id.drawerFormCommento);

        Intent intent = getIntent();
        Serializable objBook = intent.getSerializableExtra("bookId");
        Serializable objChap = intent.getSerializableExtra("chapId");
        Serializable objUser = intent.getSerializableExtra("User");
        try {
            bookId = userSession.getBookId();
            Book thisBook = BookFactory.getInstance().getBookById(bookId);
            try {
                chapId = userSession.getChapId();
                Chapter thisChap = ChapterFactory.getInstance().getChapterByChapNum(chapId, bookId);
            } catch (NullPointerException e) {
                Intent goToChapterList = new Intent (getApplicationContext(), ChapterList.class);
                startActivity(goToChapterList);
            }
        } catch (NullPointerException ex) {
            Intent goToCatalogo = new Intent (getApplicationContext(), Catalogo.class);
            startActivity(goToCatalogo);
        }
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }


        final RatingBar bar = findViewById(R.id.ratingBar);
        final EditText feedbackMessage = findViewById(R.id.feedbackMessage);
        Button feedbackSubmit = findViewById(R.id.feedbackSubmit);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.formcommentobar);
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
        getSupportActionBar().setTitle(BookFactory.getInstance().getBookById(bookId).getTitle());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_menu_formcommento);
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





        feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vote = bar.getNumStars();
                System.out.println("Voto nel form commento:  "+vote);
                String feedback = feedbackMessage.getText().toString();
                Comment comment = new Comment(feedback, vote, chapId, bookId, user,false);
                CommentFactory.getInstance().addComment(comment);
                ChapterFactory.getInstance().getChapterByChapNum(chapId,bookId).addComment(comment);
                ChapterFactory.getInstance().getChapterByChapNum(chapId,bookId).setValutation();
                userSession.setCallingActivity(classValue);
                Intent feedbacks = new Intent(FormCommento.this, CommentList.class);
                startActivity(feedbacks);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuprofilo) {
            Intent intent1 = new Intent(FormCommento.this, MyProfile.class);
            startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 2);
        if (callingActivity != null) {
            Intent goBack = new Intent(getApplicationContext(), callingActivity);
            startActivity(goBack);
        }

    }@Override
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
