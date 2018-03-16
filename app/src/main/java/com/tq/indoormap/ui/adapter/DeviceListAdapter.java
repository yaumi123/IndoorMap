package com.tq.indoormap.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tq.indoormap.R;
import com.tq.indoormap.event.BluetoothNode;
import com.tq.indoormap.location.CalculateHelper;

/**
 * Created by niantuo on 2017/4/11.
 */

public class DeviceListAdapter extends BaseQuickAdapter<BluetoothNode, BaseViewHolder> {


    public DeviceListAdapter() {
        super(R.layout.item_device_list_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, BluetoothNode item) {
        helper.setText(R.id.device_name, "NAME:" + item.getDevice().getName());
        helper.setText(R.id.device_mac, "MAC:" + item.getMac());
        helper.setText(R.id.device_rssi, "RSSI:" + item.getRssi());
        helper.setText(R.id.device_dis, "DIS:" + CalculateHelper.getHelper().getDistanceByRssi(item.getRssi()));
    }
}
