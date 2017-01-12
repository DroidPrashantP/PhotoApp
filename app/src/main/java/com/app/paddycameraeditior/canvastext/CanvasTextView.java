package com.app.paddycameraeditior.canvastext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.app.paddycameraeditior.R;

public class CanvasTextView extends View {
    static final float MIN_ZOOM = 0.8f;
    private static final String TAG = "CanvasTextView";
    float actualRadius;
    ApplyTextInterface applyListener;
    Paint bitmapPaint;
    float bitmapWidth;
    PointF center;
    float circlePadding;
    GestureDetector gestureDetector;
    Matrix identityMatrix;
    Matrix inverse;
    boolean isInCircle;
    boolean isOnRect;
    boolean isOnTouch;
    float padding;
    float paddingWidth;
    PointF previosPos;
    float[] pts;
    float radius;
    private RectF rect;
    Paint rectPaint;
    Paint rectPaintOnTouch;
    Paint redPaint;
    Bitmap removeBitmap;
    Matrix removeBitmapMatrix;
    CustomRelativeLayout.RemoveTextListener removeTextListener;
    float scale;
    Bitmap scaleBitmap;
    Matrix scaleBitmapMatrix;
    SingleTap singleTapListener;
    PointF start;
    private double startAngle;
    Matrix startMatrix;
    Rect textBoundrect;
    TextData textData;
    private boolean textSelected;
    float[] values;
    ViewSelectedListener viewSelectedListener;
    Paint whitePaint;
    PointF zoomStart;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        public boolean onDown(MotionEvent e) {
            if (CanvasTextView.this.isInCircle || CanvasTextView.this.isOnRect) {
                return true;
            }
            CanvasTextView.this.textSelected = false;
            return false;
        }

        public boolean onSingleTapUp(MotionEvent event) {
            Log.d("Single Tap", "Tapped at");
            CanvasTextView.this.pts[0] = event.getX();
            CanvasTextView.this.pts[1] = event.getY();
            CanvasTextView.this.textData.canvasMatrix.invert(CanvasTextView.this.inverse);
            CanvasTextView.this.inverse.mapPoints(CanvasTextView.this.pts, CanvasTextView.this.pts);
            CanvasTextView.this.isOnRect = CanvasTextView.this.isOnRect(CanvasTextView.this.pts[0], CanvasTextView.this.pts[1]);
            if (CanvasTextView.this.isOnRect) {
                Log.d("textSelected", "single Tapped at");
                CanvasTextView.this.textSelected = true;
                CanvasTextView.this.singleTapped();
            } else {
                CanvasTextView.this.textSelected = false;
            }
            if (CanvasTextView.this.isInCircle || CanvasTextView.this.isOnRect) {
                return true;
            }
            return false;
        }

