package com.app.camera.imagesavelib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v8.renderscript.Allocation;
import android.util.Log;
import android.widget.Toast;

import com.app.camera.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.List;


public class ImageUtility {
    static int SPLASH_TIME_OUT_AFTER_AD_LOADED = 0;
    public static int SPLASH_TIME_OUT_DEFAULT = 0;
    public static int SPLASH_TIME_OUT_LONG = 0;
    static int SPLASH_TIME_OUT_MAX = 0;
    public static int SPLASH_TIME_OUT_SHORT = 0;
    private static final String TAG = "SaveImage Utility";
    static final long interStitialPeriodMin = 6000;
    public static long lastTimeInterstitialShowed = 0;
    public static final int sizeDivider = 100000;

    /* renamed from: com.lyrebirdstudio.imagesavelib.ImageUtility.1 */
    class C05871 implements Runnable {
        private final /* synthetic */ String val$finalDirectory;
        private final /* synthetic */ Context val$mContext;

        C05871(Context context, String str) {
            this.val$mContext = context;
            this.val$finalDirectory = str;
        }

        public void run() {
            Toast msg = Toast.makeText(this.val$mContext, new StringBuilder(String.valueOf(this.val$mContext.getString(R.string.save_image_directory_error_message))).append(this.val$finalDirectory).append(".").toString(), Toast.LENGTH_SHORT);
            msg.setGravity(17, msg.getXOffset() / 2, msg.getYOffset() / 2);
            msg.show();
        }
    }


    public static boolean getAmazonMarket(Context context) {
        int AMAZON_MARKET = 0;
        try {
            AMAZON_MARKET = context.getPackageManager().getApplicationInfo(context.getPackageName(), Allocation.USAGE_SHARED).metaData.getInt("amazon_market");
            if (AMAZON_MARKET < 0) {
                AMAZON_MARKET = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AMAZON_MARKET == 1) {
            return true;
        }
        return false;
    }

    public static String getPrefferredDirectoryPathEx(Context mContext) {
        String directory = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append(mContext.getResources().getString(R.string.directory)).toString();
        String prefDir = PreferenceManager.getDefaultSharedPreferences(mContext).getString("save_image_directory_custom", null);
        if (prefDir != null) {
            return new StringBuilder(String.valueOf(prefDir)).append(File.separator).toString();
        }
        return directory;
    }

    public static String getPrefferredDirectoryPath(Context mContext, boolean showErrorMessage, boolean getPrefDirectoryNoMatterWhat, boolean isAppCamera) {
        String directory;
        if (isAppCamera) {
            directory = new StringBuilder(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())).append(File.separator).append(mContext.getResources().getString(R.string.directory)).toString();
        } else {
            directory = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append(mContext.getResources().getString(R.string.directory)).toString();
        }
        String prefDir = PreferenceManager.getDefaultSharedPreferences(mContext).getString("save_image_directory_custom", null);
        if (prefDir != null) {
            prefDir = new StringBuilder(String.valueOf(prefDir)).append(File.separator).toString();
            if (getPrefDirectoryNoMatterWhat) {
                return prefDir;
            }
            File dirFile = new File(prefDir);
            String finalDirectory = directory;
            if (dirFile.canRead() && dirFile.canWrite() && checkIfEACCES(prefDir)) {
                directory = prefDir;
            } else if (showErrorMessage) {
              //  ((Activity) mContext).runOnUiThread(new C05871(mContext, finalDirectory));
            }
            Log.e(TAG, "prefDir " + prefDir);
        }
        Log.e(TAG, "getPrefferredDirectoryPathEx " + getPrefferredDirectoryPathEx(mContext));
        Log.e(TAG, "getPrefferredDirectoryPath " + directory);
        return directory;
    }

    public static boolean checkIfEACCES(String dir) {
        IOException ex;
        Throwable th;
        boolean result = false;
        Writer writer = null;
        try {
            File f = new File(dir);
            String localPath = new StringBuilder(String.valueOf(dir)).append("mpp").toString();
            f.mkdirs();
            Writer writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localPath), "utf-8"));
            try {
                writer2.write("Something");
                result = true;
                writer2.close();
                Log.e(TAG, "f.delete() = " + new File(localPath).delete());
                try {
                    writer2.close();
                    writer = writer2;
                } catch (Exception e) {
                    writer = writer2;
                }
            } catch (IOException e2) {
                ex = e2;
                writer = writer2;
                try {
                    Log.e(TAG, ex.toString());
                    try {
                        writer.close();
                    } catch (Exception e3) {
                    }
                    return result;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        writer.close();
                    } catch (Exception e4) {
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                writer = writer2;
                writer.close();
            }
        } catch (IOException e5) {
            ex = e5;
            Log.e(TAG, ex.toString());
            return result;
        }
        return result;
    }

    private static long getFreeMemory() {
        return Runtime.getRuntime().maxMemory() - Debug.getNativeHeapAllocatedSize();
    }

    public static int maxSizeForDimension() {
        int maxSize = (int) Math.sqrt(((double) getFreeMemory()) / 30.0d);
        Log.e(TAG, "maxSize " + maxSize);
        return Math.min(maxSize, 1624);
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        if (width < height) {
            return (int) Math.ceil((double) (((float) height) / ((float) reqHeight)));
        }
        return (int) Math.ceil((double) (((float) width) / ((float) reqWidth)));
    }

    public static boolean shouldShowAds(Context mContext) {
        return !mContext.getPackageName().toLowerCase().contains("pro");
    }


    public static double getLeftSizeOfMemory() {
        double totalSize = Double.valueOf((double) Runtime.getRuntime().maxMemory()).doubleValue();
        double heapAllocated = Double.valueOf((double) Runtime.getRuntime().totalMemory()).doubleValue();
        double nativeAllocated = Double.valueOf((double) Debug.getNativeHeapAllocatedSize()).doubleValue();
        double usedMemory = heapAllocated - Double.valueOf((double) Runtime.getRuntime().freeMemory()).doubleValue();
        Log.e("heapAllocated", " " + Runtime.getRuntime().totalMemory());
        Log.e("nativeAllocated", " " + Debug.getNativeHeapAllocatedSize());
        Log.e("getNativeHeapFreeSize", " " + Debug.getNativeHeapFreeSize());
        Log.e("usedMemory", " " + usedMemory);
        Log.e("old free memory ", " " + ((totalSize - heapAllocated) - nativeAllocated));
        return (totalSize - usedMemory) - nativeAllocated;
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
        Bitmap graySourceBtm = localBitmap;
        if (graySourceBtm == null || VERSION.SDK_INT >= 13) {
            return graySourceBtm;
        }
        Bitmap temp = graySourceBtm.copy(Config.ARGB_8888, false);
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
        throw new UnsupportedOperationException("Method not decompiled: com.lyrebirdstudio.imagesavelib.ImageUtility.rotateBitmap(android.graphics.Bitmap, int):android.graphics.Bitmap");
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


    static {
        SPLASH_TIME_OUT_SHORT = 150;
        SPLASH_TIME_OUT_LONG = 800;
        SPLASH_TIME_OUT_MAX = 1300;
    }



    public static boolean isPackageInstalled(String packagename, Context context) {
        try {
            context.getPackageManager().getPackageInfo(packagename, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
