package com.chessman.fivechessmangame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Checkerboard checkerboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkerboard = findViewById(R.id.checkerboard);

        checkerboard.setOnCheckerboardListener(new Checkerboard.OnCheckerboardListener() {
            @Override
            public void onWinListener(int type) {

            }
        });

    }
}