        public boolean onDoubleTap(MotionEvent e) {
            CanvasTextView.this.pts[0] = e.getX();
            CanvasTextView.this.pts[1] = e.getY();
            CanvasTextView.this.textData.canvasMatrix.invert(CanvasTextView.this.inverse);
            CanvasTextView.this.inverse.mapPoints(CanvasTextView.this.pts, CanvasTextView.this.pts);
            CanvasTextView.this.isOnRect = CanvasTextView.this.isOnRect(CanvasTextView.this.pts[0], CanvasTextView.this.pts[1]);
            if (CanvasTextView.this.isOnRect) {
                Log.d("textSelected", "double Tapped at");
                CanvasTextView.this.textSelected = true;
                CanvasTextView.this.singleTapped();
            } else {
                CanvasTextView.this.textSelected = false;
            }
            Log.d("Double Tap", "Tapped at");
            return true;
        }
    }

    public void setTextSelected(boolean selection) {
        this.textSelected = selection;
        postInvalidate();
    }

    public boolean getTextSelected() {
        return this.textSelected;
    }

    public void setApplyInterface(ApplyTextInterface l) {
        this.applyListener = l;
    }

    public void setRemoveTextListener(CustomRelativeLayout.RemoveTextListener listener) {
        this.removeTextListener = listener;
    }

    public CanvasTextView(Context context, TextData td, Bitmap removeBtm, Bitmap scaleBtm) {
        super(context);
        this.padding = 30.0f;
        this.paddingWidth = 10.0f;
        this.radius = 40.0f;
        this.actualRadius = this.padding;
        this.circlePadding = 5.0f;
        this.center = new PointF();
        this.identityMatrix = new Matrix();
        this.redPaint = new Paint(1);
        this.whitePaint = new Paint(1);
        this.bitmapPaint = new Paint(1);
        this.values = new float[9];
        this.scale = 1.0f;
        this.textSelected = false;
        this.isOnTouch = false;
        this.removeBitmapMatrix = new Matrix();
        this.scaleBitmapMatrix = new Matrix();
        this.isOnRect = false;
        this.isInCircle = false;
        this.start = new PointF();
        this.previosPos = new PointF();
        this.zoomStart = new PointF();
        this.startMatrix = new Matrix();
        this.startAngle = 0.0d;
        this.inverse = new Matrix();
        this.pts = new float[2];
        float textSize = context.getResources().getDimension(R.dimen.myFontSize);
        float screenWidth = (float) getResources().getDisplayMetrics().widthPixels;
        float screenHeight = (float) getResources().getDisplayMetrics().heightPixels;
        this.rectPaint = new Paint(1);
        this.rectPaint.setColor(2011028957);
        this.redPaint.setColor(getResources().getColor(R.color.red));
        this.whitePaint.setColor(getResources().getColor(R.color.text_white));
        this.bitmapPaint.setFilterBitmap(true);
        this.rectPaintOnTouch = new Paint(1);
        this.rectPaintOnTouch.setColor(2011028957);
        this.textBoundrect = new Rect();
        if (td == null) {
            this.textData = new TextData(textSize);
            this.textData.textPaint.getTextBounds(TextData.defaultMessage, 0, TextData.defaultMessage.length(), this.textBoundrect);
            this.textData.xPos = (screenWidth / 2.0f) - ((float) (this.textBoundrect.width() / 2));
            this.textData.yPos = screenHeight / 3.0f;
        } else {
            this.textData = td;
            if (this.textData.getFontPath() != null) {
                Typeface typeFace = FontCache.get(context, this.textData.getFontPath());
                if (typeFace != null) {
                    this.textData.textPaint.setTypeface(typeFace);
                }
            }
            this.textData.textPaint.getTextBounds(this.textData.message, 0, this.textData.message.length(), this.textBoundrect);
        }

        this.rect = new RectF(this.textData.xPos - this.paddingWidth, (this.textData.yPos - ((float) this.textBoundrect.height())) - this.padding, (this.textData.xPos + ((float) this.textBoundrect.width())) + (2.0f * this.paddingWidth), this.textData.yPos + this.padding);
        this.gestureDetector = new GestureDetector(context, new GestureListener());
        this.actualRadius = screenWidth / 20.0f;
        this.circlePadding = this.actualRadius / 2.0f;
        if (this.actualRadius <= 5.0f) {
            this.actualRadius = this.padding;
        }
        this.removeBitmap = removeBtm;
        this.scaleBitmap = scaleBtm;
        this.bitmapWidth = (float) this.removeBitmap.getWidth();
        this.removeBitmapMatrix.reset();
        this.scaleBitmapMatrix.reset();
        float bitmapScale = (2.0f * this.actualRadius) / this.bitmapWidth;
        this.removeBitmapMatrix.postScale(bitmapScale, bitmapScale);
        this.removeBitmapMatrix.postTranslate(this.rect.left - ((this.bitmapWidth * bitmapScale) / 2.0f), this.rect.top - ((this.bitmapWidth * bitmapScale) / 2.0f));
        this.scaleBitmapMatrix.postScale(bitmapScale, bitmapScale);
        this.scaleBitmapMatrix.postTranslate(this.rect.right - ((this.bitmapWidth * bitmapScale) / 2.0f), this.rect.bottom - ((this.bitmapWidth * bitmapScale) / 2.0f));
        this.scale = getScale();
        this.scaleBitmapMatrix.postScale(1.0f / this.scale, 1.0f / this.scale, this.rect.right, this.rect.bottom);
        this.removeBitmapMatrix.postScale(1.0f / this.scale, 1.0f / this.scale, this.rect.left, this.rect.top);
    }

    public void setMatrix(MyMatrix matrix) {
        this.textData.canvasMatrix = matrix;
        this.scale = getScale();
    }

    public void onDraw(Canvas canvas) {
        canvas.setMatrix(this.textData.canvasMatrix);
        if (this.textSelected) {
            if (this.isOnTouch) {
                canvas.drawRect(this.rect, this.rectPaintOnTouch);
            } else {
                canvas.drawRect(this.rect, this.rectPaint);
            }
            float rad = this.actualRadius / this.scale;
            canvas.drawCircle(this.rect.right, this.rect.bottom, rad, this.whitePaint);
            canvas.drawCircle(this.rect.left, this.rect.top, rad, this.redPaint);
            canvas.drawBitmap(this.scaleBitmap, this.scaleBitmapMatrix, this.bitmapPaint);
            canvas.drawBitmap(this.removeBitmap, this.removeBitmapMatrix, this.bitmapPaint);
        }
        Log.e("message", textData.message);
        Log.e("X", ""+textData.xPos);
        Log.e("Y", ""+this.textData.yPos);
        canvas.drawText(this.textData.message, this.textData.xPos, this.textData.yPos, this.textData.textPaint);
    }

    void checkMatrix() {
        this.textData.canvasMatrix.getValues(this.values);
        if (getScale() < 0.5f) {
            this.textData.canvasMatrix.postScale(0.5f, 0.5f, this.pts[0], this.pts[1]);
        }
    }

    void singleTapped() {
        this.singleTapListener.onSingleTap(this.textData);
    }

    void setTextColor(int color) {
        this.textData.textPaint.setColor(color);
        postInvalidate();
    }

    public void setMessage(CharSequence newMessage) {
        if (newMessage.length() == 0) {
            this.textData.message = TextData.defaultMessage;
        } else {
            this.textData.message = newMessage.toString();
        }
        float exRight = this.rect.right;
        this.rect.right = (this.rect.left + this.textData.textPaint.measureText(this.textData.message)) + (2.0f * this.paddingWidth);
        this.scaleBitmapMatrix.postTranslate(this.rect.right - exRight, 0.0f);
        postInvalidate();
    }

    public void setNewTextData(TextData textData) {
        this.textData = textData;
        float exRight = this.rect.right;
        this.rect.right = (this.rect.left + textData.textPaint.measureText(textData.message)) + (2.0f * this.paddingWidth);
        this.scaleBitmapMatrix.postTranslate(this.rect.right - exRight, 0.0f);
        postInvalidate();
    }

    boolean isOnRect(float x, float y) {
        if (x <= this.rect.left || x >= this.rect.right || y <= this.rect.top || y >= this.rect.bottom) {
            return false;
        }
        this.textSelected = true;
        return true;
    }

    boolean isInCircle(float x, float y) {
        if (((x - this.rect.right) * (x - this.rect.right)) + ((y - this.rect.bottom) * (y - this.rect.bottom)) >= (this.radius * this.radius) / (this.scale * this.scale)) {
            return false;
        }
        this.textSelected = true;
        return true;
    }

    boolean isOnCross(float x, float y) {
        if (((x - this.rect.left) * (x - this.rect.left)) + ((y - this.rect.top) * (y - this.rect.top)) >= ((this.actualRadius + this.circlePadding) * (this.actualRadius + this.circlePadding)) / (this.scale * this.scale)) {
            return false;
        }
        Log.e(TAG, "isOncross");
        this.textSelected = true;
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MirrorActivity.INDEX_MIRROR:
                this.isOnTouch = true;
                this.pts[0] = x;
                this.pts[1] = y;
                this.textData.canvasMatrix.invert(this.inverse);
                this.inverse.mapPoints(this.pts, this.pts);
                if (!this.textSelected || !isOnCross(this.pts[0], this.pts[1])) {
                    this.isOnRect = isOnRect(this.pts[0], this.pts[1]);
                    this.isInCircle = isInCircle(this.pts[0], this.pts[1]);
                    this.start.set(x, y);
                    this.previosPos.set(x, y);
                    this.zoomStart.set(x, y);
                    this.pts[0] = this.rect.centerX();
                    this.pts[1] = this.rect.centerY();
                    this.textData.canvasMatrix.mapPoints(this.pts, this.pts);
                    this.startAngle = (double) (-pointToAngle(x, y, this.pts[0], this.pts[1]));
                    this.startMatrix.set(this.textData.canvasMatrix);
                    if (this.isInCircle || this.isOnRect) {
                        this.viewSelectedListener.setSelectedView(this);
                        break;
                    }
                }else {
                    this.removeTextListener.onRemove();
                }
                return false;
            case MirrorActivity.INDEX_MIRROR_3D:
                this.isOnTouch = false;
                this.isOnRect = false;
                break;
            case MirrorActivity.INDEX_MIRROR_RATIO:
                if (!this.isInCircle) {
                    if (this.isOnRect) {
                        this.textData.canvasMatrix.set(this.startMatrix);
                        this.textData.canvasMatrix.postTranslate(x - this.previosPos.x, y - this.previosPos.y);
                        break;
                    }
                }
                float currentAngle = (float) (-pointToAngle(x, y, this.pts[0], this.pts[1]));
                this.textData.canvasMatrix.postRotate((float) (this.startAngle - ((double) currentAngle)), this.pts[0], this.pts[1]);
                this.startAngle = (double) currentAngle;
                float scaley = ((float) Math.sqrt((double) (((x - this.pts[0]) * (x - this.pts[0])) + ((y - this.pts[1]) * (y - this.pts[1]))))) / ((float) Math.sqrt((double) (((this.zoomStart.x - this.pts[0]) * (this.zoomStart.x - this.pts[0])) + ((this.zoomStart.y - this.pts[1]) * (this.zoomStart.y - this.pts[1])))));
                this.scale = getScale();
                if (this.scale >= MIN_ZOOM || (this.scale < MIN_ZOOM && scaley > 1.0f)) {
                    this.textData.canvasMatrix.postScale(scaley, scaley, this.pts[0], this.pts[1]);
                    this.zoomStart.set(x, y);
                    this.scale = getScale();
                    this.scaleBitmapMatrix.postScale(1.0f / scaley, 1.0f / scaley, this.rect.right, this.rect.bottom);
                    this.removeBitmapMatrix.postScale(1.0f / scaley, 1.0f / scaley, this.rect.left, this.rect.top);
                    break;
                }
                break;
        }
        postInvalidate();
        return this.gestureDetector.onTouchEvent(event);
    }

    float getScale() {
        this.textData.canvasMatrix.getValues(this.values);
        float scalex = this.values[0];
        float skewy = this.values[3];
        return (float) Math.sqrt((double) ((scalex * scalex) + (skewy * skewy)));
    }

    public void setSingleTapListener(SingleTap l) {
        this.singleTapListener = l;
    }

    public void setViewSelectedListener(ViewSelectedListener l) {
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
}