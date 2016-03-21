package com.app.camera.utils.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.app.camera.R;

/**
 * Created by Prashant on 13/1/16.
 */
public class CustomBlurView extends View{
    final Path path = new Path();
    private final Paint paint;

    Bitmap bitmapMaster;
    Canvas canvasMaster;

    public CustomBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), 10, 10, Path.Direction.CW);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }


}
