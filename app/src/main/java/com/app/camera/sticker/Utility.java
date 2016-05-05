package com.app.camera.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Debug;
import android.support.v4.view.ViewCompat;
import android.util.Log;

import com.app.camera.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class Utility {
    private static final AtomicInteger sNextGeneratedId;
    public static final int[][] stickerResIdList;
    private static final float limitDivider = 60.0f;
    private static final float limitDividerGinger = 160.0f;
    private static final int upperLimitGinger = 800;
    private static String TAG = Utility.class.getName();

    static {
        sNextGeneratedId = new AtomicInteger(1);
        int[][] r0 = new int[4][];
        r0[0] = new int[]{R.drawable.couple_a,R.drawable.couple_b,R.drawable.couple_c,R.drawable.couple_d,R.drawable.couple_e,R.drawable.couple_f,R.drawable.couple_g,R.drawable.couple_h,R.drawable.couple_i,R.drawable.couple_j};
        r0[1] = new int[]{R.drawable.emoji_a,R.drawable.emoji_b,R.drawable.emoji_c,R.drawable.emoji_d,R.drawable.emoji_e,R.drawable.emoji_f,R.drawable.emoji_g,R.drawable.emoji_h,R.drawable.emoji_i};
        r0[2] = new int[]{R.drawable.love_one_a,R.drawable.love_one_b,R.drawable.love_one_c,R.drawable.love_one_d,R.drawable.love_one_e,R.drawable.love_one_f,R.drawable.love_one_g};
        r0[3] = new int[]{R.drawable.love_two_a,R.drawable.love_two_b,R.drawable.love_two_c,R.drawable.love_two_d,R.drawable.love_two_e,R.drawable.love_two_f,R.drawable.love_two_g,R.drawable.love_two_h};

//        r0[4] = new int[]{R.drawable.candy_01, R.drawable.candy_02, R.drawable.candy_03, R.drawable.candy_04, R.drawable.candy_05, R.drawable.candy_06, R.drawable.candy_07, R.drawable.candy_08, R.drawable.candy_09, R.drawable.candy_10, R.drawable.candy_11, R.drawable.candy_12, R.drawable.candy_13, R.drawable.candy_14, R.drawable.candy_15, R.drawable.candy_16, R.drawable.candy_17, R.drawable.candy_18, R.drawable.candy_19, R.drawable.candy_20};
//        r0[5] = new int[]{R.drawable.love_bird_01, R.drawable.love_bird_02, R.drawable.love_bird_03, R.drawable.love_bird_04, R.drawable.love_bird_05, R.drawable.love_bird_06, R.drawable.love_bird_07, R.drawable.love_bird_08, R.drawable.love_bird_09};
//        r0[6] = new int[]{R.drawable.monster_01, R.drawable.monster_02, R.drawable.monster_03, R.drawable.monster_04, R.drawable.monster_05, R.drawable.monster_06, R.drawable.monster_07, R.drawable.monster_08, R.drawable.monster_09, R.drawable.monster_10, R.drawable.monster_11, R.drawable.monster_12, R.drawable.monster_13, R.drawable.monster_14, R.drawable.monster_15, R.drawable.monster_16, R.drawable.monster_17, R.drawable.monster_18, R.drawable.monster_19, R.drawable.monster_20, R.drawable.monster_21, R.drawable.monster_22, R.drawable.monster_23, R.drawable.monster_24, R.drawable.monster_25, R.drawable.monster_26, R.drawable.monster_27, R.drawable.monster_28, R.drawable.monster_29, R.drawable.monster_30, R.drawable.monster_31, R.drawable.monster_32, R.drawable.monster_33, R.drawable.monster_34, R.drawable.monster_35, R.drawable.monster_36};
//        r0[7] = new int[]{R.drawable.comic_01, R.drawable.comic_02, R.drawable.comic_03, R.drawable.comic_04, R.drawable.comic_05, R.drawable.comic_06, R.drawable.comic_07, R.drawable.comic_08, R.drawable.comic_09, R.drawable.comic_10, R.drawable.comic_11, R.drawable.comic_12, R.drawable.comic_13, R.drawable.comic_14, R.drawable.comic_15, R.drawable.comic_16, R.drawable.comic_17, R.drawable.comic_18, R.drawable.comic_19, R.drawable.comic_20, R.drawable.comic_21, R.drawable.comic_22, R.drawable.comic_23, R.drawable.comic_24, R.drawable.comic_25, R.drawable.comic_26, R.drawable.comic_27, R.drawable.comic_28};
//        r0[8] = new int[]{R.drawable.flag_01, R.drawable.flag_02, R.drawable.flag_03, R.drawable.flag_04, R.drawable.flag_05, R.drawable.flag_06, R.drawable.flag_07, R.drawable.flag_08, R.drawable.flag_09, R.drawable.flag_10, R.drawable.flag_11, R.drawable.flag_12, R.drawable.flag_13, R.drawable.flag_14, R.drawable.flag_15, R.drawable.flag_16, R.drawable.flag_17, R.drawable.flag_18, R.drawable.flag_19, R.drawable.flag_20, R.drawable.flag_21, R.drawable.flag_22, R.drawable.flag_23, R.drawable.flag_24, R.drawable.flag_25, R.drawable.flag_26, R.drawable.flag_27, R.drawable.flag_28, R.drawable.flag_29, R.drawable.flag_30, R.drawable.flag_31, R.drawable.flag_32, R.drawable.flag_33, R.drawable.flag_34, R.drawable.flag_35, R.drawable.flag_36, R.drawable.flag_37, R.drawable.flag_38, R.drawable.flag_39, R.drawable.flag_40, R.drawable.flag_41, R.drawable.flag_42, R.drawable.flag_43, R.drawable.flag_44, R.drawable.flag_45, R.drawable.flag_46, R.drawable.flag_47, R.drawable.flag_48, R.drawable.flag_49, R.drawable.flag_50, R.drawable.flag_51, R.drawable.flag_52, R.drawable.flag_53, R.drawable.flag_54, R.drawable.flag_55, R.drawable.flag_56, R.drawable.flag_57, R.drawable.flag_58, R.drawable.flag_59, R.drawable.flag_60, R.drawable.flag_61, R.drawable.flag_62, R.drawable.flag_63, R.drawable.flag_64, R.drawable.flag_65, R.drawable.flag_66, R.drawable.flag_67, R.drawable.flag_68, R.drawable.flag_69, R.drawable.flag_70, R.drawable.flag_71, R.drawable.flag_72, R.drawable.flag_73, R.drawable.flag_74, R.drawable.flag_75};
//        r0[9] = new int[]{R.drawable.glasses_01, R.drawable.glasses_02, R.drawable.glasses_03, R.drawable.glasses_04, R.drawable.glasses_05, R.drawable.glasses_06, R.drawable.glasses_07, R.drawable.glasses_08, R.drawable.glasses_09, R.drawable.glasses_10, R.drawable.glasses_11, R.drawable.glasses_12, R.drawable.glasses_13, R.drawable.glasses_14, R.drawable.glasses_15, R.drawable.glasses_16, R.drawable.glasses_17, R.drawable.glasses_18, R.drawable.glasses_19, R.drawable.glasses_20, R.drawable.glasses_21, R.drawable.glasses_22, R.drawable.glasses_23, R.drawable.glasses_24, R.drawable.glasses_25};
//        r0[10] = new int[]{R.drawable.beard_01, R.drawable.beard_02, R.drawable.beard_03, R.drawable.beard_04, R.drawable.beard_05, R.drawable.beard_06, R.drawable.beard_07, R.drawable.beard_08, R.drawable.beard_09, R.drawable.beard_10, R.drawable.beard_11, R.drawable.beard_12, R.drawable.beard_13, R.drawable.beard_14, R.drawable.beard_15, R.drawable.beard_16, R.drawable.beard_17, R.drawable.beard_18, R.drawable.beard_19};
//        r0[11] = new int[]{R.drawable.hat_01, R.drawable.hat_02, R.drawable.hat_03, R.drawable.hat_04, R.drawable.hat_05, R.drawable.hat_06, R.drawable.hat_07, R.drawable.hat_02, R.drawable.hat_08, R.drawable.hat_09, R.drawable.hat_10, R.drawable.hat_11};
//        r0[12] = new int[]{R.drawable.wig_01, R.drawable.wig_02, R.drawable.wig_03, R.drawable.wig_04, R.drawable.wig_05, R.drawable.wig_06, R.drawable.wig_07, R.drawable.wig_08, R.drawable.wig_09, R.drawable.wig_10, R.drawable.wig_11};
//        r0[13] = new int[]{R.drawable.accessory_01, R.drawable.accessory_02, R.drawable.accessory_03, R.drawable.accessory_04, R.drawable.accessory_05, R.drawable.accessory_06, R.drawable.accessory_07, R.drawable.accessory_08, R.drawable.accessory_09, R.drawable.accessory_10, R.drawable.accessory_11};
        stickerResIdList = r0;
    }


    private static final int[] patternResIdList;

    private static final int[] patternResIdList3;

    private static final int[][] patternResIdList2;

    static {
        TAG = Utility.class.getSimpleName();
        patternResIdList = new int[]{R.drawable.no_pattern, R.drawable.color_picker, R.drawable.pattern_01, R.drawable.pattern_02, R.drawable.pattern_03, R.drawable.pattern_04, R.drawable.pattern_05, R.drawable.pattern_06, R.drawable.pattern_07, R.drawable.pattern_08, R.drawable.pattern_09, R.drawable.pattern_10, R.drawable.pattern_11, R.drawable.pattern_12, R.drawable.pattern_13, R.drawable.pattern_14, R.drawable.pattern_15, R.drawable.pattern_16, R.drawable.pattern_17, R.drawable.pattern_18, R.drawable.pattern_19, R.drawable.pattern_20, R.drawable.pattern_21, R.drawable.pattern_22, R.drawable.pattern_23, R.drawable.pattern_24, R.drawable.pattern_25, R.drawable.pattern_26, R.drawable.pattern_27, R.drawable.pattern_28, R.drawable.pattern_29, R.drawable.pattern_30, R.drawable.pattern_31, R.drawable.pattern_32, R.drawable.pattern_33, R.drawable.pattern_34, R.drawable.pattern_35, R.drawable.pattern_36, R.drawable.pattern_37, R.drawable.pattern_38, R.drawable.pattern_39, R.drawable.pattern_40, R.drawable.pattern_41, R.drawable.pattern_42, R.drawable.pattern_43, R.drawable.pattern_44, R.drawable.pattern_45, R.drawable.pattern_46, R.drawable.pattern_47, R.drawable.pattern_48, R.drawable.pattern_49, R.drawable.pattern_50, R.drawable.pattern_51, R.drawable.pattern_52, R.drawable.pattern_53, R.drawable.pattern_54, R.drawable.pattern_55, R.drawable.pattern_56, R.drawable.pattern_57};
        int[][] r0 = new int[12][];
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
    public static int generateViewId() {
        int result;
        int newValue;
        do {
            result = sNextGeneratedId.get();
            newValue = result + 1;
            if (newValue > ViewCompat.MEASURED_SIZE_MASK) {
                newValue = 1;
            }
        } while (!sNextGeneratedId.compareAndSet(result, newValue));
        return result;
    }

    public static int maxSizeForDimension(Context context, int count, float upperLimit) {
        float divider = limitDivider;
        if (Build.VERSION.SDK_INT <= 11) {
            upperLimit = 800.0f;
            divider = limitDividerGinger;
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
        if (Build.VERSION.SDK_INT <= 11) {
            divider = limitDividerGinger;
        }
        Log.e(TAG, "divider = " + divider);
        int maxSize = (int) Math.sqrt(getLeftSizeOfMemory() / ((double) divider));
        if (maxSize > 0) {
            return (int) Math.min((float) maxSize, upperLimit);
        }
        return (int) upperLimit;
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

    private static int getDefaultLimit(int count, float upperLimit) {
        int limit = (int) (((double) upperLimit) / Math.sqrt((double) count));
        Log.e(TAG, "limit = " + limit);
        return limit;
    }

    public static void decodeImageFileSize(String selectedImagePath) {
        try
        {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(selectedImagePath);

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(selectedImagePath);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            int dstWidth = 580;
            int dstHeight = 800;
            options.inSampleSize = Math.max(inWidth/dstWidth, inHeight/dstHeight);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
            // save image
            try
            {
                FileOutputStream out = new FileOutputStream(selectedImagePath);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            }
            catch (Exception e)
            {
                Log.e("Image", e.getMessage(), e);
            }
        }
        catch (IOException e)
        {
            Log.e("Image", e.getMessage(), e);
        }
    }
}
