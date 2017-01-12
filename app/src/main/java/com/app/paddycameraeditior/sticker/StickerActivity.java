package com.app.paddycameraeditior.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;


import com.app.paddycameraeditior.R;

import java.util.ArrayList;

public class StickerActivity extends FragmentActivity {
    protected static final String TAG;
    Context context;
    int currentStickerIndex;
    StickerGalleryFragment galleryFragment;
    ArrayList<StickerView> stickerList;
    FrameLayout stickerViewContainer;
    StickerView.StickerViewSelectedListener stickerViewSelectedListner;

    class C06122 implements OnClickListener {
        C06122() {
        }

        public void onClick(View v) {
            StickerActivity.this.addStickerGalleryFragment();
        }
    }
    class C09831 implements StickerView.StickerViewSelectedListener {
        C09831() {
        }

        public void setSelectedView(StickerView stickerView) {
            stickerView.bringToFront();
            StickerActivity.this.currentStickerIndex = StickerActivity.this.stickerList.indexOf(stickerView);
            for (int i = 0; i < StickerActivity.this.stickerList.size(); i++) {
                if (StickerActivity.this.currentStickerIndex != i) {
                    ((StickerView) StickerActivity.this.stickerList.get(i)).setViewSelected(false);
                } else {
                    ((StickerView) StickerActivity.this.stickerList.get(i)).setViewSelected(true);
                }
            }
            stickerView.bringToFront();
            if (VERSION.SDK_INT < 19) {
                StickerActivity.this.stickerViewContainer.requestLayout();
            }
        }

        public void onTouchUp(StickerData data) {
        }
    }

    class C09843 implements StickerGalleryListener {
        C09843() {
        }

        public void onGalleryOkSingleImage(int resId) {
            StickerView stickerView = new StickerView(StickerActivity.this.context, BitmapFactory.decodeResource(StickerActivity.this.getResources(), resId), null, BitmapFactory.decodeResource(StickerActivity.this.getResources(), R.drawable.ic_reset_48x48), BitmapFactory.decodeResource(StickerActivity.this.getResources(), R.drawable.ok_white), resId);
            if (VERSION.SDK_INT < 17) {
                stickerView.setId(Utility.generateViewId());
            } else {
                stickerView.setId(Utility.generateViewId());
            }
            stickerView.setStickerViewSelectedListener(StickerActivity.this.stickerViewSelectedListner);
            if (StickerActivity.this.stickerViewContainer == null) {
                StickerActivity.this.stickerViewContainer = (FrameLayout) StickerActivity.this.findViewById(R.id.sticker_view_container);
            }
            StickerActivity.this.stickerViewContainer.addView(stickerView);
            FragmentManager fm = StickerActivity.this.getSupportFragmentManager();
            if (StickerActivity.this.galleryFragment == null) {
                StickerActivity.this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
            }
            fm.beginTransaction().hide(StickerActivity.this.galleryFragment).commit();
        }

        public void onGalleryOkImageArray(int[] ImageIdList) {
            Bitmap removeBitmap = BitmapFactory.decodeResource(StickerActivity.this.getResources(), R.drawable.ic_reset_48x48);
            Bitmap scaleBitmap = BitmapFactory.decodeResource(StickerActivity.this.getResources(), R.drawable.ok_white);
            for (int i = 0; i < ImageIdList.length; i++) {
                StickerView stickerView = new StickerView(StickerActivity.this.context, BitmapFactory.decodeResource(StickerActivity.this.getResources(), ImageIdList[i]), null, removeBitmap, scaleBitmap, ImageIdList[i]);
                stickerView.setStickerViewSelectedListener(StickerActivity.this.stickerViewSelectedListner);
                if (StickerActivity.this.stickerViewContainer == null) {
                    StickerActivity.this.stickerViewContainer = (FrameLayout) StickerActivity.this.findViewById(R.id.sticker_view_container);
                }
                StickerActivity.this.stickerViewContainer.addView(stickerView);
            }
            FragmentManager fm = StickerActivity.this.getSupportFragmentManager();
            if (StickerActivity.this.galleryFragment == null) {
                StickerActivity.this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
            }
            fm.beginTransaction().hide(StickerActivity.this.galleryFragment).commit();
        }

