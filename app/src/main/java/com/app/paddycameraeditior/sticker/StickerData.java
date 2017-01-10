package com.app.paddycameraeditior.sticker;

import android.graphics.Matrix;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StickerData implements Serializable, Parcelable {
    public static final Creator<StickerData> CREATOR;
    private static final String TAG;
    private static final long serialVersionUID = 3789254141896332763L;
    MyMatrix canvasMatrix;
    public MyMatrix imageSaveMatrix;
    int resId;
    public float xPos;
    public float yPos;

    /* renamed from: com.lyrebirdstudio.sticker.StickerData.1 */
    static class C06131 implements Creator<StickerData> {
        C06131() {
        }

        public StickerData createFromParcel(Parcel in) {
            return new StickerData(in);
        }

        public StickerData[] newArray(int size) {
            return new StickerData[size];
        }
    }

    static {
        TAG = StickerData.class.getSimpleName();
        CREATOR = new C06131();
    }

    public int getResId() {
        return this.resId;
    }

    public void setCanvasMatrix(Matrix mat) {
        this.canvasMatrix.set(mat);
    }

    public MyMatrix getCanvasMatrix() {
        return this.canvasMatrix;
    }

    public StickerData(int resId) {
        this.canvasMatrix = new MyMatrix(imageSaveMatrix);
        this.canvasMatrix.reset();
        this.resId = resId;
    }

    public StickerData(StickerData src) {
        if (src.canvasMatrix != null) {
            this.canvasMatrix = new MyMatrix(src.canvasMatrix);
        }
        this.xPos = src.xPos;
        this.yPos = src.yPos;
        if (src.imageSaveMatrix != null) {
            this.imageSaveMatrix = new MyMatrix(src.imageSaveMatrix);
        }
        this.resId = src.resId;
    }

    public void set(StickerData src) {
        if (src.canvasMatrix != null) {
            this.canvasMatrix = new MyMatrix(src.canvasMatrix);
        }
        this.xPos = src.xPos;
        this.yPos = src.yPos;
        if (src.imageSaveMatrix != null) {
            this.imageSaveMatrix = new MyMatrix(src.imageSaveMatrix);
        }
        this.resId = src.resId;
    }

    public void setImageSaveMatrix(Matrix mtr) {
        MyMatrix inverse = new MyMatrix(imageSaveMatrix);
        mtr.invert(inverse);
        MyMatrix canvas = new MyMatrix(inverse);
        canvas.set(this.canvasMatrix);
        inverse.preConcat(canvas);
        this.imageSaveMatrix = inverse;
        Log.e(TAG, "setImageSaveMatrix");
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeFloat(this.xPos);
        oos.writeFloat(this.yPos);
        oos.writeObject(this.canvasMatrix);
        oos.writeObject(this.imageSaveMatrix);
        oos.writeInt(this.resId);
    }

    private void readObject(ObjectInputStream ois) throws Exception, ClassNotFoundException {
        ois.defaultReadObject();
        this.xPos = ois.readFloat();
        this.yPos = ois.readFloat();
        this.canvasMatrix = (MyMatrix) ois.readObject();
        this.imageSaveMatrix = (MyMatrix) ois.readObject();
        this.resId = ois.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.xPos);
        dest.writeFloat(this.yPos);
        dest.writeParcelable(this.canvasMatrix, flags);
        dest.writeParcelable(this.imageSaveMatrix, flags);
        dest.writeInt(this.resId);
    }

    public StickerData(Parcel in) {
        this.xPos = in.readFloat();
        this.yPos = in.readFloat();
        this.canvasMatrix = (MyMatrix) in.readParcelable(MyMatrix.class.getClassLoader());
        this.imageSaveMatrix = (MyMatrix) in.readParcelable(MyMatrix.class.getClassLoader());
        this.resId = in.readInt();
    }

    public static StickerData[] toStickerData(Parcelable[] parcelables) {
        if (parcelables == null) {
            return null;
        }
        StickerData[] objects = new StickerData[parcelables.length];
        System.arraycopy(parcelables, 0, objects, 0, parcelables.length);
        return objects;
    }
}
