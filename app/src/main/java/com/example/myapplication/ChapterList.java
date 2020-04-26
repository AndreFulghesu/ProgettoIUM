package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterList extends AppCompatActivity {

    Dialog plotDialog;
    ArrayList<Chapter> chapters = ChapterFactory.getInstance().getChapters();
    User user;
    int bookId;
    Boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        plotDialog = new Dialog(this);


        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");
        Serializable objUser = intent.getSerializableExtra("User");

        if (objUser != null) {
            user = (User) objUser;
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.chapterlistbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(ChapterList.this, Catalogo.class);
                goBack.putExtra("User", user);
                goBack.putExtra("bookId", bookId);
                startActivity(goBack);
            }
        });

        final ListView chapterList = findViewById(R.id.chapterlist);

        if (obj != null) {
            bookId = (int) obj;
        }
        getSupportActionBar().setTitle("Capitoli: " + BookFactory.getInstance().getBookById(bookId).getTitle());

        CustomChapterAdapter adapter = new CustomChapterAdapter(this, R.layout.chapteritem, chapters);
        chapters.clear();
        chapters = ChapterFactory.getInstance().getChaptersByBookId(bookId);
        adapter.clear();
        adapter.addAll(chapters);
        adapter.notifyDataSetChanged();
        chapterList.setAdapter(adapter);

        final int finalBookId = bookId;
        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book bk = BookFactory.getInstance().getBookById(finalBookId);
                Intent readBook = new Intent(ChapterList.this, LeggiLibro.class);

                readBook.putExtra("bookId", bk.getId());
                readBook.putExtra("chapId", position + 1);
                readBook.putExtra("User", user);
                startActivity(readBook);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        MenuItem itemProfile = menu.findItem(R.id.menuprofilo);
        MenuItem itemLogout = menu.findItem(R.id.menulogout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bookPlot:
                Intent seePlot = new Intent(ChapterList.this, PlotPopUp.class);
                seePlot.putExtra("bookId", bookId);
                seePlot.putExtra("User", user);
                startActivity(seePlot);
                break;
            case R.id.menulogout:
                Intent login = new Intent(ChapterList.this, Login.class);
                startActivity(login);
                break;
            case R.id.menuprofilo:
                Intent myProfile = new Intent(ChapterList.this, MyProfile.class);
                startActivity(myProfile);
                break;
            case R.id.report:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
