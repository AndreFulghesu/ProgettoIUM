package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterList extends AppCompatActivity {

    ArrayList<Chapter> chapters = ChapterFactory.getInstance().getChapters();
    User user;
    int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");
        Serializable objUser = intent.getSerializableExtra("USER");

        if (objUser!= null) {
            user = (User) objUser;
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.chapterlistbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(ChapterList.this, Catalogo.class);
                startActivity(goBack);
            }
        });

        for (Chapter c: chapters) {
            System.out.println("Stampa pre Gigi Book: "+ c.getBookId() + "\tChap: "+ c.getChaptNum());
        }

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
                Intent readBook = new Intent (ChapterList.this, LeggiLibro.class);
                readBook.putExtra("bookId", bk.getId());
                readBook.putExtra("chapterId", position+1);
                startActivity(readBook);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        MenuItem itemProfile = menu.findItem(R.id.menuprofilo);
        MenuItem itemLogout = menu.findItem(R.id.menulogout);
        itemProfile.setTitle("Il mio Profilo");
        itemLogout.setTitle("Logout");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menulogout:
                Intent intent = new Intent (ChapterList.this, Login.class);
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent intent1 = new Intent (ChapterList.this, MyProfile.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
