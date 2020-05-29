package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {

    int problemId;
    User user;
    TextView textSopraBarraRicerca;
    TextView spinnerSelected;
    SearchView searchView;
    ListView reportSearched;
    Book bookSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final UserSession userSession = new UserSession(this);

        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }
        setContentView(R.layout.activity_report);

        final Spinner reportSpinner = findViewById(R.id.spinner_report);

        spinnerSelected = findViewById(R.id.report_spinner_selected);
        textSopraBarraRicerca = findViewById(R.id.text_sopra_report_search);

        Intent intent = getIntent();
        Serializable objReport = intent.getSerializableExtra("reportId");

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.reportbar);
        setSupportActionBar(toolbar);
        if (userSession.getTheme() == false) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.gradient2));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_black));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarGrey));
            toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
        /**toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });*/

        if (objReport != null) {
            problemId = (int) objReport;
            switch (problemId){
                case 0:
                    spinnerSelected.setText("Segnalazione: Bug o problemi di sistema");
                    break;
                case 1:
                    spinnerSelected.setText("Segnalazione: Utente Scorretto");
                    break;
                case 2:
                    spinnerSelected.setText("Segnalazione: Violazione di proprietà intellettuale");
                    break;
                case 3:
                    spinnerSelected.setText("Segnalazione: Errori di testo");
                    break;
            }
        }

        ArrayList<String> choices = new ArrayList<>();
        choices.add("Bug o problemi di sistema");
        choices.add("Utente scorretto");
        choices.add("Violazione di proprietà intellettuale");
        choices.add("Errori di testo");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_item, R.id.testoMenu, choices);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        reportSpinner.setAdapter(adapter);

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                switch (str) {
                    case "Bug o problemi di sistema":
                        spinnerSelected.setText("Segnalazione: Bug o problemi di sistema");
                        problemId = 0;
                        break;
                    case "Utente scorretto":
                        problemId = 1;
                        spinnerSelected.setText("Segnalazione: Utente Scorretto");
                        break;
                    case "Violazione di proprietà intellettuale":
                        spinnerSelected.setText("Segnalazione: Violazione di proprietà intellettuale");
                        problemId = 2;
                        break;
                    case "Errori di testo":
                        spinnerSelected.setText("Segnalazione: Errori di testo");
                        problemId = 3;
                        break;
                    default:
                        problemId = -1;
                        spinnerSelected.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reportSearched = findViewById(R.id.report_searched);
        searchView = findViewById(R.id.search_report_item);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (bookSelected != null) {
                    ArrayList<Book> newFiltered = new ArrayList<>();
                    newFiltered.add(bookSelected);
                    BookAdapterSearch adapter = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, newFiltered);
                    reportSearched.setAdapter(adapter);
                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (problemId == 2 || problemId == 3) {
                    ArrayList<Book> filtered = new ArrayList<>();
                    ArrayList<Book> books;
                    books = BookFactory.getInstance().getBooks();
                    if (newText != null && !newText.isEmpty()) {
                        for (Book b : books) {
                            if (b.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                                System.out.println(b.getTitle());
                                filtered.add(b);
                            }
                        }
                        BookAdapterSearch adapter = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, filtered);
                        reportSearched.setAdapter(adapter);
                    } else {
                        BookAdapterSearch adapter = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, filtered);
                        books.clear();
                        books = BookFactory.getInstance().getBooks();
                        adapter.notifyDataSetChanged();
                        reportSearched.setAdapter(adapter);
                    }
                } else
                    if (problemId == 1) {
                        ArrayList<User> filtered = new ArrayList<>();
                        ArrayList<User> users;
                        users = UserFactory.getInstance().getUsers();
                        if (newText != null && !newText.isEmpty()) {
                            for (User u : users) {
                                if (u.getUsername().toLowerCase().contains(newText.toLowerCase())) {
                                    filtered.add(u);
                                }
                            }
                            CustomUserAdapter adapter = new CustomUserAdapter(getApplicationContext(), R.layout.row_username, filtered);
                            reportSearched.setAdapter(adapter);
                        } else {
                            CustomUserAdapter adapter = new CustomUserAdapter(getApplicationContext(), R.layout.row_username, filtered);
                            users.clear();
                            users = UserFactory.getInstance().getUsers();
                            adapter.notifyDataSetChanged();
                            reportSearched.setAdapter(adapter);
                        }
                    }
                    return true;
                }
        });

        reportSearched.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Book> book = new ArrayList<>();
                bookSelected = (Book) parent.getItemAtPosition(position);
                book.add(bookSelected);
                BookAdapterSearch selectedItem = new BookAdapterSearch(getApplicationContext(), R.layout.bookitem, book);
                reportSearched.setAdapter(selectedItem);
            }
        });

    }
}
