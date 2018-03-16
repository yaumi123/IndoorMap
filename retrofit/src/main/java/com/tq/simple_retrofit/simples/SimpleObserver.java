package com.tq.simple_retrofit.simples;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by niantuo on 2017/2/26.
 */

public abstract class SimpleObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

}
