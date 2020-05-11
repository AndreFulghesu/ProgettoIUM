package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;

public class LeggiLibro extends AppCompatActivity {

    final int classValue = 4;
    private float dimText = 50;
    User user;
    String textChapter;
    int bookId, chapId;
    DrawerLayout drawer;
    Button schermo_intero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserSession userSession = new UserSession(this);

        //help me here
        //collega questa roba allo schermo intero e passa il testo del libro 

        schermo_intero = findViewById(R.id.schermoIntero);

        if (userSession.getTheme() == false) {
            setTheme(R.style.AppTheme);
            System.out.println("TEMA NORMALE");
        } else {
            setTheme(R.style.darkTheme);
            System.out.println("TEMA SCURO");

        }
        setContentView(R.layout.drawer_leggilibro);
        drawer = findViewById(R.id.drawerLeggiLibro);

        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        System.out.println("Utente Loggato " + user.getNome()+ " " + user.getCognome());

        try {
            bookId = userSession.getBookId();
            Book thisBook = BookFactory.getInstance().getBookById(bookId);
            try {
                chapId = userSession.getChapId();
                textChapter = thisBook.getChapter(chapId).getText();
            } catch (NullPointerException e) {
                textChapter = thisBook.getChapter(1).getText();
            }
        } catch (NullPointerException ex) {
            Intent goToCatalogo = new Intent (LeggiLibro.this, Catalogo.class);
            startActivity(goToCatalogo);
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.leggilibrobar);
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

        final TextView textBook = findViewById(R.id.textBook);
        Button piu = findViewById(R.id.dimTextPiu);
        Button meno = findViewById(R.id.dimTextMeno);
        Button feedback = findViewById(R.id.feedback);
        Button readFeedback = findViewById(R.id.readFeedback);

        textBook.setText(textChapter);
        textBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimText);
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
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeFeedback = new Intent(LeggiLibro.this, FormCommento.class);
                userSession.setCallingActivity(classValue);
                writeFeedback.putExtra("User",user);
                writeFeedback.putExtra("bookId",bookId);
                writeFeedback.putExtra("chapId",chapId);
                startActivity(writeFeedback);
            }
        });

        readFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commenti = new Intent(LeggiLibro.this, CommentList.class);
                userSession.setCallingActivity(classValue);
                commenti.putExtra("User",user);
                commenti.putExtra("bookId",bookId);
                commenti.putExtra("chapId",chapId);
                startActivity(commenti);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menulogout:
                Intent intent = new Intent (LeggiLibro.this, Login.class);
                UserSession session = new UserSession(this);
                session.invalidateSession();
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent intent1 = new Intent (LeggiLibro.this, MyProfile.class);
                startActivity(intent1);
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
            goBack.putExtra("bookId", bookId);
            startActivity(goBack);
        }
    }
}
