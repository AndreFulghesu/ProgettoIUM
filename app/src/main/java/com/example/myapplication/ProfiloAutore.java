package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfiloAutore extends AppCompatActivity {

    TextView nomeCognome, usernameAuthor,visiteLibro, valutazioneUtente;
    User user_author;
    ListView lista_libri;
    ImageView star;
    private ArrayList<Book> libri_autore ;
    final int classValue = 2;
    private float valutation;
    private int num_visual;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo_autore);
        final UserSession userSession = new UserSession(this);


        /**Gestione del tema dell'applicazione**/
        if (userSession.getTheme() == false) {
            setTheme(R.style.AppTheme);
            System.out.println("TEMA NORMALE");
        } else {
            setTheme(R.style.darkTheme);
            System.out.println("TEMA SCURO");
        }

        /**Gestione del layout della Toolbar**/

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.authorToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profilo Autore");
        if (userSession.getTheme() == false) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));

        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);


        nomeCognome = findViewById(R.id.NomeCognome);
        usernameAuthor = findViewById(R.id.UsernameAuthor);
        lista_libri = findViewById(R.id.listaLibri);
        valutazioneUtente = findViewById(R.id.valutazioneUtente);
        visiteLibro = findViewById(R.id.visiteLibro);
        star = findViewById(R.id.stella_valutazione);


        try {
            user_author = UserFactory.getInstance().getUserByUsername(userSession.getUsernameAuthor());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }

        libri_autore = BookFactory.getInstance().getBookByUser(user_author);

        valutation= BookFactory.getInstance().getValutationTotalBookUser(user_author);
        nomeCognome.setText(user_author.getNome()+user_author.getCognome());
        usernameAuthor.setText(user_author.getUsername());
        valutazioneUtente.setText(""+roundDown5(valutation));
        setStarColor(valutation,star);

        visiteLibro.setText(""+sommaView(libri_autore));



        /**Gestione dell'adapter per la ListView dei libri**/

        libri_autore.clear();
        BookAdapterSearch adapter = new BookAdapterSearch(ProfiloAutore.this, R.layout.book_searched, libri_autore);
        libri_autore = BookFactory.getInstance().getBookByUser(user_author);
        adapter.clear();
        adapter.addAll(libri_autore);
        adapter.notifyDataSetChanged();
        lista_libri.setAdapter(adapter);


        /**Gestione del comportamento del sistema alla pressione da parte
         * dell'utente su un elemento della ListView**/
        lista_libri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book bk = (Book) lista_libri.getItemAtPosition(position);
                Intent readBook = new Intent (ProfiloAutore.this, ChapterList.class);
                userSession.setCallingActivity(classValue);
                userSession.setBookId(bk.getId());
                startActivity(readBook);
            }
        });


    }


    public static double roundDown5(float d) {
        return Math.floor(d * 1e2) / 1e2;
    }

    public void setStarColor (float valutation, ImageView star){
        if(valutation==5){
            star.setColorFilter(getResources().getColor(R.color.blue));

        }else {
            if (valutation > 4.2f) {
                star.setColorFilter(getResources().getColor(R.color.green));
            } else {
                if (valutation >= 3.2f && valutation <= 4.2f) {
                    star.setColorFilter(getResources().getColor(R.color.yellow));
                } else {
                    if (valutation > 2f && valutation < 3.2f) {
                        star.setColorFilter(getResources().getColor(R.color.orange));
                    } else {
                        if (valutation > 0f && valutation < 2) {
                            star.setColorFilter(getResources().getColor(R.color.red));
                        }
                    }
                }
            }
        }
    }


    /**Gestione del cambio di activity quando l'utente preme il tasto indietro**/
    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 1);
        if (callingActivity != null) {
            Intent goBack = new Intent(this.getApplicationContext(), callingActivity);
            startActivity(goBack);
        }
    }

    public int sommaView (ArrayList<Book> libri){

        int views=0;
        for (Book b: libri){

            views = views + b.getViews();
        }

        return views;

    }


}
