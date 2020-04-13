package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class Commenti extends AppCompatActivity {

    ListView lista;
    ArrayList<String> elenco = new ArrayList<>();
    ArrayList<String> descrizioni = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.lista1);

        elenco.add("Gianni vitale");
        elenco.add("gesu il nazareno");
        elenco.add("fabrizio sau");
        elenco.add("Sviluppatore1");

        descrizioni.add("commento molto molto bello");
        descrizioni.add("io sicuramente potevo scriverlo meglio");
        descrizioni.add("io ho scritto un libro bellissimo invece, ma ho voglia di scrivere un commento molto ma molto esteso" +
                "per vedere se quei bastardi di sviluppatori hanno gestito bene la cosa");
        descrizioni.add("Oh no ma guarda che bello");


        MyAdapter adatt = new MyAdapter(this, elenco, descrizioni);
        lista.setAdapter(adatt);

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context contesto;
        ArrayList<String> elenco_autori = new ArrayList<>();
        ArrayList<String> elenco_descrizione = new ArrayList<>();

        MyAdapter (Context c, ArrayList<String> autori, ArrayList<String> descrizione){
            super(c,R.layout.row,R.id.autore,autori);
            this.contesto = c;
            this.elenco_autori = autori;
            this.elenco_descrizione = descrizione;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row,parent,false);
            TextView autore = row.findViewById(R.id.autore);
            TextView descrizione = row.findViewById(R.id.contenuto);

            autore.setText(elenco_autori.get(position));
            descrizione.setText(elenco_descrizione.get(position));


            return row;

        }
    }
}
