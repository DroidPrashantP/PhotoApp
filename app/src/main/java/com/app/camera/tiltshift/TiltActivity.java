package com.app.camera.tiltshift;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.view.View;

import com.app.camera.R;


public class TiltActivity extends FragmentActivity {
    TiltFragment titlFragment;

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
        Bitmap bitmapBlur = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
        setContentView(R.layout.activity_tilt);
        FragmentManager fm = getSupportFragmentManager();
        this.titlFragment = (TiltFragment) fm.findFragmentByTag("my_tilt_fragment");
        if (this.titlFragment == null) {
            this.titlFragment = new TiltFragment();
            this.titlFragment.setBitmaps(bitmap, bitmapBlur);
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.tilt_fragment_container, this.titlFragment, "my_tilt_fragment");
            ft.commit();
            return;
        }
        getSupportFragmentManager().beginTransaction().show(this.titlFragment).commit();
    }
}
