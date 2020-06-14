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
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class LeggiLibro extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    final int classValue = 4;
    private float dimText = 50;
    User user;
    String textChapter;
    int bookId, chapId;
    DrawerLayout drawer;
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView, navHeader;
    ImageView profileImage;
    TextView welcomeHeader;
    long startTime;
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
        setContentView(R.layout.drawer_leggilibro);
        drawer = findViewById(R.id.drawerLeggiLibro);

        /**Gestione del sistema nel caso in cui non esista la sessione**/
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            finish();
        }
        try {
            bookId = userSession.getBookId();
            Book thisBook = BookFactory.getInstance().getBookById(bookId);
            try {
                chapId = userSession.getChapId();
                textChapter = thisBook.getChapter(chapId).getText();
            } catch (NullPointerException e) {
                chapId = 1;
                textChapter = thisBook.getChapter(chapId).getText();
            }
        } catch (NullPointerException ex) {
            Intent goToCatalogo = new Intent (LeggiLibro.this, Catalogo.class);
            startActivity(goToCatalogo);
        }

        /**final ArrayList<Comment> attuali = ChapterFactory.getInstance().getChapterByChapNum(chapId,bookId).getComment();
        final HashMap<Comment,Boolean> test = user.getMapLikes();*/

        /**Gestione del layout della Toolbar**/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.leggilibrobar);
        setSupportActionBar(toolbar);
        if (!userSession.getTheme()) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);

        /**Gestione apertura menu laterale nella toolbar*/
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
        navigationView = findViewById(R.id.nav_menu_leggiibro);
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

        /**Gestione visualizzazione immagine utente nel drawer*/
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

        getSupportActionBar().setTitle(BookFactory.getInstance().getBookById(bookId).getTitle());

        /**Gestione menu footer*/

        bottomNavigationMenu = findViewById(R.id.leggiLibroFooter);

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavMenuLasciaCommento:
                        Intent lasciaCommento = new Intent(getApplicationContext(), FormCommento.class);
                        startActivity(lasciaCommento);
                        break;
                    case R.id.bottomNavHome:
                        Intent home = new Intent(getApplicationContext(), Home.class);
                        startActivity(home);
                        break;
                    case R.id.bottomNavLeggiCommenti:
                        Intent commenti = new Intent(getApplicationContext(), CommentList.class);
                        startActivity(commenti);
                        break;
                }
                return false;
            }
        });

        /**Associazione variabili d'istanza con elementi del layout*/
        final TextView textBook = findViewById(R.id.textBook);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textBook.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
            textBook.setTextDirection(View.TEXT_DIRECTION_LTR);
        }
        Button piu = findViewById(R.id.dimTextPiu);
        Button meno = findViewById(R.id.dimTextMeno);
        /**Button feedback = findViewById(R.id.feedback);
        Button readFeedback = findViewById(R.id.readFeedback);*/
        Button schermo_intero = findViewById(R.id.schermoIntero);

        textBook.setText(textChapter);
        textBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimText);
        startTime = System.currentTimeMillis();

        /**Gestione dimensione testo in base alla pressione degli appositi bottoni*/
        piu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newDim = textBook.getTextSize();
                textBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, newDim + 4);
            }
        });
        meno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newDim = textBook.getTextSize();
                textBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, newDim - 4);
            }
        });
        /**Gestione passagio alla activity di scrittura del commento*/
        /**feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeFeedback = new Intent(LeggiLibro.this, FormCommento.class);
                userSession.setCallingActivity(classValue);
                long endTime = System.currentTimeMillis() - startTime;
                if (endTime> 30000) {
                    BookFactory.getInstance().getBookById(userSession.getBookId()).incrementViews();
                }
                startActivity(writeFeedback);
            }
        });*/

        /**Gestione passaggio alla activity per la lettura dei commento*/
        /**readFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commenti = new Intent(LeggiLibro.this, CommentList.class);
                userSession.setCallingActivity(classValue);
                long endTime = System.currentTimeMillis() - startTime;
                if (endTime> 30000) {
                    BookFactory.getInstance().getBookById(bookId).incrementViews();
                }
                startActivity(commenti);
            }
        });*/
        /**Gestione passaggio alla activity per la lettura a schermo intero*/
        schermo_intero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maxiSchermo = new Intent(LeggiLibro.this, NewMaxiLettura.class);
                userSession.setCallingActivity(classValue);
                userSession.setBookId(bookId);
                userSession.setChapId(chapId);
                long endTime = System.currentTimeMillis() - startTime;
                if (endTime> 30000) {
                    BookFactory.getInstance().getBookById(bookId).incrementViews();
                }
                startActivity(maxiSchermo);
            }
        });

    }

    /**Gestione del menu nella toolbar**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nextchapter_menu, menu);
        return true;
    }

    /**Gestione del comportamento del sistema alla pressione da parte
     * dell'utente su un elemento del menu nella toolbar**/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nextChapterButton) {
            UserSession userSession = new UserSession(getApplicationContext());
            if (ChapterFactory.getInstance().getChapterByChapNum(chapId+1, bookId) != null) {
                userSession.setChapId(chapId + 1);
                Intent nextChapter = new Intent(getApplicationContext(), LeggiLibro.class);
                nextChapter.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(nextChapter);
            } else {
                Toast.makeText(this, "Il capitolo richiesto non esiste.", Toast.LENGTH_LONG+1).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**Gestione sistema alla pressione del tasto Indietro*/
    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 1);
        if (callingActivity != null) {
            UserFactory.getInstance().addLibroIniziato(user, BookFactory.getInstance().getBookById(bookId),
                    ChapterFactory.getInstance().getChapterByChapNum(chapId, bookId));
            long endTime = System.currentTimeMillis() - startTime;
            if (endTime> 1000) {
                BookFactory.getInstance().addViewsBook(BookFactory.getInstance().getBookById(bookId));
            }
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
