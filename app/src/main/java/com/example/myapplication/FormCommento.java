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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class FormCommento extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    final int classValue = 6;
    int bookId, chapId;
    User user;
    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView, navHeader;
    ImageView profileImage;
    RatingBar bar;
    EditText feedbackMessage;
    Button feedbackSubmit;
    TextView welcomeHeader;
    BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Gestione richiesta sessione dalla classe**/
        final UserSession userSession = new UserSession(this);

        /**Gestione del tema dell'applicazione**/
        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }
        setContentView(R.layout.drawer_formcommento);
        drawer = findViewById(R.id.drawerFormCommento);

        /**Gestione del sistema nel caso in cui non esista la sessione**/
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

        /**Associazione variabili d'istanza con elementi del layout*/
        bar = findViewById(R.id.ratingBar);
        feedbackMessage = findViewById(R.id.feedbackMessage);
        feedbackSubmit = findViewById(R.id.feedbackSubmit);

        /**Gestione del layout della Toolbar**/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.formcommentobar);
        setSupportActionBar(toolbar);
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
        getSupportActionBar().setTitle(BookFactory.getInstance().getBookById(bookId).getTitle());

        /**Gestione dello switch per il cambio tema dell'applicazione, presente nel menu laterale**/
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
        /**Fine gestione switch per il cambio tema**/

        /**Gestione immagine utente nel drawerMenu*/
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
        bottomNavigationMenu = findViewById(R.id.formCommentoFooter);

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

        /**Gestione pressione tasto per il salvataggio del commento*/
        feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vote = (int)bar.getRating();
                String feedback = feedbackMessage.getText().toString();
                Comment comment = new Comment(feedback, vote, chapId, bookId, user,false);
                CommentFactory.getInstance().addComment(comment);
                ChapterFactory.getInstance().getChapterByChapNum(chapId,bookId).addComment(comment);
                ChapterFactory.getInstance().getChapterByChapNum(chapId,bookId).setValutation();
                userSession.setCallingActivity(classValue);
                Toast.makeText(getApplicationContext(), "Commento salvato!", Toast.LENGTH_LONG+3).show();
                Intent feedbacks = new Intent(FormCommento.this, CommentList.class);
                startActivity(feedbacks);
            }
        });
    }

    /**Gestione pressione del tasto Indietro*/
    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 2);
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
