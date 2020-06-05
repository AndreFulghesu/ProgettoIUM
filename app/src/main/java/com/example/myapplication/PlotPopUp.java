package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

/**Activity popup per la visualizzazione della trama di un libro*/
public class PlotPopUp extends Activity {

    /**Variabili d'istanza*/
    int bookId;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Gestione richiesta sessione dalla classe
         * ed eventualmente gestione dell'eccezione lanciata**/
        final UserSession userSession = new UserSession(this);
        try {
            user = UserFactory.getInstance().getUserByUsername(userSession.getUserSession());
            bookId = userSession.getBookId();
        } catch (NullPointerException e) {
            finish();
        }

        /**Gestione del tema dell'applicazione**/
        if (!userSession.getTheme()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.darkTheme);
        }

        setContentView(R.layout.plot_popup);

        TextView plotText = findViewById(R.id.plottext);
        plotText.setText(BookFactory.getInstance().getBookById(bookId).getPlot());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        /**Gestione dimensionw pagina*/
        int width =dm.widthPixels;
        int height =dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int)(height*.6));
        TextView popUpClose = findViewById(R.id.popupclose);

        /**Gestione chiusura pagina*/
        popUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(PlotPopUp.this, ChapterList.class);
                goBack.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(goBack);
            }
        });
    }
}
