package com.tq.indoormap.location;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;

import com.tq.indoormap.event.BluetoothNode;
import com.tq.indoormap.event.DeviceListEvent;
import com.tq.indoormap.event.LocationEvent;
import com.tq.indoormap.global.Config;
import com.tq.indoormap.location.impl.LocationManager2;
import com.tq.indoormap.location.impl.LocationManagerImpl;
import com.tq.indoormap.utils.ToastUtils;
import com.tq.simple_retrofit.RxBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by niantuo on 2017/4/11.
 */

public abstract class LocationManager {
    final private String TAG = LocationManager.class.getSimpleName();

    private List<BluetoothNode> nodes;

    private static LocationManager locationManager;

    public static LocationManager create(Context context) {
        int type = Config.getType(context);
        if (locationManager == null) {
            locationManager = type == Type.TYPE_1 ? new LocationManagerImpl() : new LocationManager2();
        } else if (type == Type.TYPE_1 && (locationManager instanceof LocationManager2)) {
            locationManager = new LocationManagerImpl();
        } else if (type == Type.TYPE_2 && (locationManager instanceof LocationManagerImpl)) {
            locationManager = new LocationManager2();
        }
        return locationManager;
    }


    public LocationManager() {
        nodes = new ArrayList<>();
    }


    protected abstract boolean isEnable();

    protected abstract void enable();

    public abstract void getLocation();


    protected void addBleNode(BluetoothNode node) {
        if (!nodes.contains(node))
            nodes.add(node);
    }

    protected void clearNodes() {
        nodes.clear();
    }


    protected synchronized void calculateLocation() {
        if (nodes.isEmpty()) {
            postEvent(null, 0);
            return;
        }

        RxBus.getBus().post(new DeviceListEvent(nodes));

        Schedulers.computation()
                .scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        calculate();
                    }
                });
    }

    private void calculate() {

        if (nodes.size() == 1) {

            BluetoothNode node = nodes.get(0);
            double dis = CalculateHelper.getHelper().getDistanceByRssi(node.getRssi());
            PointF location = Coordinate.getForNode(node.getMac());
            postEvent(location, dis);

        } else if (nodes.size() == 2) {

            BluetoothNode node1 = nodes.get(0);
            BluetoothNode node2 = nodes.get(1);

            PointF[] pointFs = getLocation(node1, node2);

            if (pointFs == null || pointFs.length == 0) {
                postEvent(null, 0);
                return;
            }

            if (pointFs.length == 1) {

                postEvent(pointFs[0], -1);

            } else {

                PointF l1 = pointFs[0];
                PointF l2 = pointFs[1];
                PointF location = new PointF((l1.x + l2.x) / 2, (l1.y + l2.y) / 2);
                postEvent(location, CalculateHelper.getHelper().getDisBy(l1, l2) / 2);
            }
        } else {

            Collections.sort(nodes, new ValueComparator());
            List<BluetoothNode> calNodes = nodes.subList(0, 3);

            PointF[] maybeP1 = getLocation(calNodes.get(0), calNodes.get(1));
            PointF[] maybeP2 = getLocation(calNodes.get(0), calNodes.get(2));

            if (maybeP1 == null || maybeP2 == null) {

                postEvent(null, -1);

                return;
            }

            LocationEvent locationEvent = new LocationEvent();

            for (PointF pointF : maybeP1) {
                for (PointF pointF1 : maybeP2) {
                    if (pointF == pointF1) {
                        locationEvent.setPointF(pointF);
                        break;
                    }
                }
            }

            postEvent(locationEvent.getPointF(), locationEvent.getRadio());
        }

    }


    private PointF[] getLocation(BluetoothNode node1, BluetoothNode node2) {
        PointF p1 = Coordinate.getForNode(node1.getMac());
        PointF p2 = Coordinate.getForNode(node2.getMac());

        double d1 = CalculateHelper.getHelper().getDistanceByRssi(node1.getRssi());
        double d2 = CalculateHelper.getHelper().getDistanceByRssi(node2.getRssi());

        Log.d(TAG, "getLocation: point->" + p1 + " dis->" + d1);
        Log.d(TAG, "getLocation: point->" + p2 + " dis->" + d2);

        PointF[] pointFs = new CirIntersect(new Circle(p1, d1), new Circle(p2, d2)).intersect();
        return pointFs;
    }

    /**
     * 把结果发送出去，接收方为
     *
     * @param pointF
     * @param radio
     * @see com.tq.indoormap.ui.fragment.MapFragment
     */
    private void postEvent(PointF pointF, double radio) {

        Log.d(TAG, "postEvent: " + pointF + "  devi:" + radio);

        if (pointF == null) {
            ToastUtils.toast("定位失败，请重试");
            RxBus.getBus().post(new LocationEvent());
            return;
        }

        if (radio < 0) {
            RxBus.getBus().post(new LocationEvent(pointF));
        } else {
            RxBus.getBus().post(new LocationEvent(pointF, (int) radio));
        }
    }


    public interface Type {
        int TYPE_1 = 0;
        int TYPE_2 = 1;
    }
}
