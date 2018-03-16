package com.tq.indoormap.ui.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tq.indoormap.R;
import com.tq.indoormap.global.Config;
import com.tq.indoormap.location.LocationManager;

import butterknife.BindView;

/**
 * Created by niantuo on 2017/4/2.
 * 设置
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.type_switch)
    Switch mSwitch;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected void initView() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int type = Config.getType(this);

        mSwitch.setChecked(type == LocationManager.Type.TYPE_2);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                keep(isChecked ? LocationManager.Type.TYPE_2 : LocationManager.Type.TYPE_1);
            }
        });

    }

    private void keep(int type) {
        Config.keepType(this, type);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
