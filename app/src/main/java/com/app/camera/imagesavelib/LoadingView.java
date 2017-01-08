package com.app.camera.imagesavelib;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class LoadingView extends RelativeLayout {
    private static final String TAG;

    static {
        TAG = LoadingView.class.getSimpleName();
    }

    public LoadingView(Context context) {
        super(context);
      //  inflate(getContext(), R.layout.interstitial_splash_screen, this);
    }

    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Log.e(TAG, "dispatchKeyEventPreIme(" + event + ")");
        if (event.getKeyCode() != 4) {
            return super.dispatchKeyEventPreIme(event);
        }
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (getWindowToken() != null) {
            try {
                wm.removeView(this);
                Log.e(TAG, "dispatchKeyEventPreIme view removed!");
                return true;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return false;
    }
}
