// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.paddycameraeditior.utils.CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

public class BlurBuilder
{

    public static final float BITMAP_SCALE = 0.4F;
    public static final float BLUR_RADIUS_DEFAULT = 14F;
    public static final float BLUR_RADIUS_MAX = 25F;
    public static final int BLUR_RADIUS_SENTINEL = -1;
    private static final String TAG = BlurBuilder.class.getName();
    private float blur;
    Bitmap outputBitmap;
    RenderScript rs;
    ScriptIntrinsicBlur theIntrinsic;
    Allocation tmpIn;
    Allocation tmpOut;

    public BlurBuilder(Context context, Bitmap bitmap)
    {
        blur = 0.1F;
        init(context, bitmap);
        blur = 14F;
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blur(float f)
    {
        float f1 = 0.1F;
        if (f != 0.0F) {
            blur = f1;
            theIntrinsic.setRadius(f1);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);
            return outputBitmap;
        }
        if (f < 0.0F)
        {
            f1 = blur;
        } else
        {
            f1 = f;
            if (f > 25F)
            {
                f1 = 25F;
            }
        }
        return outputBitmap;
    }

    public void destroy()
    {
        rs.destroy();
        outputBitmap.recycle();
        outputBitmap = null;
        tmpOut.destroy();
        tmpIn.destroy();
    }

    public float getBlur()
    {
        return blur;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean getStatus()
    {
        if (rs == null || tmpOut == null || theIntrinsic == null || outputBitmap == null || outputBitmap.isRecycled() || tmpIn == null)
        {
            return false;
        }
        try
        {
            theIntrinsic.setRadius(blur);
        }
        catch (Exception exception)
        {
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void init(Context context, Bitmap bitmap)
    {
        int j = Math.round((float)bitmap.getWidth() * 0.4F);
        int k = Math.round((float)bitmap.getHeight() * 0.4F);
        Log.e("TAG", (new StringBuilder("blur width ")).append(j).toString());
        Log.e("TAG", (new StringBuilder("blur height ")).append(k).toString());
        int i = j;
        if (j % 2 == 1)
        {
            i = j + 1;
        }
        j = k;
        if (k % 2 == 1)
        {
            j = k + 1;
        }
        if (android.os.Build.VERSION.SDK_INT < 12)
        {
            android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
            options.inDither = true;
            options.inScaled = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPurgeable = true;
            bitmap = createScaledBitmap(bitmap, i, j, false);
        } else
        {
            bitmap = Bitmap.createScaledBitmap(bitmap, i, j, false);
        }
        outputBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        rs = RenderScript.create(context);
        theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        tmpIn = Allocation.createFromBitmap(rs, bitmap);
        tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(14F);
        theIntrinsic.setInput(tmpIn);
        if (bitmap != outputBitmap)
        {
            bitmap.recycle();
        }
    }

}
