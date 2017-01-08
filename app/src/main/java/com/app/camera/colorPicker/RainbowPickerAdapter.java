// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.colorPicker;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
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
//    String colorStringList[] = new String[]{"#c41915", "#ca4318", "#ec6c20","#fd8a26", "#9aca28","#c8b654", "#c79c38", "#ca7620","#87af43","#52a526","#66961A", "#13874B",
//    "#20AA66","#66B393", "#1897A6","#6FB2B8","#3cb6e3","#199ACA","#1892e6","#1688cd","#8266C9","#754CB2","#6568C9","#1A56B6", "#A868C9","#754CB2","#9D44B6","#FB454B","#E52464"};
    String colorStringList[] = new String[]{"#FFFFFF", "#EFDECD", "#CD4A4A", "#CC6666", "#BC5D58", "#FF5349", "#FD5E53", "#FD7C6E", "#FDBCB4", "#FF6E4A", "#FFA089", "#EA7E5D", "#B4674D", "#A5694F", "#FF7538", "#FF7F49", "#DD9475", "#FF8243", "#FFA474", "#9F8170", "#CD9575", "#EFCDB8", "#D68A59", "#DEAA88", "#FAA76C", "#FFCFAB", "#FFBD88", "#FDD9B5", "#FFA343", "#EFDBC5", "#FFB653", "#E7C697", "#8A795D", "#FAE7B5", "#FFCF48", "#FCD975", "#FDDB6D", "#FCE883", "#F0E891", "#ECEABE", "#BAB86C", "#FDFC74", "#FDFC74", "#FFFF99", "#C5E384", "#B2EC5D", "#87A96B", "#A8E4A0", "#1DF914", "#76FF7A", "#71BC78", "#6DAE81", "#9FE2BF", "#1CAC78", "#30BA8F", "#45CEA2", "#3BB08F", "#1CD3A2", "#17806D", "#158078", "#1FCECB", "#78DBE2", "#77DDE7", "#80DAEB", "#414A4C", "#199EBD", "#1CA9C9", "#1DACD6", "#9ACEEB", "#1A4876", "#1974D2", "#2B6CC4", "#1F75FE", "#C5D0E6", "#B0B7C6", "#5D76CB", "#A2ADD0", "#979AAA", "#ADADD6", "#7366BD", "#7442C8", "#7851A9", "#9D81BA", "#926EAE", "#CDA4DE", "#8F509D", "#C364C5", "#FB7EFD", "#FC74FD", "#8E4585", "#FF1DCE", "#FF1DCE", "#FF48D0", "#E6A8D7", "#C0448F", "#6E5160", "#DD4492", "#FF43A4", "#F664AF", "#FCB4D5", "#FFBCD9", "#F75394", "#FFAACC", "#E3256B", "#FDD7E4", "#CA3767", "#DE5D83", "#FC89AC", "#F780A1", "#C8385A", "#EE204D", "#FF496C", "#EF98AA", "#FC6C85", "#FC2847", "#FF9BAA", "#CB4154", "#EDEDED", "#DBD7D2", "#CDC5C2", "#95918C", "#232323"};

    public RainbowPickerAdapter(Context context) {
        this.context = context;
        int green;
        int red;
        int blue;
        // defines the width of each color square
        colorGridColumnWidth = 80;

       // int colorCount = 25;
        for (int i = 0; i<colorStringList.length; i++){
            Integer intcol = Color.parseColor(colorStringList[i]);
            colorList.add(intcol);
        }

        this.context = context;
        this.colorGridColumnWidth = (int) context.getResources().getDimension(R.dimen.colorGridColumnWidth);

//        int step = AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY / 16;
//        for (green = 0; green <= MotionEventCompat.ACTION_MASK; green += 16) {
//            this.colorList.add(Integer.valueOf(Color.rgb(MotionEventCompat.ACTION_MASK, green, 0)));
//        }
//        for (red = MotionEventCompat.ACTION_MASK; red >= 0; red -= 16) {
//            this.colorList.add(Integer.valueOf(Color.rgb(red, MotionEventCompat.ACTION_MASK, 0)));
//        }
//        for (blue = 0; blue <= MotionEventCompat.ACTION_MASK; blue += 16) {
//            this.colorList.add(Integer.valueOf(Color.rgb(0, MotionEventCompat.ACTION_MASK, blue)));
//        }
//        for (green = MotionEventCompat.ACTION_MASK; green >= 0; green -= 16) {
//            this.colorList.add(Integer.valueOf(Color.rgb(0, green, MotionEventCompat.ACTION_MASK)));
//        }
//        for (red = 0; red <= MotionEventCompat.ACTION_MASK; red += 16) {
//            this.colorList.add(Integer.valueOf(Color.rgb(red, 0, MotionEventCompat.ACTION_MASK)));
//        }
//        for (blue = MotionEventCompat.ACTION_MASK; blue >= 0; blue -= 16) {
//            this.colorList.add(Integer.valueOf(Color.rgb(MotionEventCompat.ACTION_MASK, 0, blue)));
//        }
//        for (int i = MotionEventCompat.ACTION_MASK; i >= 0; i -= 11) {
//            this.colorList.add(Integer.valueOf(Color.rgb(i, i, i)));
//        }



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
