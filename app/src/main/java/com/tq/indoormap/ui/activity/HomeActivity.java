package com.tq.indoormap.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.tq.indoormap.R;
import com.tq.indoormap.ui.adapter.PagerFragmentAdapter;
import com.tq.indoormap.ui.fragment.MapFragment;
import com.tq.indoormap.ui.fragment.MineFragment;
import com.tq.indoormap.ui.fragment.NearFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by niantuo on 2017/4/2.
 * email：976056042@qq.com
 * 主页
 */
public class HomeActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private PagerFragmentAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home_layout;
    }


    @Override
    protected void initView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MapFragment());
        fragments.add(new NearFragment());
        fragments.add(new MineFragment());

        mAdapter = new PagerFragmentAdapter(getSupportFragmentManager(), fragments, null);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.addOnPageChangeListener(this);
        updateTitle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getIntExtra("action", 0) == 1992) {
            mViewPager.setCurrentItem(0, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(SearchActivity.class);
            return true;
        } else if (item.getItemId() == R.id.action_to_scan) {
            startActivity(DeviceScanActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_near:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_mine:
                mViewPager.setCurrentItem(2);
                return true;
        }
        updateTitle();
        return false;
    }

    private void updateTitle() {
        final int currentPos = mViewPager.getCurrentItem();
        if (currentPos == 0) {
            setTitle(R.string.app_name);
        } else if (currentPos == 1) {
            setTitle(R.string.title_near);
        } else {
            setTitle(R.string.title_mine);
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.navigation_near);
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.navigation_mine);
                break;
        }
        updateTitle();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
}
