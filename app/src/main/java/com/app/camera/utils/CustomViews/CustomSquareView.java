package com.app.camera.utils.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by sandeep on 18/12/15.
 */
public class CustomSquareView extends View {

    public static final int BACKGROUND_BLUR = 1;
    public static final int BACKGROUND_PATTERN = 0;
    private static final int INVALID_POINTER_ID = 1;
    public static final int PATTERN_SENTINEL = -1;
    private static final int UPPER_SIZE_LIMIT = 2048;
    private static final String TAG = CustomSquareView.class.getName();
    int backgroundMode;
    float bitmapHeight;
    Matrix bitmapMatrix;
    float bitmapWidth;
    Bitmap blurBitmap;
    Matrix blurBitmapMatrix;
    private int blurRadius;
    PointF centerOriginal;
    Paint dashPaint;
    Path dashPathHorizontal;
    Path dashPathHorizontalTemp;
    Path dashPathVertical;
    Path dashPathVerticalTemp;
    float epsilon;
    float f[];
    Bitmap filterBitmap;
    float finalAngle;
    Paint grayPaint;
    Matrix identityMatrix;
    Matrix inverseMatrix;
    boolean isOrthogonal;
    private int mActivePointerId;
    float mLastTouchX;
    float mLastTouchY;
    private RotationGestureDetector mRotationDetector;
    private ScaleGestureDetector mScaleDetector;
    float mScaleFactor;
    int offsetX;
    int offsetY;
    Paint paint;
    Bitmap patternBitmap;
    Paint patternPaint;
    Paint pointPaint;
    RotationGestureDetector.OnRotationGestureListener rotateListener;
    int screenHeight;
    int screenWidth;
    Matrix textMatrix;
    //    final SquareActivity this$0;
    float values[];
    int viewHeight;
    int viewWidth;
    private Context context;
    private Bitmap sourceBitmap;

    public CustomSquareView(Context context) {
        super(context);
    }

    private String saveBitmap() {
        float f1;
        Bitmap bitmap;
        Object obj;
        int i = maxSizeForSave(context, 2048F);
        f1 = Math.max(viewHeight, viewWidth);
        f1 = (float) i / f1;
        int k = (int) ((float) viewWidth * f1);
        int l = (int) ((float) viewHeight * f1);
        i = k;
        if (k <= 0) {
            i = viewWidth;
            // Log.e(SquareActivity.TAG, "newBtmWidth");
        }
        k = l;
        if (l <= 0) {
            k = viewHeight;
            //Log.e(SquareActivity.TAG, "newBtmHeight");
        }
        bitmap = Bitmap.createBitmap(i, k, android.graphics.Bitmap.Config.ARGB_8888);
        obj = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postTranslate(-offsetX, -offsetY);
        matrix.postScale(f1, f1);
        ((Canvas) (obj)).setMatrix(matrix);
        if (backgroundMode == 0) {
            ((Canvas) (obj)).drawRect(offsetX, offsetY, offsetX + viewWidth, offsetY + viewHeight, patternPaint);
        }
        if (blurBitmap != null && !blurBitmap.isRecycled() && backgroundMode == 1) {
            ((Canvas) (obj)).drawBitmap(blurBitmap, blurBitmapMatrix, paint);
        }
        ((Canvas) (obj)).drawBitmap(sourceBitmap, bitmapMatrix, paint);
        if (filterBitmap != null && !filterBitmap.isRecycled()) {
            ((Canvas) (obj)).drawBitmap(filterBitmap, bitmapMatrix, paint);
        }
        return bitmap.toString();
    }

    private void setPathPositions() {
        dashPathVertical.reset();
        dashPathHorizontal.reset();
        dashPathVertical.moveTo(bitmapWidth / 2.0F, -bitmapHeight / 5F);
        dashPathVertical.lineTo(bitmapWidth / 2.0F, (bitmapHeight * 6F) / 5F);
        dashPathHorizontal.moveTo(-bitmapWidth / 5F, bitmapHeight / 2.0F);
        dashPathHorizontal.lineTo((bitmapWidth * 6F) / 5F, bitmapHeight / 2.0F);
    }

