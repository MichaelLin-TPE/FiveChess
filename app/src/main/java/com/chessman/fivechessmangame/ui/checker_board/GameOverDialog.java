package com.chessman.fivechessmangame.ui.checker_board;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chessman.fivechessmangame.R;

public class GameOverDialog extends DialogFragment {

    private OnGameOverDialogClickListener onGameOverDialogClickListener;

    public void setOnGameOverDialogClickListener(OnGameOverDialogClickListener onGameOverDialogClickListener){
        this.onGameOverDialogClickListener = onGameOverDialogClickListener;
    }

    public static GameOverDialog newInstance() {

        Bundle args = new Bundle();
        GameOverDialog fragment = new GameOverDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_over_layout, null);
        Dialog dialog = new Dialog(getActivity());
        // 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        initView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 400, getActivity().getResources().getDisplayMetrics()));
//        wlp.height = Math.round(TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, 250, getActivity().getResources().getDisplayMetrics()));
        ;
        window.setAttributes(wlp);
        return dialog;
    }

    private void initView(View view) {
        setCancelable(false);
        TextView tvCancel = view.findViewById(R.id.back_button);
        TextView tvPlay = view.findViewById(R.id.play_again_button);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGameOverDialogClickListener.onCancel();
            }
        });

        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGameOverDialogClickListener.onPlayAgain();
            }
        });
    }

    public interface OnGameOverDialogClickListener{
        void onCancel();
        void onPlayAgain();
    }


}
