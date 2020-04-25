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
    ArrayList<Comment> elencoCommenti = new ArrayList<>();
    Chapter capitoloCorrente;
    Book libroCorrente;
    private int bookId,chapterId;
    TextView numberCap, titleBook;
    public static final String USER_EXTRA ="com.example.faber.bonusIum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commenti);

        lista = findViewById(R.id.lista1);
        numberCap = findViewById(R.id.numberCap);
        titleBook = findViewById(R.id.titleBook);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra("bookId");
        Serializable obj2 = intent.getSerializableExtra("chapterId");
        Serializable obj3 = intent.getSerializableExtra(Registrazione.USER_EXTRA);

        if (obj != null){
            bookId = (int) obj;
        }

        if (obj2 != null){
            chapterId = (int) obj2;
        }

        if (obj3 != null){
            actualUser = (User)obj3;
        }

        //gestione visualizzazione numero capitolo e titolo libro
        capitoloCorrente = ChapterFactory.getInstance().getChapterByChapNum(chapterId,bookId);
        numberCap.setText("CAPITOLO " + capitoloCorrente.getChaptNum());

        libroCorrente = BookFactory.getInstance().getBookById(bookId);
        titleBook.setText(libroCorrente.getTitle());


        //inizio gestione layout della lista

        elencoCommenti = CommentFactory.getInstance().getCommentId(chapterId,bookId);
        for (Comment c : elencoCommenti){
            System.out.println(c.getText());
        }

        MyAdapter adatt = new MyAdapter(this, elencoCommenti);
        elencoCommenti.clear();
        elencoCommenti = CommentFactory.getInstance().getCommentId(chapterId,bookId);
        adatt.notifyDataSetChanged();
        lista.setAdapter(adatt);

    }

    class MyAdapter extends ArrayAdapter<Comment> {

        Context contesto;
        ArrayList<Comment> elenco_commenti;

        MyAdapter (Context c, ArrayList<Comment> commenti){
            super(c,R.layout.row);
            this.contesto = c;
            this.elenco_commenti = commenti;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View row = null;

            if (convertView == null) {
                LayoutInflater inflater = ((Activity) contesto).getLayoutInflater();
                row = inflater.inflate(R.layout.row, parent, false);
                //Make sure the textview exists in this xml
            } else {
                row = convertView;
            }
            Log.d("SeenDroid", String.format("Get view %d", position));
            TextView autore = row.findViewById(R.id.autore);
            TextView descrizione = row.findViewById(R.id.contenuto);
            if (elenco_commenti.get(0) == null){
                descrizione.setText("Nessun commento");
                return row;
            }

            autore.setText(elenco_commenti.get(position).getUserAuthor().getNome() + " " + elenco_commenti.get(position).getUserAuthor().getCognome());
            descrizione.setText(elenco_commenti.get(position).getText());

            return row;

        }
    }
}
