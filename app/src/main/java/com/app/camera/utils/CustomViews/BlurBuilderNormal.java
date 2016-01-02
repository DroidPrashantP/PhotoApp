// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.utils.CustomViews;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

// Referenced classes of package com.lyrebirdstudio.lyrebirdlibrary:
//            EffectFragment

public class BlurBuilderNormal
{

    private static final float BITMAP_SCALE = 0.4F;
    public static final int BLUR_RADIUS_DEFAULT = 14;
    public static final int BLUR_RADIUS_MAX = 25;
    public static final int BLUR_RADIUS_SENTINEL = -1;
    private static final String TAG = BlurBuilderNormal.class.getName();
    Bitmap inputBitmap;
    int lastBlurRadius;
    Matrix matrixBlur;
    Bitmap outputBitmap;
    Paint paintBlur;

    public BlurBuilderNormal()
    {
        lastBlurRadius = -1;
        paintBlur = new Paint(2);
        matrixBlur = new Matrix();
    }

    public static Bitmap createCroppedBitmap(Bitmap bitmap, int i, int j, int k, int l, boolean flag)
    {
        Bitmap bitmap1 = Bitmap.createBitmap(k, l, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint();
        paint.setFilterBitmap(flag);
        canvas.drawBitmap(bitmap, -i, -j, paint);
        return bitmap1;
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int i, int j, boolean flag)
    {
        Matrix matrix = new Matrix();
        matrix.setScale((float)i / (float)bitmap.getWidth(), (float)j / (float)bitmap.getHeight());
        Bitmap bitmap1 = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint();
        paint.setFilterBitmap(flag);
        canvas.drawBitmap(bitmap, matrix, paint);
        return bitmap1;
    }

    public Bitmap createBlurBitmapNDK(Bitmap bitmap, int i)
    {
        int j = i;
        if (i <= 2)
        {
            j = 2;
        }
        if (lastBlurRadius == j && outputBitmap != null)
        {
            return outputBitmap;
        }
        if (inputBitmap == null)
        {
            int k = Math.round((float)bitmap.getWidth() * 0.4F);
            int l = Math.round((float)bitmap.getHeight() * 0.4F);
            i = k;
            if (k % 2 == 1)
            {
                i = k + 1;
            }
            k = l;
            if (l % 2 == 1)
            {
                k = l + 1;
            }
            if (android.os.Build.VERSION.SDK_INT < 12)
            {
                android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
                options.inDither = true;
                options.inScaled = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inPurgeable = true;
                inputBitmap = createScaledBitmap(bitmap, i, k, false);
            } else
            {
                inputBitmap = Bitmap.createScaledBitmap(bitmap, i, k, false);
            }
        }
        if (outputBitmap == null)
        {
            outputBitmap = inputBitmap.copy(Bitmap.Config.ARGB_8888, true);
        } else
        {
            (new Canvas(outputBitmap)).drawBitmap(inputBitmap, 0.0F, 0.0F, paintBlur);
        }
       // EffectFragment.functionToBlur(outputBitmap, j);
        lastBlurRadius = j;
        return outputBitmap;
    }

    public void destroy()
    {
        outputBitmap.recycle();
        outputBitmap = null;
        inputBitmap.recycle();
        inputBitmap = null;
    }

    public int getBlur()
    {
        return lastBlurRadius;
    }

}
