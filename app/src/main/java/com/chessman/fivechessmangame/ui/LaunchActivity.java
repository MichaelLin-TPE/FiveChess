package com.chessman.fivechessmangame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.chessman.fivechessmangame.R;
import com.chessman.fivechessmangame.ui.checker_board.CheckerboardActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(LaunchActivity.this, CheckerboardActivity.class);
                startActivity(it);
                finish();
            }
        },2000);
    }
}