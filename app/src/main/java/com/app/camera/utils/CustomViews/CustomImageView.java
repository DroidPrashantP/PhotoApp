package com.app.camera.utils.CustomViews;

/**
 * Created by Prashant on 18/12/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {

    private static final int PADDING = 8;
    private static final float STROKE_WIDTH = 8.0f;

    private Paint mBorderPaint;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setPadding(PADDING, PADDING, PADDING, PADDING);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initBorderPaint();
    }

    private void initBorderPaint() {
        mBorderPaint = new Paint();
//        mBorderPaint.setAntiAlias(true);
//        mBorderPaint.setStyle(Paint.Style.STROKE);
//        mBorderPaint.setColor(Color.WHITE);
//        mBorderPaint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.drawRect(PADDING, PADDING, getWidth()/2, getHeight()/2,null);
    }
}