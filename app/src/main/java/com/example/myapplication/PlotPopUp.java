package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class PlotPopUp extends Activity {

    int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_popup);

        Intent intent = getIntent();
        final Serializable bookObj = intent.getSerializableExtra("bookId");
        if (bookObj != null) {
            bookId = (int) bookObj;
        }
        TextView plotText = findViewById(R.id.plottext);
        plotText.setText(BookFactory.getInstance().getBookById(bookId).getPlot());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height =dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int)(height*.6));
        TextView popUpClose = findViewById(R.id.popupclose);
        popUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(PlotPopUp.this, ChapterList.class);
                goBack.putExtra("bookId", bookId);
                goBack.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goBack);
            }
        });
    }
}