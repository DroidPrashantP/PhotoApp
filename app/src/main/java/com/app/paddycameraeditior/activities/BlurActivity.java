package com.app.paddycameraeditior.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.paddycameraeditior.AppController;
import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.adapters.BackgroundLayoutAdapter;
import com.app.paddycameraeditior.adapters.FilterLayoutAdapter;
import com.app.paddycameraeditior.adapters.FramesLayoutAdapter;
import com.app.paddycameraeditior.adapters.MainLayoutAdapter;
import com.app.paddycameraeditior.adapters.MaskLayoutAdapter;
import com.app.paddycameraeditior.adapters.ToolLayoutAdapter;
import com.app.paddycameraeditior.analytics.AnalyticBasic;
import com.app.paddycameraeditior.bitmap.BitmapLoader;
import com.app.paddycameraeditior.bitmap.BitmapProcessing;
import com.app.paddycameraeditior.multitouchview.StickerCustomView;
import com.app.paddycameraeditior.utils.CommonActivity;
import com.app.paddycameraeditior.utils.Constants;
import com.app.paddycameraeditior.utils.CustomFrameLayout.RatioDatumMode;
import com.app.paddycameraeditior.utils.CustomViews.MultiTouchListener;
import com.app.paddycameraeditior.utils.CustomViews.ZoomableView;
import com.app.paddycameraeditior.utils.ImageMainFilter;
import com.app.paddycameraeditior.utils.Toaster;
import com.app.paddycameraeditior.utils.UriToUrl;
import com.commit451.nativestackblur.NativeStackBlur;

import java.util.ArrayList;

public class BlurActivity extends Activity implements View.OnClickListener {


    private static RatioDatumMode MODE = RatioDatumMode.DATUM_WIDTH;
    private static final int TEXTVIEW_CALL = 2;
    private static int STRICKER_RESULT = 10;
    private Animation animation;
    private ZoomableView image_holder;
    private ImageView background_image;
    private Bitmap last_bitmap;
    private int source_id;
    private Uri imageUri;
    private String imageUrl;
    private ArrayList<String> effects;
    private RelativeLayout toolbox;

    private LinearLayout holder_target;
    private LinearLayout rotation_holder;
    private RelativeLayout blurSeekbarLayout;
    private RelativeLayout spaceSeekbarLayout;
    private RelativeLayout mFramesLayout;
    private RelativeLayout mBackgroundLayout;
    private RelativeLayout mMaskRelativeView;
    private LinearLayout mRatioLayout;
    private RelativeLayout mFilterLayout;
    private RelativeLayout mFxLayout;
    private LinearLayout strickerSelectionView;
    private RelativeLayout commonSeekbarLayout;
    private LinearLayout commonSeekbarCancelLayout;
    private LinearLayout commonSeekbarApplyLayout;

    private boolean toolLayoutflag = false;
    private boolean blurLayoutflag = false;
    private boolean spaceLayoutflag = false;
    private boolean frameLayoutFlag = false;
    private boolean backgroundLayoutFlag = false;
    private boolean maskLayoutFlag = false;
    private boolean ratioLayoutFlag = false;
    private boolean filterLayoutFlag = false;
    private boolean fxLayoutFlag = false;

    private RecyclerView mainLayoutRecyclerView;
    private RecyclerView maskRecyclerView;
    private RecyclerView filterRecyclerView;

    private SeekBar mSeekbar;
    private SeekBar mSpaceSeekbar;
    private SeekBar mCommonSeekbar;

    Bitmap originalBitmap = null;
    Bitmap originalBgBitmap = null;
    String Originalpath;
    RelativeLayout mMainContainer;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private int selectedFrame = 0;
    private RelativeLayout mMaskLayout;
    private LinearLayout customView;
    private Matrix matrix = new Matrix();
    private boolean shapeApplyFlag = true;
    private RelativeLayout ratioFrameLayout;
    private StickerCustomView stickerCustomView;
    private ProgressDialog progressDialog;
    private Bitmap temp;
    private Bitmap frame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        findviewbyIds();
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

