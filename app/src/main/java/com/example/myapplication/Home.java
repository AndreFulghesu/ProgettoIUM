package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import java.util.ArrayList;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    final int classValue = 1;
    DrawerLayout drawer;
    User user;
    MaterialSearchView searchView;
    ListView listView;
    ArrayList<Book> books = BookFactory.getInstance().getBooks();
    Menu drawerMenu;
    MenuItem menuItem;
    SwitchCompat dmSwitch;
    NavigationView navigationView;
    View actionView,navHeader;
    ImageView profileImage;
    TextView welcomeHeader;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Gestione richiesta sessione dalla classe**/
        final UserSession userSession = new UserSession(this);

        System.out.println("Utente loggato: "+userSession.getUserSession());

        /**Gestione del tema dell'applicazione**/
        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }
        setContentView(R.layout.drawer_home);

        /**Gestione collegamento tra variabili ed elementi del layot**/
        drawer = findViewById(R.id.drawerHome);
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.listView);

        /**Gestione del layout della Toolbar**/
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.catalogoToolbar);
        setSupportActionBar(toolbar);
        if (!userSession.getTheme()) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }
        getSupportActionBar().setTitle("HomePage");
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        /**Gestione del sistema nel caso in cui non esista la sessione**/
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            finish();
        }

        /**Gestione dello switch per il cambio tema dell'applicazione, presente nel menu laterale**/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_menu_home);
        navigationView.setNavigationItemSelectedListener(this);
        drawerMenu = navigationView.getMenu();

        menuItem = drawerMenu.findItem(R.id.nav_darkmode);
        actionView = MenuItemCompat.getActionView(menuItem);
        navHeader = navigationView.getHeaderView(0);
        welcomeHeader = navHeader.findViewById(R.id.welcomeHeader);
        welcomeHeader.setText("Ciao, "+ user.getNome() + "!");
        profileImage = navHeader.findViewById(R.id.headerProfileImg);

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

        /**Gestione della barra di ricerca nella home**/
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                listView = findViewById(R.id.listView);
                BookAdapterSearch adapter = new BookAdapterSearch(Home.this, R.layout.book_searched, books);
                books.clear();
                listView.setAdapter(adapter);
            }
        });

        ImageButton mBackBtn = (ImageButton) searchView.findViewById(R.id.action_up_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams((LinearLayout.LayoutParams.MATCH_PARENT), (0));
                listView.setLayoutParams(mParam);
                searchView.closeSearch();
            }
        });

        /**Gestione della ricerca e della restituzione dei libri ricercati,
       * basata sul pattern matching**/
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

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
                    LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams((LinearLayout.LayoutParams.MATCH_PARENT), (1230));
                    listView.setLayoutParams(mParam);
                    BookAdapterSearch adapter = new BookAdapterSearch(Home.this, R.layout.book_searched, filtered);
                    listView.setAdapter(adapter);
                } else {
                    BookAdapterSearch adapter = new BookAdapterSearch(Home.this, R.layout.book_searched, filtered);
                    books.clear();
                    books = BookFactory.getInstance().getBooks();
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });

        /**Gestione click utente su un elemento della listView*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book bk = (Book) listView.getItemAtPosition(position);
                Intent readBook = new Intent (Home.this, ChapterList.class);
                userSession.setCallingActivity(classValue);
                readBook.putExtra("User", user);
                readBook.putExtra("bookId", bk.getId());
                userSession.setBookId(bk.getId());
                startActivity(readBook);
            }
        });

        /**Gestione collegamento tra variabili ed elementi del layot**/
        ImageView continuaLettura = findViewById(R.id.continuaLettura);
        ImageView catalogo = findViewById(R.id.catalogo);
        ImageView myProfile = findViewById(R.id.myProfile);
        //ImageView logout = findViewById(R.id.homeLogout);

        continuaLettura.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent continuaLettura = new Intent(Home.this, ContinuaLettura.class);
            userSession.setCallingActivity(classValue);
            System.out.println(getApplicationContext().getClass().getName());
            startActivity(continuaLettura);
            }
        });
        /**Gestione cambio di activity nel caso in cui l'utente
         * abbia premuto Catalogo**/
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCatalogo = new Intent(Home.this, Catalogo.class);
                userSession.setCallingActivity(classValue);
                System.out.println(getApplicationContext().getClass().getName());
                startActivity(gotoCatalogo);
            }
        });
        /**Gestione cambio di activity nel caso in cui l'utente
         * abbia premuto Il Mio Profilo**/
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Home.this, MyProfile.class);
                userSession.setCallingActivity(classValue);
                System.out.println(getApplicationContext().getClass().getName());
                startActivity(profile);

            }
        });
    }
    /**Gestione del comportamento del sistema alla pressione da parte
     * dell'utente su un elemento del menu nella toolbar**/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout) {
            Intent intent = new Intent(Home.this, Login.class);
            UserSession session = new UserSession(this);
            session.invalidateSession();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    /**Gestione dell'uscita dall'applicazione nel caso in cui si torni indietro**/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("(Y)ouBook")
                .setMessage("Sei sicuro di voler uscire dall'applicazione?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    /**Gestione della barra di ricerca nella toolbar**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchItem);

        return true;
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
            /**case R.id.select_img:
                //permessi
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        //permessi non garantiti
                        String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //mostra popoup per chiedere i permessi
                        requestPermissions(permissions,PERMISSION_CODE);
                    }else{
                        //permessi esistenti
                        pickImagreFromGallery();
                    }
                }else{
                    //system os is less then marshmellow
                    pickImagreFromGallery();
                }*/
        }
        return true;
    }

    /**private void pickImagreFromGallery (){

        //pick image

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permessi garantiti
                    pickImagreFromGallery();
                }else{
                    Toast.makeText(this,"Non hai i permessi!",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set Img on profile
            if (data!=null) {
                profileImg.setImageURI(data.getData());
            }
        }
    }*/
}
