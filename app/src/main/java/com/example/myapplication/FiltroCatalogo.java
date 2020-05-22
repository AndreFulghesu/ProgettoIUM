package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FiltroCatalogo extends AppCompatActivity  {

    int ordinamentoId;
    Genres filterGenre;
    int bookId;
    User user;
    ArrayList<String> choices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserSession userSession = new UserSession(this);

        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }
        setContentView(R.layout.activity_filtro_catalogo);

        Button salvaFiltri = findViewById(R.id.salvaFiltri);
        Intent intent = getIntent();
        Serializable bookObj = intent.getSerializableExtra("bookId");
        Serializable userObj = intent.getSerializableExtra("User");
        final Spinner ordSpinner = findViewById(R.id.spinner_ordinamento);
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }
        if (bookObj != null) {
            bookId = (int) bookObj;
        }


        choices.add("Ordina per suggeriti di sistema (default)");
        choices.add("Ordina per valutazione");
        choices.add("Ordina per numero di visualizzazioni");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height =dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int)(height*.6));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_item, R.id.testoMenu, choices);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ordSpinner.setAdapter(adapter);

        ordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                switch (str) {
                    case "Ordina per suggeriti di sistema (default)":
                        ordinamentoId = 0;
                        break;
                    case "Ordina per valutazione":
                        ordinamentoId = 1;
                        break;
                    case "Ordina per numero di visualizzazioni":
                        ordinamentoId = 2;
                        break;
                    default:
                        ordinamentoId = -1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        salvaFiltri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getFilter = new Intent(getApplicationContext(), Catalogo.class);
                if (ordinamentoId != -1)
                    getFilter.putExtra("ORDINAMENTO", ordinamentoId);
                if (filterGenre != null)
                    getFilter.putExtra("GENERE_FILTRAGGIO", filterGenre);
                startActivity(getFilter);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.fantascienza_radiobutton:
                if (checked)
                    filterGenre = Genres.FANTASCIENZA;
                break;
            case R.id.fantasy_radiobutton:
                if (checked)
                    filterGenre = Genres.FANTASY;
                break;
            case R.id.horror_radiobutton:
                if (checked)
                    filterGenre = Genres.HORROR;
                break;
            case R.id.poliziesco_radiobutton:
                if (checked)
                    filterGenre = Genres.POLIZIESCO;
                break;
            case R.id.storico_radiobutton:
                if (checked)
                    filterGenre = Genres.STORICO;
                break;
            case R.id.thriller_radiobutton:
                if (checked)
                    filterGenre = Genres.THRILLER;
                break;
            default:
                filterGenre = null;
        }
    }
}
