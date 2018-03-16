package com.tq.indoormap.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tq.indoormap.R;
import com.tq.indoormap.ui.activity.CollectionActivity;
import com.tq.indoormap.ui.activity.MyInfoActivity;
import com.tq.indoormap.ui.activity.SettingActivity;

import butterknife.OnClick;

/**
 * Created by niantuo on 2017/4/2.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.my_info, R.id.my_collection, R.id.setting})
    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.my_info) {
            startActivityForResult(new Intent(getContext(), MyInfoActivity.class), 11);
        } else if (viewId == R.id.my_collection) {
            startActivity(new Intent(getContext(), CollectionActivity.class));
        } else if (viewId == R.id.setting) {
            startActivity(new Intent(getContext(), SettingActivity.class));
        }
    }
}
