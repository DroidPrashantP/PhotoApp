package com.app.paddycameraeditior.mirror;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.paddycameraeditior.Domain.MirrorMode;
import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.activities.SaveImageActivity;
import com.app.paddycameraeditior.adapters.MyRecyclerViewAdapter;
import com.app.paddycameraeditior.analytics.AnalyticBasic;
import com.app.paddycameraeditior.bitmap.BitmapResizer;
import com.app.paddycameraeditior.canvastext.ApplyTextInterface;
import com.app.paddycameraeditior.canvastext.CustomRelativeLayout;
import com.app.paddycameraeditior.canvastext.SingleTap;
import com.app.paddycameraeditior.canvastext.TextData;
import com.app.paddycameraeditior.collagelib.Utility;
import com.app.paddycameraeditior.fragments.EffectFragment;
import com.app.paddycameraeditior.fragments.FontFragment;
import com.app.paddycameraeditior.sticker.StickerData;
import com.app.paddycameraeditior.sticker.StickerGalleryFragment;
import com.app.paddycameraeditior.sticker.StickerGalleryListener;
import com.app.paddycameraeditior.sticker.StickerView;
import com.app.paddycameraeditior.utils.LibUtility;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MirrorNewActivity extends AppCompatActivity {

    public static final int INDEX_MIRROR = 0;
    public static final int INDEX_MIRROR_3D = 1;
    public static final int INDEX_MIRROR_ADJUSTMENT = 5;
    public static final int INDEX_MIRROR_EFFECT = 3;
    public static final int INDEX_MIRROR_FRAME = 4;
    public static final int INDEX_MIRROR_INVISIBLE_VIEW = 7;
    public static final int INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX = 4;
    public static final int INDEX_MIRROR_RATIO = 2;
    static final int SAVE_IMAGE_ID = 1348;
    public static final int TAB_SIZE = 6;
    private static final String TAG = "MirrorNewActivity";
    int D3_BUTTON_SIZE;
    int MIRROR_BUTTON_SIZE;
    int RATIO_BUTTON_SIZE;
    Activity activity;
    AdView adWhirlLayout;
    CustomRelativeLayout canvasText;
    Context context;
    int currentSelectedTabIndex;
    int currentStickerIndex;
    Button[] d3ButtonArray;
    private int[] d3resList;
    EffectFragment effectFragment;
    Bitmap filterBitmap;
    FontFragment.FontChoosedListener fontChoosedListener;
    FontFragment fontFragment;
    StickerGalleryFragment galleryFragment;
    int initialYPos;
    InterstitialAd interstitial;
    RelativeLayout mainLayout;
    Matrix matrix;
    Matrix matrixMirror1;
    Matrix matrixMirror2;
    Matrix matrixMirror3;
    Matrix matrixMirror4;
    Button[] mirrorButtonArray;
    MirrorView mirrorView;
    int mode;
    float mulX;
    float mulY;
    Button[] ratioButtonArray;
    Bitmap removeBitmap;
    AlertDialog saveImageAlert;
    Bitmap scaleBitmap;
    int screenHeightPixels;
    int screenWidthPixels;
    boolean showText;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    Bitmap sourceBitmap;
    StickerGalleryListener stickerGalleryListener;
    ArrayList<StickerView> stickerList;
    FrameLayout stickerViewContainer;
    StickerView.StickerViewSelectedListener stickerViewSelectedListner;
    View[] tabButtonList;
    ArrayList<TextData> textDataList;
    ViewFlipper viewFlipper;


    class C06009 implements DialogInterface.OnClickListener {
        C06009() {
        }

        public void onClick(DialogInterface dialog, int id) {
            SaveImageTask saveImageTask = new SaveImageTask();
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(3);
            saveImageTask.execute(objArr);
        }
    }

    class MirrorView extends View {
        final Matrix f510I;
        int currentModeIndex;
        Bitmap d3Bitmap;
        boolean d3Mode;
        int defaultColor;
        RectF destRect1;
        RectF destRect1X;
        RectF destRect1Y;
        RectF destRect2;
        RectF destRect2X;
        RectF destRect2Y;
        RectF destRect3;
        RectF destRect4;
        float distance;
        boolean drawSavedImage;
        RectF dstRectPaper1;
        RectF dstRectPaper2;
        RectF dstRectPaper3;
        RectF dstRectPaper4;
        Bitmap frameBitmap;
        Paint framePaint;
        int height;
        boolean isTouchStartedLeft;
        boolean isTouchStartedTop;
        boolean isVerticle;
        Matrix m1;
        Matrix m2;
        Matrix m3;
        MirrorMode[] mirrorModeList;
        MirrorMode modeX;
        MirrorMode modeX10;
        MirrorMode modeX11;
        MirrorMode modeX12;
        MirrorMode modeX13;
        MirrorMode modeX14;
        MirrorMode modeX15;
        MirrorMode modeX16;
        MirrorMode modeX17;
        MirrorMode modeX18;
        MirrorMode modeX19;
        MirrorMode modeX2;
        MirrorMode modeX20;
        MirrorMode modeX3;
        MirrorMode modeX4;
        MirrorMode modeX5;
        MirrorMode modeX6;
        MirrorMode modeX7;
        MirrorMode modeX8;
        MirrorMode modeX9;
        float oldX;
        float oldY;
        RectF srcRect1;
        RectF srcRect2;
        RectF srcRect3;
        RectF srcRectPaper;
        int tMode1;
        int tMode2;
        int tMode3;
        Matrix textMatrix;
        Paint textRectPaint;
        RectF totalArea1;
        RectF totalArea2;
        RectF totalArea3;
        int width;

        public MirrorView(Context context, int screenWidth, int screenHeight) {
            super(context);
            this.f510I = new Matrix();
            this.framePaint = new Paint();
            this.isVerticle = false;
            this.defaultColor = R.color.bg;
            this.mirrorModeList = new MirrorMode[20];
            this.currentModeIndex = MirrorNewActivity.INDEX_MIRROR;
            this.drawSavedImage = false;
            this.d3Mode = false;
            this.textMatrix = new Matrix();
            this.textRectPaint = new Paint(MirrorNewActivity.INDEX_MIRROR_3D);
            this.m1 = new Matrix();
            this.m2 = new Matrix();
            this.m3 = new Matrix();
            this.width = MirrorNewActivity.this.sourceBitmap.getWidth();
            this.height = MirrorNewActivity.this.sourceBitmap.getHeight();
            int widthPixels = screenWidth;
            int heightPixels = screenHeight;
            createMatrix(widthPixels, heightPixels);
            createRectX(widthPixels, heightPixels);
            createRectY(widthPixels, heightPixels);
            createRectXY(widthPixels, heightPixels);
            createModes();
            this.framePaint.setAntiAlias(true);
            this.framePaint.setFilterBitmap(true);
            this.framePaint.setDither(true);
            this.textRectPaint.setColor(getResources().getColor(R.color.bg));
        }

        private void reset(int widthPixels, int heightPixels, boolean invalidate) {
            createMatrix(widthPixels, heightPixels);
            createRectX(widthPixels, heightPixels);
            createRectY(widthPixels, heightPixels);
            createRectXY(widthPixels, heightPixels);
            createModes();
            if (invalidate) {
                postInvalidate();
            }
        }

        private String saveBitmap(boolean saveToFile, int widthPixel, int heightPixel) {
            int i;
            float minDimen = (float) Math.min(widthPixel, heightPixel);
            float upperScale = (float) Utility.maxSizeForSave();
            float scale = upperScale / minDimen;
            Log.e(MirrorNewActivity.TAG, "upperScale" + upperScale);
            Log.e(MirrorNewActivity.TAG, "scale" + scale);
            if (MirrorNewActivity.this.mulY > MirrorNewActivity.this.mulX) {
                float f = MirrorNewActivity.this.mulX;
                float r0 = 1.0f;
                scale = (r0 * scale) / MirrorNewActivity.this.mulY;
            }
            if (scale <= 0.0f) {
                scale = 1.0f;
            }
            Log.e(MirrorNewActivity.TAG, "scale" + scale);
            int wP = Math.round(((float) widthPixel) * scale);
            int wH = Math.round(((float) heightPixel) * scale);
            RectF srcRect = this.mirrorModeList[this.currentModeIndex].getSrcRect();
            reset(wP, wH, false);
            int btmWidth = Math.round(MirrorNewActivity.this.mirrorView.getCurrentMirrorMode().rectTotalArea.width());
            int btmHeight = Math.round(MirrorNewActivity.this.mirrorView.getCurrentMirrorMode().rectTotalArea.height());
            if (btmWidth % MirrorNewActivity.INDEX_MIRROR_RATIO == MirrorNewActivity.INDEX_MIRROR_3D) {
                btmWidth--;
            }
            if (btmHeight % MirrorNewActivity.INDEX_MIRROR_RATIO == MirrorNewActivity.INDEX_MIRROR_3D) {
                btmHeight--;
            }
            Bitmap savedBitmap = Bitmap.createBitmap(btmWidth, btmHeight, Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(savedBitmap);
            Matrix matrix = new Matrix();
            matrix.reset();
            Log.e(MirrorNewActivity.TAG, "btmWidth " + btmWidth);
            Log.e(MirrorNewActivity.TAG, "btmHeight " + btmHeight);
            matrix.postTranslate(((float) (-(wP - btmWidth))) / 2.0f, ((float) (-(wH - btmHeight))) / 2.0f);
            MirrorMode saveMode = this.mirrorModeList[this.currentModeIndex];
            saveMode.setSrcRect(srcRect);
            if (MirrorNewActivity.this.filterBitmap == null) {
                drawMode(bitmapCanvas, MirrorNewActivity.this.sourceBitmap, saveMode, matrix);
            } else {
                drawMode(bitmapCanvas, MirrorNewActivity.this.filterBitmap, saveMode, matrix);
            }
            if (this.d3Mode && this.d3Bitmap != null) {
                if (!this.d3Bitmap.isRecycled()) {
                    bitmapCanvas.setMatrix(matrix);
                    bitmapCanvas.drawBitmap(this.d3Bitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
                }
            }
            Matrix mat;
            if (MirrorNewActivity.this.textDataList != null) {
                i = MirrorNewActivity.INDEX_MIRROR;
                while (true) {
                    if (i >= MirrorNewActivity.this.textDataList.size()) {
                        break;
                    }
                    mat = new Matrix();
                    mat.set(((TextData) MirrorNewActivity.this.textDataList.get(i)).imageSaveMatrix);
                    mat.postScale(scale, scale);
                    mat.postTranslate(((float) (-(wP - btmWidth))) / 2.0f, ((float) (-(wH - btmHeight))) / 2.0f);
                    bitmapCanvas.setMatrix(mat);
                    bitmapCanvas.drawText(((TextData) MirrorNewActivity.this.textDataList.get(i)).message, ((TextData) MirrorNewActivity.this.textDataList.get(i)).xPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).yPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).textPaint);
                    i += MirrorNewActivity.INDEX_MIRROR_3D;
                }
            }
            i = MirrorNewActivity.INDEX_MIRROR;
            while (true) {
                if (i >= MirrorNewActivity.this.stickerViewContainer.getChildCount()) {
                    break;
                }
                mat = new Matrix();
                StickerView view = (StickerView) MirrorNewActivity.this.stickerViewContainer.getChildAt(i);
                StickerData data = view.getStickerData();
                mat.set(data.getCanvasMatrix());
                mat.postScale(scale, scale);
                mat.postTranslate(((float) (-(wP - btmWidth))) / 2.0f, ((float) (-(wH - btmHeight))) / 2.0f);
                bitmapCanvas.setMatrix(mat);
                if (view.stickerBitmap != null) {
                    if (!view.stickerBitmap.isRecycled()) {
                        bitmapCanvas.drawBitmap(view.stickerBitmap, data.xPos, data.yPos, view.paint);
                    }
                }
                i += MirrorNewActivity.INDEX_MIRROR_3D;
            }
            if (this.frameBitmap != null) {
                if (!this.frameBitmap.isRecycled()) {
                    bitmapCanvas.setMatrix(matrix);
                    bitmapCanvas.drawBitmap(this.frameBitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
                }
            }
            String resultPath = null;
            if (saveToFile) {
                String twitterUploadFile = String.valueOf(System.currentTimeMillis());
                resultPath = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append(MirrorNewActivity.this.getString(R.string.directory)).append(twitterUploadFile).append(".jpg").toString();
                new File(resultPath).getParentFile().mkdirs();
                try {
                    FileOutputStream out = new FileOutputStream(resultPath);
                    savedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            savedBitmap.recycle();
            reset(widthPixel, heightPixel, false);
            this.mirrorModeList[this.currentModeIndex].setSrcRect(srcRect);
            return resultPath;
        }

        private void setCurrentMode(int index) {
            this.currentModeIndex = index;
        }

        public MirrorMode getCurrentMirrorMode() {
            return this.mirrorModeList[this.currentModeIndex];
        }

        private void createModes() {
            this.modeX = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRect3, this.destRect1, this.destRect1, this.destRect3, this.destRect3, MirrorNewActivity.this.matrixMirror1, this.f510I, MirrorNewActivity.this.matrixMirror1, this.tMode3, this.totalArea3);
            this.modeX2 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRect3, this.destRect1, this.destRect4, this.destRect1, this.destRect4, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.f510I, this.tMode3, this.totalArea3);
            this.modeX3 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRect3, this.destRect3, this.destRect2, this.destRect3, this.destRect2, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.f510I, this.tMode3, this.totalArea3);
            this.modeX8 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRect3, this.destRect1, this.destRect1, this.destRect1, this.destRect1, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, this.tMode3, this.totalArea3);
            int m9TouchMode = MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX;
            if (this.tMode3 == 0) {
                m9TouchMode = MirrorNewActivity.INDEX_MIRROR;
            }
            this.modeX9 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRect3, this.destRect2, this.destRect2, this.destRect2, this.destRect2, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, m9TouchMode, this.totalArea3);
            int m10TouchMode = MirrorNewActivity.INDEX_MIRROR_EFFECT;
            if (this.tMode3 == MirrorNewActivity.INDEX_MIRROR_3D) {
                m10TouchMode = MirrorNewActivity.INDEX_MIRROR_3D;
            }
            this.modeX10 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRect3, this.destRect3, this.destRect3, this.destRect3, this.destRect3, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, m10TouchMode, this.totalArea3);
            int m11TouchMode = MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX;
            if (this.tMode3 == 0) {
                m11TouchMode = MirrorNewActivity.INDEX_MIRROR_EFFECT;
            }
            this.modeX11 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRect3, this.destRect4, this.destRect4, this.destRect4, this.destRect4, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, m11TouchMode, this.totalArea3);
            this.modeX4 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect1, this.destRect1X, this.destRect1X, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            int m5TouchMode = MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX;
            if (this.tMode1 == 0) {
                m5TouchMode = MirrorNewActivity.INDEX_MIRROR;
            } else if (this.tMode1 == MirrorNewActivity.INDEX_MIRROR_ADJUSTMENT) {
                m5TouchMode = MirrorNewActivity.INDEX_MIRROR_ADJUSTMENT;
            }
            this.modeX5 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect1, this.destRect2X, this.destRect2X, MirrorNewActivity.this.matrixMirror1, m5TouchMode, this.totalArea1);
            this.modeX6 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect2, this.destRect1Y, this.destRect1Y, MirrorNewActivity.this.matrixMirror2, this.tMode2, this.totalArea2);
            int m7TouchMode = MirrorNewActivity.INDEX_MIRROR_EFFECT;
            if (this.tMode2 == MirrorNewActivity.INDEX_MIRROR_3D) {
                m7TouchMode = MirrorNewActivity.INDEX_MIRROR_3D;
            } else if (this.tMode2 == MirrorNewActivity.TAB_SIZE) {
                m7TouchMode = MirrorNewActivity.TAB_SIZE;
            }
            this.modeX7 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect2, this.destRect2Y, this.destRect2Y, MirrorNewActivity.this.matrixMirror2, m7TouchMode, this.totalArea2);
            this.modeX12 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect1, this.destRect1X, this.destRect2X, MirrorNewActivity.this.matrixMirror4, this.tMode1, this.totalArea1);
            this.modeX13 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect2, this.destRect1Y, this.destRect2Y, MirrorNewActivity.this.matrixMirror4, this.tMode2, this.totalArea2);
            this.modeX14 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect1, this.destRect1X, this.destRect1X, MirrorNewActivity.this.matrixMirror3, this.tMode1, this.totalArea1);
            this.modeX15 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_RATIO, this.srcRect2, this.destRect1Y, this.destRect1Y, MirrorNewActivity.this.matrixMirror3, this.tMode2, this.totalArea2);
            this.modeX16 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRectPaper, this.dstRectPaper1, this.dstRectPaper2, this.dstRectPaper3, this.dstRectPaper4, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.f510I, this.tMode1, this.totalArea1);
            this.modeX17 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRectPaper, this.dstRectPaper1, this.dstRectPaper3, this.dstRectPaper3, this.dstRectPaper1, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.modeX18 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRectPaper, this.dstRectPaper2, this.dstRectPaper4, this.dstRectPaper2, this.dstRectPaper4, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.modeX19 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRectPaper, this.dstRectPaper1, this.dstRectPaper2, this.dstRectPaper2, this.dstRectPaper1, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.modeX20 = new MirrorMode(MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, this.srcRectPaper, this.dstRectPaper4, this.dstRectPaper3, this.dstRectPaper3, this.dstRectPaper4, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.mirrorModeList[MirrorNewActivity.INDEX_MIRROR] = this.modeX4;
            this.mirrorModeList[MirrorNewActivity.INDEX_MIRROR_3D] = this.modeX5;
            this.mirrorModeList[MirrorNewActivity.INDEX_MIRROR_RATIO] = this.modeX6;
            this.mirrorModeList[MirrorNewActivity.INDEX_MIRROR_EFFECT] = this.modeX7;
            this.mirrorModeList[MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX] = this.modeX8;
            this.mirrorModeList[MirrorNewActivity.INDEX_MIRROR_ADJUSTMENT] = this.modeX9;
            this.mirrorModeList[MirrorNewActivity.TAB_SIZE] = this.modeX10;
            this.mirrorModeList[MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW] = this.modeX11;
            this.mirrorModeList[8] = this.modeX12;
            this.mirrorModeList[9] = this.modeX13;
            this.mirrorModeList[10] = this.modeX14;
            this.mirrorModeList[11] = this.modeX15;
            this.mirrorModeList[12] = this.modeX;
            this.mirrorModeList[13] = this.modeX2;
            this.mirrorModeList[14] = this.modeX3;
            this.mirrorModeList[15] = this.modeX7;
            this.mirrorModeList[16] = this.modeX17;
            this.mirrorModeList[17] = this.modeX18;
            this.mirrorModeList[18] = this.modeX19;
            this.mirrorModeList[19] = this.modeX20;
        }

        public Bitmap getBitmap() {
            setDrawingCacheEnabled(true);
            buildDrawingCache();
            Bitmap bmp = Bitmap.createBitmap(getDrawingCache());
            setDrawingCacheEnabled(false);
            return bmp;
        }

        public void setFrame(int index) {
            if (!(this.frameBitmap == null || this.frameBitmap.isRecycled())) {
                this.frameBitmap.recycle();
                this.frameBitmap = null;
            }
            if (index == 0) {
                postInvalidate();
                return;
            }
            this.frameBitmap = BitmapFactory.decodeResource(getResources(), LibUtility.borderRes[index]);
            postInvalidate();
        }

        private void createMatrix(int widthPixels, int heightPixels) {
            this.f510I.reset();
            MirrorNewActivity.this.matrixMirror1.reset();
            MirrorNewActivity.this.matrixMirror1.postScale(-1.0f, 1.0f);
            MirrorNewActivity.this.matrixMirror1.postTranslate((float) widthPixels, 0.0f);
            MirrorNewActivity.this.matrixMirror2.reset();
            MirrorNewActivity.this.matrixMirror2.postScale(1.0f, -1.0f);
            MirrorNewActivity.this.matrixMirror2.postTranslate(0.0f, (float) heightPixels);
            MirrorNewActivity.this.matrixMirror3.reset();
            MirrorNewActivity.this.matrixMirror3.postScale(-1.0f, -1.0f);
            MirrorNewActivity.this.matrixMirror3.postTranslate((float) widthPixels, (float) heightPixels);
        }

        private void createRectX(int widthPixels, int heightPixels) {
            float destH = ((float) widthPixels) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX);
            float destW = ((float) widthPixels) / 2.0f;
            float destX = 0.0f;
            float destY = (float) MirrorNewActivity.this.initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            destY = ((float) MirrorNewActivity.this.initialYPos) + ((((float) heightPixels) - destH) / 2.0f);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.destRect1X = new RectF(destX, destY, destW + destX, destH + destY);
            float destXX = destX + destW;
            this.destRect2X = new RectF(destXX, destY, destW + destXX, destH + destY);
            this.totalArea1 = new RectF(destX, destY, destW + destXX, destH + destY);
            this.tMode1 = MirrorNewActivity.INDEX_MIRROR_3D;
            if (MirrorNewActivity.this.mulX * ((float) this.height) <= (MirrorNewActivity.this.mulY * 2.0f) * ((float) this.width)) {
                srcX = (((float) this.width) - (((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height)) / 2.0f)) / 2.0f;
                srcX2 = srcX + (((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height)) / 2.0f);
            } else {
                srcY = (((float) this.height) - (((float) (this.width * MirrorNewActivity.INDEX_MIRROR_RATIO)) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX))) / 2.0f;
                srcY2 = srcY + (((float) (this.width * MirrorNewActivity.INDEX_MIRROR_RATIO)) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX));
                this.tMode1 = MirrorNewActivity.INDEX_MIRROR_ADJUSTMENT;
            }
            this.srcRect1 = new RectF(srcX, srcY, srcX2, srcY2);
            this.srcRectPaper = new RectF(srcX, srcY, ((srcX2 - srcX) / 2.0f) + srcX, srcY2);
            float destWPapar = destW / 2.0f;
            this.dstRectPaper1 = new RectF(destX, destY, destWPapar + destX, destH + destY);
            float dextXP = destX + destWPapar;
            this.dstRectPaper2 = new RectF(dextXP, destY, destWPapar + dextXP, destH + destY);
            dextXP += destWPapar;
            this.dstRectPaper3 = new RectF(dextXP, destY, destWPapar + dextXP, destH + destY);
            dextXP += destWPapar;
            this.dstRectPaper4 = new RectF(dextXP, destY, destWPapar + dextXP, destH + destY);
        }

        private void createRectY(int widthPixels, int heightPixels) {
            float destH = (((float) widthPixels) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX)) / 2.0f;
            float destW = (float) widthPixels;
            float destX = 0.0f;
            float destY = (float) MirrorNewActivity.this.initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            destY = ((float) MirrorNewActivity.this.initialYPos) + ((((float) heightPixels) - (2.0f * destH)) / 2.0f);
            this.destRect1Y = new RectF(destX, destY, destW + destX, destH + destY);
            float destYY = destY + destH;
            this.destRect2Y = new RectF(destX, destYY, destW + destX, destH + destYY);
            this.totalArea2 = new RectF(destX, destY, destW + destX, destH + destYY);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.tMode2 = MirrorNewActivity.INDEX_MIRROR;
            if ((MirrorNewActivity.this.mulX * 2.0f) * ((float) this.height) > MirrorNewActivity.this.mulY * ((float) this.width)) {
                srcY = (((float) this.height) - (((MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX) * ((float) this.width)) / 2.0f)) / 2.0f;
                srcY2 = srcY + (((MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX) * ((float) this.width)) / 2.0f);
            } else {
                srcX = (((float) this.width) - (((float) (this.height * MirrorNewActivity.INDEX_MIRROR_RATIO)) * (MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY))) / 2.0f;
                srcX2 = srcX + (((float) (this.height * MirrorNewActivity.INDEX_MIRROR_RATIO)) * (MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY));
                this.tMode2 = MirrorNewActivity.TAB_SIZE;
            }
            this.srcRect2 = new RectF(srcX, srcY, srcX2, srcY2);
        }

        private void createRectXY(int widthPixels, int heightPixels) {
            float destH = (((float) widthPixels) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX)) / 2.0f;
            float destW = ((float) widthPixels) / 2.0f;
            float destX = 0.0f;
            float destY = (float) MirrorNewActivity.this.initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            destY = ((float) MirrorNewActivity.this.initialYPos) + ((((float) heightPixels) - (2.0f * destH)) / 2.0f);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.destRect1 = new RectF(destX, destY, destW + destX, destH + destY);
            float destX2 = destX + destW;
            this.destRect2 = new RectF(destX2, destY, destW + destX2, destH + destY);
            float destY2 = destY + destH;
            this.destRect3 = new RectF(destX, destY2, destW + destX, destH + destY2);
            this.destRect4 = new RectF(destX2, destY2, destW + destX2, destH + destY2);
            this.totalArea3 = new RectF(destX, destY, destW + destX2, destH + destY2);
            if (MirrorNewActivity.this.mulX * ((float) this.height) <= MirrorNewActivity.this.mulY * ((float) this.width)) {
                srcX = (((float) this.width) - ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height))) / 2.0f;
                srcX2 = srcX + ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height));
                this.tMode3 = MirrorNewActivity.INDEX_MIRROR_3D;
            } else {
                srcY = (((float) this.height) - (((float) this.width) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX))) / 2.0f;
                srcY2 = srcY + (((float) this.width) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX));
                this.tMode3 = MirrorNewActivity.INDEX_MIRROR;
            }
            this.srcRect3 = new RectF(srcX, srcY, srcX2, srcY2);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawColor(this.defaultColor);
            if (MirrorNewActivity.this.filterBitmap == null) {
                drawMode(canvas, MirrorNewActivity.this.sourceBitmap, this.mirrorModeList[this.currentModeIndex], this.f510I);
            } else {
                drawMode(canvas, MirrorNewActivity.this.filterBitmap, this.mirrorModeList[this.currentModeIndex], this.f510I);
            }
            if (!(!this.d3Mode || this.d3Bitmap == null || this.d3Bitmap.isRecycled())) {
                canvas.setMatrix(this.f510I);
                canvas.drawBitmap(this.d3Bitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
            }
            if (MirrorNewActivity.this.showText) {
                for (int i = MirrorNewActivity.INDEX_MIRROR; i < MirrorNewActivity.this.textDataList.size(); i += MirrorNewActivity.INDEX_MIRROR_3D) {
                    this.textMatrix.set(((TextData) MirrorNewActivity.this.textDataList.get(i)).imageSaveMatrix);
                    this.textMatrix.postConcat(this.f510I);
                    canvas.setMatrix(this.textMatrix);
                    canvas.drawText(((TextData) MirrorNewActivity.this.textDataList.get(i)).message, ((TextData) MirrorNewActivity.this.textDataList.get(i)).xPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).yPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).textPaint);
                    canvas.setMatrix(this.f510I);
                    canvas.drawRect(0.0f, 0.0f, this.mirrorModeList[this.currentModeIndex].rectTotalArea.left, (float) MirrorNewActivity.this.screenHeightPixels, this.textRectPaint);
                    canvas.drawRect(0.0f, 0.0f, (float) MirrorNewActivity.this.screenWidthPixels, this.mirrorModeList[this.currentModeIndex].rectTotalArea.top, this.textRectPaint);
                    canvas.drawRect(this.mirrorModeList[this.currentModeIndex].rectTotalArea.right, 0.0f, (float) MirrorNewActivity.this.screenWidthPixels, (float) MirrorNewActivity.this.screenHeightPixels, this.textRectPaint);
                    canvas.drawRect(0.0f, this.mirrorModeList[this.currentModeIndex].rectTotalArea.bottom, (float) MirrorNewActivity.this.screenWidthPixels, (float) MirrorNewActivity.this.screenHeightPixels, this.textRectPaint);
                }
            }
            if (!(this.frameBitmap == null || this.frameBitmap.isRecycled())) {
                canvas.setMatrix(this.f510I);
                canvas.drawBitmap(this.frameBitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
            }
            super.onDraw(canvas);
        }

        private void drawMode(Canvas canvas, Bitmap bitmap, MirrorMode mirrorMode, Matrix matrix) {
            canvas.setMatrix(matrix);
            canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect1, this.framePaint);
            this.m1.set(mirrorMode.matrix1);
            this.m1.postConcat(matrix);
            canvas.setMatrix(this.m1);
            if (!(bitmap == null || bitmap.isRecycled())) {
                canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect2, this.framePaint);
            }
            if (mirrorMode.count == MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                this.m2.set(mirrorMode.matrix2);
                this.m2.postConcat(matrix);
                canvas.setMatrix(this.m2);
                if (!(bitmap == null || bitmap.isRecycled())) {
                    canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect3, this.framePaint);
                }
                this.m3.set(mirrorMode.matrix3);
                this.m3.postConcat(matrix);
                canvas.setMatrix(this.m3);
                if (bitmap != null && !bitmap.isRecycled()) {
                    canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect4, this.framePaint);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MirrorNewActivity.INDEX_MIRROR /*0*/:
                    if (x < ((float) (MirrorNewActivity.this.screenWidthPixels / MirrorNewActivity.INDEX_MIRROR_RATIO))) {
                        this.isTouchStartedLeft = true;
                    } else {
                        this.isTouchStartedLeft = false;
                    }
                    if (y < ((float) (MirrorNewActivity.this.screenHeightPixels / MirrorNewActivity.INDEX_MIRROR_RATIO))) {
                        this.isTouchStartedTop = true;
                    } else {
                        this.isTouchStartedTop = false;
                    }
                    this.oldX = x;
                    this.oldY = y;
                    break;
                case MirrorNewActivity.INDEX_MIRROR_RATIO /*2*/:
                    moveGrid(this.mirrorModeList[this.currentModeIndex].getSrcRect(), x - this.oldX, y - this.oldY);
                    this.mirrorModeList[this.currentModeIndex].updateBitmapSrc();
                    this.oldX = x;
                    this.oldY = y;
                    break;
            }
            postInvalidate();
            return true;
        }

        void moveGrid(RectF srcRect, float x, float y) {
            if (this.mirrorModeList[this.currentModeIndex].touchMode == MirrorNewActivity.INDEX_MIRROR_3D || this.mirrorModeList[this.currentModeIndex].touchMode == MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX || this.mirrorModeList[this.currentModeIndex].touchMode == MirrorNewActivity.TAB_SIZE) {
                if (this.mirrorModeList[this.currentModeIndex].touchMode == MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                    x *= -1.0f;
                }
                if (this.isTouchStartedLeft && this.mirrorModeList[this.currentModeIndex].touchMode != MirrorNewActivity.TAB_SIZE) {
                    x *= -1.0f;
                }
                if (srcRect.left + x < 0.0f) {
                    x = -srcRect.left;
                }
                if (srcRect.right + x >= ((float) this.width)) {
                    x = ((float) this.width) - srcRect.right;
                }
                srcRect.left += x;
                srcRect.right += x;
            } else if (this.mirrorModeList[this.currentModeIndex].touchMode == 0 || this.mirrorModeList[this.currentModeIndex].touchMode == MirrorNewActivity.INDEX_MIRROR_EFFECT || this.mirrorModeList[this.currentModeIndex].touchMode == MirrorNewActivity.INDEX_MIRROR_ADJUSTMENT) {
                if (this.mirrorModeList[this.currentModeIndex].touchMode == MirrorNewActivity.INDEX_MIRROR_EFFECT) {
                    y *= -1.0f;
                }
                if (this.isTouchStartedTop && this.mirrorModeList[this.currentModeIndex].touchMode != MirrorNewActivity.INDEX_MIRROR_ADJUSTMENT) {
                    y *= -1.0f;
                }
                if (srcRect.top + y < 0.0f) {
                    y = -srcRect.top;
                }
                if (srcRect.bottom + y >= ((float) this.height)) {
                    y = ((float) this.height) - srcRect.bottom;
                }
                srcRect.top += y;
                srcRect.bottom += y;
            }
        }
    }

    final class MyMediaScannerConnectionClient implements MediaScannerConnection.MediaScannerConnectionClient {
        private MediaScannerConnection mConn;
        private String mFilename;
        private String mMimetype;

        public MyMediaScannerConnectionClient(Context ctx, File file, String mimetype) {
            this.mFilename = file.getAbsolutePath();
            this.mConn = new MediaScannerConnection(ctx, this);
            this.mConn.connect();
        }

        public void onMediaScannerConnected() {
            this.mConn.scanFile(this.mFilename, this.mMimetype);
        }

        public void onScanCompleted(String path, Uri uri) {
            this.mConn.disconnect();
        }
    }

    class C09731 implements FontFragment.FontChoosedListener {
        C09731() {
        }

        public void onOk(TextData textData) {
            MirrorNewActivity.this.canvasText.addTextView(textData);
            MirrorNewActivity.this.getSupportFragmentManager().beginTransaction().remove(MirrorNewActivity.this.fontFragment).commit();
            Log.e(MirrorNewActivity.TAG, "onOK called");
        }
    }

    class C09742 implements StickerView.StickerViewSelectedListener {
        C09742() {
        }

        public void setSelectedView(StickerView stickerView) {
            stickerView.bringToFront();
            stickerView.bringToFront();
            if (Build.VERSION.SDK_INT < 19) {
                MirrorNewActivity.this.stickerViewContainer.requestLayout();
            }
        }

        public void onTouchUp(StickerData data) {
        }
    }

    class C09753 implements SingleTap {
        C09753() {
        }

        public void onSingleTap(TextData textData) {
            MirrorNewActivity.this.fontFragment = new FontFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable("text_data", textData);
            MirrorNewActivity.this.fontFragment.setArguments(arguments);
            MirrorNewActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.text_view_fragment_container, MirrorNewActivity.this.fontFragment, "FONT_FRAGMENT").commit();
            Log.e(MirrorNewActivity.TAG, "replace fragment");
            MirrorNewActivity.this.fontFragment.setFontChoosedListener(MirrorNewActivity.this.fontChoosedListener);
        }
    }

    class C09764 implements ApplyTextInterface {
        C09764() {
        }

        public void onOk(ArrayList<TextData> tdList) {
            Iterator it = tdList.iterator();
            while (it.hasNext()) {
                ((TextData) it.next()).setImageSaveMatrix(MirrorNewActivity.this.mirrorView.f510I);
            }
            MirrorNewActivity.this.textDataList = tdList;
            MirrorNewActivity.this.showText = true;
            if (MirrorNewActivity.this.mainLayout == null) {
                MirrorNewActivity.this.mainLayout = (RelativeLayout) MirrorNewActivity.this.findViewById(R.id.layout_mirror_activity);
            }
            MirrorNewActivity.this.mainLayout.removeView(MirrorNewActivity.this.canvasText);
            MirrorNewActivity.this.mirrorView.postInvalidate();
        }

        public void onCancel() {
            MirrorNewActivity.this.showText = true;
            MirrorNewActivity.this.mainLayout.removeView(MirrorNewActivity.this.canvasText);
            MirrorNewActivity.this.mirrorView.postInvalidate();
        }
    }

    class C09786 implements EffectFragment.BitmapReady {
        C09786() {
        }

        public void onBitmapReady(Bitmap bitmap) {
            MirrorNewActivity.this.filterBitmap = bitmap;
            MirrorNewActivity.this.mirrorView.postInvalidate();
        }
    }

    class C09797 implements LibUtility.BuyProVersion {
        C09797() {
        }

        public void proVersionCalled() {
        }
    }

    class C09808 implements MyRecyclerViewAdapter.RecyclerAdapterIndexChangedListener {
        C09808() {
        }

        public void onIndexChanged(int position) {
            MirrorNewActivity.this.mirrorView.setFrame(position);
        }
    }

    private class SaveImageTask extends AsyncTask<Object, Object, Object> {
        ProgressDialog progressDialog;
        String resultPath;
        int saveMode;

        private SaveImageTask() {
            this.saveMode = MirrorNewActivity.INDEX_MIRROR;
            this.resultPath = null;
        }

        protected Object doInBackground(Object... arg0) {
            if (arg0 != null) {
                this.saveMode = ((Integer) arg0[MirrorNewActivity.INDEX_MIRROR]).intValue();
            }
            this.resultPath = MirrorNewActivity.this.mirrorView.saveBitmap(true, MirrorNewActivity.this.screenWidthPixels, MirrorNewActivity.this.screenHeightPixels);
            return null;
        }

        protected void onPreExecute() {
            this.progressDialog = new ProgressDialog(MirrorNewActivity.this.context);
            this.progressDialog.setMessage("Saving image ...");
            this.progressDialog.show();
        }

        protected void onPostExecute(Object result) {
            boolean z = true;
            try {
                if (this.progressDialog != null && this.progressDialog.isShowing()) {
                    this.progressDialog.cancel();
                }
            } catch (Exception e) {
            }
            Toast msg;
            if (this.saveMode == 0 || this.saveMode == MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                super.onPostExecute(result);
                msg = Toast.makeText(MirrorNewActivity.this.context, "Image has been saved to the /SD" + MirrorNewActivity.this.getString(R.string.directory) + " folder.", MirrorNewActivity.INDEX_MIRROR_3D);
                msg.setGravity(17, msg.getXOffset() / MirrorNewActivity.INDEX_MIRROR_RATIO, msg.getYOffset() / MirrorNewActivity.INDEX_MIRROR_RATIO);
                msg.show();
                MediaScannerConnection.MediaScannerConnectionClient client = new MyMediaScannerConnectionClient(MirrorNewActivity.this.getApplicationContext(), new File(this.resultPath), null);
                if (this.saveMode == MirrorNewActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                    MirrorNewActivity.this.finish();
                }
            } else if (this.saveMode == MirrorNewActivity.INDEX_MIRROR_3D) {
                super.onPostExecute(result);
                try {
                    Intent picMessageIntent = new Intent("android.intent.action.SEND");
                    picMessageIntent.setFlags(268435456);
                    picMessageIntent.setType("image/jpeg");
                    if (this.resultPath != null) {
                        picMessageIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(this.resultPath)));
                        MirrorNewActivity.this.startActivity(picMessageIntent);
                    }
                } catch (Exception e2) {
                    msg = Toast.makeText(MirrorNewActivity.this.context, "There is no email app installed on your device to handle this request.", MirrorNewActivity.INDEX_MIRROR_3D);
                    msg.setGravity(17, msg.getXOffset() / MirrorNewActivity.INDEX_MIRROR_RATIO, msg.getYOffset() / MirrorNewActivity.INDEX_MIRROR_RATIO);
                    msg.show();
                }
            } else if (this.saveMode == MirrorNewActivity.INDEX_MIRROR_RATIO) {
//                fbIntent = new Intent(MirrorNewActivity.this.context, FacebookActivity.class);
//                if (this.resultPath != null) {
//                    fbIntent.putExtra("imagePath", this.resultPath);
//                    MirrorNewActivity.this.startActivity(fbIntent);
//                }
            } else if (this.saveMode == MirrorNewActivity.INDEX_MIRROR_EFFECT) {
                Intent fbIntent = new Intent(MirrorNewActivity.this.context, SaveImageActivity.class);
                if (this.resultPath != null) {
                    boolean z2;
                    fbIntent.putExtra("imagePath", this.resultPath);
                  // fbIntent.putExtra("urlFacebookLike", MirrorNewActivity.this.getString(R.string.facebook_like_url));
                  //  fbIntent.putExtra("proVersionUrl", MirrorNewActivity.this.getString(R.string.pro_package));
                    fbIntent.putExtra("folder", MirrorNewActivity.this.getString(R.string.directory));
                  //  fbIntent.putExtra("twitter_message", new StringBuilder(String.valueOf(MirrorNewActivity.this.getString(R.string.hashtag_twitter))).append(" ").toString());
                    String str = "should_show_ads";
//                    if (CommonLibrary.isAppPro(MirrorNewActivity.this.context)) {
//                        z2 = false;
//                    } else {
//                        z2 = true;
//                    }
//                    fbIntent.putExtra(str, z2);
//                    String str2 = "show_inter_ad";
//                    if (CommonLibrary.isAppPro(MirrorNewActivity.this.context) || !MirrorNewActivity.this.getResources().getBoolean(R.bool.showInterstitialAds)) {
//                        z = false;
//                    }
//                    fbIntent.putExtra(str2, z);
                    MirrorNewActivity.this.startActivityForResult(fbIntent, MirrorNewActivity.SAVE_IMAGE_ID);
                }
            }
            MyMediaScannerConnectionClient myMediaScannerConnectionClient = new MyMediaScannerConnectionClient(MirrorNewActivity.this.getApplicationContext(), new File(this.resultPath), null);
        }
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    public MirrorNewActivity() {
        this.context = this;
        this.matrix = new Matrix();
        this.matrixMirror1 = new Matrix();
        this.matrixMirror2 = new Matrix();
        this.matrixMirror3 = new Matrix();
        this.matrixMirror4 = new Matrix();
        this.activity = this;
        this.initialYPos = INDEX_MIRROR;
        this.textDataList = new ArrayList();
        this.showText = false;
        this.fontChoosedListener = new C09731();
        this.d3resList = new int[]{R.drawable.mirror_3d_14, R.drawable.mirror_3d_14, R.drawable.mirror_3d_10, R.drawable.mirror_3d_10, R.drawable.mirror_3d_11, R.drawable.mirror_3d_11, R.drawable.mirror_3d_4, R.drawable.mirror_3d_4, R.drawable.mirror_3d_3, R.drawable.mirror_3d_3, R.drawable.mirror_3d_1, R.drawable.mirror_3d_1, R.drawable.mirror_3d_6, R.drawable.mirror_3d_6, R.drawable.mirror_3d_13, R.drawable.mirror_3d_13, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16};
        this.currentStickerIndex = INDEX_MIRROR;
        this.stickerViewSelectedListner = new C09742();
        this.D3_BUTTON_SIZE = 24;
        this.MIRROR_BUTTON_SIZE = 15;
        this.RATIO_BUTTON_SIZE = 11;
        this.currentSelectedTabIndex = -1;
        this.mode = INDEX_MIRROR_ADJUSTMENT;
        this.mulX = 16.0f;
        this.mulY = 16.0f;
    }

    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        Bundle extras = getIntent().getExtras();
        this.sourceBitmap = BitmapResizer.decodeBitmapFromFile(extras.getString("selectedImagePath"), extras.getInt("MAX_SIZE"));
        if (this.sourceBitmap == null) {
            Toast msg = Toast.makeText(this.context, "Could not load the photo, please use another GALLERY app!", INDEX_MIRROR_3D);
            msg.setGravity(17, msg.getXOffset() / INDEX_MIRROR_RATIO, msg.getYOffset() / INDEX_MIRROR_RATIO);
            msg.show();
            finish();
            return;
        }
        int width;
        int height;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.screenHeightPixels = metrics.heightPixels;
        this.screenWidthPixels = metrics.widthPixels;
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        Log.e(TAG, "width " + width);
        Log.e(TAG, "screenWidthPixels " + this.screenWidthPixels);
        Log.e(TAG, "height " + height);
        Log.e(TAG, "heightPixels " + this.screenHeightPixels);
        if (this.screenWidthPixels <= 0) {
            this.screenWidthPixels = width;
        }
        if (this.screenHeightPixels <= 0) {
            this.screenHeightPixels = height;
        }
        this.mirrorView = new MirrorView(this.context, this.screenWidthPixels, this.screenHeightPixels);
        setContentView(R.layout.activity_mirror_new);
        this.mainLayout = (RelativeLayout) findViewById(R.id.layout_mirror_activity);
        this.mainLayout.addView(this.mirrorView);
        this.stickerList = new ArrayList();
        this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
        this.stickerViewContainer.bringToFront();
        this.viewFlipper = (ViewFlipper) findViewById(R.id.mirror_view_flipper);
        this.viewFlipper.bringToFront();
        ((ViewGroup) findViewById(R.id.mirror_footer)).bringToFront();
        this.slideLeftIn = AnimationUtils.loadAnimation(this.activity, R.anim.slide_in_left);
        this.slideLeftOut = AnimationUtils.loadAnimation(this.activity, R.anim.slide_out_left);
        this.slideRightIn = AnimationUtils.loadAnimation(this.activity, R.anim.slide_in_right);
        this.slideRightOut = AnimationUtils.loadAnimation(this.activity, R.anim.slide_out_right);

        findViewById(R.id.mirror_header).bringToFront();
     //   addEffectFragment();
        Utility.logFreeMemory(this);
        setSelectedTab(0);
        findViewById(R.id.sticker_grid_fragment_container).bringToFront();
        if (savedInstanceState != null) {
            FragmentManager fm = getSupportFragmentManager();
            this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
            if (this.galleryFragment != null) {
                fm.beginTransaction().hide(this.galleryFragment).commit();
                this.galleryFragment.setGalleryListener(createGalleryListener());
            }
        }

        try {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

        AnalyticBasic.hitGoogleAnalytics(this, MirrorNewActivity.class.getName());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SAVE_IMAGE_ID && !CommonLibrary.isAppPro(this.context) && this.context.getResources().getBoolean(R.bool.showInterstitialAds)) {
//            ImageUtility.displayInterStitialWithSplashScreen(this.interstitial, this.activity, ImageUtility.SPLASH_TIME_OUT_DEFAULT, "Mirror_Image_Edit_After_Save");
//        }
    }

    public void onResume() {
        super.onResume();
        Utility.logFreeMemory(this);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.sourceBitmap != null) {
            this.sourceBitmap.recycle();
        }
        if (this.filterBitmap != null) {
            this.filterBitmap.recycle();
        }
        if (this.adWhirlLayout != null) {
            this.adWhirlLayout.removeAllViews();
            this.adWhirlLayout.destroy();
        }
        if (this.stickerViewContainer == null) {
            this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
        }
        if (this.stickerViewContainer != null) {
            for (int i = INDEX_MIRROR; i < this.stickerViewContainer.getChildCount(); i += INDEX_MIRROR_3D) {
                ((StickerView) this.stickerViewContainer.getChildAt(i)).onDestroy();
            }
            this.stickerViewContainer.removeAllViews();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("show_text", this.showText);
        savedInstanceState.putSerializable("text_data", this.textDataList);
        if (this.fontFragment != null && this.fontFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(this.fontFragment).commit();
        }
        if (this.stickerViewContainer != null && this.stickerViewContainer.getChildCount() > 0) {
            int size = this.stickerViewContainer.getChildCount();
            StickerData[] stickerDataArray = new StickerData[size];
            for (int i = INDEX_MIRROR; i < size; i += INDEX_MIRROR_3D) {
                stickerDataArray[i] = ((StickerView) this.stickerViewContainer.getChildAt(i)).getStickerData();
            }
            savedInstanceState.putParcelableArray("sticker_data_array", stickerDataArray);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.showText = savedInstanceState.getBoolean("show_text");
        this.textDataList = (ArrayList) savedInstanceState.getSerializable("text_data");
        if (this.textDataList == null) {
            this.textDataList = new ArrayList();
        }
        StickerData[] stickerDataArray = StickerData.toStickerData(savedInstanceState.getParcelableArray("sticker_data_array"));
        if (stickerDataArray != null) {
            Bitmap removeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.remove_text);
            Bitmap scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scale_text);
            for (int i = INDEX_MIRROR; i < stickerDataArray.length; i += INDEX_MIRROR_3D) {
                StickerView stickerView = new StickerView(this.context, BitmapFactory.decodeResource(getResources(), stickerDataArray[i].getResId()), stickerDataArray[i], removeBitmap, scaleBitmap, stickerDataArray[i].getResId());
                stickerView.setStickerViewSelectedListener(this.stickerViewSelectedListner);
                if (this.stickerViewContainer == null) {
                    this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
                }
                this.stickerViewContainer.addView(stickerView);
            }
        }
    }


    public void showCloseDialog() {
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to save image?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        this.mirrorView.drawSavedImage = false;
        if (id == R.id.button_save_mirror_image) {
            SaveImageTask saveImageTask = new SaveImageTask();
            Object[] objArr = new Object[INDEX_MIRROR_3D];
            objArr[INDEX_MIRROR] = Integer.valueOf(INDEX_MIRROR_EFFECT);
            saveImageTask.execute(objArr);
        } else if (id == R.id.closeScreen) {
            showCloseDialog();
        } else if (id == R.id.button_mirror) {
            setSelectedTab(INDEX_MIRROR);
        } else if (id == R.id.button_mirror_frame) {
            setSelectedTab(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
        } else if (id == R.id.button_mirror_ratio) {
            setSelectedTab(INDEX_MIRROR_RATIO);
        } else if (id == R.id.button_mirror_effect) {
            setSelectedTab(INDEX_MIRROR_EFFECT);
        } else if (id == R.id.button_mirror_adj) {
            setSelectedTab(INDEX_MIRROR_ADJUSTMENT);
        } else if (id == R.id.button_mirror_3d) {
            setSelectedTab(INDEX_MIRROR_3D);
        } else if (id == R.id.button_3d_1) {
            set3dMode(INDEX_MIRROR);
        } else if (id == R.id.button_3d_2) {
            set3dMode(INDEX_MIRROR_3D);
        } else if (id == R.id.button_3d_3) {
            set3dMode(INDEX_MIRROR_RATIO);
        } else if (id == R.id.button_3d_4) {
            set3dMode(INDEX_MIRROR_EFFECT);
        } else if (id == R.id.button_3d_5) {
            set3dMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
        } else if (id == R.id.button_3d_6) {
            set3dMode(INDEX_MIRROR_ADJUSTMENT);
        } else if (id == R.id.button_3d_7) {
            set3dMode(TAB_SIZE);
        } else if (id == R.id.button_3d_8) {
            set3dMode(INDEX_MIRROR_INVISIBLE_VIEW);
        } else if (id == R.id.button_3d_9) {
            set3dMode(8);
        } else if (id == R.id.button_3d_10) {
            set3dMode(9);
        } else if (id == R.id.button_3d_11) {
            set3dMode(10);
        } else if (id == R.id.button_3d_12) {
            set3dMode(11);
        } else if (id == R.id.button_3d_13) {
            set3dMode(12);
        } else if (id == R.id.button_3d_14) {
            set3dMode(13);
        } else if (id == R.id.button_3d_15) {
            set3dMode(14);
        } else if (id == R.id.button_3d_16) {
            set3dMode(15);
        } else if (id == R.id.button_3d_17) {
            set3dMode(16);
        } else if (id == R.id.button_3d_18) {
            set3dMode(17);
        } else if (id == R.id.button_3d_19) {
            set3dMode(18);
        } else if (id == R.id.button_3d_20) {
            set3dMode(19);
        } else if (id == R.id.button_3d_21) {
            set3dMode(20);
        } else if (id == R.id.button_3d_22) {
            set3dMode(21);
        } else if (id == R.id.button_3d_23) {
            set3dMode(22);
        } else if (id == R.id.button_3d_24) {
            set3dMode(23);
        } else if (id == R.id.button11) {
            this.mulX = 1.0f;
            this.mulY = 1.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR);
        } else if (id == R.id.button21) {
            this.mulX = 2.0f;
            this.mulY = 1.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_3D);
        } else if (id == R.id.button12) {
            this.mulX = 1.0f;
            this.mulY = 2.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_RATIO);
        } else if (id == R.id.button32) {
            this.mulX = 3.0f;
            this.mulY = 2.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_EFFECT);
        } else if (id == R.id.button23) {
            this.mulX = 2.0f;
            this.mulY = 3.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
        } else if (id == R.id.button43) {
            this.mulX = 4.0f;
            this.mulY = 3.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_ADJUSTMENT);
        } else if (id == R.id.button34) {
            this.mulX = 3.0f;
            this.mulY = 4.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(TAB_SIZE);
        } else if (id == R.id.button45) {
            this.mulX = 4.0f;
            this.mulY = 5.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_INVISIBLE_VIEW);
        } else if (id == R.id.button57) {
            this.mulX = 5.0f;
            this.mulY = 7.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(8);
        } else if (id == R.id.button169) {
            this.mulX = 16.0f;
            this.mulY = 9.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(9);
        } else if (id == R.id.button916) {
            this.mulX = 9.0f;
            this.mulY = 16.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(10);
        } else if (id == R.id.button_m1) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR);
        } else if (id == R.id.button_m2) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR_3D);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_3D);
        } else if (id == R.id.button_m3) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR_RATIO);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_RATIO);
        } else if (id == R.id.button_m4) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR_EFFECT);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_EFFECT);
        } else if (id == R.id.button_m5) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
        } else if (id == R.id.button_m6) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR_ADJUSTMENT);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_ADJUSTMENT);
        } else if (id == R.id.button_m7) {
            this.mirrorView.setCurrentMode(TAB_SIZE);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(TAB_SIZE);
        } else if (id == R.id.button_m8) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR_INVISIBLE_VIEW);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_INVISIBLE_VIEW);
        } else if (id == R.id.button_m9) {
            this.mirrorView.setCurrentMode(8);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(8);
        } else if (id == R.id.button_m10) {
            this.mirrorView.setCurrentMode(9);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(9);
        } else if (id == R.id.button_m11) {
            this.mirrorView.setCurrentMode(10);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(10);
        } else if (id == R.id.button_m12) {
            this.mirrorView.setCurrentMode(11);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(11);
        } else if (id == R.id.button_m13) {
            this.mirrorView.setCurrentMode(12);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(12);
        } else if (id == R.id.button_m14) {
            this.mirrorView.setCurrentMode(13);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(13);
        } else if (id == R.id.button_m15) {
            this.mirrorView.setCurrentMode(14);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(14);
        } else if (id == R.id.button_mirror_text) {
            addCanvasTextView();
            clearViewFlipper();
        } else if (id == R.id.button_mirror_sticker) {
            addStickerGalleryFragment();
        } else {
            this.effectFragment.myClickHandler(id);
            if (id == R.id.button_lib_cancel || id == R.id.button_lib_ok) {
                clearFxAndFrame();
            }
        }
    }

    private void clearFxAndFrame() {
        int selectedTabIndex = this.effectFragment.getSelectedTabIndex();
        if (this.currentSelectedTabIndex != INDEX_MIRROR_EFFECT && this.currentSelectedTabIndex != INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
            return;
        }
        if (selectedTabIndex == 0 || selectedTabIndex == INDEX_MIRROR_3D) {
            clearViewFlipper();
        }
    }

    void addCanvasTextView() {
        this.canvasText = new CustomRelativeLayout(context, this.textDataList, this.mirrorView.f510I, (SingleTap) new C09753());
        this.canvasText.setApplyTextListener(new C09764());
        this.showText = false;
        this.mirrorView.invalidate();
        this.mainLayout.addView(this.canvasText);
        ((RelativeLayout) findViewById(R.id.text_view_fragment_container)).bringToFront();
        if (textDataList.size() == 0) {
            this.fontFragment = new FontFragment();
            this.fontFragment.setArguments(new Bundle());
            getSupportFragmentManager().beginTransaction().add(R.id.text_view_fragment_container, this.fontFragment, "FONT_FRAGMENT").commit();
            Log.e(TAG, "add fragment");
            this.fontFragment.setFontChoosedListener(this.fontChoosedListener);
        }
    }

    public void addStickerGalleryFragment() {
        FragmentManager fm = getSupportFragmentManager();
        this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
        if (this.galleryFragment == null) {
            this.galleryFragment = new StickerGalleryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.sticker_grid_fragment_container, this.galleryFragment, "myStickerFragmentTag");
            ft.commit();
            this.galleryFragment.setGalleryListener(createGalleryListener());
            Log.e(TAG, "galleryFragment null");
        } else {
            Log.e(TAG, "show gallery fragment");
            getSupportFragmentManager().beginTransaction().show(this.galleryFragment).commit();
        }
        this.galleryFragment.setTotalImage(this.stickerViewContainer.getChildCount());
    }

    StickerGalleryListener createGalleryListener() {
        if (this.stickerGalleryListener == null) {
            this.stickerGalleryListener = new StickerGalleryListener() {
                public void onGalleryOkSingleImage(int resId) {
                    Bitmap bitmap = BitmapFactory.decodeResource(MirrorNewActivity.this.getResources(), resId);
                    if (MirrorNewActivity.this.removeBitmap == null) {
                        MirrorNewActivity.this.removeBitmap = BitmapFactory.decodeResource(MirrorNewActivity.this.getResources(), R.drawable.remove_text);
                    }
                    if (MirrorNewActivity.this.scaleBitmap == null) {
                        MirrorNewActivity.this.scaleBitmap = BitmapFactory.decodeResource(MirrorNewActivity.this.getResources(), R.drawable.scale_text);
                    }
                    StickerView stickerView = new StickerView(MirrorNewActivity.this.context, bitmap, null, MirrorNewActivity.this.removeBitmap, MirrorNewActivity.this.scaleBitmap, resId);
                    stickerView.setStickerViewSelectedListener(MirrorNewActivity.this.stickerViewSelectedListner);
                    if (MirrorNewActivity.this.stickerViewContainer == null) {
                        MirrorNewActivity.this.stickerViewContainer = (FrameLayout) MirrorNewActivity.this.findViewById(R.id.sticker_view_container);
                    }
                    MirrorNewActivity.this.stickerViewContainer.addView(stickerView);
                    Utility.logFreeMemory(MirrorNewActivity.this);
                    FragmentManager fm = MirrorNewActivity.this.getSupportFragmentManager();
                    if (MirrorNewActivity.this.galleryFragment == null) {
                        MirrorNewActivity.this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
                    }
                    fm.beginTransaction().hide(MirrorNewActivity.this.galleryFragment).commit();
                }
                @Override
                public void onGalleryOkImageArray(int[] ImageIdList) {
                    Bitmap removeBitmap = BitmapFactory.decodeResource(MirrorNewActivity.this.getResources(), R.drawable.remove_text);
                    Bitmap scaleBitmap = BitmapFactory.decodeResource(MirrorNewActivity.this.getResources(), R.drawable.scale_text);
                    for (int i = MirrorNewActivity.INDEX_MIRROR; i < ImageIdList.length; i += MirrorNewActivity.INDEX_MIRROR_3D) {
                        StickerView stickerView = new StickerView(MirrorNewActivity.this.context, BitmapFactory.decodeResource(MirrorNewActivity.this.getResources(), ImageIdList[i]), null, removeBitmap, scaleBitmap, ImageIdList[i]);
                        stickerView.setStickerViewSelectedListener(MirrorNewActivity.this.stickerViewSelectedListner);
                        if (MirrorNewActivity.this.stickerViewContainer == null) {
                            MirrorNewActivity.this.stickerViewContainer = (FrameLayout) MirrorNewActivity.this.findViewById(R.id.sticker_view_container);
                        }
                        MirrorNewActivity.this.stickerViewContainer.addView(stickerView);
                    }
                    FragmentManager fm = MirrorNewActivity.this.getSupportFragmentManager();
                    if (MirrorNewActivity.this.galleryFragment == null) {
                        MirrorNewActivity.this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myFragmentTag");
                    }
                    fm.beginTransaction().hide(MirrorNewActivity.this.galleryFragment).commit();
                }
                @Override
                public void onGalleryCancel() {
                    MirrorNewActivity.this.getSupportFragmentManager().beginTransaction().hide(MirrorNewActivity.this.galleryFragment).commit();
                }
            };
        }
        return this.stickerGalleryListener;
    }

    private void set3dMode(int index) {
        this.mirrorView.d3Mode = true;
        if (index > 15 && index < 20) {
            this.mirrorView.setCurrentMode(index);
        } else if (index > 19) {
            this.mirrorView.setCurrentMode(index - 4);
        } else if (index % INDEX_MIRROR_RATIO == 0) {
            this.mirrorView.setCurrentMode(INDEX_MIRROR);
        } else {
            this.mirrorView.setCurrentMode(INDEX_MIRROR_3D);
        }
        this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, false);
        if (Build.VERSION.SDK_INT < 11) {
            if (!(this.mirrorView.d3Bitmap == null || this.mirrorView.d3Bitmap.isRecycled())) {
                this.mirrorView.d3Bitmap.recycle();
            }
            this.mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), this.d3resList[index]);
        } else {
            loadInBitmap(this.d3resList[index]);
        }
        this.mirrorView.postInvalidate();
        setD3ButtonBg(index);
    }

    @SuppressLint({"NewApi"})
    private void loadInBitmap(int resId) {
        Log.e(TAG, "loadInBitmap");
        Utility.logFreeMemory(this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (this.mirrorView.d3Bitmap == null || this.mirrorView.d3Bitmap.isRecycled()) {
            options.inJustDecodeBounds = true;
            options.inMutable = true;
            BitmapFactory.decodeResource(getResources(), resId, options);
            this.mirrorView.d3Bitmap = Bitmap.createBitmap(options.outWidth, options.outHeight, Bitmap.Config.ARGB_8888);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = INDEX_MIRROR_3D;
        options.inBitmap = this.mirrorView.d3Bitmap;
        try {
            this.mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            if (!(this.mirrorView.d3Bitmap == null || this.mirrorView.d3Bitmap.isRecycled())) {
                this.mirrorView.d3Bitmap.recycle();
            }
            this.mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), resId);
        }
    }

    private void setD3ButtonBg(int index) {
        if (d3ButtonArray == null) {
            d3ButtonArray = new Button[D3_BUTTON_SIZE];
            d3ButtonArray[0] = (Button) findViewById(R.id.button_3d_1);
            d3ButtonArray[1] = (Button) findViewById(R.id.button_3d_2);
            d3ButtonArray[2] = (Button) findViewById(R.id.button_3d_3);
            d3ButtonArray[3] = (Button) findViewById(R.id.button_3d_4);
            d3ButtonArray[4] = (Button) findViewById(R.id.button_3d_5);
            d3ButtonArray[5] = (Button) findViewById(R.id.button_3d_6);
            d3ButtonArray[6] = (Button) findViewById(R.id.button_3d_7);
            d3ButtonArray[7] = (Button) findViewById(R.id.button_3d_8);
            d3ButtonArray[8] = (Button) findViewById(R.id.button_3d_9);
            d3ButtonArray[9] = (Button) findViewById(R.id.button_3d_10);
            d3ButtonArray[10] = (Button) findViewById(R.id.button_3d_11);
            d3ButtonArray[11] = (Button) findViewById(R.id.button_3d_12);
            d3ButtonArray[12] = (Button) findViewById(R.id.button_3d_13);
            d3ButtonArray[13] = (Button) findViewById(R.id.button_3d_14);
            d3ButtonArray[14] = (Button) findViewById(R.id.button_3d_15);
            d3ButtonArray[15] = (Button) findViewById(R.id.button_3d_16);
            d3ButtonArray[16] = (Button) findViewById(R.id.button_3d_17);
            d3ButtonArray[17] = (Button) findViewById(R.id.button_3d_18);
            d3ButtonArray[18] = (Button) findViewById(R.id.button_3d_19);
            d3ButtonArray[19] = (Button) findViewById(R.id.button_3d_20);
            d3ButtonArray[20] = (Button) findViewById(R.id.button_3d_21);
            d3ButtonArray[21] = (Button) findViewById(R.id.button_3d_22);
            d3ButtonArray[22] = (Button) findViewById(R.id.button_3d_23);
            d3ButtonArray[23] = (Button) findViewById(R.id.button_3d_24);
        }
        for (int i = 0; i < D3_BUTTON_SIZE; i++) {
            d3ButtonArray[i].setBackgroundColor(getResources().getColor(R.color.primary));
        }
        d3ButtonArray[index].setBackgroundColor(getResources().getColor(R.color.primary_dark));
    }

    private void setMirrorButtonBg(int index) {
        if (mirrorButtonArray == null) {
            mirrorButtonArray = new Button[MIRROR_BUTTON_SIZE];
            mirrorButtonArray[0] = (Button) findViewById(R.id.button_m1);
            mirrorButtonArray[1] = (Button) findViewById(R.id.button_m2);
            mirrorButtonArray[2] = (Button) findViewById(R.id.button_m3);
            mirrorButtonArray[3] = (Button) findViewById(R.id.button_m4);
            mirrorButtonArray[4] = (Button) findViewById(R.id.button_m5);
            mirrorButtonArray[5] = (Button) findViewById(R.id.button_m6);
            mirrorButtonArray[6] = (Button) findViewById(R.id.button_m7);
            mirrorButtonArray[7] = (Button) findViewById(R.id.button_m8);
            mirrorButtonArray[8] = (Button) findViewById(R.id.button_m9);
            mirrorButtonArray[9] = (Button) findViewById(R.id.button_m10);
            mirrorButtonArray[10] = (Button) findViewById(R.id.button_m11);
            mirrorButtonArray[11] = (Button) findViewById(R.id.button_m12);
            mirrorButtonArray[12] = (Button) findViewById(R.id.button_m13);
            mirrorButtonArray[13] = (Button) findViewById(R.id.button_m14);
            mirrorButtonArray[14] = (Button) findViewById(R.id.button_m15);
        }
        for (int i = 0; i < MIRROR_BUTTON_SIZE; i += INDEX_MIRROR_3D) {
            mirrorButtonArray[i].setBackgroundResource(R.color.primary);
        }
        mirrorButtonArray[index].setBackgroundResource(R.color.primary_dark);
    }

    private void setRatioButtonBg(int index) {
        if (ratioButtonArray == null) {
            ratioButtonArray = new Button[RATIO_BUTTON_SIZE];
            ratioButtonArray[INDEX_MIRROR] = (Button) findViewById(R.id.button11);
            ratioButtonArray[INDEX_MIRROR_3D] = (Button) findViewById(R.id.button21);
            ratioButtonArray[INDEX_MIRROR_RATIO] = (Button) findViewById(R.id.button12);
            ratioButtonArray[INDEX_MIRROR_EFFECT] = (Button) findViewById(R.id.button32);
            ratioButtonArray[INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX] = (Button) findViewById(R.id.button23);
            ratioButtonArray[INDEX_MIRROR_ADJUSTMENT] = (Button) findViewById(R.id.button43);
            ratioButtonArray[TAB_SIZE] = (Button) findViewById(R.id.button34);
            ratioButtonArray[INDEX_MIRROR_INVISIBLE_VIEW] = (Button) findViewById(R.id.button45);
            ratioButtonArray[8] = (Button) findViewById(R.id.button57);
            ratioButtonArray[9] = (Button) findViewById(R.id.button169);
            ratioButtonArray[10] = (Button) findViewById(R.id.button916);
        }
        for (int i = INDEX_MIRROR; i < RATIO_BUTTON_SIZE; i += INDEX_MIRROR_3D) {
            ratioButtonArray[i].setBackgroundResource(R.drawable.crop_border);
            ratioButtonArray[i].setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.ratioButtonArray[index].setBackgroundResource(R.drawable.crop_border_selected);
        this.ratioButtonArray[index].setTextColor(-1);
    }

    void setSelectedTab(int index) {
        setTabBg(INDEX_MIRROR);
        int displayedChild = this.viewFlipper.getDisplayedChild();
        if (index == 0) {
            if (displayedChild != 0) {
                this.viewFlipper.setInAnimation(this.slideLeftIn);
                this.viewFlipper.setOutAnimation(this.slideRightOut);
                this.viewFlipper.setDisplayedChild(INDEX_MIRROR);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_3D) {
            setTabBg(INDEX_MIRROR_3D);
            if (displayedChild != INDEX_MIRROR_3D) {
                if (displayedChild == 0) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                }
                this.viewFlipper.setDisplayedChild(INDEX_MIRROR_3D);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_RATIO) {
            setTabBg(INDEX_MIRROR_RATIO);
            if (displayedChild != INDEX_MIRROR_RATIO) {
                if (displayedChild == 0) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                }
                this.viewFlipper.setDisplayedChild(INDEX_MIRROR_RATIO);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_EFFECT) {
            setTabBg(INDEX_MIRROR_EFFECT);
            this.effectFragment.setSelectedTabIndex(INDEX_MIRROR);
            if (displayedChild != INDEX_MIRROR_EFFECT) {
                if (displayedChild == 0 || displayedChild == INDEX_MIRROR_RATIO) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                }
                this.viewFlipper.setDisplayedChild(INDEX_MIRROR_EFFECT);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
            setTabBg(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
            this.effectFragment.setSelectedTabIndex(INDEX_MIRROR_3D);
            if (displayedChild != INDEX_MIRROR_EFFECT) {
                if (displayedChild == INDEX_MIRROR_ADJUSTMENT) {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                }
                this.viewFlipper.setDisplayedChild(INDEX_MIRROR_EFFECT);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_ADJUSTMENT) {
            setTabBg(INDEX_MIRROR_ADJUSTMENT);
            this.effectFragment.showToolBar();
            if (displayedChild != INDEX_MIRROR_EFFECT) {
                this.viewFlipper.setInAnimation(this.slideRightIn);
                this.viewFlipper.setOutAnimation(this.slideLeftOut);
                this.viewFlipper.setDisplayedChild(INDEX_MIRROR_EFFECT);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_INVISIBLE_VIEW) {
            setTabBg(-1);
            if (displayedChild != INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                this.viewFlipper.setInAnimation(this.slideRightIn);
                this.viewFlipper.setOutAnimation(this.slideLeftOut);
                this.viewFlipper.setDisplayedChild(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
            }
        }
    }

    private void setTabBg(int index) {
        this.currentSelectedTabIndex = index;
        if (this.tabButtonList == null) {
            this.tabButtonList = new View[TAB_SIZE];
            this.tabButtonList[INDEX_MIRROR] = findViewById(R.id.button_mirror);
            this.tabButtonList[INDEX_MIRROR_3D] = findViewById(R.id.button_mirror_3d);
            this.tabButtonList[INDEX_MIRROR_EFFECT] = findViewById(R.id.button_mirror_effect);
            this.tabButtonList[INDEX_MIRROR_RATIO] = findViewById(R.id.button_mirror_ratio);
            this.tabButtonList[INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX] = findViewById(R.id.button_mirror_frame);
            this.tabButtonList[INDEX_MIRROR_ADJUSTMENT] = findViewById(R.id.button_mirror_adj);
        }
        for (int i = INDEX_MIRROR; i < this.tabButtonList.length; i += INDEX_MIRROR_3D) {
            this.tabButtonList[i].setBackgroundResource(R.drawable.mirror_footer_button);
        }
        if (index >= 0) {
            this.tabButtonList[index].setBackgroundResource(R.color.footer_button_color_pressed);
        }
    }

    void setSelectedTab() {
        if (this.effectFragment == null) {
            this.effectFragment = (EffectFragment) getSupportFragmentManager().findFragmentByTag("MY_EFFECT_FRAGMENT");
            if (this.effectFragment == null) {
                this.effectFragment = new EffectFragment();
                Log.e(TAG, "EffectFragment == null");
                this.effectFragment.isAppPro(true);
                this.effectFragment.setBitmap(this.sourceBitmap);
                this.effectFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.mirror_effect_fragment_container, this.effectFragment, "MY_EFFECT_FRAGMENT").commit();
            } else {
                this.effectFragment.setBitmap(this.sourceBitmap);
                this.effectFragment.isAppPro(true);
                this.effectFragment.setSelectedTabIndex(INDEX_MIRROR);
            }
            this.effectFragment.setBitmapReadyListener(new C09786());
           // this.effectFragment.setBuyProVersionListener(new C09797());
            this.effectFragment.setBorderIndexChangedListener(new C09808());
        }
    }

    void setVisibilityOfFilterHorizontalListview(boolean show) {
        if (show && this.effectFragment.isHidden()) {
            getSupportFragmentManager().beginTransaction().show(this.effectFragment).commit();
        }
        if (!show && this.effectFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().hide(this.effectFragment).commit();
        }
        Log.e(TAG, "effectFragment.isVisible() " + this.effectFragment.isVisible());
        findViewById(R.id.mirror_effect_fragment_container).bringToFront();
    }

    void clearViewFlipper() {
        this.viewFlipper.setInAnimation(null);
        this.viewFlipper.setOutAnimation(null);
        this.viewFlipper.setDisplayedChild(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
        setTabBg(-1);
    }

    public void onBackPressed() {
        if (this.fontFragment != null && this.fontFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(this.fontFragment).commit();
        } else if (this.galleryFragment != null && this.galleryFragment.isVisible()) {
            this.galleryFragment.backtrace();
        } else if (this.viewFlipper.getDisplayedChild() == INDEX_MIRROR_EFFECT) {
            clearFxAndFrame();
            if (this.effectFragment.backPressed()) {
                Log.e(TAG, "effectFragment back pressed!");
            } else {
                clearViewFlipper();
            }
        } else if (!this.showText && this.canvasText != null) {
            this.showText = true;
            this.mainLayout.removeView(this.canvasText);
            this.mirrorView.postInvalidate();
            this.canvasText = null;
            Log.e(TAG, "replace fragment");
        } else if (this.viewFlipper.getDisplayedChild() != INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
            clearViewFlipper();
        } else {
            backButtonAlertBuilder();
        }
    }

    private void backButtonAlertBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("Would you like to save image ?").setCancelable(true).setPositiveButton("Yes", new C06009()).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setNeutralButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MirrorNewActivity.this.activity.finish();
            }
        });
        this.saveImageAlert = builder.create();
        this.saveImageAlert.show();
    }
}
