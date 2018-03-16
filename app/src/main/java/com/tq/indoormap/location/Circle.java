package com.tq.indoormap.location;

import android.graphics.PointF;

/**
 * Created by niantuo on 2017/4/8.
 */

public class Circle {

    private double x;
    private double y;
    private double r;

    public Circle(PointF p,double r) {
        this(p.x,p.y,r);
    }

    public Circle(double X, double Y, double R){
        x=X;
        y=Y;
        r=R;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getR(){
        return r;
    }

}