    PointF getCenterOfImage() {
        if (centerOriginal == null) {
            centerOriginal = new PointF();
        }
        if (f == null) {
            f = new float[2];
        }
        float f1 = bitmapWidth / 2.0F;
        float f2 = bitmapHeight / 2.0F;
        f[0] = f1;
        f[1] = f2;
        bitmapMatrix.mapPoints(f);
        centerOriginal.set(f[0], f[1]);
        return centerOriginal;
    }

    float getMatrixRotation(Matrix matrix) {
        matrix.getValues(values);
        return (float) Math.round(Math.atan2(values[1], values[0]) * 57.295779513082323D);
    }

    public void onDraw(Canvas canvas) {
        if (backgroundMode == 0) {
            canvas.drawRect(offsetX, offsetY, offsetX + viewWidth, offsetY + viewHeight, patternPaint);
        }
        if (blurBitmap != null && !blurBitmap.isRecycled() && backgroundMode == 1) {
            canvas.drawBitmap(blurBitmap, blurBitmapMatrix, paint);
        }
        canvas.drawBitmap(sourceBitmap, bitmapMatrix, paint);
        if (filterBitmap != null && !filterBitmap.isRecycled()) {
            canvas.drawBitmap(filterBitmap, bitmapMatrix, paint);
        }
        if (isOrthogonal) {
            dashPathVertical.transform(bitmapMatrix, dashPathVerticalTemp);
            dashPathHorizontal.transform(bitmapMatrix, dashPathHorizontalTemp);
            canvas.drawPath(dashPathVerticalTemp, dashPaint);
            canvas.drawPath(dashPathHorizontalTemp, dashPaint);
        }

//        canvas.drawRect(0.0F, 0.0F, screenWidth, offsetY, grayPaint);
//        canvas.drawRect(offsetX, offsetY + viewHeight, screenWidth, screenHeight, grayPaint);

    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        return true;

    }

    public void setBlurBitmap(int i) {
//        if (blurBuilderNormal == null)
//        {
//            blurBuilderNormal = new BlurBuilderNormal();
//        }
//        backgroundMode = 1;
//        blurBitmap = blurBuilderNormal.createBlurBitmapNDK(sourceBitmap, i);
//        blurRadius = blurBuilderNormal.getBlur();
//        setMatrixCenter(blurBitmapMatrix, blurBitmap.getWidth(), blurBitmap.getHeight());
//        postInvalidate();
    }

    public void setCropBitmap(int i, int j, int k, int l) {
        Bitmap bitmap = sourceBitmap;
        int i1 = k;
        if ((float) k > bitmapWidth) {
            i1 = (int) bitmapWidth;
        }
        k = l;
        if ((float) l > bitmapHeight) {
            k = (int) bitmapHeight;
        }
//        if (android.os.Build.VERSION.SDK_INT < 12)
//        {
//            sourceBitmap = BlurBuilderNormal.createCroppedBitmap(bitmap, i, j, i1 - i, k - j, false);
//        } else
//        {
//            sourceBitmap = Bitmap.createBitmap(bitmap, i, j, i1 - i, k - j);
//        }
//        effectFragment.setBitmap(sourceBitmap);
//        effectFragment.execQueue();
//        if (bitmap != sourceBitmap)
//        {
//            bitmap.recycle();
//        }
//        bitmapHeight = sourceBitmap.getHeight();
//        bitmapWidth = sourceBitmap.getWidth();
//        setPathPositions();
//        setScaleMatrix(0);
    }

    void setMatrixCenter(Matrix matrix, float f1, float f2) {
        matrix.reset();
        float f3 = Math.max((float) viewWidth / f1, (float) viewHeight / f2);
        float f4 = offsetX;
        f1 = ((float) viewWidth - f3 * f1) / 2.0F;
        float f5 = offsetY;
        f2 = ((float) viewHeight - f3 * f2) / 2.0F;
        matrix.postScale(f3, f3);
        matrix.postTranslate(f4 + f1, f5 + f2);
    }

