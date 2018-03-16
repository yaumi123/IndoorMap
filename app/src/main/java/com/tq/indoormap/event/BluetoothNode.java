package com.tq.indoormap.event;

import android.bluetooth.BluetoothDevice;

import com.tq.indoormap.location.ValueComparator;

/**
 * Created by niantuo on 2017/4/8.
 */

public class BluetoothNode implements ValueComparator.Value {
    private int rssi;
    private BluetoothDevice mDevice;


    public BluetoothNode setDevice(BluetoothDevice mDevice) {
        this.mDevice = mDevice;
        return this;
    }

    public BluetoothDevice getDevice() {
        return mDevice;
    }

    public BluetoothNode setRssi(int rssi){
        this.rssi = rssi;
        return this;
    }

    public BluetoothNode setRssi(short rssi) {
        this.rssi = rssi;
        return this;
    }

    public String getMac() {
        return mDevice == null ? "" : mDevice.getAddress();
    }


    public int getRssi() {
        return rssi;
    }


    @Override
    public int getValue() {
        return rssi;
    }


    @Override
    public boolean equals(Object obj) {
        if (mDevice == null) return false;
        if (obj instanceof BluetoothNode) {
            if (mDevice.getAddress().equals(((BluetoothNode) obj).getDevice().getAddress())) {
                return true;
            }
        }
        return super.equals(obj);
    }
}
