package com.tq.indoormap.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tq.indoormap.R;
import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.model.ShopModel;
import com.tq.indoormap.network.MyResult;
import com.tq.indoormap.ui.activity.ShopDetailActivity;
import com.tq.indoormap.ui.adapter.ShopListAdapter;
import com.tq.indoormap.utils.ToastUtils;
import com.tq.simple_retrofit.simples.ResultObserver;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by niantuo on 2017/4/2.
 */

public class NearFragment extends BaseFragment {

    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private RecyclerView mRecyclerView;

    private ShopListAdapter mAdapter;

    private ShopModel mModel;

    private int index = 0;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_near_shop_layout;
    }

    @Override
    protected void initView(View view) {
        String[] titles = getContext().getResources().getStringArray(R.array.types);
        List<String> titleList = new ArrayList<>();
        Collections.addAll(titleList, titles);

        List<View> views = new ArrayList<>();

        ListView foodView = new ListView(getContext());
        ListView clothesView = new ListView(getContext());
        ListView funnyView = new ListView(getContext());
        ListView lifeView = new ListView(getContext());

        views.add(foodView);
        views.add(clothesView);
        views.add(funnyView);
        views.add(lifeView);

        mRecyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.fragment_near_shop_content_layout, null);
        dropDownMenu.setDropDownMenu(titleList, views, mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                Shop shop = mAdapter.getItem(position);
                ShopDetailActivity.startAction(getContext(), shop);
            }
        });


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mModel = new ShopModel();

        mAdapter = new ShopListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        refresh(true);

    }


    private void refresh(boolean refresh) {
        if (refresh) {
            index = 0;
        }
        mModel.loadRecommended(index, new ResultObserver<MyResult<List<Shop>>>(refresh ? 11 : 22) {
            @Override
            protected void onSuccess(int requestCode, MyResult<List<Shop>> data) {
                if (data.isSuccess()) {
                    index = data.getPageIndex();
                    if (requestCode == 11) {
                        mAdapter.setNewData(data.getData());
                    } else {
                        mAdapter.addData(data.getData());
                    }
                } else {
                    ToastUtils.toast(data.getMsg());
                }
            }

            @Override
            protected void onError(int requestCode, int errCode, String errMsg) {
                ToastUtils.toast(errMsg);
            }
        });

    }


}
