package com.app.camera.collagelib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;

public class CopyOfShapeBeforeScrapBook {
    public static final int MATRIX_MODE_CENTER = 1;
    public static final int MATRIX_MODE_FIT = 0;
    public static final int MATRIX_MODE_FLIP_HORIZONTAL = 4;
    public static final int MATRIX_MODE_FLIP_VERTICAL = 5;
    public static final int MATRIX_MODE_MOVE_DOWN = 13;
    public static final int MATRIX_MODE_MOVE_LEFT = 10;
    public static final int MATRIX_MODE_MOVE_RIGHT = 11;
    public static final int MATRIX_MODE_MOVE_UP = 12;
    public static final int MATRIX_MODE_ROTATE_LEFT = 3;
    public static final int MATRIX_MODE_ROTATE_NEGATIVE = 6;
    public static final int MATRIX_MODE_ROTATE_POSITIVE = 7;
    public static final int MATRIX_MODE_ROTATE_RIGHT = 2;
    public static final int MATRIX_MODE_ZOOM_IN = 8;
    public static final int MATRIX_MODE_ZOOM_OUT = 9;
    private static final String TAG = "Shape";
    public final int SHAPE_MODE_MASK;
    public final int SHAPE_MODE_POINT;
    public final int SHAPE_MODE_RECT;
    private Bitmap bitmap;
    Matrix bitmapMatrix;
    RectF bounds;
    PointF centerOriginal;
    float dx;
    float dy;
    int[] exceptionIndex;
    float[] f504f;
    Paint iconMaskPaint;
    Paint iconPaint;
    Xfermode iconXferMode;
    private Bitmap maskBitmap;
    private Matrix maskMatrix;
    Paint maskPaint;
    int offsetX;
    int offsetY;
    RectF originalBounds;
    Path originalPath;
    private Paint paintPath;
    private Paint paintTransparent;
    private Paint paintTransparentMask;
    Paint paintXferMode;
    Path path;
    Path[] pathList;
    int pathListLength;
    Matrix pathMatrix;
    PointF[] points;
    RectF f505r;
    Region region;
    float scaleDown;
    float scaleUp;
    int shapeMode;
    RectF sourceRect;
    RectF tempRect;
    Matrix transparentMaskMatrix;

    public CopyOfShapeBeforeScrapBook(PointF[] points, Bitmap b, int[] exceptionIndex, int offsetX, int offsetY) {
        this.offsetY = MATRIX_MODE_FIT;
        this.offsetX = MATRIX_MODE_FIT;
        this.SHAPE_MODE_POINT = MATRIX_MODE_CENTER;
        this.SHAPE_MODE_RECT = MATRIX_MODE_ROTATE_RIGHT;
        this.SHAPE_MODE_MASK = MATRIX_MODE_ROTATE_LEFT;
        this.maskBitmap = null;
        this.maskMatrix = new Matrix();
        this.transparentMaskMatrix = new Matrix();
        this.tempRect = new RectF();
        this.f505r = new RectF();
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.scaleDown = 0.95f;
        this.scaleUp = 1.05f;
        this.f504f = new float[MATRIX_MODE_ROTATE_RIGHT];
        this.centerOriginal = new PointF();
        this.points = points;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        createPathFromPoints();
        this.path.offset((float) offsetX, (float) offsetY);
        this.exceptionIndex = exceptionIndex;
        this.bitmap = b;
        this.shapeMode = MATRIX_MODE_CENTER;
        init();
    }

    public CopyOfShapeBeforeScrapBook(PointF[] points, Bitmap b, int[] exceptionIndex, int offsetX, int offsetY, Bitmap mask) {
        this.offsetY = MATRIX_MODE_FIT;
        this.offsetX = MATRIX_MODE_FIT;
        this.SHAPE_MODE_POINT = MATRIX_MODE_CENTER;
        this.SHAPE_MODE_RECT = MATRIX_MODE_ROTATE_RIGHT;
        this.SHAPE_MODE_MASK = MATRIX_MODE_ROTATE_LEFT;
        this.maskBitmap = null;
        this.maskMatrix = new Matrix();
        this.transparentMaskMatrix = new Matrix();
        this.tempRect = new RectF();
        this.f505r = new RectF();
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.scaleDown = 0.95f;
        this.scaleUp = 1.05f;
        this.f504f = new float[MATRIX_MODE_ROTATE_RIGHT];
        this.centerOriginal = new PointF();
        this.maskBitmap = mask;
        this.points = points;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        createPathFromPoints();
        this.path.offset((float) offsetX, (float) offsetY);
        this.exceptionIndex = exceptionIndex;
        this.bitmap = b;
        this.shapeMode = MATRIX_MODE_ROTATE_LEFT;
        init();
    }

