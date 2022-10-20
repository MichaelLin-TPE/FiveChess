package com.chessman.fivechessmangame.ui.checker_board;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chessman.fivechessmangame.R;

public class CheckerboardActivity extends AppCompatActivity implements CheckerboardView {

    //棋盤
    private Checkerboard checkerboard;
    //配對圈圈
    private ProgressBar searchProgressBar;
    //配對資訊
    private TextView tvSearchContent;
    //整體的VIEW
    private ConstraintLayout rootView;
    //Presenter
    private CheckerboardPresenter presenter;
    //自己的倒數計時
    private TextView tvLocaleUserCountDown;
    //自己的倒數計時圈圈
    private ProgressBar localeUserProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
        initView();
        presenter.onCreate();
        Log.i("Michael","onCreate");
    }

    private void initPresenter() {
        presenter = new CheckerboardPresenterImpl(this);
    }

    private void initView() {
        searchProgressBar = findViewById(R.id.progress);
        tvSearchContent = findViewById(R.id.title);
        rootView = findViewById(R.id.root_view);
        searchProgressBar.setVisibility(View.GONE);
        tvSearchContent.setVisibility(View.GONE);


    }

    @Override
    public void showCheckerboard() {
        checkerboard = new Checkerboard(this);
        rootView.addView(checkerboard);
        checkerboard.setOnCheckerboardListener(new Checkerboard.OnCheckerboardListener() {
            @Override
            public void onWinOrLose(int type) {

            }

            @Override
            public void onCatchTopYAndBottomY(float topY, float bottomY) {
                Log.i("Michael","onCatchTopYAndBottomY");
                presenter.onCatchBottomTy(bottomY);
                presenter.onCatchTopY(topY);
            }
        });
    }

    //顯示自己
    @Override
    public void showLocaleUser(float bottomY) {
        View view = View.inflate(this, R.layout.left_user_layout, null);
        rootView.addView(view);
        view.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        view.setY(bottomY + 100f);
        tvLocaleUserCountDown = view.findViewById(R.id.count_down);
        localeUserProgressBar = view.findViewById(R.id.count_down_progress);
        Log.i("Michael","showLocaleUser");
//        presenter.startToCountDownLocaleUser();

    }

    //顯示對手
    @Override
    public void showOtherUser(float topY) {
        View view = View.inflate(this, R.layout.right_user_layout, null);
        rootView.addView(view);
        view.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setY(topY - ((view.getBottom() - view.getTop())) - 100f);
            }
        });

    }

    @Override
    public void showLocaleCountDown(boolean isShow) {
        tvLocaleUserCountDown.setVisibility(isShow ? View.VISIBLE : View.GONE);
        localeUserProgressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLocaleCountDownValue(String time) {
        tvLocaleUserCountDown.setText(time);
    }

    @Override
    public void showGameOverDialog() {

    }
}