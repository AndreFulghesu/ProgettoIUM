package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class FormCommento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_commento);

        RatingBar bar = findViewById(R.id.ratingBar);
        final TextView rateMessage = findViewById(R.id.rateMessage);
        EditText feedbackMessage = findViewById(R.id.feedbackMessage);
        Button feedbackSubmit = findViewById(R.id.feedbackSubmit);

        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateMessage.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        rateMessage.setText("Il peggior capitolo che io abbia letto...");
                        break;
                    case 2:
                        rateMessage.setText("Si può migliorare...");
                        break;
                    case 3:
                        rateMessage.setText("Un buon capitolo.");
                        break;
                    case 4:
                        rateMessage.setText("Un ottimo capitolo!");
                        break;
                    case 5:
                        rateMessage.setText("Il miglior capitolo letto ultimamente!");
                        break;
                    default:
                        rateMessage.setText("");
                }
            }
        });
        feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbacks = new Intent(FormCommento.this, Commenti.class);
                startActivity(feedbacks);
            }
        });
    }
}
