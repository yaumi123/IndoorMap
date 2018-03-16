package com.tq.indoormap.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tq.indoormap.R;

import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by niantuo on 2017/4/2.
 * activity的基类
 */

abstract class BaseActivity extends AppCompatActivity {

    final static String TAG = BaseActivity.class.getSimpleName();

    protected Toolbar mToolbar;
    private MaterialDialog materialDialog;

    protected abstract
    @LayoutRes
    int getLayoutResId();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutRes = getLayoutResId();
        if (layoutRes > 0) {
            setDefContentView(layoutRes);
            ButterKnife.bind(this);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null)
                setSupportActionBar(mToolbar);
        } else {
            Log.d(TAG, "onCreate->layoutRes: " + layoutRes);
            Log.i(TAG, "onCreate: " + "no  layout");
        }

        initView();
        initData(savedInstanceState);
    }


    protected void setDefContentView(@LayoutRes int layoutRes) {
        setContentView(layoutRes);
    }

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void showLoadingDialog() {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog.Builder(this)
                    .progress(true, -2)
                    .build();
            materialDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (materialDialog != null) {
            materialDialog.dismiss();
            materialDialog = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this, getClass().getCanonicalName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this, this.getClass().getCanonicalName());
    }
}