    public void changeRatio(PointF[] points, int[] exceptionIndex, int offsetX, int offsetY) {
        this.points = points;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        createPathFromPoints();
        this.path.offset((float) offsetX, (float) offsetY);
        this.exceptionIndex = exceptionIndex;
        init();
    }

    public void freeBitmaps() {
        if (!(this.bitmap == null || this.bitmap.isRecycled())) {
            this.bitmap.recycle();
        }
        if (this.maskBitmap != null && !this.maskBitmap.isRecycled()) {
            this.maskBitmap = null;
        }
    }

    public CopyOfShapeBeforeScrapBook(RectF r, Bitmap b) {
        this.offsetY = MATRIX_MODE_FIT;
        this.offsetX = MATRIX_MODE_FIT;
        this.SHAPE_MODE_POINT = MATRIX_MODE_CENTER;
        this.SHAPE_MODE_RECT = MATRIX_MODE_ROTATE_RIGHT;
        this.SHAPE_MODE_MASK = MATRIX_MODE_ROTATE_LEFT;
        this.maskBitmap = null;
        this.maskMatrix = new Matrix();
        this.transparentMaskMatrix = new Matrix();
        this.tempRect = new RectF();
        this.f505r = new RectF();
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.scaleDown = 0.95f;
        this.scaleUp = 1.05f;
        this.f504f = new float[MATRIX_MODE_ROTATE_RIGHT];
        this.centerOriginal = new PointF();
        this.sourceRect = r;
        createPathFromRect();
        this.bitmap = b;
        this.shapeMode = MATRIX_MODE_ROTATE_RIGHT;
        init();
    }

    public void setRadius(CornerPathEffect corEffect) {
        this.paintPath.setPathEffect(corEffect);
        this.paintTransparent.setPathEffect(corEffect);
    }

    public float smallestDistance() {
        float smallestDistance = 1500.0f;
        for (int i = MATRIX_MODE_FIT; i < this.points.length; i += MATRIX_MODE_CENTER) {
            for (int j = MATRIX_MODE_FIT; j < this.points.length; j += MATRIX_MODE_CENTER) {
                if (i != j) {
                    float distance = Math.abs(this.points[i].x - this.points[j].x) + Math.abs(this.points[i].y - this.points[j].y);
                    if (distance < smallestDistance) {
                        smallestDistance = distance;
                    }
                }
            }
        }
        return smallestDistance;
    }

    public void init() {
        this.bounds = new RectF();
        this.originalPath = new Path(this.path);
        this.path.computeBounds(this.bounds, true);
        this.originalBounds = new RectF(this.bounds);
        this.paintXferMode = new Paint(MATRIX_MODE_CENTER);
        this.paintXferMode.setFilterBitmap(true);
        this.paintXferMode.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        this.paintPath = new Paint(MATRIX_MODE_CENTER);
        this.paintPath.setFilterBitmap(true);
        this.maskPaint = new Paint(MATRIX_MODE_CENTER);
        this.maskPaint.setFilterBitmap(true);
        this.paintTransparent = new Paint(MATRIX_MODE_CENTER);
        this.paintTransparent.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.paintTransparent.setFilterBitmap(true);
        setBitmapPosition();
        this.paintPath.setPathEffect(new CornerPathEffect(3.0f));
        this.pathMatrix = new Matrix();
        this.region = new Region();
        this.region.setPath(this.path, new Region((int) this.bounds.left, (int) this.bounds.top, (int) this.bounds.right, (int) this.bounds.bottom));
    }

