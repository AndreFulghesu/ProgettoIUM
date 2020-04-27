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
import java.util.List;

public class Catalogo extends AppCompatActivity {

    ArrayList<Book> books= BookFactory.getInstance().getBooks();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.catalogoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catalogo Libri");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(Catalogo.this, Home.class);
                goBack.putExtra("User", user);
                startActivity(goBack);
            }
        });

        final ListView lst= findViewById(R.id.booklist);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("User");
        if (obj != null) {
            user = (User) obj;
        }

        CustomBookAdapter adapter = new CustomBookAdapter(this, R.layout.bookitem, books);
        books.clear();
        books = BookFactory.getInstance().getBooks();
        adapter.notifyDataSetChanged();
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book bk = (Book) lst.getItemAtPosition(position);
                Intent readBook = new Intent (Catalogo.this, ChapterList.class);
                readBook.putExtra("User", user);
                readBook.putExtra("bookId", bk.getId());
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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menulogout:
                Intent intent = new Intent (Catalogo.this, Login.class);
                startActivity(intent);
                break;
            case R.id.menuprofilo:
                Intent intent1 = new Intent (Catalogo.this, MyProfile.class);
                intent1.putExtra("User", user);
                startActivity(intent1);
                break;
            case R.id.report:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
