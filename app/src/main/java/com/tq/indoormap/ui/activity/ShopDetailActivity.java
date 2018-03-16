package com.tq.indoormap.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.tq.imageloader.DefImageLoader;
import com.tq.indoormap.R;
import com.tq.indoormap.databinding.ActivityShopDetailLayoutBinding;
import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.event.ShopEvent;
import com.tq.simple_retrofit.RxBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by niantuo on 2017/4/2.
 * 商铺详情
 */

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {

    private Shop shop;

    @BindView(R.id.shop_avatar)
    ImageView mShopIcon;

    public static void startAction(Context activity, Shop shop) {
        Intent intent = new Intent(activity, ShopDetailActivity.class);
        intent.putExtra("shop", shop);
        activity.startActivity(intent);
    }


    private ActivityShopDetailLayoutBinding mBinding;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shop_detail_layout;
    }

    @Override
    protected void setDefContentView(@LayoutRes int layoutRes) {
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId());
    }

    @Override
    protected void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        shop = getIntent().getParcelableExtra("shop");
        if (shop != null) {
            mBinding.setShop(shop);
            DefImageLoader.getDefault().display(mShopIcon, shop.getIcon());
        }

    }


    @OnClick({R.id.floating_btn})
    @Override
    public void onClick(View v) {
        RxBus.getBus().post(ShopEvent.create(shop));
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("action", 1992);
        startActivity(intent);
        finish();
    }
}
