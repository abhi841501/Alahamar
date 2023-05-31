package com.example.alahamar.AlahamarActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.alahamar.R;

public class SplashScreen extends AppCompatActivity {
    ImageView splashscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashscreen=findViewById(R.id.splashscreen);
        //startSplashTimer();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                callActivity();
            }
        }, 3000);
    }

    private void callActivity() {
        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
