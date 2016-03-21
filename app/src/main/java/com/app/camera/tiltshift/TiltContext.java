package com.app.camera.tiltshift;

import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TiltContext implements Parcelable, Serializable {
    public static final Creator<TiltContext> CREATOR;
    private static final String TAG = "TiltContext";
    private static final long serialVersionUID = -5455273508176976785L;
    transient Shader gradient;
    transient Shader gradientTouch;
    int f515h;
    MyMatrix matrix;
    public TiltMode mode;
    int f516w;

    /* renamed from: com.lyrebirdstudio.tiltshift.TiltContext.1 */
    static class C06211 implements Creator<TiltContext> {
        C06211() {
        }

        public TiltContext createFromParcel(Parcel in) {
            return new TiltContext(in);
        }

        public TiltContext[] newArray(int size) {
            return new TiltContext[size];
        }
    }

    public enum TiltMode implements Parcelable {
        NONE(0),
        RADIAL(1),
        LINEAR(2);
        
        public static final Creator<TiltMode> CREATOR;
        private int f514v;

        /* renamed from: com.lyrebirdstudio.tiltshift.TiltContext.TiltMode.1 */
        static class C06221 implements Creator<TiltMode> {
            C06221() {
            }

            public TiltMode createFromParcel(Parcel source) {
                TiltMode tiltMode = TiltMode.values()[source.readInt()];
                tiltMode.setValue(source.readInt());
                return tiltMode;
            }

            public TiltMode[] newArray(int size) {
                return new TiltMode[size];
            }
        }

        static {
            CREATOR = new C06221();
        }

        private TiltMode(int value) {
            this.f514v = value;
        }

        public void setValue(int value) {
            this.f514v = value;
        }

        public int getValue() {
            return this.f514v;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
            dest.writeInt(this.f514v);
        }
    }

    public TiltContext(TiltMode mode, int w, int h) {
        this.mode = mode;
        this.matrix = new MyMatrix(getMatrix());
        this.matrix.reset();
        this.f516w = w;
        this.f515h = h;
        if (mode == TiltMode.LINEAR) {
            this.gradient = createLinearGradient(h);
            this.gradientTouch = createLinearGradientPaint(h);
        } else if (mode == TiltMode.RADIAL) {
            this.gradient = createRadialGradient(w, h);
            this.gradientTouch = createRadialGradientPaint(w, h);
        } else {
            this.gradient = null;
            this.gradientTouch = null;
        }
    }

    public TiltContext(TiltContext tiltContext) {
        this.gradient = tiltContext.gradient;
        this.gradientTouch = tiltContext.gradientTouch;
        this.mode = tiltContext.mode;
        this.matrix = new MyMatrix(tiltContext.matrix);
        this.f516w = tiltContext.f516w;
        this.f515h = tiltContext.f515h;
    }

    public void setLocalMatrix() {
        if (this.gradient != null) {
            this.gradient.setLocalMatrix(this.matrix);
        }
        if (this.gradientTouch != null) {
            this.gradientTouch.setLocalMatrix(this.matrix);
        }
    }

    public MyMatrix getMatrix() {
        return this.matrix;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.f516w);
        dest.writeInt(this.f515h);
        dest.writeParcelable(this.matrix, flags);
        dest.writeParcelable(this.mode, flags);
    }

    public TiltContext(Parcel in) {
        Log.e(TAG, "tilt context create");
        this.f516w = in.readInt();
        this.f515h = in.readInt();
        this.matrix = (MyMatrix) in.readParcelable(MyMatrix.class.getClassLoader());
        this.mode = (TiltMode) in.readParcelable(TiltMode.class.getClassLoader());
        if (this.mode == TiltMode.LINEAR) {
            this.gradient = createLinearGradient(this.f515h);
            this.gradientTouch = createLinearGradientPaint(this.f515h);
        } else if (this.mode == TiltMode.RADIAL) {
            this.gradient = createRadialGradient(this.f516w, this.f515h);
            this.gradientTouch = createRadialGradientPaint(this.f516w, this.f515h);
        }
        setLocalMatrix();
    }

    static {
        CREATOR = new C06211();
    }

    public static LinearGradient createLinearGradient(int h) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, (float) h, new int[]{-16711936, -16711936, -1291780352, MotionEventCompat.ACTION_POINTER_INDEX_MASK, MotionEventCompat.ACTION_POINTER_INDEX_MASK, -1291780352, -16711936, -16711936}, new float[]{0.0f, 0.055555556f, 0.3148148f, 0.4351852f, 0.5648148f, 0.6851852f, 0.9444444f, 1.0f}, TileMode.CLAMP);
    }

    public static LinearGradient createLinearGradientPaint(int h) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, (float) h, new int[]{-16711936, -872349952, MotionEventCompat.ACTION_POINTER_INDEX_MASK, MotionEventCompat.ACTION_POINTER_INDEX_MASK, -872349952, -16711936}, new float[]{0.0f, 0.3148148f, 0.4351852f, 0.5648148f, 0.6851852f, 1.0f}, TileMode.CLAMP);
    }

    public static RadialGradient createRadialGradient(int w, int h) {
        if (w > 0 && h > 0) {
            float radius = (float) Math.sqrt((double) (((float) (w * h)) / 16.0f));
        }
        return new RadialGradient((float) (w / 2), (float) (h / 2), ((float) Math.min(w, h)) * 0.555f, new int[]{MotionEventCompat.ACTION_POINTER_INDEX_MASK, MotionEventCompat.ACTION_POINTER_INDEX_MASK, -2147418368, -16711936}, new float[]{0.0f, 0.10309278f, 0.30927834f, 1.0f}, TileMode.CLAMP);
    }

    public static RadialGradient createRadialGradientPaint(int w, int h) {
        float radius = 250.0f;
        if (w > 0 && h > 0) {
            radius = (float) Math.sqrt((double) (((float) (w * h)) * 0.077f));
        }
        return new RadialGradient((float) (w / 2), (float) (h / 2), radius, new int[]{MotionEventCompat.ACTION_POINTER_INDEX_MASK, MotionEventCompat.ACTION_POINTER_INDEX_MASK, -16711936}, new float[]{0.0f, 0.34f, 1.0f}, TileMode.CLAMP);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        Log.e(TAG, "w " + this.f516w);
        Log.e(TAG, "h " + this.f515h);
        oos.writeInt(this.f516w);
        oos.writeInt(this.f515h);
        oos.writeObject(this.matrix);
        oos.writeObject(this.mode);
    }

    private void readObject(ObjectInputStream ois) throws Exception, ClassNotFoundException {
        ois.defaultReadObject();
        Log.e(TAG, "tilt readObject");
        this.f516w = ois.readInt();
        this.f515h = ois.readInt();
        Log.e(TAG, "w " + this.f516w);
        Log.e(TAG, "h " + this.f515h);
        this.matrix = (MyMatrix) ois.readObject();
        this.mode = (TiltMode) ois.readObject();
        if (this.mode == TiltMode.LINEAR) {
            this.gradient = createLinearGradient(this.f515h);
            this.gradientTouch = createLinearGradientPaint(this.f515h);
        } else if (this.mode == TiltMode.RADIAL) {
            this.gradient = createRadialGradient(this.f516w, this.f515h);
            this.gradientTouch = createRadialGradientPaint(this.f516w, this.f515h);
        }
        setLocalMatrix();
    }
}
