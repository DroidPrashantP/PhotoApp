package com.app.paddycameraeditior.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.paddycameraeditior.AppController;
import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.adapters.BackgroundLayoutAdapter;
import com.app.paddycameraeditior.adapters.FilterLayoutAdapter;
import com.app.paddycameraeditior.adapters.FramesLayoutAdapter;
import com.app.paddycameraeditior.adapters.MainLayoutAdapter;
import com.app.paddycameraeditior.adapters.ToolLayoutAdapter;
import com.app.paddycameraeditior.analytics.AnalyticBasic;
import com.app.paddycameraeditior.bitmap.BitmapLoader;
import com.app.paddycameraeditior.bitmap.BitmapProcessing;
import com.app.paddycameraeditior.canvastext.ApplyTextInterface;
import com.app.paddycameraeditior.canvastext.CustomRelativeLayout;
import com.app.paddycameraeditior.canvastext.SingleTap;
import com.app.paddycameraeditior.canvastext.TextData;
import com.app.paddycameraeditior.common_lib.Parameter;
import com.app.paddycameraeditior.fragments.FontFragment;
import com.app.paddycameraeditior.multitouchview.StickerCustomView;
import com.app.paddycameraeditior.sticker.StickerData;
import com.app.paddycameraeditior.sticker.StickerView;
import com.app.paddycameraeditior.sticker.Utility;
import com.app.paddycameraeditior.utils.CommonActivity;
import com.app.paddycameraeditior.utils.Constants;
import com.app.paddycameraeditior.utils.CustomViews.BlurBuilderNormal;
import com.app.paddycameraeditior.utils.CustomViews.MultiTouchListener;
import com.app.paddycameraeditior.utils.CustomViews.RotationGestureDetector;
import com.app.paddycameraeditior.utils.ImageMainFilter;
import com.app.paddycameraeditior.utils.Toaster;
import com.app.paddycameraeditior.utils.UriToUrl;
import com.commit451.nativestackblur.NativeStackBlur;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class PhotoActivity extends FragmentActivity implements View.OnClickListener {

    private static final int STRICKER_RESULT = 1;
    private static final int TEXTVIEW_CALL = 2;
    private static final String TAG = PhotoActivity.class.getName();
    private int source_id;
    private Uri imageUri;
    private String imageUrl;
    private Bitmap originalBitmap = null;
    private Bitmap originalBgBitmap = null;
    private String Originalpath;
    private ImageView background_image;

    private RelativeLayout mMainContainer;
    private LinearLayout rotation_holder;
    private RelativeLayout blurSeekbarLayout;
    private RelativeLayout mFramesLayout;
    private RelativeLayout mFxLayout;
    private RelativeLayout mBackgroundLayout;
    private CustomImageView image_holder;
    private SeekBar mSeekbar;
    private SeekBar mCommonSeekbar;
    private RelativeLayout commonSeekbarLayout;
    private LinearLayout commonSeekbarCancelLayout;
    private LinearLayout commonSeekbarApplyLayout;
    private FrameLayout frameContainer;


    private boolean toolLayoutflag = false;
    private boolean blurLayoutflag = false;
    private boolean frameLayoutFlag = false;
    private boolean backgroundLayoutFlag = false;
    private boolean filterLayoutFlag = false;
    private boolean fxLayoutFlag = false;

    private ArrayList<String> effects;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private int selectedFrame = 0;
    private Matrix matrix = new Matrix();
    private View mFilterLayout;
    private StickerCustomView stickerCustomView;
    private RadioGroup ratioFrameLayout;
    private LinearLayout strickerSelectionView;
    private Bitmap frame;
    private Bitmap tempFrameBitmap;
    private FrameLayout TextContainer;
    ArrayList<TextData> textDataList = new ArrayList<TextData>();
    private Matrix identityMatrix = new Matrix();
    private RelativeLayout mParentLayout;
    private FontFragment.FontChoosedListener fontChoosedListener;
    private CustomRelativeLayout customRelativeLayout;
    private FontFragment fontFragment;
    private Matrix mat;
    private FrameLayout stickerViewContainer;
    private SquareView mSqView;
    private Bitmap sourceBitmap;
    private boolean showText = false;
    //  EffectFragment effectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        findViewByIds();

        if (savedInstanceState == null) {
            source_id = getIntent().getExtras().getInt(Constants.EXTRA_KEY_IMAGE_SOURCE);
            imageUri = getIntent().getData();
            effects = new ArrayList<String>();
            try {
                loadImage();

            } catch (Exception e) {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);

            }
        } else {
            effects = savedInstanceState.getStringArrayList(Constants.KEY_EFFECTS_LIST);
            imageUrl = savedInstanceState.getString(Constants.KEY_URL);
            source_id = savedInstanceState.getInt(Constants.KEY_SOURCE_ID);
            setImage((Bitmap) savedInstanceState.getParcelable(Constants.KEY_BITMAP));
        }

        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        setMainLayout();
        blurSection();
        setFrameLayout();
        setmBackgroundLayoutLayout();
        setRotationsToolsLayout();
        setFilterLayout();
        setFxLayout();

        try {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

        AnalyticBasic.hitGoogleAnalytics(this, PhotoActivity.class.getName());
    }

    private void findViewByIds() {
        stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
        mParentLayout = (RelativeLayout)findViewById(R.id.parentLayout);
        frameContainer = (FrameLayout) findViewById(R.id.frameContainer);
        //image_holder = (CustomImageView) findViewById(R.id.source_image);
        image_holder = new CustomImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        image_holder.setLayoutParams(lp);

        background_image = (ImageView) findViewById(R.id.background_image);
        mMainContainer = (RelativeLayout) findViewById(R.id.container);
        mMainContainer.addView(image_holder);
        image_holder.setOnTouchListener(new MultiTouchListener());
//        textViewMsg1 = (TextView)findViewById(R.id.textViewMoveable1);
        TextContainer = (FrameLayout)findViewById(R.id.frameContainer);

        rotation_holder = (LinearLayout) findViewById(R.id.rotation_holder);
        rotation_holder.setVisibility(View.INVISIBLE);
        blurSeekbarLayout = (RelativeLayout) findViewById(R.id.blurSeekbarLayout);
        mFramesLayout = (RelativeLayout) findViewById(R.id.frameLayout);
        mFxLayout = (RelativeLayout) findViewById(R.id.fxLayout);
        mBackgroundLayout = (RelativeLayout) findViewById(R.id.backgroundLayout);
        strickerSelectionView = (LinearLayout) findViewById(R.id.strickerSelectionView);
        mFilterLayout = (RelativeLayout) findViewById(R.id.filterLayout);

        commonSeekbarLayout = (RelativeLayout) findViewById(R.id.commonSeekbarLayout);
        commonSeekbarCancelLayout = (LinearLayout) findViewById(R.id.cancel);
        commonSeekbarApplyLayout = (LinearLayout) findViewById(R.id.apply);

        commonSeekbarApplyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonSeekbarLayout.setAnimation(slideRightOut);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.VISIBLE);
                mFilterLayout.setAnimation(slideLeftIn);

            }
        });
        commonSeekbarCancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonSeekbarLayout.setAnimation(slideRightOut);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.VISIBLE);
                mFilterLayout.setAnimation(slideLeftIn);
                image_holder.setImageBitmap(originalBitmap);
            }
        });

        fontChoosedListener = new TextApply();
    }

    public void setMainLayout() {
        RecyclerView mainLayoutRecyclerView = (RecyclerView) findViewById(R.id.mainLayout);
        mainLayoutRecyclerView.setHasFixedSize(true);
        mainLayoutRecyclerView.setScrollbarFadingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainLayoutRecyclerView.setLayoutManager(layoutManager);
        MainLayoutAdapter mainLayoutAdapter = new MainLayoutAdapter(this, Constants.CurrentFunction.GALLERY);
        mainLayoutRecyclerView.setAdapter(mainLayoutAdapter);

    }

    public void setRotationsToolsLayout() {
        RecyclerView mainLayoutRecyclerView = (RecyclerView) findViewById(R.id.ToolsLayoutRecyclerView);
        mainLayoutRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainLayoutRecyclerView.setLayoutManager(layoutManager);
        ToolLayoutAdapter toolLayoutAdapter = new ToolLayoutAdapter(this, Constants.CurrentFunction.GALLERY);
        mainLayoutRecyclerView.setAdapter(toolLayoutAdapter);

    }

    public void setFrameLayout() {

        RecyclerView frameRecyclerview = (RecyclerView) findViewById(R.id.frameRecyclerView);
        frameRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        frameRecyclerview.setLayoutManager(layoutManager);
        FramesLayoutAdapter framesLayoutAdapter = new FramesLayoutAdapter(this, Constants.CurrentFunction.GALLERY, Constants.FilterFunction.FRAME);
        frameRecyclerview.setAdapter(framesLayoutAdapter);

    }

    public void setmBackgroundLayoutLayout() {

        RecyclerView backgroundRecyclerView = (RecyclerView) findViewById(R.id.backgroundRecyclerView);
        backgroundRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        backgroundRecyclerView.setLayoutManager(layoutManager);
        BackgroundLayoutAdapter backgroundLayoutAdapter = new BackgroundLayoutAdapter(this, Constants.CurrentFunction.GALLERY);
        backgroundRecyclerView.setAdapter(backgroundLayoutAdapter);
    }

    public void setFilterLayout() {
        RecyclerView filterRecyclerView = (RecyclerView) findViewById(R.id.filterRecyclerView);
        filterRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        filterRecyclerView.setLayoutManager(layoutManager);
        FilterLayoutAdapter mainLayoutAdapter = new FilterLayoutAdapter(this, Constants.CurrentFunction.GALLERY);
        filterRecyclerView.setAdapter(mainLayoutAdapter);

    }

    public void setFxLayout() {
        RecyclerView fxRecyclerView = (RecyclerView) findViewById(R.id.fxRecyclerView);
        fxRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fxRecyclerView.setLayoutManager(layoutManager);
        FramesLayoutAdapter framesLayoutAdapter = new FramesLayoutAdapter(this, Constants.CurrentFunction.GALLERY, Constants.FilterFunction.FX);
        fxRecyclerView.setAdapter(framesLayoutAdapter);
    }

    private void blurSection() {
        mSeekbar = (SeekBar) findViewById(R.id.blurSeekbar);
        final TextView seekBarTextview = (TextView) findViewById(R.id.seekBarProgressText);
        mSeekbar.setMax(25);
        mSeekbar.setProgress(14);
        seekBarTextview.setText("" + 14);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress;
                if (seekBar.getProgress() == 0) {
                    progress = 1;
                } else {
                    progress = seekBar.getProgress();
                }
                seekBarTextview.setText((new StringBuilder()).append(seekBar.getProgress()).toString());
                if (android.os.Build.VERSION.SDK_INT > 17) {
                    Bitmap copyBitmap = originalBgBitmap.copy(originalBgBitmap.getConfig(), true);
                    Bitmap blurredBitmap = NativeStackBlur.process(copyBitmap, progress * 2);
                    background_image.setImageBitmap(blurredBitmap);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                float f = (float) progress;
                seekBarTextview.setText((new StringBuilder()).append((int) f).toString());
            }
        });

    }

    private void loadImage() throws Exception {
        BitmapWorkerTask bitmapWorker = new BitmapWorkerTask();
        bitmapWorker.execute();
    }

    @Override
    public void onClick(View v) {
    }

    public void BackgroundClick(int id, int pos) {
        if (pos == 0) {
            background_image.setImageBitmap(originalBgBitmap);
        } else {
            background_image.setImageResource(id);
        }
    }

    public void FilterSelection(int selectedPosition) {

        if (selectedPosition == 0) {
            commonSeekbarLayout.setVisibility(View.VISIBLE);
            CommonSeekbarSection(Constants.FilterFunction.BRIGHTNESS);
        }
        if (selectedPosition == 1) {
            commonSeekbarLayout.setVisibility(View.VISIBLE);
            CommonSeekbarSection(Constants.FilterFunction.CONTRAST);
        }
        if (selectedPosition == 2) {
            commonSeekbarLayout.setVisibility(View.VISIBLE);
            CommonSeekbarSection(Constants.FilterFunction.SATURATION);
        }
        if (selectedPosition == 3) {
            commonSeekbarLayout.setVisibility(View.VISIBLE);
            CommonSeekbarSection(Constants.FilterFunction.TINT);
        }
        if (selectedPosition == 4) {
            commonSeekbarLayout.setVisibility(View.VISIBLE);
            CommonSeekbarSection(Constants.FilterFunction.BLUR);
        }
        if (selectedPosition == 5) {
//            commonSeekbarLayout.setVisibility(View.VISIBLE);
//            CommonSeekbarSection(Constants.FilterFunction.SHARPEN);
        }
        mFilterLayout.setVisibility(View.GONE);
    }

    public void FxClick(int i, int pos) {
        new setFxAsynctask(pos, originalBitmap,1).execute();
    }

    public void FxSelection(View view) {
        if (view.getId() == R.id.closefx){
            image_holder.setImageBitmap(originalBitmap);
            mFxLayout.setVisibility(View.GONE);
            fxLayoutFlag = false;
        }else if (view.getId() == R.id.savefx){
            mFxLayout.setVisibility(View.GONE);
            fxLayoutFlag = false;
        }
    }

    private class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {
        DisplayMetrics metrics;
        BitmapLoader bitmapLoader;
        ProgressDialog prog;

        public BitmapWorkerTask() {
            metrics = getResources().getDisplayMetrics();
            imageUrl = UriToUrl.get(getApplicationContext(), imageUri);
            bitmapLoader = new BitmapLoader();
            prog = ProgressDialog.show(PhotoActivity.this, "", "Progress...");
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        overridePendingTransition(0, 0);
        super.onStop();
    }


    private Bitmap getCurrentBitmap() {
        try {
            return ((BitmapDrawable) image_holder.getDrawable()).getBitmap();
        } catch (Exception e) {
            return null;
        }
    }

    private void setImage(Bitmap bitmap) {
        try {
            if (bitmap != null) {
                originalBitmap = bitmap;
                originalBgBitmap = bitmap;
                sourceBitmap = bitmap;
                Bitmap copyBitmap = bitmap;
                tempFrameBitmap = originalBitmap;
                image_holder.setImageBitmap(bitmap);
                try {
                    Bitmap blurredBitmap = NativeStackBlur.process(copyBitmap, 30);
                    background_image.setImageBitmap(blurredBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Display display = getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int height = display.getHeight();
//                mSqView = new SquareView(this, width, height);
//                mMainContainer.addView(this.mSqView);

            } else {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        } catch (Exception e) {
            Toaster.make(getApplicationContext(), R.string.error_img_not_found);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.KEY_URL, imageUrl);
        outState.putInt(Constants.KEY_SOURCE_ID, source_id);
        outState.putStringArrayList(Constants.KEY_EFFECTS_LIST, effects);
        outState.putParcelable(Constants.KEY_BITMAP, getCurrentBitmap());
    }

    public void mToolLayoutHandler(int id) {
        if (id == 0) {
            Bitmap bitmap = CommonActivity.RotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 0);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == 1) {
            Bitmap bitmap = CommonActivity.RotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 1);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == 2) {
            Bitmap bitmap = CommonActivity.flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 1);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == 3) {
            Bitmap bitmap = CommonActivity.flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 0);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == 4) {
            float scalingFactor = 1.0f; // scale down to half the size
            image_holder.setScaleX(scalingFactor);
            image_holder.setScaleY(scalingFactor);
            image_holder.setRotation(0);
            image_holder.setX(0);
            image_holder.setY(0);
        }
        if (id == 5) {
            image_holder.setImageBitmap(originalBitmap);
            image_holder.setScaleX(2.5f);
            image_holder.setScaleY(2.5f);
            image_holder.setRotation(0);
            image_holder.setX(0);
            image_holder.setY(0);
        }
    }

    public void MainLayoutContainer(int pos) {
        if (pos == 0) {
            //myViewFlipper.bringToFront();
            if (toolLayoutflag == false) {
                rotation_holder.startAnimation(slideLeftIn);
                rotation_holder.setVisibility(View.VISIBLE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                toolLayoutflag = true;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                filterLayoutFlag= false;
                fxLayoutFlag= false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 1) {
            // myViewFlipper.bringToFront();
            if (blurLayoutflag == false) {
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.VISIBLE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                blurLayoutflag = true;
                toolLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                filterLayoutFlag= false;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 2) {
            try {
                Intent intent = new Intent(this, CropActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
                intent.setData(imageUri);
                startActivityForResult(intent, Constants.CROP_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pos == 3) {
            if (backgroundLayoutFlag == false) {
                mBackgroundLayout.startAnimation(slideRightIn);
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.VISIBLE);
                mFilterLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = true;
                filterLayoutFlag= false;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 4) {
            if (frameLayoutFlag == false) {
                mFramesLayout.startAnimation(slideRightIn);
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.VISIBLE);
                mBackgroundLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = true;
                backgroundLayoutFlag = false;
                filterLayoutFlag= false;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 5) {
            startActivityForResult(new Intent(this, StickerActivity.class), STRICKER_RESULT);
            hideAllLayout();
        }
        if (pos == 6) {
            if (filterLayoutFlag == false) {
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.VISIBLE);
                mFilterLayout.startAnimation(slideRightIn);
                mFxLayout.setVisibility(View.GONE);
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                filterLayoutFlag= true;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 7) {
            if (fxLayoutFlag == false) {
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.VISIBLE);
                mFxLayout.startAnimation(slideRightIn);
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                filterLayoutFlag= false;
                fxLayoutFlag = true;
            } else {
               hideAllLayout();
            }
        }
        if (pos == 8) {
          //  startActivityForResult(new Intent(this, TextViewActivity.class), TEXTVIEW_CALL);
            addCanvasTextView();
            hideAllLayout();
        }
    }

    public void hideAllLayout(){
        rotation_holder.setVisibility(View.INVISIBLE);
        blurSeekbarLayout.setVisibility(View.GONE);
        mFramesLayout.setVisibility(View.GONE);
        mBackgroundLayout.setVisibility(View.GONE);
        mFilterLayout.setVisibility(View.GONE);
        mFxLayout.setVisibility(View.GONE);
        toolLayoutflag = false;
        blurLayoutflag = false;
        frameLayoutFlag = false;
        backgroundLayoutFlag = false;
        filterLayoutFlag= false;
        fxLayoutFlag = false;
    }
    private void CommonSeekbarSection(final String type) {
        final TextView seekbarTextview = (TextView) findViewById(R.id.seekBarProgressText);
        mCommonSeekbar = (SeekBar) findViewById(R.id.commonSeekbar);
        mCommonSeekbar.setProgress(0);
        if (type.equalsIgnoreCase(Constants.FilterFunction.BRIGHTNESS)) {
            mCommonSeekbar.setMax(255);
        }
        if (type.equalsIgnoreCase(Constants.FilterFunction.CONTRAST)) {
            mCommonSeekbar.setMax(255);
        }
        if (type.equalsIgnoreCase(Constants.FilterFunction.BLUR)) {
            mCommonSeekbar.setMax(25);
        }
        if (type.equalsIgnoreCase(Constants.FilterFunction.TINT)) {
            mCommonSeekbar.setMax(100);
        }
        if (type.equalsIgnoreCase(Constants.FilterFunction.SHARPEN)) {
            mCommonSeekbar.setMax(100);
        }

        mCommonSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress;
                if (seekBar.getProgress() == 0) {
                    progress = 1;
                } else {
                    progress = seekBar.getProgress();
                }
                seekbarTextview.setVisibility(View.GONE);
                if (type.equalsIgnoreCase(Constants.FilterFunction.BLUR)) {
                    Bitmap copyBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);
                    Bitmap blurredBitmap = NativeStackBlur.process(copyBitmap, progress * 2);
                    image_holder.setImageBitmap(blurredBitmap);
                } else {
                    new FilterBackGroundTask(type, originalBitmap, progress).execute();
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekbarTextview.setVisibility(View.VISIBLE);
                seekbarTextview.setText((new StringBuilder()).append(seekBar.getProgress()).toString());

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                seekbarTextview.setVisibility(View.VISIBLE);
                seekbarTextview.setText((new StringBuilder()).append(seekBar.getProgress()).toString());

            }
        });

    }
    @SuppressLint("NewApi")
    public void FrameClick(int id, int pos) {
        if (pos == 0){
            image_holder.setImageBitmap(originalBitmap);
            mFramesLayout.setVisibility(View.GONE);
            frameLayoutFlag = false;
        }else {
            selectedFrame = id;
            frame = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), selectedFrame));
            frame = Bitmap.createScaledBitmap(frame, originalBitmap.getWidth(), originalBitmap.getHeight(), false);
            new setFrameBacktask().execute();
        }
    }

    @SuppressLint("NewApi")
    public void FrameSelection(View view) {
        if (view.getId() == R.id.closeFrame) {
            image_holder.setImageBitmap(originalBitmap);
            mFramesLayout.setVisibility(View.GONE);
            frameLayoutFlag = false;
        }
        if (view.getId() == R.id.saveFrame) {
            mFramesLayout.setVisibility(View.GONE);
            frameLayoutFlag = false;
            originalBitmap = tempFrameBitmap;
        }
    }

    public void SaveImageToGallery(View view) {
        Bitmap bitmap = CommonActivity.getViewBitmap(mMainContainer);
        Originalpath = CommonActivity.SaveNewImage(this, bitmap);

        if (Originalpath != null) {
            Intent intent = new Intent(this, SaveImageActivity.class);
            intent.putExtra("message", "Photo Resize App");
            intent.putExtra("imagePath", Originalpath);
            startActivity(intent);
        }
    }

    public void CloseScreen(View view) {
        showCloseDialog();
    }

    @Override
    public void onBackPressed() {
        showCloseDialog();
    }

    public void showCloseDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to save image?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                Bitmap bitmap = CommonActivity.getViewBitmap(mMainContainer);
                Originalpath = CommonActivity.SaveNewImage(PhotoActivity.this, bitmap);
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

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CROP_IMAGE) {
            try {
                if (resultCode == RESULT_OK) {
                    // get cropped bitmap from Application
                    Bitmap cropped = ((AppController) getApplication()).cropped;
                    image_holder.setImageBitmap(cropped);
                } else {

                }
            } catch (Exception e) {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }else if (requestCode == STRICKER_RESULT) {
            try {
                if (resultCode == RESULT_OK) {
                    int resId = data.getExtras().getInt("resID");
                    stickerCustomView = new StickerCustomView(PhotoActivity.this);
                    stickerCustomView.addDrawable(resId, PhotoActivity.this);
                    mMainContainer.addView(stickerCustomView);
                    stickerCustomView.loadImages(this);
                    strickerSelectionView.setVisibility(View.VISIBLE);
                } else {
                    Log.e("Resp", "error");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }else if (requestCode == TEXTVIEW_CALL) {
            try {
                if (resultCode == RESULT_OK) {
                    String text = data.getExtras().getString(Constants.Bundle.TEXT);
                    int colorCode = data.getExtras().getInt(Constants.Bundle.COLOR_CODE);

                    TextView usertext = new TextView(this);
                    usertext.setText(text);
                    usertext.setTextSize(14);
                    usertext.setTextColor(colorCode);
                    usertext.setBackgroundColor(Color.TRANSPARENT);

                    Bitmap textBitmap;

                    Log.e("Width", ""+usertext.getWidth());
                    Log.e("Height", ""+usertext.getHeight());

                    textBitmap = Bitmap.createBitmap(200, 50, Bitmap.Config.ARGB_8888);
                    Canvas c = new Canvas(textBitmap);
                    usertext.layout(200, 200, 300, 400);
                    usertext.draw(c);
                    // create imageview in layout file and declare here

                  //  ImageView iv = (ImageView) findViewById(R.id.source_image);
                    CustomImageView customImageView = new CustomImageView(this);
                    customImageView.setImageBitmap(textBitmap);
                    customImageView.setOnTouchListener(new MultiTouchListener());

                    mMainContainer.addView(customImageView);
                    image_holder.setImageBitmap(originalBitmap);

                } else {
                    Log.e("Resp", "error");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
    }


    public void ClearStricker(View view) {
        stickerCustomView.ClearSticker(strickerSelectionView);

    }

    public void SetStricker(View view) {
        stickerCustomView.ApplySticker();
        strickerSelectionView.setVisibility(View.GONE);
    }
    private class FilterBackGroundTask extends AsyncTask<Void, Void, Bitmap> {
        private String type;
        private Bitmap filterBitmap;
        private ProgressDialog progressDialog;
        private int progress;

        public FilterBackGroundTask(String type, Bitmap bitmap, int prog) {
            this.type = type;
            filterBitmap = bitmap;
            progress = prog;
            progressDialog = ProgressDialog.show(PhotoActivity.this, "", "Loading...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap;
            if (type.equalsIgnoreCase(Constants.FilterFunction.BRIGHTNESS)) {
                Bitmap copyBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);
                return bitmap = ImageMainFilter.doBrightness(copyBitmap, progress);
            }
            if (type.equalsIgnoreCase(Constants.FilterFunction.CONTRAST)) {
                return bitmap = ImageMainFilter.createContrast(originalBitmap, progress);
            }
            if (type.equalsIgnoreCase(Constants.FilterFunction.SATURATION)) {
                return bitmap = ImageMainFilter.applySaturationFilter(originalBitmap, progress);
            }

            if (type.equalsIgnoreCase(Constants.FilterFunction.TINT)) {
                return bitmap = ImageMainFilter.tintImage(originalBitmap, progress);
            }

            if (type.equalsIgnoreCase(Constants.FilterFunction.SHARPEN)) {
                return bitmap = ImageMainFilter.sharpen(originalBitmap, progress);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            image_holder.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    }

    public class setFrameBacktask extends AsyncTask<Void, Void, Void> {
        ProgressDialog prog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            prog =  ProgressDialog.show(PhotoActivity.this, "", "Progress...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            tempFrameBitmap = setFrame(originalBitmap);
            Log.i("temp", "widht : "+String.valueOf(tempFrameBitmap.getWidth())+" Height : "+String.valueOf(tempFrameBitmap.getHeight()));
            return null;
        }

        @Override
        protected void onPostExecute(Void res){
            super.onPostExecute(res);
            image_holder.setImageBitmap(tempFrameBitmap);
            prog.dismiss();
            Log.i("setFrame", "Complete");
        }
    }

    public class setFxAsynctask extends AsyncTask<Void, Void, Bitmap> {
        private int type;
        private Bitmap filterBitmap;
        private ProgressDialog progressDialog;
        private int progress;

        public setFxAsynctask(int type, Bitmap bitmap, int prog) {
            this.type = type;
            filterBitmap = bitmap;
            progress = prog;
            progressDialog = ProgressDialog.show(PhotoActivity.this, "", "Loading...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            Bitmap copyBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);
            switch (type){
                case 0:
                    bitmap = originalBitmap;
                    break;
                case 1:
                    bitmap = BitmapProcessing.sepia(copyBitmap);
                    break;
                case 2:
                    bitmap = BitmapProcessing.tint(copyBitmap, 0xFF1e9a8d);
                    break;
                case 3:
                    bitmap = BitmapProcessing.hue(copyBitmap, (float) 100);
                    break;
                case 4:
                    bitmap = BitmapProcessing.tint(copyBitmap, 0xFF8D7742);
                    break;
                case 5:
                    bitmap = BitmapProcessing.hue(copyBitmap, (float) 200);
                    break;
                case 6:
                    bitmap = BitmapProcessing.vignette(copyBitmap);
                    break;
                case 7:
                    bitmap = BitmapProcessing.hue(copyBitmap, (float) 300);
                    break;
                case 8:
                    bitmap = BitmapProcessing.saturation(copyBitmap, 180);
                    break;
                case 9:
                    bitmap = BitmapProcessing.doGreyscale(copyBitmap);
                    break;
                case 10:
                    bitmap = BitmapProcessing.tint(copyBitmap,0xFFFFC107);
                    break;
                case 11:
                    bitmap = BitmapProcessing.hue(copyBitmap, (float) 180);
                    break;
                case 12:
                    bitmap = BitmapProcessing.saturation(copyBitmap, 150);
                    break;
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            image_holder.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    }

    public Bitmap setFrame(Bitmap bit){
        Bitmap bitmap = bit.copy(Bitmap.Config.ARGB_8888, true);
        for (int i=0;i<frame.getWidth();i++){
            for(int j=0;j<frame.getHeight();j++){
                if (Color.alpha(frame.getPixel(i, j))!=0){
                    bitmap.setPixel(i, j, frame.getPixel(i, j));
                }
            }
        }
        frame.recycle();
        return bitmap;
    }

    public void addCanvasTextView() {

         customRelativeLayout= new CustomRelativeLayout(this, this.textDataList, identityMatrix, new SingleTap() {
            public void onSingleTap(TextData textData) {
                fontFragment = new FontFragment();
                Bundle arguments = new Bundle();
                arguments.putSerializable("text_data", textData);
                fontFragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.parentLayout, fontFragment, "FONT_FRAGMENT").commit();
                fontFragment.setFontChoosedListener(fontChoosedListener);
            }
        });

        mParentLayout.addView(customRelativeLayout);
        fontFragment = new FontFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("text_data", new TextData());
        fontFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.parentLayout, fontFragment, "FONT_FRAGMENT").commit();
        fontFragment.setFontChoosedListener(fontChoosedListener);
        customRelativeLayout.setApplyTextListener(new ApplyTextInterface() {
            @Override
            public void onCancel() {
                mParentLayout.removeView(customRelativeLayout);
               // mSqView.postInvalidate();
            }

            @Override
            public void onOk(ArrayList<TextData> arrayList) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((TextData) it.next()).setImageSaveMatrix(identityMatrix);
                }
                textDataList = arrayList;
                showText = true;
                if (mParentLayout == null) {
                   // mParentLayout = (RelativeLayout) findViewById(R.id.nocrop_main_layout);
                }
                mParentLayout.removeView(customRelativeLayout);
                mParentLayout.postInvalidate();
            }
        });
    }

    /* renamed from: paddy.com.lyrebirdstudio.instasquare.lib.SquareActivity.1 */
    class TextApply implements FontFragment.FontChoosedListener {
        TextApply() {
        }

        public void onOk(TextData textData) {
            customRelativeLayout.addTextView(textData);
            getSupportFragmentManager().beginTransaction().remove(fontFragment).commit();
            Log.e(TAG, "onOK called");
        }
    }

        class SquareView extends View {
        public static final int BACKGROUND_BLUR = 1;
        public static final int BACKGROUND_PATTERN = 0;
        private static final int INVALID_POINTER_ID = 1;
        public static final int PATTERN_SENTINEL = -1;
        private static final int UPPER_SIZE_LIMIT = 2048;
        int backgroundMode;
        float bitmapHeight;
        Matrix bitmapMatrix;
        float bitmapWidth;
        Bitmap blurBitmap;
        Matrix blurBitmapMatrix;
        BlurBuilderNormal blurBuilderNormal;
        private int blurRadius;
        PointF centerOriginal;
        Paint dashPaint;
        Path dashPathHorizontal;
        Path dashPathHorizontalTemp;
        Path dashPathVertical;
        Path dashPathVerticalTemp;
        float epsilon;
        float[] f509f;
        Bitmap filterBitmap;
        float finalAngle;
        Paint grayPaint;
        Matrix identityMatrix;
        Matrix inverseMatrix;
        boolean isOrthogonal;
        private int mActivePointerId;
        float mLastTouchX;
        float mLastTouchY;
        private RotationGestureDetector mRotationDetector;
        float mScaleFactor;
        int offsetX;
        int offsetY;
        Paint paint;
        Parameter parameter;
        Bitmap patternBitmap;
        Paint patternPaint;
        Paint pointPaint;
        RotationGestureDetector.OnRotationGestureListener rotateListener;
        int screenHeight;
        int screenWidth;
        Matrix textMatrix;
        float[] values;
        int viewHeight;
        int viewWidth;
        public boolean showText;
        private ScaleGestureDetector mScaleDetector;

        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            private ScaleListener() {
            }

            public boolean onScale(ScaleGestureDetector detector) {
                SquareView.this.mScaleFactor = detector.getScaleFactor();
                detector.isInProgress();
                SquareView.this.mScaleFactor = Math.max(0.1f, Math.min(SquareView.this.mScaleFactor, 5.0f));
                PointF center = SquareView.this.getCenterOfImage();
                SquareView.this.bitmapMatrix.postScale(SquareView.this.mScaleFactor, SquareView.this.mScaleFactor, center.x, center.y);
                SquareView.this.invalidate();
                return true;
            }
        }

        /* renamed from: paddy.com.lyrebirdstudio.instasquare.lib.SquareActivity.SquareView.1 */
        class C09611 implements RotationGestureDetector.OnRotationGestureListener {
            C09611() {
            }

            public void OnRotation(RotationGestureDetector rotationDetector) {
                float angle = rotationDetector.getAngle();
                float rotation = SquareView.this.getMatrixRotation(SquareView.this.bitmapMatrix);
                if ((rotation == 0.0f || rotation == 90.0f || rotation == 180.0f || rotation == -180.0f || rotation == -90.0f) && Math.abs(SquareView.this.finalAngle - angle) < SquareView.this.epsilon) {
                    SquareView.this.isOrthogonal = true;
                    return;
                }
                if (Math.abs((rotation - SquareView.this.finalAngle) + angle) < SquareView.this.epsilon) {
                    angle = SquareView.this.finalAngle - rotation;
                    SquareView.this.isOrthogonal = true;
                } else if (Math.abs(90.0f - ((rotation - SquareView.this.finalAngle) + angle)) < SquareView.this.epsilon) {
                    angle = (SquareView.this.finalAngle + 90.0f) - rotation;
                    SquareView.this.isOrthogonal = true;
                } else if (Math.abs(180.0f - ((rotation - SquareView.this.finalAngle) + angle)) < SquareView.this.epsilon) {
                    angle = (SquareView.this.finalAngle + 180.0f) - rotation;
                    SquareView.this.isOrthogonal = true;
                } else if (Math.abs(-180.0f - ((rotation - SquareView.this.finalAngle) + angle)) < SquareView.this.epsilon) {
                    angle = (SquareView.this.finalAngle - 0.024902344f) - rotation;
                    SquareView.this.isOrthogonal = true;
                } else if (Math.abs(-90.0f - ((rotation - SquareView.this.finalAngle) + angle)) < SquareView.this.epsilon) {
                    angle = (SquareView.this.finalAngle - 0.049804688f) - rotation;
                    SquareView.this.isOrthogonal = true;
                } else {
                    SquareView.this.isOrthogonal = false;
                }
                PointF center = SquareView.this.getCenterOfImage();
                SquareView.this.bitmapMatrix.postRotate(SquareView.this.finalAngle - angle, center.x, center.y);
                SquareView.this.finalAngle = angle;
                SquareView.this.invalidate();
            }
        }

        public SquareView(Context context, int w, int h) {
            super(context);
            this.identityMatrix = new Matrix();
            this.textMatrix = new Matrix();
            this.backgroundMode = BACKGROUND_PATTERN;
            this.dashPaint = new Paint();
            this.dashPathVerticalTemp = new Path();
            this.dashPathHorizontalTemp = new Path();
            this.isOrthogonal = false;
            this.blurRadius = 14;
            this.inverseMatrix = new Matrix();
            this.f509f = new float[SquareActivity.TAB_INDEX_SQUARE_BLUR];
            this.centerOriginal = new PointF();
            this.mActivePointerId = INVALID_POINTER_ID;
            this.values = new float[9];
            this.mScaleFactor = 1.0f;
            this.finalAngle = 0.0f;
            this.epsilon = 4.0f;
            this.rotateListener = new C09611();
            this.screenWidth = w;
            this.screenHeight = h;
            this.paint = new Paint();
            this.bitmapMatrix = new Matrix();
            //   this.paint.setColor(getColor(R));
            int min = Math.min(w, h);
            this.viewWidth = min;
            this.viewHeight = min;
            this.offsetX = Math.abs(w - this.viewWidth) / SquareActivity.TAB_INDEX_SQUARE_BLUR;
            this.offsetY = Math.abs(h - this.viewHeight) / SquareActivity.TAB_INDEX_SQUARE_BLUR;
            new BitmapFactory.Options().inPreferredConfig = Bitmap.Config.ARGB_8888;
            this.bitmapWidth = (float) originalBitmap.getWidth();
            this.bitmapHeight = (float) originalBitmap.getHeight();
            float bitmapScale = Math.min(((float) this.viewWidth) / this.bitmapWidth, ((float) this.viewHeight) / this.bitmapHeight);
            float bitmapOffsetX = ((float) this.offsetX) + ((((float) this.viewWidth) - (this.bitmapWidth * bitmapScale)) / 2.0f);
            float bitmapOffsetY = ((float) this.offsetY) + ((((float) this.viewHeight) - (this.bitmapHeight * bitmapScale)) / 2.0f);
            this.bitmapMatrix.postScale(bitmapScale, bitmapScale);
            this.bitmapMatrix.postTranslate(bitmapOffsetX, bitmapOffsetY);
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            this.mRotationDetector = new RotationGestureDetector(this.rotateListener);
            this.grayPaint = new Paint();
            // this.grayPaint.setColor(-12303292);
            this.pointPaint = new Paint();
            this.pointPaint.setColor(InputDeviceCompat.SOURCE_ANY);
            this.pointPaint.setStrokeWidth(20.0f);
            this.blurBitmapMatrix = new Matrix();
            this.patternPaint = new Paint(INVALID_POINTER_ID);
            this.patternPaint.setColor(PATTERN_SENTINEL);
            //   this.dashPaint.setColor(-7829368);
            this.dashPaint.setStyle(Paint.Style.STROKE);
            float strokeW = ((float) this.screenWidth) / 120.0f;
            if (strokeW <= 0.0f) {
                strokeW = 5.0f;
            }
            this.dashPaint.setStrokeWidth(strokeW);
            Paint paint = this.dashPaint;
            float[] fArr = new float[SquareActivity.TAB_INDEX_SQUARE_BLUR];
            fArr[BACKGROUND_PATTERN] = strokeW;
            fArr[INVALID_POINTER_ID] = strokeW;
            paint.setPathEffect(new DashPathEffect(fArr, 0.0f));
            this.dashPathVertical = new Path();
            this.dashPathHorizontal = new Path();
            setPathPositions();
        }

        private void setPathPositions() {
            this.dashPathVertical.reset();
            this.dashPathHorizontal.reset();
            this.dashPathVertical.moveTo(this.bitmapWidth / 2.0f, (-this.bitmapHeight) / 5.0f);
            this.dashPathVertical.lineTo(this.bitmapWidth / 2.0f, (this.bitmapHeight * 6.0f) / 5.0f);
            this.dashPathHorizontal.moveTo((-this.bitmapWidth) / 5.0f, this.bitmapHeight / 2.0f);
            this.dashPathHorizontal.lineTo((this.bitmapWidth * 6.0f) / 5.0f, this.bitmapHeight / 2.0f);
        }

        @Override
        public void onDraw(Canvas canvas) {
            if (this.backgroundMode == 0) {
                canvas.drawRect((float) this.offsetX, (float) this.offsetY, (float) (this.offsetX + this.viewWidth), (float) (this.offsetY + this.viewHeight), this.patternPaint);
            }
            if (!(this.blurBitmap == null || this.blurBitmap.isRecycled() || this.backgroundMode != INVALID_POINTER_ID)) {
                canvas.drawBitmap(this.blurBitmap, this.blurBitmapMatrix, this.paint);
            }
            canvas.drawBitmap(originalBitmap, this.bitmapMatrix, this.paint);
            if (!(this.filterBitmap == null || this.filterBitmap.isRecycled())) {
                canvas.drawBitmap(this.filterBitmap, this.bitmapMatrix, this.paint);
            }
            if (this.isOrthogonal) {
                this.dashPathVertical.transform(this.bitmapMatrix, this.dashPathVerticalTemp);
                this.dashPathHorizontal.transform(this.bitmapMatrix, this.dashPathHorizontalTemp);
                canvas.drawPath(this.dashPathVerticalTemp, this.dashPaint);
                canvas.drawPath(this.dashPathHorizontalTemp, this.dashPaint);
            }
            if (showText) {
                for (int i = 0 ; i < textDataList.size(); i++) {
                    textMatrix.set((textDataList.get(i)).imageSaveMatrix);
                    canvas.setMatrix(this.textMatrix);
                    canvas.drawText((textDataList.get(i)).message, (textDataList.get(i)).xPos, (textDataList.get(i)).yPos, ( textDataList.get(i)).textPaint);
                    canvas.setMatrix(this.identityMatrix);
                }
            }
            if (this.offsetX == 0) {
                canvas.drawRect(0.0f, 0.0f, (float) this.screenWidth, (float) this.offsetY, this.grayPaint);
                canvas.drawRect((float) this.offsetX, (float) (this.offsetY + this.viewHeight), (float) this.screenWidth, (float) this.screenHeight, this.grayPaint);
            } else if (this.offsetY == 0) {
                canvas.drawRect(0.0f, 0.0f, (float) this.offsetX, (float) this.screenHeight, this.grayPaint);
                canvas.drawRect((float) (this.offsetX + this.viewHeight), (float) this.offsetY, (float) this.screenWidth, (float) this.screenHeight, this.grayPaint);
            }
        }

        private String saveBitmap() {
            int i;
            float max = (float) Math.max(this.viewHeight, this.viewWidth);
            float btmScale = ((float) Utility.maxSizeForSave(PhotoActivity.this, 2048.0f)) / max;
            int newBtmWidth = (int) (((float) this.viewWidth) * btmScale);
            int newBtmHeight = (int) (((float) this.viewHeight) * btmScale);
            if (newBtmWidth <= 0) {
                newBtmWidth = this.viewWidth;
                Log.e(TAG, "newBtmWidth");
            }
            if (newBtmHeight <= 0) {
                newBtmHeight = this.viewHeight;
                Log.e(TAG, "newBtmHeight");
            }
            Bitmap savedBitmap = Bitmap.createBitmap(newBtmWidth, newBtmHeight, Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(savedBitmap);
            Matrix sizeMat = new Matrix();
            sizeMat.reset();
            sizeMat.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
            sizeMat.postScale(btmScale, btmScale);
            bitmapCanvas.setMatrix(sizeMat);
            if (this.backgroundMode == 0) {
                bitmapCanvas.drawRect((float) this.offsetX, (float) this.offsetY, (float) (this.offsetX + this.viewWidth), (float) (this.offsetY + this.viewHeight), this.patternPaint);
            }
            if (!(this.blurBitmap == null || this.blurBitmap.isRecycled() || this.backgroundMode != INVALID_POINTER_ID)) {
                bitmapCanvas.drawBitmap(this.blurBitmap, this.blurBitmapMatrix, this.paint);
            }
            bitmapCanvas.drawBitmap(originalBitmap, this.bitmapMatrix, this.paint);
            if (!(this.filterBitmap == null || this.filterBitmap.isRecycled())) {
                bitmapCanvas.drawBitmap(this.filterBitmap, this.bitmapMatrix, this.paint);
            }
            if (textDataList != null) {
                for (i = BACKGROUND_PATTERN; i < textDataList.size(); i += INVALID_POINTER_ID) {
                    Matrix mat = new Matrix();
                    mat.set(((TextData) textDataList.get(i)).imageSaveMatrix);
                    mat.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
                    mat.postScale(btmScale, btmScale);
                    bitmapCanvas.setMatrix(mat);
                    bitmapCanvas.drawText(((TextData) textDataList.get(i)).message, ((TextData) textDataList.get(i)).xPos, ((TextData) textDataList.get(i)).yPos, ((TextData) textDataList.get(i)).textPaint);
                }
            }
            for (i = BACKGROUND_PATTERN; i < stickerViewContainer.getChildCount(); i += INVALID_POINTER_ID) {
                mat = new Matrix();
                StickerView view = (StickerView) stickerViewContainer.getChildAt(i);
                StickerData data = view.getStickerData();
                mat.set(data.getCanvasMatrix());
                mat.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
                mat.postScale(btmScale, btmScale);
                bitmapCanvas.setMatrix(mat);
                if (!(view.stickerBitmap == null || view.stickerBitmap.isRecycled())) {
                    bitmapCanvas.drawBitmap(view.stickerBitmap, data.xPos, data.yPos, view.paint);
                }
            }
            String resultPath = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append(getString(R.string.directory)).append(String.valueOf(System.currentTimeMillis())).append(".jpg").toString();
            new File(resultPath).getParentFile().mkdirs();
            try {
                OutputStream out = new FileOutputStream(resultPath);
                savedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            savedBitmap.recycle();
            return resultPath;
        }

        void setPatternPaint(int resId) {
            if (this.patternPaint == null) {
                this.patternPaint = new Paint(INVALID_POINTER_ID);
                this.patternPaint.setColor(PATTERN_SENTINEL);
            }
            if (resId == PATTERN_SENTINEL) {
                this.patternPaint.setShader(null);
                this.patternPaint.setColor(PATTERN_SENTINEL);
                postInvalidate();
                return;
            }
            this.patternBitmap = BitmapFactory.decodeResource(getResources(), resId);
            this.patternPaint.setShader(new BitmapShader(this.patternBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            postInvalidate();
        }

        void setPatternPaintColor(int color) {
            if (this.patternPaint == null) {
                this.patternPaint = new Paint(INVALID_POINTER_ID);
            }
            this.patternPaint.setShader(null);
            this.patternPaint.setColor(color);
            postInvalidate();
        }

        public void setBlurBitmap(int radius) {
            if (this.blurBuilderNormal == null) {
                this.blurBuilderNormal = new BlurBuilderNormal();
            }
            this.backgroundMode = INVALID_POINTER_ID;
            this.blurBitmap = this.blurBuilderNormal.createBlurBitmapNDK(originalBitmap, radius);
            this.blurRadius = this.blurBuilderNormal.getBlur();
            setMatrixCenter(this.blurBitmapMatrix, (float) this.blurBitmap.getWidth(), (float) this.blurBitmap.getHeight());
            postInvalidate();
        }

        public void setCropBitmap(int left, int top, int right, int bottom) {
            Bitmap localCropBtm = originalBitmap;
            if (((float) right) > this.bitmapWidth) {
                right = (int) this.bitmapWidth;
            }
            if (((float) bottom) > this.bitmapHeight) {
                bottom = (int) this.bitmapHeight;
            }
            if (Build.VERSION.SDK_INT < 12) {
                originalBitmap = BlurBuilderNormal.createCroppedBitmap(localCropBtm, left, top, right - left, bottom - top, false);
            } else {
                originalBitmap = Bitmap.createBitmap(localCropBtm, left, top, right - left, bottom - top);
            }
//            effectFragment.setBitmap(originalBitmap);
//            effectFragment.execQueue();
            if (localCropBtm != originalBitmap) {
                localCropBtm.recycle();
            }
            this.bitmapHeight = (float) originalBitmap.getHeight();
            this.bitmapWidth = (float) originalBitmap.getWidth();
            setPathPositions();
            setScaleMatrix(BACKGROUND_PATTERN);
        }

        public void setScaleMatrix(int mode) {
            PointF centerOfImage = getCenterOfImage();
            if (mode == 0) {
                this.bitmapMatrix.reset();
                float bitmapScale = Math.min(((float) this.viewWidth) / this.bitmapWidth, ((float) this.viewHeight) / this.bitmapHeight);
                float bitmapOffsetX = ((float) this.offsetX) + ((((float) this.viewWidth) - (this.bitmapWidth * bitmapScale)) / 2.0f);
                float bitmapOffsetY = ((float) this.offsetY) + ((((float) this.viewHeight) - (this.bitmapHeight * bitmapScale)) / 2.0f);
                this.bitmapMatrix.postScale(bitmapScale, bitmapScale);
                this.bitmapMatrix.postTranslate(bitmapOffsetX, bitmapOffsetY);
            } else if (mode == INVALID_POINTER_ID) {
                setMatrixCenter(this.bitmapMatrix, this.bitmapWidth, this.bitmapHeight);
            } else if (mode == SquareActivity.TAB_INDEX_SQUARE_FRAME) {
                this.bitmapMatrix.postRotate(-90.0f, centerOfImage.x, centerOfImage.y);
            } else if (mode == SquareActivity.TAB_INDEX_SQUARE_BLUR) {
                this.bitmapMatrix.postRotate(90.0f, centerOfImage.x, centerOfImage.y);
            } else if (mode == SquareActivity.TAB_INDEX_SQUARE_FX) {
                this.bitmapMatrix.postScale(-1.0f, 1.0f, centerOfImage.x, centerOfImage.y);
            } else if (mode == SquareActivity.TAB_INDEX_SQUARE_ADJ) {
                this.bitmapMatrix.postScale(1.0f, -1.0f, centerOfImage.x, centerOfImage.y);
            }
            postInvalidate();
        }

        void setMatrixCenter(Matrix matrix, float btmwidth, float btmheight) {
            matrix.reset();
            float bitmapScale = Math.max(((float) this.viewWidth) / btmwidth, ((float) this.viewHeight) / btmheight);
            float bitmapOffsetX = ((float) this.offsetX) + ((((float) this.viewWidth) - (bitmapScale * btmwidth)) / 2.0f);
            float bitmapOffsetY = ((float) this.offsetY) + ((((float) this.viewHeight) - (bitmapScale * btmheight)) / 2.0f);
            matrix.postScale(bitmapScale, bitmapScale);
            matrix.postTranslate(bitmapOffsetX, bitmapOffsetY);
        }

        PointF getCenterOfImage() {
            if (this.centerOriginal == null) {
                this.centerOriginal = new PointF();
            }
            if (this.f509f == null) {
                this.f509f = new float[SquareActivity.TAB_INDEX_SQUARE_BLUR];
            }
            float y = this.bitmapHeight / 2.0f;
            this.f509f[BACKGROUND_PATTERN] = this.bitmapWidth / 2.0f;
            this.f509f[INVALID_POINTER_ID] = y;
            this.bitmapMatrix.mapPoints(this.f509f);
            this.centerOriginal.set(this.f509f[BACKGROUND_PATTERN], this.f509f[INVALID_POINTER_ID]);
            return this.centerOriginal;
        }

        public boolean onTouchEvent(MotionEvent ev) {
            int newPointerIndex = BACKGROUND_PATTERN;
            mScaleDetector.onTouchEvent(ev);
            this.mRotationDetector.onTouchEvent(ev);
            int action = ev.getAction();
            float x;
            float y;
            int pointerIndex;
            switch (action & MotionEventCompat.ACTION_MASK) {
                case BACKGROUND_PATTERN /*0*/:
                    x = ev.getX();
                    y = ev.getY();
                    if (x >= ((float) this.offsetX) && y >= ((float) this.offsetY) && x <= ((float) (this.offsetX + this.viewWidth)) && y <= ((float) (this.offsetY + this.viewHeight))) {
                        this.mLastTouchX = x;
                        this.mLastTouchY = y;
                        this.mActivePointerId = ev.getPointerId(BACKGROUND_PATTERN);
                        break;
                    }
                    return false;
                case INVALID_POINTER_ID /*1*/:
                    this.isOrthogonal = false;
                    this.mActivePointerId = INVALID_POINTER_ID;
                    break;
                case SquareActivity.TAB_INDEX_SQUARE_BLUR /*2*/:
                    pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                    x = ev.getX(pointerIndex);
                    y = ev.getY(pointerIndex);
                    this.bitmapMatrix.postTranslate(x - this.mLastTouchX, y - this.mLastTouchY);
                    this.mLastTouchX = x;
                    this.mLastTouchY = y;
                    break;
                case SquareActivity.TAB_INDEX_SQUARE_FRAME /*3*/:
                    this.isOrthogonal = false;
                    this.mActivePointerId = INVALID_POINTER_ID;
                    break;
                case SquareActivity.TAB_SIZE /*6*/:
                    this.isOrthogonal = false;
                    this.finalAngle = 0.0f;
                    pointerIndex = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & action) >> 8;
                    if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
                        if (pointerIndex == 0) {
                            newPointerIndex = INVALID_POINTER_ID;
                        }
                        this.mLastTouchX = ev.getX(newPointerIndex);
                        this.mLastTouchY = ev.getY(newPointerIndex);
                        this.mActivePointerId = ev.getPointerId(newPointerIndex);
                        break;
                    }
                    break;
            }
            invalidate();
            return true;
        }

        float getMatrixRotation(Matrix matrix) {
            matrix.getValues(this.values);
            return (float) Math.round(Math.atan2((double) this.values[INVALID_POINTER_ID], (double) this.values[BACKGROUND_PATTERN]) * 57.29577951308232d);
        }
    }

    public class CustomRlLayout extends RelativeLayout {
        public CustomRlLayout(Context context) {
            super(context);
        }

        public CustomRlLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomRlLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }
    }

    public class CustomImageView extends ImageView {

        private static final int PADDING = 8;

        private Paint mBorderPaint;
        private Matrix textMatrix;
        private Matrix mainMatrix;

        public CustomImageView(Context context) {
            this(context, null);
        }

        public CustomImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
            setPadding(PADDING, PADDING, PADDING, PADDING);
            textMatrix = new Matrix();
            mainMatrix = new Matrix();
        }

        public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initBorderPaint();
        }

        private void initBorderPaint() {
            mBorderPaint = new Paint();
            mBorderPaint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (showText) {
                for (int i = 0; i < textDataList.size(); i ++) {
                    textMatrix.set((textDataList.get(i)).imageSaveMatrix);
                    textMatrix.postConcat(identityMatrix);
                    canvas.setMatrix(textMatrix);
                    canvas.drawText(((TextData) textDataList.get(i)).message, ((TextData) textDataList.get(i)).xPos, ((TextData) textDataList.get(i)).yPos, ((TextData) textDataList.get(i)).textPaint);
                    canvas.setMatrix(identityMatrix);
//                    canvas.drawRect(0.0f, 0.0f, this.mirrorModeList[this.currentModeIndex].rectTotalArea.left, (float) screenHeightPixels, this.textRectPaint);
//                    canvas.drawRect(0.0f, 0.0f, (float) screenWidthPixels, this.mirrorModeList[this.currentModeIndex].rectTotalArea.top, this.textRectPaint);
//                    canvas.drawRect(this.mirrorModeList[this.currentModeIndex].rectTotalArea.right, 0.0f, (float) screenWidthPixels, (float) screenHeightPixels, this.textRectPaint);
//                    canvas.drawRect(0.0f, this.mirrorModeList[this.currentModeIndex].rectTotalArea.bottom, (float) screenWidthPixels, (float) screenHeightPixels, this.textRectPaint);
                }
            }
            super.onDraw(canvas);
        }
    }
}
