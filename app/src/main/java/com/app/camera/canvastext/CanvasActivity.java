package com.app.camera.canvastext;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.camera.R;
import com.app.camera.colorPicker.RainbowPickerDialog;
import com.app.camera.common_lib.OnItemSelected;
import com.app.camera.fragments.FontFragment;

import java.util.ArrayList;


public class CanvasActivity extends FragmentActivity {
    private String TAG;
    CanvasTextView canvasTextView;
    Context context;
    CustomRelativeLayout crl;
    FontFragment.FontChoosedListener fontChoosedListener;
    FontFragment fragment;

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    public CanvasActivity() {
        this.context = this;
        this.TAG = "CanvasActivity";
        this.fontChoosedListener = new FontFragment.FontChoosedListener() {
            @Override
            public void onOk(TextData textData) {
                CanvasActivity.this.crl.addTextView(textData);
                CanvasActivity.this.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                Log.e(CanvasActivity.this.TAG, "onOK called");
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        setContentView(R.layout.activity_canvas_2);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.my_main_layout);
        ArrayList<TextData> textDataListParam = new ArrayList();
        Matrix canvasMatrix = new Matrix();
        ((RelativeLayout) findViewById(R.id.fragment_container)).bringToFront();
        this.fragment = new FontFragment();
        this.fragment.setArguments(new Bundle());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "MY_FRAGMENT").commit();
        Log.e(this.TAG, "add fragment");
        this.fragment.setFontChoosedListener(this.fontChoosedListener);
    }

    public void myClickHandler(View view) {
        if (view.getId() == R.id.button_text_color) {
            RainbowPickerDialog dialog = new RainbowPickerDialog(this);
            dialog.setListener(new OnItemSelected() {
                @Override
                public void itemSelected(int color) {
                    CanvasActivity.this.canvasTextView.setTextColor(color);
                }
            });
            dialog.show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.canvas, menu);
        return true;
    }
}
