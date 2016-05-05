package com.app.camera.bitmap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.os.Debug;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;

import com.app.camera.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitmapResizer {
    public static Bitmap decodeFile(File f, int requiredSize) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int REQUIRED_SIZE = requiredSize;
            int width_tmp = o.outWidth;
            int height_tmp = o.outHeight;
            int scale = 1;
            while (width_tmp / 2 >= REQUIRED_SIZE && height_tmp / 2 >= REQUIRED_SIZE) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            Options o2 = new Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static int maxSizeForDimension(Context context) {
        return (int) Math.sqrt(((double) getFreeMemory(context)) / 80.0d);
    }

    public static long getFreeMemory(Context context) {
        return ((long) (((ActivityManager) context.getSystemService("activity")).getMemoryClass() * AccessibilityNodeInfoCompat.ACTION_DISMISS)) - Debug.getNativeHeapAllocatedSize();
    }

    public static Bitmap decodeX(String selectedImagePath, int requiredSize, int[] scaler, int[] rotation) {
        String o1 = BuildConfig.FLAVOR;
        try {
            o1 = new ExifInterface(selectedImagePath).getAttribute("Orientation");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (o1 == null) {
            o1 = BuildConfig.FLAVOR;
        }
        File f = new File(selectedImagePath);
        Bitmap localBitmap;
        Matrix localMatrix;
        Bitmap result;
        if (o1.contentEquals("6")) {
            rotation[0] = 90;
            localBitmap = decodeFile(f, requiredSize, scaler);
            localMatrix = new Matrix();
            localMatrix.postRotate(90.0f);
            result = Bitmap.createBitmap(localBitmap, 0, 0, localBitmap.getWidth(), localBitmap.getHeight(), localMatrix, false);
            localBitmap.recycle();
            return result;
        } else if (o1.contentEquals("8")) {
            rotation[0] = 270;
            localBitmap = decodeFile(f, requiredSize, scaler);
            localMatrix = new Matrix();
            localMatrix.postRotate(270.0f);
            result = Bitmap.createBitmap(localBitmap, 0, 0, localBitmap.getWidth(), localBitmap.getHeight(), localMatrix, false);
            localBitmap.recycle();
            return result;
        } else if (!o1.contentEquals("3")) {
            return decodeFile(f, requiredSize, scaler);
        } else {
            rotation[0] = 180;
            localBitmap = decodeFile(f, requiredSize, scaler);
            localMatrix = new Matrix();
            localMatrix.postRotate(180.0f);
            result = Bitmap.createBitmap(localBitmap, 0, 0, localBitmap.getWidth(), localBitmap.getHeight(), localMatrix, false);
            localBitmap.recycle();
            return result;
        }
    }

    public static Bitmap decodeFile(File f, int requiredSize, int[] scaler) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int REQUIRED_SIZE = requiredSize;
            int scale = 1;
            int max = Math.max(o.outWidth, o.outHeight);
            while (max / 2 >= REQUIRED_SIZE && max / 2 >= REQUIRED_SIZE) {
                max /= 2;
                scale *= 2;
            }
            Options o2 = new Options();
            o2.inSampleSize = scale;
            scaler[0] = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    public static Point decodeFileSize(File f, int requiredSize) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int REQUIRED_SIZE = requiredSize;
            int width_tmp = o.outWidth;
            int height_tmp = o.outHeight;
            int scale = 1;
            while (Math.max(width_tmp, height_tmp) / 2 > REQUIRED_SIZE) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            if (scale == 1) {
                return new Point(-1, -1);
            }
            return new Point(width_tmp, height_tmp);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    public static Point getFileSize(File f, int requiredSize) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            return new Point(o.outWidth, o.outHeight);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Bitmap decodeBitmapFromFile(String selectedImagePath, int MAX_SIZE) {
        int orientation = 0;
        try {
            orientation = new ExifInterface(selectedImagePath).getAttributeInt("Orientation", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap localBitmap = decodeFile(selectedImagePath, MAX_SIZE);
        if (localBitmap == null) {
            return null;
        }
      //  Bitmap graySourceBtm = rotateBitmap(localBitmap, orientation);
        Bitmap graySourceBtm = localBitmap;
        if (graySourceBtm == null || VERSION.SDK_INT >= 13) {
            return graySourceBtm;
        }
        Bitmap temp = graySourceBtm.copy(Config.ARGB_8888, true);
        if (graySourceBtm != temp) {
            graySourceBtm.recycle();
        }
        return temp;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static Bitmap rotateBitmap(Bitmap r9, int r10) {
        /*
        r5 = new android.graphics.Matrix;	 Catch:{ Exception -> 0x002d }
        r5.<init>();	 Catch:{ Exception -> 0x002d }
        switch(r10) {
            case 1: goto L_0x0008;
            case 2: goto L_0x0009;
            case 3: goto L_0x0027;
            case 4: goto L_0x0033;
            case 5: goto L_0x0040;
            case 6: goto L_0x004d;
            case 7: goto L_0x0053;
            case 8: goto L_0x0060;
            default: goto L_0x0008;
        };	 Catch:{ Exception -> 0x002d }
    L_0x0008:
        return r9;
    L_0x0009:
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.setScale(r0, r1);	 Catch:{ Exception -> 0x002d }
    L_0x0010:
        r1 = 0;
        r2 = 0;
        r3 = r9.getWidth();	 Catch:{ OutOfMemoryError -> 0x0066 }
        r4 = r9.getHeight();	 Catch:{ OutOfMemoryError -> 0x0066 }
        r6 = 1;
        r0 = r9;
        r7 = android.graphics.Bitmap.createBitmap(r0, r1, r2, r3, r4, r5, r6);	 Catch:{ OutOfMemoryError -> 0x0066 }
        if (r7 == r9) goto L_0x0025;
    L_0x0022:
        r9.recycle();	 Catch:{ OutOfMemoryError -> 0x0066 }
    L_0x0025:
        r9 = r7;
        goto L_0x0008;
    L_0x0027:
        r0 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r5.setRotate(r0);	 Catch:{ Exception -> 0x002d }
        goto L_0x0010;
    L_0x002d:
        r8 = move-exception;
        r8.printStackTrace();
        r9 = 0;
        goto L_0x0008;
    L_0x0033:
        r0 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r5.setRotate(r0);	 Catch:{ Exception -> 0x002d }
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.postScale(r0, r1);	 Catch:{ Exception -> 0x002d }
        goto L_0x0010;
    L_0x0040:
        r0 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r5.setRotate(r0);	 Catch:{ Exception -> 0x002d }
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.postScale(r0, r1);	 Catch:{ Exception -> 0x002d }
        goto L_0x0010;
    L_0x004d:
        r0 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r5.setRotate(r0);	 Catch:{ Exception -> 0x002d }
        goto L_0x0010;
    L_0x0053:
        r0 = -1028390912; // 0xffffffffc2b40000 float:-90.0 double:NaN;
        r5.setRotate(r0);	 Catch:{ Exception -> 0x002d }
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.postScale(r0, r1);	 Catch:{ Exception -> 0x002d }
        goto L_0x0010;
    L_0x0060:
        r0 = -1028390912; // 0xffffffffc2b40000 float:-90.0 double:NaN;
        r5.setRotate(r0);	 Catch:{ Exception -> 0x002d }
        goto L_0x0010;
    L_0x0066:
        r8 = move-exception;
        r8.printStackTrace();	 Catch:{ Exception -> 0x002d }
        r9 = 0;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: BitmapResizer.rotateBitmap(android.graphics.Bitmap, int):android.graphics.Bitmap");
    }

    private static Bitmap decodeFile(String selectedImagePath, int MAX_SIZE) {
        Options o = new Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, o);
        int scale = 1;
        int width_tmp = o.outWidth;
        int height_tmp = o.outHeight;
        while (Math.max(width_tmp, height_tmp) / 2 > MAX_SIZE) {
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        Bitmap b = BitmapFactory.decodeFile(selectedImagePath, o2);
        if (b != null) {
            Log.e("decoded file height", String.valueOf(b.getHeight()));
            Log.e("decoded file width", String.valueOf(b.getWidth()));
        }
        return b;
    }
}
