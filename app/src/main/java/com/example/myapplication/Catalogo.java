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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Catalogo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    final int classValue = 2;
    ArrayList<Book> books= BookFactory.getInstance().getBooks();
    User user;
    Genres genreFilter;
    int ordNum;

    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    MaterialSearchView searchView;
    View actionView, navHeader;
    ImageView profileImage;
    TextView welcomeHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Gestione richiesta sessione dalla classe**/
        final UserSession userSession = new UserSession(this);

        /**Gestione del sistema nel caso in cui non esista la sessione**/
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            finish();
        }

        /**Gestione del tema dell'applicazione**/
        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }
        setContentView(R.layout.drawer_catalogo);
        drawer = findViewById(R.id.drawerCatalogo);

        /**Gestione del layout della Toolbar**/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.catalogoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catalogo Libri");
        if (!userSession.getTheme()) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);

        /**Gestione apertura menu laterale**/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        final ListView lst= findViewById(R.id.booklist);

        /**Gestione dello switch per il cambio tema dell'applicazione, presente nel menu laterale**/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_menu_catalogo);
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

        /**Gestione dell'ordinamento delle liste di libri in base ad eventuali
         *  cambiamenti di parametri nella activityu FiltroCatalogo**/

        Intent intent = getIntent();
        Serializable objGenre = intent.getSerializableExtra("GENERE_FILTRAGGIO");

        ordNum = userSession.getOrdinamento();

        if (objGenre != null) {
            genreFilter = (Genres) objGenre;
            System.out.println(genreFilter.toString());
        }

        /**Gestione dell'adapter per la ListView dei libri**/

        books.clear();
        CustomBookAdapter adapter = new CustomBookAdapter(this, R.layout.bookitem, books);
        books = BookFactory.getInstance().getBooks();
        adapter.clear();
        adapter.addAll(books);
        adapter.notifyDataSetChanged();
        lst.setAdapter(adapter);

        /**Gestione ordinamento lista libri in base ala scelta dell'utente salvata in sessione*/
        if (ordNum == 1) {
            if (genreFilter != null) {
                books = BookFactory.getInstance().getBooksByGenre(genreFilter);
                Collections.sort(books);
                adapter.clear();
                adapter.addAll(books);
            } else {
                Collections.sort(books);
                books = BookFactory.getInstance().getBooks();
                Collections.sort(books);
            }
        } else {
            if (genreFilter != null) {
                books = BookFactory.getInstance().getBooksByGenre(genreFilter);
                adapter.clear();
                adapter.addAll(books);
            } else {
                books = BookFactory.getInstance().getBooks();
            }
        }

        /**Gestione della barra di ricerca nel catalogo dei libri**/
        searchView = findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                CustomBookAdapter adapter = new CustomBookAdapter(getApplicationContext(), R.layout.bookitem, books);
                books.clear();
                lst.setAdapter(adapter);
            }
        });
        /**Gestione della ricerca e della restituzione dei libri ricercati,
         * basata sul pattern matching**/
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Book> filtered = new ArrayList<>();
                books.clear();
                books = BookFactory.getInstance().getBooks();
                if (newText != null && !newText.isEmpty()) {
                    for (Book b : books) {
                        if (b.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            System.out.println(b.getTitle());
                            filtered.add(b);
                        }
                    }
                    BookAdapterSearch adapter = new BookAdapterSearch(Catalogo.this, R.layout.bookitem, filtered);
                    lst.setAdapter(adapter);
                } else {
                    BookAdapterSearch adapter = new BookAdapterSearch(Catalogo.this, R.layout.bookitem  , filtered);
                    books.clear();
                    books = BookFactory.getInstance().getBooks();
                    adapter.notifyDataSetChanged();
                    lst.setAdapter(adapter);
                }
                return true;
            }
        });

        /**Gestione del comportamento del sistema alla pressione da parte
         * dell'utente su un elemento della ListView**/
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book bk = (Book) lst.getItemAtPosition(position);
                Intent readBook = new Intent (Catalogo.this, ChapterList.class);
                userSession.setCallingActivity(classValue);
                userSession.setBookId(bk.getId());
                startActivity(readBook);
            }
        });

    }
    /**Gestione del menu nella toolbar**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        searchView.setMenuItem(searchItem);
        return true;
    }

    /**Gestione del comportamento del sistema alla pressione da parte
     * dell'utente su un elemento del menu nella toolbar**/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filtralibri) {
            Intent filtraLibri = new Intent(Catalogo.this, FiltroCatalogo.class);
            filtraLibri.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(filtraLibri);
        }
        return super.onOptionsItemSelected(item);
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
                Uri uri = Uri.parse("http://www.google.com"); /**missing 'http://' will cause crashed*/
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
        return true;
    }
}
