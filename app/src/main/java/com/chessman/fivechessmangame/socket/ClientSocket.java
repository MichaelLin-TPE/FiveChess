package com.chessman.fivechessmangame.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

    private static final String HOST = "192.168.0.13";
    private static final int PORT = 12345;
    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static PrintWriter printWriter;

    private static OnSocketConnectionListener onSocketConnectionListener;

    public static void setOnSocketConnectionListener(OnSocketConnectionListener listener){
        onSocketConnectionListener = listener;
    }

    public static void startConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(HOST,PORT);
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                    while (true){
                        if (socket.isConnected() && !socket.isInputShutdown() && bufferedReader.readLine() != null){
                            onSocketConnectionListener.onReceivedMsg(bufferedReader.readLine());
                        }
                    }
                }catch (Exception e){
                    onSocketConnectionListener.onFail("連線失敗,請稍後再試");
                }
            }
        }).start();
    }

    public static void sendMsg(String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (socket.isConnected() && !socket.isOutputShutdown()){
                    printWriter.println(msg);
                }
            }
        }).start();
    }


    public interface OnSocketConnectionListener{
        void onSuccessful();
        void onFail(String error);
        void onReceivedMsg(String msg);
    }


}
