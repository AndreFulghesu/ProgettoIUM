package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterList extends AppCompatActivity {

    ArrayList<Chapter> chapters = ChapterFactory.getInstance().getChapters();

    int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        for (Chapter c: chapters) {
            System.out.println("Stampa pre Gigi Book: "+ c.getBookId() + "\tChap: "+ c.getChaptNum());
        }

        final ListView chapterList = findViewById(R.id.chapterlist);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");

        if (obj != null) {
            bookId = (int) obj;
        }

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
}
