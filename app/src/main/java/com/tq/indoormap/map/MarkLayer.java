package com.tq.indoormap.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.layer.MapBaseLayer;
import com.onlylemi.mapview.library.utils.MapMath;
import com.tq.imageloader.DefImageLoader;
import com.tq.imageloader.DefaultParams;
import com.tq.indoormap.R;
import com.tq.indoormap.entity.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niantuo on 2017/4/4.
 */

public class MarkLayer extends MapBaseLayer {

    final String TAG=MarkLayer.class.getSimpleName();

    private List<Shop> shopList;
    private Paint paint;
    private Bitmap markBitmap;
    private Bitmap bmpMarkTouch;
    private float radiusMark;
    private MarkIsClickListener listener;

    private boolean isClickMark = false;
    private int num = -1;


    public MarkLayer(MapView mapView) {
        this(mapView, new ArrayList<Shop>());
    }

    public MarkLayer(MapView mapView, List<Shop> shops) {
        super(mapView);
        this.shopList = shops;
        initLayer();
    }

    private void initLayer() {
        radiusMark = setValue(10f);
        markBitmap = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.mark_);
        bmpMarkTouch = DefImageLoader.getDefault().loadBitmapSync("drawable://"+DefaultParams.DEF_IMAGE,60);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onTouch(MotionEvent event) {
        if (shopList == null || shopList.isEmpty()) return;

        Log.d(TAG, "onTouch: "+event.getX()+"-->"+event.getY());

        float[] goal = mapView.convertMapXYToScreenXY(event.getX(), event.getY());

        Log.d(TAG, "onTouch: "+goal[0]+" -->>"+goal[1]);

        for (int i = 0; i < shopList.size(); i++) {
            Shop shop = shopList.get(i);

            Log.d(TAG, "onTouch: "+shop.getPointX()+"-->"+shop.getPointY());

            if (MapMath.getDistanceBetweenTwoPoints(goal[0], goal[1],
                    shop.getPointX(), shop.getPointY()) <= 50) {
                num = i;
                isClickMark = true;
                break;
            }

            if (i == shopList.size() - 1) {
                isClickMark = false;
            }
        }

        if (listener != null && isClickMark) {
            if (num > -1 && num < shopList.size()) {
                listener.markIsClick(shopList.get(num));
                mapView.refresh();
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, float currentRotateDegrees) {
        if (!isVisible || shopList == null || shopList.isEmpty())
            return;
        canvas.save();
        for (int i = 0; i < shopList.size(); i++) {
            Shop shop = shopList.get(i);
            drawMark(canvas, currentMatrix, shop, i);
        }
        canvas.restore();
    }


    private void drawMark(Canvas canvas, Matrix currentMatrix, Shop shop, int i) {
        PointF mark = shop.getPoint();
        float[] goal = {mark.x, mark.y};
        currentMatrix.mapPoints(goal);

        String marksName = shop.getName();

        paint.setColor(Color.BLACK);
        paint.setTextSize(radiusMark);
        //mark name
        if (mapView.getCurrentZoom() > 1.0 && !TextUtils.isEmpty(marksName)) {
            canvas.drawText(marksName, goal[0] - radiusMark, goal[1] -
                    radiusMark / 2, paint);
        }
        //mark ico

        canvas.drawBitmap(markBitmap, goal[0] - markBitmap.getWidth() / 2,
                goal[1] - markBitmap.getHeight() / 2, paint);


        if (i == num && isClickMark) {
            canvas.drawBitmap(bmpMarkTouch, goal[0] - bmpMarkTouch.getWidth() / 2,
                    goal[1] - bmpMarkTouch.getHeight(), paint);
        }

    }


    public float getRadiusMark() {
        return radiusMark;
    }

    public int getNum() {
        return num;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public MarkIsClickListener getListener() {
        return listener;
    }

    public Paint getPaint() {
        return paint;
    }


    public void setClickMark(boolean clickMark) {
        isClickMark = clickMark;
    }

    public void setListener(MarkIsClickListener listener) {
        this.listener = listener;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setRadiusMark(float radiusMark) {
        this.radiusMark = radiusMark;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }


    public interface MarkIsClickListener {
        void markIsClick(Shop shop);
    }
}
