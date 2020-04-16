package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChapterList extends AppCompatActivity {
    List<Chapter> chapters= ChapterFactory.getInstance().getChaptersByBookId(1);
    ArrayList<String> chapsString = chaptersToString(chapters);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        final ListView chapterList = findViewById(R.id.chapterlist);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");
        int bookId = -1;

        if (obj != null) {
            bookId = (int) obj;
        }

        CustomChapterAdapter adapter = new CustomChapterAdapter(this, R.layout.chapteritem, chapsString);
        chapters.clear();
        chapters = ChapterFactory.getInstance().getChaptersByBookId(bookId);
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
    public ArrayList<String> chaptersToString(List<Chapter> chaps) {
        ArrayList<String> list= new ArrayList<>();
        int counter = 1;
        for (Chapter c: chaps){
            list.add("Capitolo " + counter);
            counter++;
            System.out.println(c.getChaptNum());
        }
        return list;
    }
}
