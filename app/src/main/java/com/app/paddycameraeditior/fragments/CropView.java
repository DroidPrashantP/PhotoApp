package com.app.paddycameraeditior.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.bitmap.BitmapResizer;
import com.app.paddycameraeditior.utils.CustomViews.BlurBuilder;

public class CropView extends View {
    Bitmap bitmap;
    int bitmapHeight;
    Paint bitmapPaint;
    float bitmapScale;
    int bitmapWidth;
    int borderSize;
    int downBorder;
    Paint gridFramePaint;
    Paint gridPaint;
    PointF gridStart;
    int halfBorder;
    float height;
    public int inSampleSize;
    boolean isOnTouch;
    int leftBorder;
    PointF leftDown;
    PointF leftUp;
    Matrix matrix;
    PointF midDown;
    PointF midLeft;
    PointF midRight;
    PointF midUp;
    int mode;
    boolean moveGrid;
    float multiplier;
    float oldX;
    float oldY;
    Paint pointPaint;
    float pointRadius;
    RectF rectDown;
    float rectH;
    RectF rectLeft;
    Paint rectPaint;
    float rectRadius;
    RectF rectRight;
    RectF rectUp;
    float rectW;
    int rightBorder;
    PointF rightDown;
    PointF rightUp;
    int rotation;
    int screenHeight;
    int screenWidth;
    PointF selectedPoint;
    float sentinel;
    int topBorder;
    float width;

