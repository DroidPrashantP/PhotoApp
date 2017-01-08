package com.app.camera.collagelib;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.v8.renderscript.Allocation;
import android.util.Log;

import com.app.camera.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CopyOfUtilityBeforeScrapbook {
    public static final int ICON_SIZE = 160;
    private static final String TAG;
    private static final float limitDivider = 30.0f;
    private static final float limitDividerGinger = 160.0f;
    public static final int[] patternResIdList;
    public static final int[][] patternResIdList2;
    public static final int[] patternResIdList3;
    private static final int upperLimitGinger = 800;

    private static final int[][] r0;

    static {
        TAG = CopyOfUtilityBeforeScrapbook.class.getSimpleName();
        patternResIdList = new int[]{R.drawable.no_pattern, R.drawable.color_picker, R.drawable.pattern_01, R.drawable.pattern_02, R.drawable.pattern_03, R.drawable.pattern_04, R.drawable.pattern_05, R.drawable.pattern_06, R.drawable.pattern_07, R.drawable.pattern_08, R.drawable.pattern_09, R.drawable.pattern_10, R.drawable.pattern_11, R.drawable.pattern_12, R.drawable.pattern_13, R.drawable.pattern_14, R.drawable.pattern_15, R.drawable.pattern_16, R.drawable.pattern_17, R.drawable.pattern_18, R.drawable.pattern_19, R.drawable.pattern_20, R.drawable.pattern_21, R.drawable.pattern_22, R.drawable.pattern_23, R.drawable.pattern_24, R.drawable.pattern_25, R.drawable.pattern_26, R.drawable.pattern_27, R.drawable.pattern_28, R.drawable.pattern_29, R.drawable.pattern_30, R.drawable.pattern_31, R.drawable.pattern_32, R.drawable.pattern_33, R.drawable.pattern_34, R.drawable.pattern_35, R.drawable.pattern_36, R.drawable.pattern_37, R.drawable.pattern_38, R.drawable.pattern_39, R.drawable.pattern_40, R.drawable.pattern_41, R.drawable.pattern_42, R.drawable.pattern_43, R.drawable.pattern_44, R.drawable.pattern_45, R.drawable.pattern_46, R.drawable.pattern_47, R.drawable.pattern_48, R.drawable.pattern_49, R.drawable.pattern_50, R.drawable.pattern_51, R.drawable.pattern_52, R.drawable.pattern_53, R.drawable.pattern_54, R.drawable.pattern_55, R.drawable.pattern_56, R.drawable.pattern_57};
        r0 = new int[12][];
        r0[0] = new int[]{R.drawable.pattern_085, R.drawable.pattern_086, R.drawable.pattern_087, R.drawable.pattern_088, R.drawable.pattern_089, R.drawable.pattern_090, R.drawable.pattern_091, R.drawable.pattern_092, R.drawable.pattern_093, R.drawable.pattern_094, R.drawable.pattern_095, R.drawable.pattern_096};
        r0[1] = new int[]{R.drawable.pattern_097, R.drawable.pattern_098, R.drawable.pattern_099, R.drawable.pattern_100, R.drawable.pattern_101, R.drawable.pattern_102, R.drawable.pattern_103, R.drawable.pattern_104, R.drawable.pattern_105, R.drawable.pattern_106, R.drawable.pattern_107, R.drawable.pattern_108};
        r0[2] = new int[]{R.drawable.pattern_061, R.drawable.pattern_062, R.drawable.pattern_063, R.drawable.pattern_064, R.drawable.pattern_065, R.drawable.pattern_066, R.drawable.pattern_067, R.drawable.pattern_068, R.drawable.pattern_069, R.drawable.pattern_070, R.drawable.pattern_071, R.drawable.pattern_072};
        r0[3] = new int[]{R.drawable.pattern_073, R.drawable.pattern_074, R.drawable.pattern_075, R.drawable.pattern_076, R.drawable.pattern_077, R.drawable.pattern_078, R.drawable.pattern_079, R.drawable.pattern_080, R.drawable.pattern_081, R.drawable.pattern_082, R.drawable.pattern_083, R.drawable.pattern_084};
        r0[4] = new int[]{R.drawable.pattern_109, R.drawable.pattern_110, R.drawable.pattern_111, R.drawable.pattern_112, R.drawable.pattern_113, R.drawable.pattern_114, R.drawable.pattern_115, R.drawable.pattern_116, R.drawable.pattern_117, R.drawable.pattern_118, R.drawable.pattern_119, R.drawable.pattern_120};
        r0[5] = new int[]{R.drawable.pattern_121, R.drawable.pattern_122, R.drawable.pattern_123, R.drawable.pattern_124, R.drawable.pattern_125, R.drawable.pattern_126, R.drawable.pattern_127, R.drawable.pattern_128, R.drawable.pattern_129, R.drawable.pattern_130, R.drawable.pattern_131};
        r0[6] = new int[]{R.drawable.pattern_132, R.drawable.pattern_133, R.drawable.pattern_134, R.drawable.pattern_135, R.drawable.pattern_136, R.drawable.pattern_137, R.drawable.pattern_138, R.drawable.pattern_139, R.drawable.pattern_140, R.drawable.pattern_141, R.drawable.pattern_142};
        r0[7] = new int[]{R.drawable.pattern_49, R.drawable.pattern_50, R.drawable.pattern_51, R.drawable.pattern_52, R.drawable.pattern_53, R.drawable.pattern_54, R.drawable.pattern_55, R.drawable.pattern_56, R.drawable.pattern_57, R.drawable.pattern_058, R.drawable.pattern_059, R.drawable.pattern_060};
        r0[8] = new int[]{R.drawable.pattern_01, R.drawable.pattern_02, R.drawable.pattern_03, R.drawable.pattern_04, R.drawable.pattern_05, R.drawable.pattern_06, R.drawable.pattern_07, R.drawable.pattern_08, R.drawable.pattern_09, R.drawable.pattern_10, R.drawable.pattern_11, R.drawable.pattern_12};
        r0[9] = new int[]{R.drawable.pattern_13, R.drawable.pattern_14, R.drawable.pattern_15, R.drawable.pattern_16, R.drawable.pattern_17, R.drawable.pattern_18, R.drawable.pattern_19, R.drawable.pattern_20, R.drawable.pattern_21, R.drawable.pattern_22, R.drawable.pattern_23, R.drawable.pattern_24};
        r0[10] = new int[]{R.drawable.pattern_25, R.drawable.pattern_26, R.drawable.pattern_27, R.drawable.pattern_28, R.drawable.pattern_29, R.drawable.pattern_30, R.drawable.pattern_31, R.drawable.pattern_32, R.drawable.pattern_33, R.drawable.pattern_34, R.drawable.pattern_35, R.drawable.pattern_36};
        r0[11] = new int[]{R.drawable.pattern_37, R.drawable.pattern_38, R.drawable.pattern_39, R.drawable.pattern_40, R.drawable.pattern_41, R.drawable.pattern_42, R.drawable.pattern_43, R.drawable.pattern_44, R.drawable.pattern_45, R.drawable.pattern_46, R.drawable.pattern_47, R.drawable.pattern_48};
        patternResIdList2 = r0;
        patternResIdList3 = new int[]{R.drawable.no_pattern, R.drawable.color_picker, R.drawable.pattern_icon_08, R.drawable.pattern_icon_09, R.drawable.pattern_icon_06, R.drawable.pattern_icon_07, R.drawable.pattern_icon_10, R.drawable.pattern_icon_11, R.drawable.pattern_icon_12, R.drawable.pattern_icon_05, R.drawable.pattern_icon_01, R.drawable.pattern_icon_02, R.drawable.pattern_icon_03, R.drawable.pattern_icon_04};
    }

    public static void createIconBitmap(List<ShapeLayout> shapeList) {
        for (int i = 0; i < shapeList.size(); i++) {
            Bitmap drawBitmap = Bitmap.createBitmap(ICON_SIZE, ICON_SIZE, Config.ARGB_8888);
            Canvas canvas = new Canvas(drawBitmap);
            canvas.drawColor(-1);
            int sc = canvas.saveLayer(-160.0f, -160.0f, limitDividerGinger, limitDividerGinger, null, Canvas.ALL_SAVE_FLAG);
            for (int j = 0; j < ((ShapeLayout) shapeList.get(i)).shapeArr.length; j++) {
                ((ShapeLayout) shapeList.get(i)).shapeArr[j].initIcon(ICON_SIZE, ICON_SIZE);
                boolean drawPorterClear = false;
                if (j == ((ShapeLayout) shapeList.get(i)).getClearIndex()) {
                    drawPorterClear = true;
                }
                ((ShapeLayout) shapeList.get(i)).shapeArr[j].drawShapeIcon(canvas, ICON_SIZE, ICON_SIZE, sc, drawPorterClear);
            }
            canvas.restoreToCount(sc);
            File myDir = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/collage_icons").toString());
            myDir.mkdirs();
            try {
                FileOutputStream out = new FileOutputStream(new File(myDir, "collage_" + ((ShapeLayout) shapeList.get(i)).shapeArr.length + "_" + i + ".png"));
                drawBitmap.compress(CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isPackageProEx(Context context) {
        return context.getPackageName().toLowerCase().contains("pro");
    }

    public static Bitmap getBitmapFromId(Context context, long imageID) {
        Bitmap bitmap = null;
        try {
            bitmap = Media.getBitmap(context.getContentResolver(), Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Long.toString(imageID)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getScaledBitmapFromId(Context context, long imageID) {
        Uri uri = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Long.toString(imageID));
        Options options = new Options();
        options.inSampleSize = 4;
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
    }

    public static Bitmap getScaledBitmapFromId(Context context, long imageID, int orientation, int requiredSize) {
        Uri uri = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Long.toString(imageID));
        Options boundsOption = new Options();
        boundsOption.inJustDecodeBounds = true;
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileDescriptor == null) {
            return null;
        }
        BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, boundsOption);
        Options options = new Options();
        options.inSampleSize = calculateInSampleSize(boundsOption, requiredSize, requiredSize);
        Bitmap actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
        if (actuallyUsableBitmap == null) {
            return null;
        }
        Bitmap btm = rotateImage(actuallyUsableBitmap, orientation);
        if (btm == null) {
            return actuallyUsableBitmap;
        }
        if (actuallyUsableBitmap == btm) {
            return btm;
        }
        actuallyUsableBitmap.recycle();
        return btm;
    }

    public static Bitmap decodeFile(String path, int requiredSize) {
        try {
            File f = new File(path);
            Options boundsOption = new Options();
            boundsOption.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, boundsOption);
            Options o2 = new Options();
            o2.inSampleSize = calculateInSampleSize(boundsOption, requiredSize, requiredSize);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rotateBitmap(bitmap, exif.getAttributeInt("Orientation", 0));
        } catch (FileNotFoundException e2) {
            return null;
        }
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
        throw new UnsupportedOperationException("Method not decompiled: com.lyrebirdstudio.collagelib.CopyOfUtilityBeforeScrapbook.rotateBitmap(android.graphics.Bitmap, int):android.graphics.Bitmap");
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

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (true) {
                if (halfHeight / inSampleSize <= reqHeight && halfWidth / inSampleSize <= reqWidth) {
                    break;
                }
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static final void launchFacebook(Context mContext) {
//        String urlFb = mContext.getString(R.string.facebook_like_url);
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.setData(Uri.parse(urlFb));
//        if (mContext.getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() == 0) {
//            mContext.startActivity(new Intent(mContext, FacebookLike.class));
//        } else {
//            mContext.startActivity(intent);
//        }
//        Toast.makeText(mContext, mContext.getString(R.string.facebook_like_us), 1).show();
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

    public static double getLeftSizeOfMemoryMb() {
        return (Double.valueOf((double) (Runtime.getRuntime().maxMemory() / 1048576)).doubleValue() - Double.valueOf((double) (Runtime.getRuntime().totalMemory() / 1048576)).doubleValue()) - (Double.valueOf((double) Debug.getNativeHeapAllocatedSize()).doubleValue() / Double.valueOf(1048576.0d).doubleValue());
    }

    public static double getLeftSizeOfMemoryEx(Context context) {
        double totalSize = Double.valueOf((double) Runtime.getRuntime().maxMemory()).doubleValue();
        double heapAllocated = Double.valueOf((double) Runtime.getRuntime().totalMemory()).doubleValue();
        return (totalSize - heapAllocated) - Double.valueOf((double) Debug.getNativeHeapAllocatedSize()).doubleValue();
    }

    public static double getLeftSizeOfMemory() {
        double totalSize = Double.valueOf((double) Runtime.getRuntime().maxMemory()).doubleValue();
        double heapAllocated = Double.valueOf((double) Runtime.getRuntime().totalMemory()).doubleValue();
        return (totalSize - (heapAllocated - Double.valueOf((double) Runtime.getRuntime().freeMemory()).doubleValue())) - Double.valueOf((double) Debug.getNativeHeapAllocatedSize()).doubleValue();
    }

    public static void logFreeMemory(Context context) {
        Log.e(TAG, "free memory own method = " + (getLeftSizeOfMemory() / 1048576.0d));
    }

    public static int maxSizeForDimension(Context context, int count, float upperLimit) {
        float divider = limitDivider;
        if (VERSION.SDK_INT <= 11) {
            divider = limitDividerGinger;
            upperLimit = 800.0f;
        }
        Log.e(TAG, "divider = " + divider);
        int maxSize = (int) Math.sqrt(getLeftSizeOfMemory() / ((double) (((float) count) * divider)));
        if (maxSize <= 0) {
            maxSize = getDefaultLimit(count, upperLimit);
        }
        return Math.min(maxSize, getDefaultLimit(count, upperLimit));
    }

    public static int maxSizeForSave(Context context, float upperLimit) {
        float divider = limitDivider;
        if (VERSION.SDK_INT <= 11) {
            divider = limitDividerGinger;
        }
        Log.e(TAG, "divider = " + divider);
        int maxSize = (int) Math.sqrt(getLeftSizeOfMemory() / ((double) divider));
        if (maxSize > 0) {
            return (int) Math.min((float) maxSize, upperLimit);
        }
        return (int) upperLimit;
    }

    private static int getDefaultLimit(int count, float upperLimit) {
        int limit = (int) (((double) upperLimit) / Math.sqrt((double) count));
        Log.e(TAG, "limit = " + limit);
        return limit;
    }
}
