package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    private float dimText = 50;
    User user;
    String textChapter;
    int bookId, chapId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leggi_libro);
        Intent intent = getIntent();
        Serializable objBook = intent.getSerializableExtra("bookId");
        Serializable objChap = intent.getSerializableExtra("chapId");
        Serializable obj3 = intent.getSerializableExtra("User");
        if (obj3 != null) {
            User user = (User) obj3;
        }

        if (objBook != null) {
            try {
                bookId = (int) objBook;
                Book thisBook = BookFactory.getInstance().getBookById(bookId);
                if (objChap != null) {
                    try {
                        chapId = (int) objChap;
                        textChapter = thisBook.getChapter(chapId).getText();
                    } catch (NullPointerException e) {
                        textChapter = thisBook.getChapter(1).getText();

                    }
                }
            } catch (NullPointerException ex) {
                Intent goToCatalogo = new Intent (LeggiLibro.this, Catalogo.class);
                startActivity(goToCatalogo);
            }
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.leggilibrobar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent (LeggiLibro.this, ChapterList.class);
                goBack.putExtra("bookId", bookId);
                goBack.putExtra("User", user);
                goBack.putExtra("chapId", chapId);
                startActivity(goBack);
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
}