    public void setBitmap(Bitmap bitmap, boolean isFilter) {
        this.bitmap = bitmap;
        if (!isFilter) {
            setBitmapPosition();
        }
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public Bitmap getMaskBitmap() {
        return this.maskBitmap;
    }

    private void setBitmapPosition() {
        float scaleBitmap;
        int bitmapWidth = this.bitmap.getWidth();
        int bitmapHeight = this.bitmap.getHeight();
        float scaleBitmapX = this.bounds.width() / ((float) bitmapWidth);
        float scaleBitmapY = this.bounds.height() / ((float) bitmapHeight);
        if (scaleBitmapX < scaleBitmapY) {
            scaleBitmap = scaleBitmapY;
        } else {
            scaleBitmap = scaleBitmapX;
        }
        float bitmapY = this.bounds.top - (((((float) bitmapHeight) * scaleBitmap) - this.bounds.height()) / 2.0f);
        float bitmapX = this.bounds.left - (((((float) bitmapWidth) * scaleBitmap) - this.bounds.width()) / 2.0f);
        this.bitmapMatrix = new Matrix();
        this.bitmapMatrix.reset();
        this.bitmapMatrix.postScale(scaleBitmap, scaleBitmap);
        this.bitmapMatrix.postTranslate(bitmapX, bitmapY);
        if (this.shapeMode == MATRIX_MODE_ROTATE_LEFT) {
            setMaskBitmapPositions();
        }
    }

    private void setMaskBitmapPositions() {
        if (this.maskBitmap != null) {
            float scaleMaskBitmap;
            float scaleMaskBitmapTr;
            int maskBitmapWidth = this.maskBitmap.getWidth();
            int maskBitmapHeight = this.maskBitmap.getHeight();
            float scaleMaskBitmapX = this.bounds.width() / ((float) maskBitmapWidth);
            float scaleMaskBitmapY = this.bounds.height() / ((float) maskBitmapHeight);
            if (scaleMaskBitmapX > scaleMaskBitmapY) {
                scaleMaskBitmap = scaleMaskBitmapY;
            } else {
                scaleMaskBitmap = scaleMaskBitmapX;
            }
            float maskBitmapY = this.bounds.top - (((((float) maskBitmapHeight) * scaleMaskBitmap) - this.bounds.height()) / 2.0f);
            float maskBitmapX = this.bounds.left - (((((float) maskBitmapWidth) * scaleMaskBitmap) - this.bounds.width()) / 2.0f);
            this.maskMatrix = new Matrix();
            this.maskMatrix.reset();
            this.maskMatrix.postScale(scaleMaskBitmap, scaleMaskBitmap);
            this.maskMatrix.postTranslate(maskBitmapX, maskBitmapY);
            float scaleMaskBitmapXTr = this.originalBounds.width() / ((float) maskBitmapWidth);
            float scaleMaskBitmapYTr = this.originalBounds.height() / ((float) maskBitmapHeight);
            if (scaleMaskBitmapXTr > scaleMaskBitmapYTr) {
                scaleMaskBitmapTr = scaleMaskBitmapYTr;
            } else {
                scaleMaskBitmapTr = scaleMaskBitmapXTr;
            }
            float maskBitmapYTr = this.originalBounds.top - (((((float) maskBitmapHeight) * scaleMaskBitmapTr) - this.originalBounds.height()) / 2.0f);
            float maskBitmapXTr = this.originalBounds.left - (((((float) maskBitmapWidth) * scaleMaskBitmapTr) - this.originalBounds.width()) / 2.0f);
            this.transparentMaskMatrix = new Matrix();
            this.transparentMaskMatrix.reset();
            this.transparentMaskMatrix.postScale(scaleMaskBitmapTr, scaleMaskBitmapTr);
            this.transparentMaskMatrix.postTranslate(maskBitmapXTr, maskBitmapYTr);
        }
    }

    public void scalePath(float distance, float width, float height) {
        if (this.shapeMode == MATRIX_MODE_CENTER) {
            pathTransform(this.points, this.path, distance, this.originalBounds.centerX(), this.originalBounds.centerY());
        } else if (this.shapeMode == MATRIX_MODE_ROTATE_RIGHT) {
            pathTransformFromRect(distance);
        } else {
            float scaleX = (width - (2.0f * distance)) / width;
            float scaleY = (height - (2.0f * distance)) / height;
            this.pathMatrix.reset();
            this.pathMatrix.setScale(scaleX, scaleY, this.originalBounds.centerX(), this.originalBounds.centerY());
            this.originalPath.transform(this.pathMatrix, this.path);
        }
        this.path.computeBounds(this.bounds, true);
        if (this.shapeMode == MATRIX_MODE_ROTATE_LEFT) {
            setMaskBitmapPositions();
        }
    }

    void createPathFromPoints() {
        this.path = new Path();
        this.path.setFillType(FillType.EVEN_ODD);
        this.path.moveTo(this.points[MATRIX_MODE_FIT].x, this.points[MATRIX_MODE_FIT].y);
        for (int i = MATRIX_MODE_CENTER; i < this.points.length; i += MATRIX_MODE_CENTER) {
            this.path.lineTo(this.points[i].x, this.points[i].y);
        }
        this.path.lineTo(this.points[MATRIX_MODE_FIT].x, this.points[MATRIX_MODE_FIT].y);
        this.path.close();
    }

    void createPathFromRect() {
        this.path = new Path();
        this.path.addRect(this.sourceRect, Direction.CCW);
    }

    void pathTransform(PointF[] points, Path path, float distance, float centerX, float centerY) {
        int i;
        centerX -= (float) this.offsetX;
        centerY -= (float) this.offsetY;
        path.rewind();
        path.setFillType(FillType.EVEN_ODD);
        int size = points.length;
        float[] distanceArray = new float[size];
        for (i = MATRIX_MODE_FIT; i < size; i += MATRIX_MODE_CENTER) {
            distanceArray[i] = distance;
        }
        if (this.exceptionIndex != null) {
            for (i = MATRIX_MODE_FIT; i < this.exceptionIndex.length; i += MATRIX_MODE_CENTER) {
                distanceArray[this.exceptionIndex[i]] = 2.0f * distance;
            }
        }
        path.moveTo(checkRange(points[MATRIX_MODE_FIT].x, distanceArray[MATRIX_MODE_FIT], centerX), checkRange(points[MATRIX_MODE_FIT].y, distance, centerY));
        for (i = MATRIX_MODE_CENTER; i < size; i += MATRIX_MODE_CENTER) {
            path.lineTo(checkRange(points[i].x, distanceArray[i], centerX), checkRange(points[i].y, distance, centerY));
        }
        path.lineTo(checkRange(points[MATRIX_MODE_FIT].x, distanceArray[MATRIX_MODE_FIT], centerX), checkRange(points[MATRIX_MODE_FIT].y, distance, centerY));
        path.close();
        path.offset((float) this.offsetX, (float) this.offsetY);
    }

    void pathTransformFromRect(float distance) {
        float top = this.sourceRect.top;
        float left = this.sourceRect.left;
        float bottom = this.sourceRect.bottom;
        this.tempRect.set(left + distance, top + distance, this.sourceRect.right - distance, bottom - distance);
        this.path.rewind();
        this.path.addRect(this.tempRect, Direction.CCW);
    }

    float checkRange(float pointA, float distance, float centerA) {
        if (pointA > centerA) {
            return pointA - distance;
        }
        if (pointA < centerA) {
            return pointA + distance;
        }
        return pointA;
    }

    public void drawShape(Canvas canvas, int width, int height, int j, boolean drawPorterClear) {
        if (drawPorterClear) {
            if (this.shapeMode != MATRIX_MODE_ROTATE_LEFT) {
                canvas.drawPath(this.originalPath, this.paintTransparent);
            } else if (!(this.maskBitmap == null || this.maskBitmap.isRecycled())) {
                canvas.drawBitmap(this.maskBitmap, this.transparentMaskMatrix, this.paintTransparent);
            }
            canvas.restoreToCount(j);
        }
        this.f505r.set(0.0f, 0.0f, (float) this.bitmap.getWidth(), (float) this.bitmap.getHeight());
        this.bitmapMatrix.mapRect(this.f505r);
        int k = canvas.saveLayer(this.f505r, null, 31);
        if (this.shapeMode != MATRIX_MODE_ROTATE_LEFT) {
            canvas.drawPath(this.path, this.paintPath);
        } else if (!(this.maskBitmap == null || this.maskBitmap.isRecycled())) {
            canvas.drawBitmap(this.maskBitmap, this.maskMatrix, this.maskPaint);
        }
        canvas.drawBitmap(this.bitmap, this.bitmapMatrix, this.paintXferMode);
        canvas.restoreToCount(k);
    }

    public void drawShapeForSave(Canvas canvas, int width, int height, int j, boolean drawPorterClear) {
        if (drawPorterClear) {
            if (this.shapeMode != MATRIX_MODE_ROTATE_LEFT) {
                canvas.drawPath(this.originalPath, this.paintTransparent);
            } else if (!(this.maskBitmap == null || this.maskBitmap.isRecycled())) {
                canvas.drawBitmap(this.maskBitmap, this.transparentMaskMatrix, this.paintTransparent);
            }
            canvas.restoreToCount(j);
        }
        RectF r = new RectF(0.0f, 0.0f, (float) (this.bitmap.getWidth() + MATRIX_MODE_FIT), (float) (this.bitmap.getHeight() + MATRIX_MODE_FIT));
        this.bitmapMatrix.mapRect(r);
        int k = canvas.saveLayer(r, null, 31);
        if (this.shapeMode != MATRIX_MODE_ROTATE_LEFT) {
            canvas.drawPath(this.path, this.paintPath);
        } else if (!(this.maskBitmap == null || this.maskBitmap.isRecycled())) {
            canvas.drawBitmap(this.maskBitmap, this.maskMatrix, this.maskPaint);
        }
        canvas.drawBitmap(this.bitmap, this.bitmapMatrix, this.paintXferMode);
        canvas.restoreToCount(k);
    }

    public void initIcon(int width, int height) {
        this.iconPaint = new Paint(MATRIX_MODE_CENTER);
        this.iconPaint.setFilterBitmap(true);
        this.iconPaint.setColor(-7829368);
        this.paintXferMode.setColor(-7829368);
        scalePath(5.0f, (float) width, (float) height);
        this.iconMaskPaint = new Paint(MATRIX_MODE_CENTER);
        this.iconMaskPaint.setFilterBitmap(true);
        this.iconMaskPaint.setColor(-7829368);
        this.iconXferMode = new PorterDuffXfermode(Mode.SRC_IN);
        this.iconMaskPaint.setXfermode(this.iconXferMode);
    }

    void drawShapeIcon(Canvas canvas, int width, int height, int j, boolean drawPorterClear) {
        setMaskBitmapPositions();
        this.path.offset((float) (-this.offsetX), (float) (-this.offsetY));
        this.originalPath.offset((float) (-this.offsetX), (float) (-this.offsetY));
        this.maskMatrix.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
        this.transparentMaskMatrix.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
        if (drawPorterClear) {
            if (this.shapeMode == MATRIX_MODE_ROTATE_LEFT) {
                canvas.drawBitmap(this.maskBitmap, this.transparentMaskMatrix, this.paintTransparent);
            } else {
                canvas.drawPath(this.originalPath, this.paintTransparent);
            }
            canvas.restoreToCount(j);
        }
        if (this.shapeMode == MATRIX_MODE_ROTATE_LEFT) {
            int i = canvas.saveLayer(0.0f, 0.0f, (float) width, (float) height, null, 31);
            canvas.drawBitmap(this.maskBitmap, this.maskMatrix, this.iconPaint);
            canvas.drawBitmap(this.maskBitmap, this.maskMatrix, this.iconMaskPaint);
            canvas.restoreToCount(i);
            return;
        }
        canvas.drawPath(this.path, this.iconPaint);
    }

    void drawShapeIcon2(Canvas canvas, int width, int height) {
        this.path.offset((float) (-this.offsetX), (float) (-this.offsetY));
        this.originalPath.offset((float) (-this.offsetX), (float) (-this.offsetY));
        this.maskMatrix.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
        this.transparentMaskMatrix.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
        Paint p2 = new Paint();
        if (this.shapeMode == MATRIX_MODE_ROTATE_LEFT) {
            int i = canvas.saveLayer(0.0f, 0.0f, (float) width, (float) height, null, 31);
            canvas.drawBitmap(this.maskBitmap, this.transparentMaskMatrix, p2);
            p2.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, p2);
            p2.setXfermode(null);
            canvas.restoreToCount(i);
            return;
        }
        canvas.drawPath(this.path, this.iconPaint);
    }

