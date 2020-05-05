package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalogo extends AppCompatActivity {

    final int classValue = 2;
    ArrayList<Book> books= BookFactory.getInstance().getBooks();
    User user;
    DrawerLayout drawer;
    Switch navSwitch;

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

        for (Book b : books){
            System.out.println("Media libro " +b.getAverage());
        }

        setContentView(R.layout.drawer_catalogo);

        drawer = findViewById(R.id.drawerCatalogo);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.catalogoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catalogo Libri");
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
        /**navSwitch = findViewById(R.id.nav_darkmode);
        if (userSession.getTheme() == false) {
            navSwitch.setChecked(false);
        } else {
            navSwitch.setChecked(true);
        }**/
        final ListView lst= findViewById(R.id.booklist);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("User");
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        System.out.println("Utente Loggato " + user.getNome()+ " " + user.getCognome());

        CustomBookAdapter adapter = new CustomBookAdapter(this, R.layout.bookitem, books);
        books.clear();
        books = BookFactory.getInstance().getBooks();
        adapter.notifyDataSetChanged();
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book bk = (Book) lst.getItemAtPosition(position);
                Intent readBook = new Intent (Catalogo.this, ChapterList.class);
                userSession.setCallingActivity(classValue);
                readBook.putExtra("User", user);
                readBook.putExtra("bookId", bk.getId());
                startActivity(readBook);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }
    private void setDarkModeSwitchListener(){
        navSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.setTheme(true);
                    Intent changeTheme = new Intent (getApplicationContext(), Catalogo.this.getClass());
                    startActivity(changeTheme);
                }
                else {
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.setTheme(false);
                    Intent changeTheme = new Intent (getApplicationContext(), Catalogo.this.getClass());
                    startActivity(changeTheme);
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                Intent intent = new Intent (Catalogo.this, Login.class);
                UserSession session = new UserSession(this);
                session.invalidateSession();
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent myProfile = new Intent (Catalogo.this, MyProfile.class);
                myProfile.putExtra("User", user);
                myProfile.putExtra("riferimento",0);
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
        Class callingActivity = userSession.getActivityFromValue(userSession.getCallingActivityValue());
        if (callingActivity != null) {
            Intent goBack = new Intent(this.getApplicationContext(), callingActivity);
            startActivity(goBack);
        }
    }
}
