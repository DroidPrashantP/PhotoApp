package com.app.paddycameraeditior.sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.activities.MirrorActivity;
import com.app.paddycameraeditior.utils.CustomViews.BlurBuilder;


public class StickerView extends View {
    static float MIN_ZOOM;
    private static final String TAG;
    float actualRadius;
    Paint bitmapPaint;
    float btmH;
    float btmW;
    PointF center;
    float circlePadding;
    Paint dashPaint;
    Path dashPathHorizontal;
    Path dashPathHorizontalTemp;
    Path dashPathVertical;
    Path dashPathVerticalTemp;
    final float epsilon;
    GestureDetector gestureDetector;
    Matrix inverse;
    boolean isInCircle;
    boolean isOnRect;
    boolean isOnTouch;
    boolean orthogonal;
    float padding;
    float paddingWidth;
    public Paint paint;
    PointF previosPos;
    float[] pts;
    Paint rectPaint;
    Paint rectPaintOnTouch;
    Paint redPaint;
    Bitmap removeBitmap;
    Matrix removeBitmapMatrix;
    float removeBitmapWidth;
    boolean savedViewSelected;
    float scale;
    Bitmap scaleBitmap;
    Matrix scaleBitmapMatrix;
    SingleTap singleTapListener;
    PointF start;
    private float startAngle;
    Matrix startMatrix;
    public Bitmap stickerBitmap;
    StickerData stickerData;
    Rect textBoundrect;
    RectF touchRect;
    float[] f513v;
    float[] values;
    private boolean viewSelected;
    StickerViewSelectedListener viewSelectedListener;
    Paint whitePaint;
    PointF zoomStart;

    private class GestureListener extends SimpleOnGestureListener {
        private GestureListener() {
        }

        public boolean onDown(MotionEvent e) {
            if (StickerView.this.isInCircle || StickerView.this.isOnRect) {
                return true;
            }
            StickerView.this.viewSelected = false;
            return false;
        }

        public boolean onSingleTapUp(MotionEvent event) {
            Log.e(StickerView.TAG, "onSingleTapUp");
            StickerView.this.pts[0] = event.getX();
            StickerView.this.pts[1] = event.getY();
            StickerView.this.stickerData.canvasMatrix.invert(StickerView.this.inverse);
            StickerView.this.inverse.mapPoints(StickerView.this.pts, StickerView.this.pts);
            StickerView.this.isOnRect = StickerView.this.isOnRect(StickerView.this.pts[0], StickerView.this.pts[1]);
            if (StickerView.this.isOnRect) {
                StickerView.this.viewSelected = !StickerView.this.savedViewSelected;
                StickerView.this.singleTapped();
            } else {
                StickerView.this.viewSelected = false;
            }
            if (StickerView.this.isInCircle || StickerView.this.isOnRect) {
                return true;
            }
            return false;
        }

        public boolean onDoubleTap(MotionEvent e) {
            StickerView.this.pts[0] = e.getX();
            StickerView.this.pts[1] = e.getY();
            StickerView.this.stickerData.canvasMatrix.invert(StickerView.this.inverse);
            StickerView.this.inverse.mapPoints(StickerView.this.pts, StickerView.this.pts);
            StickerView.this.isOnRect = StickerView.this.isOnRect(StickerView.this.pts[0], StickerView.this.pts[1]);
            if (StickerView.this.isOnRect) {
                StickerView.this.viewSelected = true;
                StickerView.this.singleTapped();
            } else {
                StickerView.this.viewSelected = false;
            }
            return true;
        }
    }

    public interface SingleTap {
        void onSingleTap(StickerData stickerData);
    }

    public interface StickerViewSelectedListener {
        void onTouchUp(StickerData stickerData);

        void setSelectedView(StickerView stickerView);
    }

    public interface StickerViewTouchUpListener {
        void onTouchUp();
    }

    static {
        TAG = StickerView.class.getSimpleName();
        MIN_ZOOM = BlurBuilder.BITMAP_SCALE;
    }

    public void setViewSelected(boolean selection) {
        this.viewSelected = selection;
        postInvalidate();
    }

    public StickerData getStickerData() {
        return this.stickerData;
    }

