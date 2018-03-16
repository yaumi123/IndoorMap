package com.tq.indoormap.location;

import android.graphics.PointF;
import android.util.Log;

import com.tq.indoormap.db.GreenDaoUtils;
import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.greenDao.ShopDao;

/**
 * Created by niantuo on 2017/4/8.
 */

public class Coordinate {
    final static String TAG = Coordinate.class.getCanonicalName();

    public static PointF getForNode(String node) {
        ShopDao dao = GreenDaoUtils.getSingleTon().getSession().getShopDao();
        Shop shop = dao.queryBuilder()
                .where(ShopDao.Properties.Node.eq(node))
                .unique();

        Log.d(TAG, "getForNode: " + shop);

        if (shop == null) {
            float x = (float) Math.random() * 500;
            float y = (float) Math.random() * 1000;
            return new PointF(x, y);
        }

        Log.d(TAG, "getForNode: " + shop.getPoint());
        return shop.getPoint();
    }
}
