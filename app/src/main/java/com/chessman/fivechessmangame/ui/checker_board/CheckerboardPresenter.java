package com.chessman.fivechessmangame.ui.checker_board;

public interface CheckerboardPresenter {

    void onCreate();

    /**
     * 取得棋盤底部Y
     */
    void onCatchBottomTy(float bottomY);

    /**
     * 取得棋盤頂部Y
     */
    void onCatchTopY(float topY);

    void startToCountDownLocaleUser();

    void onGameOverCancel();

    void onGameOverPlayAgain();
}
