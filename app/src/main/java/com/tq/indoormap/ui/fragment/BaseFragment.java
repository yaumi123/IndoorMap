package com.tq.indoormap.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by niantuo on 2017/4/2.
 */

abstract class BaseFragment extends Fragment {

    private final static String TAG = BaseFragment.class.getSimpleName();

    private ProgressDialog mProgressDialog;

    protected abstract
    @LayoutRes
    int getLayoutResId();

    protected abstract void initView(View view);

    protected abstract void initData(Bundle savedInstanceState);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getLayoutResId();
        if (resId > 0) {
            return inflater.inflate(resId, container, false);
        } else {
            Log.i(TAG, "onCreateView: no layout");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData(savedInstanceState);
    }


    protected void showLoadingDialog(){
        AndroidSchedulers.mainThread()
                .scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        if (mProgressDialog==null){
                            mProgressDialog = new ProgressDialog(getContext());
                            mProgressDialog.show();;
                        }
                    }
                });

    }

    protected void hideLoadingDialog(){

        if (mProgressDialog!=null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(getContext(),getClass().getCanonicalName());
    }

    @Override
    public void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(getContext(),getClass().getCanonicalName());
    }
}
