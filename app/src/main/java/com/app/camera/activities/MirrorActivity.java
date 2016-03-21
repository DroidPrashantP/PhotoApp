package com.app.camera.activities;

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
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.camera.Domain.MirrorMode;
import com.app.camera.R;
import com.app.camera.bitmap.BitmapLoader;
import com.app.camera.canvastext.ApplyTextInterface;
import com.app.camera.canvastext.CustomRelativeLayout;
import com.app.camera.canvastext.TextData;
import com.app.camera.common_lib.MyAsyncTask;
import com.app.camera.sticker.StickerData;
import com.app.camera.sticker.StickerGalleryFragment;
import com.app.camera.sticker.StickerGalleryListener;
import com.app.camera.sticker.StickerView;
import com.app.camera.sticker.Utility;
import com.app.camera.utils.Constants;
import com.app.camera.utils.Toaster;
import com.app.camera.utils.UriToUrl;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MirrorActivity extends AppCompatActivity {

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
    private static final String TAG = "MirrorActivity";
    private String imageUrl;
    int D3_BUTTON_SIZE;
    int MIRROR_BUTTON_SIZE;
    int RATIO_BUTTON_SIZE;
    Activity activity;

    Context context;
    int currentSelectedTabIndex;
    int currentStickerIndex;
    Button d3ButtonArray[];
    private int d3resList[];
    Bitmap filterBitmap;
    int initialYPos;
    InterstitialAd interstitial;
    RelativeLayout mainLayout;
    Matrix matrix;
    Matrix matrixMirror1;
    Matrix matrixMirror2;
    Matrix matrixMirror3;
    Matrix matrixMirror4;
    Button mirrorButtonArray[];
    MirrorView mirrorView;
    int mode;
    float mulX;
    float mulY;
    Button ratioButtonArray[];
    Bitmap removeBitmap;
    AlertDialog saveImageAlert;
    Bitmap scaleBitmap;
    public int screenHeightPixels;
    public int screenWidthPixels;
    boolean showText;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    Bitmap sourceBitmap;
    ArrayList stickerList;
    FrameLayout stickerViewContainer;
    View tabButtonList[];
    ArrayList textDataList;
    ViewFlipper viewFlipper;
    private CustomRelativeLayout canvasText;
    public Uri imageUri;
    private int source_id;
    //  private EffectFragment effectFragment;

    public MirrorActivity() {
        context = this;
        matrix = new Matrix();
        matrixMirror1 = new Matrix();
        matrixMirror2 = new Matrix();
        matrixMirror3 = new Matrix();
        matrixMirror4 = new Matrix();
        activity = this;
        initialYPos = 0;
        textDataList = new ArrayList();
        showText = false;
        //   fontChoosedListener = new C09731();
        d3resList = new int[]{R.drawable.mirror_3d_14, R.drawable.mirror_3d_14, R.drawable.mirror_3d_10, R.drawable.mirror_3d_10, R.drawable.mirror_3d_11, R.drawable.mirror_3d_11, R.drawable.mirror_3d_4, R.drawable.mirror_3d_4, R.drawable.mirror_3d_3, R.drawable.mirror_3d_3, R.drawable.mirror_3d_1, R.drawable.mirror_3d_1, R.drawable.mirror_3d_6, R.drawable.mirror_3d_6, R.drawable.mirror_3d_13, R.drawable.mirror_3d_13, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16};
        currentStickerIndex = 0;
        //  stickerViewSelectedListner = new C09742();
        D3_BUTTON_SIZE = 24;
        MIRROR_BUTTON_SIZE = 15;
        RATIO_BUTTON_SIZE = 11;
        currentSelectedTabIndex = -1;
        mode = INDEX_MIRROR_ADJUSTMENT;
        mulX = 16.0f;
        mulY = 16.0f;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        try {
            source_id = getIntent().getExtras().getInt(Constants.EXTRA_KEY_IMAGE_SOURCE);
            imageUri = getIntent().getData();
            loadImage();

        } catch (Exception e) {
            e.printStackTrace();
        }
        int width;
        int height;
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeightPixels = metrics.heightPixels;
        screenWidthPixels = metrics.widthPixels;
        Display display = getWindowManager().getDefaultDisplay();
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
        Log.e(TAG, "screenWidthPixels " + screenWidthPixels);
        Log.e(TAG, "height " + height);
        Log.e(TAG, "heightPixels " + screenHeightPixels);
        if (screenWidthPixels <= 0) {
            screenWidthPixels = width;
        }
        if (screenHeightPixels <= 0) {
            screenHeightPixels = height;
        }
        setContentView(R.layout.activity_mirror);
        try {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadImage() {
        BitmapWorkerTask bitmapWorker = new BitmapWorkerTask();
        bitmapWorker.execute();
    }

    private class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {
        DisplayMetrics metrics;
        BitmapLoader bitmapLoader;
        ProgressDialog prog;

        public BitmapWorkerTask() {
            metrics = getResources().getDisplayMetrics();
            imageUrl = UriToUrl.get(getApplicationContext(), imageUri);
            bitmapLoader = new BitmapLoader();
            prog = ProgressDialog.show(MirrorActivity.this, "", "Progress...");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Void... arg0) {
            try {
                return bitmapLoader.load(getApplicationContext(), new int[]{metrics.widthPixels, metrics.heightPixels}, imageUrl);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                setImage(bitmap);
            } else {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
            prog.dismiss();
        }
    }

    private void setImage(Bitmap bitmap) {
        sourceBitmap = bitmap;
        mirrorView = new MirrorView(this, screenWidthPixels, screenHeightPixels);
        mainLayout = (RelativeLayout) findViewById(R.id.container);
        mainLayout.addView(mirrorView);
        stickerList = new ArrayList();
        stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
        stickerViewContainer.bringToFront();

        viewFlipper = (ViewFlipper) findViewById(R.id.mirror_view_flipper);
        viewFlipper.bringToFront();
        ((ViewGroup) findViewById(R.id.mirror_footer)).bringToFront();
        slideLeftIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
        slideLeftOut = AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);
        slideRightIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
        slideRightOut = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right);

        findViewById(R.id.mirror_header).bringToFront();
        addEffectFragment();
        Utility.logFreeMemory(this);
        setSelectedTab(0);
        // findViewById(R.id.sticker_grid_fragment_container).bringToFront();
//        if (savedInstanceState != null) {
//            FragmentManager fm = getSupportFragmentManager();
//            galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
//            if (galleryFragment != null) {
//                fm.beginTransaction().hide(galleryFragment).commit();
//                galleryFragment.setGalleryListener(createGalleryListener());
//            }
//        }
    }

    void addEffectFragment() {
//        if (effectFragment == null) {
//            effectFragment = (EffectFragment) getSupportFragmentManager().findFragmentByTag("MY_EFFECT_FRAGMENT");
//            if (effectFragment == null) {
//                effectFragment = new EffectFragment();
//                Log.e(TAG, "EffectFragment == null");
//                effectFragment.isAppPro(true);
//                effectFragment.setBitmap(sourceBitmap);
//                effectFragment.setArguments(getIntent().getExtras());
//                getSupportFragmentManager().beginTransaction().add(R.id.mirror_effect_fragment_container, effectFragment, "MY_EFFECT_FRAGMENT").commit();
//            } else {
//                effectFragment.setBitmap(sourceBitmap);
//                effectFragment.isAppPro(true);
//                effectFragment.setSelectedTabIndex(INDEX_MIRROR);
//            }
//            effectFragment.setBitmapReadyListener(new C09786());
//            effectFragment.setBuyProVersionListener(new C09797());
//            effectFragment.setBorderIndexChangedListener(new C09808());
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mirror, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setTabBg(int index) {
        currentSelectedTabIndex = index;
        if (tabButtonList == null) {
            tabButtonList = new View[TAB_SIZE];
            tabButtonList[INDEX_MIRROR] = findViewById(R.id.button_mirror);
            tabButtonList[INDEX_MIRROR_3D] = findViewById(R.id.button_mirror_3d);
            tabButtonList[INDEX_MIRROR_EFFECT] = findViewById(R.id.button_mirror_effect);
            tabButtonList[INDEX_MIRROR_RATIO] = findViewById(R.id.button_mirror_ratio);
            tabButtonList[INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX] = findViewById(R.id.button_mirror_frame);
            tabButtonList[INDEX_MIRROR_ADJUSTMENT] = findViewById(R.id.button_mirror_adj);
        }
        for (int i = INDEX_MIRROR; i < tabButtonList.length; i += INDEX_MIRROR_3D) {
            tabButtonList[i].setBackgroundResource(R.drawable.mirror_footer_button);
        }
        if (index >= 0) {
            tabButtonList[index].setBackgroundResource(R.color.category_color_five);
        }
    }

    void setSelectedTab(int index) {
        setTabBg(INDEX_MIRROR);
        int displayedChild = viewFlipper.getDisplayedChild();
        if (index == 0) {
            if (displayedChild != 0) {
                viewFlipper.setInAnimation(slideLeftIn);
                viewFlipper.setOutAnimation(slideRightOut);
                viewFlipper.setDisplayedChild(INDEX_MIRROR);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_3D) {
            setTabBg(INDEX_MIRROR_3D);
            if (displayedChild != INDEX_MIRROR_3D) {
                if (displayedChild == 0) {
                    viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                } else {
                    viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                }
                viewFlipper.setDisplayedChild(INDEX_MIRROR_3D);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_RATIO) {
            setTabBg(INDEX_MIRROR_RATIO);
            if (displayedChild != INDEX_MIRROR_RATIO) {
                if (displayedChild == 0) {
                    viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                } else {
                    viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                }
                viewFlipper.setDisplayedChild(INDEX_MIRROR_RATIO);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_EFFECT) {
            setTabBg(INDEX_MIRROR_EFFECT);
            // effectFragment.setSelectedTabIndex(INDEX_MIRROR);
            if (displayedChild != INDEX_MIRROR_EFFECT) {
                if (displayedChild == 0 || displayedChild == INDEX_MIRROR_RATIO) {
                    viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                } else {
                    viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                }
                viewFlipper.setDisplayedChild(INDEX_MIRROR_EFFECT);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
            setTabBg(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
            //  effectFragment.setSelectedTabIndex(INDEX_MIRROR_3D);
            if (displayedChild != INDEX_MIRROR_EFFECT) {
                if (displayedChild == INDEX_MIRROR_ADJUSTMENT) {
                    viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                } else {
                    viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                }
                viewFlipper.setDisplayedChild(INDEX_MIRROR_EFFECT);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_ADJUSTMENT) {
            setTabBg(INDEX_MIRROR_ADJUSTMENT);
            // effectFragment.showToolBar();
            if (displayedChild != INDEX_MIRROR_EFFECT) {
                viewFlipper.setInAnimation(slideRightIn);
                viewFlipper.setOutAnimation(slideLeftOut);
                viewFlipper.setDisplayedChild(INDEX_MIRROR_EFFECT);
            } else {
                return;
            }
        }
        if (index == INDEX_MIRROR_INVISIBLE_VIEW) {
            setTabBg(-1);
            if (displayedChild != INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                viewFlipper.setInAnimation(slideRightIn);
                viewFlipper.setOutAnimation(slideLeftOut);
                viewFlipper.setDisplayedChild(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
            }
        }
    }


    class MirrorView extends View {

        public Matrix I = new Matrix();
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
        public MirrorMode mirrorModeList[];
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

            I = new Matrix();
            framePaint = new Paint();
            isVerticle = false;
            defaultColor = getResources().getColor(R.color.text_white);
            mirrorModeList = new MirrorMode[20];
            currentModeIndex = 0;
            drawSavedImage = false;
            d3Mode = false;
            textMatrix = new Matrix();
            textRectPaint = new Paint(1);
            m1 = new Matrix();
            m2 = new Matrix();
            m3 = new Matrix();
            if (sourceBitmap != null) {
                width = sourceBitmap.getWidth();
                height = sourceBitmap.getHeight();
            } else {
                Log.e("Bitmap0 = ", "null");
                Toast.makeText(MirrorActivity.this, "Null Bitmap", Toast.LENGTH_LONG).show();
            }
            int widthPixels = screenWidth;
            int heightPixels = screenHeight;
            createMatrix(widthPixels, heightPixels);
            createRectX(widthPixels, heightPixels);
            createRectY(widthPixels, heightPixels);
            createRectXY(widthPixels, heightPixels);
            createModes();
            framePaint.setAntiAlias(true);
            framePaint.setFilterBitmap(true);
            framePaint.setDither(true);
            textRectPaint.setColor(getResources().getColor(R.color.blue));
        }

        public void createMatrix(int i, int j) {
            I.reset();
            matrixMirror1.reset();
            matrixMirror1.postScale(-1F, 1.0F);
            matrixMirror1.postTranslate(i, 0.0F);
            matrixMirror2.reset();
            matrixMirror2.postScale(1.0F, -1F);
            matrixMirror2.postTranslate(0.0F, j);
            matrixMirror3.reset();
            matrixMirror3.postScale(-1F, -1F);
            matrixMirror3.postTranslate(i, j);
        }

        private void createModes() {
            modeX = new MirrorMode(4, srcRect3, destRect1, destRect1, destRect3, destRect3, matrixMirror1, I, matrixMirror1, tMode3, totalArea3);
            modeX2 = new MirrorMode(4, srcRect3, destRect1, destRect4, destRect1, destRect4, matrixMirror1, matrixMirror1, I, tMode3, totalArea3);
            modeX3 = new MirrorMode(4, srcRect3, destRect3, destRect2, destRect3, destRect2, matrixMirror1, matrixMirror1, I, tMode3, totalArea3);
            modeX8 = new MirrorMode(4, srcRect3, destRect1, destRect1, destRect1, destRect1, matrixMirror1, matrixMirror2, matrixMirror3, tMode3, totalArea3);
            int m9TouchMode = 4;
            if (tMode3 == 0) {
                m9TouchMode = 0;
            }
            modeX9 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRect3, destRect2, destRect2, destRect2, destRect2, matrixMirror1, matrixMirror2, matrixMirror3, m9TouchMode, totalArea3);
            int m10TouchMode = INDEX_MIRROR_EFFECT;
            if (tMode3 == INDEX_MIRROR_3D) {
                m10TouchMode = INDEX_MIRROR_3D;
            }
            modeX10 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRect3, destRect3, destRect3, destRect3, destRect3, matrixMirror1, matrixMirror2, matrixMirror3, m10TouchMode, totalArea3);
            int m11TouchMode = INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX;
            if (tMode3 == 0) {
                m11TouchMode = INDEX_MIRROR_EFFECT;
            }
            modeX11 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRect3, destRect4, destRect4, destRect4, destRect4, matrixMirror1, matrixMirror2, matrixMirror3, m11TouchMode, totalArea3);
            modeX4 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect1, destRect1X, destRect1X, matrixMirror1, tMode1, totalArea1);
            int m5TouchMode = INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX;
            if (tMode1 == 0) {
                m5TouchMode = INDEX_MIRROR;
            } else if (tMode1 == INDEX_MIRROR_ADJUSTMENT) {
                m5TouchMode = INDEX_MIRROR_ADJUSTMENT;
            }
            modeX5 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect1, destRect2X, destRect2X, matrixMirror1, m5TouchMode, totalArea1);
            modeX6 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect2, destRect1Y, destRect1Y, matrixMirror2, tMode2, totalArea2);
            int m7TouchMode = INDEX_MIRROR_EFFECT;
            if (tMode2 == INDEX_MIRROR_3D) {
                m7TouchMode = INDEX_MIRROR_3D;
            } else if (tMode2 == TAB_SIZE) {
                m7TouchMode = TAB_SIZE;
            }
            modeX7 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect2, destRect2Y, destRect2Y, matrixMirror2, m7TouchMode, totalArea2);
            modeX12 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect1, destRect1X, destRect2X, matrixMirror4, tMode1, totalArea1);
            modeX13 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect2, destRect1Y, destRect2Y, matrixMirror4, tMode2, totalArea2);
            modeX14 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect1, destRect1X, destRect1X, matrixMirror3, tMode1, totalArea1);
            modeX15 = new MirrorMode(INDEX_MIRROR_RATIO, srcRect2, destRect1Y, destRect1Y, matrixMirror3, tMode2, totalArea2);
            modeX16 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRectPaper, dstRectPaper1, dstRectPaper2, dstRectPaper3, dstRectPaper4, matrixMirror1, matrixMirror1, I, tMode1, totalArea1);
            modeX17 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRectPaper, dstRectPaper1, dstRectPaper3, dstRectPaper3, dstRectPaper1, I, matrixMirror1, matrixMirror1, tMode1, totalArea1);
            modeX18 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRectPaper, dstRectPaper2, dstRectPaper4, dstRectPaper2, dstRectPaper4, I, matrixMirror1, matrixMirror1, tMode1, totalArea1);
            modeX19 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRectPaper, dstRectPaper1, dstRectPaper2, dstRectPaper2, dstRectPaper1, I, matrixMirror1, matrixMirror1, tMode1, totalArea1);
            modeX20 = new MirrorMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX, srcRectPaper, dstRectPaper4, dstRectPaper3, dstRectPaper3, dstRectPaper4, I, matrixMirror1, matrixMirror1, tMode1, totalArea1);
            mirrorModeList[0] = modeX4;
            mirrorModeList[1] = modeX5;
            mirrorModeList[2] = modeX6;
            mirrorModeList[3] = modeX7;
            mirrorModeList[4] = modeX8;
            mirrorModeList[5] = modeX9;
            mirrorModeList[6] = modeX10;
            mirrorModeList[7] = modeX11;
            mirrorModeList[8] = modeX12;
            mirrorModeList[9] = modeX13;
            mirrorModeList[10] = modeX14;
            mirrorModeList[11] = modeX15;
            mirrorModeList[12] = modeX;
            mirrorModeList[13] = modeX2;
            mirrorModeList[14] = modeX3;
            mirrorModeList[15] = modeX7;
            mirrorModeList[16] = modeX17;
            mirrorModeList[17] = modeX18;
            mirrorModeList[18] = modeX19;
            mirrorModeList[19] = modeX20;
        }

        public void createRectX(int widthPixels, int heightPixels) {
            float destH = ((float) widthPixels) * (mulY / mulX);
            float destW = ((float) widthPixels) / 2.0f;
            float destX = 0.0f;
            float destY = initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((mulX / mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            destY = ((float) initialYPos) + ((((float) heightPixels) - destH) / 2.0f);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.destRect1X = new RectF(destX, destY, destW + destX, destH + destY);
            float destXX = destX + destW;
            this.destRect2X = new RectF(destXX, destY, destW + destXX, destH + destY);
            this.totalArea1 = new RectF(destX, destY, destW + destXX, destH + destY);
            this.tMode1 = MirrorActivity.INDEX_MIRROR_3D;
            if (mulX * ((float) this.height) <= (mulY * 2.0f) * ((float) this.width)) {
                srcX = (((float) this.width) - (((mulX / mulY) * ((float) this.height)) / 2.0f)) / 2.0f;
                srcX2 = srcX + (((mulX / mulY) * ((float) this.height)) / 2.0f);
            } else {
                srcY = (((float) this.height) - (((float) (this.width * MirrorActivity.INDEX_MIRROR_RATIO)) * (mulY / mulX))) / 2.0f;
                srcY2 = srcY + (((float) (this.width * MirrorActivity.INDEX_MIRROR_RATIO)) * (mulY / mulX));
                this.tMode1 = MirrorActivity.INDEX_MIRROR_ADJUSTMENT;
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
            float destH = (((float) widthPixels) * (mulY / mulX)) / 2.0f;
            float destW = (float) widthPixels;
            float destX = 0.0f;
            float destY = (float) initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((mulX / mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            destY = ((float) initialYPos) + ((((float) heightPixels) - (2.0f * destH)) / 2.0f);
            this.destRect1Y = new RectF(destX, destY, destW + destX, destH + destY);
            float destYY = destY + destH;
            this.destRect2Y = new RectF(destX, destYY, destW + destX, destH + destYY);
            this.totalArea2 = new RectF(destX, destY, destW + destX, destH + destYY);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.tMode2 = MirrorActivity.INDEX_MIRROR;
            if ((mulX * 2.0f) * ((float) this.height) > mulY * ((float) this.width)) {
                srcY = (((float) this.height) - (((mulY / mulX) * ((float) this.width)) / 2.0f)) / 2.0f;
                srcY2 = srcY + (((mulY / mulX) * ((float) this.width)) / 2.0f);
            } else {
                srcX = (((float) this.width) - (((float) (this.height * MirrorActivity.INDEX_MIRROR_RATIO)) * (mulX / mulY))) / 2.0f;
                srcX2 = srcX + (((float) (this.height * MirrorActivity.INDEX_MIRROR_RATIO)) * (mulX / mulY));
                this.tMode2 = MirrorActivity.TAB_SIZE;
            }
            this.srcRect2 = new RectF(srcX, srcY, srcX2, srcY2);
        }

        private void createRectXY(int widthPixels, int heightPixels) {
            float destH = (((float) widthPixels) * (mulY / mulX)) / 2.0f;
            float destW = ((float) widthPixels) / 2.0f;
            float destX = 0.0f;
            float destY = (float) initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((mulX / mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            destY = ((float) initialYPos) + ((((float) heightPixels) - (2.0f * destH)) / 2.0f);
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
            if (mulX * ((float) this.height) <= mulY * ((float) this.width)) {
                srcX = (((float) this.width) - ((mulX / mulY) * ((float) this.height))) / 2.0f;
                srcX2 = srcX + ((mulX / mulY) * ((float) this.height));
                this.tMode3 = MirrorActivity.INDEX_MIRROR_3D;
            } else {
                srcY = (((float) this.height) - (((float) this.width) * (mulY / mulX))) / 2.0f;
                srcY2 = srcY + (((float) this.width) * (mulY / mulX));
                this.tMode3 = MirrorActivity.INDEX_MIRROR;
            }
            this.srcRect3 = new RectF(srcX, srcY, srcX2, srcY2);
        }

        private void drawMode(Canvas canvas, Bitmap bitmap, MirrorMode mirrormode, Matrix matrix1) {
            canvas.setMatrix(matrix1);
            canvas.drawBitmap(bitmap, mirrormode.getDrawBitmapSrc(), mirrormode.rect1, framePaint);
            m1.set(mirrormode.matrix1);
            m1.postConcat(matrix1);
            canvas.setMatrix(m1);
            if (bitmap != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(bitmap, mirrormode.getDrawBitmapSrc(), mirrormode.rect2, framePaint);
            }
            if (mirrormode.count == 4) {
                m2.set(mirrormode.matrix2);
                m2.postConcat(matrix1);
                canvas.setMatrix(m2);
                if (bitmap != null && !bitmap.isRecycled()) {
                    canvas.drawBitmap(bitmap, mirrormode.getDrawBitmapSrc(), mirrormode.rect3, framePaint);
                }
                m3.set(mirrormode.matrix3);
                m3.postConcat(matrix1);
                canvas.setMatrix(m3);
                if (bitmap != null && !bitmap.isRecycled()) {
                    canvas.drawBitmap(bitmap, mirrormode.getDrawBitmapSrc(), mirrormode.rect4, framePaint);
                }
            }
        }

        private void reset(int i, int j, boolean flag) {
            createMatrix(i, j);
            createRectX(i, j);
            createRectY(i, j);
            createRectXY(i, j);
            createModes();
            if (flag) {
                postInvalidate();
            }
        }


        private void setCurrentMode(int i) {
            currentModeIndex = i;
        }

        public Bitmap getBitmap() {
            setDrawingCacheEnabled(true);
            buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(getDrawingCache());
            setDrawingCacheEnabled(false);
            return bitmap;
        }

        public MirrorMode getCurrentMirrorMode() {
            return mirrorModeList[currentModeIndex];
        }

        void moveGrid(RectF srcRect, float x, float y) {
            if (mirrorModeList[currentModeIndex].touchMode == MirrorActivity.INDEX_MIRROR_3D || mirrorModeList[currentModeIndex].touchMode == MirrorActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX || mirrorModeList[currentModeIndex].touchMode == MirrorActivity.TAB_SIZE) {
                if (mirrorModeList[currentModeIndex].touchMode == MirrorActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                    x *= -1.0f;
                }
                if (isTouchStartedLeft && mirrorModeList[currentModeIndex].touchMode != MirrorActivity.TAB_SIZE) {
                    x *= -1.0f;
                }
                if (srcRect.left + x < 0.0f) {
                    x = -srcRect.left;
                }
                if (srcRect.right + x >= ((float) width)) {
                    x = ((float) width) - srcRect.right;
                }
                srcRect.left += x;
                srcRect.right += x;
            } else if (mirrorModeList[currentModeIndex].touchMode == 0 || mirrorModeList[currentModeIndex].touchMode == MirrorActivity.INDEX_MIRROR_EFFECT || mirrorModeList[currentModeIndex].touchMode == MirrorActivity.INDEX_MIRROR_ADJUSTMENT) {
                if (mirrorModeList[currentModeIndex].touchMode == MirrorActivity.INDEX_MIRROR_EFFECT) {
                    y *= -1.0f;
                }
                if (isTouchStartedTop && mirrorModeList[currentModeIndex].touchMode != MirrorActivity.INDEX_MIRROR_ADJUSTMENT) {
                    y *= -1.0f;
                }
                if (srcRect.top + y < 0.0f) {
                    y = -srcRect.top;
                }
                if (srcRect.bottom + y >= ((float) height)) {
                    y = ((float) height) - srcRect.bottom;
                }
                srcRect.top += y;
                srcRect.bottom += y;
            }
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(defaultColor);
            if (filterBitmap == null) {
                drawMode(canvas, sourceBitmap, mirrorModeList[currentModeIndex], I);
            } else {
                drawMode(canvas, filterBitmap, mirrorModeList[currentModeIndex], I);
            }
            if (!(!d3Mode || d3Bitmap == null || d3Bitmap.isRecycled())) {
                canvas.setMatrix(I);
                canvas.drawBitmap(d3Bitmap, null, mirrorModeList[currentModeIndex].rectTotalArea, framePaint);
            }
            if (showText) {
                for (int i = MirrorActivity.INDEX_MIRROR; i < textDataList.size(); i += MirrorActivity.INDEX_MIRROR_3D) {
                    textMatrix.set(((TextData) textDataList.get(i)).imageSaveMatrix);
                    textMatrix.postConcat(I);
                    canvas.setMatrix(textMatrix);
                    canvas.drawText(((TextData) textDataList.get(i)).message, ((TextData) textDataList.get(i)).xPos, ((TextData) textDataList.get(i)).yPos, ((TextData) textDataList.get(i)).textPaint);
                    canvas.setMatrix(I);
                    canvas.drawRect(0.0f, 0.0f, mirrorModeList[currentModeIndex].rectTotalArea.left, (float) screenHeightPixels, textRectPaint);
                    canvas.drawRect(0.0f, 0.0f, (float) screenWidthPixels, mirrorModeList[currentModeIndex].rectTotalArea.top, textRectPaint);
                    canvas.drawRect(mirrorModeList[currentModeIndex].rectTotalArea.right, 0.0f, (float) screenWidthPixels, (float) screenHeightPixels, textRectPaint);
                    canvas.drawRect(0.0f, mirrorModeList[currentModeIndex].rectTotalArea.bottom, (float) screenWidthPixels, (float) screenHeightPixels, textRectPaint);
                }
            }
            if (!(frameBitmap == null || frameBitmap.isRecycled())) {
                canvas.setMatrix(I);
                canvas.drawBitmap(frameBitmap, null, mirrorModeList[currentModeIndex].rectTotalArea, framePaint);
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MirrorActivity.INDEX_MIRROR:
                    if (x < ((float) (screenWidthPixels / MirrorActivity.INDEX_MIRROR_RATIO))) {
                        this.isTouchStartedLeft = true;
                    } else {
                        this.isTouchStartedLeft = false;
                    }
                    if (y < ((float) (screenHeightPixels / MirrorActivity.INDEX_MIRROR_RATIO))) {
                        this.isTouchStartedTop = true;
                    } else {
                        this.isTouchStartedTop = false;
                    }
                    this.oldX = x;
                    this.oldY = y;
                    break;
                case MirrorActivity.INDEX_MIRROR_RATIO /*2*/:
                    moveGrid(this.mirrorModeList[this.currentModeIndex].getSrcRect(), x - this.oldX, y - this.oldY);
                    this.mirrorModeList[this.currentModeIndex].updateBitmapSrc();
                    this.oldX = x;
                    this.oldY = y;
                    break;
            }
            postInvalidate();
            return true;
        }


        public void setFrame(int i) {
            if (frameBitmap != null && !frameBitmap.isRecycled()) {
                frameBitmap.recycle();
                frameBitmap = null;
            }
            if (i == 0) {
                postInvalidate();
                return;
            } else {
                //   frameBitmap = BitmapFactory.decodeResource(getResources(), LibUtility.borderRes[i]);
                postInvalidate();
                return;
            }
        }

        public String saveBitmap(boolean saveToFile, int widthPixel, int heightPixel) {
            int i;
            float minDimen = (float) Math.min(widthPixel, heightPixel);
            float max = (float) Math.max(widthPixel, heightPixel);
            float upperScale = ((float) com.app.camera.collagelib.Utility.maxSizeForSave(context, 2048.0f)) / max;
            float scale = upperScale / minDimen;
            Log.e(MirrorActivity.TAG, "upperScale" + upperScale);
            Log.e(MirrorActivity.TAG, "scale" + scale);
            if (mulY > mulX) {
                float f = MirrorActivity.this.mulX;
                float r0 = 1;
                scale = (r0 * scale) / MirrorActivity.this.mulY;
            }
            if (scale <= 0.0f) {
                scale = 1.0f;
            }
            Log.e(MirrorActivity.TAG, "scale" + scale);
            int wP = Math.round(((float) widthPixel) * scale);
            int wH = Math.round(((float) heightPixel) * scale);
            RectF srcRect = this.mirrorModeList[this.currentModeIndex].getSrcRect();
            reset(wP, wH, false);
            int btmWidth = Math.round(MirrorActivity.this.mirrorView.getCurrentMirrorMode().rectTotalArea.width());
            int btmHeight = Math.round(MirrorActivity.this.mirrorView.getCurrentMirrorMode().rectTotalArea.height());
            if (btmWidth % MirrorActivity.INDEX_MIRROR_RATIO == MirrorActivity.INDEX_MIRROR_3D) {
                btmWidth--;
            }
            if (btmHeight % MirrorActivity.INDEX_MIRROR_RATIO == MirrorActivity.INDEX_MIRROR_3D) {
                btmHeight--;
            }
            Bitmap savedBitmap = Bitmap.createBitmap(btmWidth, btmHeight, Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(savedBitmap);
            Matrix matrix = new Matrix();
            matrix.reset();
            Log.e(MirrorActivity.TAG, "btmWidth " + btmWidth);
            Log.e(MirrorActivity.TAG, "btmHeight " + btmHeight);
            matrix.postTranslate(((float) (-(wP - btmWidth))) / 2.0f, ((float) (-(wH - btmHeight))) / 2.0f);
            MirrorMode saveMode = this.mirrorModeList[this.currentModeIndex];
            saveMode.setSrcRect(srcRect);
            if (MirrorActivity.this.filterBitmap == null) {
                drawMode(bitmapCanvas, MirrorActivity.this.sourceBitmap, saveMode, matrix);
            } else {
                drawMode(bitmapCanvas, MirrorActivity.this.filterBitmap, saveMode, matrix);
            }
            if (this.d3Mode && this.d3Bitmap != null) {
                if (!this.d3Bitmap.isRecycled()) {
                    bitmapCanvas.setMatrix(matrix);
                    bitmapCanvas.drawBitmap(this.d3Bitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
                }
            }
            Matrix mat;
            if (MirrorActivity.this.textDataList != null) {
                i = MirrorActivity.INDEX_MIRROR;
                while (true) {
                    if (i >= MirrorActivity.this.textDataList.size()) {
                        break;
                    }
                    mat = new Matrix();
                    mat.set(((TextData) MirrorActivity.this.textDataList.get(i)).imageSaveMatrix);
                    mat.postScale(scale, scale);
                    mat.postTranslate(((float) (-(wP - btmWidth))) / 2.0f, ((float) (-(wH - btmHeight))) / 2.0f);
                    bitmapCanvas.setMatrix(mat);
                    bitmapCanvas.drawText(((TextData) MirrorActivity.this.textDataList.get(i)).message, ((TextData) MirrorActivity.this.textDataList.get(i)).xPos, ((TextData) MirrorActivity.this.textDataList.get(i)).yPos, ((TextData) MirrorActivity.this.textDataList.get(i)).textPaint);
                    i += MirrorActivity.INDEX_MIRROR_3D;
                }
            }
            i = 0;
            while (true) {
                if (i >= MirrorActivity.this.stickerViewContainer.getChildCount()) {
                    break;
                }
                mat = new Matrix();
                StickerView view = (StickerView) MirrorActivity.this.stickerViewContainer.getChildAt(i);
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
                i += MirrorActivity.INDEX_MIRROR_3D;
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
                resultPath = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append(MirrorActivity.this.getString(R.string.directory)).append(twitterUploadFile).append(".jpg").toString();
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
        mirrorView.drawSavedImage = false;
        if (id == R.id.saveImage) {
            SaveImageTask saveImageTask = new SaveImageTask();
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(3);
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
            mulX = 1.0f;
            mulY = 1.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR);
        } else if (id == R.id.button21) {
            mulX = 2.0f;
            mulY = 1.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_3D);
        } else if (id == R.id.button12) {
            mulX = 1.0f;
            mulY = 2.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_RATIO);
        } else if (id == R.id.button32) {
            mulX = 3.0f;
            mulY = 2.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_EFFECT);
        } else if (id == R.id.button23) {
            mulX = 2.0f;
            mulY = 3.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
        } else if (id == R.id.button43) {
            mulX = 4.0f;
            mulY = 3.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_ADJUSTMENT);
        } else if (id == R.id.button34) {
            mulX = 3.0f;
            mulY = 4.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(TAB_SIZE);
        } else if (id == R.id.button45) {
            mulX = 4.0f;
            mulY = 5.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(INDEX_MIRROR_INVISIBLE_VIEW);
        } else if (id == R.id.button57) {
            mulX = 5.0f;
            mulY = 7.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(8);
        } else if (id == R.id.button169) {
            mulX = 16.0f;
            mulY = 9.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(9);
        } else if (id == R.id.button916) {
            mulX = 9.0f;
            mulY = 16.0f;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setRatioButtonBg(10);
        } else if (id == R.id.button_m1) {
            mirrorView.setCurrentMode(INDEX_MIRROR);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR);
        } else if (id == R.id.button_m2) {
            mirrorView.setCurrentMode(INDEX_MIRROR_3D);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_3D);
        } else if (id == R.id.button_m3) {
            mirrorView.setCurrentMode(INDEX_MIRROR_RATIO);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_RATIO);
        } else if (id == R.id.button_m4) {
            mirrorView.setCurrentMode(INDEX_MIRROR_EFFECT);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_EFFECT);
        } else if (id == R.id.button_m5) {
            mirrorView.setCurrentMode(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX);
        } else if (id == R.id.button_m6) {
            mirrorView.setCurrentMode(INDEX_MIRROR_ADJUSTMENT);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_ADJUSTMENT);
        } else if (id == R.id.button_m7) {
            mirrorView.setCurrentMode(TAB_SIZE);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(TAB_SIZE);
        } else if (id == R.id.button_m8) {
            mirrorView.setCurrentMode(INDEX_MIRROR_INVISIBLE_VIEW);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(INDEX_MIRROR_INVISIBLE_VIEW);
        } else if (id == R.id.button_m9) {
            mirrorView.setCurrentMode(8);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(8);
        } else if (id == R.id.button_m10) {
            mirrorView.setCurrentMode(9);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(9);
        } else if (id == R.id.button_m11) {
            mirrorView.setCurrentMode(10);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(10);
        } else if (id == R.id.button_m12) {
            mirrorView.setCurrentMode(11);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(11);
        } else if (id == R.id.button_m13) {
            mirrorView.setCurrentMode(12);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(12);
        } else if (id == R.id.button_m14) {
            mirrorView.setCurrentMode(13);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(13);
        } else if (id == R.id.button_m15) {
            mirrorView.setCurrentMode(14);
            mirrorView.d3Mode = false;
            mirrorView.reset(screenWidthPixels, screenHeightPixels, true);
            setMirrorButtonBg(14);
        } else if (id == R.id.button_mirror_text) {
            addCanvasTextView();
            //clearViewFlipper();
        } else if (id == R.id.button_mirror_sticker) {
            addStickerGalleryFragment();
        } else {
//            effectFragment.myClickHandler(id);
//            if (id == R.id.button_lib_cancel || id == R.id.button_lib_ok) {
//                clearFxAndFrame();
//            }
        }

    }

    private class SaveImageTask extends AsyncTask<Object, Object, Object> {
        ProgressDialog progressDialog;
        String resultPath;
        int saveMode;

        private SaveImageTask() {
            this.saveMode = MirrorActivity.INDEX_MIRROR;
            this.resultPath = null;
        }

        protected Object doInBackground(Object... arg0) {
            if (arg0 != null) {
                this.saveMode = ((Integer) arg0[MirrorActivity.INDEX_MIRROR]).intValue();
            }
            resultPath = mirrorView.saveBitmap(true, screenWidthPixels,screenHeightPixels);
            return null;
        }

        protected void onPreExecute() {
            this.progressDialog = new ProgressDialog(MirrorActivity.this.context);
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
            if (this.saveMode == 0 || this.saveMode == 4) {
                super.onPostExecute(result);
                msg = Toast.makeText(MirrorActivity.this.context, "Image has been saved to the /SD" + MirrorActivity.this.getString(R.string.directory) + " folder.", Toast.LENGTH_SHORT);
                msg.setGravity(17, msg.getXOffset() / MirrorActivity.INDEX_MIRROR_RATIO, msg.getYOffset() / MirrorActivity.INDEX_MIRROR_RATIO);
                msg.show();
                MediaScannerConnection.MediaScannerConnectionClient client = new MyMediaScannerConnectionClient(MirrorActivity.this.getApplicationContext(), new File(this.resultPath), null);
                if (this.saveMode == MirrorActivity.INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
                    MirrorActivity.this.finish();
                }
            } else if (this.saveMode ==1) {
                super.onPostExecute(result);
                try {
                    Intent picMessageIntent = new Intent("android.intent.action.SEND");
                    picMessageIntent.setFlags(268435456);
                    picMessageIntent.setType("image/jpeg");
                    if (this.resultPath != null) {
                        picMessageIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(this.resultPath)));
                        MirrorActivity.this.startActivity(picMessageIntent);
                    }
                } catch (Exception e2) {
                    msg = Toast.makeText(MirrorActivity.this.context, "There is no email app installed on your device to handle this request.", Toast.LENGTH_SHORT);
                    msg.setGravity(17, msg.getXOffset() / MirrorActivity.INDEX_MIRROR_RATIO, msg.getYOffset() / MirrorActivity.INDEX_MIRROR_RATIO);
                    msg.show();
                }
            }else if (this.saveMode == 3) {
                Intent fbIntent = new Intent(context, SaveImageActivity.class);
                if (this.resultPath != null) {
                    boolean z2;
                    fbIntent.putExtra("imagePath", this.resultPath);
//                    fbIntent.putExtra("urlFacebookLike", getString(R.string.facebook_like_url));
//                    fbIntent.putExtra("proVersionUrl", getString(R.string.pro_package));
                    fbIntent.putExtra("folder", getString(R.string.directory));
//                    fbIntent.putExtra("twitter_message", new StringBuilder(String.valueOf(getString(R.string.hashtag_twitter))).append(" ").toString());
                    String str = "should_show_ads";
//                    if (CommonLibrary.isAppPro(context)) {
//                        z2 = false;
//                    } else {
//                        z2 = true;
//                    }
//                    fbIntent.putExtra(str, z2);
                    String str2 = "show_inter_ad";
//                    if (CommonLibrary.isAppPro(context) || !context.getResources().getBoolean(R.bool.showInterstitialAds)) {
//                        z = false;
//                    }
                    fbIntent.putExtra(str2, z);
                    startActivityForResult(fbIntent, MirrorActivity.SAVE_IMAGE_ID);
                }
            }
            MyMediaScannerConnectionClient myMediaScannerConnectionClient = new MyMediaScannerConnectionClient(MirrorActivity.this.getApplicationContext(), new File(this.resultPath), null);
        }
    }


    private void clearFxAndFrame() {
//        int selectedTabIndex = effectFragment.getSelectedTabIndex();
//        if (currentSelectedTabIndex != INDEX_MIRROR_EFFECT && currentSelectedTabIndex != INDEX_MIRROR_INVISIBLE_VIEW_ACTUAL_INDEX) {
//            return;
//        }
//        if (selectedTabIndex == 0 || selectedTabIndex == INDEX_MIRROR_3D) {
//            clearViewFlipper();
//        }
    }

    void addCanvasTextView() {
//        canvasText = new CustomRelativeLayout(context, textDataList, mirrorView.I, new C09753());
//        canvasText.setApplyTextListener(new C09764());
//        showText = false;
//        mirrorView.invalidate();
//        mainLayout.addView(canvasText);
//        ((RelativeLayout) findViewById(R.id.text_view_fragment_container)).bringToFront();
//        fontFragment = new FontFragment();
//        fontFragment.setArguments(new Bundle());
//        getSupportFragmentManager().beginTransaction().add(R.id.text_view_fragment_container, fontFragment, "FONT_FRAGMENT").commit();
//        Log.e(TAG, "add fragment");
//        fontFragment.setFontChoosedListener(fontChoosedListener);
    }

    public void addStickerGalleryFragment() {
        FragmentManager fm = getSupportFragmentManager();
//        galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
//        if (galleryFragment == null) {
//            galleryFragment = new StickerGalleryFragment();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.add(R.id.sticker_grid_fragment_container, galleryFragment, "myStickerFragmentTag");
//            ft.commit();
//            galleryFragment.setGalleryListener(createGalleryListener());
//            Log.e(TAG, "galleryFragment null");
//        } else {
//            Log.e(TAG, "show gallery fragment");
//            getSupportFragmentManager().beginTransaction().show(galleryFragment).commit();
//        }
//        galleryFragment.setTotalImage(stickerViewContainer.getChildCount());
    }

