package com.chessman.fivechessmangame.ui.checker_board;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class CheckerboardPresenterImpl implements CheckerboardPresenter{


    private CheckerboardView mView;

    private static final int TIMER_VALUE = 30;

    public CheckerboardPresenterImpl(CheckerboardView mView) {
        this.mView = mView;
    }

    @Override
    public void onCreate() {
        mView.showCheckerboard();
    }

    @Override
    public void onCatchBottomTy(float bottomY) {
        mView.showLocaleUser(bottomY);
    }

    @Override
    public void onCatchTopY(float topY) {
        mView.showOtherUser(topY);
    }

    @Override
    public void startToCountDownLocaleUser() {

        Observable.interval(1, TimeUnit.SECONDS)
                .take(30)
                .map(v -> 29 - v)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mView.showLocaleCountDown(true);
                        mView.setLocaleCountDownValue(String.valueOf(aLong));
                    }
                })
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) {
                        Log.i("Michael","time : "+aLong);
                        return aLong == 0;
                    }
                })
                .doOnComplete(()->{
                    mView.showGameOverDialog();
                    Log.i("Michael","完成倒數");
                }).subscribe();

    }
}
