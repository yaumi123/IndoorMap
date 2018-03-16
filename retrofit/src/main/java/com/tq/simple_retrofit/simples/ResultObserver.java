package com.tq.simple_retrofit.simples;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by niantuo on 2017/2/26.
 */

public abstract class ResultObserver<T> implements Observer<T> {
    private final int ERR_SYS = -1000;
    private final String ERR_SYS_MSG = "system error";

    private final int ERR_UNKNOWN = -999;

    private final int requestCode;

    public ResultObserver() {
        this(11);
    }

    public ResultObserver(int requestCode) {
        this.requestCode = requestCode;
    }


    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(final T t) {
        if (t == null) {
            AndroidSchedulers.mainThread()
                    .scheduleDirect(new Runnable() {
                        @Override
                        public void run() {
                            onError(requestCode, ERR_SYS, ERR_SYS_MSG);
                        }
                    });
            return;
        }

        onSave(requestCode, t);

        AndroidSchedulers.mainThread()
                .scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(requestCode, t);
                    }
                });

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(final Throwable throwable) {
        AndroidSchedulers.mainThread()
                .scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        onError(requestCode, ERR_UNKNOWN, throwable.getMessage());
                    }
                });
    }


    protected void onSave(int requestCode, T data) {

    }

    protected abstract void onSuccess(int requestCode, T data);

    protected abstract void onError(int requestCode, int errCode, String errMsg);
}
