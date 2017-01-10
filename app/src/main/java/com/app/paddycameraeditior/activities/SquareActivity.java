package com.app.paddycameraeditior.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader.TileMode;
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
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.adapters.BackgroundPatternAdapter;
import com.app.paddycameraeditior.adapters.MyRecylceAdapterBase;
import com.app.paddycameraeditior.adapters.SquareColorPickerAdapter;
import com.app.paddycameraeditior.canvastext.ApplyTextInterface;
import com.app.paddycameraeditior.canvastext.CustomRelativeLayout;
import com.app.paddycameraeditior.canvastext.SingleTap;
import com.app.paddycameraeditior.canvastext.TextData;
import com.app.paddycameraeditior.collagelib.Utility;
import com.app.paddycameraeditior.common_lib.Parameter;
import com.app.paddycameraeditior.cropImages.FragmentCrop;
import com.app.paddycameraeditior.fragments.EffectFragment;
import com.app.paddycameraeditior.fragments.FontFragment;
import com.app.paddycameraeditior.imagesavelib.ImageUtility;
import com.app.paddycameraeditior.sticker.StickerData;
import com.app.paddycameraeditior.sticker.StickerGalleryFragment;
import com.app.paddycameraeditior.sticker.StickerGalleryListener;
import com.app.paddycameraeditior.sticker.StickerView;
import com.app.paddycameraeditior.utils.CustomViews.BlurBuilder;
import com.app.paddycameraeditior.utils.CustomViews.BlurBuilderNormal;
import com.app.paddycameraeditior.utils.CustomViews.RotationGestureDetector;
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

public class SquareActivity extends FragmentActivity {
    public static final int INDEX_SQUARE = 0;
    public static final int INDEX_SQUARE_ADJ = 3;
    public static final int INDEX_SQUARE_BACKGROUND = 1;
    public static final int INDEX_SQUARE_BLUR = 2;
    public static final int INDEX_SQUARE_FRAME = 6;
    public static final int INDEX_SQUARE_FX = 5;
    public static final int INDEX_SQUARE_INVISIBLE_VIEW = 4;
    public static final int MATRIX_MODE_CENTER = 1;
    public static final int MATRIX_MODE_FIT = 0;
    public static final int MATRIX_MODE_FLIP_HORIZONTAL = 4;
    public static final int MATRIX_MODE_FLIP_VERTICAL = 5;
    public static final int MATRIX_MODE_ROTATE_LEFT = 3;
    public static final int MATRIX_MODE_ROTATE_RIGHT = 2;
    public static final int TAB_INDEX_SQUARE = 0;
    public static final int TAB_INDEX_SQUARE_ADJ = 5;
    public static final int TAB_INDEX_SQUARE_BACKGROUND = 1;
    public static final int TAB_INDEX_SQUARE_BLUR = 2;
    public static final int TAB_INDEX_SQUARE_FRAME = 3;
    public static final int TAB_INDEX_SQUARE_FX = 4;
    public static final int TAB_SIZE = 6;
    static final int INSTAGRAM_ID = 470;
    static final int SAVE_IMAGE_ID = 1346;
    private static final String TAG = SquareActivity.class.getSimpleName();
    public int SAVE_FINISH;
    public int SAVE_INSTAGRAM;
    public int SAVE_NORMAL;
    public Activity activity;
    public AdView adWhirlLayout;
    public TextView blurText;
    public CustomRelativeLayout canvasText;
    public LinearLayout colorContainer;
    public Context context;
    public FragmentCrop cropFragment;
    public FragmentCrop.CropListener cropListener;
    public int currentSelectedTabIndex;
    public int currentStickerIndex;
    public EffectFragment effectFragment;
    public FontFragment.FontChoosedListener fontChoosedListener;
    public FontFragment fontFragment;
    public StickerGalleryFragment galleryFragment;
    public InterstitialAd interstitial;
    public SquareView mSqView;
    public RelativeLayout mainLayout;
    public ArrayList<MyRecylceAdapterBase> patternAdapterList;
    public Bitmap removeBitmap;
    public AlertDialog saveImageAlert;
    public Bitmap scaleBitmap;
    public SeekBar seekBarBlur;
    public View selectFilterTextView;
    public View selectSwapTextView;
    public Bitmap sourceBitmap;
    public StickerGalleryListener stickerGalleryListener;
    public ArrayList<StickerView> stickerList;
    public FrameLayout stickerViewContainer;
    public StickerView.StickerViewSelectedListener stickerViewSelectedListner;
    public View[] tabButtonList;
    public ArrayList<TextData> textDataList;
    public ViewFlipper viewFlipper;
    boolean selectImageForAdj;
    boolean showText;
    private ScaleGestureDetector mScaleDetector;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private RecyclerView recyclerViewColor;

