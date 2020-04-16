package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChapterList extends AppCompatActivity {
    List<Chapter> chapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        ListView chapterList = findViewById(R.id.chapterlist);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");
        int bookId;

        if (obj != null) {
            bookId = (int) obj;
            chapters = BookFactory.getInstance().getBookById(bookId).getChapters();
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.string_list_element, R.id.chapterlist, chaptersToString(chapters));
        adapter.notifyDataSetChanged();
        chapterList.setAdapter(adapter);
    }
    public ArrayList<String> chaptersToString(List<Chapter> chaps) {
        ArrayList<String> list= new ArrayList<>();
        int counter = 1;
        for (Chapter c: chaps){
            list.add("Capitolo " + counter);
            counter++;
        }
        return list;
    }
}
