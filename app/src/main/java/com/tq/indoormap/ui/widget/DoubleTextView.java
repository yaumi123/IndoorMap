package com.tq.indoormap.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.tq.indoormap.R;

/**
 * Created by niantuo on 2017/4/2.
 */

public class DoubleTextView extends AppCompatTextView {
    final static String TAG = DoubleTextView.class.getSimpleName();

    private String secondText;
    private Paint mPaint;

    public DoubleTextView(Context context) {
        this(context, null);
    }

    public DoubleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DoubleTextView);
        secondText = array.getString(R.styleable.DoubleTextView_secondText);
        int size = array.getDimensionPixelSize(R.styleable.DoubleTextView_secondTextSize, 16);
        int color = array.getColor(R.styleable.DoubleTextView_secondTextColor, Color.GRAY);
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(size);
        mPaint.setColor(color);
        array.recycle();
    }


    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    public String getSecondText() {
        return secondText;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(secondText)) return;
        float top = getHeight() / 2 + getFontHeight(mPaint.getTextSize()) / 4;
        canvas.drawText(secondText, getSecondTextStart(), top, mPaint);
    }


    private float getSecondTextStart() {

        Log.d(TAG, "getSecondTextStart: "+getCompoundDrawables());
        Log.d(TAG, "getSecondTextStart: "+getCompoundDrawablesRelative());
        Log.d(TAG, "getSecondTextStart: "+getCompoundDrawablePadding());

        Drawable[] drawables = getCompoundDrawables();
        int padding = getCompoundDrawablePadding();

        Drawable right = drawables[2];
        float left = getRight() - getPaddingRight() - calculateLength();

        if (right != null) {
            left = left - padding - right.getIntrinsicWidth();
        }
        return left;
    }

    private float calculateLength() {
        return mPaint.measureText(secondText);
    }

    public int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
}
