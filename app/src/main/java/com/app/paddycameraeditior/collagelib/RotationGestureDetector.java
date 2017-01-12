package com.app.paddycameraeditior.collagelib;

import android.view.MotionEvent;

public class RotationGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private float fX;
    private float fY;
    private float mAngle;
    private OnRotationGestureListener mListener;
    private int ptrID1;
    private int ptrID2;
    private float sX;
    private float sY;

    public interface OnRotationGestureListener {
        void OnRotation(RotationGestureDetector rotationGestureDetector);
    }

    public float getAngle() {
        return this.mAngle;
    }

    public RotationGestureDetector(OnRotationGestureListener listener) {
        this.mListener = listener;
        this.ptrID1 = INVALID_POINTER_ID;
        this.ptrID2 = INVALID_POINTER_ID;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MirrorActivity.INDEX_MIRROR /*0*/:
                this.ptrID1 = event.getPointerId(event.getActionIndex());
                break;
            case MirrorActivity.INDEX_MIRROR_3D /*1*/:
                this.ptrID1 = INVALID_POINTER_ID;
                break;
            case MirrorActivity.INDEX_MIRROR_RATIO /*2*/:
                if (!(this.ptrID1 == INVALID_POINTER_ID || this.ptrID2 == INVALID_POINTER_ID)) {
                    int index1Move = event.findPointerIndex(this.ptrID1);
                    int index2Move = event.findPointerIndex(this.ptrID2);
                    int pointerCountMove = event.getPointerCount();
                    if (index1Move >= 0 && index1Move < pointerCountMove && index2Move >= 0 && index2Move < pointerCountMove) {
                        float nsX = event.getX(event.findPointerIndex(this.ptrID1));
                        float nsY = event.getY(event.findPointerIndex(this.ptrID1));
                        this.mAngle = angleBetweenLines(this.fX, this.fY, this.sX, this.sY, event.getX(event.findPointerIndex(this.ptrID2)), event.getY(event.findPointerIndex(this.ptrID2)), nsX, nsY);
                        if (this.mListener != null) {
                            this.mListener.OnRotation(this);
                            break;
                        }
                    }
                }
                break;
            case MirrorActivity.INDEX_MIRROR_EFFECT /*3*/:
                this.ptrID1 = INVALID_POINTER_ID;
                this.ptrID2 = INVALID_POINTER_ID;
                break;
            case MirrorActivity.INDEX_MIRROR_ADJUSTMENT /*5*/:
                this.ptrID2 = event.getPointerId(event.getActionIndex());
                int index1 = event.findPointerIndex(this.ptrID1);
                int index2 = event.findPointerIndex(this.ptrID2);
                int pointerCount = event.getPointerCount();
                if (index1 >= 0 && index1 < pointerCount && index2 >= 0 && index2 < pointerCount) {
                    this.sX = event.getX(index1);
                    this.sY = event.getY(index1);
                    this.fX = event.getX(index2);
                    this.fY = event.getY(index2);
                    break;
                }
            case MirrorActivity.TAB_SIZE /*6*/:
                this.ptrID2 = INVALID_POINTER_ID;
                break;
        }
        return true;
    }

    private float angleBetweenLines(float fX, float fY, float sX, float sY, float nfX, float nfY, float nsX, float nsY) {
        float angle = ((float) Math.toDegrees((double) (((float) Math.atan2((double) (fY - sY), (double) (fX - sX))) - ((float) Math.atan2((double) (nfY - nsY), (double) (nfX - nsX)))))) % 360.0f;
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        if (angle > 180.0f) {
            return angle - 360.0f;
        }
        return angle;
    }
}
