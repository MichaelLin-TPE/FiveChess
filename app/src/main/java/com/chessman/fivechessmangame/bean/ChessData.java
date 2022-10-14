package com.chessman.fivechessmangame.bean;

public class ChessData {

    private float chessX;
    private float chessY;
    private boolean isAlreadyPressed;
    private int playerType;

    public int getPlayerType() {
        return playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public float getChessX() {
        return chessX;
    }

    public void setChessX(float chessX) {
        this.chessX = chessX;
    }

    public float getChessY() {
        return chessY;
    }

    public void setChessY(float chessY) {
        this.chessY = chessY;
    }

    public boolean isAlreadyPressed() {
        return isAlreadyPressed;
    }

    public void setAlreadyPressed(boolean alreadyPressed) {
        isAlreadyPressed = alreadyPressed;
    }
}
