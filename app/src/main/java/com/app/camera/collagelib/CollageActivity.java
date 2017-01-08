package com.app.camera.collagelib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.NinePatchDrawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.camera.R;
import com.app.camera.activities.SaveImageActivity;
import com.app.camera.canvastext.ApplyTextInterface;
import com.app.camera.canvastext.CustomRelativeLayout;
import com.app.camera.canvastext.SingleTap;
import com.app.camera.canvastext.TextData;
import com.app.camera.common_lib.Parameter;
import com.app.camera.fragments.FontFragment;
import com.app.camera.fragments.FullEffectFragment;
import com.app.camera.gallerylib.GalleryUtility;
import com.app.camera.pointlist.Collage;
import com.app.camera.pointlist.CollageLayout;
import com.app.camera.pointlist.MaskPair;
import com.app.camera.sticker.StickerData;
import com.app.camera.sticker.StickerGalleryFragment;
import com.app.camera.sticker.StickerGalleryListener;
import com.app.camera.sticker.StickerView;
import com.app.camera.utils.CustomViews.BlurBuilder;
import com.app.camera.utils.CustomViews.BlurBuilderNormal;
import com.app.camera.utils.LibUtility;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"NewApi"})
public class CollageActivity extends FragmentActivity {
    public static final int INDEX_COLLAGE = 0;
    public static final int INDEX_COLLAGE_BACKGROUND = 1;
    public static final int INDEX_COLLAGE_BLUR = 4;
    public static final int INDEX_COLLAGE_INVISIBLE_VIEW = 5;
    public static final int INDEX_COLLAGE_RATIO = 3;
    public static final int INDEX_COLLAGE_SPACE = 2;
    public static final int TAB_SIZE = 6;
    static final int SAVE_IMAGE_ID = 1543;
    private static final String TAG = "CollageView";
    private static final float UPPER_SIZE_FOR_LOAD = 1500.0f;
    public final int defaultSizeProgressForBlur;
    int RATIO_BUTTON_SIZE;
    Activity activity;
    AdView adWhirlLayout;
    Bitmap[] bitmapList;
    Bitmap btmDelete;
    Bitmap btmScale;
    CustomRelativeLayout canvasText;
    MyAdapter collageAdapter;
    RecyclerView collageRecyclerView;
    CollageView collageView;
    LinearLayout colorContainer;
    Context context;
    ViewGroup contextFooter;
    int currentStickerIndex;
    FontFragment.FontChoosedListener fontChoosedListener;
    FontFragment fontFragment;
    FullEffectFragment fullEffectFragment;
    StickerGalleryFragment galleryFragment;
    int height;
    InterstitialAd interstitial;
    boolean isScrapBook;
    OnSeekBarChangeListener mSeekBarListener;
    RelativeLayout mainLayout;
    float mulX;
    float mulY;
    NinePatchDrawable npd;
    Parameter[] parameterList;
    ArrayList<MyRecylceAdapterBase> patternAdapterList;
    Button[] ratioButtonArray;
    AlertDialog saveImageAlert;
    SeekBar seekBarPadding;
    SeekBar seekBarRound;
    SeekBar seekbarBlur;
    SeekBar seekbarSize;
    View selectFilterTextView;
    boolean selectImageForAdj;
    View selectSwapTextView;
    boolean showText;
    StickerGalleryListener stickerGalleryListener;
    ArrayList<StickerView> stickerList;
    FrameLayout stickerViewContainer;
    StickerView.StickerViewSelectedListener stickerViewSelectedListner;
    Bitmap stickerremoveBitmap;
    Bitmap stickerscaleBitmap;
    boolean swapMode;
    View[] tabButtonList;
    ArrayList<TextData> textDataList;
    ViewFlipper viewFlipper;
    int width;
    private RotationGestureDetector mRotationDetector;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;

    public CollageActivity() {
        this.activity = this;
        this.selectImageForAdj = false;
        this.isScrapBook = false;
        this.patternAdapterList = new ArrayList();
        this.defaultSizeProgressForBlur = 45;
        this.btmDelete = null;
        this.btmScale = null;
        this.textDataList = new ArrayList();
        this.showText = false;
        this.fontChoosedListener = new C09411();
        this.currentStickerIndex = INDEX_COLLAGE;
        this.stickerViewSelectedListner = new C09422();
        this.mSeekBarListener = new C05703();
        this.context = this;
        this.swapMode = false;
        this.mulX = 1.0f;
        this.mulY = 1.0f;
        this.RATIO_BUTTON_SIZE = 11;
    }

