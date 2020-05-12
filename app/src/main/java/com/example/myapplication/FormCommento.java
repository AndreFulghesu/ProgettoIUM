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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class FormCommento extends AppCompatActivity {
    final int classValue = 6;
    int bookId, chapId;
    User user;
    DrawerLayout drawer;

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

        System.out.println("Utente Loggato " + user.getNome()+ " " + user.getCognome());

        final RatingBar bar = findViewById(R.id.ratingBar);
        final TextView rateMessage = findViewById(R.id.rateMessage);
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


        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateMessage.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        rateMessage.setText("Il peggior capitolo che io abbia letto...");
                        break;
                    case 2:
                        rateMessage.setText("Si può migliorare...");
                        break;
                    case 3:
                        rateMessage.setText("Un buon capitolo.");
                        break;
                    case 4:
                        rateMessage.setText("Un ottimo capitolo!");
                        break;
                    case 5:
                        rateMessage.setText("Il miglior capitolo letto ultimamente!");
                        break;
                    default:
                        rateMessage.setText("");
                }
            }
        });

        feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vote = bar.getNumStars();
                String feedback = feedbackMessage.getText().toString();
                Comment comment = new Comment(feedback, vote, chapId, bookId, user,false);
                CommentFactory.getInstance().addComment(comment);
                Intent feedbacks = new Intent(FormCommento.this, CommentList.class);
                feedbacks.putExtra("User", user);
                feedbacks.putExtra("bookId", bookId);
                feedbacks.putExtra("chapId", chapId);
                startActivity(feedbacks);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        MenuItem itemProfile = menu.findItem(R.id.menuprofilo);
        MenuItem itemLogout = menu.findItem(R.id.menulogout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menulogout:
                Intent intent = new Intent (FormCommento.this, Login.class);
                UserSession session = new UserSession(this);
                session.invalidateSession();
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent intent1 = new Intent (FormCommento.this, MyProfile.class);
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
        Class callingActivity = userSession.getActivityFromValue(classValue - 2);
        if (callingActivity != null) {
            Intent goBack = new Intent(getApplicationContext(), callingActivity);
            goBack.putExtra("bookId", bookId);
            goBack.putExtra("chapId", chapId);
            startActivity(goBack);
        }
    }
}