    public void setStickerData(StickerData data) {
        this.stickerData.set(data);
    }

    public boolean getViewSelected() {
        return this.viewSelected;
    }

    @SuppressLint({"NewApi"})
    public StickerView(Context context, Bitmap bitmap, StickerData stData, Bitmap removeBtm, Bitmap scaleBtm, int resId) {
        super(context);
        this.padding = 30.0f;
        this.paddingWidth = 10.0f;
        this.actualRadius = this.padding;
        this.center = new PointF();
        this.values = new float[9];
        this.scale = 1.0f;
        this.viewSelected = false;
        this.isOnTouch = false;
        this.removeBitmapMatrix = new Matrix();
        this.scaleBitmapMatrix = new Matrix();
        this.circlePadding = 5.0f;
        this.redPaint = new Paint(1);
        this.whitePaint = new Paint(1);
        this.bitmapPaint = new Paint(1);
        this.isOnRect = false;
        this.isInCircle = false;
        this.dashPathVerticalTemp = new Path();
        this.dashPathHorizontalTemp = new Path();
        this.dashPaint = new Paint();
        this.start = new PointF();
        this.previosPos = new PointF();
        this.zoomStart = new PointF();
        this.startMatrix = new Matrix();
        this.startAngle = 0.0f;
        this.inverse = new Matrix();
        this.pts = new float[2];
        this.savedViewSelected = false;
        this.epsilon = 4.0f;
        this.orthogonal = false;
        this.f513v = new float[9];
        this.stickerBitmap = bitmap;
        this.removeBitmap = removeBtm;
        this.scaleBitmap = scaleBtm;
        float screenWidth = (float) getResources().getDisplayMetrics().widthPixels;
        float screenHeight = (float) getResources().getDisplayMetrics().heightPixels;
        float minDimen = Math.min(screenWidth, screenHeight);
        this.rectPaint = new Paint(1);
        this.rectPaint.setColor(2006555033);
        this.rectPaintOnTouch = new Paint(1);
        this.rectPaintOnTouch.setColor(2011028957);
        this.textBoundrect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (stData == null) {
            this.stickerData = new StickerData(resId);
            float scale = minDimen / 1080.0f;
            this.stickerData.canvasMatrix.postScale(scale, scale);
            this.stickerData.canvasMatrix.postTranslate(0.1f, 0.1f);
            this.stickerData.xPos = ((screenWidth / scale) - ((float) this.textBoundrect.width())) / 2.0f;
            this.stickerData.yPos = screenHeight / (3.0f * scale);
        } else {
            this.stickerData = stData;
        }
        this.touchRect = new RectF(this.stickerData.xPos - this.paddingWidth, this.stickerData.yPos - this.padding, (this.stickerData.xPos + ((float) this.textBoundrect.width())) + this.paddingWidth, (this.stickerData.yPos + ((float) this.textBoundrect.height())) + this.padding);
        this.gestureDetector = new GestureDetector(context, new GestureListener());
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setFilterBitmap(true);
        this.redPaint.setColor(getResources().getColor(R.color.primary));
        this.whitePaint.setColor(getResources().getColor(R.color.primary_dark));
        this.bitmapPaint.setFilterBitmap(true);
        this.actualRadius = minDimen / 20.0f;
        float maxDimForBitmap = (float) Math.max(bitmap.getWidth(), bitmap.getHeight());
        if (maxDimForBitmap > this.actualRadius * 3.0f) {
            MIN_ZOOM = (this.actualRadius * 3.0f) / maxDimForBitmap;
        }
        this.circlePadding = this.actualRadius / 2.0f;
        if (this.actualRadius <= 5.0f) {
            this.actualRadius = this.padding;
        }
        this.removeBitmapWidth = (float) this.removeBitmap.getWidth();
        this.removeBitmapMatrix.reset();
        this.scaleBitmapMatrix.reset();
        float bitmapScale = (2.0f * this.actualRadius) / this.removeBitmapWidth;
        this.removeBitmapMatrix.postScale(bitmapScale, bitmapScale);
        this.removeBitmapMatrix.postTranslate(this.touchRect.left - ((this.removeBitmapWidth * bitmapScale) / 2.0f), this.touchRect.top - ((this.removeBitmapWidth * bitmapScale) / 2.0f));
        this.scaleBitmapMatrix.postScale(bitmapScale, bitmapScale);
        this.scaleBitmapMatrix.postTranslate(this.touchRect.right - ((this.removeBitmapWidth * bitmapScale) / 2.0f), this.touchRect.bottom - ((this.removeBitmapWidth * bitmapScale) / 2.0f));
        this.scale = getScale();
        this.scaleBitmapMatrix.postScale(1.0f / this.scale, 1.0f / this.scale, this.touchRect.right, this.touchRect.bottom);
        this.removeBitmapMatrix.postScale(1.0f / this.scale, 1.0f / this.scale, this.touchRect.left, this.touchRect.top);
        this.btmW = (float) bitmap.getWidth();
        this.btmH = (float) bitmap.getHeight();
        this.dashPaint.setColor(getResources().getColor(R.color.text_white_grey));
        this.dashPaint.setStyle(Style.STROKE);
        float strokeW = screenWidth / 120.0f;
        if (strokeW <= 0.0f) {
            strokeW = 5.0f;
        }
        this.dashPaint.setStrokeWidth(strokeW);
        this.dashPaint.setPathEffect(new DashPathEffect(new float[]{strokeW, strokeW}, 0.0f));
        this.dashPathVertical = new Path();
        this.dashPathVertical.moveTo(this.btmW / 2.0f, (-this.btmH) / 5.0f);
        this.dashPathVertical.lineTo(this.btmW / 2.0f, (6.0f * this.btmH) / 5.0f);
        this.dashPathHorizontal = new Path();
        this.dashPathHorizontal.moveTo((-this.btmW) / 5.0f, this.btmH / 2.0f);
        this.dashPathHorizontal.lineTo((6.0f * this.btmW) / 5.0f, this.btmH / 2.0f);
    }

