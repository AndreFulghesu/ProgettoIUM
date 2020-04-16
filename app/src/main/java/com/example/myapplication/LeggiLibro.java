package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;

public class LeggiLibro extends AppCompatActivity {

    private float dimText = 50;
    public static final String USER_EXTRA ="com.example.faber.bonusIum";
    User newUser;

    String textChapter;
    int bookId, chapId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leggi_libro);
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");
        Serializable obj2 = intent.getSerializableExtra("chapterId");
        Serializable obj3 = intent.getSerializableExtra(Registrazione.USER_EXTRA);

        if (obj3 != null) {
            User newUser = (User) obj3;
            UserFactory.getInstance().addUser(newUser);
            System.out.println(newUser.getUsername());
            UserFactory.getInstance().printUsers();
        }

        if (obj != null) {
            try {
                bookId = (int) obj;
                Book thisBook = BookFactory.getInstance().getBookById(bookId);
                if (obj2 != null) {
                    try {
                        chapId = (int) obj2;
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

        final TextView textBook = findViewById(R.id.textBook);
        Button piu = findViewById(R.id.dimTextPiu);
        Button meno = findViewById(R.id.dimTextMeno);
        Button feedback = findViewById(R.id.feedback);
        ImageView back =findViewById(R.id.frecciaIndietro);

        textBook.setText(textChapter);
        textBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimText);
        piu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newDim = textBook.getTextSize();
                textBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, newDim + 4); }
        });
        meno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newDim = textBook.getTextSize();
                textBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, newDim - 4); }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeFeedback = new Intent(LeggiLibro.this, FormCommento.class);
                startActivity(writeFeedback);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent catalogo = new Intent(LeggiLibro.this, ChapterList.class);
                catalogo.putExtra(USER_EXTRA, newUser);
                catalogo.putExtra("bookId", bookId);
                startActivity(catalogo);
            }
        });
    }
}
