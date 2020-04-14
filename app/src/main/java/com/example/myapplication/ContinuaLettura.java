package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class ContinuaLettura extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continua_lettura);
        ListView lst= findViewById(R.id.booklist);
        List<Book> books = BookFactory.getInstance().getBooks();
        CustomBookAdapter adapter = new CustomBookAdapter(this, R.layout.bookitem, books);
        lst.setAdapter(adapter);
    }
}