    public void setMatrix(MyMatrix matrix) {
        this.stickerData.canvasMatrix.set(matrix);
        this.scale = getScale();
    }

    public void onDestroy() {
        this.stickerBitmap.recycle();
        this.stickerBitmap = null;
    }

    public void onDraw(Canvas canvas) {
        canvas.setMatrix(this.stickerData.canvasMatrix);
        this.removeBitmapMatrix.reset();
        this.scaleBitmapMatrix.reset();
        float bitmapScale = (this.actualRadius * 2.0f) / this.removeBitmapWidth;
        this.removeBitmapMatrix.postScale(bitmapScale, bitmapScale);
        this.removeBitmapMatrix.postTranslate(this.touchRect.left - ((this.removeBitmapWidth * bitmapScale) / 2.0f), this.touchRect.top - ((this.removeBitmapWidth * bitmapScale) / 2.0f));
        this.scaleBitmapMatrix.postScale(bitmapScale, bitmapScale);
        this.scaleBitmapMatrix.postTranslate(this.touchRect.right - ((this.removeBitmapWidth * bitmapScale) / 2.0f), this.touchRect.bottom - ((this.removeBitmapWidth * bitmapScale) / 2.0f));
        this.scale = getScale();
        this.scaleBitmapMatrix.postScale(1.0f / this.scale, 1.0f / this.scale, this.touchRect.right, this.touchRect.bottom);
        this.removeBitmapMatrix.postScale(1.0f / this.scale, 1.0f / this.scale, this.touchRect.left, this.touchRect.top);
        float rad = this.actualRadius / this.scale;
        if (this.viewSelected) {
            if (this.isOnTouch) {
                canvas.drawRect(this.touchRect, this.rectPaintOnTouch);
            } else {
                canvas.drawRect(this.touchRect, this.rectPaint);
            }
            canvas.drawCircle(this.touchRect.right, this.touchRect.bottom, rad, this.whitePaint);
            canvas.drawCircle(this.touchRect.left, this.touchRect.top, rad, this.redPaint);
            canvas.drawBitmap(this.scaleBitmap, this.scaleBitmapMatrix, this.bitmapPaint);
            canvas.drawBitmap(this.removeBitmap, this.removeBitmapMatrix, this.bitmapPaint);
        }
        if (!(this.stickerBitmap == null || this.stickerBitmap.isRecycled())) {
            canvas.drawBitmap(this.stickerBitmap, this.stickerData.xPos, this.stickerData.yPos, this.paint);
        }
        if (this.orthogonal) {
            this.dashPathVertical.offset(this.stickerData.xPos, this.stickerData.yPos, this.dashPathVerticalTemp);
            this.dashPathHorizontal.offset(this.stickerData.xPos, this.stickerData.yPos, this.dashPathHorizontalTemp);
            canvas.drawPath(this.dashPathVerticalTemp, this.dashPaint);
            canvas.drawPath(this.dashPathHorizontalTemp, this.dashPaint);
        }
    }

