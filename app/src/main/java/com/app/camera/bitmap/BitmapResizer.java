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
import android.net.Uri;
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
    private static final String TAG = BitmapResizer.class.getName();

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
        return ((long) (((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() * AccessibilityNodeInfoCompat.ACTION_DISMISS)) - Debug.getNativeHeapAllocatedSize();
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
        Bitmap graySourceBtm = null;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                graySourceBtm = rotateImage(localBitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                graySourceBtm = rotateImage(localBitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                graySourceBtm = rotateImage(localBitmap, 270);
            default:
                graySourceBtm = localBitmap;
        }

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
    public static Bitmap rotateBitmap(Bitmap img, int degree) {
//        if (degrees != 0 && bitmap != null)
//        {
//            Matrix m = new Matrix();
//            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
//            Bitmap b2 = createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
//            if (b2 != null)
//            {
//                if (bitmap != b2)
//                {
//                    bitmap.recycle();
//                    bitmap = b2;
//                }
//            }
//        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
       // return bitmap;
    }

    public static Bitmap createBitmap(Bitmap bm, int i, int j, int width, int height, Matrix m, boolean c)
    {
        Bitmap b = null;
        try
        {
            b = Bitmap.createBitmap(bm, i, j, width, height, m, c);
           // Log.wtf(TAG, "Bitmap size in createBitmap : " + BitmapUtils.getBitmapSize(b));
        }
        catch (OutOfMemoryError e)
        {
            Log.wtf(TAG, "Out of Memory");

            System.gc();

            try
            {
                b = Bitmap.createBitmap(bm, i, j, width, height, m, c);
               // Log.wtf(TAG, "Bitmap size in createBitmap : " + BitmapUtils.getBitmapSize(b));
            }
            catch (OutOfMemoryError ex)
            {
                Log.wtf(TAG, "Out of Memory even after System.gc");
            }
            catch (Exception exc)
            {
                Log.e(TAG, "Exception in createBitmap : ", exc);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception in createBitmap : ", e);
        }
        return b;
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

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}
