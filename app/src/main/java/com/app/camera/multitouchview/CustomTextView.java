package com.app.camera.multitouchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Prashant on 25/1/16.
 */
public class CustomTextView extends TextView{
    private static final int PADDING = 8;

    public CustomTextView(Context context) {
        super(context);
        setPadding(PADDING, PADDING, PADDING, PADDING);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextColor(int id){
        setTextColor(id);
    }

    public void setText(String text){
        setText(text);
    }

    public Bitmap getImageBitmap(){
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(80, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        layout(30, 40, 200, 100);
        draw(c);
        return bitmap;
    }
}
