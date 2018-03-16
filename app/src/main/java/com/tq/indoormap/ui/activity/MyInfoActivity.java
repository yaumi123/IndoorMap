package com.tq.indoormap.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.tq.indoormap.R;
import com.tq.indoormap.databinding.ActivityMyInfoLayoutBinding;
import com.tq.indoormap.db.GreenDaoUtils;
import com.tq.indoormap.entity.User;
import com.tq.indoormap.greenDao.UserDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by niantuo on 2017/4/2.
 * 我的信息
 */

public class MyInfoActivity extends BaseActivity {

    private ActivityMyInfoLayoutBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_info_layout;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setDefContentView(@LayoutRes int layoutRes) {
        mBinding = DataBindingUtil.setContentView(this, layoutRes);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        UserDao userDao = GreenDaoUtils.getSingleTon().getSession().getUserDao();

        List<User> users = userDao.loadAll();

        if (users == null || users.isEmpty()) {
            User user = new User();
            user.setGuid(UUID.randomUUID().toString());
            user.setName("小明");
            user.setGender("男");
            user.setPhone("18198136356");
            String formatStr = "yyyy-MM-dd";
            user.setBirthday(new SimpleDateFormat(formatStr).format(new Date()));
            userDao.save(user);

            mBinding.setUser(user);

        } else {
            mBinding.setUser(users.get(0));
        }


    }
}