    void singleTapped() {
    }

    boolean isOnRect(float x, float y) {
        if (x <= this.touchRect.left || x >= this.touchRect.right || y <= this.touchRect.top || y >= this.touchRect.bottom) {
            return false;
        }
        this.viewSelected = true;
        return true;
    }

    boolean isInCircle(float x, float y) {
        if (((x - this.touchRect.right) * (x - this.touchRect.right)) + ((y - this.touchRect.bottom) * (y - this.touchRect.bottom)) >= ((this.actualRadius + this.circlePadding) * (this.actualRadius + this.circlePadding)) / (this.scale * this.scale)) {
            return false;
        }
        this.viewSelected = true;
        return true;
    }

    boolean isOnCross(float x, float y) {
        if (((x - this.touchRect.left) * (x - this.touchRect.left)) + ((y - this.touchRect.top) * (y - this.touchRect.top)) >= ((this.actualRadius + this.circlePadding) * (this.actualRadius + this.circlePadding)) / (this.scale * this.scale)) {
            return false;
        }
        this.viewSelected = true;
        return true;
    }

    boolean isOnCross2(float x, float y, float left, float top) {
        if (((x - left) * (x - left)) + ((y - top) * (y - top)) >= ((this.actualRadius + this.circlePadding) * (this.actualRadius + this.circlePadding)) / (this.scale * this.scale)) {
            return false;
        }
        this.viewSelected = true;
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction() & MotionEventCompat.ACTION_MASK) {
            case MirrorActivity.INDEX_MIRROR :
                this.savedViewSelected = this.viewSelected;
                this.isOnTouch = true;
                this.pts[0] = x;
                this.pts[1] = y;
                this.stickerData.canvasMatrix.invert(this.inverse);
                this.inverse.mapPoints(this.pts, this.pts);
                if (!this.viewSelected || !isOnCross(this.pts[0], this.pts[1])) {
                    this.isOnRect = isOnRect(this.pts[0], this.pts[1]);
                    this.isInCircle = isInCircle(this.pts[0], this.pts[1]);
                    this.start.set(x, y);
                    this.previosPos.set(x, y);
                    this.zoomStart.set(x, y);
                    this.pts[0] = this.touchRect.centerX();
                    this.pts[1] = this.touchRect.centerY();
                    this.stickerData.canvasMatrix.mapPoints(this.pts, this.pts);
                    this.startAngle = (float) (-pointToAngle(x, y, this.pts[0], this.pts[1]));
                    this.startMatrix.set(this.stickerData.canvasMatrix);
                    if (this.isInCircle || this.isOnRect) {
                        this.viewSelectedListener.setSelectedView(this);
                        break;
                    }
                }else {
                ((ViewGroup) getParent()).removeView(this);
                onDestroy();
                }
                break;
            case MirrorActivity.INDEX_MIRROR_3D /*1*/:
                this.orthogonal = false;
                this.viewSelectedListener.onTouchUp(this.stickerData);
                this.isOnTouch = false;
                this.isOnRect = false;
                break;
            case MirrorActivity.INDEX_MIRROR_RATIO /*2*/:
                if (!this.isInCircle) {
                    if (this.isOnRect) {
                        this.stickerData.canvasMatrix.set(this.startMatrix);
                        this.stickerData.canvasMatrix.postTranslate(x - this.previosPos.x, y - this.previosPos.y);
                        break;
                    }
                }
                float currentAngle = (float) (-pointToAngle(x, y, this.pts[0], this.pts[1]));
                float rotation = getMatrixRotation(this.stickerData.canvasMatrix);
                if ((rotation == 0.0f || rotation == 90.0f || rotation == 180.0f || rotation == -180.0f || rotation == -90.0f) && Math.abs(this.startAngle - currentAngle) < 4.0f) {
                    this.orthogonal = true;
                } else {
                    if (Math.abs((rotation - this.startAngle) + currentAngle) < 4.0f) {
                        currentAngle = this.startAngle - rotation;
                        this.orthogonal = true;
                        Log.d(TAG, "aaaaa " + Float.toString(rotation));
                    } else if (Math.abs(90.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                        currentAngle = (90.0f + this.startAngle) - rotation;
                        this.orthogonal = true;
                        Log.d(TAG, "bbbbb " + Float.toString(rotation));
                    } else if (Math.abs(180.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                        currentAngle = (180.0f + this.startAngle) - rotation;
                        this.orthogonal = true;
                        Log.d(TAG, "cccc " + Float.toString(rotation));
                    } else if (Math.abs(-180.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                        currentAngle = (this.startAngle - 0.024902344f) - rotation;
                        this.orthogonal = true;
                    } else if (Math.abs(-90.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                        currentAngle = (this.startAngle - 0.049804688f) - rotation;
                        this.orthogonal = true;
                        Log.d(TAG, "dddd " + Float.toString(rotation));
                    } else {
                        this.orthogonal = false;
                    }
                    this.stickerData.canvasMatrix.postRotate(this.startAngle - currentAngle, this.pts[0], this.pts[1]);
                    this.startAngle = currentAngle;
                }
                float scaley = ((float) Math.sqrt((double) (((x - this.pts[0]) * (x - this.pts[0])) + ((y - this.pts[1]) * (y - this.pts[1]))))) / ((float) Math.sqrt((double) (((this.zoomStart.x - this.pts[0]) * (this.zoomStart.x - this.pts[0])) + ((this.zoomStart.y - this.pts[1]) * (this.zoomStart.y - this.pts[1])))));
                this.scale = getScale();
                if (this.scale >= MIN_ZOOM || (this.scale < MIN_ZOOM && scaley > 1.0f)) {
                    this.stickerData.canvasMatrix.postScale(scaley, scaley, this.pts[0], this.pts[1]);
                    this.zoomStart.set(x, y);
                    this.scale = getScale();
                    break;
                }
                break;
        }
        boolean returnValue = this.gestureDetector.onTouchEvent(event);
        postInvalidate();
        return returnValue;
    }

    float getScale() {
        this.stickerData.canvasMatrix.getValues(this.values);
        float scalex = this.values[0];
        float skewy = this.values[3];
        return (float) Math.sqrt((double) ((scalex * scalex) + (skewy * skewy)));
    }

    public void setSingleTapListener(SingleTap l) {
        this.singleTapListener = l;
    }

    public void setStickerViewSelectedListener(StickerViewSelectedListener l) {
        this.viewSelectedListener = l;
    }

    private int pointToAngle(float x, float y, float centerX, float centerY) {
        if (x >= centerX && y < centerY) {
            return ((int) Math.toDegrees(Math.atan(((double) (x - centerX)) / ((double) (centerY - y))))) + 270;
        }
        if (x > centerX && y >= centerY) {
            return (int) Math.toDegrees(Math.atan(((double) (y - centerY)) / ((double) (x - centerX))));
        }
        if (x <= centerX && y > centerY) {
            return ((int) Math.toDegrees(Math.atan(((double) (centerX - x)) / ((double) (y - centerY))))) + 90;
        }
        if (x < centerX && y <= centerY) {
            return ((int) Math.toDegrees(Math.atan(((double) (centerY - y)) / ((double) (centerX - x))))) + 180;
        }
        throw new IllegalArgumentException();
    }

    float getMatrixRotation(Matrix matrix) {
        matrix.getValues(this.f513v);
        return (float) Math.round(Math.atan2((double) this.f513v[1], (double) this.f513v[0]) * 57.29577951308232d);
    }
}
