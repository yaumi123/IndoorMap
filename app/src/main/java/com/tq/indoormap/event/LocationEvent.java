package com.tq.indoormap.event;

import android.graphics.PointF;

/**
 * Created by niantuo on 2017/4/8.
 */

public class LocationEvent {
    private PointF pointF;
    private int radio;

    public LocationEvent() {
        super();
    }

    public LocationEvent(PointF pointF) {
        this(pointF, 10);
    }


    public LocationEvent(PointF pointF, int radio) {
        this.pointF = pointF;
        this.radio = radio;
    }

    public void setPointF(PointF pointF) {
        this.pointF = pointF;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public int getRadio() {
        return radio;
    }

    public PointF getPointF() {
        return pointF;
    }
}
