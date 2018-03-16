package com.tq.indoormap.location.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.tq.indoormap.event.BluetoothNode;
import com.tq.indoormap.global.MyApplication;
import com.tq.indoormap.location.CalculateHelper;
import com.tq.indoormap.location.LocationManager;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

/**
 * Created by niantuo on 2017/4/8.
 */

public class LocationManagerImpl extends LocationManager {


    final String TAG = LocationManager.class.getSimpleName();

    private BluetoothSPP bluetoothSPP;
    private BluetoothAdapter bluetoothAdapter;


    public LocationManagerImpl() {
        bluetoothSPP = new BluetoothSPP(MyApplication.getContext());
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        registerReceiver(MyApplication.getContext());
        enable();
    }


    @Override
    public void getLocation() {
        clearNodes();
        bluetoothAdapter.startDiscovery();
    }

    private void registerReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(mReceiver, filter);
    }


    @Override
    protected void enable() {
        if (!isEnable())
            bluetoothSPP.enable();
    }

    @Override
    protected boolean isEnable() {
        return bluetoothSPP.isBluetoothEnabled();
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.d(TAG, "onReceive: " + action);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MAX_VALUE);
                BluetoothNode node = new BluetoothNode().setDevice(device).setRssi(rssi);

                Log.d(TAG, "onReceive: " + device.getAddress());

                Log.d(TAG, "onReceive -> name : " + device.getName());
                Log.d(TAG, "onReceive -> rssi : " + rssi);


                Log.d(TAG, "onReceive: " + CalculateHelper.getHelper().getDistanceByRssi(rssi));

                addBleNode(node);

                calculateLocation();

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d(TAG, "onReceive: finish");
                calculateLocation();
            }
        }
    };


}
