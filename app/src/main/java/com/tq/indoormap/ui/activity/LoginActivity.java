package com.tq.indoormap.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.tq.indoormap.R;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by niantuo on 2017/4/2.
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login_layout;
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_login)
    @Override
    public void onClick(View v) {
        showLoadingDialog();
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        hideLoadingDialog();
                        startActivity(HomeActivity.class);
                        finish();
                    }
                });

    }
}
