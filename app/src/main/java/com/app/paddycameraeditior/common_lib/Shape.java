// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.paddycameraeditior.common_lib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;

import com.app.paddycameraeditior.pointlist.Collage;

public class Shape {

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
    public static final int MESSAGE_DEFAULT = 0;
    public static final int MESSAGE_MAX_BOTTOM = 6;
    public static final int MESSAGE_MAX_LEFT = 3;
    public static final int MESSAGE_MAX_RIGHT = 4;
    public static final int MESSAGE_MAX_TOP = 5;
    public static final int MESSAGE_MAX_ZOOM = 1;
    public static final int MESSAGE_MIN_ZOOM = 2;
    private static final String TAG = "Shape";
    static final int scrapBookRotation[] = {
            13, -13, -7, -12, 11, 8, -9, 10, 9
    };
    public final int SHAPE_MODE_MASK;
    public final int SHAPE_MODE_POINT;
    public final int SHAPE_MODE_RECT;
    private Bitmap bitmap;
    int bitmapHeight;
    Matrix bitmapMatrix = new Matrix();
    RectF bitmapRect;
    int bitmapWidth;
    Paint borderPaint;
    int borderStrokeWidth;
    public RectF bounds;
    Bitmap btmDelete;
    Bitmap btmScale;
    PointF centerOriginal;
    Paint dashPaint;
    Path dashPathHorizontal;
    Path dashPathVertical;
    int delW;
    float deleteWidthHalf;
    float dx;
    float dy;
    int exceptionIndex[];
    float f[];
    Paint iconMaskPaint;
    Paint iconPaint;
    Xfermode iconXferMode;
    Matrix inverse;
    boolean isScrapBook;
    private Bitmap maskBitmap;
    private Matrix maskMatrix;
    Paint maskPaint;
    float maxScale;
    float minScale;
    NinePatchDrawable npd;
    int npdPadding;
    int offsetX;
    int offsetY;
    RectF originalBounds;
    Path originalPath;
    float p[];
    private Paint paintPath;
    Paint paintScrap;
    private Paint paintTransparent;
    Paint paintXferMode;
    Path path;
    Path pathList[];
    int pathListLength;
    Matrix pathMatrix;
    PointF points[];
    float pts[];
    RectF r;
    public Region region;
    Matrix removeBitmapMatrix;
    Matrix scaleBitmapMatrix;
    float scaleDown;
    float scaleUp;
    float scrapBookPadding;
    int screenWidth;
    int shapeMode;
    RectF sourceRect;
    final float tempRadius;
    RectF tempRect;
    final float tempScrapBookPadding;
    float tempTouchStrokeWidth;
    Paint touchPaint;
    RectF touchRect;
    float touchStrokeWidth;
    Matrix transparentMaskMatrix;
    float v[];
    float values[];

    public Shape(PointF apointf[], Bitmap bitmap1, int ai[], int i, int j, Bitmap bitmap2, boolean flag,
                 int k, boolean flag1, Bitmap bitmap3, Bitmap bitmap4, int l) {
        offsetY = 0;
        offsetX = 0;
        SHAPE_MODE_POINT = 1;
        SHAPE_MODE_RECT = 2;
        SHAPE_MODE_MASK = 3;
        maskBitmap = null;
        maskMatrix = new Matrix();
        transparentMaskMatrix = new Matrix();
        v = new float[9];
        tempRect = new RectF();
        r = new RectF();
        minScale = 1.0F;
        maxScale = 1.0F;
        bitmapRect = new RectF();
        p = new float[2];
        dx = 0.0F;
        dy = 0.0F;
        scaleDown = 0.95F;
        scaleUp = 1.05F;
        f = new float[2];
        centerOriginal = new PointF();
        touchPaint = new Paint(1);
        borderPaint = new Paint(1);
        paintScrap = new Paint(2);
        pts = new float[2];
        inverse = new Matrix();
        tempScrapBookPadding = 25F;
        scrapBookPadding = 25F;
        tempTouchStrokeWidth = 8F;
        touchStrokeWidth = tempTouchStrokeWidth;
        values = new float[9];
        tempRadius = 60F;
        borderStrokeWidth = 6;
        dashPaint = new Paint();
        delW = 0;
        deleteWidthHalf = 0.0F;
        npdPadding = 16;
        maskBitmap = bitmap2;
        points = apointf;
        offsetX = i;
        offsetY = j;
        btmDelete = bitmap3;
        btmScale = bitmap4;
        screenWidth = l;
        isScrapBook = flag;
        createPathFromPoints();
        path.offset(i, j);
        exceptionIndex = ai;
        bitmap = bitmap1;
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        shapeMode = 3;
        init(flag, k, false, 0, 0);
    }