//    StickerGalleryFragment.StickerGalleryListener createGalleryListener() {
//        if (stickerGalleryListener == null) {
//            stickerGalleryListener = new C09775();
//        }
//        return stickerGalleryListener;
//    }

    private void set3dMode(int index) {
        mirrorView.d3Mode = true;
        if (index > 15 && index < 20) {
            mirrorView.setCurrentMode(index);
        } else if (index > 19) {
            mirrorView.setCurrentMode(index - 4);
        } else if (index % INDEX_MIRROR_RATIO == 0) {
            mirrorView.setCurrentMode(INDEX_MIRROR);
        } else {
            mirrorView.setCurrentMode(INDEX_MIRROR_3D);
        }
        mirrorView.reset(screenWidthPixels, screenHeightPixels, false);
        if (Build.VERSION.SDK_INT < 11) {
            if (!(mirrorView.d3Bitmap == null || mirrorView.d3Bitmap.isRecycled())) {
                mirrorView.d3Bitmap.recycle();
            }
            mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), d3resList[index]);
        } else {
            loadInBitmap(d3resList[index]);
        }
        mirrorView.postInvalidate();
        setD3ButtonBg(index);
    }

    @SuppressLint({"NewApi"})
    private void loadInBitmap(int resId) {
        Log.e(TAG, "loadInBitmap");
        Utility.logFreeMemory(MirrorActivity.this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (mirrorView.d3Bitmap == null || mirrorView.d3Bitmap.isRecycled()) {
            options.inJustDecodeBounds = true;
            options.inMutable = true;
            BitmapFactory.decodeResource(getResources(), resId, options);
            mirrorView.d3Bitmap = Bitmap.createBitmap(options.outWidth, options.outHeight, Bitmap.Config.ARGB_8888);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = INDEX_MIRROR_3D;
        options.inBitmap = mirrorView.d3Bitmap;
        try {
            mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            if (!(mirrorView.d3Bitmap == null || mirrorView.d3Bitmap.isRecycled())) {
                mirrorView.d3Bitmap.recycle();
            }
            mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), resId);
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
            ratioButtonArray[i].setBackgroundResource(R.drawable.border_background);
        }
        ratioButtonArray[index].setBackgroundResource(R.drawable.ratio_bg_pressed);
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

//    /* renamed from: paddy.com.lyrebirdstudio.mirror.MirrorActivity.1 */
//    class C09731 implements FontChoosedListener {
//        C09731() {
//        }
//
//        public void onOk(TextData textData) {
//            canvasText.addTextView(textData);
//            getSupportFragmentManager().beginTransaction().remove(fontFragment).commit();
//            Log.e(MirrorActivity.TAG, "onOK called");
//        }
//    }
//
//    /* renamed from: paddy.com.lyrebirdstudio.mirror.MirrorActivity.2 */
//    class C09742 implements StickerViewSelectedListener {
//        C09742() {
//        }
//
//        public void setSelectedView(StickerView stickerView) {
//            stickerView.bringToFront();
//            stickerView.bringToFront();
//            if (Build.VERSION.SDK_INT < 19) {
//                stickerViewContainer.requestLayout();
//            }
//        }
//
//        public void onTouchUp(StickerData data) {
//        }
//    }
//
//    /* renamed from: paddy.com.lyrebirdstudio.mirror.MirrorActivity.3 */
//    class C09753 implements SingleTap {
//        C09753() {
//        }
//
//        public void onSingleTap(TextData textData) {
//            fontFragment = new FontFragment();
//            Bundle arguments = new Bundle();
//            arguments.putSerializable("text_data", textData);
//            fontFragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction().replace(R.id.text_view_fragment_container, fontFragment, "FONT_FRAGMENT").commit();
//            Log.e(MirrorActivity.TAG, "replace fragment");
//            fontFragment.setFontChoosedListener(fontChoosedListener);
//        }
//    }

    /* renamed from: paddy.com.lyrebirdstudio.mirror.MirrorActivity.4 */
    class C09764 implements ApplyTextInterface {
        C09764() {
        }

        public void onOk(ArrayList<TextData> tdList) {
            Iterator it = tdList.iterator();
            while (it.hasNext()) {
                ((TextData) it.next()).setImageSaveMatrix(mirrorView.I);
            }
            textDataList = tdList;
            showText = true;
            if (mainLayout == null) {
                mainLayout = (RelativeLayout) findViewById(R.id.layout_mirror_activity);
            }
            mainLayout.removeView(canvasText);
            mirrorView.postInvalidate();
        }

        public void onCancel() {
            showText = true;
            mainLayout.removeView(canvasText);
            mirrorView.postInvalidate();
        }
    }

    class C09775 implements StickerGalleryListener {
        C09775() {
        }

        public void onGalleryOkSingleImage(int resId) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
            if (removeBitmap == null) {
                removeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.remove_text);
            }
            if (scaleBitmap == null) {
                scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scale_text);
            }
            StickerView stickerView = new StickerView(context, bitmap, null, removeBitmap, scaleBitmap, resId);
            //   stickerView.setStickerViewSelectedListener(stickerViewSelectedListner);
            if (stickerViewContainer == null) {
                stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
            }
            stickerViewContainer.addView(stickerView);
            Utility.logFreeMemory(MirrorActivity.this);
            FragmentManager fm = getSupportFragmentManager();