    public CropView(Context context, String inputPath, int scW, int scH, Bitmap btm, int insp) {
        super(context);
        this.mode = 0;
        this.pointRadius = BlurBuilder.BLUR_RADIUS_MAX;
        this.borderSize = 50;
        this.halfBorder = 25;
        this.leftBorder = this.halfBorder;
        this.topBorder = this.leftBorder;
        this.bitmapScale = 1.0f;
        this.isOnTouch = false;
        this.moveGrid = false;
        this.sentinel = 70.0f;
        this.multiplier = 1.0f;
        this.inSampleSize = 1;
        this.rectW = 10.0f;
        this.rectH = 15.0f;
        this.rectRadius = 6.0f;
        this.rotation = 0;
        this.pointPaint = new Paint(1);
        this.bitmapPaint = new Paint(1);
        this.gridPaint = new Paint(1);
        this.gridPaint.setStrokeWidth(3.0f);
        this.gridPaint.setColor(-1);
        this.gridFramePaint = new Paint(this.gridPaint);
        this.gridFramePaint.setStrokeWidth(4.0f);
        this.pointPaint.setColor(-1);
        this.rectPaint = new Paint(1);
        this.rectPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        float shortSide = (float) Math.min(scH, scW);
        this.borderSize = (int) (shortSide / 15.0f);
        if (this.borderSize % 2 == 1) {
            this.borderSize--;
        }
        int i = this.borderSize / 2;
        this.halfBorder = i;
        this.leftBorder = i;
        this.topBorder = i;
        this.pointRadius = (float) i;
        this.sentinel = shortSide / 6.0f;
        this.rectW = shortSide / 40.0f;
        this.rectH = shortSide / 30.0f;
        this.rectRadius = shortSide / 50.0f;
        this.screenWidth = scW;
        this.screenHeight = scH;
        if (btm == null) {
            int[] scaleInt = new int[1];
            int[] rotateInt = new int[1];
            this.bitmap = BitmapResizer.decodeX(inputPath, BitmapResizer.maxSizeForDimension(context), scaleInt, rotateInt);
            this.inSampleSize = scaleInt[0];
            this.rotation = rotateInt[0];
        } else {
            this.bitmap = btm;
            this.inSampleSize = insp;
        }
        float toolbarSize = 1.2f * context.getResources().getDimension(R.dimen.text_size_medium);
        float headerSize = context.getResources().getDimension(R.dimen.toolbar_height);
        if (this.bitmap != null) {
            this.bitmapWidth = this.bitmap.getWidth();
            this.bitmapHeight = this.bitmap.getHeight();
            this.bitmapScale = Math.min(((float) (scW - this.borderSize)) / ((float) this.bitmapWidth), ((((float) (scH - this.borderSize)) - toolbarSize) - headerSize) / ((float) this.bitmapHeight));
            this.matrix = new Matrix();
            this.matrix.postScale(this.bitmapScale, this.bitmapScale);
            this.matrix.postTranslate((float) this.halfBorder, (float) this.halfBorder);
            this.width = (float) Math.round(((float) this.bitmapWidth) * this.bitmapScale);
            this.height = (float) Math.round(((float) this.bitmapHeight) * this.bitmapScale);
            this.gridStart = new PointF((float) this.leftBorder, (float) this.topBorder);
            this.leftUp = new PointF((float) this.leftBorder, (float) this.topBorder);
            this.leftDown = new PointF((float) this.leftBorder, ((float) this.topBorder) + this.height);
            this.rightUp = new PointF(((float) this.leftBorder) + this.width, (float) this.topBorder);
            this.rightDown = new PointF(((float) this.leftBorder) + this.width, ((float) this.topBorder) + this.height);
            this.rightBorder = (int) (this.width + ((float) this.leftBorder));
            this.downBorder = (int) (this.height + ((float) this.leftBorder));
            this.midLeft = new PointF((float) this.leftBorder, ((float) this.topBorder) + (this.height / 2.0f));
            this.midRight = new PointF((float) this.rightBorder, ((float) this.topBorder) + (this.height / 2.0f));
            this.midUp = new PointF(((float) this.leftBorder) + (this.width / 2.0f), (float) this.topBorder);
            this.midDown = new PointF(((float) this.leftBorder) + (this.width / 2.0f), (float) this.downBorder);
            this.rectUp = new RectF(this.midUp.x - this.rectH, this.midUp.y - this.rectW, this.midUp.x + this.rectH, this.midUp.y + this.rectW);
            this.rectDown = new RectF(this.midDown.x - this.rectH, this.midDown.y - this.rectW, this.midDown.x + this.rectH, this.midDown.y + this.rectW);
            this.rectLeft = new RectF(this.midLeft.x - this.rectW, this.midLeft.y - this.rectH, this.midLeft.x + this.rectW, this.midLeft.y + this.rectH);
            this.rectRight = new RectF(this.midRight.x - this.rectW, this.midRight.y - this.rectH, this.midRight.x + this.rectW, this.midRight.y + this.rectH);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Math.round(((float) this.bitmapWidth) * this.bitmapScale) + this.borderSize, Math.round(((float) this.bitmapHeight) * this.bitmapScale) + this.borderSize);
    }

    public void onDraw(Canvas canvas) {
        if (!(this.bitmap == null || this.bitmap.isRecycled())) {
            canvas.drawBitmap(this.bitmap, this.matrix, this.bitmapPaint);
        }
        int sc = canvas.saveLayer((float) this.leftBorder, (float) this.topBorder, (float) this.rightBorder, (float) this.downBorder, null, 31);
        canvas.drawColor(-1795162112);
        canvas.drawRect(this.leftUp.x, this.leftUp.y, this.rightDown.x, this.rightDown.y, this.rectPaint);
        canvas.restoreToCount(sc);
        float startX = this.leftUp.x;
        float startY = this.leftUp.y;
        float horizontalInterval = (this.rightUp.x - this.leftUp.x) / 3.0f;
        float verticalInterval = (this.leftDown.y - this.leftUp.y) / 3.0f;
        int i = 0;
        while (i < 4) {
            if (i == 0 || i == 3) {
                canvas.drawLine(startX, this.leftUp.y, startX, this.leftDown.y, this.gridFramePaint);
                canvas.drawLine(this.leftUp.x, startY, this.rightUp.x, startY, this.gridFramePaint);
            } else {
                canvas.drawLine(startX, this.leftUp.y, startX, this.leftDown.y, this.gridPaint);
                canvas.drawLine(this.leftUp.x, startY, this.rightUp.x, startY, this.gridPaint);
            }
            startX += horizontalInterval;
            startY += verticalInterval;
            i++;
        }
        canvas.drawCircle(this.leftUp.x, this.leftUp.y, this.pointRadius, this.pointPaint);
        canvas.drawCircle(this.leftDown.x, this.leftDown.y, this.pointRadius, this.pointPaint);
        canvas.drawCircle(this.rightUp.x, this.rightUp.y, this.pointRadius, this.pointPaint);
        canvas.drawCircle(this.rightDown.x, this.rightDown.y, this.pointRadius, this.pointPaint);
        if (this.mode == 0) {
            canvas.drawRoundRect(this.rectLeft, this.rectRadius, this.rectRadius, this.pointPaint);
            canvas.drawRoundRect(this.rectUp, this.rectRadius, this.rectRadius, this.pointPaint);
            canvas.drawRoundRect(this.rectRight, this.rectRadius, this.rectRadius, this.pointPaint);
            canvas.drawRoundRect(this.rectDown, this.rectRadius, this.rectRadius, this.pointPaint);
        }
    }

