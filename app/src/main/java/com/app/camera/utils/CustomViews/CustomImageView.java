package com.app.camera.utils.CustomViews;

/**
 * Created by Prashant on 18/12/15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {

    private static final int PADDING = 8;

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
        mBorderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}