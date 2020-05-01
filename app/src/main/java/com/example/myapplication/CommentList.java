package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommentList extends AppCompatActivity {

    ListView lista;
    User actualUser;
    ArrayList<Comment> myComments = CommentFactory.getInstance().getComments();
    Chapter capitoloCorrente;
    Book libroCorrente;
    private int bookId,chapterId;
    TextView numberCap, titleBook;
    public static final String USER_EXTRA ="com.example.faber.bonusIum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commenti);

        lista = findViewById(R.id.listaCommenti);
        numberCap = findViewById(R.id.testoCapitolo);
        titleBook = findViewById(R.id.testoLibro);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");
        Serializable obj2 = intent.getSerializableExtra("chapId");
        Serializable obj3 = intent.getSerializableExtra("User");

        if (obj != null){
            bookId = (int) obj;
        }

        if (obj2 != null){
            chapterId = (int) obj2;
        }

        if (obj3 != null){
            actualUser = (User)obj3;
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.commentiToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(CommentList.this, LeggiLibro.class);
                goBack.putExtra("User", actualUser);
                goBack.putExtra("bookId", bookId);
                goBack.putExtra("chapId", chapterId);
                startActivity(goBack);
            }
        });

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Commenti ");


        //gestione visualizzazione numero capitolo e titolo libro
        capitoloCorrente = ChapterFactory.getInstance().getChapterByChapNum(chapterId,bookId);
        numberCap.setText("CAPITOLO " + capitoloCorrente.getChaptNum());

        libroCorrente = BookFactory.getInstance().getBookById(bookId);
        titleBook.setText(libroCorrente.getTitle());


        //inizio gestione layout della lista

        //myComments = CommentFactory.getInstance().getCommentId(chapterId,bookId);

        for (Comment c : myComments){
            System.out.println("Commento: "+c.getText());
            //System.out.println("Autore: "+c.getUserAuthor().getNome() + " " + c.getUserAuthor().getCognome());
        }






        CustomCommentAdapter adapter = new CustomCommentAdapter(this, R.layout.commentitem, myComments);
        myComments.clear();
        myComments = CommentFactory.getInstance().getCommentById(chapterId,bookId);
        adapter.clear();
        adapter.addAll(myComments);
        adapter.notifyDataSetChanged();

        lista.setAdapter(adapter);

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
                Intent intent = new Intent (CommentList.this, Login.class);
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent intent1 = new Intent (CommentList.this, MyProfile.class);
                startActivity(intent1);
                break;
            case R.id.report:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
