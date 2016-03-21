package com.app.camera.Domain;

/**
 * Created by sandeep on 4/2/16.
 */

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class MirrorMode {
    private static final String TAG = "MirrorMode";
    public static final int TOUCH_HORIZONTAL = 1;
    public static final int TOUCH_HORIZONTAL_DIRECT = 6;
    public static final int TOUCH_HORIZONTAL_REVERSE = 4;
    public static final int TOUCH_VERTICAL = 0;
    public static final int TOUCH_VERTICAL_DIRECT = 5;
    public static final int TOUCH_VERTICAL_REVERSE = 3;
    public int count;
    private Rect drawBitmapSrc;
    public Matrix matrix1;
    public Matrix matrix2;
    public Matrix matrix3;
    public RectF rect1;
    public RectF rect2;
    public RectF rect3;
    public RectF rect4;
    public RectF rectTotalArea;
    private RectF srcRect;
    public int touchMode;

    public MirrorMode(int count, RectF sR, RectF rect1, RectF rect2, Matrix matrix, int tM, RectF rta) {
        this.count = count;
        this.srcRect = sR;
        this.drawBitmapSrc = new Rect();
        this.srcRect.round(this.drawBitmapSrc);
        this.rect1 = rect1;
        this.rect2 = rect2;
        this.matrix1 = matrix;
        this.touchMode = tM;
        this.rectTotalArea = rta;
    }

    public void setSrcRect(RectF src) {
        this.srcRect.set(src);
        updateBitmapSrc();
    }

    public RectF getSrcRect() {
        return this.srcRect;
    }

    public Rect getDrawBitmapSrc() {
        return this.drawBitmapSrc;
    }

    public void updateBitmapSrc() {
        this.srcRect.round(this.drawBitmapSrc);
    }

    public MirrorMode(int c, RectF sR, RectF r1, RectF r2, RectF r3, RectF r4, Matrix m1, Matrix m2, Matrix m3, int tM, RectF rta) {
        this.count = c;
        this.srcRect = sR;
        this.drawBitmapSrc = new Rect();
        this.srcRect.round(drawBitmapSrc);
        this.rect1 = r1;
        this.rect2 = r2;
        this.rect3 = r3;
        this.rect4 = r4;
        this.matrix1 = m1;
        this.matrix2 = m2;
        this.matrix3 = m3;
        this.touchMode = tM;
        this.rectTotalArea = rta;
    }
}