    public static boolean hasHardwareAcceleration(Activity activity) {
        Window window = activity.getWindow();
        if (window != null && (window.getAttributes().flags & ViewCompat.MEASURED_STATE_TOO_SMALL) != 0) {
            return true;
        }
        try {
            if ((activity.getPackageManager().getActivityInfo(activity.getComponentName(), INDEX_COLLAGE).flags & AdRequest.MAX_CONTENT_URL_LENGTH) != 0) {
                return true;
            }
        } catch (NameNotFoundException e) {
            Log.e("Chrome", "getActivityInfo(self) should not fail");
        }
        return false;
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(INDEX_COLLAGE_BACKGROUND);
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        Utility.logFreeMemory(this.context);
        setContentView(R.layout.activity_collage);
        Bundle extras = getIntent().getExtras();
        int arraySize = getCollageSize(extras);
        seekBarRound = (SeekBar) findViewById(R.id.seekbar_round);
        seekBarRound.setOnSeekBarChangeListener(this.mSeekBarListener);
        seekBarPadding = (SeekBar) findViewById(R.id.seekbar_padding);
        seekBarPadding.setOnSeekBarChangeListener(this.mSeekBarListener);
        seekbarSize = (SeekBar) findViewById(R.id.seekbar_size);
        seekbarSize.setOnSeekBarChangeListener(this.mSeekBarListener);
        seekbarBlur = (SeekBar) findViewById(R.id.seekbar_collage_blur);
        seekbarBlur.setOnSeekBarChangeListener(this.mSeekBarListener);
        RecyclerView recyclerViewColor = (RecyclerView) findViewById(R.id.recyclerView_color);
        collageRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_grid);
        int colorDefault = getResources().getColor(R.color.view_flipper_bg_color);
        int colorSelected = getResources().getColor(R.color.footer_button_color_pressed);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        collageRecyclerView.setLayoutManager(layoutManager);
        collageAdapter = new MyAdapter(Collage.collageIconArray[arraySize - 1], new C09434(), colorDefault, colorSelected, false, true);
        collageRecyclerView.setAdapter(collageAdapter);
        collageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        viewFlipper = (ViewFlipper) findViewById(R.id.collage_view_flipper);
        viewFlipper.setDisplayedChild(5);
        createAdapterList(colorDefault, colorSelected);
        RecyclerView recyclerViewPattern = (RecyclerView) findViewById(R.id.recyclerView_pattern);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        colorContainer = (LinearLayout) findViewById(R.id.color_container);
        recyclerViewPattern.setLayoutManager(linearLayoutManager);
        recyclerViewPattern.setAdapter(new MyAdapter(Utility.patternResIdList3, new C09445(recyclerViewColor), colorDefault, colorSelected, false, false));
        recyclerViewPattern.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManagerColor = new LinearLayoutManager(this.context);
        layoutManagerColor.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewColor.setLayoutManager(layoutManagerColor);
        recyclerViewColor.setAdapter(new ColorPickerAdapter(new C09456(), colorDefault, colorSelected));
        recyclerViewColor.setItemAnimator(new DefaultItemAnimator());
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.collage_footer);
        horizontalScrollView.bringToFront();
        horizontalScrollView.postDelayed(new C05717(horizontalScrollView), 50);
        horizontalScrollView.postDelayed(new C05728(horizontalScrollView), 600);
        new BitmapWorkerTask().execute(new Bundle[]{extras, bundle});

    }

    private void createAdapterList(int colorDefault, int colorSelected) {
        int size = Utility.patternResIdList2.length;
        this.patternAdapterList.clear();
        this.patternAdapterList.add(new ColorPickerAdapter(new C09469(), colorDefault, colorSelected));
        for (int i = INDEX_COLLAGE; i < size; i += INDEX_COLLAGE_BACKGROUND) {
            this.patternAdapterList.add(new MyAdapter(Utility.patternResIdList2[i], new MyAdapter.CurrentCollageIndexChangedListener() {
                public void onIndexChanged(int positionOrColor) {
                    collageView.setPatternPaint(positionOrColor);
                }
            }, colorDefault, colorSelected, true, true));
        }
    }

    public void addCanvasTextView() {
        this.canvasText = new CustomRelativeLayout(this.context, this.textDataList, this.collageView.identityMatrix, new SingleTap() {
            public void onSingleTap(TextData textData) {
                fontFragment = new FontFragment();
                Bundle arguments = new Bundle();
                arguments.putSerializable("text_data", textData);
                fontFragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.collage_text_view_fragment_container, fontFragment, "FONT_FRAGMENT").commit();
                Log.e(CollageActivity.TAG, "replace fragment");
                fontFragment.setFontChoosedListener(fontChoosedListener);
            }
        });
        this.canvasText.setApplyTextListener(new ApplyTextInterface() {
            public void onOk(ArrayList<TextData> tdList) {
                Iterator it = tdList.iterator();
                while (it.hasNext()) {
                    ((TextData) it.next()).setImageSaveMatrix(collageView.identityMatrix);
                }
                textDataList = tdList;
                showText = true;
                if (mainLayout == null) {
                    mainLayout = (RelativeLayout) findViewById(R.id.collage_main_layout);
                }
                mainLayout.removeView(canvasText);
                collageView.postInvalidate();
            }

            public void onCancel() {
                showText = true;
                mainLayout.removeView(canvasText);
                collageView.postInvalidate();
            }
        });
        this.showText = false;
        this.collageView.invalidate();
        this.mainLayout.addView(this.canvasText);
        ((RelativeLayout) findViewById(R.id.collage_text_view_fragment_container)).bringToFront();
        if (textDataList.size() == 0) {
            this.fontFragment = new FontFragment();
            this.fontFragment.setArguments(new Bundle());
            getSupportFragmentManager().beginTransaction().add(R.id.collage_text_view_fragment_container, this.fontFragment, "FONT_FRAGMENT").commit();
            Log.e(TAG, "add fragment");
            this.fontFragment.setFontChoosedListener(this.fontChoosedListener);
        }
    }

    private void setVisibilityForSingleImage() {
        findViewById(R.id.seekbar_corner_container).setVisibility(View.GONE);
        findViewById(R.id.seekbar_space_container).setVisibility(View.GONE);
        findViewById(R.id.button_collage_blur).setVisibility(View.VISIBLE);
        findViewById(R.id.button_collage_context_delete).setVisibility(View.GONE);
        findViewById(R.id.button_collage_context_swap).setVisibility(View.GONE);
        if (!this.isScrapBook) {
            this.collageView.setCollageSize(this.collageView.sizeMatrix, 45);
            if (this.seekbarSize != null) {
                this.seekbarSize.setProgress(45);
            }
        }
        this.collageView.setBlurBitmap(this.collageView.blurRadius);
        if (!this.isScrapBook) {
            setSelectedTab(INDEX_COLLAGE_BLUR);
        }
    }

    private void setVisibilityForScrapbook() {
        findViewById(R.id.button_collage_layout).setVisibility(View.GONE);
        findViewById(R.id.button_collage_space).setVisibility(View.GONE);
        findViewById(R.id.button_collage_context_swap).setVisibility(View.GONE);
        findViewById(R.id.button_collage_context_fit).setVisibility(View.GONE);
        findViewById(R.id.button_collage_context_center).setVisibility(View.GONE);
        findViewById(R.id.button_collage_context_delete).setVisibility(View.GONE);
    }

    int getCollageSize(Bundle extras) {
        long[] selectedImageList = extras.getLongArray("photo_id_list");
        if (selectedImageList == null) {
            return 1;
        }
        return selectedImageList.length;
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

    public StickerGalleryListener createGalleryListener() {
        if (this.stickerGalleryListener == null) {
            this.stickerGalleryListener = new StickerGalleryListener() {
                public void onGalleryOkSingleImage(int resId) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
                    if (stickerremoveBitmap == null) {
                        stickerremoveBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_remove_text);
                    }
                    if (stickerscaleBitmap == null) {
                        stickerscaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_scale_text);
                    }
                    StickerView stickerView = new StickerView(context, bitmap, null, stickerremoveBitmap, stickerscaleBitmap, resId);
                    stickerView.setStickerViewSelectedListener(stickerViewSelectedListner);
                    if (stickerViewContainer == null) {
                        stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
                    }
                    stickerViewContainer.addView(stickerView);
                    FragmentManager fm = getSupportFragmentManager();
                    if (galleryFragment == null) {
                        galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
                    }
                    fm.beginTransaction().hide(galleryFragment).commit();
                }

                public void onGalleryOkImageArray(int[] ImageIdList) {
                    if (stickerremoveBitmap == null) {
                        stickerremoveBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_remove_text);
                    }
                    if (stickerscaleBitmap == null) {
                        stickerscaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_scale_text);
                    }
                    for (int i = CollageActivity.INDEX_COLLAGE; i < ImageIdList.length; i += CollageActivity.INDEX_COLLAGE_BACKGROUND) {
                        StickerView stickerView = new StickerView(context, BitmapFactory.decodeResource(getResources(), ImageIdList[i]), null, stickerremoveBitmap, stickerscaleBitmap, ImageIdList[i]);
                        stickerView.setStickerViewSelectedListener(stickerViewSelectedListner);
                        if (stickerViewContainer == null) {
                            stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
                        }
                        stickerViewContainer.addView(stickerView);
                    }
                    FragmentManager fm = getSupportFragmentManager();
                    if (galleryFragment == null) {
                        galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myFragmentTag");
                    }
                    fm.beginTransaction().hide(galleryFragment).commit();
                }

                public void onGalleryCancel() {
                    getSupportFragmentManager().beginTransaction().hide(galleryFragment).commit();
                }
            };
        }
        return this.stickerGalleryListener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SAVE_IMAGE_ID && !CommonLibrary.isAppPro(this.context) && this.context.getResources().getBoolean(R.bool.showInterstitialAds)) {
//            ImageUtility.displayInterStitialWithSplashScreen(this.interstitial, this, ImageUtility.SPLASH_TIME_OUT_DEFAULT, "COLLAGE_ACTIVITY_ONACTIVITYRESULT");
//        }
    }

    public void onDestroy() {
        int i;
        super.onDestroy();
        if (this.bitmapList != null) {
            for (i = INDEX_COLLAGE; i < this.bitmapList.length; i += INDEX_COLLAGE_BACKGROUND) {
                if (this.bitmapList[i] != null) {
                    this.bitmapList[i].recycle();
                }
            }
        }
        if (this.collageView != null) {
            if (this.collageView.shapeLayoutList != null) {
                for (i = INDEX_COLLAGE; i < this.collageView.shapeLayoutList.size(); i += INDEX_COLLAGE_BACKGROUND) {
                    for (int j = INDEX_COLLAGE; j < ((ShapeLayout) this.collageView.shapeLayoutList.get(i)).shapeArr.length; j += INDEX_COLLAGE_BACKGROUND) {
                        if (((ShapeLayout) this.collageView.shapeLayoutList.get(i)).shapeArr[j] != null) {
                            ((ShapeLayout) this.collageView.shapeLayoutList.get(i)).shapeArr[j].freeBitmaps();
                        }
                    }
                }
            }
            if (this.collageView.maskBitmapArray != null) {
                for (i = INDEX_COLLAGE; i < this.collageView.maskBitmapArray.length; i += INDEX_COLLAGE_BACKGROUND) {
                    if (this.collageView.maskBitmapArray[i] != null) {
                        if (!this.collageView.maskBitmapArray[i].isRecycled()) {
                            this.collageView.maskBitmapArray[i].recycle();
                        }
                        this.collageView.maskBitmapArray[i] = null;
                    }
                }
            }
        }
        if (this.adWhirlLayout != null) {
            this.adWhirlLayout.removeAllViews();
            this.adWhirlLayout.destroy();
        }
    }

    void setSelectedTab(int index) {
        if (this.viewFlipper != null) {
            setTabBg(INDEX_COLLAGE);
            int displayedChild = this.viewFlipper.getDisplayedChild();
            if (displayedChild != INDEX_COLLAGE_BACKGROUND) {
                hideColorContainer();
            }
            if (index == 0) {
                if (displayedChild != 0) {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                    this.viewFlipper.setDisplayedChild(INDEX_COLLAGE);
                } else {
                    return;
                }
            }
            if (index == INDEX_COLLAGE_BACKGROUND) {
                setTabBg(INDEX_COLLAGE_BACKGROUND);
                if (displayedChild != INDEX_COLLAGE_BACKGROUND) {
                    if (displayedChild == 0) {
                        this.viewFlipper.setInAnimation(this.slideRightIn);
                        this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    } else {
                        this.viewFlipper.setInAnimation(this.slideLeftIn);
                        this.viewFlipper.setOutAnimation(this.slideRightOut);
                    }
                    this.viewFlipper.setDisplayedChild(1);
                } else {
                    return;
                }
            }
            if (index == INDEX_COLLAGE_BLUR) {
                setTabBg(INDEX_COLLAGE_BLUR);
                if (displayedChild != INDEX_COLLAGE_BLUR) {
                    if (displayedChild == 0) {
                        this.viewFlipper.setInAnimation(this.slideRightIn);
                        this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    } else {
                        this.viewFlipper.setInAnimation(this.slideLeftIn);
                        this.viewFlipper.setOutAnimation(this.slideRightOut);
                    }
                    this.viewFlipper.setDisplayedChild(INDEX_COLLAGE_BLUR);
                } else {
                    return;
                }
            }
            if (index == INDEX_COLLAGE_SPACE) {
                setTabBg(INDEX_COLLAGE_SPACE);
                if (displayedChild != INDEX_COLLAGE_SPACE) {
                    if (displayedChild == 0 || displayedChild == INDEX_COLLAGE_BACKGROUND) {
                        this.viewFlipper.setInAnimation(this.slideRightIn);
                        this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    } else {
                        this.viewFlipper.setInAnimation(this.slideLeftIn);
                        this.viewFlipper.setOutAnimation(this.slideRightOut);
                    }
                    this.viewFlipper.setDisplayedChild(INDEX_COLLAGE_SPACE);
                } else {
                    return;
                }
            }
            if (index == INDEX_COLLAGE_RATIO) {
                setTabBg(INDEX_COLLAGE_RATIO);
                if (displayedChild != INDEX_COLLAGE_RATIO) {
                    if (displayedChild == INDEX_COLLAGE_INVISIBLE_VIEW) {
                        this.viewFlipper.setInAnimation(this.slideLeftIn);
                        this.viewFlipper.setOutAnimation(this.slideRightOut);
                    } else {
                        this.viewFlipper.setInAnimation(this.slideRightIn);
                        this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    }
                    this.viewFlipper.setDisplayedChild(INDEX_COLLAGE_RATIO);
                } else {
                    return;
                }
            }
            if (index == INDEX_COLLAGE_INVISIBLE_VIEW) {
                setTabBg(-1);
                if (displayedChild != INDEX_COLLAGE_INVISIBLE_VIEW) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    this.viewFlipper.setDisplayedChild(INDEX_COLLAGE_INVISIBLE_VIEW);
                }
            }
        }
    }

    private void setTabBg(int index) {
        if (this.tabButtonList == null) {
            this.tabButtonList = new View[TAB_SIZE];
            this.tabButtonList[INDEX_COLLAGE] = findViewById(R.id.button_collage_layout);
            this.tabButtonList[INDEX_COLLAGE_SPACE] = findViewById(R.id.button_collage_space);
            this.tabButtonList[INDEX_COLLAGE_BLUR] = findViewById(R.id.button_collage_blur);
            this.tabButtonList[INDEX_COLLAGE_BACKGROUND] = findViewById(R.id.button_collage_background);
            this.tabButtonList[INDEX_COLLAGE_RATIO] = findViewById(R.id.button_collage_ratio);
            this.tabButtonList[INDEX_COLLAGE_INVISIBLE_VIEW] = findViewById(R.id.button_collage_adj);
        }
        for (int i = INDEX_COLLAGE; i < this.tabButtonList.length; i += INDEX_COLLAGE_BACKGROUND) {
            this.tabButtonList[i].setBackgroundResource(R.drawable.collage_footer_button);
        }
        if (index >= 0) {
            this.tabButtonList[index].setBackgroundResource(R.color.footer_button_color_pressed);
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
            for (int i = INDEX_COLLAGE; i < size; i += INDEX_COLLAGE_BACKGROUND) {
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
            Bitmap stickerremoveBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_remove_text);
            Bitmap stickerscaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_scale_text);
            for (int i = INDEX_COLLAGE; i < stickerDataArray.length; i += INDEX_COLLAGE_BACKGROUND) {
                StickerView stickerView = new StickerView(this.context, BitmapFactory.decodeResource(getResources(), stickerDataArray[i].getResId()), stickerDataArray[i], stickerremoveBitmap, stickerscaleBitmap, stickerDataArray[i].getResId());
                stickerView.setStickerViewSelectedListener(this.stickerViewSelectedListner);
                if (this.stickerViewContainer == null) {
                    this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
                }
                this.stickerViewContainer.addView(stickerView);
            }
        }
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        if (id == R.id.button_collage_layout) {
            setSelectedTab(INDEX_COLLAGE);
        } else if (id == R.id.button_collage_ratio) {
            setSelectedTab(INDEX_COLLAGE_RATIO);
        } else if (id == R.id.button_collage_blur) {
            collageView.setBlurBitmap(collageView.blurRadius);
            setSelectedTab(INDEX_COLLAGE_BLUR);
            collageView.startAnimator();
        } else if (id == R.id.button_collage_background) {
            setSelectedTab(INDEX_COLLAGE_BACKGROUND);
        } else if (id == R.id.button_collage_space) {
            setSelectedTab(INDEX_COLLAGE_SPACE);
        } else if (id == R.id.button_collage_adj) {
            if ((collageView.shapeLayoutList.get(0)).shapeArr.length == INDEX_COLLAGE_BACKGROUND) {
                collageView.shapeIndex = 0;
                collageView.openFilterFragment();
            } else if (collageView.shapeIndex >= 0) {
                this.collageView.openFilterFragment();
                Log.e(TAG, "collageView.shapeIndex>=0 openFilterFragment");
            } else {
                setSelectedTab(5);
                this.selectFilterTextView.setVisibility(View.VISIBLE);
                this.selectImageForAdj = true;
            }
        } else if (id == R.id.button_collage_context_swap) {
            if (((ShapeLayout) this.collageView.shapeLayoutList.get(this.collageView.currentCollageIndex)).shapeArr.length == INDEX_COLLAGE_SPACE) {
                this.collageView.swapBitmaps(INDEX_COLLAGE, INDEX_COLLAGE_BACKGROUND);
            } else {
                this.selectSwapTextView.setVisibility(View.VISIBLE);
                this.swapMode = true;
            }
        } else if (id == R.id.button_collage_context_delete) {
            createDeleteDialog();
        } else if (id == R.id.button_collage_context_filter) {
            this.collageView.openFilterFragment();
        } else if (id == R.id.button_save_collage_image) {
            setSelectedTab(INDEX_COLLAGE_INVISIBLE_VIEW);
            SaveImageTask saveImageTask = new SaveImageTask();
            Object[] objArr = new Object[INDEX_COLLAGE_BACKGROUND];
            objArr[INDEX_COLLAGE] = Integer.valueOf(INDEX_COLLAGE_RATIO);
            saveImageTask.execute(objArr);
        } else if (id == R.id.button_cancel_collage_image) {
            backButtonAlertBuilder();
        } else if (id == R.id.button11) {
            this.mulX = 1.0f;
            this.mulY = 1.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(INDEX_COLLAGE);
        } else if (id == R.id.button21) {
            this.mulX = 2.0f;
            this.mulY = 1.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(INDEX_COLLAGE_BACKGROUND);
        } else if (id == R.id.button12) {
            this.mulX = 1.0f;
            this.mulY = 2.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(INDEX_COLLAGE_SPACE);
        } else if (id == R.id.button32) {
            this.mulX = 3.0f;
            this.mulY = 2.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(INDEX_COLLAGE_RATIO);
        } else if (id == R.id.button23) {
            this.mulX = 2.0f;
            this.mulY = 3.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(INDEX_COLLAGE_BLUR);
        } else if (id == R.id.button43) {
            this.mulX = 4.0f;
            this.mulY = 3.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(INDEX_COLLAGE_INVISIBLE_VIEW);
        } else if (id == R.id.button34) {
            this.mulX = 3.0f;
            this.mulY = 4.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(TAB_SIZE);
        } else if (id == R.id.button45) {
            this.mulX = 4.0f;
            this.mulY = 5.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(7);
        } else if (id == R.id.button57) {
            this.mulX = 5.0f;
            this.mulY = 7.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(8);
        } else if (id == R.id.button169) {
            this.mulX = 16.0f;
            this.mulY = 9.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(9);
        } else if (id == R.id.button916) {
            this.mulX = 9.0f;
            this.mulY = 16.0f;
            this.collageView.updateShapeListForRatio(this.width, this.height);
            setRatioButtonBg(10);
        } else if (id == R.id.hide_select_image_warning) {
            this.selectSwapTextView.setVisibility(View.INVISIBLE);
            this.swapMode = false;
        } else if (id == R.id.hide_select_image_warning_filter) {
            this.selectFilterTextView.setVisibility(View.INVISIBLE);
            this.selectImageForAdj = false;
        } else if (id == R.id.hide_color_container) {
            hideColorContainer();
        } else if (id == R.id.button_mirror_text) {
            addCanvasTextView();
            clearViewFlipper();
        } else if (id == R.id.button_mirror_sticker) {
            addStickerGalleryFragment();
        }
        if (id == R.id.button_collage_context_fit) {
            this.collageView.setShapeScaleMatrix(INDEX_COLLAGE);
        } else if (id == R.id.button_collage_context_center) {
            this.collageView.setShapeScaleMatrix(INDEX_COLLAGE_BACKGROUND);
        } else if (id == R.id.button_collage_context_rotate_left) {
            this.collageView.setShapeScaleMatrix(INDEX_COLLAGE_RATIO);
        } else if (id == R.id.button_collage_context_rotate_right) {
            this.collageView.setShapeScaleMatrix(INDEX_COLLAGE_SPACE);
        } else if (id == R.id.button_collage_context_flip_horizontal) {
            this.collageView.setShapeScaleMatrix(INDEX_COLLAGE_BLUR);
        } else if (id == R.id.button_collage_context_flip_vertical) {
            this.collageView.setShapeScaleMatrix(INDEX_COLLAGE_INVISIBLE_VIEW);
        } else if (id == R.id.button_collage_context_rotate_negative) {
            this.collageView.setShapeScaleMatrix(TAB_SIZE);
        } else if (id == R.id.button_collage_context_rotate_positive) {
            this.collageView.setShapeScaleMatrix(7);
        } else if (id == R.id.button_collage_context_zoom_in) {
            toastMatrixMessage(this.collageView.setShapeScaleMatrix(8));
        } else if (id == R.id.button_collage_context_zoom_out) {
            toastMatrixMessage(this.collageView.setShapeScaleMatrix(9));
        } else if (id == R.id.button_collage_context_move_left) {
            toastMatrixMessage(this.collageView.setShapeScaleMatrix(10));
        } else if (id == R.id.button_collage_context_move_right) {
            toastMatrixMessage(this.collageView.setShapeScaleMatrix(11));
        } else if (id == R.id.button_collage_context_move_up) {
            toastMatrixMessage(this.collageView.setShapeScaleMatrix(12));
        } else if (id == R.id.button_collage_context_move_down) {
            toastMatrixMessage(this.collageView.setShapeScaleMatrix(13));
        } else if (this.fullEffectFragment != null && this.fullEffectFragment.isVisible()) {
            this.fullEffectFragment.myClickHandler(view);
        }
        GalleryUtility.logHeap();
        Utility.logFreeMemory(this.context);
    }

    void toastMatrixMessage(int message) {
        String str = null;
        if (message == INDEX_COLLAGE_BACKGROUND) {
            str = "You reached maximum zoom!";
        } else if (message == INDEX_COLLAGE_SPACE) {
            str = "You reached minimum zoom!";
        } else if (message == TAB_SIZE) {
            str = "You reached max bottom!";
        } else if (message == INDEX_COLLAGE_INVISIBLE_VIEW) {
            str = "You reached max top!";
        } else if (message == INDEX_COLLAGE_BLUR) {
            str = "You reached max right!";
        } else if (message == INDEX_COLLAGE_RATIO) {
            str = "You reached max left!";
        }
        if (str != null) {
            Toast msg = Toast.makeText(this.context, str, Toast.LENGTH_SHORT);
            msg.setGravity(17, msg.getXOffset() / INDEX_COLLAGE_SPACE, msg.getYOffset() / INDEX_COLLAGE_SPACE);
            msg.show();
        }
    }

    void createDeleteDialog() {
        if (((ShapeLayout) this.collageView.shapeLayoutList.get(INDEX_COLLAGE)).shapeArr.length == INDEX_COLLAGE_BACKGROUND) {
            Toast msg = Toast.makeText(this.context, "You can't delete last image!", Toast.LENGTH_SHORT);
            msg.setGravity(17, msg.getXOffset() / INDEX_COLLAGE_SPACE, msg.getYOffset() / INDEX_COLLAGE_SPACE);
            msg.show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("Do you want to delete it?").setCancelable(true).setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                collageView.deleteBitmap(collageView.shapeIndex, width, width);
            }
        }).setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        this.saveImageAlert = builder.create();
        this.saveImageAlert.show();
    }

    void clearViewFlipper() {
        this.viewFlipper.setDisplayedChild(INDEX_COLLAGE_INVISIBLE_VIEW);
        setTabBg(-1);
    }

    private void hideColorContainer() {
        if (this.colorContainer == null) {
            this.colorContainer = (LinearLayout) findViewById(R.id.color_container);
        }
        this.colorContainer.setVisibility(View.INVISIBLE);
    }

    private void setRatioButtonBg(int index) {
        if (this.ratioButtonArray == null) {
            this.ratioButtonArray = new Button[this.RATIO_BUTTON_SIZE];
            this.ratioButtonArray[INDEX_COLLAGE] = (Button) findViewById(R.id.button11);
            this.ratioButtonArray[INDEX_COLLAGE_BACKGROUND] = (Button) findViewById(R.id.button21);
            this.ratioButtonArray[INDEX_COLLAGE_SPACE] = (Button) findViewById(R.id.button12);
            this.ratioButtonArray[INDEX_COLLAGE_RATIO] = (Button) findViewById(R.id.button32);
            this.ratioButtonArray[INDEX_COLLAGE_BLUR] = (Button) findViewById(R.id.button23);
            this.ratioButtonArray[INDEX_COLLAGE_INVISIBLE_VIEW] = (Button) findViewById(R.id.button43);
            this.ratioButtonArray[TAB_SIZE] = (Button) findViewById(R.id.button34);
            this.ratioButtonArray[7] = (Button) findViewById(R.id.button45);
            this.ratioButtonArray[8] = (Button) findViewById(R.id.button57);
            this.ratioButtonArray[9] = (Button) findViewById(R.id.button169);
            this.ratioButtonArray[10] = (Button) findViewById(R.id.button916);
        }
        for (int i = INDEX_COLLAGE; i < this.RATIO_BUTTON_SIZE; i += INDEX_COLLAGE_BACKGROUND) {
            this.ratioButtonArray[i].setBackgroundResource(R.drawable.crop_border);
            this.ratioButtonArray[i].setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.ratioButtonArray[index].setBackgroundResource(R.drawable.crop_border_selected);
        this.ratioButtonArray[index].setTextColor(-1);
    }

    void addEffectFragment() {
        if (this.fullEffectFragment == null) {
            this.fullEffectFragment = (FullEffectFragment) getSupportFragmentManager().findFragmentByTag("FULL_FRAGMENT");
            Log.e(TAG, "addEffectFragment");
            if (this.fullEffectFragment == null) {
                this.fullEffectFragment = new FullEffectFragment();
                Log.e(TAG, "EffectFragment == null");
                this.fullEffectFragment.setArguments(getIntent().getExtras());
                Log.e(TAG, "fullEffectFragment null");
                getSupportFragmentManager().beginTransaction().add(R.id.collage_effect_fragment_container, this.fullEffectFragment, "FULL_FRAGMENT").commitAllowingStateLoss();
            } else {
                Log.e(TAG, "not null null");
                if (this.collageView.shapeIndex >= 0) {
                    this.fullEffectFragment.setBitmapWithParameter(this.bitmapList[this.collageView.shapeIndex], this.parameterList[this.collageView.shapeIndex]);
                }
            }
            getSupportFragmentManager().beginTransaction().hide(this.fullEffectFragment).commitAllowingStateLoss();
            this.fullEffectFragment.setFullBitmapReadyListener(new FullEffectFragment.FullBitmapReady() {
                public void onBitmapReady(Bitmap bitmap, Parameter parameter) {
                    if (parameter.getTiltContext() != null) {
                        Log.e(CollageActivity.TAG, "onBitmapReady tilt mode" + parameter.getTiltContext().mode);
                    }
                    collageView.updateShapeListForFilterBitmap(bitmap);
                    collageView.updateParamList(parameter);
                    collageView.postInvalidate();
                    getSupportFragmentManager().beginTransaction().hide(fullEffectFragment).commit();
                    collageView.postInvalidate();
                }

                public void onCancel() {
                    setVisibilityOfFilterHorizontalListview(false);
                    collageView.postInvalidate();
                }
            });
            findViewById(R.id.collage_effect_fragment_container).bringToFront();
        }
    }

    void setVisibilityOfFilterHorizontalListview(boolean show) {
        if (show && this.fullEffectFragment.isHidden()) {
            getSupportFragmentManager().beginTransaction().show(this.fullEffectFragment).commit();
        }
        if (!show && this.fullEffectFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().hide(this.fullEffectFragment).commit();
        }
        findViewById(R.id.collage_effect_fragment_container).bringToFront();
    }

    public void onBackPressed() {
        if (this.fontFragment != null && this.fontFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(this.fontFragment).commit();
        } else if (this.galleryFragment != null && this.galleryFragment.isVisible()) {
            this.galleryFragment.backtrace();
        } else if (!this.showText && this.canvasText != null) {
            this.showText = true;
            this.mainLayout.removeView(this.canvasText);
            this.collageView.postInvalidate();
            this.canvasText = null;
            Log.e(TAG, "replace fragment");
        } else if (this.fullEffectFragment == null || !this.fullEffectFragment.isVisible()) {
            if (this.colorContainer.getVisibility() == View.VISIBLE) {
                hideColorContainer();
            } else if (this.swapMode) {
                this.selectSwapTextView.setVisibility(View.INVISIBLE);
                this.swapMode = false;
            } else if (this.collageView != null && this.collageView.shapeIndex >= 0) {
                this.collageView.unselectShapes();
            } else if (this.selectImageForAdj) {
                this.selectFilterTextView.setVisibility(View.INVISIBLE);
                this.selectImageForAdj = false;
            } else if (this.viewFlipper == null || this.viewFlipper.getDisplayedChild() == INDEX_COLLAGE_INVISIBLE_VIEW) {
                backButtonAlertBuilder();
            } else {
                setSelectedTab(INDEX_COLLAGE_INVISIBLE_VIEW);
            }
        } else if (!this.fullEffectFragment.onBackPressed()) {
            setVisibilityOfFilterHorizontalListview(false);
        }
    }

    private void backButtonAlertBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("Would you like to save image ?").setCancelable(true).setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SaveImageTask saveImageTask = new SaveImageTask();
                Object[] objArr = new Object[CollageActivity.INDEX_COLLAGE_BACKGROUND];
                objArr[CollageActivity.INDEX_COLLAGE] = Integer.valueOf(CollageActivity.INDEX_COLLAGE_BLUR);
                saveImageTask.execute(objArr);
            }
        }).setNegativeButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setNeutralButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        this.saveImageAlert = builder.create();
        this.saveImageAlert.show();
    }

    /* renamed from: com.lyrebirdstudio.collagelib.CollageActivity.3 */
    class C05703 implements OnSeekBarChangeListener {
        C05703() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            if (seekBar.getId() == R.id.seekbar_collage_blur) {
                float radius = ((float) seekBar.getProgress()) / 4.0f;
                if (radius > BlurBuilder.BLUR_RADIUS_MAX) {
                    radius = BlurBuilder.BLUR_RADIUS_MAX;
                }
                if (radius < 0.0f) {
                    radius = 0.0f;
                }
                Log.e(CollageActivity.TAG, "blur radius " + radius);
                collageView.setBlurBitmap((int) radius);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int id = seekBar.getId();
            if (id == R.id.seekbar_round) {
                if (collageView != null) {
                    collageView.setCornerRadius((float) progress);
                }
            } else if (id == R.id.seekbar_padding) {
                if (collageView != null) {
                    collageView.setPathPadding(collageView.currentCollageIndex, (float) progress);
                }
            } else if (id == R.id.seekbar_size) {
                if (collageView != null) {
                    collageView.setCollageSize(collageView.sizeMatrix, progress);
                }
            } else if (id == R.id.seekbar_collage_blur) {
                float f = ((float) progress) / 4.0f;
            }
        }
    }

    class C05717 implements Runnable {
        private final /* synthetic */ HorizontalScrollView val$horizontalScrollView;

        C05717(HorizontalScrollView horizontalScrollView) {
            this.val$horizontalScrollView = horizontalScrollView;
        }

        public void run() {
            this.val$horizontalScrollView.scrollTo(this.val$horizontalScrollView.getChildAt(CollageActivity.INDEX_COLLAGE).getMeasuredWidth(), CollageActivity.INDEX_COLLAGE);
        }
    }

    class C05728 implements Runnable {
        private final /* synthetic */ HorizontalScrollView val$horizontalScrollView;

        C05728(HorizontalScrollView horizontalScrollView) {
            this.val$horizontalScrollView = horizontalScrollView;
        }

        public void run() {
            this.val$horizontalScrollView.fullScroll(17);
        }
    }

    class CollageView extends View {
        public static final int BACKGROUND_BLUR = 1;
        public static final int BACKGROUND_PATTERN = 0;
        public static final int PATTERN_SENTINEL = -1;
        static final float RATIO_CONSTANT = 1.25f;
        private static final int INVALID_POINTER_ID = 1;
        private static final int UPPER_SIZE_LIMIT = 2048;
        final float epsilon;
        float MIN_ZOOM;
        RectF above;
        int animEpsilon;
        int animHalfTime;
        int animSizeSeekbarProgress;
        boolean animate;
        int animationCount;
        int animationDurationLimit;
        int animationLimit;
        int backgroundMode;
        Bitmap blurBitmap;
        BlurBuilder blurBuilder;
        BlurBuilderNormal blurBuilderNormal;
        RectF blurRectDst;
        Rect blurRectSrc;
        Paint borderPaint;
        RectF bottom;
        RectF bottomLeft;
        RectF bottomRight;
        Paint circlePaint;
        float cornerRadius;
        int currentCollageIndex;
        RectF drawingAreaRect;
        float finalAngle;
        Bitmap frameBitmap;
        int frameDuration;
        RectF frameRect;
        Matrix identityMatrix;
        boolean isInCircle;
        boolean isOnCross;
        RectF left;
        float mLastTouchX;
        float mLastTouchY;
        float mScaleFactor;
        Bitmap[] maskBitmapArray;
        int[] maskResIdList;
        float[] matrixValues;
        boolean move;
        int offsetX;
        int offsetY;
        float oldX;
        float oldY;
        boolean orthogonal;
        float paddingDistance;
        Paint paint;
        Paint paintGray;
        Bitmap patternBitmap;
        Paint patternPaint;
        int previousIndex;
        float[] pts;
        Rect rectAnim;
        RectF right;
        RotationGestureDetector.OnRotationGestureListener rotateListener;
        Shape scaleShape;
        int screenHeight;
        int screenWidth;
        int shapeIndex;
        List<ShapeLayout> shapeLayoutList;
        Matrix sizeMatrix;
        Matrix sizeMatrixSaved;
        float sizeScale;
        ArrayList<Float> smallestDistanceList;
        Matrix startMatrix;
        long startTime;
        Matrix textMatrix;
        RectF topLeft;
        RectF topRight;
        float[] values;
        float xscale;
        float yscale;
        PointF zoomStart;
        private Runnable animator;
        private int blurRadius;
        private int mActivePointerId;
        private ScaleGestureDetector mScaleDetector;
        private GestureDetectorCompat mTouchDetector;
        private float startAngle;

        @SuppressLint({"NewApi"})
        public CollageView(Context context, int width, int height) {
            super(context);
            this.paint = new Paint();
            this.paddingDistance = 0.0f;
            this.cornerRadius = 0.0f;
            this.currentCollageIndex = 0;
            this.shapeIndex = PATTERN_SENTINEL;
            this.patternPaint = new Paint(1);
            this.shapeLayoutList = new ArrayList();
            this.identityMatrix = new Matrix();
            this.maskResIdList = new int[]{R.drawable.mask_butterfly, R.drawable.mask_cloud, R.drawable.mask_clover, R.drawable.mask_leaf, R.drawable.mask_left_foot, R.drawable.mask_diamond, R.drawable.mask_santa, R.drawable.mask_snowman, R.drawable.mask_paw, R.drawable.mask_egg, R.drawable.mask_twitter, R.drawable.mask_circle, R.drawable.mask_hexagon, R.drawable.mask_heart};
            this.smallestDistanceList = new ArrayList();
            this.yscale = 1.0f;
            this.xscale = 1.0f;
            this.sizeScale = 1.0f;
            this.sizeMatrix = new Matrix();
            this.animSizeSeekbarProgress = 0;
            this.animate = false;
            this.animationCount = 0;
            this.animationLimit = 31;
            this.animHalfTime = (this.animationLimit / CollageActivity.INDEX_COLLAGE_SPACE) + INVALID_POINTER_ID;
            this.frameDuration = 10;
            this.animEpsilon = 20;
            this.animationDurationLimit = 50;
            this.startTime = System.nanoTime();
            this.animator = new C05731();
            this.rectAnim = new Rect();
            this.textMatrix = new Matrix();
            this.blurRectDst = new RectF();
            this.drawingAreaRect = new RectF();
            this.above = new RectF();
            this.left = new RectF();
            this.right = new RectF();
            this.bottom = new RectF();
            this.move = false;
            this.paintGray = new Paint(1);
            this.mActivePointerId = 1;
            this.zoomStart = new PointF();
            this.startMatrix = new Matrix();
            this.startAngle = 0.0f;
            this.MIN_ZOOM = 0.1f;
            this.isInCircle = false;
            this.isOnCross = false;
            this.orthogonal = false;
            this.mScaleFactor = 1.0f;
            this.matrixValues = new float[9];
            this.finalAngle = 0.0f;
            this.epsilon = 4.0f;
            this.rotateListener = new C09472();
            this.values = new float[9];
            this.blurRadius = 10;
            this.backgroundMode = 0;
            this.blurRectSrc = new Rect();
            this.maskBitmapArray = new Bitmap[this.maskResIdList.length];
            this.borderPaint = new Paint(1);
            this.borderPaint.setColor(getResources().getColor(R.color.blue));
            this.borderPaint.setStyle(Style.STROKE);
            this.borderPaint.setStrokeWidth(10.0f);
            this.screenWidth = width;
            this.screenHeight = height;
            this.circlePaint = new Paint();
            this.circlePaint.setColor(SupportMenu.CATEGORY_MASK);
            this.identityMatrix.reset();
            this.topLeft = new RectF((float) (width * BACKGROUND_PATTERN), (float) (height * BACKGROUND_PATTERN), 0.5f * ((float) width), 0.5f * ((float) height));
            this.topRight = new RectF(0.5f * ((float) width), 0.0f * ((float) height), 1.0f * ((float) width), 0.5f * ((float) height));
            this.bottomLeft = new RectF((float) (width * BACKGROUND_PATTERN), 0.5f * ((float) height), 0.5f * ((float) width), 1.0f * ((float) height));
            this.bottomRight = new RectF(0.5f * ((float) width), 0.5f * ((float) height), 1.0f * ((float) width), 1.0f * ((float) height));
            Path pathTopLeft = new Path();
            Path pathTopRight = new Path();
            Path pathBottomLeft = new Path();
            Path pathBottomRight = new Path();
            pathTopLeft.addRect(this.topLeft, Direction.CCW);
            pathTopRight.addRect(this.topRight, Direction.CCW);
            pathBottomLeft.addRect(this.bottomLeft, Direction.CCW);
            pathBottomRight.addRect(this.bottomRight, Direction.CCW);
            this.mTouchDetector = new GestureDetectorCompat(context, new MyGestureListener());
            this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            mRotationDetector = new RotationGestureDetector(this.rotateListener);
            calculateOffset();
            this.patternPaint = new Paint(INVALID_POINTER_ID);
            this.patternPaint.setColor(PATTERN_SENTINEL);
            createShapeList(bitmapList.length, width, height);
            this.paintGray.setColor(12303292);
        }

        private void calculateOffset() {
            PointF scale = getRatio();
            this.offsetX = (int) ((((float) width) - (scale.x * ((float) width))) / 2.0f);
            this.offsetY = (int) ((((float) height) - (scale.y * ((float) width))) / 2.0f);
        }

        public String saveBitmap(int width, int height) {
            int i;
            int btmWidth = (int) (((float) width) * collageView.xscale);
            int btmHeight = (int) (((float) width) * collageView.yscale);
            float max = (float) Math.max(btmWidth, btmHeight);
            float btmScale = ((float) Utility.maxSizeForSave(context, 2048.0f)) / max;
            int newBtmWidth = (int) (((float) btmWidth) * btmScale);
            int newBtmHeight = (int) (((float) btmHeight) * btmScale);
            if (newBtmWidth <= 0) {
                newBtmWidth = btmWidth;
                Log.e(CollageActivity.TAG, "newBtmWidth");
            }
            if (newBtmHeight <= 0) {
                newBtmHeight = btmHeight;
                Log.e(CollageActivity.TAG, "newBtmHeight");
            }
            Bitmap savedBitmap = Bitmap.createBitmap(newBtmWidth, newBtmHeight, Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(savedBitmap);
            ShapeLayout arr = (ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex);
            Matrix sizeMat = new Matrix();
            sizeMat.reset();
            sizeMat.preScale(btmScale, btmScale);
            bitmapCanvas.setMatrix(sizeMat);
            if (this.backgroundMode == 0) {
                bitmapCanvas.drawRect(0.0f, 0.0f, (float) btmWidth, (float) btmHeight, this.patternPaint);
            }
            if (!(this.blurBitmap == null || this.blurBitmap.isRecycled() || this.backgroundMode != INVALID_POINTER_ID)) {
                RectF rectF = new RectF(0.0f, 0.0f, (float) btmWidth, (float) btmHeight);
                bitmapCanvas.drawBitmap(this.blurBitmap, this.blurRectSrc, rectF, this.paint);
            }
            sizeMat.postScale(this.sizeScale, this.sizeScale, ((float) newBtmWidth) / 2.0f, ((float) newBtmHeight) / 2.0f);
            sizeMat.preTranslate((float) (-this.offsetX), (float) (-this.offsetY));
            bitmapCanvas.setMatrix(sizeMat);
            int q = bitmapCanvas.saveLayer(((float) (-width)) / this.sizeScale, ((float) (-height)) / this.sizeScale, ((float) this.offsetX) + (((float) width) / this.sizeScale), ((float) this.offsetY) + (((float) height) / this.sizeScale), null, Canvas.ALL_SAVE_FLAG);
            for (i = BACKGROUND_PATTERN; i < arr.shapeArr.length; i += INVALID_POINTER_ID) {
                boolean drawPorterClear = false;
                if (i == arr.getClearIndex()) {
                    drawPorterClear = true;
                }
                Log.e(CollageActivity.TAG, "drawPorterClear " + drawPorterClear);
                if (isScrapBook) {
                    arr.shapeArr[i].drawShapeForScrapBook(bitmapCanvas, newBtmWidth, newBtmHeight, false, false);
                } else {
                    arr.shapeArr[i].drawShapeForSave(bitmapCanvas, newBtmWidth, newBtmHeight, q, drawPorterClear);
                }
            }
            Matrix mat;
            if (textDataList != null) {
                for (i = BACKGROUND_PATTERN; i < textDataList.size(); i += INVALID_POINTER_ID) {
                    mat = new Matrix();
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
            bitmapCanvas.restoreToCount(q);
            String resultPath = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append(getString(R.string.directory)).append(String.valueOf(System.currentTimeMillis())).append(".jpg").toString();
            new File(resultPath).getParentFile().mkdirs();
            try {
                OutputStream fileOutputStream = new FileOutputStream(resultPath);
                savedBitmap.compress(CompressFormat.JPEG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            savedBitmap.recycle();
            return resultPath;
        }

        int getMaskIndex(int resId) {
            for (int i = BACKGROUND_PATTERN; i < this.maskResIdList.length; i += INVALID_POINTER_ID) {
                if (resId == this.maskResIdList[i]) {
                    return i;
                }
            }
            return PATTERN_SENTINEL;
        }

        private void createShapeList(int shapeCount, int width, int height) {
            this.shapeLayoutList.clear();
            this.smallestDistanceList.clear();
            Collage collage = Collage.CreateCollage(shapeCount, width, width, isScrapBook);
            int size = ((CollageLayout) collage.collageLayoutList.get(BACKGROUND_PATTERN)).shapeList.size();
            Log.e(CollageActivity.TAG, "bitmapList.length " + bitmapList.length);
            int i = BACKGROUND_PATTERN;
            while (i < collage.collageLayoutList.size()) {
                Shape[] shapeArray = new Shape[size];
                for (int j = BACKGROUND_PATTERN; j < shapeCount; j += INVALID_POINTER_ID) {
                    boolean masked = false;
                    int resId = BACKGROUND_PATTERN;
                    if (!(((CollageLayout) collage.collageLayoutList.get(i)).maskPairList == null || ((CollageLayout) collage.collageLayoutList.get(i)).maskPairList.isEmpty())) {
                        for (MaskPair maskPair : ((CollageLayout) collage.collageLayoutList.get(i)).maskPairList) {
                            if (j == maskPair.index) {
                                masked = true;
                                resId = maskPair.id;
                            }
                        }
                    }
                    if (masked) {
                        Bitmap maskBitmap = null;
                        int maskIndex = getMaskIndex(resId);
                        if (maskIndex >= 0) {
                            if (this.maskBitmapArray == null) {
                                this.maskBitmapArray = new Bitmap[this.maskResIdList.length];
                            }
                            if (this.maskBitmapArray[maskIndex] == null) {
                                this.maskBitmapArray[maskIndex] = loadMaskBitmap2(resId);
                                Log.e(CollageActivity.TAG, "load mask bitmap from factory");
                            } else {
                                Log.e(CollageActivity.TAG, "load mask bitmap from pool");
                            }
                            maskBitmap = this.maskBitmapArray[maskIndex];
                        }
                        shapeArray[j] = new Shape((PointF[]) ((CollageLayout) collage.collageLayoutList.get(i)).shapeList.get(j), bitmapList[j], null, this.offsetX, this.offsetY, maskBitmap, isScrapBook, j, false, btmDelete, btmScale, this.screenWidth);
                        if (isScrapBook) {
                            shapeArray[j].initScrapBook(npd);
                        }
                    } else {
                        int i2 = j;
                        shapeArray[j] = new Shape((PointF[]) ((CollageLayout) collage.collageLayoutList.get(i)).shapeList.get(j), bitmapList[j], ((CollageLayout) collage.collageLayoutList.get(i)).getexceptionIndex(j), this.offsetX, this.offsetY, isScrapBook, i2, false, btmDelete, btmScale, this.screenWidth);
                        if (isScrapBook) {
                            shapeArray[j].initScrapBook(npd);
                        }
                    }
                }
                this.smallestDistanceList.add(Float.valueOf(smallestDistance(shapeArray)));
                ShapeLayout shapeLayout = new ShapeLayout(shapeArray);
                shapeLayout.setClearIndex(((CollageLayout) collage.collageLayoutList.get(i)).getClearIndex());
                this.shapeLayoutList.add(shapeLayout);
                i += INVALID_POINTER_ID;
            }
            if (!isScrapBook) {
                if (shapeCount != INVALID_POINTER_ID) {
                    for (int index = BACKGROUND_PATTERN; index < this.shapeLayoutList.size(); index += INVALID_POINTER_ID) {
                        setPathPadding(index, (float) getResources().getInteger(R.integer.default_space_value));
                        for (i = BACKGROUND_PATTERN; i < ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr.length; i += INVALID_POINTER_ID) {
                            ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr[i].setScaleMatrix(INVALID_POINTER_ID);
                        }
                    }
                    setCollageSize(this.sizeMatrix, getResources().getInteger(R.integer.default_ssize_value));
                } else if (bitmapList.length == INVALID_POINTER_ID) {
                    setCollageSize(this.sizeMatrix, getResources().getInteger(R.integer.default_ssize_value));
                }
            }
        }

        private int setShapeScaleMatrix(int mode) {
            if (this.shapeIndex < 0) {
                return PATTERN_SENTINEL;
            }
            int message = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].setScaleMatrix(mode);
            invalidate();
            return message;
        }

        private void deleteBitmap(int index, int width, int height) {
            Log.e(CollageActivity.TAG, "index" + index);
            Shape[] scrapBookTemp = ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr;
            if (index >= 0 && index < ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr.length) {
                int i;
                int newSize = ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr.length + PATTERN_SENTINEL;
                Bitmap[] currentBitmapListTemp = new Bitmap[newSize];
                Bitmap[] bitmapListTemp = new Bitmap[newSize];
                int j = BACKGROUND_PATTERN;
                for (i = BACKGROUND_PATTERN; i < currentBitmapListTemp.length + INVALID_POINTER_ID; i += INVALID_POINTER_ID) {
                    if (i != index) {
                        currentBitmapListTemp[j] = ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr[i].getBitmap();
                        bitmapListTemp[j] = bitmapList[i];
                        j += INVALID_POINTER_ID;
                    }
                }
                bitmapList[index].recycle();
                ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr[index].getBitmap().recycle();
                this.shapeLayoutList.clear();
                this.smallestDistanceList.clear();
                Collage collage = Collage.CreateCollage(newSize, width, width, isScrapBook);
                int size = ((CollageLayout) collage.collageLayoutList.get(BACKGROUND_PATTERN)).shapeList.size();
                bitmapList = bitmapListTemp;
                i = BACKGROUND_PATTERN;
                while (i < collage.collageLayoutList.size()) {
                    Shape[] shapeArray = new Shape[size];
                    for (j = BACKGROUND_PATTERN; j < currentBitmapListTemp.length; j += INVALID_POINTER_ID) {
                        boolean masked = false;
                        int resId = BACKGROUND_PATTERN;
                        if (!(((CollageLayout) collage.collageLayoutList.get(i)).maskPairList == null || ((CollageLayout) collage.collageLayoutList.get(i)).maskPairList.isEmpty())) {
                            for (MaskPair maskPair : ((CollageLayout) collage.collageLayoutList.get(i)).maskPairList) {
                                if (j == maskPair.index) {
                                    masked = true;
                                    resId = maskPair.id;
                                }
                            }
                        }
                        if (masked) {
                            Bitmap maskBitmap = null;
                            int maskIndez = getMaskIndex(resId);
                            if (maskIndez >= 0) {
                                if (this.maskBitmapArray == null) {
                                    this.maskBitmapArray = new Bitmap[this.maskResIdList.length];
                                }
                                if (this.maskBitmapArray[maskIndez] == null) {
                                    this.maskBitmapArray[maskIndez] = loadMaskBitmap2(resId);
                                    Log.e(CollageActivity.TAG, "load mask bitmap from factory");
                                } else {
                                    Log.e(CollageActivity.TAG, "load mask bitmap from pool");
                                }
                                maskBitmap = this.maskBitmapArray[maskIndez];
                            }
                            shapeArray[j] = new Shape((PointF[]) ((CollageLayout) collage.collageLayoutList.get(i)).shapeList.get(j), currentBitmapListTemp[j], null, this.offsetX, this.offsetY, maskBitmap, isScrapBook, j, true, btmDelete, btmScale, this.screenWidth);
                            if (isScrapBook) {
                                shapeArray[j].initScrapBook(npd);
                            }
                        } else {
                            int i2 = j;
                            shapeArray[j] = new Shape((PointF[]) ((CollageLayout) collage.collageLayoutList.get(i)).shapeList.get(j), currentBitmapListTemp[j], ((CollageLayout) collage.collageLayoutList.get(i)).getexceptionIndex(j), this.offsetX, this.offsetY, isScrapBook, i2, true, btmDelete, btmScale, this.screenWidth);
                            if (isScrapBook) {
                                shapeArray[j].initScrapBook(npd);
                            }
                        }
                    }
                    if (isScrapBook) {
                        for (int k = BACKGROUND_PATTERN; k < scrapBookTemp.length; k += INVALID_POINTER_ID) {
                            if (k < index) {
                                shapeArray[k].bitmapMatrix.set(scrapBookTemp[k].bitmapMatrix);
                            }
                            if (k > index) {
                                shapeArray[k + PATTERN_SENTINEL].bitmapMatrix.set(scrapBookTemp[k].bitmapMatrix);
                            }
                        }
                    }
                    ShapeLayout shapeLayout = new ShapeLayout(shapeArray);
                    shapeLayout.setClearIndex(((CollageLayout) collage.collageLayoutList.get(i)).getClearIndex());
                    this.shapeLayoutList.add(shapeLayout);
                    this.smallestDistanceList.add(Float.valueOf(smallestDistance(shapeArray)));
                    i += INVALID_POINTER_ID;
                }
                this.currentCollageIndex = BACKGROUND_PATTERN;
                collageAdapter.selectedPosition = BACKGROUND_PATTERN;
                collageAdapter.setData(Collage.collageIconArray[newSize + PATTERN_SENTINEL]);
                collageAdapter.notifyDataSetChanged();
                if (!isScrapBook) {
                    updateShapeListForRatio(width, height);
                }
                unselectShapes();
                for (i = BACKGROUND_PATTERN; i < ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr.length; i += INVALID_POINTER_ID) {
                    Log.e(CollageActivity.TAG, "i " + i + "is recylced " + ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr[i].getBitmap().isRecycled());
                }
                invalidate();
                if (currentBitmapListTemp.length == INVALID_POINTER_ID) {
                    setVisibilityForSingleImage();
                }
                if (newSize == INVALID_POINTER_ID) {
                    setPathPadding(BACKGROUND_PATTERN, 0.0f);
                    if (this.sizeScale == 1.0f && !isScrapBook) {
                        setCollageSize(this.sizeMatrix, getResources().getInteger(R.integer.default_ssize_value));
                    }
                }
            }
        }

        Bitmap loadMaskBitmapx(int resId) {
            new Options().inPreferredConfig = Config.ARGB_8888;
            Bitmap source = BitmapFactory.decodeResource(getResources(), resId);
            Bitmap mask = source.extractAlpha();
            source.recycle();
            return mask;
        }

        Bitmap loadMaskBitmap2(int resId) {
            return convertToAlphaMask(BitmapFactory.decodeResource(getResources(), resId));
        }

        private Bitmap convertToAlphaMask(Bitmap b) {
            Bitmap a = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Config.ALPHA_8);
            new Canvas(a).drawBitmap(b, 0.0f, 0.0f, null);
            b.recycle();
            return a;
        }

        public float smallestDistance(Shape[] shapeArray) {
            float smallestDistance = shapeArray[BACKGROUND_PATTERN].smallestDistance();
            for (int i = BACKGROUND_PATTERN; i < shapeArray.length; i += INVALID_POINTER_ID) {
                float distance = shapeArray[i].smallestDistance();
                if (distance < smallestDistance) {
                    smallestDistance = distance;
                }
            }
            return smallestDistance;
        }

        private void updateShapeListForRatio(int width, int height) {
            int shapeCount = ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr.length;
            PointF scale = getRatio();
            calculateOffset();
            Collage collage = Collage.CreateCollage(shapeCount, (int) (scale.x * ((float) width)), (int) (((float) width) * scale.y), isScrapBook);
            this.smallestDistanceList.clear();
            for (int index = BACKGROUND_PATTERN; index < this.shapeLayoutList.size(); index += INVALID_POINTER_ID) {
                if (shapeCount == INVALID_POINTER_ID) {
                    ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr[BACKGROUND_PATTERN].changeRatio((PointF[]) ((CollageLayout) collage.collageLayoutList.get(index)).shapeList.get(BACKGROUND_PATTERN), null, this.offsetX, this.offsetY, isScrapBook, BACKGROUND_PATTERN, (int) (scale.x * ((float) width)), (int) (((float) width) * scale.y));
                } else {
                    for (int j = BACKGROUND_PATTERN; j < shapeCount; j += INVALID_POINTER_ID) {
                        ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr[j].changeRatio((PointF[]) ((CollageLayout) collage.collageLayoutList.get(index)).shapeList.get(j), null, this.offsetX, this.offsetY, isScrapBook, j, (int) (scale.x * ((float) width)), (int) (((float) width) * scale.y));
                    }
                }
                this.smallestDistanceList.add(Float.valueOf(smallestDistance(((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr)));
                setPathPadding(index, this.paddingDistance);
                if (!isScrapBook) {
                    for (int k = BACKGROUND_PATTERN; k < ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr.length; k += INVALID_POINTER_ID) {
                        ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr[k].setScaleMatrix(INVALID_POINTER_ID);
                    }
                }
            }
            setCornerRadius(this.cornerRadius);
            if (this.blurBitmap != null) {
                setBlurRect2((float) this.blurBitmap.getWidth(), (float) this.blurBitmap.getHeight());
            }
            postInvalidate();
        }

        PointF getRatio() {
            this.yscale = 1.0f;
            this.xscale = 1.0f;
            this.yscale = mulY / mulX;
            if (!isScrapBook && this.yscale > RATIO_CONSTANT) {
                this.xscale = RATIO_CONSTANT / this.yscale;
                this.yscale = RATIO_CONSTANT;
            }
            return new PointF(this.xscale, this.yscale);
        }

        private void updateShapeListForFilterBitmap(Bitmap bitmap) {
            if (this.shapeIndex >= 0) {
                for (int i = BACKGROUND_PATTERN; i < this.shapeLayoutList.size(); i += INVALID_POINTER_ID) {
                    ((ShapeLayout) this.shapeLayoutList.get(i)).shapeArr[this.shapeIndex].setBitmap(bitmap, true);
                }
            }
        }

        void updateParamList(Parameter p) {
            if (this.shapeIndex >= 0) {
                parameterList[this.shapeIndex] = new Parameter(p);
            }
        }

        private void swapBitmaps(int index1, int index2) {
            Bitmap bitmap1 = ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr[index1].getBitmap();
            Bitmap bitmap2 = ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr[index2].getBitmap();
            for (int i = BACKGROUND_PATTERN; i < this.shapeLayoutList.size(); i += INVALID_POINTER_ID) {
                ((ShapeLayout) this.shapeLayoutList.get(i)).shapeArr[index1].setBitmap(bitmap2, false);
                ((ShapeLayout) this.shapeLayoutList.get(i)).shapeArr[index2].setBitmap(bitmap1, false);
            }
            Bitmap temp = bitmapList[index1];
            bitmapList[index1] = bitmapList[index2];
            bitmapList[index2] = temp;
            Parameter tempParam = parameterList[index1];
            parameterList[index1] = parameterList[index2];
            parameterList[index2] = tempParam;
            float sd = ((Float) this.smallestDistanceList.get(index1)).floatValue();
            this.smallestDistanceList.set(index1, (Float) this.smallestDistanceList.get(index2));
            this.smallestDistanceList.set(index2, Float.valueOf(sd));
            selectSwapTextView.setVisibility(CollageActivity.INDEX_COLLAGE_BLUR);
            unselectShapes();
        }

        void setCurrentCollageIndex(int index) {
            this.currentCollageIndex = index;
            if (this.currentCollageIndex >= this.shapeLayoutList.size()) {
                this.currentCollageIndex = BACKGROUND_PATTERN;
            }
            if (this.currentCollageIndex < 0) {
                this.currentCollageIndex = this.shapeLayoutList.size() + PATTERN_SENTINEL;
            }
            setCornerRadius(this.cornerRadius);
            setPathPadding(this.currentCollageIndex, this.paddingDistance);
        }

        private void setCornerRadius(float radius) {
            this.cornerRadius = radius;
            CornerPathEffect corEffect = new CornerPathEffect(radius);
            for (int i = BACKGROUND_PATTERN; i < ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr.length; i += INVALID_POINTER_ID) {
                ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i].setRadius(corEffect);
            }
            postInvalidate();
        }

        private void setPathPadding(int index, float distance) {
            this.paddingDistance = distance;
            for (int i = BACKGROUND_PATTERN; i < ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr.length; i += INVALID_POINTER_ID) {
                ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr[i].scalePath((((Float) this.smallestDistanceList.get(index)).floatValue() / 250.0f) * distance, (float) this.screenWidth, (float) this.screenWidth);
                if (!isScrapBook) {
                    ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr[i].checkScaleBounds();
                    ((ShapeLayout) this.shapeLayoutList.get(index)).shapeArr[i].checkBoundries();
                }
            }
            postInvalidate();
        }

        private void setCollageSize(Matrix matrix, int progress) {
            matrix.reset();
            this.sizeScale = calculateSize((float) progress);
            matrix.postScale(this.sizeScale, this.sizeScale, (((float) (this.offsetX + this.offsetX)) + (((float) width) * this.xscale)) / 2.0f, (((float) (this.offsetY + this.offsetY)) + (((float) width) * this.yscale)) / 2.0f);
            invalidate();
        }

        float calculateSize(float progress) {
            return 1.0f - (progress / 200.0f);
        }

        int calculateSizeProgress(float size) {
            int progress = 200 - Math.round(200.0f * size);
            if (progress < 0) {
                progress = BACKGROUND_PATTERN;
            }
            if (progress > 100) {
                return 100;
            }
            return progress;
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
            this.patternPaint.setShader(new BitmapShader(this.patternBitmap, TileMode.REPEAT, TileMode.REPEAT));
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

        public void setFrame(int index) {
            if (this.frameRect == null) {
                this.frameRect = new RectF(0.0f, 0.0f, (float) this.screenWidth, (float) this.screenWidth);
            }
            if (!(this.frameBitmap == null || this.frameBitmap.isRecycled())) {
                this.frameBitmap.recycle();
                this.frameBitmap = null;
            }
            if (index != 0) {
                this.frameBitmap = BitmapFactory.decodeResource(getResources(), LibUtility.borderRes[index]);
                postInvalidate();
            }
        }

        public void startAnimator() {
            if (seekbarSize != null) {
                this.animSizeSeekbarProgress = seekbarSize.getProgress();
            } else {
                this.animSizeSeekbarProgress = BACKGROUND_PATTERN;
            }
            this.sizeMatrixSaved = new Matrix(this.sizeMatrix);
            this.animationCount = BACKGROUND_PATTERN;
            this.animate = true;
            removeCallbacks(this.animator);
            postDelayed(this.animator, 150);
        }

        int animSize(int value) {
            int res;
            if (value < this.animHalfTime) {
                res = value;
            } else {
                res = this.animationLimit - value;
            }
            return this.animSizeSeekbarProgress + Math.round((float) (res * CollageActivity.INDEX_COLLAGE_SPACE));
        }

        public void onDraw(Canvas canvas) {
            int width = getWidth();
            int height = getHeight();
            canvas.save();
            this.drawingAreaRect.set((float) this.offsetX, (float) this.offsetY, ((float) this.offsetX) + (((float) width) * this.xscale), ((float) this.offsetY) + (((float) width) * this.yscale));
            canvas.drawPaint(this.paintGray);
            if (this.backgroundMode == 0) {
                canvas.drawRect(this.drawingAreaRect, this.patternPaint);
            }
            if (!(this.blurBitmap == null || this.blurBitmap.isRecycled() || this.backgroundMode != INVALID_POINTER_ID)) {
                this.blurRectDst.set(this.drawingAreaRect);
                canvas.drawBitmap(this.blurBitmap, this.blurRectSrc, this.blurRectDst, this.paint);
            }
            if (!isScrapBook) {
                canvas.setMatrix(this.sizeMatrix);
            }
            int j = BACKGROUND_PATTERN;
            if (!isScrapBook || showText) {
                j = canvas.saveLayer(0.0f, 0.0f, ((float) width) / this.sizeScale, ((float) height) / this.sizeScale, null, Canvas.ALL_SAVE_FLAG);
            }
            int i = BACKGROUND_PATTERN;
            while (i < ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr.length) {
                boolean drawPorterClear = false;
                if (i == ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).getClearIndex()) {
                    drawPorterClear = true;
                }
                if (isScrapBook) {
                    ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i].drawShapeForScrapBook(canvas, width, height, i == this.shapeIndex, this.orthogonal);
                } else {
                    ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i].drawShape(canvas, width, height, j, drawPorterClear);
                }
                i += INVALID_POINTER_ID;
            }
            if (!isScrapBook && this.shapeIndex >= 0 && ((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr.length > INVALID_POINTER_ID) {
                canvas.drawRect(((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].bounds, this.borderPaint);
            }
            if (showText) {
                canvas.restoreToCount(j);
                for (i = BACKGROUND_PATTERN; i < textDataList.size(); i += INVALID_POINTER_ID) {
                    this.textMatrix.set(((TextData) textDataList.get(i)).imageSaveMatrix);
                    canvas.setMatrix(this.textMatrix);
                    canvas.drawText(((TextData) textDataList.get(i)).message, ((TextData) textDataList.get(i)).xPos, ((TextData) textDataList.get(i)).yPos, ((TextData) textDataList.get(i)).textPaint);
                    canvas.setMatrix(this.identityMatrix);
                }
            }
            if (!(this.frameBitmap == null || this.frameBitmap.isRecycled())) {
                canvas.drawBitmap(this.frameBitmap, null, this.frameRect, this.paint);
            }
            if (isScrapBook) {
                canvas.restore();
                this.above.set(0.0f, 0.0f, (float) canvas.getWidth(), this.drawingAreaRect.top);
                this.left.set(0.0f, this.drawingAreaRect.top, this.drawingAreaRect.left, this.drawingAreaRect.bottom);
                this.right.set(this.drawingAreaRect.right, this.drawingAreaRect.top, (float) canvas.getWidth(), this.drawingAreaRect.bottom);
                this.bottom.set(0.0f, this.drawingAreaRect.bottom, (float) canvas.getWidth(), (float) canvas.getHeight());
                canvas.drawRect(this.above, this.paintGray);
                canvas.drawRect(this.left, this.paintGray);
                canvas.drawRect(this.right, this.paintGray);
                canvas.drawRect(this.bottom, this.paintGray);
            }
        }

        public boolean onTouchEvent(MotionEvent ev) {
            this.mScaleDetector.onTouchEvent(ev);
            this.mTouchDetector.onTouchEvent(ev);
            if (isScrapBook) {
                mRotationDetector.onTouchEvent(ev);
            }
            int action = ev.getAction();
            float x;
            float y;
            int pointerIndex;
            switch (action & MotionEventCompat.ACTION_MASK) {
                case BACKGROUND_PATTERN /*0*/:
                    this.previousIndex = this.shapeIndex;
                    x = ev.getX();
                    y = ev.getY();
                    this.mLastTouchX = x;
                    this.mLastTouchY = y;
                    this.orthogonal = false;
                    this.mActivePointerId = ev.getPointerId(BACKGROUND_PATTERN);
                    if (isScrapBook && this.shapeIndex >= 0) {
                        this.zoomStart.set(x, y);
                        this.pts = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].getMappedCenter();
                        if (this.pts != null) {
                            this.startAngle = -Utility.pointToAngle(x, y, this.pts[BACKGROUND_PATTERN], this.pts[INVALID_POINTER_ID]);
                        }
                        this.isInCircle = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].isInCircle(x, y);
                        this.isOnCross = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].isOnCross(x, y);
                        break;
                    }
                    selectCurrentShape(x, y, false);
                    break;
                case INVALID_POINTER_ID /*1*/:
                    this.orthogonal = false;
                    this.mActivePointerId = INVALID_POINTER_ID;
                    if (this.isOnCross) {
                        createDeleteDialog();
                    }
                    this.isInCircle = false;
                    this.isOnCross = false;
                    invalidate();
                    break;
                case CollageActivity.INDEX_COLLAGE_SPACE /*2*/:
                    if (!this.isOnCross) {
                        pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                        x = ev.getX(pointerIndex);
                        y = ev.getY(pointerIndex);
                        if (this.shapeIndex < 0) {
                            selectCurrentShape(x, y, false);
                        }
                        if (this.shapeIndex >= 0) {
                            if (!isScrapBook || !this.isInCircle) {
                                ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].bitmapMatrixTranslate(x - this.mLastTouchX, y - this.mLastTouchY);
                                this.mLastTouchX = x;
                                this.mLastTouchY = y;
                                invalidate();
                                break;
                            }
                            this.pts = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].getMappedCenter();
                            float currentAngle = -Utility.pointToAngle(x, y, this.pts[BACKGROUND_PATTERN], this.pts[INVALID_POINTER_ID]);
                            Log.d(CollageActivity.TAG, "currentAngle " + Float.toString(currentAngle));
                            float rotation = getMatrixRotation(((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].bitmapMatrix);
                            if ((rotation == 0.0f || rotation == 90.0f || rotation == 180.0f || rotation == -180.0f || rotation == -90.0f) && Math.abs(this.startAngle - currentAngle) < 4.0f) {
                                this.orthogonal = true;
                            } else {
                                if (Math.abs((rotation - this.startAngle) + currentAngle) < 4.0f) {
                                    currentAngle = this.startAngle - rotation;
                                    this.orthogonal = true;
                                    Log.d(CollageActivity.TAG, "aaaaa " + Float.toString(rotation));
                                } else {
                                    if (Math.abs(90.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                                        currentAngle = (90.0f + this.startAngle) - rotation;
                                        this.orthogonal = true;
                                        Log.d(CollageActivity.TAG, "bbbbb " + Float.toString(rotation));
                                    } else {
                                        if (Math.abs(180.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                                            currentAngle = (180.0f + this.startAngle) - rotation;
                                            this.orthogonal = true;
                                            Log.d(CollageActivity.TAG, "cccc " + Float.toString(rotation));
                                        } else {
                                            if (Math.abs(-180.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                                                currentAngle = (-180.0f + this.startAngle) - rotation;
                                                this.orthogonal = true;
                                            } else {
                                                if (Math.abs(-90.0f - ((rotation - this.startAngle) + currentAngle)) < 4.0f) {
                                                    currentAngle = (-90.0f + this.startAngle) - rotation;
                                                    this.orthogonal = true;
                                                    Log.d(CollageActivity.TAG, "dddd " + Float.toString(rotation));
                                                } else {
                                                    this.orthogonal = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].bitmapMatrixRotate(this.startAngle - currentAngle);
                                this.startAngle = currentAngle;
                            }
                            float scaley = ((float) Math.sqrt((double) (((x - this.pts[BACKGROUND_PATTERN]) * (x - this.pts[BACKGROUND_PATTERN])) + ((y - this.pts[INVALID_POINTER_ID]) * (y - this.pts[INVALID_POINTER_ID]))))) / ((float) Math.sqrt((double) (((this.zoomStart.x - this.pts[BACKGROUND_PATTERN]) * (this.zoomStart.x - this.pts[BACKGROUND_PATTERN])) + ((this.zoomStart.y - this.pts[INVALID_POINTER_ID]) * (this.zoomStart.y - this.pts[INVALID_POINTER_ID])))));
                            float scale = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].getScale();
                            if (scale >= this.MIN_ZOOM || (scale < this.MIN_ZOOM && scaley > 1.0f)) {
                                ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].bitmapMatrixScaleScrapBook(scaley, scaley);
                                this.zoomStart.set(x, y);
                            }
                            invalidate();
                            return true;
                        }
                    }
                    break;
                case CollageActivity.INDEX_COLLAGE_RATIO:
                    this.mActivePointerId = INVALID_POINTER_ID;
                    this.isInCircle = false;
                    this.isOnCross = false;
                    break;
                case CollageActivity.TAB_SIZE:
                    this.finalAngle = 0.0f;
                    pointerIndex = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & action) >> 8;
                    if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
                        int newPointerIndex = pointerIndex == 0 ? INVALID_POINTER_ID : BACKGROUND_PATTERN;
                        this.mLastTouchX = ev.getX(newPointerIndex);
                        this.mLastTouchY = ev.getY(newPointerIndex);
                        this.mActivePointerId = ev.getPointerId(newPointerIndex);
                        break;
                    }
                    break;
            }
            return true;
        }

        private void selectCurrentShapeScrapBook(float x, float y, boolean isSingleTap) {
            int i;
            int length = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr.length;
            boolean isSelected = false;
            for (i = length + PATTERN_SENTINEL; i >= 0; i += PATTERN_SENTINEL) {
                if (((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i].isScrapBookSelected(x, y)) {
                    this.shapeIndex = i;
                    isSelected = true;
                    break;
                }
            }
            if (this.previousIndex == this.shapeIndex && isSingleTap) {
                unselectShapes();
            } else if (!isSelected) {
                unselectShapes();
            } else if (selectImageForAdj) {
                openFilterFragment();
            } else if (this.shapeIndex >= 0 && this.shapeIndex < length) {
                Shape shapeTemp = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex];
                Bitmap btmTemp = bitmapList[this.shapeIndex];
                Parameter prmTemp = parameterList[this.shapeIndex];
                for (i = BACKGROUND_PATTERN; i < length; i += INVALID_POINTER_ID) {
                    if (i >= this.shapeIndex) {
                        if (i < length + PATTERN_SENTINEL) {
                            ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i] = ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i + INVALID_POINTER_ID];
                            bitmapList[i] = bitmapList[i + INVALID_POINTER_ID];
                            parameterList[i] = parameterList[i + INVALID_POINTER_ID];
                        } else {
                            ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i] = shapeTemp;
                            bitmapList[i] = btmTemp;
                            parameterList[i] = prmTemp;
                        }
                    }
                }
                if (this.previousIndex == this.shapeIndex) {
                    this.previousIndex = length + PATTERN_SENTINEL;
                } else if (this.previousIndex > this.shapeIndex) {
                    this.previousIndex += PATTERN_SENTINEL;
                }
                this.shapeIndex = length + PATTERN_SENTINEL;
                if (((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr.length > 0) {
                    contextFooter.setVisibility(BACKGROUND_PATTERN);
                    setSelectedTab(CollageActivity.INDEX_COLLAGE_INVISIBLE_VIEW);
                }
            }
            if (this.shapeIndex >= 0) {
                ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].bitmapMatrixgGetValues(this.matrixValues);
                this.mScaleFactor = this.matrixValues[BACKGROUND_PATTERN];
            }
            postInvalidate();
        }

        private void selectCurrentShape(float x, float y, boolean isSingleTap) {
            if (isScrapBook) {
                selectCurrentShapeScrapBook(x, y, isSingleTap);
            } else {
                selectCurrentShapeCollage(x, y, isSingleTap);
            }
        }

        private void selectCurrentShapeCollage(float x, float y, boolean isSingleTap) {
            int swapIndex = this.shapeIndex;
            for (int i = BACKGROUND_PATTERN; i < ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr.length; i += INVALID_POINTER_ID) {
                if (((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[i].region.contains((int) x, (int) y)) {
                    this.shapeIndex = i;
                }
            }
            if (selectImageForAdj) {
                openFilterFragment();
            } else if (swapMode) {
                Log.e(CollageActivity.TAG, "PRE SWAP");
                if (swapIndex != this.shapeIndex && swapIndex > PATTERN_SENTINEL && this.shapeIndex > PATTERN_SENTINEL) {
                    Log.e(CollageActivity.TAG, "SWAP");
                    swapBitmaps(this.shapeIndex, swapIndex);
                    swapMode = false;
                }
            } else if (this.previousIndex == this.shapeIndex && isSingleTap) {
                unselectShapes();
            } else if (((ShapeLayout) this.shapeLayoutList.get(BACKGROUND_PATTERN)).shapeArr.length > 0) {
                contextFooter.setVisibility(BACKGROUND_PATTERN);
                setSelectedTab(CollageActivity.INDEX_COLLAGE_INVISIBLE_VIEW);
                Log.e(CollageActivity.TAG, "VISIBLE");
            }
            if (this.shapeIndex >= 0) {
                ((ShapeLayout) this.shapeLayoutList.get(this.currentCollageIndex)).shapeArr[this.shapeIndex].bitmapMatrixgGetValues(this.matrixValues);
                this.mScaleFactor = this.matrixValues[BACKGROUND_PATTERN];
            }
            postInvalidate();
        }

        void unselectShapes() {
            contextFooter.setVisibility(INVISIBLE);
            this.shapeIndex = PATTERN_SENTINEL;
            Log.e(CollageActivity.TAG, "unselectShapes");
            postInvalidate();
        }

        public void openFilterFragment() {
            selectFilterTextView.setVisibility(View.INVISIBLE);
            selectImageForAdj = false;
            if (this.shapeIndex >= 0) {
                if (!(parameterList[this.shapeIndex] == null || parameterList[this.shapeIndex].getTiltContext() == null)) {
                    Log.e(CollageActivity.TAG, "tilt mode " + parameterList[this.shapeIndex].getTiltContext().mode);
                }
                fullEffectFragment.setBitmapWithParameter(bitmapList[this.shapeIndex], parameterList[this.shapeIndex]);
                setVisibilityOfFilterHorizontalListview(true);
            }
        }

        float getMatrixRotation(Matrix matrix) {
            matrix.getValues(this.values);
            return (float) Math.round(Math.atan2((double) this.values[INVALID_POINTER_ID], (double) this.values[BACKGROUND_PATTERN]) * 57.29577951308232d);
        }

        public void setBlurBitmap(int radius) {
            if (this.blurBuilderNormal == null) {
                this.blurBuilderNormal = new BlurBuilderNormal();
            }
            this.backgroundMode = INVALID_POINTER_ID;
            this.blurBitmap = this.blurBuilderNormal.createBlurBitmapNDK(bitmapList[BACKGROUND_PATTERN], radius);
            this.blurRadius = this.blurBuilderNormal.getBlur();
            setBlurRect2((float) this.blurBitmap.getWidth(), (float) this.blurBitmap.getHeight());
            postInvalidate();
        }

        void setMatrixCenterEx(Matrix matrix, float btmwidth, float btmheight) {
            matrix.reset();
            float bitmapScale = Math.max((((float) width) * this.xscale) / btmwidth, (((float) width) * this.yscale) / btmheight);
            float bitmapOffsetX = ((float) this.offsetX) + (((((float) width) * this.xscale) - (bitmapScale * btmwidth)) / 2.0f);
            float bitmapOffsetY = ((float) this.offsetY) + (((((float) width) * this.yscale) - (bitmapScale * btmheight)) / 2.0f);
            matrix.postScale(bitmapScale, bitmapScale);
            matrix.postTranslate(bitmapOffsetX, bitmapOffsetY);
        }

        void setBlurRect(float btmwidth, float btmheight) {
            float w;
            float h;
            float scaleRatio = mulX / mulY;
            if (btmwidth / btmheight > scaleRatio) {
                w = (float) ((int) (btmheight * scaleRatio));
                h = (float) ((int) btmheight);
            } else {
                w = (float) ((int) btmwidth);
                h = (float) ((int) (btmwidth / scaleRatio));
            }
            int l = (int) ((btmwidth - w) / 2.0f);
            int t = (int) ((btmheight - h) / 2.0f);
            this.blurRectSrc.set(l, t, (int) (((float) l) + w), (int) (((float) t) + h));
        }

        void setBlurRect2(float btmwidth, float btmheight) {
            float w;
            float h;
            if ((mulY * btmwidth) / mulX < btmheight) {
                w = (float) ((int) btmwidth);
                h = (mulY * btmwidth) / mulX;
            } else {
                w = (((float) ((int) mulX)) * btmheight) / mulY;
                h = (float) ((int) btmheight);
            }
            int l = (int) ((btmwidth - w) / 2.0f);
            int t = (int) ((btmheight - h) / 2.0f);
            this.blurRectSrc.set(l, t, (int) (((float) l) + w), (int) (((float) t) + h));
        }

        /* renamed from: com.lyrebirdstudio.collagelib.CollageActivity.CollageView.1 */
        class C05731 implements Runnable {
            C05731() {
            }

            public void run() {
                boolean scheduleNewFrame = false;
                int iter = ((int) (((float) (System.nanoTime() - CollageView.this.startTime)) / 1000000.0f)) / CollageView.this.animationDurationLimit;
                if (iter <= 0) {
                    iter = CollageView.INVALID_POINTER_ID;
                }
                CollageView collageView;
                if (CollageView.this.animationCount == 0) {
                    collageView = CollageView.this;
                    collageView.animationCount += CollageView.INVALID_POINTER_ID;
                } else {
                    collageView = CollageView.this;
                    collageView.animationCount += iter;
                }
                CollageView.this.setCollageSize(CollageView.this.sizeMatrix, CollageView.this.animSize(CollageView.this.animationCount));
                if (CollageView.this.animationCount < CollageView.this.animationLimit) {
                    scheduleNewFrame = true;
                } else {
                    CollageView.this.animate = false;
                }
                if (scheduleNewFrame) {
                    CollageView.this.postDelayed(this, (long) CollageView.this.frameDuration);
                } else {
                    CollageView.this.sizeMatrix.set(CollageView.this.sizeMatrixSaved);
                }
                ((ShapeLayout) CollageView.this.shapeLayoutList.get(CollageView.this.currentCollageIndex)).shapeArr[CollageView.BACKGROUND_PATTERN].f508r.roundOut(CollageView.this.rectAnim);
                CollageView.this.invalidate(CollageView.this.rectAnim);
                CollageView.this.startTime = System.nanoTime();
            }
        }

        class MyGestureListener extends SimpleOnGestureListener {
            private static final String DEBUG_TAG = "Gestures";

            MyGestureListener() {
            }

            public boolean onSingleTapConfirmed(MotionEvent event) {
                Log.d(DEBUG_TAG, "onSingleTapConfirmed: ");
                return true;
            }

            public boolean onSingleTapUp(MotionEvent event) {
                Log.d(DEBUG_TAG, "onSingleTapUp: ");
                if (!CollageView.this.isOnCross) {
                    collageView.selectCurrentShape(event.getX(), event.getY(), true);
                }
                return true;
            }
        }

        private class ScaleListener extends SimpleOnScaleGestureListener {
            private ScaleListener() {
            }

            public boolean onScale(ScaleGestureDetector detector) {
                if (CollageView.this.shapeIndex >= 0) {
                    CollageView.this.mScaleFactor = detector.getScaleFactor();
                    detector.isInProgress();
                    CollageView.this.mScaleFactor = Math.max(0.1f, Math.min(CollageView.this.mScaleFactor, 5.0f));
                    CollageView.this.scaleShape = ((ShapeLayout) CollageView.this.shapeLayoutList.get(CollageView.this.currentCollageIndex)).shapeArr[CollageView.this.shapeIndex];
                    if (isScrapBook) {
                        CollageView.this.scaleShape.bitmapMatrixScaleScrapBook(CollageView.this.mScaleFactor, CollageView.this.mScaleFactor);
                    } else {
                        CollageView.this.scaleShape.bitmapMatrixScale(CollageView.this.mScaleFactor, CollageView.this.mScaleFactor, CollageView.this.scaleShape.bounds.centerX(), CollageView.this.scaleShape.bounds.centerY());
                    }
                    CollageView.this.invalidate();
                    CollageView.this.requestLayout();
                }
                return true;
            }
        }

        /* renamed from: com.lyrebirdstudio.collagelib.CollageActivity.CollageView.2 */
        class C09472 implements RotationGestureDetector.OnRotationGestureListener {
            C09472() {
            }

            public void OnRotation(RotationGestureDetector rotationDetector) {
                if (CollageView.this.shapeIndex >= 0) {
                    float angle = rotationDetector.getAngle();
                    CollageView.this.scaleShape = ((ShapeLayout) CollageView.this.shapeLayoutList.get(CollageView.this.currentCollageIndex)).shapeArr[CollageView.this.shapeIndex];
                    float rotation = CollageView.this.getMatrixRotation(CollageView.this.scaleShape.bitmapMatrix);
                    if ((rotation == 0.0f || rotation == 90.0f || rotation == 180.0f || rotation == -180.0f || rotation == -90.0f) && Math.abs(CollageView.this.finalAngle - angle) < 4.0f) {
                        CollageView.this.orthogonal = true;
                        return;
                    }
                    if (Math.abs((rotation - CollageView.this.finalAngle) + angle) < 4.0f) {
                        angle = CollageView.this.finalAngle - rotation;
                        CollageView.this.orthogonal = true;
                    }
                    if (Math.abs(90.0f - ((rotation - CollageView.this.finalAngle) + angle)) < 4.0f) {
                        angle = (CollageView.this.finalAngle + 90.0f) - rotation;
                        CollageView.this.orthogonal = true;
                    }
                    if (Math.abs(180.0f - ((rotation - CollageView.this.finalAngle) + angle)) < 4.0f) {
                        angle = (180.0f + CollageView.this.finalAngle) - rotation;
                        CollageView.this.orthogonal = true;
                    }
                    if (Math.abs(-180.0f - ((rotation - CollageView.this.finalAngle) + angle)) < 4.0f) {
                        angle = (CollageView.this.finalAngle - 0.024902344f) - rotation;
                        CollageView.this.orthogonal = true;
                    }
                    if (Math.abs(-90.0f - ((rotation - CollageView.this.finalAngle) + angle)) < 4.0f) {
                        angle = (CollageView.this.finalAngle - 0.049804688f) - rotation;
                        CollageView.this.orthogonal = true;
                    } else {
                        CollageView.this.orthogonal = false;
                    }
                    CollageView.this.scaleShape.bitmapMatrixRotate(CollageView.this.finalAngle - angle);
                    CollageView.this.finalAngle = angle;
                    CollageView.this.invalidate();
                    CollageView.this.requestLayout();
                }
            }
        }
    }

    final class MyMediaScannerConnectionClient implements MediaScannerConnectionClient {
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

    /* renamed from: com.lyrebirdstudio.collagelib.CollageActivity.1 */
    class C09411 implements FontFragment.FontChoosedListener {
        C09411() {
        }

        public void onOk(TextData textData) {
            if (canvasText == null) {
                addCanvasTextView();
            }
            canvasText.addTextView(textData);
            getSupportFragmentManager().beginTransaction().remove(fontFragment).commit();
            Log.e(CollageActivity.TAG, "onOK called");
        }
    }

    class C09422 implements StickerView.StickerViewSelectedListener {
        C09422() {
        }

        public void setSelectedView(StickerView stickerView) {
            Log.e("Sticker", "Coming here");
            stickerView.bringToFront();
            stickerView.bringToFront();
            if (VERSION.SDK_INT < 19) {
                stickerViewContainer.requestLayout();
            }
        }

        public void onTouchUp(StickerData data) {
            Log.e("Sticker", "onTouchUp");
        }
    }

    /* renamed from: com.lyrebirdstudio.collagelib.CollageActivity.4 */
    class C09434 implements MyAdapter.CurrentCollageIndexChangedListener {
        C09434() {
        }

        public void onIndexChanged(int position) {
            collageView.setCurrentCollageIndex(position);
        }
    }

    class C09445 implements MyAdapter.CurrentCollageIndexChangedListener {
        private final /* synthetic */ RecyclerView val$recyclerViewColor;

        C09445(RecyclerView recyclerView) {
            this.val$recyclerViewColor = recyclerView;
        }

        public void onIndexChanged(int position) {
            collageView.backgroundMode = CollageActivity.INDEX_COLLAGE;
            if (position == 0) {
                collageView.setPatternPaint(-1);
                return;
            }
            int newPos = position - 1;
            if (patternAdapterList.get(newPos) != this.val$recyclerViewColor.getAdapter()) {
                this.val$recyclerViewColor.setAdapter((Adapter) patternAdapterList.get(newPos));
                (patternAdapterList.get(newPos)).setSelectedPositinVoid();
            } else {
                (patternAdapterList.get(newPos)).setSelectedPositinVoid();
                (patternAdapterList.get(newPos)).notifyDataSetChanged();
            }
            colorContainer.setVisibility(View.VISIBLE);
        }
    }

    class C09456 implements MyAdapter.CurrentCollageIndexChangedListener {
        C09456() {
        }

        public void onIndexChanged(int color) {
            collageView.setPatternPaintColor(color);
        }
    }

    class C09469 implements MyAdapter.CurrentCollageIndexChangedListener {
        C09469() {
        }

        public void onIndexChanged(int color) {
            collageView.setPatternPaintColor(color);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Bundle, Void, Void> {
        int arraySize;
        Bundle data;
        ProgressDialog progressDialog;
        Bundle savedInstanceState;

        BitmapWorkerTask() {
        }

        protected void onPreExecute() {
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setMessage("loading images!");
            this.progressDialog.show();
        }

        protected Void doInBackground(Bundle... params) {
            int i;
            data = params[0];
            savedInstanceState = params[1];
            isScrapBook = data.getBoolean("is_scrap_book", false);
            long[] selectedImageList = data.getLongArray("photo_id_list");
            int[] selectedImageOrientationList = data.getIntArray("photo_orientation_list");
            arraySize = 0;
            int maxDivider;
            if (selectedImageList == null) {
                String selectedImagePath = this.data.getString("selected_image_path");
                if (selectedImagePath != null) {
                    arraySize = 1;
                    bitmapList = new Bitmap[arraySize];
                    maxDivider = arraySize;
                    if (maxDivider < 3) {
                        maxDivider = 3;
                    }
                    String str = selectedImagePath;
                    bitmapList[0] = Utility.decodeFile(str, Utility.maxSizeForDimension(context, maxDivider, UPPER_SIZE_FOR_LOAD), isScrapBook);
                }
            } else {
                arraySize = selectedImageList.length;
                bitmapList = new Bitmap[arraySize];
                maxDivider = arraySize;
                if (maxDivider < 3) {
                    maxDivider = 3;
                }
                int requiredSize = Utility.maxSizeForDimension(context, maxDivider, UPPER_SIZE_FOR_LOAD);
                int loadingImageError = 0;
                for (i = CollageActivity.INDEX_COLLAGE; i < arraySize; i += CollageActivity.INDEX_COLLAGE_BACKGROUND) {
                    Bitmap bitmap = Utility.getScaledBitmapFromId(context, selectedImageList[i], selectedImageOrientationList[i], requiredSize, isScrapBook);
                    if (bitmap != null) {
                        bitmapList[i] = bitmap;
                    } else {
                        loadingImageError += CollageActivity.INDEX_COLLAGE_BACKGROUND;
                    }
                }
                if (loadingImageError > 0) {
                    int newSize = arraySize - loadingImageError;
                    Bitmap[] arr = new Bitmap[newSize];
                    int j = 0;
                    for (i = 0; i < arraySize; i++) {
                        if (bitmapList[i] != null) {
                            arr[j] = bitmapList[i];
                            j += 1;
                        }
                    }
                    arraySize = newSize;
                    bitmapList = arr;
                }
            }
            parameterList = new Parameter[arraySize];
            for (i = 0; i < parameterList.length; i++) {
                parameterList[i] = new Parameter();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
            }
            if (arraySize <= 0) {
                Toast msg = Toast.makeText(context, "Couldn't load images!", Toast.LENGTH_SHORT);
                msg.setGravity(17, msg.getXOffset() / CollageActivity.INDEX_COLLAGE_SPACE, msg.getYOffset() / CollageActivity.INDEX_COLLAGE_SPACE);
                msg.show();
                finish();
                return;
            }
            if (Collage.collageIconArray[bitmapList.length - 1] != collageAdapter.iconList) {
                collageAdapter.setData(Collage.collageIconArray[bitmapList.length - 1]);
                collageAdapter.notifyDataSetChanged();
                Log.e(CollageActivity.TAG, "change collage icons");
            }
//            if (isScrapBook) {
//                btmDelete = BitmapFactory.decodeResource(getResources(), R.drawable.scrapbook_remove);
//                btmScale = BitmapFactory.decodeResource(getResources(), R.drawable.scrapbook_scale);
//            }
            if (isScrapBook) {
                npd = (NinePatchDrawable) ContextCompat.getDrawable(context, R.drawable.shadow_7);
                Log.e(CollageActivity.TAG, "ndp width " + npd.getMinimumHeight());
            }
            collageView = new CollageView(context, width, height);
            mainLayout = (RelativeLayout) findViewById(R.id.collage_main_layout);
            mainLayout.addView(collageView);
            viewFlipper.bringToFront();
            slideLeftIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
            slideLeftOut = AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);
            slideRightIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
            slideRightOut = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right);
            addEffectFragment();
            if (arraySize == CollageActivity.INDEX_COLLAGE_BACKGROUND) {
                setVisibilityForSingleImage();
            }
            if (isScrapBook) {
                setVisibilityForScrapbook();
            }
//            if (CommonLibrary.isAppPro(context)) {
//                adWhirlLayout = (AdView) findViewById(R.id.collage_edit_ad_id);
//                adWhirlLayout.setVisibility(8);
//            } else {
//                adWhirlLayout = (AdView) findViewById(R.id.collage_edit_ad_id);
//                adWhirlLayout.bringToFront();
//                adWhirlLayout.loadAd(new Builder().build());
//                if (context.getResources().getBoolean(R.bool.showInterstitialAds)) {
//                    interstitial = new InterstitialAd(context);
//                    interstitial.setAdUnitId(getString(R.string.interstital_ad_id));
//                    interstitial.loadAd(new Builder().build());
//                }
//            }
            stickerList = new ArrayList();
            stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
            stickerViewContainer.bringToFront();
            viewFlipper = (ViewFlipper) findViewById(R.id.collage_view_flipper);
            viewFlipper.bringToFront();
            ((HorizontalScrollView) findViewById(R.id.collage_footer)).bringToFront();
            findViewById(R.id.collage_header).bringToFront();
            contextFooter = (ViewGroup) findViewById(R.id.collage_context_menu);
            contextFooter.bringToFront();
            selectSwapTextView = findViewById(R.id.select_image_swap);
            selectSwapTextView.bringToFront();
            selectSwapTextView.setVisibility(View.INVISIBLE);
            selectFilterTextView = findViewById(R.id.select_image_filter);
            selectFilterTextView.bringToFront();
            selectFilterTextView.setVisibility(View.INVISIBLE);
            findViewById(R.id.sticker_grid_fragment_container).bringToFront();
            if (this.savedInstanceState != null) {
                FragmentManager fm = getSupportFragmentManager();
                galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
                if (galleryFragment != null) {
                    fm.beginTransaction().hide(galleryFragment).commit();
                    galleryFragment.setGalleryListener(stickerGalleryListener);
                }
            }
        }
    }

    private class SaveImageTask extends AsyncTask<Object, Object, Object> {
        ProgressDialog progressDialog;
        String resultPath;
        int saveMode;

        private SaveImageTask() {
            this.saveMode = CollageActivity.INDEX_COLLAGE;
            this.resultPath = null;
        }

        protected Object doInBackground(Object... arg0) {
            if (arg0 != null) {
                this.saveMode = ((Integer) arg0[CollageActivity.INDEX_COLLAGE]).intValue();
            }
            resultPath = collageView.saveBitmap(width, height);
            return null;
        }

        protected void onPreExecute() {
            this.progressDialog = new ProgressDialog(context);
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
                msg = Toast.makeText(context, "Image has been saved to the /SD" + getString(R.string.directory) + " folder.", Toast.LENGTH_SHORT);
                msg.setGravity(17, msg.getXOffset() / CollageActivity.INDEX_COLLAGE_SPACE, msg.getYOffset() / CollageActivity.INDEX_COLLAGE_SPACE);
                msg.show();
                MediaScannerConnectionClient client = new MyMediaScannerConnectionClient(getApplicationContext(), new File(this.resultPath), null);
                if (this.saveMode == CollageActivity.INDEX_COLLAGE_BLUR) {
                    finish();
                }
            } else if (this.saveMode == CollageActivity.INDEX_COLLAGE_BACKGROUND) {
                super.onPostExecute(result);
                try {
                    Intent picMessageIntent = new Intent("android.intent.action.SEND");
                    picMessageIntent.setFlags(268435456);
                    picMessageIntent.setType("image/jpeg");
                    if (this.resultPath != null) {
                        picMessageIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(this.resultPath)));
                        startActivity(picMessageIntent);
                    }
                } catch (Exception e2) {
                    msg = Toast.makeText(context, "There is no email app installed on your device to handle this request.", Toast.LENGTH_SHORT);
                    msg.setGravity(17, msg.getXOffset() / CollageActivity.INDEX_COLLAGE_SPACE, msg.getYOffset() / CollageActivity.INDEX_COLLAGE_SPACE);
                    msg.show();
                }
            } else if (this.saveMode == 3) {
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
                    startActivityForResult(fbIntent, CollageActivity.SAVE_IMAGE_ID);
                }
            }
            MyMediaScannerConnectionClient myMediaScannerConnectionClient = new MyMediaScannerConnectionClient(getApplicationContext(), new File(this.resultPath), null);
        }
    }
}
