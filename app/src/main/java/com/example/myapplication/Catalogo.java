package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class Catalogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(Registrazione.USER_EXTRA);

        ListView lst= findViewById(R.id.booklist);
        List<Book> books = BookFactory.getInstance().getBooks();
        CustomBookAdapter adapter = new CustomBookAdapter(this, R.layout.bookitem, books);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent readBook = new Intent (Catalogo.this, LeggiLibro.class);
                readBook.putExtra("bookId", position+1);
                startActivity(readBook);
            }
        });

        if (obj != null) {
            User newUser = (User) obj;
            UserFactory.getInstance().addUser(newUser);
            System.out.println(newUser.getUsername());
            UserFactory.getInstance().printUsers();
        }

    }
}