        public void onGalleryCancel() {
            StickerActivity.this.getSupportFragmentManager().beginTransaction().hide(StickerActivity.this.galleryFragment).commit();
        }
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    public StickerActivity() {
        this.context = this;
        this.currentStickerIndex = 0;
        this.stickerViewSelectedListner = new C09831();
    }

    static {
        TAG = StickerActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        if (savedInstanceState != null) {
            this.galleryFragment = (StickerGalleryFragment) getSupportFragmentManager().findFragmentByTag("myFragmentTag");
            if (this.galleryFragment != null) {
              //  this.galleryFragment.setGalleryListener(createGalleryListener());
            }
        }
        setContentView(R.layout.activity_sticker);
        this.stickerList = new ArrayList();
        this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
        this.stickerViewContainer.bringToFront();
        ((Button) findViewById(R.id.button1)).setOnClickListener(new C06122());
        if (savedInstanceState != null) {
            Log.e(TAG, "galleryFragment savedInstanceState not null ");
            FragmentManager fm = getSupportFragmentManager();
            this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
            if (this.galleryFragment != null) {
                fm.beginTransaction().hide(this.galleryFragment).commit();
                //this.galleryFragment.setGalleryListener(createGalleryListener());
                Log.e(TAG, "galleryFragment not null ");
                return;
            }
            return;
        }
        addStickerGalleryFragment();
    }

    public void addStickerGalleryFragment() {
        Log.e(TAG, "addStickerGalleryFragment");
        FragmentManager fm = getSupportFragmentManager();
        this.galleryFragment = (StickerGalleryFragment) fm.findFragmentByTag("myStickerFragmentTag");
        if (this.galleryFragment == null) {
            this.galleryFragment = new StickerGalleryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.sticker_grid_fragment_container, this.galleryFragment, "myStickerFragmentTag");
            ft.commit();
           // this.galleryFragment.setGalleryListener(createGalleryListener());
            findViewById(R.id.sticker_grid_fragment_container).bringToFront();
        } else {
            getSupportFragmentManager().beginTransaction().show(this.galleryFragment).commit();
            findViewById(R.id.sticker_grid_fragment_container).bringToFront();
        }
       // this.galleryFragment.setTotalImage(this.stickerViewContainer.getChildCount());
    }

    //StickerGalleryFragment.StickerGalleryListener createGalleryListener() {
//        return new C09843();
//    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (this.stickerViewContainer != null && this.stickerViewContainer.getChildCount() > 0) {
            int size = this.stickerViewContainer.getChildCount();
            StickerData[] stickerDataArray = new StickerData[size];
            for (int i = 0; i < size; i++) {
                stickerDataArray[i] = ((StickerView) this.stickerViewContainer.getChildAt(i)).getStickerData();
                Log.e(TAG, "stickerDataArray[i].resId " + stickerDataArray[i].resId);
            }
            savedInstanceState.putParcelableArray("sticker_data_array", stickerDataArray);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bitmap removeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_reset_48x48);
        Bitmap scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_reset_48x48);
        StickerData[] stickerDataArray = (StickerData[]) savedInstanceState.getParcelableArray("sticker_data_array");
        for (int i = 0; i < stickerDataArray.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), stickerDataArray[i].resId);
            Log.e(TAG, "stickerDataArray[i].resId " + stickerDataArray[i].resId);
            StickerView stickerView = new StickerView(this.context, bitmap, stickerDataArray[i], removeBitmap, scaleBitmap, stickerDataArray[i].resId);
            stickerView.setStickerViewSelectedListener(this.stickerViewSelectedListner);
            if (this.stickerViewContainer == null) {
                this.stickerViewContainer = (FrameLayout) findViewById(R.id.sticker_view_container);
            }
            this.stickerViewContainer.addView(stickerView);
        }
    }

    public void onBackPressed() {
        this.galleryFragment = (StickerGalleryFragment) getSupportFragmentManager().findFragmentByTag("myStickerFragmentTag");
        if (this.galleryFragment == null || !this.galleryFragment.isVisible()) {
            finish();
        } else {
           // this.galleryFragment.backtrace();
        }
    }
}
