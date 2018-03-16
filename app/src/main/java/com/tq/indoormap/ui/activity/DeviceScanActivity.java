package com.tq.indoormap.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tq.indoormap.R;
import com.tq.indoormap.event.DeviceListEvent;
import com.tq.indoormap.location.LocationManager;
import com.tq.indoormap.ui.adapter.DeviceListAdapter;
import com.tq.simple_retrofit.RxBus;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by niantuo on 2017/4/11.
 * 蓝牙搜索
 */

public class DeviceScanActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private DeviceListAdapter mAdapter;

    private Observable<DeviceListEvent> mDevicesObservable;

    private LocationManager locationManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scan_device_layout;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getDrawable(R.drawable.shape_line_1_dp_height));
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("附近蓝牙");
        mAdapter = new DeviceListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mDevicesObservable = RxBus.getBus().register(DeviceListEvent.class);

        mDevicesObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceListEvent>() {
                    @Override
                    public void accept(DeviceListEvent deviceListEvent) throws Exception {
                        Log.d(TAG, "accept: " + deviceListEvent);
                        mAdapter.setNewData(deviceListEvent.getNodes());
                    }
                });

        locationManager = LocationManager.create(this);
        locationManager.getLocation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sync, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_scan) {
            locationManager.getLocation();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getBus().unregister(DeviceListEvent.class, mDevicesObservable);
    }
}
