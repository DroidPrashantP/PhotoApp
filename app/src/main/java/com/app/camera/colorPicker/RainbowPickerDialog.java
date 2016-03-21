// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.colorPicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.app.camera.R;
import com.app.camera.common_lib.OnItemSelected;

public class RainbowPickerDialog extends Dialog {

    OnItemSelected listener;
    GridView gridview;

    public RainbowPickerDialog(Context context) {
        super(context);
        setTitle("Color Picker");
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.color_picker);
        gridview = (GridView) findViewById(R.id.gridViewColors);
        gridview.setAdapter(new RainbowPickerAdapter(getContext()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int pos, long l) {
                int value = view.getId();
                Log.e("id",""+ value);
                listener.itemSelected(value);
                dismiss();
            }

        });
    }

    public void setListener(OnItemSelected onitemselected) {
        listener = onitemselected;
    }
}
