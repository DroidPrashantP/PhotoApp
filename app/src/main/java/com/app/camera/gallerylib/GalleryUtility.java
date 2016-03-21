package com.app.camera.gallerylib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Debug;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import java.text.DecimalFormat;

public class GalleryUtility {
    public static Bitmap getThumbnailBitmap(Context context, long imageId, int orientation) {
        Bitmap bitmap = Thumbnails.getThumbnail(context.getContentResolver(), imageId, 1, null);
        if (bitmap == null) {
            return null;
        }
        Bitmap btm = rotateImage(bitmap, orientation);
        if (btm == null) {
            return bitmap;
        }
        if (btm == bitmap) {
            return btm;
        }
        bitmap.recycle();
        return btm;
    }

    public static void logHeap() {
        Double allocated = Double.valueOf(Double.valueOf((double) Debug.getNativeHeapAllocatedSize()).doubleValue() / Double.valueOf(1048576.0d).doubleValue());
        Double available = Double.valueOf(Double.valueOf((double) Debug.getNativeHeapSize()).doubleValue() / 1048576.0d);
        Double free = Double.valueOf(Double.valueOf((double) Debug.getNativeHeapFreeSize()).doubleValue() / 1048576.0d);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        Log.d("tag", "debug. =================================");
        Log.d("tag", "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
        Log.d("tag", "debug.memory: allocated: " + df.format(Double.valueOf((double) (Runtime.getRuntime().totalMemory() / 1048576))) + "MB of " + df.format(Double.valueOf((double) (Runtime.getRuntime().maxMemory() / 1048576))) + "MB (" + df.format(Double.valueOf((double) (Runtime.getRuntime().freeMemory() / 1048576))) + "MB free)");
    }

    private static Bitmap rotateImage(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        if (orientation == 90) {
            matrix.postRotate(90.0f);
        } else if (orientation == 180) {
            matrix.postRotate(180.0f);
        } else if (orientation == 270) {
            matrix.postRotate(270.0f);
        }
        if (orientation == 0) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