    public SquareActivity() {
        this.activity = this;
        this.context = this;
        this.selectImageForAdj = false;
        this.patternAdapterList = new ArrayList();
        this.textDataList = new ArrayList();
        this.showText = false;
        this.fontChoosedListener = new C09551();
        this.currentStickerIndex = TAB_INDEX_SQUARE;
        this.stickerViewSelectedListner = new C09562();
        this.currentSelectedTabIndex = TAB_INDEX_SQUARE;
        this.SAVE_NORMAL = TAB_INDEX_SQUARE_FRAME;
        this.SAVE_FINISH = TAB_INDEX_SQUARE_FX;
        this.SAVE_INSTAGRAM = TAB_INDEX_SQUARE_ADJ;
        this.cropListener = new C09573();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(TAB_INDEX_SQUARE_BACKGROUND);
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        Bundle extras = getIntent().getExtras();
        String selectedImagePath = extras.getString("selectedImagePath");
//        this.sourceBitmap = BitmapResizer.decodeBitmapFromFile(selectedImagePath, extras.getInt("MAX_SIZE"));
        this.sourceBitmap = ImageUtility.decodeBitmapFromFile(selectedImagePath, extras.getInt("MAX_SIZE"));
        if (this.sourceBitmap == null) {
            Toast msg = Toast.makeText(this.context, "Could not load the photo, please use another GALLERY app!", Toast.LENGTH_SHORT);
            msg.setGravity(17, msg.getXOffset() / TAB_INDEX_SQUARE_BLUR, msg.getYOffset() / TAB_INDEX_SQUARE_BLUR);
            msg.show();
            finish();
            return;
        }
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        setContentView(R.layout.activity_square);
        this.mainLayout = (RelativeLayout) findViewById(R.id.nocrop_main_layout);
        this.mSqView = new SquareView(this, width, height);
        this.mainLayout.addView(this.mSqView);
        recyclerViewColor = (RecyclerView) findViewById(R.id.recyclerView_color);
        int colorDefault = getResources().getColor(R.color.view_flipper_bg_color);
        int colorSelected = getResources().getColor(R.color.footer_button_color_pressed);
        new LinearLayoutManager(this.context).setOrientation(LinearLayoutManager.HORIZONTAL);
        this.viewFlipper = (ViewFlipper) findViewById(R.id.square_view_flipper);
        this.viewFlipper.setDisplayedChild(TAB_INDEX_SQUARE_FX);
        createAdapterList(colorDefault, colorSelected);
        RecyclerView recyclerViewPattern = (RecyclerView) findViewById(R.id.recyclerView_pattern);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.colorContainer = (LinearLayout) findViewById(R.id.color_container);
        recyclerViewPattern.setLayoutManager(linearLayoutManager);
        recyclerViewPattern.setAdapter(new BackgroundPatternAdapter(Utility.patternResIdList3, new BackgroundPatternAdapter.CurrentSquareIndexChangedListener() {
            @Override
            public void onIndexChanged(int position) {
                mSqView.backgroundMode = 0;
                if (position == 0) {
                    mSqView.setPatternPaint(-1);
                    return;
                }
                int newPos = position - 1;
                if (patternAdapterList.get(newPos) != recyclerViewColor.getAdapter()) {
                    recyclerViewColor.setAdapter(patternAdapterList.get(newPos));
                    (patternAdapterList.get(newPos)).setSelectedPositinVoid();
                } else {
                    (patternAdapterList.get(newPos)).setSelectedPositinVoid();
                    (patternAdapterList.get(newPos)).notifyDataSetChanged();
                }
                colorContainer.setVisibility(View.VISIBLE);
            }
        }, colorDefault, colorSelected, false, false));
        recyclerViewPattern.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this.context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewColor.setLayoutManager(linearLayoutManager1);
        recyclerViewColor.setAdapter(new SquareColorPickerAdapter(new BackgroundPatternAdapter.CurrentSquareIndexChangedListener() {
            @Override
            public void onIndexChanged(int color) {
                mSqView.setPatternPaintColor(color);
            }
        }, colorDefault, colorSelected));
        recyclerViewColor.setItemAnimator(new DefaultItemAnimator());
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.square_footer);
        horizontalScrollView.bringToFront();
        horizontalScrollView.postDelayed(new C05926(horizontalScrollView), 50);
        horizontalScrollView.postDelayed(new C05937(horizontalScrollView), 600);
        this.viewFlipper.bringToFront();
        this.slideLeftIn = AnimationUtils.loadAnimation(this.activity, R.anim.slide_in_left);
        this.slideLeftOut = AnimationUtils.loadAnimation(this.activity, R.anim.slide_out_left);
        this.slideRightIn = AnimationUtils.loadAnimation(this.activity, R.anim.slide_in_right);
        this.slideRightOut = AnimationUtils.loadAnimation(this.activity, R.anim.slide_out_right);
        addEffectFragment();

        this.stickerList = new ArrayList();
        this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
        this.stickerViewContainer.bringToFront();
        this.viewFlipper = (ViewFlipper) findViewById(R.id.square_view_flipper);
        this.viewFlipper.bringToFront();
        horizontalScrollView.bringToFront();
        findViewById(R.id.square_header).bringToFront();
        this.blurText = (TextView) findViewById(R.id.square_blur_text_view);
        findViewById(R.id.sticker_grid_fragment_container).bringToFront();
        this.seekBarBlur = (SeekBar) findViewById(R.id.nocrop_blur_seek_bar);
        this.seekBarBlur.setOnSeekBarChangeListener(new C05948());
        if (savedInstanceState != null) {
            FragmentManager fm = getSupportFragmentManager();
            this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
            if (this.galleryFragment != null) {
                fm.beginTransaction().hide(this.galleryFragment).commit();
                this.galleryFragment.setGalleryListener(createGalleryListener());
            }
        }
        if (this.mSqView != null) {
            this.mSqView.setBlurBitmap(this.mSqView.blurRadius);
        }

