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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;

public class CommentList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    final int classValue = 5;
    ListView lista;
    User user;
    ArrayList<Comment> myComments = CommentFactory.getInstance().getComments();
    Chapter capitoloCorrente;
    Book libroCorrente;
    private int bookId,chapId;
    TextView numberCap, titleBook, commentiVuoti;
    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView, navHeader;
    ImageView profileImage;
    TextView welcomeHeader;
    BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Gestione richiesta sessione dalla classe**/
        final UserSession userSession = new UserSession(this);

        /**Gestione del tema dell'applicazione**/
        if (userSession.getTheme() == false) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }

        /**Gestione associazione tra variabili d'istanza ed elementi del layout*/
        setContentView(R.layout.drawer_commenti);
        drawer = findViewById(R.id.drawerCommenti);
        lista = findViewById(R.id.listaCommenti);
        numberCap = findViewById(R.id.testoCapitolo);
        titleBook = findViewById(R.id.testoLibro);
        commentiVuoti = findViewById(R.id.commentiVuoti);

        /**Controllo dell'utente salvato in sessione e gestione
         * in caso di eccezione nel codice*/
        try {
            bookId = userSession.getBookId();
            try {
                chapId = userSession.getChapId();
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
            finish();
        }

        /**Gestione del layout della Toolbar**/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.commentiToolbar);
        setSupportActionBar(toolbar);
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

        getSupportActionBar().setTitle("Commenti ");

        /**Gestione dello switch per il cambio tema dell'applicazione, presente nel menu laterale**/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_menu_commenti);
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

        /**Gestione menu footer*/
        bottomNavigationMenu = findViewById(R.id.commentiFooter);

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavMenuContinuaLettura:
                        Intent contLettura = new Intent(getApplicationContext(), ContinuaLettura.class);
                        startActivity(contLettura);
                        break;
                    case R.id.bottomNavMenuHome:
                        Intent home = new Intent(getApplicationContext(), Home.class);
                        startActivity(home);
                        break;
                    case R.id.bottomNavMenuProfilo:
                        Intent profilo = new Intent(getApplicationContext(), MyProfile.class);
                        startActivity(profilo);
                        break;
                }
                return false;
            }
        });

        /**Gestione visualizzazione numero capitolo e titolo libro*/
        capitoloCorrente = ChapterFactory.getInstance().getChapterByChapNum(chapId,bookId);
        numberCap.setText("CAPITOLO " + capitoloCorrente.getChaptNum());

        libroCorrente = BookFactory.getInstance().getBookById(bookId);
        titleBook.setText(libroCorrente.getTitle());

        ArrayList<Comment> debugging = CommentFactory.getInstance().getCommentById(chapId,bookId);
        if (debugging.isEmpty()){
            commentiVuoti.setText("Non ci sono commenti");
        } else {
            CustomCommentAdapter adapter = new CustomCommentAdapter(this, R.layout.commentitem, myComments);
            myComments.clear();
            myComments = CommentFactory.getInstance().getCommentById(chapId, bookId);
            adapter.clear();
            adapter.addAll(myComments);
            adapter.notifyDataSetChanged();
            lista.setAdapter(adapter);
        }
    }

    /**Gestione del cambio di activity quando l'utente preme il tasto indietro**/
    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 1);
        if (callingActivity != null) {
            Intent goBack = new Intent(getApplicationContext(), callingActivity);
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
