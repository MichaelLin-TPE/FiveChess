package com.chessman.fivechessmangame.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.chessman.fivechessmangame.application.MyApplication;

public class Tool {

    public static Context getContext(){
        return MyApplication.instance.getApplicationContext();
    }

    public static int getScreenWidth(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.heightPixels;
    }

}
