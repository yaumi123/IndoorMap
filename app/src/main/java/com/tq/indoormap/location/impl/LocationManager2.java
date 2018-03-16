package com.tq.indoormap.location.impl;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.tq.indoormap.event.BluetoothNode;
import com.tq.indoormap.global.MyApplication;
import com.tq.indoormap.location.LocationManager;
import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.scan.PeriodScanCallback;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.utils.BleUtil;

/**
 * Created by niantuo on 2017/4/11.
 */

public class LocationManager2 extends LocationManager {
    final String TAG = LocationManager2.class.getSimpleName();


    private PeriodScanCallback periodScanCallback = new PeriodScanCallback() {
        @Override
        public void scanTimeout() {
            Log.d(TAG, "scanTimeout: ");
            calculateLocation();
        }

        @Override
        public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {

            Log.d(TAG, "onDeviceFound: " + bluetoothLeDevice);
            Log.d(TAG, "onDeviceFound: " + bluetoothLeDevice.getAddress() + "  rssi:" + bluetoothLeDevice.getRssi());

            BluetoothNode node = new BluetoothNode();
            node.setDevice(bluetoothLeDevice.getDevice());
            node.setRssi(bluetoothLeDevice.getRssi());

            calculateLocation();
        }
    };


    public LocationManager2() {
        ViseBluetooth.getInstance().init(MyApplication.getContext());
        enable();
    }

    @Override
    protected boolean isEnable() {
        return BleUtil.isBleEnable(MyApplication.getContext());
    }

    @Override
    protected void enable() {
        if (!isEnable())
            BluetoothAdapter.getDefaultAdapter().enable();
    }

    @Override
    public void getLocation() {
        clearNodes();
        ViseBluetooth.getInstance().setScanTimeout(30 * 1000).startScan(periodScanCallback);
    }
}
