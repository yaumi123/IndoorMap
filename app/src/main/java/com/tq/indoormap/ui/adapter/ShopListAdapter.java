package com.tq.indoormap.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tq.imageloader.DefImageLoader;
import com.tq.indoormap.R;
import com.tq.indoormap.entity.Shop;

/**
 * Created by niantuo on 2017/4/2.
 */

public class ShopListAdapter extends BaseQuickAdapter<Shop, BaseViewHolder> {

    public ShopListAdapter() {
        super(R.layout.item_shop_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {

        ImageView imageView = helper.getView(R.id.item_shop_icon);

        DefImageLoader.getDefault().display(imageView, item.getIcon());

        helper.setText(R.id.item_shop_name, item.getName());

    }
}
