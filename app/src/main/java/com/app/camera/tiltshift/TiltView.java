package com.app.camera.tiltshift;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TiltView extends View {
    private static final String TAG = "TiltView";
    Bitmap bitmap;
    Bitmap bitmapBlur;
    int f520h;
    Paint paint;
    float scale;
    float screenHeight;
    float screenWidth;
    TiltHelper tiltHelper;
    int viewHeight;
    int viewWidth;
    int f521w;

    /* renamed from: com.lyrebirdstudio.tiltshift.TiltView.1 */
    class C06251 implements Runnable {
        C06251() {
        }

        public void run() {
            TiltView.this.tiltHelper.startAnimator();
        }
    }

    public TiltView(Context context, Bitmap btm, Bitmap blur, int scrW, int scrH, TiltContext tc) {
        super(context);
        this.paint = new Paint(1);
        this.scale = 1.0f;
        this.bitmap = btm;
        this.bitmapBlur = blur;
        this.f521w = this.bitmap.getWidth();
        this.f520h = this.bitmap.getHeight();
        this.tiltHelper = new TiltHelper(this, this.bitmapBlur, tc, this.f521w, this.f520h);
        this.screenWidth = (float) scrW;
        this.screenHeight = (float) scrH;
        this.scale = Math.min(this.screenWidth / ((float) this.f521w), this.screenHeight / ((float) this.f520h));
        Log.e(TAG, "tilt view scale " + this.scale);
        this.viewWidth = (int) (this.scale * ((float) this.f521w));
        this.viewHeight = (int) (this.scale * ((float) this.f520h));
        this.paint.setFilterBitmap(true);
        post(new C06251());
    }

    public void onDraw(Canvas canvas) {
        canvas.scale(this.scale, this.scale);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, this.paint);
        this.tiltHelper.drawTiltShift(canvas, this.bitmapBlur, this.f521w, this.f520h);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int myWidth = (int) (((double) MeasureSpec.getSize(heightMeasureSpec)) * 0.5d);
        super.onMeasure(MeasureSpec.makeMeasureSpec(this.viewWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(this.viewHeight, MeasureSpec.AT_MOST));
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.tiltHelper.onTouchEvent(event);
    }
}
