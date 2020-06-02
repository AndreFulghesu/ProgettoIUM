package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    ImageView logo;
    TextView scrittaLogo,sottoScritta, scrittaBellina;
    private static int timeOut = 5500;
    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_start);

        logo = findViewById(R.id.logo);
        scrittaLogo = findViewById(R.id.scrittaLogo);
        scrittaBellina = findViewById(R.id.scrittaBellina);
        sottoScritta = findViewById(R.id.sottoscritta);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, timeOut);



        logo.setAnimation(topAnim);
        scrittaLogo.setAnimation(bottomAnim);
        sottoScritta.setAnimation(bottomAnim);
        scrittaBellina.setAnimation(bottomAnim);
    }
}

