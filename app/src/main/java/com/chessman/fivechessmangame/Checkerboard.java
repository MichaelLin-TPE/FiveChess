package com.chessman.fivechessmangame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.chessman.fivechessmangame.bean.ChessData;
import com.chessman.fivechessmangame.bean.InputData;
import com.chessman.fivechessmangame.tool.Tool;

import java.util.ArrayList;

public class Checkerboard extends View {

    private Canvas canvas;

    private final ArrayList<ChessData> allCheckerboardList = new ArrayList<>();
    private static final float CHESS_SIZE = 40f;
    private final ArrayList<InputData> userInputDataList = new ArrayList<>();
    private boolean isBlackFirst = true;
    private static final int WHITE_SIDE = 1;
    private static final int BLACK_SIDE = 2;
    private static final int EMPTY_SIDE = 0;
    private OnCheckerboardListener onCheckerboardListener;


    public void setOnCheckerboardListener(OnCheckerboardListener onCheckerboardListener){
        this.onCheckerboardListener = onCheckerboardListener;
    }

    public Checkerboard(Context context) {
        super(context);
        initView();
    }

    public Checkerboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Checkerboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float pressX = event.getX();
                float pressY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (isInPressChessRange(pressX, pressY)) {
                            putChessOnBoard();
                        }
                        break;
                }

                return true;
            }
        });
    }

    //放棋子 資料在 isInPressChessRange 的時候就建制好了
    private void putChessOnBoard() {
        invalidate();
    }

    private boolean isInPressChessRange(float pressX, float pressY) {
        boolean isCanBePress = false;
        Log.i("Michael","pressX : "+pressX + " pressY : "+pressY);
        for (ChessData data : allCheckerboardList) {
            Log.i("Michael","x : "+data.getChessX() + " y : "+data.getChessY());
            if (Math.abs(data.getChessX() - pressX) < 50 && Math.abs(data.getChessY() - pressY) < 50 && !data.isAlreadyPressed()) {
                data.setAlreadyPressed(true);
                data.setPlayerType(isBlackFirst ? BLACK_SIDE : WHITE_SIDE);
                addUserInputData(data.getChessX(), data.getChessY());
                isCanBePress = true;
                break;
            }
        }
        return isCanBePress;
    }

    private void addUserInputData(float chessX, float chessY) {
        InputData data = new InputData();
        data.setTargetX(chessX);
        data.setTargetY(chessY);
        data.setPlayerType(isBlackFirst ? BLACK_SIDE : WHITE_SIDE);
        userInputDataList.add(data);
        isBlackFirst = !isBlackFirst;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        float topY = (Tool.getScreenHeight() - Tool.getScreenWidth()) / 2f;
        float stopY = topY + (Tool.getScreenWidth() - 30f - 30f);
        //繪製背景
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.parseColor("#C1A171"));
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0f, topY-50f, Tool.getScreenWidth(), stopY+50f, bgPaint);

        Log.i("Michael","TopY : "+topY);
        //top line
        canvas.drawLine(30f, topY, Tool.getScreenWidth() - 30f, topY, paint);
        //left line
        canvas.drawLine(30f, topY, 30f, stopY, paint);
        //right line
        canvas.drawLine(Tool.getScreenWidth() - 30f, topY, Tool.getScreenWidth() - 30f, stopY, paint);
        //bottom line
        canvas.drawLine(30f, stopY, Tool.getScreenWidth() - 30f, stopY, paint);

        //開始畫棋盤
        float singleItemWidth = ((Tool.getScreenWidth() - 60f) / 14f);

        //縱向
        for (int i = 1; i <= 13; i++) {
            canvas.drawLine(singleItemWidth * i + 30f, topY, singleItemWidth * i + 30f, stopY, paint);
        }

        //橫向
        for (int i = 1; i <= 13; i++) {
            canvas.drawLine(30f, topY + singleItemWidth * i, Tool.getScreenWidth() - 30f, topY + singleItemWidth * i, paint);
        }

        //蒐集棋盤所有點的座標
        if (allCheckerboardList.isEmpty()) {
            float firstX = 30f;
            int portraitIndex = 0;
            int horizontalIndex = 0;
            for (int i = 0; i < 225; i++) {
                if (horizontalIndex != 0 && horizontalIndex % 15 == 0) {
                    horizontalIndex = 0;
                    portraitIndex++;
                }
                ChessData data = new ChessData();
                data.setChessX(firstX + (singleItemWidth * horizontalIndex));
                data.setChessY(topY + (singleItemWidth * portraitIndex));
                data.setAlreadyPressed(false);
                allCheckerboardList.add(data);
                horizontalIndex++;
            }
        }


        //中心點
        float centerPointX = ((Tool.getScreenWidth()) / 2f);
        float centerPointY = topY + ((stopY - topY) / 2);
        canvas.drawCircle(centerPointX, centerPointY, 10f, paint);

        //右上點
        float leftTopPointX = singleItemWidth * 3 + 30f;
        float leftTopPointY = topY + singleItemWidth * 3;
        canvas.drawCircle(leftTopPointX, leftTopPointY, 10f, paint);

        //左上點
        float rightTopPointX = singleItemWidth * 11 + 30f;
        float rightTopPointY = topY + singleItemWidth * 3;
        canvas.drawCircle(rightTopPointX, rightTopPointY, 10f, paint);

        //左下點
        float rightBottomPointX = singleItemWidth * 11 + 30f;
        float rightBottomPointY = topY + singleItemWidth * 11;
        canvas.drawCircle(rightBottomPointX, rightBottomPointY, 10f, paint);

        //右下點
        float leftBottomPointX = singleItemWidth * 3 + 30f;
        float leftBottomPointY = topY + singleItemWidth * 11;
        canvas.drawCircle(leftBottomPointX, leftBottomPointY, 10f, paint);

        //下棋
        if (userInputDataList.isEmpty()) {
            return;
        }
        Paint chessPaint = new Paint();
        for (InputData data : userInputDataList) {
            chessPaint.setColor(data.getPlayerType() == BLACK_SIDE ? Color.BLACK : Color.WHITE);
            canvas.drawCircle(data.getTargetX(), data.getTargetY(), CHESS_SIZE, chessPaint);
        }
        checkWhoWins();
    }

    //檢查是否有用戶贏了
    private void checkWhoWins() {

        int blackHorizontalIndex = 0, whiteHorizontalIndex = 0; //橫向
        int blackPortraitIndex = 0, whitePortraitIndex = 0; //直向
        boolean isBlackWin = false;
        boolean isWhiteWin = false;
        for (ChessData data : allCheckerboardList) {
            int index = 0;
            for (ChessData chessData : allCheckerboardList) {
                //判斷是否有橫排已經5顆黑棋子了
                if (data.getChessY() == chessData.getChessY() && chessData.isAlreadyPressed() && chessData.getPlayerType() == BLACK_SIDE) {
                    blackHorizontalIndex++;
                    if (blackHorizontalIndex == 5) {
                        isBlackWin = true;
                        break;
                    }
                } else if (data.getChessY() == chessData.getChessY() && chessData.isAlreadyPressed() &&
                        (chessData.getPlayerType() == WHITE_SIDE || chessData.getPlayerType() == EMPTY_SIDE)) {
                    blackHorizontalIndex = 0;
                }
                //判斷是否有橫排已經5顆白棋子了
                if (data.getChessY() == chessData.getChessY() && chessData.isAlreadyPressed() && chessData.getPlayerType() == WHITE_SIDE) {
                    whiteHorizontalIndex++;
                    if (whiteHorizontalIndex == 5) {
                        isWhiteWin = true;
                        break;
                    }
                } else if (data.getChessY() == chessData.getChessY() && chessData.isAlreadyPressed() &&
                        (chessData.getPlayerType() == BLACK_SIDE || chessData.getPlayerType() == EMPTY_SIDE)) {
                    whiteHorizontalIndex = 0;
                }
                //判斷是否有直排已經5顆黑棋子了
                if (data.getChessX() == chessData.getChessX() && chessData.isAlreadyPressed() && chessData.getPlayerType() == BLACK_SIDE) {
                    blackPortraitIndex++;
                    if (blackPortraitIndex == 5) {
                        isBlackWin = true;
                        break;
                    }
                } else if (data.getChessX() == chessData.getChessX() && chessData.isAlreadyPressed() &&
                        (chessData.getPlayerType() == WHITE_SIDE || chessData.getPlayerType() == EMPTY_SIDE)) {
                    blackPortraitIndex = 0;
                }
                //判斷是否有直排已經5顆白棋子了
                if (data.getChessX() == chessData.getChessX() && chessData.isAlreadyPressed() && chessData.getPlayerType() == WHITE_SIDE) {
                    whitePortraitIndex++;
                    if (whitePortraitIndex == 5) {
                        isWhiteWin = true;
                        break;
                    }
                } else if (data.getChessX() == chessData.getChessX() && chessData.isAlreadyPressed() &&
                        (chessData.getPlayerType() == BLACK_SIDE || chessData.getPlayerType() == EMPTY_SIDE)) {
                    whitePortraitIndex = 0;
                }

                //判斷斜向白子是否五顆了 先判斷誰的Y比較大
                if (chessData.isAlreadyPressed() && chessData.getPlayerType() == WHITE_SIDE && checkChessRight(index,WHITE_SIDE)){
                    isWhiteWin = true;
                    break;
                }
                if (chessData.isAlreadyPressed() && chessData.getPlayerType() == WHITE_SIDE && checkChessLeft(index,WHITE_SIDE)){
                    isWhiteWin = true;
                    break;
                }


                if (chessData.isAlreadyPressed() && chessData.getPlayerType() == BLACK_SIDE && checkChessRight(index,BLACK_SIDE)){
                    isWhiteWin = true;
                    break;
                }

                if (chessData.isAlreadyPressed() && chessData.getPlayerType() == BLACK_SIDE && checkChessLeft(index,BLACK_SIDE)){
                    isWhiteWin = true;
                    break;
                }


                index ++;
            }
            blackPortraitIndex = 0;
            whitePortraitIndex = 0;
            blackHorizontalIndex = 0;
            whiteHorizontalIndex = 0;
            if (isBlackWin) {
                break;
            }
            if (isWhiteWin) {
                break;
            }
        }
        if (isWhiteWin) {
            isBlackFirst = true;
            allCheckerboardList.clear();
            userInputDataList.clear();
            invalidate();
            Log.i("Michael", "白子勝利");
            onCheckerboardListener.onWinListener(WHITE_SIDE);
            return;
        }

        if (isBlackWin) {
            isBlackFirst = true;
            allCheckerboardList.clear();
            userInputDataList.clear();
            invalidate();
            onCheckerboardListener.onWinListener(BLACK_SIDE);
            Log.i("Michael", "黑子獲勝");
        }
    }

    private boolean checkChessLeft(int index , int type) {
        int winIndex = 0;
        for (int i = index ; i < allCheckerboardList.size() ; i += 14){
            ChessData data = allCheckerboardList.get(i);
            if (data.isAlreadyPressed() && data.getPlayerType() == type){
                winIndex ++;
            }else if (data.isAlreadyPressed() && data.getPlayerType() != type){
                break;
            }
        }

        for (int i = index ; i >= 0 ; i -= 14){
            ChessData data = allCheckerboardList.get(i);
            if (data.isAlreadyPressed() && data.getPlayerType() == type){
                winIndex ++;
            }else if (data.isAlreadyPressed() && data.getPlayerType() != type){
                break;
            }
        }
        return winIndex >= 6;
    }


    private boolean checkChessRight(int index , int type) {
        int winIndex = 0;
        for (int i = index ; i < allCheckerboardList.size() ; i += 16){
            ChessData data = allCheckerboardList.get(i);
            if (data.isAlreadyPressed() && data.getPlayerType() == type){
                winIndex ++;
            }else if (data.isAlreadyPressed() && data.getPlayerType() != type){
                break;
            }
        }

        for (int i = index ; i >= 0 ; i -= 16){
            ChessData data = allCheckerboardList.get(i);
            if (data.isAlreadyPressed() && data.getPlayerType() == type){
                winIndex ++;
            }else if (data.isAlreadyPressed() && data.getPlayerType() != type){
                break;
            }
        }
        return winIndex >= 6;
    }


    public interface OnCheckerboardListener{
        void onWinListener(int type);
    }

}