        // setCustomView();
        setMainLayout();
        setMaskLayout();
        blurSection();
        SpaceSelection();
        setFrameLayout();
        setmBackgroundLayoutLayout();
        setRotationsToolsLayout();
        setFilterLayout();
        setFxLayout();
        AnalyticBasic.hitGoogleAnalytics(this, PhotoActivity.class.getName());

    }

    private void setCustomView() {
        image_holder.setScaleX(0.75f);
        image_holder.setScaleY(0.75f);
        matrix.postScale(0.75f, 0.75f, (float) image_holder.getX(), image_holder.getY());
    }

    private void findviewbyIds() {
        ratioFrameLayout = (RelativeLayout) findViewById(R.id.customRatioView);
        mMaskRelativeView = (RelativeLayout) findViewById(R.id.shapeLayout);
        image_holder = (ZoomableView) findViewById(R.id.source_image);
        background_image = (ImageView) findViewById(R.id.background_image);
        mMainContainer = (RelativeLayout) findViewById(R.id.container);
        mainLayoutRecyclerView = (RecyclerView) findViewById(R.id.mainLayout);
        maskRecyclerView = (RecyclerView) findViewById(R.id.MaskRecyclerView);
        filterRecyclerView = (RecyclerView) findViewById(R.id.filterRecyclerView);

        rotation_holder = (LinearLayout) findViewById(R.id.rotation_holder);
        rotation_holder.setVisibility(View.INVISIBLE);
        blurSeekbarLayout = (RelativeLayout) findViewById(R.id.blurSeekbarLayout);
        spaceSeekbarLayout = (RelativeLayout) findViewById(R.id.spaceSeekbarLayout);
        strickerSelectionView = (LinearLayout) findViewById(R.id.strickerSelectionView);
        commonSeekbarLayout = (RelativeLayout) findViewById(R.id.commonSeekbarLayout);

        mFramesLayout = (RelativeLayout) findViewById(R.id.frameLayout);
        mFxLayout = (RelativeLayout) findViewById(R.id.fxLayout);
        mBackgroundLayout = (RelativeLayout) findViewById(R.id.backgroundLayout);
        mMaskLayout = (RelativeLayout) findViewById(R.id.MaskLayout);
        mRatioLayout = (LinearLayout) findViewById(R.id.ratioLayout);
        mFilterLayout = (RelativeLayout) findViewById(R.id.filterLayout);


        commonSeekbarCancelLayout = (LinearLayout) findViewById(R.id.cancel);
        commonSeekbarApplyLayout = (LinearLayout) findViewById(R.id.apply);


        image_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toolLayoutflag) {
                    toolLayoutflag = true;
                    rotation_holder.setVisibility(View.VISIBLE);
                    mainLayoutRecyclerView.setVisibility(View.GONE);

                    mMaskLayout.setVisibility(View.GONE);
                    blurSeekbarLayout.setVisibility(View.GONE);
                    mFramesLayout.setVisibility(View.GONE);
                    mBackgroundLayout.setVisibility(View.GONE);
                    spaceSeekbarLayout.setVisibility(View.GONE);
                    mRatioLayout.setVisibility(View.GONE);
                    mFilterLayout.setVisibility(View.GONE);
                    mFxLayout.setVisibility(View.GONE);
                    maskLayoutFlag = false;
                    blurLayoutflag = false;
                    frameLayoutFlag = false;
                    backgroundLayoutFlag = false;
                    spaceLayoutflag = false;
                    ratioLayoutFlag = false;
                    filterLayoutFlag = false;
                    fxLayoutFlag = false;

                } else {
                    mainLayoutRecyclerView.setVisibility(View.VISIBLE);
                    hideAllLayout();
                }
            }
        });


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
    }

    public void setMaskLayout() {
        maskRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        maskRecyclerView.setLayoutManager(layoutManager);
        MaskLayoutAdapter maskLayoutAdapter = new MaskLayoutAdapter(this, Constants.CurrentFunction.BLUR);
        maskRecyclerView.setAdapter(maskLayoutAdapter);

    }

    public void setFxLayout() {
        RecyclerView fxRecyclerView = (RecyclerView) findViewById(R.id.fxRecyclerView);
        fxRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fxRecyclerView.setLayoutManager(layoutManager);
        FramesLayoutAdapter framesLayoutAdapter = new FramesLayoutAdapter(this, Constants.CurrentFunction.BLUR, Constants.FilterFunction.FX);
        fxRecyclerView.setAdapter(framesLayoutAdapter);
    }

    public void setMainLayout() {
        mainLayoutRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainLayoutRecyclerView.setLayoutManager(layoutManager);
        MainLayoutAdapter mainLayoutAdapter = new MainLayoutAdapter(this, Constants.CurrentFunction.BLUR);
        mainLayoutRecyclerView.setAdapter(mainLayoutAdapter);

    }

    public void setRotationsToolsLayout() {
        RecyclerView mainLayoutRecyclerView = (RecyclerView) findViewById(R.id.ToolsLayoutRecyclerView);
        mainLayoutRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainLayoutRecyclerView.setLayoutManager(layoutManager);
        ToolLayoutAdapter toolLayoutAdapter = new ToolLayoutAdapter(this, Constants.CurrentFunction.BLUR);
        mainLayoutRecyclerView.setAdapter(toolLayoutAdapter);

    }

    public void setFrameLayout() {

        RecyclerView frameRecyclerview = (RecyclerView) findViewById(R.id.frameRecyclerView);
        frameRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        frameRecyclerview.setLayoutManager(layoutManager);
        FramesLayoutAdapter framesLayoutAdapter = new FramesLayoutAdapter(this, Constants.CurrentFunction.BLUR, Constants.FilterFunction.FRAME);
        frameRecyclerview.setAdapter(framesLayoutAdapter);
    }


    public void setmBackgroundLayoutLayout() {

        RecyclerView backgroundRecyclerView = (RecyclerView) findViewById(R.id.backgroundRecyclerView);
        backgroundRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        backgroundRecyclerView.setLayoutManager(layoutManager);
        BackgroundLayoutAdapter backgroundLayoutAdapter = new BackgroundLayoutAdapter(this, Constants.CurrentFunction.BLUR);
        backgroundRecyclerView.setAdapter(backgroundLayoutAdapter);
    }

    public void setFilterLayout() {
        RecyclerView filterRecyclerView = (RecyclerView) findViewById(R.id.filterRecyclerView);
        filterRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        filterRecyclerView.setLayoutManager(layoutManager);
        FilterLayoutAdapter mainLayoutAdapter = new FilterLayoutAdapter(this, Constants.CurrentFunction.BLUR);
        filterRecyclerView.setAdapter(mainLayoutAdapter);

    }

    private void blurSection() {
        mSeekbar = (SeekBar) findViewById(R.id.blurSeekbar);
        final TextView seekbarTextview = (TextView) findViewById(R.id.seekBarProgressText);
        mSeekbar.setMax(25);
        mSeekbar.setProgress(14);
        seekbarTextview.setText("" + 14);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress;
                if (seekBar.getProgress() == 0) {
                    progress = 1;
                } else {
                    progress = seekBar.getProgress();
                }
                seekbarTextview.setText((new StringBuilder()).append(seekBar.getProgress()).toString());
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
                seekbarTextview.setText((new StringBuilder()).append((int) f).toString());
            }
        });
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

    private void SpaceSelection() {
        mSpaceSeekbar = (SeekBar) findViewById(R.id.spaceSeekbar);
        mSpaceSeekbar.setMax(100);
        mSpaceSeekbar.setProgress(50);
        mSpaceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                float floatValue = 0.5f;
                if (seekBar.getProgress() == 0) {
                    progress = 1;
                } else {
                    progress = seekBar.getProgress();
                }

                floatValue = (float) (0.5 + (progress * 0.005));
                image_holder.setScaleX(floatValue);
                image_holder.setScaleY(floatValue);
                matrix.postScale(floatValue, floatValue, (float) image_holder.getX(), image_holder.getY());
            }
        });

    }

    private void loadImage() throws Exception {
        BitmapWorkerTask bitmaporker = new BitmapWorkerTask();
        bitmaporker.execute();
    }

    @Override
    public void onClick(View v) {
    }

    public void BackgroundClick(int id, int pos) {
        if (pos == 0) {
            Bitmap copyBitmap = originalBgBitmap;
            Bitmap blurredBitmap = NativeStackBlur.process(copyBitmap, 30);
            background_image.setImageBitmap(blurredBitmap);

        } else {
            background_image.setImageResource(id);
        }
    }


    @SuppressLint("NewApi")
    public void ShapeClick(int id, int pos) {
        if (pos == 0) {
            image_holder.setImageBitmap(originalBgBitmap);
            shapeApplyFlag = true;
        } else {
            if (shapeApplyFlag) {
                   setCustomView();
                Bitmap  bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap mask = BitmapFactory.decodeResource(getResources(), id);
                //You can change original image here and draw anything you want to be masked on it.
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.9), (int) (bitmap.getHeight() * 0.9), true);

                Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas tempCanvas = new Canvas(result);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

                int width = mask.getWidth();
                int height = mask.getHeight();
                float centerX = (width - bitmap.getWidth()) * 0.5f;
                float centerY = (height - bitmap.getHeight()) * 0.5f;

                tempCanvas.drawBitmap(resized, centerX, centerY, null);
                tempCanvas.drawBitmap(mask, 0, 0, paint);
                paint.setXfermode(null);

                //Draw result after performing masking
                tempCanvas.drawBitmap(result, 0, 0, new Paint());
                 image_holder.setImageBitmap(result);

            }
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
            commonSeekbarLayout.setVisibility(View.GONE);
            mFramesLayout.setVisibility(View.VISIBLE);
            CommonSeekbarSection(Constants.FilterFunction.FRAME);
        }
        mFilterLayout.setVisibility(View.GONE);
    }

    public void ClearStricker(View view) {
        stickerCustomView.ClearSticker(strickerSelectionView);

    }

    public void SetStricker(View view) {
        stickerCustomView.ApplySticker();
        strickerSelectionView.setVisibility(View.GONE);
    }

    public void FxClick(int i, int pos) {
        new setFxAsynctask(pos, originalBitmap,1).execute();
    }

    public void FxSelection(View view) {
        if (view.getId() == R.id.closefx){
            image_holder.buildDrawingCache();
            originalBitmap = image_holder.getDrawingCache();
            image_holder.setImageBitmap(originalBitmap);
            mFxLayout.setVisibility(View.GONE);
            fxLayoutFlag = false;
        }else if (view.getId() == R.id.savefx){
            mFxLayout.setVisibility(View.GONE);
            fxLayoutFlag = false;
        }
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
            progressDialog = ProgressDialog.show(BlurActivity.this, "", "Loading...");
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

    private class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {
        DisplayMetrics metrics;
        BitmapLoader bitmapLoader;
        ProgressDialog mProgressDialog;

        public BitmapWorkerTask() {
            metrics = getResources().getDisplayMetrics();
            imageUrl = UriToUrl.get(getApplicationContext(), imageUri);
            bitmapLoader = new BitmapLoader();
            mProgressDialog = ProgressDialog.show(BlurActivity.this, "Please wait...",null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Void... arg0) {
            try {
                mProgressDialog.dismiss();
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
        }
    }

    public class setFrameBacktask extends AsyncTask<Void, Void, Void> {
        ProgressDialog prog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            prog =  ProgressDialog.show(BlurActivity.this, "", "Progress...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            temp = setFrame(originalBitmap);
            return null;
        }

        @Override
        protected void onPostExecute(Void res){
            super.onPostExecute(res);
            image_holder.setImageBitmap(temp);
            prog.dismiss();
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


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        overridePendingTransition(0, 0);
        super.onStop();
    }


    private Bitmap getCurrentbitmap() {
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
                Bitmap copyBitmap = bitmap;
                Bitmap bmp2 = bitmap.copy(bitmap.getConfig(), true);
                image_holder.setImageBitmap(bitmap);
                try {
                    Bitmap blurredBitmap = NativeStackBlur.process(copyBitmap, 30);
                    background_image.setImageBitmap(blurredBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
        outState.putParcelable(Constants.KEY_BITMAP, getCurrentbitmap());
    }

    public void mToolLayoutHandler(int id) {
        if (id == 0) {
            setImage(originalBitmap);
        }
        if (id == 1) {
            Bitmap bitmap = CommonActivity.RotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 0);
            image_holder.setImageBitmap(bitmap);
        }

        if (id == 2) {
            Bitmap bitmap = CommonActivity.RotateImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 1);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == 3) {
            Bitmap bitmap = CommonActivity.flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 1);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == 4) {
            Bitmap bitmap = CommonActivity.flipImage(((BitmapDrawable) image_holder.getDrawable()).getBitmap(), 0);
            image_holder.setImageBitmap(bitmap);
        }
        if (id == 5) {
            image_holder.TranslateImage(-10, 0);
        }
        if (id == 6) {
            image_holder.TranslateImage(10, 0);
        }
        if (id == 7) {
            image_holder.TranslateImage(0, -10);
        }
        if (id == 8) {
            image_holder.TranslateImage(0, 10);
        }
        if (id == 8) {
            image_holder.ZoomIN(0.5f);
        }
        if (id == 9) {
            image_holder.ZoomOut(-0.5f);
        }
    }

    public void MainLayoutContainer(int pos) {
        if (pos == 0) {
            //myViewFlipper.bringToFront();
            if (toolLayoutflag == false) {
                mMaskLayout.startAnimation(slideLeftIn);
                mMaskLayout.setVisibility(View.VISIBLE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                spaceSeekbarLayout.setVisibility(View.GONE);
                rotation_holder.setVisibility(View.GONE);
                mRatioLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                maskLayoutFlag = true;
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                spaceLayoutflag = false;
                ratioLayoutFlag = false;
                filterLayoutFlag = false;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 1) {
            // myViewFlipper.bringToFront();
            if (blurLayoutflag == false) {
                mMaskLayout.setVisibility(View.GONE);
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.VISIBLE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                spaceSeekbarLayout.setVisibility(View.GONE);
                mRatioLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                maskLayoutFlag = false;
                blurLayoutflag = true;
                toolLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                spaceLayoutflag = false;
                ratioLayoutFlag = false;
                filterLayoutFlag = false;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 2) {
            if (backgroundLayoutFlag == false) {
                mMaskLayout.setVisibility(View.GONE);
                mBackgroundLayout.startAnimation(slideRightIn);
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.VISIBLE);
                spaceSeekbarLayout.setVisibility(View.GONE);
                mRatioLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                maskLayoutFlag = false;
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = true;
                spaceLayoutflag = false;
                ratioLayoutFlag = false;
                filterLayoutFlag = false;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 3) {
            if (spaceLayoutflag == false) {
                mMaskLayout.setVisibility(View.GONE);
                spaceSeekbarLayout.startAnimation(slideRightIn);
                spaceSeekbarLayout.setVisibility(View.VISIBLE);
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                mRatioLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);
                maskLayoutFlag = false;
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                spaceLayoutflag = true;
                ratioLayoutFlag = false;
                filterLayoutFlag = false;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 4) {
            startActivityForResult(new Intent(this, StickerActivity.class), STRICKER_RESULT);
            hideAllLayout();
        }
        if (pos == 5) {
            if (filterLayoutFlag == false) {
                mMaskLayout.setVisibility(View.GONE);
                spaceSeekbarLayout.setVisibility(View.GONE);
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                mRatioLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.VISIBLE);
                mFilterLayout.startAnimation(slideRightIn);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.GONE);

                maskLayoutFlag = false;
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                spaceLayoutflag = false;
                ratioLayoutFlag = false;
                filterLayoutFlag = true;
                fxLayoutFlag = false;
            } else {
                hideAllLayout();
            }
        }
        if (pos == 6) {
            if (fxLayoutFlag == false) {
                mMaskLayout.setVisibility(View.GONE);
                spaceSeekbarLayout.setVisibility(View.GONE);
                rotation_holder.setVisibility(View.GONE);
                blurSeekbarLayout.setVisibility(View.GONE);
                mFramesLayout.setVisibility(View.GONE);
                mBackgroundLayout.setVisibility(View.GONE);
                mRatioLayout.setVisibility(View.GONE);
                mFilterLayout.setVisibility(View.GONE);
                commonSeekbarLayout.setVisibility(View.GONE);
                mFxLayout.setVisibility(View.VISIBLE);
                mFxLayout.startAnimation(slideRightIn);

                maskLayoutFlag = false;
                toolLayoutflag = false;
                blurLayoutflag = false;
                frameLayoutFlag = false;
                backgroundLayoutFlag = false;
                spaceLayoutflag = false;
                ratioLayoutFlag = false;
                filterLayoutFlag = false;
                fxLayoutFlag = true;
            } else {
                hideAllLayout();
            }

        }
        if (pos == 7) {
            startActivityForResult(new Intent(this, TextViewActivity.class), TEXTVIEW_CALL);
            hideAllLayout();
        }

    }

    public void hideAllLayout(){
        rotation_holder.setVisibility(View.INVISIBLE);
        blurSeekbarLayout.setVisibility(View.GONE);
        spaceSeekbarLayout.setVisibility(View.GONE);
        mFramesLayout.setVisibility(View.GONE);
        mBackgroundLayout.setVisibility(View.GONE);
        mMaskLayout.setVisibility(View.GONE);
        mRatioLayout.setVisibility(View.GONE);
        mFilterLayout.setVisibility(View.GONE);
        commonSeekbarLayout.setVisibility(View.GONE);
        mFxLayout.setVisibility(View.GONE);
        maskLayoutFlag = false;
        toolLayoutflag = false;
        blurLayoutflag = false;
        frameLayoutFlag = false;
        backgroundLayoutFlag = false;
        spaceLayoutflag = false;
        ratioLayoutFlag = false;
        filterLayoutFlag = false;
        fxLayoutFlag = false;
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
                Originalpath = CommonActivity.SaveNewImage(BlurActivity.this, bitmap);
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
               // Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
        if (requestCode == STRICKER_RESULT) {
            try {
                if (resultCode == RESULT_OK) {
                    int resId = data.getExtras().getInt("resID");
                    stickerCustomView = new StickerCustomView(BlurActivity.this);
                    stickerCustomView.addDrawable(resId, BlurActivity.this);
                    ratioFrameLayout.addView(stickerCustomView);
                    stickerCustomView.loadImages(this);
                    strickerSelectionView.setVisibility(View.VISIBLE);
                } else {
                    Log.e("Resp", "error");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == TEXTVIEW_CALL) {
            try {
                if (resultCode == RESULT_OK) {
                    String text = data.getExtras().getString(Constants.Bundle.TEXT);
                    int colorCode = data.getExtras().getInt(Constants.Bundle.COLOR_CODE);

                    TextView usertext = new TextView(this);
                    usertext.setText(text);
                    usertext.setTextSize(25);
                    usertext.setTextColor(colorCode);
                    usertext.setBackgroundColor(Color.TRANSPARENT);

                    Bitmap textBitmap;

                    Log.e("Width", ""+usertext.getWidth());
                    Log.e("Height", ""+usertext.getHeight());

                    textBitmap = Bitmap.createBitmap(200, 50, Bitmap.Config.ARGB_8888);
                    Canvas c = new Canvas(textBitmap);
                    usertext.layout(300, 300, 500, 600);
                    usertext.draw(c);
                    // create imageview in layout file and declare here

                    //  ImageView iv = (ImageView) findViewById(R.id.source_image);
                    CustomImageView customImageView = new CustomImageView(this);
                    customImageView.setImageBitmap(textBitmap);
                    customImageView.setOnTouchListener(new MultiTouchListener());

                    mMainContainer.addView(customImageView);
                    image_holder.setImageBitmap(originalBitmap);

                } else {
                   // Log.e("Resp", "error");
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
    }


//
//    public void SetCustomRatio(View view){
//        int id = view.getId();
//
//        switch (id) {
//
//            case R.id.button1_1:
//                ratioFrameLayout.setRatio(MODE, 1f, 1f);
//
//                break;
//            case R.id.button2_1:
//                ratioFrameLayout.setRatio(MODE, 2f, 1f);
//
//                break;
//            case R.id.button1_2:
//                ratioFrameLayout.setRatio(MODE, 1f, 2f);
//
//                break;
//            case R.id.button3_2:
//                ratioFrameLayout.setRatio(MODE, 3f, 2f);
//
//                break;
//            case R.id.button2_3:
//                ratioFrameLayout.setRatio(MODE, 2f, 3f);
//
//                break;
//            case R.id.button4_3:
//                ratioFrameLayout.setRatio(MODE, 4f, 3f);
//
//                break;
//            case R.id.button3_4:
//                ratioFrameLayout.setRatio(MODE, 3f, 4f);
//
//                break;
//            case R.id.button4_5:
//                ratioFrameLayout.setRatio(MODE, 4f, 5f);
//
//                break;
//            case R.id.button5_7:
//                ratioFrameLayout.setRatio(MODE, 5f, 7f);
//
//                break;
//            case R.id.button16_9:
//                ratioFrameLayout.setRatio(MODE, 16f, 9f);
//                break;
//        }
//    }

    public class setFxAsynctask extends AsyncTask<Void, Void, Bitmap> {
        private int type;
        private Bitmap filterBitmap;
        private ProgressDialog progressDialog;
        private int progress;

        public setFxAsynctask(int type, Bitmap bitmap, int prog) {
            this.type = type;
            filterBitmap = bitmap;
            progress = prog;
            progressDialog = ProgressDialog.show(BlurActivity.this, "", "Loading...");
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
                    bitmap = BitmapProcessing.contrast(copyBitmap, 50.0);
                    break;
                case 2:
                    bitmap = BitmapProcessing.sepia(copyBitmap);
                    break;
                case 3:
                    bitmap = BitmapProcessing.gaussian(copyBitmap);
                    break;
                case 4:
                    bitmap = BitmapProcessing.grayscale(copyBitmap);
                    break;
                case 5:
                    bitmap = BitmapProcessing.sharpen(copyBitmap);
                    break;
                case 6:
                    bitmap = BitmapProcessing.vignette(copyBitmap);
                    break;
                case 7:
                    bitmap = BitmapProcessing.invert(copyBitmap);
                    break;
                case 8:
                    bitmap = BitmapProcessing.emboss(copyBitmap);
                    break;
                case 9:
                    bitmap = BitmapProcessing.brightness(copyBitmap, 100);
                    break;
                case 10:
                    bitmap = BitmapProcessing.tint(copyBitmap,0xFF1E8D24);
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
            super.onDraw(canvas);
        }
    }
}
