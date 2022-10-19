package com.chessman.fivechessmangame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chessman.fivechessmangame.R;
import com.chessman.fivechessmangame.checker_board.Checkerboard;

public class CheckerboardActivity extends AppCompatActivity {

    private Checkerboard checkerboard;

    private ProgressBar progressBar;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();



    }

    private void initView() {
        checkerboard = findViewById(R.id.checkerboard);
        progressBar = findViewById(R.id.progress);
        tvContent = findViewById(R.id.title);

        progressBar.setVisibility(View.GONE);
        tvContent.setVisibility(View.GONE);
        checkerboard.setVisibility(View.VISIBLE);

        checkerboard.setOnCheckerboardListener(new Checkerboard.OnCheckerboardListener() {
            @Override
            public void onWinOrLose(int type) {

            }

            @Override
            public void onCatchTopYAndBottomY(float topY, float bottomY) {

            }
        });

    }
}