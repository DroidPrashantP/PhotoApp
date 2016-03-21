// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.colorPicker;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.app.camera.R;

import java.util.ArrayList;
import java.util.List;

public class RainbowPickerAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> colorList = new ArrayList<Integer>();
    int colorGridColumnWidth;
    String colorStringList[] = new String[]{"#c41915", "#ca4318", "#ec6c20","#fd8a26", "#9aca28","#c8b654", "#c79c38", "#ca7620","#87af43","#52a526","#66961A", "#13874B",
    "#20AA66","#66B393", "#1897A6","#6FB2B8","#3cb6e3","#199ACA","#1892e6","#1688cd","#8266C9","#754CB2","#6568C9","#1A56B6", "#A868C9","#754CB2","#9D44B6","#FB454B","#E52464"};

    public RainbowPickerAdapter(Context context) {

        this.context = context;

        // defines the width of each color square
        colorGridColumnWidth = 80;

       // int colorCount = 25;
        for (int i = 0; i<colorStringList.length; i++){
            Integer intcol = Color.parseColor(colorStringList[i]);
            colorList.add(intcol);
        }

//        int step = 256 / (colorCount / 6);
//        int red = 0, green = 0, blue = 0;
//
//        // FF 00 00 --> FF FF 00
//        for (red = 255, green = 0, blue = 0; green <= 255; green += step)
//            colorList.add(Color.rgb(red, green, blue));
//
//        // FF FF 00 --> 00 FF 00
//        for (red = 255, green = 255, blue = 0; red >= 0; red -= step)
//            colorList.add(Color.rgb(red, green, blue));
//
//        // 00 FF 00 --> 00 FF FF
//        for (red = 0, green = 255, blue = 0; blue <= 255; blue += step)
//            colorList.add(Color.rgb(red, green, blue));
//
//        // 00 FF FF -- > 00 00 FF
//        for (red = 0, green = 255, blue = 255; green >= 0; green -= step)
//            colorList.add(Color.rgb(red, green, blue));
//
//        // 00 00 FF --> FF 00 FF
//        for (red = 0, green = 0, blue = 255; red <= 255; red += step)
//            colorList.add(Color.rgb(red, green, blue));
//
//        // FF 00 FF -- > FF 00 00
//        for (red = 255, green = 0, blue = 255; blue >= 0; blue -= 256 / step)
//            colorList.add(Color.rgb(red, green, blue));
//
//        // add gray colors
//        for (int i = 255; i >= 0; i -= 11) {
//            colorList.add(Color.rgb(i, i, i));
//        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // can we reuse a view?
        if (convertView == null) {
            imageView = new ImageView(context);
            // set the width of each color square
            imageView.setLayoutParams(new GridView.LayoutParams(colorGridColumnWidth, colorGridColumnWidth));

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setBackgroundColor(colorList.get(position));
        imageView.setId(colorList.get(position));

        return imageView;
    }

    public int getCount() {
        return colorList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
