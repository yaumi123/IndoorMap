package com.tq.indoormap.ui.activity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by niantuo on 2017/4/2.
 * 闪屏
 */

public class SplashActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return 0;
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Observable
                .timer(2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        toMain();
                    }
                });
    }


    private void toMain() {
        startActivity(LoginActivity.class);
        finish();
    }


}