//            if (galleryFragment == null) {
//                galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
//            }
//            fm.beginTransaction().hide(galleryFragment).commit();
        }

        public void onGalleryOkImageArray(int[] ImageIdList) {
            Bitmap removeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.remove_text);
            Bitmap scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scale_text);
            for (int i = MirrorActivity.INDEX_MIRROR; i < ImageIdList.length; i += MirrorActivity.INDEX_MIRROR_3D) {
                StickerView stickerView = new StickerView(context, BitmapFactory.decodeResource(getResources(), ImageIdList[i]), null, removeBitmap, scaleBitmap, ImageIdList[i]);
                //   stickerView.setStickerViewSelectedListener(stickerViewSelectedListner);
                if (stickerViewContainer == null) {
                    stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
                }
                stickerViewContainer.addView(stickerView);
            }
            FragmentManager fm = getSupportFragmentManager();
//            if (galleryFragment == null) {
//                galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myFragmentTag");
//            }
//            fm.beginTransaction().hide(galleryFragment).commit();
        }

        public void onGalleryCancel() {
            //  getSupportFragmentManager().beginTransaction().hide(galleryFragment).commit();
        }
    }

    /* renamed from: paddy.com.lyrebirdstudio.mirror.MirrorActivity.6 */
//    class C09786 implements BitmapReady {
//        C09786() {
//        }
//
//        public void onBitmapReady(Bitmap bitmap) {
//            filterBitmap = bitmap;
//            mirrorView.postInvalidate();
//        }
//    }

    /* renamed from: paddy.com.lyrebirdstudio.mirror.MirrorActivity.7 */
//    class C09797 implements BuyProVersion {
//        C09797() {
//        }
//
//        public void proVersionCalled() {
//        }
//    }

    /* renamed from: paddy.com.lyrebirdstudio.mirror.MirrorActivity.8 */
//    class C09808 implements RecyclerAdapterIndexChangedListener {
//        C09808() {
//        }
//
//        public void onIndexChanged(int position) {
//            mirrorView.setFrame(position);
//        }
//    }

    public void CloseScreen(View view) {
        showCloseDialog();
    }

    @Override
    public void onBackPressed() {
        showCloseDialog();
    }

}