    public Shape(PointF apointf[], Bitmap bitmap1, int ai[], int i, int j, boolean flag, int k,
                 boolean flag1, Bitmap bitmap2, Bitmap bitmap3, int l) {
        offsetY = 0;
        offsetX = 0;
        SHAPE_MODE_POINT = 1;
        SHAPE_MODE_RECT = 2;
        SHAPE_MODE_MASK = 3;
        maskBitmap = null;
        maskMatrix = new Matrix();
        transparentMaskMatrix = new Matrix();
        v = new float[9];
        tempRect = new RectF();
        r = new RectF();
        minScale = 1.0F;
        maxScale = 1.0F;
        bitmapRect = new RectF();
        p = new float[2];
        dx = 0.0F;
        dy = 0.0F;
        scaleDown = 0.95F;
        scaleUp = 1.05F;
        f = new float[2];
        centerOriginal = new PointF();
        touchPaint = new Paint(1);
        borderPaint = new Paint(1);
        paintScrap = new Paint(2);
        pts = new float[2];
        inverse = new Matrix();
        tempScrapBookPadding = 25F;
        scrapBookPadding = 25F;
        tempTouchStrokeWidth = 8F;
        touchStrokeWidth = tempTouchStrokeWidth;
        values = new float[9];
        tempRadius = 60F;
        borderStrokeWidth = 6;
        dashPaint = new Paint();
        delW = 0;
        deleteWidthHalf = 0.0F;
        npdPadding = 16;
        points = apointf;
        offsetX = i;
        offsetY = j;
        btmDelete = bitmap2;
        btmScale = bitmap3;
        screenWidth = l;
        isScrapBook = flag;
        createPathFromPoints();
        path.offset(i, j);
        exceptionIndex = ai;
        bitmap = bitmap1;
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        shapeMode = 1;
        init(flag, k, false, 0, 0);
    }

    private float getBitmapScale() {
        float f1 = bounds.width() / (float) bitmapWidth;
        float f2 = bounds.height() / (float) bitmapHeight;
        if (f1 < f2) {
            return f2;
        } else {
            return f1;
        }
    }

    private float getBitmapScaleRotated() {
        float f1 = getMatrixRotation(bitmapMatrix);
        float f2;
        if (f1 == 90F || f1 == 270F || f1 == -90F || f1 == -270F) {
            f1 = bounds.width() / (float) bitmapHeight;
            f2 = bounds.height() / (float) bitmapWidth;
        } else {
            f1 = bounds.width() / (float) bitmapWidth;
            f2 = bounds.height() / (float) bitmapHeight;
        }
        if (f1 < f2) {
            return f2;
        } else {
            return f1;
        }
    }

    public void setBitmapPosition() {
        float f1 = getBitmapScale();
        float f2 = bounds.top;
        float f3 = ((float) bitmapHeight * f1 - bounds.height()) / 2.0F;
        float f4 = bounds.left;
        float f5 = ((float) bitmapWidth * f1 - bounds.width()) / 2.0F;
        bitmapMatrix = new Matrix();
        bitmapMatrix.reset();
        bitmapMatrix.postScale(f1, f1);
        bitmapMatrix.postTranslate(f4 - f5, f2 - f3);
        if (shapeMode == 3) {
            setMaskBitmapPositions();
        }
        setMaxMinScales(f1);
    }

    private void setMaskBitmapPositions() {
        if (maskBitmap == null) {
            return;
        }
        int i = maskBitmap.getWidth();
        int j = maskBitmap.getHeight();
        float f1 = bounds.width() / (float) i;
        float f2 = bounds.height() / (float) j;
        if (f1 > f2) {
            f1 = f2;
        }
        f2 = bounds.top;
        float f3 = ((float) j * f1 - bounds.height()) / 2.0F;
        float f4 = bounds.left;
        float f5 = ((float) i * f1 - bounds.width()) / 2.0F;
        maskMatrix = new Matrix();
        maskMatrix.reset();
        maskMatrix.postScale(f1, f1);
        maskMatrix.postTranslate(f4 - f5, f2 - f3);
        f1 = originalBounds.width() / (float) i;
        f2 = originalBounds.height() / (float) j;
        if (f1 > f2) {
            f1 = f2;
        }
        f2 = originalBounds.top;
        f3 = ((float) j * f1 - originalBounds.height()) / 2.0F;
        f4 = originalBounds.left;
        f5 = ((float) i * f1 - originalBounds.width()) / 2.0F;
        transparentMaskMatrix = new Matrix();
        transparentMaskMatrix.reset();
        transparentMaskMatrix.postScale(f1, f1);
        transparentMaskMatrix.postTranslate(f4 - f5, f2 - f3);
    }

