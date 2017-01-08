package com.app.camera;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by Prashant on 24/12/15.
 */
public class AppController extends Application{

    private static final String TAG = AppController.class.getSimpleName();
    public Bitmap MainBitmap = null;
    public Bitmap cropped = null;
    public Bitmap shareBitmap = null;

    @Override
    public void onCreate(){
        super.onCreate();
       // Fabric.with(this, new Crashlytics());
        // load custom font

    }
}

