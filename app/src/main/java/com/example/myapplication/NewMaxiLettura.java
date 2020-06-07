package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import com.androidessence.pinchzoomtextview.PinchZoomTextView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NewMaxiLettura extends Activity {

    PinchZoomTextView chapText;
    TextView nextChapter;
    float ratio = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final UserSession userSession = new UserSession(getApplicationContext());

        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }

        setContentView(R.layout.activity_new_maxi_lettura);

        final int chapId = userSession.getChapId();
        final int bookId = userSession.getBookId();
        chapText = findViewById(R.id.testoIntero);
        nextChapter = findViewById(R.id.nextChapter);
        chapText.setText(ChapterFactory.getInstance().getChapterByChapNum(chapId, bookId).getText());
        chapText.setTextSize(ratio + 13);
        nextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChapterFactory.getInstance().getChapterByChapNum(chapId+1, bookId) != null) {
                    userSession.setChapId(chapId + 1);
                    Intent nextChapter = new Intent(getApplicationContext(), NewMaxiLettura.class);
                    nextChapter.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(nextChapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Il capitolo richiesto non esiste.", Toast.LENGTH_LONG+1).show();
                }
            }
        });
    }
}