    void setPatternPaint(int i) {
        if (patternPaint == null) {
            patternPaint = new Paint(1);
            patternPaint.setColor(-1);
        }
        if (i == -1) {
            patternPaint.setShader(null);
            patternPaint.setColor(-1);
            postInvalidate();
            return;
        } else {
            patternBitmap = BitmapFactory.decodeResource(getResources(), i);
            BitmapShader bitmapshader = new BitmapShader(patternBitmap, android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
            patternPaint.setShader(bitmapshader);
            postInvalidate();
            return;
        }
    }

    void setPatternPaintColor(int i) {
        if (patternPaint == null) {
            patternPaint = new Paint(1);
        }
        patternPaint.setShader(null);
        patternPaint.setColor(i);
        postInvalidate();
    }

    public void setScaleMatrix(int i) {
        PointF pointf = getCenterOfImage();
        if (i != 0)
            bitmapMatrix.reset();
        float f1 = Math.min((float) viewWidth / bitmapWidth, (float) viewHeight / bitmapHeight);
        float f2 = offsetX;
        float f3 = ((float) viewWidth - bitmapWidth * f1) / 2.0F;
        float f4 = offsetY;
        float f5 = ((float) viewHeight - bitmapHeight * f1) / 2.0F;
        bitmapMatrix.postScale(f1, f1);
        bitmapMatrix.postTranslate(f2 + f3, f4 + f5);
        postInvalidate();
        if (i == 1) {
            setMatrixCenter(bitmapMatrix, bitmapWidth, bitmapHeight);
        } else if (i == 3) {
            bitmapMatrix.postRotate(-90F, pointf.x, pointf.y);
        } else if (i == 2) {
            bitmapMatrix.postRotate(90F, pointf.x, pointf.y);
        } else if (i == 4) {
            bitmapMatrix.postScale(-1F, 1.0F, pointf.x, pointf.y);
        } else if (i == 5) {
            bitmapMatrix.postScale(1.0F, -1F, pointf.x, pointf.y);
        }

    }


    public CustomSquareView(Context context1, int i, int j) {
        super(context1);
        ScaleListener scaleListener = new ScaleListener(context1);
        identityMatrix = new Matrix();
        textMatrix = new Matrix();
        backgroundMode = 0;
        dashPaint = new Paint();
        dashPathVerticalTemp = new Path();
        dashPathHorizontalTemp = new Path();
        isOrthogonal = false;
        blurRadius = 14;
        inverseMatrix = new Matrix();
        f = new float[2];
        centerOriginal = new PointF();
        mActivePointerId = 1;
        values = new float[9];
        mScaleFactor = 1.0F;
        finalAngle = 0.0F;
        epsilon = 4F;
        rotateListener = new RotationView();
        screenWidth = i;
        screenHeight = j;
        paint = new Paint();
        bitmapMatrix = new Matrix();
        paint.setColor(0xffcccccc);
        int k = Math.min(i, j);
        viewWidth = k;
        viewHeight = k;
        offsetX = Math.abs(i - viewWidth) / 2;
        offsetY = Math.abs(j - viewHeight) / 2;
        (new android.graphics.BitmapFactory.Options()).inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888;
        bitmapWidth = sourceBitmap.getWidth();
        bitmapHeight = sourceBitmap.getHeight();
        float f1 = Math.min((float) viewWidth / bitmapWidth, (float) viewHeight / bitmapHeight);
        float f2 = offsetX;
        float f3 = ((float) viewWidth - bitmapWidth * f1) / 2.0F;
        float f4 = offsetY;
        float f5 = ((float) viewHeight - bitmapHeight * f1) / 2.0F;
        bitmapMatrix.postScale(f1, f1);
        bitmapMatrix.postTranslate(f2 + f3, f4 + f5);
        mScaleDetector = new ScaleGestureDetector(context1, scaleListener);
        mRotationDetector = new RotationGestureDetector(rotateListener);
        grayPaint = new Paint();
        grayPaint.setColor(Color.RED);
        pointPaint = new Paint();
        pointPaint.setColor(-Color.GREEN);
        pointPaint.setStrokeWidth(20F);
        blurBitmapMatrix = new Matrix();
        patternPaint = new Paint(1);
        patternPaint.setColor(-1);
        dashPaint.setColor(0xff888888);
        dashPaint.setStyle(android.graphics.Paint.Style.STROKE);
        f2 = (float) screenWidth / 120F;
        f1 = f2;
        if (f2 <= 0.0F) {
            f1 = 5F;
        }
        dashPaint.setStrokeWidth(f1);
        dashPaint.setPathEffect(new DashPathEffect(new float[]{
                f1, f1
        }, 0.0F));
        dashPathVertical = new Path();
        dashPathHorizontal = new Path();
        setPathPositions();
    }

    private class ScaleListener extends android.view.ScaleGestureDetector.SimpleOnScaleGestureListener {

        public ScaleListener(Context context) {
        }

        public boolean onScale(ScaleGestureDetector scalegesturedetector) {
            mScaleFactor = scalegesturedetector.getScaleFactor();
            scalegesturedetector.isInProgress();
            mScaleFactor = Math.max(0.1F, Math.min(mScaleFactor, 5F));
//            scalegesturedetector = getCenterOfImage();
//            bitmapMatrix.postScale(mScaleFactor, mScaleFactor, ((PointF) (scalegesturedetector)).x, ((PointF) (scalegesturedetector)).y);
            invalidate();
            return true;
        }
    }


    class RotationView implements RotationGestureDetector.OnRotationGestureListener {


        public void OnRotation(RotationGestureDetector rotationgesturedetector) {
            float f = rotationgesturedetector.getAngle();
            float f1 = getMatrixRotation(bitmapMatrix);
            if ((f1 == 0.0F || f1 == 90F || f1 == 180F || f1 == -180F || f1 == -90F) && Math.abs(finalAngle - f) < epsilon) {
                isOrthogonal = true;
                return;
            }
            if (Math.abs((f1 - finalAngle) + f) < epsilon) {
                f = finalAngle - f1;
                isOrthogonal = true;
            } else if (Math.abs(90F - ((f1 - finalAngle) + f)) < epsilon) {
                f = (finalAngle + 90F) - f1;
                isOrthogonal = true;
            } else if (Math.abs(180F - ((f1 - finalAngle) + f)) < epsilon) {
                f = (finalAngle + 180F) - f1;
                isOrthogonal = true;
            } else if (Math.abs(-180F - ((f1 - finalAngle) + f)) < epsilon) {
                f = finalAngle - 180F - f1;
                isOrthogonal = true;
            } else if (Math.abs(-90F - ((f1 - finalAngle) + f)) < epsilon) {
                f = finalAngle - 90F - f1;
                isOrthogonal = true;
            } else {
                isOrthogonal = false;
            }
//            rotationgesturedetector = getCenterOfImage();
//            bitmapMatrix.postRotate(finalAngle - f, ((PointF) (rotationgesturedetector)).x, ((PointF) (rotationgesturedetector)).y);
            finalAngle = f;
            invalidate();
        }

    }

    public static int maxSizeForSave(Context context, float f) {
        float f1 = 60F;
        if (android.os.Build.VERSION.SDK_INT <= 11) {
            f1 = 160F;
        }
        Log.e(TAG, (new StringBuilder("divider = ")).append(f1).toString());
        int i = (int) Math.sqrt(getLeftSizeOfMemory() / (double) f1);
        if (i > 0) {
            return (int) Math.min(i, f);
        } else {
            return (int) f;
        }
    }

    public static double getLeftSizeOfMemory() {
        double d = Double.valueOf(Runtime.getRuntime().maxMemory()).doubleValue();
        double d1 = Double.valueOf(Runtime.getRuntime().totalMemory()).doubleValue();
        double d2 = Double.valueOf(Debug.getNativeHeapAllocatedSize()).doubleValue();
        return d - (d1 - Double.valueOf(Runtime.getRuntime().freeMemory()).doubleValue()) - d2;
    }
}
