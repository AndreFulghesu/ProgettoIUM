package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
    final int classValue = 5;
    ListView lista;
    User user;
    ArrayList<Comment> myComments = CommentFactory.getInstance().getComments();
    Chapter capitoloCorrente;
    Book libroCorrente;
    private int bookId,chapterId;
    TextView numberCap, titleBook;
    DrawerLayout drawer;
    public static final String USER_EXTRA ="com.example.faber.bonusIum";

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
        setContentView(R.layout.drawer_commenti);
        drawer = findViewById(R.id.drawerCommenti);

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

        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.commentiToolbar);
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
                UserSession session = new UserSession(this);
                session.invalidateSession();
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent intent1 = new Intent (CommentList.this, MyProfile.class);
                intent1.putExtra("User", user);
                startActivity(intent1);
                break;
            case R.id.report:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