    void bitmapMatrixScale(float scaleX, float scaleY, float centerX, float centerY) {
        this.bitmapMatrix.postScale(scaleX, scaleY, centerX, centerY);
    }

    void bitmapMatrixTranslate(float dx, float dy) {
        this.bitmapMatrix.postTranslate(dx, dy);
    }

    void bitmapMatrixgGetValues(float[] values) {
        this.bitmapMatrix.getValues(values);
    }

    public void setScaleMatrix(int mode) {
        if (this.dx <= 0.5f) {
            this.dx = ((float) this.bitmap.getWidth()) / 100.0f;
        }
        if (this.dy <= 0.5f) {
            this.dy = ((float) this.bitmap.getHeight()) / 100.0f;
        }
        PointF centerOfImage = getCenterOfImage();
        if (mode == 0) {
            setMatrixFit();
        } else if (mode == MATRIX_MODE_CENTER) {
            setBitmapPosition();
        } else if (mode == MATRIX_MODE_ROTATE_LEFT) {
            this.bitmapMatrix.postRotate(-90.0f, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_ROTATE_RIGHT) {
            this.bitmapMatrix.postRotate(90.0f, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_FLIP_HORIZONTAL) {
            this.bitmapMatrix.postScale(-1.0f, 1.0f, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_FLIP_VERTICAL) {
            this.bitmapMatrix.postScale(1.0f, -1.0f, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_ROTATE_NEGATIVE) {
            this.bitmapMatrix.postRotate(-10.0f, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_ROTATE_POSITIVE) {
            this.bitmapMatrix.postRotate(10.0f, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_ZOOM_IN) {
            this.bitmapMatrix.postScale(this.scaleUp, this.scaleUp, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_ZOOM_OUT) {
            this.bitmapMatrix.postScale(this.scaleDown, this.scaleDown, centerOfImage.x, centerOfImage.y);
        } else if (mode == MATRIX_MODE_MOVE_LEFT) {
            this.bitmapMatrix.postTranslate(-this.dx, 0.0f);
        } else if (mode == MATRIX_MODE_MOVE_RIGHT) {
            this.bitmapMatrix.postTranslate(this.dx, 0.0f);
        } else if (mode == MATRIX_MODE_MOVE_UP) {
            this.bitmapMatrix.postTranslate(0.0f, -this.dy);
        } else if (mode == MATRIX_MODE_MOVE_DOWN) {
            this.bitmapMatrix.postTranslate(0.0f, this.dy);
        }
    }

    PointF getCenterOfImage() {
        int bitmapWidth = this.bitmap.getWidth();
        int bitmapHeight = this.bitmap.getHeight();
        if (this.centerOriginal == null) {
            this.centerOriginal = new PointF();
        }
        if (this.f504f == null) {
            this.f504f = new float[MATRIX_MODE_ROTATE_RIGHT];
        }
        float y = ((float) bitmapHeight) / 2.0f;
        this.f504f[MATRIX_MODE_FIT] = ((float) bitmapWidth) / 2.0f;
        this.f504f[MATRIX_MODE_CENTER] = y;
        this.bitmapMatrix.mapPoints(this.f504f);
        this.centerOriginal.set(this.f504f[MATRIX_MODE_FIT], this.f504f[MATRIX_MODE_CENTER]);
        return this.centerOriginal;
    }

    void setMatrixFit() {
        int bitmapWidth = this.bitmap.getWidth();
        int bitmapHeight = this.bitmap.getHeight();
        float scaleBitmap = Math.min(this.bounds.width() / ((float) bitmapWidth), this.bounds.height() / ((float) bitmapHeight));
        float bitmapY = this.bounds.top + ((this.bounds.height() - (((float) bitmapHeight) * scaleBitmap)) / 2.0f);
        float bitmapX = this.bounds.left + ((this.bounds.width() - (((float) bitmapWidth) * scaleBitmap)) / 2.0f);
        this.bitmapMatrix.reset();
        this.bitmapMatrix.postScale(scaleBitmap, scaleBitmap);
        this.bitmapMatrix.postTranslate(bitmapX, bitmapY);
    }
}