    PointF selectPoint(float x, float y) {
        if (((x - this.leftUp.x) * (x - this.leftUp.x)) + ((y - this.leftUp.y) * (y - this.leftUp.y)) < this.pointRadius * this.pointRadius) {
            return this.leftUp;
        }
        if (((x - this.leftDown.x) * (x - this.leftDown.x)) + ((y - this.leftDown.y) * (y - this.leftDown.y)) < this.pointRadius * this.pointRadius) {
            return this.leftDown;
        }
        if (((x - this.rightUp.x) * (x - this.rightUp.x)) + ((y - this.rightUp.y) * (y - this.rightUp.y)) < this.pointRadius * this.pointRadius) {
            return this.rightUp;
        }
        if (((x - this.rightDown.x) * (x - this.rightDown.x)) + ((y - this.rightDown.y) * (y - this.rightDown.y)) < this.pointRadius * this.pointRadius) {
            return this.rightDown;
        }
        return null;
    }

    PointF selectMidPoint(float x, float y) {
        if (((x - this.midLeft.x) * (x - this.midLeft.x)) + ((y - this.midLeft.y) * (y - this.midLeft.y)) < this.pointRadius * this.pointRadius) {
            return this.midLeft;
        }
        if (((x - this.midUp.x) * (x - this.midUp.x)) + ((y - this.midUp.y) * (y - this.midUp.y)) < this.pointRadius * this.pointRadius) {
            return this.midUp;
        }
        if (((x - this.midRight.x) * (x - this.midRight.x)) + ((y - this.midRight.y) * (y - this.midRight.y)) < this.pointRadius * this.pointRadius) {
            return this.midRight;
        }
        if (((x - this.midDown.x) * (x - this.midDown.x)) + ((y - this.midDown.y) * (y - this.midDown.y)) < this.pointRadius * this.pointRadius) {
            return this.midDown;
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        this.isOnTouch = false;
        switch (event.getAction()) {
            case MirrorActivity.INDEX_MIRROR /*0*/:
                this.selectedPoint = selectPoint(x, y);
                if (this.selectedPoint == null) {
                    if (this.mode == 0) {
                        this.selectedPoint = selectMidPoint(x, y);
                    }
                    if (this.selectedPoint == null) {
                        this.moveGrid = checkMoveGrid(x, y);
                        this.oldX = x;
                        this.oldY = y;
                        break;
                    }
                }
                break;
            case MirrorActivity.INDEX_MIRROR_3D /*1*/:
                this.moveGrid = false;
                break;
            case MirrorActivity.INDEX_MIRROR_RATIO /*2*/:
                if (this.selectedPoint == null) {
                    if (this.moveGrid) {
                        moveGrid(x - this.oldX, y - this.oldY);
                        this.oldX = x;
                        this.oldY = y;
                        break;
                    }
                }
                this.isOnTouch = true;
                setPositionsOfPoints(this.selectedPoint, x, y);
                break;
        }
        postInvalidate();
        return true;
    }

    boolean checkMoveGrid(float x, float y) {
        if (x <= this.leftUp.x || x >= this.rightDown.x || y <= this.leftUp.y || y >= this.rightDown.y) {
            return false;
        }
        return true;
    }

    void moveGrid(float x, float y) {
        if (this.leftUp.x + x < ((float) this.leftBorder)) {
            x = ((float) this.leftBorder) - this.leftUp.x;
        }
        if (this.leftUp.y + y < ((float) this.leftBorder)) {
            y = ((float) this.leftBorder) - this.leftUp.y;
        }
        if (this.rightDown.x + x > ((float) this.rightBorder)) {
            x = ((float) this.rightBorder) - this.rightDown.x;
        }
        if (this.rightDown.y + y > ((float) this.downBorder)) {
            y = ((float) this.downBorder) - this.rightDown.y;
        }
        PointF pointF = this.leftUp;
        pointF.x += x;
        pointF = this.leftUp;
        pointF.y += y;
        pointF = this.leftDown;
        pointF.x += x;
        pointF = this.leftDown;
        pointF.y += y;
        pointF = this.rightUp;
        pointF.x += x;
        pointF = this.rightUp;
        pointF.y += y;
        pointF = this.rightDown;
        pointF.x += x;
        pointF = this.rightDown;
        pointF.y += y;
        setMidPositions();
    }

    void setPositionsOfPoints(PointF inPoint, float x, float y) {
        PointF pSelectedPoint = inPoint;
        if (x < ((float) this.leftBorder)) {
            x = (float) this.leftBorder;
        }
        if (x > ((float) this.rightBorder)) {
            x = (float) this.rightBorder;
        }
        if (y < ((float) this.leftBorder)) {
            y = (float) this.leftBorder;
        }
        if (y > ((float) this.downBorder)) {
            y = (float) this.downBorder;
        }
        if (inPoint == this.midLeft) {
            pSelectedPoint = this.leftUp;
            y = this.leftUp.y;
        } else if (inPoint == this.midUp) {
            pSelectedPoint = this.leftUp;
            x = this.leftUp.x;
        } else if (inPoint == this.midRight) {
            pSelectedPoint = this.rightDown;
            y = this.rightDown.y;
        } else if (inPoint == this.midDown) {
            pSelectedPoint = this.rightDown;
            x = this.rightDown.x;
        }
        PointF pointF;
        PointF pointF2;
        if (pSelectedPoint == this.leftUp) {
            if (this.mode != 0) {
                y = this.leftUp.y + ((x - this.leftUp.x) / this.multiplier);
                if (y < ((float) this.leftBorder)) {
                    y = (float) this.topBorder;
                    x = this.rightUp.x - ((this.leftDown.y - ((float) this.topBorder)) * this.multiplier);
                }
            }
            if (x <= this.rightUp.x - this.sentinel && y <= this.leftDown.y - this.sentinel) {
                pointF = this.midLeft;
                pointF2 = this.leftDown;
                this.leftUp.x = x;
                pointF2.x = x;
                pointF.x = x;
                pointF = this.midUp;
                pointF2 = this.rightUp;
                this.leftUp.y = y;
                pointF2.y = y;
                pointF.y = y;
            } else {
                return;
            }
        } else if (pSelectedPoint == this.leftDown) {
            if (this.mode != 0) {
                y = this.leftDown.y + ((this.leftDown.x - x) / this.multiplier);
                if (y > ((float) this.downBorder)) {
                    y = (float) this.downBorder;
                    x = this.rightDown.x - (this.multiplier * (((float) this.downBorder) - this.leftUp.y));
                }
            }
            if (x <= this.rightDown.x - this.sentinel && y >= this.leftUp.y + this.sentinel) {
                pointF = this.midLeft;
                pointF2 = this.leftUp;
                this.leftDown.x = x;
                pointF2.x = x;
                pointF.x = x;
                pointF = this.midDown;
                pointF2 = this.rightDown;
                this.leftDown.y = y;
                pointF2.y = y;
                pointF.y = y;
            } else {
                return;
            }
        } else if (pSelectedPoint == this.rightUp) {
            if (this.mode != 0) {
                y = this.rightUp.y + ((this.rightUp.x - x) / this.multiplier);
                if (y < ((float) this.topBorder)) {
                    y = (float) this.topBorder;
                    x = this.leftUp.x + ((this.rightDown.y - ((float) this.topBorder)) * this.multiplier);
                }
            }
            if (x >= this.leftUp.x + this.sentinel && y <= this.rightDown.y - this.sentinel) {
                pointF = this.midRight;
                pointF2 = this.rightDown;
                this.rightUp.x = x;
                pointF2.x = x;
                pointF.x = x;
                pointF = this.midUp;
                pointF2 = this.leftUp;
                this.rightUp.y = y;
                pointF2.y = y;
                pointF.y = y;
            } else {
                return;
            }
        } else if (pSelectedPoint == this.rightDown) {
            if (this.mode != 0) {
                y = this.rightDown.y + ((x - this.rightDown.x) / this.multiplier);
                if (y > ((float) this.downBorder)) {
                    y = (float) this.downBorder;
                    x = this.leftDown.x + (this.multiplier * (((float) this.downBorder) - this.rightUp.y));
                }
            }
            if (x >= this.leftDown.x + this.sentinel && y >= this.rightUp.y + this.sentinel) {
                pointF = this.midRight;
                pointF2 = this.rightUp;
                this.rightDown.x = x;
                pointF2.x = x;
                pointF.x = x;
                pointF = this.midDown;
                pointF2 = this.leftDown;
                this.rightDown.y = y;
                pointF2.y = y;
                pointF.y = y;
            } else {
                return;
            }
        }
        setMidPositions();
    }

    void setMidPositions() {
        this.midRight.x = this.rightUp.x;
        this.midLeft.x = this.leftUp.x;
        this.midUp.y = this.leftUp.y;
        this.midDown.y = this.leftDown.y;
        PointF pointF = this.midRight;
        float f = this.leftUp.y + ((this.leftDown.y - this.leftUp.y) / 2.0f);
        this.midLeft.y = f;
        pointF.y = f;
        pointF = this.midDown;
        f = this.leftUp.x + ((this.rightUp.x - this.leftUp.x) / 2.0f);
        this.midUp.x = f;
        pointF.x = f;
        this.rectUp.set(this.midUp.x - this.rectH, this.midUp.y - this.rectW, this.midUp.x + this.rectH, this.midUp.y + this.rectW);
        this.rectDown.set(this.midDown.x - this.rectH, this.midDown.y - this.rectW, this.midDown.x + this.rectH, this.midDown.y + this.rectW);
        this.rectLeft.set(this.midLeft.x - this.rectW, this.midLeft.y - this.rectH, this.midLeft.x + this.rectW, this.midLeft.y + this.rectH);
        this.rectRight.set(this.midRight.x - this.rectW, this.midRight.y - this.rectH, this.midRight.x + this.rectW, this.midRight.y + this.rectH);
    }

    void checkBounderyOfPoint(PointF point) {
        if (point.x < ((float) (this.borderSize / 2))) {
            point.x = (float) (this.borderSize / 2);
        }
        if (point.y < ((float) (this.borderSize / 2))) {
            point.y = (float) (this.borderSize / 2);
        }
        if (point.x > ((float) this.rightBorder)) {
            point.x = (float) this.rightBorder;
        }
        if (point.y > ((float) this.downBorder)) {
            point.y = (float) this.downBorder;
        }
        if (point.x < ((float) (this.borderSize / 2))) {
            point.y -= point.x * this.multiplier;
            point.x = (float) (this.borderSize / 2);
        }
        if (point.y < ((float) (this.borderSize / 2))) {
            point.x -= point.x / this.multiplier;
            point.y = (float) (this.borderSize / 2);
        }
        if (point.x > ((float) this.rightBorder)) {
            point.y -= (point.x - ((float) this.rightBorder)) * this.multiplier;
            point.x = (float) this.rightBorder;
        }
        if (point.y > ((float) this.downBorder)) {
            point.x -= (point.y - ((float) this.downBorder)) / this.multiplier;
            point.y = (float) this.downBorder;
        }
    }

    public void setMode(int pMode) {
        if (pMode >= 0 && pMode < 11) {
            this.mode = pMode;
        }
        if (this.mode == 0) {
            this.multiplier = 1.0f;
        }
        if (this.mode == 1) {
            this.multiplier = 1.0f;
        } else if (this.mode == 2) {
            this.multiplier = 2.0f;
        } else if (this.mode == 3) {
            this.multiplier = 0.5f;
        } else if (this.mode == 4) {
            this.multiplier = 1.5f;
        } else if (this.mode == 5) {
            this.multiplier = 0.6666667f;
        } else if (this.mode == 6) {
            this.multiplier = 1.3333334f;
        } else if (this.mode == 7) {
            this.multiplier = 0.75f;
        } else if (this.mode == 8) {
            this.multiplier = 0.8f;
        } else if (this.mode == 9) {
            this.multiplier = 0.71428573f;
        } else if (this.mode == 10) {
            this.multiplier = 1.7777778f;
        }
        if (this.mode != 0) {
            setModePosition();
        }
        postInvalidate();
    }

    private void setModePosition() {
        this.leftUp.x = (float) this.halfBorder;
        this.leftUp.y = (float) this.halfBorder;
        this.leftDown.x = (float) this.halfBorder;
        this.rightUp.y = (float) this.halfBorder;
        PointF pointF;
        float round;
        if (this.width / this.height > this.multiplier) {
            pointF = this.leftUp;
            round = ((float) this.leftBorder) + ((this.width - ((float) Math.round(this.height * this.multiplier))) / 2.0f);
            this.leftDown.x = round;
            pointF.x = round;
            pointF = this.leftDown;
            round = (float) this.downBorder;
            this.rightDown.y = round;
            pointF.y = round;
            pointF = this.rightUp;
            round = (this.height * this.multiplier) + this.leftUp.x;
            this.rightDown.x = round;
            pointF.x = round;
        } else {
            pointF = this.rightUp;
            round = ((float) this.topBorder) + ((this.height - ((float) Math.round(this.width / this.multiplier))) / 2.0f);
            this.leftUp.y = round;
            pointF.y = round;
            pointF = this.rightDown;
            round = (this.width / this.multiplier) + this.rightUp.y;
            this.leftDown.y = round;
            pointF.y = round;
            pointF = this.rightUp;
            round = (float) this.rightBorder;
            this.rightDown.x = round;
            pointF.x = round;
        }
        setMidPositions();
    }

    public int getLeftPos() {
        int result = this.inSampleSize * Math.round((this.leftUp.x - ((float) this.leftBorder)) / this.bitmapScale);
        if (result < 0) {
            return 0;
        }
        return result;
    }

    public int getTopPos() {
        int result = this.inSampleSize * Math.round((this.leftUp.y - ((float) this.topBorder)) / this.bitmapScale);
        if (result < 0) {
            return 0;
        }
        return result;
    }

    public int getRightPos() {
        int result = this.inSampleSize * Math.round((this.rightDown.x - ((float) this.leftBorder)) / this.bitmapScale);
        if (result < 0) {
            return 0;
        }
        return result;
    }

    public int getBottomPos() {
        int result = this.inSampleSize * Math.round((this.rightDown.y - ((float) this.topBorder)) / this.bitmapScale);
        if (result < 0) {
            return 0;
        }
        return result;
    }
}
