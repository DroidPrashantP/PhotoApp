// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.utils.CustomViews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;

import com.app.camera.fragments.EffectFragment;
import com.commit451.nativestackblur.NativeStackBlur;

// Referenced classes of package com.lyrebirdstudio.lyrebirdlibrary:
//            EffectFragment

public class BlurBuilderNormal
{

    private static final float BITMAP_SCALE = 0.4f;
    public static final int BLUR_RADIUS_DEFAULT = 14;
    public static final int BLUR_RADIUS_MAX = 25;
    public static final int BLUR_RADIUS_SENTINEL = -1;
    private static final String TAG;
    Bitmap inputBitmap;
    int lastBlurRadius;
    Matrix matrixBlur;
    Bitmap outputBitmap;
    Paint paintBlur;

    public BlurBuilderNormal() {
        this.lastBlurRadius = BLUR_RADIUS_SENTINEL;
        this.paintBlur = new Paint(2);
        this.matrixBlur = new Matrix();
    }

    static {
        TAG = BlurBuilderNormal.class.getSimpleName();
    }

    public Bitmap createBlurBitmapNDK(Bitmap sourceBitmap, int radius) {
        if (radius <= 2) {
            radius = 2;
        }
        if (this.lastBlurRadius == radius && this.outputBitmap != null) {
            return this.outputBitmap;
        }
        if (this.inputBitmap == null) {
            int width = Math.round(((float) sourceBitmap.getWidth()) * BITMAP_SCALE);
            int height = Math.round(((float) sourceBitmap.getHeight()) * BITMAP_SCALE);
            if (width % 2 == 1) {
                width++;
            }
            if (height % 2 == 1) {
                height++;
            }
            if (Build.VERSION.SDK_INT < 12) {
                BitmapFactory.Options myOptions = new BitmapFactory.Options();
                myOptions.inDither = true;
                myOptions.inScaled = false;
                myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
                myOptions.inPurgeable = true;
                this.inputBitmap = createScaledBitmap(sourceBitmap, width, height, false);
            } else {
                this.inputBitmap = Bitmap.createScaledBitmap(sourceBitmap, width, height, false);
            }
        }
        if (this.outputBitmap == null) {
            this.outputBitmap = this.inputBitmap.copy(Bitmap.Config.ARGB_8888, true);
        } else {
            new Canvas(this.outputBitmap).drawBitmap(this.inputBitmap, 0.0f, 0.0f, this.paintBlur);
        }
        //EffectFragment.functionToBlur(this.outputBitmap, radius);
        outputBitmap = NativeStackBlur.process(sourceBitmap, radius * 2);
        this.lastBlurRadius = radius;
        return this.outputBitmap;
    }

    public void destroy() {
        this.outputBitmap.recycle();
        this.outputBitmap = null;
        this.inputBitmap.recycle();
        this.inputBitmap = null;
    }

    public int getBlur() {
        return this.lastBlurRadius;
    }

    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter) {
        Matrix m = new Matrix();
        m.setScale(((float) dstWidth) / ((float) src.getWidth()), ((float) dstHeight) / ((float) src.getHeight()));
        Bitmap result = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setFilterBitmap(filter);
        canvas.drawBitmap(src, m, paint);
        return result;
    }

    public static Bitmap createCroppedBitmap(Bitmap src, int left, int top, int width, int height, boolean filter) {
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setFilterBitmap(filter);
        canvas.drawBitmap(src, (float) (-left), (float) (-top), paint);
        return result;
    }

}
