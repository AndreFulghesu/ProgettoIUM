package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    int bookId, chapId;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_commento);

        Intent intent = getIntent();
        Serializable objBook = intent.getSerializableExtra("bookId");
        Serializable objChap = intent.getSerializableExtra("chapId");
        Serializable objUser = intent.getSerializableExtra("User");
        if (objBook!=null) {
            bookId = (int) objBook;
        }
        if (objChap!=null) {
            chapId= (int) objChap;
        }
        if (objUser != null) {
            user = (User) objUser;
        }

        final RatingBar bar = findViewById(R.id.ratingBar);
        final TextView rateMessage = findViewById(R.id.rateMessage);
        final EditText feedbackMessage = findViewById(R.id.feedbackMessage);
        Button feedbackSubmit = findViewById(R.id.feedbackSubmit);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.formcommentobar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(FormCommento.this, LeggiLibro.class);
                goBack.putExtra("User", user);
                goBack.putExtra("bookId", bookId);
                goBack.putExtra("chapId", chapId);
                startActivity(goBack);
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
                Comment comment = new Comment(feedback, vote, chapId, bookId, user);
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
}
