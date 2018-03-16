package com.tq.indoormap.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by niantuo on 2017/4/2.
 */

public class PagerFragmentAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragments;
    private List<String> titles;


    public PagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles == null || position < 0 || position >= titles.size())
            return super.getPageTitle(position);
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
