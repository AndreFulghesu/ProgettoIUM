package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.ScrollView;
import android.widget.TextView;

public class  MaxiLettura extends AppCompatActivity {

    /**Dichiarazione elementi del layout ed eventuali variabili d'istanza**/
    TextView testoIntero;
    final int classValue = 6;
    private float mScale = 1f;
    private ScaleGestureDetector mScaleDetector;
    GestureDetector gestureDetector;
    Chapter attuale;
    private int bookId,chapId;
    long startTime;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Gestione richiesta sessione dalla classe
         * ed eventualmente gestione dell'eccezione lanciata**/
        final UserSession userSession = new UserSession(this);
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
        } catch (NullPointerException e) {
            System.out.println("Errore trasmissione sessione");
            finish();
        }
        try {
            bookId = userSession.getBookId();
            chapId = userSession.getChapId();
            attuale = ChapterFactory.getInstance().getChapterByChapNum(chapId,bookId);
        } catch (NullPointerException e) {
            startActivity(new Intent (getApplicationContext(), Home.class));
        }

        /**Gestione del tema dell'applicazione**/
        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }

        setContentView(R.layout.activity_maxi_lettura);


        /**Gestione del layout e della funzionalità per la modifica di
         * dimensione della View con le gesture
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        testoIntero = findViewById(R.id.testoIntero);

        testoIntero.setText(attuale.getText());

        startTime = System.currentTimeMillis();

        gestureDetector = new GestureDetector(this, new GestureListener());

        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = 1 - detector.getScaleFactor();

                float prevScale = mScale;
                mScale += scale;

                if (mScale < 0.1f) // Minimum scale condition:
                    mScale = 0.1f;

                if (mScale > 10f) // Maximum scale condition:
                    mScale = 10f;
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f / mScale, 1f / prevScale, 1f / mScale, detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                ScrollView layout = findViewById(R.id.scrollText);
                layout.startAnimation(scaleAnimation);
                return true;
            }
        });
    }

    /**Metodi usati per la gestione della dimensione della View con le gesture*/
    @Override
    public boolean dispatchTouchEvent (MotionEvent event){
        super.dispatchTouchEvent(event);
        mScaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }


    private static class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            return true;
        }
    }

    /**Gestione pressione tasto Indietro*/
    @Override
    public void onBackPressed() {
        UserSession userSession = new UserSession(getApplicationContext());
        Class callingActivity = userSession.getActivityFromValue(classValue - 2);
        if (callingActivity != null) {
            Intent goBack = new Intent(getApplicationContext(), callingActivity);
            goBack.putExtra("bookId", bookId);
            goBack.putExtra("chapId", chapId);
            long endTime = System.currentTimeMillis() - startTime;
            if (endTime> 30000) {
                BookFactory.getInstance().getBookById(userSession.getBookId()).incrementViews();
            }
            startActivity(goBack);
        }
    }
}