        try {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.adWhirlLayout != null) {
            this.adWhirlLayout.removeAllViews();
            this.adWhirlLayout.destroy();
        }
    }

    private void createAdapterList(int colorDefault, int colorSelected) {
        int size = Utility.patternResIdList2.length;
        patternAdapterList.clear();
        patternAdapterList.add(new SquareColorPickerAdapter(new BackgroundPatternAdapter.CurrentSquareIndexChangedListener() {
            @Override
            public void onIndexChanged(int color) {
                mSqView.setPatternPaintColor(color);
            }
        }, colorDefault, colorSelected));
        for (int i = 0; i < size; i++) {
            this.patternAdapterList.add(new BackgroundPatternAdapter(Utility.patternResIdList2[i], new BackgroundPatternAdapter.CurrentSquareIndexChangedListener() {
                public void onIndexChanged(int positionOrColor) {
                    mSqView.setPatternPaint(positionOrColor);
                }
            }, colorDefault, colorSelected, true, true));
        }
    }

    void addCanvasTextView() {
        this.canvasText = new CustomRelativeLayout(this.context, this.textDataList, this.mSqView.identityMatrix, new SingleTap() {
            public void onSingleTap(TextData textData) {
                SquareActivity.this.fontFragment = new FontFragment();
                Bundle arguments = new Bundle();
                arguments.putSerializable("text_data", textData);
                SquareActivity.this.fontFragment.setArguments(arguments);
                SquareActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.square_text_view_fragment_container, SquareActivity.this.fontFragment, "FONT_FRAGMENT").commit();
                Log.e(SquareActivity.TAG, "replace fragment");
                SquareActivity.this.fontFragment.setFontChoosedListener(SquareActivity.this.fontChoosedListener);
            }
        });
        this.canvasText.setApplyTextListener(new ApplyTextInterface() {
            public void onOk(ArrayList<TextData> tdList) {
                Iterator it = tdList.iterator();
                while (it.hasNext()) {
                    ((TextData) it.next()).setImageSaveMatrix(SquareActivity.this.mSqView.identityMatrix);
                }
                SquareActivity.this.textDataList = tdList;
                SquareActivity.this.showText = true;
                if (SquareActivity.this.mainLayout == null) {
                    SquareActivity.this.mainLayout = (RelativeLayout) SquareActivity.this.findViewById(R.id.nocrop_main_layout);
                }
                SquareActivity.this.mainLayout.removeView(SquareActivity.this.canvasText);
                SquareActivity.this.mSqView.postInvalidate();
            }

            public void onCancel() {
                SquareActivity.this.showText = true;
                SquareActivity.this.mainLayout.removeView(SquareActivity.this.canvasText);
                SquareActivity.this.mSqView.postInvalidate();
            }
        });
        this.showText = false;
        this.mSqView.invalidate();
        this.mainLayout.addView(this.canvasText);
        ((RelativeLayout) findViewById(R.id.square_text_view_fragment_container)).bringToFront();
        if (textDataList.size() == 0) {
            this.fontFragment = new FontFragment();
            this.fontFragment.setArguments(new Bundle());
            getSupportFragmentManager().beginTransaction().add(R.id.square_text_view_fragment_container, this.fontFragment, "FONT_FRAGMENT").commit();
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

    public StickerGalleryListener createGalleryListener() {
        if (this.stickerGalleryListener == null) {
            this.stickerGalleryListener = new StickerGalleryListener() {
                public void onGalleryOkSingleImage(int resId) {
                    Bitmap bitmap = BitmapFactory.decodeResource(SquareActivity.this.getResources(), resId);
                    if (SquareActivity.this.removeBitmap == null) {
                        SquareActivity.this.removeBitmap = BitmapFactory.decodeResource(SquareActivity.this.getResources(), R.drawable.remove_text);
                    }
                    if (SquareActivity.this.scaleBitmap == null) {
                        SquareActivity.this.scaleBitmap = BitmapFactory.decodeResource(SquareActivity.this.getResources(), R.drawable.scale_text);
                    }
                    StickerView stickerView = new StickerView(SquareActivity.this.context, bitmap, null, SquareActivity.this.removeBitmap, SquareActivity.this.scaleBitmap, resId);
                    stickerView.setStickerViewSelectedListener(SquareActivity.this.stickerViewSelectedListner);
                    if (SquareActivity.this.stickerViewContainer == null) {
                        SquareActivity.this.stickerViewContainer = (FrameLayout) SquareActivity.this.findViewById(R.id.sticker_view_container);
                    }
                    SquareActivity.this.stickerViewContainer.addView(stickerView);
                    FragmentManager fm = SquareActivity.this.getSupportFragmentManager();
                    if (SquareActivity.this.galleryFragment == null) {
                        SquareActivity.this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
                    }
                    fm.beginTransaction().hide(SquareActivity.this.galleryFragment).commit();
                }

                public void onGalleryOkImageArray(int[] ImageIdList) {
                    removeBitmap = BitmapFactory.decodeResource(SquareActivity.this.getResources(), R.drawable.remove_text);
                    scaleBitmap = BitmapFactory.decodeResource(SquareActivity.this.getResources(), R.drawable.scale_text);
                    for (int i = SquareActivity.TAB_INDEX_SQUARE; i < ImageIdList.length; i += SquareActivity.TAB_INDEX_SQUARE_BACKGROUND) {
                        StickerView stickerView = new StickerView(SquareActivity.this.context, BitmapFactory.decodeResource(SquareActivity.this.getResources(), ImageIdList[i]), null, removeBitmap, scaleBitmap, ImageIdList[i]);
                        stickerView.setStickerViewSelectedListener(SquareActivity.this.stickerViewSelectedListner);
                        if (SquareActivity.this.stickerViewContainer == null) {
                            SquareActivity.this.stickerViewContainer = (FrameLayout) SquareActivity.this.findViewById(R.id.sticker_view_container);
                        }
                        SquareActivity.this.stickerViewContainer.addView(stickerView);
                    }
                    FragmentManager fm = SquareActivity.this.getSupportFragmentManager();
                    if (SquareActivity.this.galleryFragment == null) {
                        SquareActivity.this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myFragmentTag");
                    }
                    fm.beginTransaction().hide(SquareActivity.this.galleryFragment).commit();
                }

                public void onGalleryCancel() {
                    SquareActivity.this.getSupportFragmentManager().beginTransaction().hide(SquareActivity.this.galleryFragment).commit();
                }
            };
        }
        return this.stickerGalleryListener;
    }

    void setSelectedTab(int index) {
        if (this.viewFlipper != null) {
            setTabBg(0);
            int displayedChild = this.viewFlipper.getDisplayedChild();
            if (displayedChild != TAB_INDEX_SQUARE_BACKGROUND) {
                hideColorContainer();
            }
            if (index == 0) {
                if (displayedChild != 0) {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                    this.viewFlipper.setDisplayedChild(TAB_INDEX_SQUARE);
                } else {
                    return;
                }
            }
            if (index == 1) {
                setTabBg(1);
                if (displayedChild != 1) {
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
            if (index == 2) {
                setTabBg(2);
                if (displayedChild != 2) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    this.viewFlipper.setDisplayedChild(2);
                } else {
                    return;
                }
            }
            if (index == TAB_INDEX_SQUARE_ADJ) {
                setTabBg(TAB_INDEX_SQUARE_FX);
                this.effectFragment.setSelectedTabIndex(TAB_INDEX_SQUARE);
                if (displayedChild != TAB_INDEX_SQUARE_FRAME) {
                    if (displayedChild == TAB_INDEX_SQUARE_BLUR || displayedChild == TAB_INDEX_SQUARE_BACKGROUND) {
                        this.viewFlipper.setInAnimation(this.slideRightIn);
                        this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    } else {
                        this.viewFlipper.setInAnimation(this.slideLeftIn);
                        this.viewFlipper.setOutAnimation(this.slideRightOut);
                    }
                    this.viewFlipper.setDisplayedChild(TAB_INDEX_SQUARE_FRAME);
                } else {
                    return;
                }
            }
            if (index == TAB_SIZE) {
                setTabBg(TAB_INDEX_SQUARE_FRAME);
                this.effectFragment.setSelectedTabIndex(1);
                if (displayedChild != TAB_INDEX_SQUARE_FRAME) {
                    if (displayedChild == TAB_INDEX_SQUARE_BLUR) {
                        this.viewFlipper.setInAnimation(this.slideLeftIn);
                        this.viewFlipper.setOutAnimation(this.slideRightOut);
                    } else {
                        this.viewFlipper.setInAnimation(this.slideRightIn);
                        this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    }
                    this.viewFlipper.setDisplayedChild(TAB_INDEX_SQUARE_FRAME);
                } else {
                    return;
                }
            }
            if (index == TAB_INDEX_SQUARE_FRAME) {
                setTabBg(TAB_INDEX_SQUARE_ADJ);
                this.effectFragment.showToolBar();
                if (displayedChild != TAB_INDEX_SQUARE_FRAME) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    this.viewFlipper.setDisplayedChild(TAB_INDEX_SQUARE_FRAME);
                } else {
                    return;
                }
            }
            if (index == TAB_INDEX_SQUARE_FX) {
                setTabBg(-1);
                if (displayedChild != TAB_INDEX_SQUARE_FX) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                    this.viewFlipper.setDisplayedChild(TAB_INDEX_SQUARE_FX);
                }
            }
        }
    }

    private void setTabBg(int index) {
        this.currentSelectedTabIndex = index;
        if (this.tabButtonList == null) {
            this.tabButtonList = new View[6];
            this.tabButtonList[0] = findViewById(R.id.button_square_layout);
            this.tabButtonList[1] = findViewById(R.id.button_square_background);
            this.tabButtonList[2] = findViewById(R.id.button_square_blur);
            this.tabButtonList[3] = findViewById(R.id.button_square_frame);
            this.tabButtonList[4] = findViewById(R.id.button_square_fx);
            this.tabButtonList[5] = findViewById(R.id.button_square_adj);
        }
        for (int i = TAB_INDEX_SQUARE; i < this.tabButtonList.length; i += TAB_INDEX_SQUARE_BACKGROUND) {
            this.tabButtonList[i].setBackgroundResource(R.drawable.square_footer_button);
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
            for (int i = TAB_INDEX_SQUARE; i < size; i += TAB_INDEX_SQUARE_BACKGROUND) {
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
            removeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.remove_text);
            scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ok_white);
            for (int i = TAB_INDEX_SQUARE; i < stickerDataArray.length; i += TAB_INDEX_SQUARE_BACKGROUND) {
                StickerView stickerView = new StickerView(this.context, BitmapFactory.decodeResource(getResources(), stickerDataArray[i].getResId()), stickerDataArray[i], removeBitmap, scaleBitmap, stickerDataArray[i].getResId());
                stickerView.setStickerViewSelectedListener(this.stickerViewSelectedListner);
                if (this.stickerViewContainer == null) {
                    this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
                }
                this.stickerViewContainer.addView(stickerView);
            }
        }
    }

    void clearViewFlipper() {
        this.viewFlipper.setDisplayedChild(TAB_INDEX_SQUARE_FX);
        setTabBg(-1);
    }

    private void hideColorContainer() {
        if (this.colorContainer == null) {
            this.colorContainer = (LinearLayout) findViewById(R.id.color_container);
        }
        this.colorContainer.setVisibility(View.INVISIBLE);
    }

    void addEffectFragment() {
        if (this.effectFragment == null) {
            this.effectFragment = (EffectFragment) getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");
            if (this.effectFragment == null) {
                this.effectFragment = new EffectFragment();
                this.effectFragment.setBitmap(this.sourceBitmap);
                this.effectFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.square_effect_fragment_container, this.effectFragment, "MY_FRAGMENT").commit();
            } else {
                this.effectFragment.setBitmap(this.sourceBitmap);
                this.effectFragment.setSelectedTabIndex(TAB_INDEX_SQUARE);
            }
            this.effectFragment.setBitmapReadyListener(new EffectFragment.BitmapReady() {
                public void onBitmapReady(Bitmap bitmap) {
                    SquareActivity.this.mSqView.filterBitmap = bitmap;
                    SquareActivity.this.mSqView.postInvalidate();
                }
            });
        }
    }

    void setVisibilityOfFilterHorizontalListview(boolean show) {
        Log.e(TAG, "show " + show);
        if (show && this.effectFragment.isHidden()) {
            getSupportFragmentManager().beginTransaction().show(this.effectFragment).commit();
        }
        if (!show && this.effectFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().hide(this.effectFragment).commit();
        }
        findViewById(R.id.square_effect_fragment_container).bringToFront();
    }

    public void onBackPressed() {
        if (this.cropFragment != null && this.cropFragment.isVisible()) {
            this.cropFragment.onBackPressed();
        } else if (this.fontFragment != null && this.fontFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(this.fontFragment).commit();
        } else if (this.galleryFragment != null && this.galleryFragment.isVisible()) {
            this.galleryFragment.backtrace();
        } else if (!this.showText && this.canvasText != null) {
            this.showText = true;
            this.mainLayout.removeView(this.canvasText);
            this.mSqView.postInvalidate();
            this.canvasText = null;
            Log.e(TAG, "replace fragment");
        } else if (this.viewFlipper.getDisplayedChild() == TAB_INDEX_SQUARE_FRAME) {
            clearFxAndFrame();
            if (this.effectFragment.backPressed()) {
                Log.e(TAG, "effectFragment back pressed!");
            } else {
                clearViewFlipper();
            }
        } else if (this.colorContainer.getVisibility() == View.VISIBLE) {
            hideColorContainer();
        } else if (this.selectImageForAdj) {
            this.selectFilterTextView.setVisibility(View.INVISIBLE);
            this.selectImageForAdj = false;
        } else if (this.viewFlipper == null || this.viewFlipper.getDisplayedChild() == TAB_INDEX_SQUARE_FX) {
            backButtonAlertBuilder();
        } else {
            setSelectedTab(TAB_INDEX_SQUARE_FX);
        }
    }

    private void backButtonAlertBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage("Would you like to save image ?").setCancelable(true).setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SaveImageTask saveImageTask = new SaveImageTask();
                Object[] objArr = new Object[SquareActivity.TAB_INDEX_SQUARE_BACKGROUND];
                objArr[SquareActivity.TAB_INDEX_SQUARE] = Integer.valueOf(SquareActivity.this.SAVE_FINISH);
                saveImageTask.execute(objArr);
            }
        }).setNegativeButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setNeutralButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SquareActivity.this.activity.finish();
            }
        });
        this.saveImageAlert = builder.create();
        this.saveImageAlert.show();
    }

    void saveNormal(String resultPath) {
        boolean z = true;
        Intent fbIntent = new Intent(this.context, SaveImageActivity.class);
        if (resultPath != null) {
            fbIntent.putExtra("imagePath", resultPath);
            fbIntent.putExtra("folder", getString(R.string.directory));
            String str = "show_inter_ad";
            fbIntent.putExtra(str, z);
            startActivityForResult(fbIntent, SAVE_IMAGE_ID);
        }
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        if (id == R.id.nocrop_fit) {
            this.mSqView.setScaleMatrix(TAB_INDEX_SQUARE);
        } else if (id == R.id.nocrop_center) {
            this.mSqView.setScaleMatrix(TAB_INDEX_SQUARE_BACKGROUND);
        } else if (id == R.id.button_straighten_rotate_left) {
            this.mSqView.setScaleMatrix(TAB_INDEX_SQUARE_FRAME);
        } else if (id == R.id.button_straighten_rotate_right) {
            this.mSqView.setScaleMatrix(TAB_INDEX_SQUARE_BLUR);
        } else if (id == R.id.button_straighten_flip_horizontal) {
            this.mSqView.setScaleMatrix(TAB_INDEX_SQUARE_FX);
        } else if (id == R.id.button_straighten_flip_vertical) {
            this.mSqView.setScaleMatrix(TAB_INDEX_SQUARE_ADJ);
        } else if (id == R.id.button_crop) {
            addCropFragment();
        } else if (id == R.id.button_square_blur) {
            this.mSqView.setBlurBitmap(this.mSqView.blurRadius);
            setSelectedTab(TAB_INDEX_SQUARE_BLUR);
        } else if (id == R.id.button_save_square_image) {
            SaveImageTask saveImageTask = new SaveImageTask();
            Object[] objArr = new Object[TAB_INDEX_SQUARE_BACKGROUND];
            objArr[TAB_INDEX_SQUARE] = Integer.valueOf(this.SAVE_NORMAL);
            saveImageTask.execute(objArr);
        } else if (id == R.id.button_cancel_square_image) {
            backButtonAlertBuilder();
        } else if (id == R.id.hide_color_container) {
            hideColorContainer();
        } else if (id == R.id.button_mirror_text) {
            setSelectedTab(TAB_INDEX_SQUARE_FX);
            addCanvasTextView();
            clearViewFlipper();
        } else if (id == R.id.button_mirror_sticker) {
            setSelectedTab(TAB_INDEX_SQUARE_FX);
            addStickerGalleryFragment();
        } else if (id == R.id.button_square_background) {
            setSelectedTab(1);
        }
        if (id == R.id.button_square_layout) {
            setSelectedTab(TAB_INDEX_SQUARE);
        } else if (id == R.id.button_square_background) {
            setSelectedTab(TAB_INDEX_SQUARE_BACKGROUND);
        } else if (id == R.id.button_square_adj) {
            setSelectedTab(TAB_INDEX_SQUARE_FRAME);
        } else if (id == R.id.button_square_fx) {
            setSelectedTab(TAB_INDEX_SQUARE_ADJ);
        } else if (id == R.id.button_square_frame) {
            setSelectedTab(TAB_SIZE);
        } else if (id == R.id.button_save_square_instagram) {
//            saveImageTask = new SaveImageTask();
//            objArr = new Object[TAB_INDEX_SQUARE_BACKGROUND];
//            objArr[TAB_INDEX_SQUARE] = Integer.valueOf(this.SAVE_INSTAGRAM);
//            saveImageTask.execute(objArr);
        } else {
            if (this.effectFragment != null) {
                this.effectFragment.myClickHandler(id);
            }
            if (id == R.id.button_lib_cancel || id == R.id.button_lib_ok) {
                clearFxAndFrame();
            }
        }
    }

    private void clearFxAndFrame() {
        int selectedTabIndex = this.effectFragment.getSelectedTabIndex();
        if (this.currentSelectedTabIndex != TAB_INDEX_SQUARE_FX && this.currentSelectedTabIndex != TAB_INDEX_SQUARE_FRAME) {
            return;
        }
        if (selectedTabIndex == 0 || selectedTabIndex == TAB_INDEX_SQUARE_BACKGROUND) {
            clearViewFlipper();
        }
    }

    void addCropFragment() {
        ((FrameLayout) findViewById(R.id.crop_fragment_container)).bringToFront();
        this.cropFragment = (FragmentCrop) getSupportFragmentManager().findFragmentByTag("crop_fragment");
        if (this.cropFragment == null) {
            this.cropFragment = new FragmentCrop();
            this.cropFragment.setCropListener(this.cropListener);
            this.cropFragment.setBitmap(this.sourceBitmap);
            this.cropFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.crop_fragment_container, this.cropFragment, "crop_fragment").commit();
            return;
        }
        this.cropFragment.setCropListener(this.cropListener);
        this.cropFragment.setBitmap(this.sourceBitmap);
    }

    class C05926 implements Runnable {
        private final /* synthetic */ HorizontalScrollView val$horizontalScrollView;

        C05926(HorizontalScrollView horizontalScrollView) {
            this.val$horizontalScrollView = horizontalScrollView;
        }

        public void run() {
            this.val$horizontalScrollView.scrollTo(this.val$horizontalScrollView.getChildAt(SquareActivity.TAB_INDEX_SQUARE).getMeasuredWidth(), SquareActivity.TAB_INDEX_SQUARE);
        }
    }

    class C05937 implements Runnable {
        private final /* synthetic */ HorizontalScrollView val$horizontalScrollView;

        C05937(HorizontalScrollView horizontalScrollView) {
            this.val$horizontalScrollView = horizontalScrollView;
        }

        public void run() {
            this.val$horizontalScrollView.fullScroll(17);
        }
    }

    /* renamed from: com.lyrebirdstudio.instasquare.lib.SquareActivity.8 */
    class C05948 implements OnSeekBarChangeListener {
        C05948() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            float radius = ((float) seekBar.getProgress()) / 4.0f;
            if (radius > BlurBuilder.BLUR_RADIUS_MAX) {
                radius = BlurBuilder.BLUR_RADIUS_MAX;
            }
            if (radius < 0.0f) {
                radius = 0.0f;
            }
            SquareActivity.this.blurText.setText("" + ((int) radius));
            Log.e(SquareActivity.TAG, "blur radius " + radius);
            SquareActivity.this.mSqView.setBlurBitmap((int) radius);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SquareActivity.this.blurText.setText("" + ((int) (((float) progress) / 4.0f)));
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
            return true;
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

    class SquareView extends View {
        public static final int BACKGROUND_BLUR = 1;
        public static final int BACKGROUND_PATTERN = 0;
        public static final int PATTERN_SENTINEL = -1;
        private static final int INVALID_POINTER_ID = 1;
        private static final int UPPER_SIZE_LIMIT = 2048;
        int backgroundMode;
        float bitmapHeight;
        Matrix bitmapMatrix;
        float bitmapWidth;
        Bitmap blurBitmap;
        Matrix blurBitmapMatrix;
        BlurBuilderNormal blurBuilderNormal;
        PointF centerOriginal;
        Paint dashPaint;
        Path dashPathHorizontal;
        Path dashPathHorizontalTemp;
        Path dashPathVertical;
        Path dashPathVerticalTemp;
        float epsilon;
        float[] f509f;
        Bitmap filterBitmap;
        float finalAngle = 0.0f;
        Paint grayPaint;
        Matrix identityMatrix;
        Matrix inverseMatrix;
        boolean isOrthogonal;
        float mLastTouchX;
        float mLastTouchY;
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
        private int blurRadius;
        private int mActivePointerId;
        private RotationGestureDetector mRotationDetector;

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
            this.paint.setColor(-3355444);

            int min = Math.min(w, h);
            this.viewWidth = min;
            this.viewHeight = min;
            this.offsetX = Math.abs(w - this.viewWidth) / SquareActivity.TAB_INDEX_SQUARE_BLUR;
            this.offsetY = Math.abs(h - this.viewHeight) / SquareActivity.TAB_INDEX_SQUARE_BLUR;
            new Options().inPreferredConfig = Config.ARGB_8888;
            this.bitmapWidth = (float) SquareActivity.this.sourceBitmap.getWidth();
            this.bitmapHeight = (float) SquareActivity.this.sourceBitmap.getHeight();
            float bitmapScale = Math.min(((float) this.viewWidth) / this.bitmapWidth, ((float) this.viewHeight) / this.bitmapHeight);
            float bitmapOffsetX = ((float) this.offsetX) + ((((float) this.viewWidth) - (this.bitmapWidth * bitmapScale)) / 2.0f);
            float bitmapOffsetY = ((float) this.offsetY) + ((((float) this.viewHeight) - (this.bitmapHeight * bitmapScale)) / 2.0f);
            this.bitmapMatrix.postScale(bitmapScale, bitmapScale);
            this.bitmapMatrix.postTranslate(bitmapOffsetX, bitmapOffsetY);
            SquareActivity.this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            this.mRotationDetector = new RotationGestureDetector(this.rotateListener);
            this.grayPaint = new Paint();
            this.grayPaint.setColor(-12303292);
            this.pointPaint = new Paint();
            this.pointPaint.setColor(InputDeviceCompat.SOURCE_ANY);
            this.pointPaint.setStrokeWidth(20.0f);
            this.blurBitmapMatrix = new Matrix();
            this.patternPaint = new Paint(INVALID_POINTER_ID);
            this.patternPaint.setColor(PATTERN_SENTINEL);
            this.dashPaint.setColor(-7829368);
            this.dashPaint.setStyle(Style.STROKE);
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

        public void onDraw(Canvas canvas) {
            if (this.backgroundMode == 0) {
                canvas.drawRect((float) this.offsetX, (float) this.offsetY, (float) (this.offsetX + this.viewWidth), (float) (this.offsetY + this.viewHeight), this.patternPaint);
            }
            if (!(this.blurBitmap == null || this.blurBitmap.isRecycled() || this.backgroundMode != INVALID_POINTER_ID)) {
                canvas.drawBitmap(this.blurBitmap, this.blurBitmapMatrix, this.paint);
            }
            canvas.drawBitmap(SquareActivity.this.sourceBitmap, this.bitmapMatrix, this.paint);
            if (!(this.filterBitmap == null || this.filterBitmap.isRecycled())) {
                canvas.drawBitmap(this.filterBitmap, this.bitmapMatrix, this.paint);
            }
            if (this.isOrthogonal) {
                this.dashPathVertical.transform(this.bitmapMatrix, this.dashPathVerticalTemp);
                this.dashPathHorizontal.transform(this.bitmapMatrix, this.dashPathHorizontalTemp);
                canvas.drawPath(this.dashPathVerticalTemp, this.dashPaint);
                canvas.drawPath(this.dashPathHorizontalTemp, this.dashPaint);
            }
            if (SquareActivity.this.showText) {
                for (int i = BACKGROUND_PATTERN; i < SquareActivity.this.textDataList.size(); i += INVALID_POINTER_ID) {
                    this.textMatrix.set(((TextData) SquareActivity.this.textDataList.get(i)).imageSaveMatrix);
                    canvas.setMatrix(this.textMatrix);
                    canvas.drawText(((TextData) SquareActivity.this.textDataList.get(i)).message, ((TextData) SquareActivity.this.textDataList.get(i)).xPos, ((TextData) SquareActivity.this.textDataList.get(i)).yPos, ((TextData) SquareActivity.this.textDataList.get(i)).textPaint);
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
            float btmScale = ((float) Utility.maxSizeForSave(SquareActivity.this.context, 2048.0f)) / max;
            int newBtmWidth = (int) (((float) this.viewWidth) * btmScale);
            int newBtmHeight = (int) (((float) this.viewHeight) * btmScale);
            if (newBtmWidth <= 0) {
                newBtmWidth = this.viewWidth;
                Log.e(SquareActivity.TAG, "newBtmWidth");
            }
            if (newBtmHeight <= 0) {
                newBtmHeight = this.viewHeight;
                Log.e(SquareActivity.TAG, "newBtmHeight");
            }
            Bitmap savedBitmap = Bitmap.createBitmap(newBtmWidth, newBtmHeight, Config.ARGB_8888);
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
            bitmapCanvas.drawBitmap(SquareActivity.this.sourceBitmap, this.bitmapMatrix, this.paint);
            if (!(this.filterBitmap == null || this.filterBitmap.isRecycled())) {
                bitmapCanvas.drawBitmap(this.filterBitmap, this.bitmapMatrix, this.paint);
            }
            Matrix mat;
            if (SquareActivity.this.textDataList != null) {
                for (i = BACKGROUND_PATTERN; i < SquareActivity.this.textDataList.size(); i += INVALID_POINTER_ID) {
                    mat = new Matrix();
                    mat.set(((TextData) SquareActivity.this.textDataList.get(i)).imageSaveMatrix);
                    mat.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
                    mat.postScale(btmScale, btmScale);
                    bitmapCanvas.setMatrix(mat);
                    bitmapCanvas.drawText(((TextData) SquareActivity.this.textDataList.get(i)).message, ((TextData) SquareActivity.this.textDataList.get(i)).xPos, ((TextData) SquareActivity.this.textDataList.get(i)).yPos, ((TextData) SquareActivity.this.textDataList.get(i)).textPaint);
                }
            }
            for (i = BACKGROUND_PATTERN; i < SquareActivity.this.stickerViewContainer.getChildCount(); i += INVALID_POINTER_ID) {
                mat = new Matrix();
                StickerView view = (StickerView) SquareActivity.this.stickerViewContainer.getChildAt(i);
                StickerData data = view.getStickerData();
                mat.set(data.getCanvasMatrix());
                mat.postTranslate((float) (-this.offsetX), (float) (-this.offsetY));
                mat.postScale(btmScale, btmScale);
                bitmapCanvas.setMatrix(mat);
                if (!(view.stickerBitmap == null || view.stickerBitmap.isRecycled())) {
                    bitmapCanvas.drawBitmap(view.stickerBitmap, data.xPos, data.yPos, view.paint);
                }
            }
            String resultPath = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append(SquareActivity.this.getString(R.string.directory)).append(String.valueOf(System.currentTimeMillis())).append(".jpg").toString();
            new File(resultPath).getParentFile().mkdirs();
            try {
                OutputStream out = new FileOutputStream(resultPath);
                savedBitmap.compress(CompressFormat.JPEG, 90, out);
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
                this.patternPaint = new Paint(1);
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

        public void setBlurBitmap(int radius) {
            if (this.blurBuilderNormal == null) {
                this.blurBuilderNormal = new BlurBuilderNormal();
            }
            this.backgroundMode = INVALID_POINTER_ID;
            this.blurBitmap = this.blurBuilderNormal.createBlurBitmapNDK(SquareActivity.this.sourceBitmap, radius);

            this.blurRadius = this.blurBuilderNormal.getBlur();
            setMatrixCenter(this.blurBitmapMatrix, (float) this.blurBitmap.getWidth(), (float) this.blurBitmap.getHeight());
            postInvalidate();
        }

        public void setCropBitmap(int left, int top, int right, int bottom) {
            Bitmap localCropBtm = SquareActivity.this.sourceBitmap;
            if (((float) right) > this.bitmapWidth) {
                right = (int) this.bitmapWidth;
            }
            if (((float) bottom) > this.bitmapHeight) {
                bottom = (int) this.bitmapHeight;
            }
            if (VERSION.SDK_INT < 12) {
                SquareActivity.this.sourceBitmap = BlurBuilderNormal.createCroppedBitmap(localCropBtm, left, top, right - left, bottom - top, false);
            } else {
                SquareActivity.this.sourceBitmap = Bitmap.createBitmap(localCropBtm, left, top, right - left, bottom - top);
            }
            SquareActivity.this.effectFragment.setBitmap(SquareActivity.this.sourceBitmap);
            SquareActivity.this.effectFragment.execQueue();
            if (localCropBtm != SquareActivity.this.sourceBitmap) {
                localCropBtm.recycle();
            }
            this.bitmapHeight = (float) sourceBitmap.getHeight();
            this.bitmapWidth = (float) sourceBitmap.getWidth();
            setPathPositions();
            setScaleMatrix(0);
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
            SquareActivity.this.mScaleDetector.onTouchEvent(ev);
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

        private class ScaleListener extends SimpleOnScaleGestureListener {
            private ScaleListener() {
            }

            public boolean onScale(ScaleGestureDetector detector) {
                SquareView.this.mScaleFactor = detector.getScaleFactor();
                PointF center;
                if (detector.isInProgress()) {
                    SquareView.this.mScaleFactor = Math.max(0.1f, Math.min(SquareView.this.mScaleFactor, 5.0f));
                    center = SquareView.this.getCenterOfImage();
                    SquareView.this.bitmapMatrix.postScale(SquareView.this.mScaleFactor, SquareView.this.mScaleFactor, center.x, center.y);
                    SquareView.this.invalidate();
                } else {
                    SquareView.this.mScaleFactor = Math.max(0.1f, Math.min(SquareView.this.mScaleFactor, 5.0f));
                    center = SquareView.this.getCenterOfImage();
                    SquareView.this.bitmapMatrix.postScale(SquareView.this.mScaleFactor, SquareView.this.mScaleFactor, center.x, center.y);
                    SquareView.this.invalidate();
                }
                return true;
            }
        }

        /* renamed from: com.lyrebirdstudio.instasquare.lib.SquareActivity.SquareView.1 */
        class C09611 implements RotationGestureDetector.OnRotationGestureListener {
            C09611() {
            }

            public void OnRotation(RotationGestureDetector rotationDetector) {
                float angle = rotationDetector.getAngle();
                float rotation = getMatrixRotation(bitmapMatrix);
                if ((rotation == 0.0f || rotation == 90.0f || rotation == 180.0f || rotation == -180.0f || rotation == -90.0f) && Math.abs(finalAngle - angle) < epsilon) {
                    isOrthogonal = true;
                    return;
                }
                if (Math.abs((rotation - finalAngle) + angle) < epsilon) {
                    angle = finalAngle - rotation;
                    isOrthogonal = true;
                } else if (Math.abs(90.0f - ((rotation - finalAngle) + angle)) < epsilon) {
                    angle = (finalAngle + 90.0f) - rotation;
                    isOrthogonal = true;
                } else if (Math.abs(180.0f - ((rotation - finalAngle) + angle)) < epsilon) {
                    angle = (finalAngle + 180.0f) - rotation;
                    isOrthogonal = true;
                } else if (Math.abs(-180.0f - ((rotation - finalAngle) + angle)) < epsilon) {
                    angle = (finalAngle - 0.024902344f) - rotation;
                    isOrthogonal = true;
                } else if (Math.abs(-90.0f - ((rotation - finalAngle) + angle)) < epsilon) {
                    angle = (finalAngle - 0.049804688f) - rotation;
                    isOrthogonal = true;
                } else {
                    isOrthogonal = false;
                }
                PointF center = getCenterOfImage();
                bitmapMatrix.postRotate(finalAngle - angle, center.x, center.y);
                finalAngle = angle;
                invalidate();
            }
        }
    }

    class C09551 implements FontFragment.FontChoosedListener {
        C09551() {
        }

        public void onOk(TextData textData) {
            SquareActivity.this.canvasText.addTextView(textData);
            SquareActivity.this.getSupportFragmentManager().beginTransaction().remove(SquareActivity.this.fontFragment).commit();
            Log.e(SquareActivity.TAG, "onOK called");
        }
    }

    class C09562 implements StickerView.StickerViewSelectedListener {
        C09562() {
        }

        public void setSelectedView(StickerView stickerView) {
            Log.e("Sticker", "Coming here");
            stickerView.bringToFront();
            stickerView.bringToFront();
            if (VERSION.SDK_INT < 19) {
                SquareActivity.this.stickerViewContainer.requestLayout();
            }
        }

        public void onTouchUp(StickerData data) {
            Log.e("Sticker", "onTouchUp");
        }
    }

    class C09573 implements FragmentCrop.CropListener {
        C09573() {
        }

        public void cropCancelled() {
            SquareActivity.this.getSupportFragmentManager().beginTransaction().remove(SquareActivity.this.cropFragment).commit();
        }

        public void cropApply(int leftPos, int topPos, int rightPos, int bottomPos) {
            SquareActivity.this.mSqView.setCropBitmap(leftPos, topPos, rightPos, bottomPos);
            SquareActivity.this.cropFragment.setBitmap(null);
            SquareActivity.this.getSupportFragmentManager().beginTransaction().remove(SquareActivity.this.cropFragment).commit();
        }
    }

    private class SaveImageTask extends AsyncTask<Object, Object, Object> {
        ProgressDialog progressDialog;
        String resultPath;
        int saveMode;

        private SaveImageTask() {
            this.saveMode = SquareActivity.TAB_INDEX_SQUARE;
            this.resultPath = null;
        }

        protected Object doInBackground(Object... arg0) {
            if (arg0 != null) {
                this.saveMode = ((Integer) arg0[SquareActivity.TAB_INDEX_SQUARE]).intValue();
            }
            this.resultPath = SquareActivity.this.mSqView.saveBitmap();
            return null;
        }

        protected void onPreExecute() {
            this.progressDialog = new ProgressDialog(SquareActivity.this.context);
            this.progressDialog.setMessage("Saving image ...");
            this.progressDialog.show();
        }

        protected void onPostExecute(Object result) {
            Toast msg;
            try {
                if (this.progressDialog != null && this.progressDialog.isShowing()) {
                    this.progressDialog.cancel();
                }
            } catch (Exception e) {
            }
            if (this.saveMode == SquareActivity.this.SAVE_FINISH) {
                super.onPostExecute(result);
                msg = Toast.makeText(SquareActivity.this.context, "Image has been saved to the /SD" + SquareActivity.this.getString(R.string.directory) + " folder.", SquareActivity.TAB_INDEX_SQUARE_BACKGROUND);
                msg.setGravity(17, msg.getXOffset() / SquareActivity.TAB_INDEX_SQUARE_BLUR, msg.getYOffset() / SquareActivity.TAB_INDEX_SQUARE_BLUR);
                msg.show();
                MediaScannerConnectionClient client = new MyMediaScannerConnectionClient(SquareActivity.this.getApplicationContext(), new File(this.resultPath), null);
                SquareActivity.this.finish();
            } else if (this.saveMode == SquareActivity.this.SAVE_INSTAGRAM) {
                try {
                    Intent instagram = new Intent("android.intent.action.SEND");
                    instagram.setType("image/*");
                    instagram.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(this.resultPath)));
                    instagram.putExtra("android.intent.extra.TEXT", new StringBuilder(String.valueOf(SquareActivity.this.getString(R.string.hashtag_twitter))).append(" ").toString());
                    instagram.setPackage("com.instagram.android");
                    SquareActivity.this.startActivityForResult(instagram, SquareActivity.INSTAGRAM_ID);
                } catch (Exception e2) {
                    msg = Toast.makeText(SquareActivity.this.context, SquareActivity.this.getString(R.string.no_instagram_app), SquareActivity.TAB_INDEX_SQUARE_BACKGROUND);
                    msg.setGravity(17, msg.getXOffset() / SquareActivity.TAB_INDEX_SQUARE_BLUR, msg.getYOffset() / SquareActivity.TAB_INDEX_SQUARE_BLUR);
                    msg.show();
                    SquareActivity.this.saveNormal(this.resultPath);
                }
            } else if (this.saveMode == SquareActivity.this.SAVE_NORMAL) {
                SquareActivity.this.saveNormal(this.resultPath);
            }
            MyMediaScannerConnectionClient myMediaScannerConnectionClient = new MyMediaScannerConnectionClient(SquareActivity.this.getApplicationContext(), new File(this.resultPath), null);
        }
    }
}