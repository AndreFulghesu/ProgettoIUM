package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView;
    int problemId;
    User user;
    TextView textSopraBarraRicerca;
    TextView spinnerSelected;
    SearchView searchView;
    ListView reportSearched;
    Book bookSelected;
    String stringSelected;
    Button submitButton;
    User userSelected;
    ArrayList<String> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final UserSession userSession = new UserSession(this);

        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }
        setContentView(R.layout.drawer_report);

        activities.add("Pagina di Login");
        activities.add("Pagina di registrazione");
        activities.add("Homepage");
        activities.add("Catalogo dei libri");
        activities.add("Lista dei capitoli");
        activities.add("Form di lettura");
        activities.add("Lettura a schermo intero");
        activities.add("Form di scrittura commenti");
        activities.add("Pagina di visualizzazione dei commenti");
        activities.add("Pagina di visualizzazione profilo");

        final Spinner reportSpinner = findViewById(R.id.spinner_report);

        spinnerSelected = findViewById(R.id.report_spinner_selected);
        textSopraBarraRicerca = findViewById(R.id.text_sopra_report_search);

        Intent intent = getIntent();
        Serializable objReport = intent.getSerializableExtra("reportId");

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.reportbar);
        setSupportActionBar(toolbar);
        if (userSession.getTheme() == false) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
        drawer = findViewById(R.id.drawerReport);
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
        navigationView = (NavigationView) findViewById(R.id.nav_menu_report);
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
                    Intent changeTheme = new Intent (getApplicationContext(), Report.this.getClass());
                    startActivity(changeTheme);
                }
                else {
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.setTheme(false);
                    Intent changeTheme = new Intent (getApplicationContext(), Report.this.getClass());
                    startActivity(changeTheme);
                }
            }
        });
        /**Fine gestione switch per il cambio tema**/

        if (objReport != null) {
            problemId = (int) objReport;
            switch (problemId){
                case 0:
                    spinnerSelected.setText("Segnalazione: Bug o problemi di sistema");
                    break;
                case 1:
                    spinnerSelected.setText("Segnalazione: Utente Scorretto");
                    break;
                case 2:
                    spinnerSelected.setText("Segnalazione: Violazione di proprietà intellettuale");
                    break;
                case 3:
                    spinnerSelected.setText("Segnalazione: Errori di testo");
                    break;
            }
        }

        ArrayList<String> choices = new ArrayList<>();
        choices.add("Bug o problemi di sistema");
        choices.add("Utente scorretto");
        choices.add("Violazione di proprietà intellettuale");
        choices.add("Errori di testo");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_item, R.id.testoMenu, choices);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        reportSpinner.setAdapter(adapter);

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                switch (str) {
                    case "Bug o problemi di sistema":
                        spinnerSelected.setText("Segnalazione: Bug o problemi di sistema");
                        problemId = 0;
                        break;
                    case "Utente scorretto":
                        problemId = 1;
                        spinnerSelected.setText("Segnalazione: Utente Scorretto");
                        break;
                    case "Violazione di proprietà intellettuale":
                        spinnerSelected.setText("Segnalazione: Violazione di proprietà intellettuale");
                        problemId = 2;
                        break;
                    case "Errori di testo":
                        spinnerSelected.setText("Segnalazione: Errori di testo");
                        problemId = 3;
                        break;
                    default:
                        problemId = -1;
                        spinnerSelected.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reportSearched = findViewById(R.id.report_searched);
        searchView = findViewById(R.id.search_report_item);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (bookSelected != null) {
                    ArrayList<Book> newFiltered = new ArrayList<>();
                    newFiltered.add(bookSelected);
                    BookAdapterSearch adapter = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, newFiltered);
                    reportSearched.setAdapter(adapter);
                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (problemId == 2 || problemId == 3) {
                    ArrayList<Book> filtered = new ArrayList<>();
                    ArrayList<Book> books;
                    books = BookFactory.getInstance().getBooks();
                    if (newText != null && !newText.isEmpty()) {
                        for (Book b : books) {
                            if (b.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                                System.out.println(b.getTitle());
                                filtered.add(b);
                            }
                        }
                        BookAdapterSearch adapter = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, filtered);
                        reportSearched.setAdapter(adapter);
                    } else {
                        BookAdapterSearch adapter = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, filtered);
                        books.clear();
                        books = BookFactory.getInstance().getBooks();
                        adapter.notifyDataSetChanged();
                        reportSearched.setAdapter(adapter);
                    }
                } else
                    if (problemId == 1) {
                        ArrayList<User> filtered = new ArrayList<>();
                        ArrayList<User> users;
                        users = UserFactory.getInstance().getUsers();
                        if (newText != null && !newText.isEmpty()) {
                            for (User u : users) {
                                if (u.getUsername().toLowerCase().contains(newText.toLowerCase())) {
                                    filtered.add(u);
                                }
                            }
                            CustomUserAdapter adapter = new CustomUserAdapter(getApplicationContext(), R.layout.row_username, filtered);
                            reportSearched.setAdapter(adapter);
                        } else {
                            CustomUserAdapter adapter = new CustomUserAdapter(getApplicationContext(), R.layout.row_username, filtered);
                            users.clear();
                            users = UserFactory.getInstance().getUsers();
                            adapter.notifyDataSetChanged();
                            reportSearched.setAdapter(adapter);
                        }
                    } else
                    if (problemId == 0) {
                        ArrayList<String> filtered = new ArrayList<>();
                        if (newText != null && !newText.isEmpty()) {
                            for (String s : activities) {
                                if (s.toLowerCase().contains(newText.toLowerCase())) {
                                    filtered.add(s);
                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.basic_string_item, filtered);
                            reportSearched.setAdapter(adapter);
                        } else {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.basic_string_item, filtered);
                            adapter.notifyDataSetChanged();
                            reportSearched.setAdapter(adapter);
                        }
                    }
                    return true;
                }
        });

        reportSearched.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(problemId) {
                    case 0:
                        ArrayList<String> act = new ArrayList<>();
                        stringSelected = (String) parent.getItemAtPosition(position);
                        act.add(stringSelected);
                        ArrayAdapter<String> selectedItem = new ArrayAdapter<>(getApplicationContext(), R.layout.basic_string_item, act);
                        reportSearched.setAdapter(selectedItem);
                        break;
                    case 1:
                        ArrayList<User> user = new ArrayList<>();
                        userSelected = (User) parent.getItemAtPosition(position);
                        user.add(userSelected);
                        CustomUserAdapter selectedItem1 = new CustomUserAdapter(getApplicationContext(), R.layout.basic_string_item, user);
                        reportSearched.setAdapter(selectedItem1);
                        break;
                    case 2:
                    case 3:
                    ArrayList<Book> book = new ArrayList<>();
                    bookSelected = (Book) parent.getItemAtPosition(position);
                    book.add(bookSelected);
                    BookAdapterSearch selectedItem2 = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, book);
                    reportSearched.setAdapter(selectedItem2);
                    break;
                }
            }
        });
        submitButton = findViewById(R.id.reportSubmit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "La richiesta verrà presto presa in carico!", Toast.LENGTH_LONG+2).show();
                finish();
            }
        });

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
