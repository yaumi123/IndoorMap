package com.tq.indoormap.location;

import android.graphics.PointF;

import static java.lang.Math.abs;

/**
 * Created by niantuo on 2017/4/8.
 */

public class CalculateHelper {
    private static CalculateHelper helper;

    public static CalculateHelper getHelper() {
        if (helper == null) {
            helper = new CalculateHelper();
        }
        return helper;
    }


    /**
     * d = 10^((abs(RSSI) - A) / (10 * n))
     * d - 计算所得距离
     * RSSI - 接收信号强度（负值）
     * A - 发射端和接收端相隔1米时的信号强度,默认为59，应该做测试拟合
     * n - 环境衰减因子，设置经验值为2.0，应该测试拟合得出
     *
     * @param rssi
     * @return
     */
    public double getDistanceByRssi(int rssi) {
        int iRssi = abs(rssi);
        double power = (iRssi - 59) / (10 * 2.0);
        return Math.pow(10, power)*21.55;
    }

    public PointF[] getCutPoint(PointF a, double da, PointF b, double db) {
        return new CirIntersect(new Circle(a.x, a.y, da), new Circle(b.x, b.y, db)).intersect();
    }

    public int getDisBy(PointF p1, PointF p2) {

        double result = Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2);

        return (int) Math.floor(Math.abs(Math.sqrt(result)));
    }


}
