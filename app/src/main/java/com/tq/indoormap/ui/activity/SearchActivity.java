package com.tq.indoormap.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tq.indoormap.R;
import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.model.ShopModel;
import com.tq.indoormap.network.MyResult;
import com.tq.indoormap.ui.adapter.ShopListAdapter;
import com.tq.indoormap.utils.ToastUtils;
import com.tq.simple_retrofit.simples.ResultObserver;

import java.util.List;

import butterknife.BindView;

/**
 * Created by niantuo on 2017/4/2.
 * 搜索商户界面
 */

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.searchView)
    SearchView mSearchView;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ShopListAdapter mAdapter;

    private ShopModel mModel;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_layout;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchView.setOnQueryTextListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Shop shop = mAdapter.getItem(position);
                ShopDetailActivity.startAction(SearchActivity.this, shop);
            }
        });

        mAdapter = new ShopListAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        mModel = new ShopModel();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (TextUtils.isEmpty(query))
            return false;
        showLoadingDialog();
        mModel.searchByKey(query, new ResultObserver<MyResult<List<Shop>>>() {
            @Override
            protected void onSuccess(int requestCode, MyResult<List<Shop>> data) {
                if (data.isSuccess() && data.getData() != null) {
                    mAdapter.setNewData(data.getData());
                    hideLoadingDialog();
                } else {
                    hideLoadingDialog();
                    ToastUtils.toast(data.getMsg());
                }
            }

            @Override
            protected void onError(int requestCode, int errCode, String errMsg) {
                hideLoadingDialog();
                ToastUtils.toast(errMsg);
            }
        });
        return true;
    }
}
