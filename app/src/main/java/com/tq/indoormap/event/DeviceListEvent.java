package com.tq.indoormap.event;

import java.util.List;

/**
 * Created by niantuo on 2017/4/11.
 */

public class DeviceListEvent {

    private List<BluetoothNode> nodes;

    public DeviceListEvent(List<BluetoothNode> nodes) {
        this.nodes = nodes;
    }

    public List<BluetoothNode> getNodes() {
        return nodes;
    }
}
