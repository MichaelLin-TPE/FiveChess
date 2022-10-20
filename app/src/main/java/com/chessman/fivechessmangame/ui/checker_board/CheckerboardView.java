package com.chessman.fivechessmangame.ui.checker_board;

public interface CheckerboardView {
    void showCheckerboard();

    void showLocaleUser(float bottomY);

    void showOtherUser(float topY);

    void showLocaleCountDown(boolean isShow);

    void setLocaleCountDownValue(String time);

    void showGameOverDialog();
}