    private void setScrapBookBitmapPosition(int i, boolean flag, int j, int k) {
        float af[];
        Object obj;

        i = bitmapWidth;
        int l = bitmapHeight;
        af = new float[8];
        af[0] = 0.0F;
        af[1] = 0.0F;
        af[2] = i;
        af[3] = 0.0F;
        af[4] = i;
        af[5] = l;
        af[6] = 0.0F;
        af[7] = l;
        bitmapMatrix.mapPoints(af);
        obj = new RectF(offsetX, offsetY, offsetX + j, offsetY + k);
        if (((RectF) (obj)).contains(af[0], af[1]) || ((RectF) (obj)).contains(af[2], af[3]) || ((RectF) (obj)).contains(af[4], af[5]) || ((RectF) (obj)).contains(af[6], af[7])) {
            PointF pointf;
            PointF pointf2;
            pointf = new PointF(offsetX, offsetY);
            pointf2 = new PointF(offsetX + j, offsetY);
            obj = new PointF();

            if (af[1] >= (float) offsetY) {

            }
            float f1;

            float af1[];
            ((PointF) (obj)).set(af[0], af[1]);
            af1 = new float[4];
            af1[0] = pointToLineDistance(pointf, pointf2, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("0  ")).append(distToSegment(((PointF) (obj)), pointf, pointf2)).toString());
            ((PointF) (obj)).set(af[2], af[3]);
            af1[1] = pointToLineDistance(pointf, pointf2, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("1  ")).append(distToSegment(((PointF) (obj)), pointf, pointf2)).toString());
            ((PointF) (obj)).set(af[4], af[5]);
            af1[2] = pointToLineDistance(pointf, pointf2, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("2  ")).append(distToSegment(((PointF) (obj)), pointf, pointf2)).toString());
            ((PointF) (obj)).set(af[6], af[7]);
            af1[3] = pointToLineDistance(pointf, pointf2, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("3  ")).append(distToSegment(((PointF) (obj)), pointf, pointf2)).toString());
            f1 = af1[0];
            j = 0;
            i = 1;
            if (i < 4) {
                float f4 = f1;
                if (af1[i] < f1) {
                    f4 = af1[i];
                    j = i;
                }
                Log.e("Shape", (new StringBuilder("fi  ")).append(af1[i]).toString());
                i++;
                f1 = f4;
            } else {
                bitmapMatrix.postTranslate(0.0F, (float) (offsetY + 120) - af[j * 2 + 1]);
            }

            PointF pointf1 = new PointF(offsetX, offsetY + k);
            PointF pointf3 = new PointF(offsetX + j, offsetY + k);
            ((PointF) (obj)).set(af[0], af[1]);
            float af2[] = new float[4];
            af2[0] = pointToLineDistance(pointf1, pointf3, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("A  x ")).append(pointf1.x).append(" y ").append(pointf1.y).toString());
            Log.e("Shape", (new StringBuilder("B  x ")).append(pointf3.x).append(" y ").append(pointf3.y).toString());
            Log.e("Shape", (new StringBuilder("0  ")).append(distToSegment(((PointF) (obj)), pointf1, pointf3)).toString());
            Log.e("Shape", (new StringBuilder("0  x ")).append(((PointF) (obj)).x).append(" y ").append(((PointF) (obj)).y).toString());
            ((PointF) (obj)).set(af[2], af[3]);
            af2[1] = pointToLineDistance(pointf1, pointf3, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("1  ")).append(distToSegment(((PointF) (obj)), pointf1, pointf3)).toString());
            Log.e("Shape", (new StringBuilder("1  x ")).append(((PointF) (obj)).x).append(" y ").append(((PointF) (obj)).y).toString());
            ((PointF) (obj)).set(af[4], af[5]);
            af2[2] = pointToLineDistance(pointf1, pointf3, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("2  ")).append(distToSegment(((PointF) (obj)), pointf1, pointf3)).toString());
            Log.e("Shape", (new StringBuilder("2  x ")).append(((PointF) (obj)).x).append(" y ").append(((PointF) (obj)).y).toString());
            ((PointF) (obj)).set(af[6], af[7]);
            af2[3] = pointToLineDistance(pointf1, pointf3, ((PointF) (obj)));
            Log.e("Shape", (new StringBuilder("3  ")).append(distToSegment(((PointF) (obj)), pointf1, pointf3)).toString());
            Log.e("Shape", (new StringBuilder("3  x ")).append(((PointF) (obj)).x).append(" y ").append(((PointF) (obj)).y).toString());
            float f2 = af2[0];
            j = 0;
            Log.e("Shape", (new StringBuilder("bi  ")).append(af2[0]).toString());
            i = 1;
            do {
                if (i >= 4) {
                    Log.e("Shape", (new StringBuilder("minIndex  ")).append(j).toString());
                    Log.e("Shape", (new StringBuilder(" points[minIndex*2+1] ")).append(af[j * 2 + 1]).toString());
                    Log.e("Shape", (new StringBuilder("translate ")).append((float) ((offsetY + k) - 120) - af[j * 2 + 1]).toString());
                    bitmapMatrix.postTranslate(0.0F, (float) ((offsetY + k) - 120) - af[j * 2 + 1]);
                    return;
                }
                float f5 = f2;
                if (af2[i] < f2) {
                    f5 = af2[i];
                    j = i;
                }
                Log.e("Shape", (new StringBuilder("bi  ")).append(af2[i]).toString());
                i++;
                f2 = f5;
            } while (true);
        } else {
            bitmapMatrix = new Matrix();
            setMatrixFit();
            float f3 = getScale();
            setMaxMinScales(f3);
            f3 = 1.0F / f3;
            touchStrokeWidth = tempTouchStrokeWidth * f3;
            scrapBookPadding = 25F * f3;
            bitmapMatrix.postRotate(scrapBookRotation[i], bounds.left + bounds.width() / 2.0F, bounds.top + bounds.height() / 2.0F);
            touchRect = new RectF(-scrapBookPadding, -scrapBookPadding, (float) bitmapWidth + scrapBookPadding, (float) bitmapHeight + scrapBookPadding);
            touchPaint.setColor(0xffec4f4f);
            touchPaint.setFilterBitmap(true);
            touchPaint.setStyle(Paint.Style.STROKE);
            touchPaint.setStrokeWidth(touchStrokeWidth);
            borderPaint.setColor(-1);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(borderStrokeWidth);
            borderPaint.setAntiAlias(true);
        }
    }

    void bitmapMatrixRotate(float f1) {
        p[0] = bitmapWidth / 2;
        p[1] = bitmapHeight / 2;
        bitmapMatrix.mapPoints(p);
        bitmapMatrix.postRotate(f1, p[0], p[1]);
    }

    void bitmapMatrixScale(float f1, float f2, float f3, float f4) {
        bitmapMatrix.postScale(f1, f2, f3, f4);
        checkScaleBoundries();
    }

    void bitmapMatrixScaleScrapBook(float f1, float f2) {
        p[0] = bitmapWidth / 2;
        p[1] = bitmapHeight / 2;
        bitmapMatrix.mapPoints(p);
        bitmapMatrix.postScale(f1, f2, p[0], p[1]);
        checkScaleBoundries();
    }

    void bitmapMatrixTranslate(float f1, float f2) {
        bitmapMatrix.postTranslate(f1, f2);
        if (!isScrapBook) {
            checkBoundries();
        }
    }

    public void bitmapMatrixgGetValues(float af[]) {
        bitmapMatrix.getValues(af);
    }

    public void changeRatio(PointF apointf[], int ai[], int i, int j, boolean flag, int k, int l,
                            int i1) {
        points = apointf;
        offsetX = i;
        offsetY = j;
        createPathFromPoints();
        path.offset(i, j);
        exceptionIndex = ai;
        init(flag, k, true, l, i1);
    }

    public void checkBoundries() {
        bitmapRect.set(0.0F, 0.0F, bitmapWidth, bitmapHeight);
        bitmapMatrix.mapRect(bitmapRect);
        float f1 = 0.0F;
        float f2 = 0.0F;
        if (bitmapRect.left > bounds.left) {
            f1 = bounds.left - bitmapRect.left;
        }
        if (bitmapRect.top > bounds.top) {
            f2 = bounds.top - bitmapRect.top;
        }
        if (bitmapRect.right < bounds.right) {
            f1 = bounds.right - bitmapRect.right;
        }
        if (bitmapRect.bottom < bounds.bottom) {
            f2 = bounds.bottom - bitmapRect.bottom;
        }
        bitmapMatrix.postTranslate(f1, f2);
    }

    float checkRange(float f1, float f2, float f3) {
        if (f1 > f3) {
            return f1 - f2;
        }
        if (f1 < f3) {
            return f1 + f2;
        } else {
            return f1;
        }
    }

    void checkScaleBoundries() {
        float f1 = getScale();
        PointF pointf = getCenterOfImage();
        if (f1 < minScale) {
            bitmapMatrix.postScale(minScale / f1, minScale / f1, pointf.x, pointf.y);
        }
        if (f1 > maxScale) {
            bitmapMatrix.postScale(maxScale / f1, maxScale / f1, pointf.x, pointf.y);
        }
    }

    public void checkScaleBounds() {
        setMinScales(getBitmapScale());
        checkScaleBoundries();
    }

    void createPathFromPoints() {
        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(points[0].x, points[0].y);
        int i = 1;
        do {
            if (i >= points.length) {
                path.lineTo(points[0].x, points[0].y);
                path.close();
                return;
            }
            path.lineTo(points[i].x, points[i].y);
            i++;
        } while (true);
    }

    void createPathFromRect() {
        path = new Path();
        path.addRect(sourceRect, Path.Direction.CCW);
    }

    float dist2(PointF pointf, PointF pointf1) {
        return sqr(pointf.x - pointf1.x) + sqr(pointf.y - pointf1.y);
    }

    float distToSegment(PointF pointf, PointF pointf1, PointF pointf2) {
        return (float) Math.sqrt(distToSegmentSquared(pointf, pointf1, pointf2));
    }

    float distToSegmentSquared(PointF pointf, PointF pointf1, PointF pointf2) {
        float f1 = dist2(pointf1, pointf2);
        if (f1 == 0.0F) {
            return dist2(pointf, pointf1);
        }
        f1 = ((pointf.x - pointf1.x) * (pointf2.x - pointf1.x) + (pointf.y - pointf1.y) * (pointf2.y - pointf1.y)) / f1;
        if (f1 < 0.0F) {
            return dist2(pointf, pointf1);
        }
        if (f1 > 1.0F) {
            return dist2(pointf, pointf2);
        } else {
            return dist2(pointf, new PointF(pointf1.x + (pointf2.x - pointf1.x) * f1, pointf1.y + (pointf2.y - pointf1.y) * f1));
        }
    }

    public void drawShape(Canvas canvas, int i, int j, int k, boolean flag) {
        if (flag) {
            if (shapeMode == 3) {
                if (maskBitmap != null && !maskBitmap.isRecycled()) {
                    canvas.drawBitmap(maskBitmap, transparentMaskMatrix, paintTransparent);
                }
            } else {
                canvas.drawPath(originalPath, paintTransparent);
            }
            canvas.restoreToCount(k);
        }
        r.set(0.0F, 0.0F, bitmapWidth, bitmapHeight);
        bitmapMatrix.mapRect(r);
        i = canvas.saveLayer(r, null, 0);
        if (shapeMode == 3) {
            if (maskBitmap != null && !maskBitmap.isRecycled()) {
                canvas.drawBitmap(maskBitmap, maskMatrix, maskPaint);
            }
        } else {
            canvas.drawPath(path, paintPath);
        }
        canvas.drawBitmap(bitmap, bitmapMatrix, paintXferMode);
        canvas.restoreToCount(i);
    }

    public void drawShapeForSave(Canvas canvas, int i, int j, int k, boolean flag) {
        RectF rectf;
        if (flag) {

            if (shapeMode == 3) {
                if (maskBitmap != null && !maskBitmap.isRecycled()) {
                    canvas.drawBitmap(maskBitmap, transparentMaskMatrix, paintTransparent);
                }
            } else {
                canvas.drawPath(originalPath, paintTransparent);
            }
            canvas.restoreToCount(k);
        }
        rectf = new RectF(0.0F, 0.0F, bitmapWidth + 0, bitmapHeight + 0);
        bitmapMatrix.mapRect(rectf);
        i = canvas.saveLayer(rectf, null, 0);
        if (shapeMode == 3) {
            if (maskBitmap != null && !maskBitmap.isRecycled()) {
                canvas.drawBitmap(maskBitmap, maskMatrix, maskPaint);
            }
        } else {
            canvas.drawPath(path, paintPath);
        }
        canvas.drawBitmap(bitmap, bitmapMatrix, paintXferMode);
        canvas.restoreToCount(i);
    }

    public void drawShapeForScrapBook(Canvas canvas, int i, int j, boolean flag, boolean flag1) {
        canvas.save();
        canvas.concat(bitmapMatrix);
        npd.setBounds(-npdPadding - borderStrokeWidth, -npdPadding - borderStrokeWidth, bitmapWidth + npdPadding + borderStrokeWidth, bitmapHeight + npdPadding + borderStrokeWidth);
        npd.draw(canvas);
        canvas.drawBitmap(bitmap, 0.0F, 0.0F, paintScrap);
        if (flag) {
            float f1 = 1.0F / getScale();
            touchStrokeWidth = tempTouchStrokeWidth * f1;
            touchPaint.setStrokeWidth(touchStrokeWidth);
            canvas.drawRect(touchRect, touchPaint);
            setDelAndScaleBitmapMatrix();
            if (btmDelete != null && !btmDelete.isRecycled()) {
                canvas.drawBitmap(btmDelete, removeBitmapMatrix, touchPaint);
            }
            if (btmScale != null && !btmScale.isRecycled()) {
                canvas.drawBitmap(btmScale, scaleBitmapMatrix, touchPaint);
            }
            if (flag1) {
                canvas.drawPath(dashPathVertical, dashPaint);
                canvas.drawPath(dashPathHorizontal, dashPaint);
            }
        }
        canvas.drawRect(-borderStrokeWidth / 2, -borderStrokeWidth / 2, bitmapWidth + borderStrokeWidth / 2, bitmapHeight + borderStrokeWidth / 2, borderPaint);
        canvas.restore();
    }

    void drawShapeIcon(Canvas canvas, int i, int j, int k, boolean flag) {
        setMaskBitmapPositions();
        path.offset(-offsetX, -offsetY);
        originalPath.offset(-offsetX, -offsetY);
        maskMatrix.postTranslate(-offsetX, -offsetY);
        transparentMaskMatrix.postTranslate(-offsetX, -offsetY);
        if (flag) {
            if (shapeMode == 3) {
                canvas.drawBitmap(maskBitmap, transparentMaskMatrix, paintTransparent);
            } else {
                canvas.drawPath(originalPath, paintTransparent);
            }
            canvas.restoreToCount(k);
        }
        if (shapeMode == 3) {
            i = canvas.saveLayer(0.0F, 0.0F, i, j, null, 0);
            canvas.drawBitmap(maskBitmap, maskMatrix, iconPaint);
            canvas.drawBitmap(maskBitmap, maskMatrix, iconMaskPaint);
            canvas.restoreToCount(i);
            return;
        } else {
            canvas.drawPath(path, iconPaint);
            return;
        }
    }

    void drawShapeIcon2(Canvas canvas, int i, int j) {
        path.offset(-offsetX, -offsetY);
        originalPath.offset(-offsetX, -offsetY);
        maskMatrix.postTranslate(-offsetX, -offsetY);
        transparentMaskMatrix.postTranslate(-offsetX, -offsetY);
        Paint paint = new Paint();
        if (shapeMode == 3) {
            int k = canvas.saveLayer(0.0F, 0.0F, i, j, null, 0);
            canvas.drawBitmap(maskBitmap, transparentMaskMatrix, paint);
            paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
            canvas.drawRect(0.0F, 0.0F, i, j, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(k);
            return;
        } else {
            canvas.drawPath(path, iconPaint);
            return;
        }
    }

    public void freeBitmaps() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        if (maskBitmap != null && !maskBitmap.isRecycled()) {
            maskBitmap = null;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    PointF getCenterOfImage() {
        if (centerOriginal == null) {
            centerOriginal = new PointF();
        }
        if (f == null) {
            f = new float[2];
        }
        float f1 = (float) bitmapWidth / 2.0F;
        float f2 = (float) bitmapHeight / 2.0F;
        f[0] = f1;
        f[1] = f2;
        bitmapMatrix.mapPoints(f);
        centerOriginal.set(f[0], f[1]);
        return centerOriginal;
    }

    float[] getMappedCenter() {
        pts[0] = bitmapWidth / 2;
        pts[1] = bitmapHeight / 2;
        bitmapMatrix.mapPoints(pts, pts);
        return pts;
    }

    public Bitmap getMaskBitmap() {
        return maskBitmap;
    }

    float getMatrixRotation(Matrix matrix) {
        matrix.getValues(v);
        return (float) Math.round(Math.atan2(v[1], v[0]) * 57.295779513082323D);
    }

    float getScale() {
        bitmapMatrix.getValues(values);
        float f1 = values[0];
        float f2 = values[3];
        f2 = (float) Math.sqrt(f1 * f1 + f2 * f2);
        f1 = f2;
        if (f2 <= 0.0F) {
            f1 = 1.0F;
        }
        return f1;
    }

    public void init(boolean flag, int i, boolean flag1, int j, int k) {
        bounds = new RectF();
        originalPath = new Path(path);
        path.computeBounds(bounds, true);
        originalBounds = new RectF(bounds);
        paintXferMode = new Paint(1);
        paintXferMode.setFilterBitmap(true);
        Object obj = new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN);
        paintXferMode.setXfermode(((Xfermode) (obj)));
        paintPath = new Paint(1);
        paintPath.setFilterBitmap(true);
        maskPaint = new Paint(1);
        maskPaint.setFilterBitmap(true);
        paintTransparent = new Paint(1);
        obj = new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR);
        paintTransparent.setXfermode(((Xfermode) (obj)));
        paintTransparent.setFilterBitmap(true);
        if (flag) {
            setScrapBookBitmapPosition(i, flag1, j, k);
        } else {
            setBitmapPosition();
        }
        obj = new CornerPathEffect(3F);
        paintPath.setPathEffect(((android.graphics.PathEffect) (obj)));
        pathMatrix = new Matrix();
        region = new Region();
        region.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        if (flag) {
            dashPaint.setColor(0xff888888);
            dashPaint.setStyle(Paint.Style.STROKE);
            float f2 = (float) screenWidth / 120F;
            float f1 = f2;
            if (f2 <= 0.0F) {
                f1 = 5F;
            }
            dashPaint.setStrokeWidth(f1);
            dashPaint.setPathEffect(new DashPathEffect(new float[]{
                    f1, f1
            }, 0.0F));
            dashPathVertical = new Path();
            dashPathVertical.moveTo(bitmapWidth / 2, -bitmapHeight / 5);
            dashPathVertical.lineTo(bitmapWidth / 2, (bitmapHeight * 6) / 5);
            dashPathHorizontal = new Path();
            dashPathHorizontal.moveTo(-bitmapWidth / 5, bitmapHeight / 2);
            dashPathHorizontal.lineTo((bitmapWidth * 6) / 5, bitmapHeight / 2);
        }
    }

    public void initIcon(int i, int j) {
        iconPaint = new Paint(1);
        iconPaint.setFilterBitmap(true);
        iconPaint.setColor(0xff888888);
        paintXferMode.setColor(0xff888888);
        scalePath(5F, i, j);
        iconMaskPaint = new Paint(1);
        iconMaskPaint.setFilterBitmap(true);
        iconMaskPaint.setColor(0xff888888);
        iconXferMode = new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN);
        iconMaskPaint.setXfermode(iconXferMode);
    }

    public void initScrapBook(NinePatchDrawable ninepatchdrawable) {
        setNinePatch(ninepatchdrawable);
    }

    boolean isInCircle(float f1, float f2) {
        pts[0] = f1;
        pts[1] = f2;
        bitmapMatrix.invert(inverse);
        inverse.mapPoints(pts, pts);
        f1 = pts[0];
        f2 = pts[1];
        float f3 = getScale();
        return (f1 - touchRect.right) * (f1 - touchRect.right) + (f2 - touchRect.bottom) * (f2 - touchRect.bottom) < (deleteWidthHalf * deleteWidthHalf) / (f3 * f3);
    }

    boolean isOnCross(float f1, float f2) {
        pts[0] = f1;
        pts[1] = f2;
        bitmapMatrix.invert(inverse);
        inverse.mapPoints(pts, pts);
        f1 = pts[0];
        f2 = pts[1];
        float f3 = getScale();
        return (f1 - touchRect.left) * (f1 - touchRect.left) + (f2 - touchRect.top) * (f2 - touchRect.top) < (deleteWidthHalf * deleteWidthHalf) / (f3 * f3);
    }

    public boolean isScrapBookSelected(float f1, float f2) {
        pts[0] = f1;
        pts[1] = f2;
        inverse.reset();
        bitmapMatrix.invert(inverse);
        inverse.mapPoints(pts, pts);
        f1 = pts[0];
        f2 = pts[1];
        return f1 >= 0.0F && f1 <= (float) bitmapWidth && f2 >= 0.0F && f2 <= (float) bitmapHeight;
    }

    void pathTransform(PointF apointf[], Path path1, float f1, float f2, float f3) {
        float af[];
        int i;
        int j;
        f2 -= offsetX;
        f3 -= offsetY;
        path1.rewind();
        path1.setFillType(Path.FillType.EVEN_ODD);
        j = apointf.length;
        af = new float[j];
        i = 0;
        if (i < j) {
            af[i] = f1;
            i++;
        } else {
            if (exceptionIndex == null) {
                if (i < exceptionIndex.length) {
                    af[exceptionIndex[i]] = 2.0F * f1;
                    i++;
                } else {
                    path1.moveTo(checkRange(apointf[0].x, af[0], f2), checkRange(apointf[0].y, f1, f3));
                    i = 1;
                }
            } else {
                i = 0;
            }
        }

        path1.lineTo(checkRange(apointf[i].x, af[i], f2), checkRange(apointf[i].y, f1, f3));
        i++;
        if (i >= j) {
            path1.lineTo(checkRange(apointf[0].x, af[0], f2), checkRange(apointf[0].y, f1, f3));
            path1.close();
            path1.offset(offsetX, offsetY);
            return;
        }
    }

    void pathTransformFromRect(float f1) {
        float f2 = sourceRect.top;
        float f3 = sourceRect.left;
        float f4 = sourceRect.bottom;
        float f5 = sourceRect.right;
        tempRect.set(f3 + f1, f2 + f1, f5 - f1, f4 - f1);
        path.rewind();
        path.addRect(tempRect, Path.Direction.CCW);
    }

    public float pointToLineDistance(PointF pointf, PointF pointf1, PointF pointf2) {
        float f1 = (float) Math.sqrt((pointf1.x - pointf.x) * (pointf1.x - pointf.x) + (pointf1.y - pointf.y) * (pointf1.y - pointf.y));
        return Math.abs((pointf2.x - pointf.x) * (pointf1.y - pointf.y) - (pointf2.y - pointf.y) * (pointf1.x - pointf.x)) / f1;
    }

    public void scalePath(float f1, float f2, float f3) {
        if (shapeMode == 1) {
            pathTransform(points, path, f1, originalBounds.centerX(), originalBounds.centerY());
        } else if (shapeMode == 2) {
            pathTransformFromRect(f1);
        } else {
            f2 = (f2 - 2.0F * f1) / f2;
            f1 = (f3 - 2.0F * f1) / f3;
            pathMatrix.reset();
            pathMatrix.setScale(f2, f1, originalBounds.centerX(), originalBounds.centerY());
            originalPath.transform(pathMatrix, path);
        }
        path.computeBounds(bounds, true);
        if (shapeMode == 3) {
            setMaskBitmapPositions();
        }
    }

    public void setBitmap(Bitmap bitmap1, boolean flag) {
        bitmap = bitmap1;
        bitmapWidth = bitmap1.getWidth();
        bitmapHeight = bitmap1.getHeight();
        if (!flag) {
            setBitmapPosition();
        }
    }

    void setDelAndScaleBitmapMatrix() {
        if (removeBitmapMatrix == null) {
            removeBitmapMatrix = new Matrix();
        }
        if (scaleBitmapMatrix == null) {
            scaleBitmapMatrix = new Matrix();
        }
        removeBitmapMatrix.reset();
        scaleBitmapMatrix.reset();
        if (delW == 0) {
            delW = btmDelete.getWidth();
        }
        if (screenWidth <= 0) {
            screenWidth = 720;
        }
        float f1 = (2.0F * ((float) screenWidth / 20F)) / (float) delW;
        deleteWidthHalf = ((float) delW * f1) / 1.4F;
        removeBitmapMatrix.postScale(f1, f1);
        removeBitmapMatrix.postTranslate(-scrapBookPadding - ((float) delW * f1) / 2.0F, -scrapBookPadding - ((float) delW * f1) / 2.0F);
        scaleBitmapMatrix.postScale(f1, f1);
        scaleBitmapMatrix.postTranslate(((float) bitmapWidth + scrapBookPadding) - ((float) delW * f1) / 2.0F, ((float) bitmapHeight + scrapBookPadding) - ((float) delW * f1) / 2.0F);
        f1 = getScale();
        scaleBitmapMatrix.postScale(1.0F / f1, 1.0F / f1, touchRect.right, touchRect.bottom);
        removeBitmapMatrix.postScale(1.0F / f1, 1.0F / f1, touchRect.left, touchRect.top);
        if (screenWidth > 0) {
            tempTouchStrokeWidth = (float) screenWidth / 120F;
        }
    }

    void setMatrixFit() {
        float f2 = Math.min(bounds.width() / (float) bitmapWidth, bounds.height() / (float) bitmapHeight);
        float f1 = f2;
        if (isScrapBook) {
            f1 = f2 * Collage.scrapBookShapeScale;
        }
        Log.e("Shape", (new StringBuilder("Collage.scrapBookShapeScale ")).append(Collage.scrapBookShapeScale).toString());
        f2 = bounds.top;
        float f3 = (bounds.height() - (float) bitmapHeight * f1) / 2.0F;
        float f4 = bounds.left;
        float f5 = (bounds.width() - (float) bitmapWidth * f1) / 2.0F;
        bitmapMatrix.reset();
        bitmapMatrix.postScale(f1, f1);
        bitmapMatrix.postTranslate(f4 + f5, f2 + f3);
    }

    void setMaxMinScales(float f1) {
        if (isScrapBook) {
            minScale = f1 / 2.0F;
        } else {
            minScale = f1;
        }
        if (isScrapBook) {
            maxScale = f1 * 2.0F;
            return;
        } else {
            maxScale = 4F * f1;
            return;
        }
    }

    void setMaxMinScalesRotated() {
        float f1 = getBitmapScaleRotated();
        minScale = f1;
        maxScale = 4F * f1;
    }

    void setMinScales(float f1) {
        if (isScrapBook) {
            minScale = f1 / 2.0F;
            return;
        } else {
            minScale = f1;
            return;
        }
    }

    public void setNinePatch(NinePatchDrawable ninepatchdrawable) {
        npd = ninepatchdrawable;
        //  ninepatchdrawable = new NinePatchDrawable();
//        touchRect.round(ninepatchdrawable);
    }

    public void setRadius(CornerPathEffect cornerpatheffect) {
        paintPath.setPathEffect(cornerpatheffect);
        paintTransparent.setPathEffect(cornerpatheffect);
    }

    public int setScaleMatrix(int i) {
        PointF pointf;
        if (dx <= 0.5F) {
            dx = (float) bitmapWidth / 100F;
        }
        if (dy <= 0.5F) {
            dy = (float) bitmapHeight / 100F;
        }
        pointf = getCenterOfImage();

        setMatrixFit();
        checkScaleBoundries();
        if (!isScrapBook) {
            checkBoundries();
        }
        if (i == 1) {

        } else if (i == 3) {
            bitmapMatrix.postRotate(-90F, pointf.x, pointf.y);
            if (!isScrapBook) {
                setMaxMinScalesRotated();
                float f1 = getScale();
                bitmapMatrix.postScale(minScale / f1, minScale / f1, pointf.x, pointf.y);
            }
        } else if (i == 2) {
            bitmapMatrix.postRotate(90F, pointf.x, pointf.y);
            if (!isScrapBook) {
                setMaxMinScalesRotated();
                float f2 = getScale();
                bitmapMatrix.postScale(minScale / f2, minScale / f2, pointf.x, pointf.y);
            }
        } else if (i == 4) {
            bitmapMatrix.postScale(-1F, 1.0F, pointf.x, pointf.y);
        } else if (i == 5) {
            bitmapMatrix.postScale(1.0F, -1F, pointf.x, pointf.y);
        } else if (i == 6) {
            bitmapMatrix.postRotate(-10F, pointf.x, pointf.y);
        } else if (i == 7) {
            bitmapMatrix.postRotate(10F, pointf.x, pointf.y);
        } else if (i == 8) {
            if (getScale() < maxScale) {
                bitmapMatrix.postScale(scaleUp, scaleUp, pointf.x, pointf.y);
            } else {
                return 1;
            }
        } else if (i == 9) {
            if (getScale() > minScale) {
                bitmapMatrix.postScale(scaleDown, scaleDown, pointf.x, pointf.y);
            } else {
                return 2;
            }
        } else if (i == 10) {
            bitmapRect.set(0.0F, 0.0F, bitmapWidth, bitmapHeight);
            bitmapMatrix.mapRect(bitmapRect);
            if (bitmapRect.right > bounds.right || isScrapBook) {
                bitmapMatrix.postTranslate(-dx, 0.0F);
            } else {
                return 3;
            }
        } else if (i == 11) {
            bitmapRect.set(0.0F, 0.0F, bitmapWidth, bitmapHeight);
            bitmapMatrix.mapRect(bitmapRect);
            if (bitmapRect.left < bounds.left || isScrapBook) {
                bitmapMatrix.postTranslate(dx, 0.0F);
            } else {
                return 4;
            }
        } else if (i == 12) {
            bitmapRect.set(0.0F, 0.0F, bitmapWidth, bitmapHeight);
            bitmapMatrix.mapRect(bitmapRect);
            if (bitmapRect.bottom > bounds.bottom || isScrapBook) {
                bitmapMatrix.postTranslate(0.0F, -dy);
            } else {
                return 5;
            }
        } else if (i == 13) {
            bitmapRect.set(0.0F, 0.0F, bitmapWidth, bitmapHeight);
            bitmapMatrix.mapRect(bitmapRect);
            if (bitmapRect.top < bounds.top || isScrapBook) {
                bitmapMatrix.postTranslate(0.0F, dy);
            } else {
                return 6;
            }
        }
        return 0;
    }

    public float smallestDistance() {
        float f1;
        int i;
        f1 = 1500F;
        i = 0;
        _L2:
        if (i >= points.length) {
            return f1;
        }
        int j = 0;
        do {
            label0:
            {
                if (j < points.length) {
                    break label0;
                }
                i++;
            }
            if (true) {
                continue;
            }
            float f2 = f1;
            if (i != j) {
                float f3 = Math.abs(points[i].x - points[j].x) + Math.abs(points[i].y - points[j].y);
                f2 = f1;
                if (f3 < f1) {
                    f2 = f3;
                }
            }
            j++;
            f1 = f2;
        } while (true);

    }

    float sqr(float f1) {
        return f1 * f1;
    }

}
