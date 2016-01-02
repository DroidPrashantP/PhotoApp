package com.app.camera.utils.CustomViews;

/**
 * Created by Prashant on 18/12/15.
 */
import android.view.MotionEvent;

public class RotationGestureDetector
{
    public static interface OnRotationGestureListener
    {

        public abstract void OnRotation(RotationGestureDetector rotationgesturedetector);
    }


    private static final int INVALID_POINTER_ID = -1;
    private float fX;
    private float fY;
    private float mAngle;
    private OnRotationGestureListener mListener;
    private int ptrID1;
    private int ptrID2;
    private float sX;
    private float sY;

    public RotationGestureDetector(OnRotationGestureListener onrotationgesturelistener)
    {
        mListener = onrotationgesturelistener;
        ptrID1 = -1;
        ptrID2 = -1;
    }

    private float angleBetweenLines(float f, float f1, float f2, float f3, float f4, float f5, float f6,
                                    float f7)
    {
        f1 = (float)Math.toDegrees((float)Math.atan2(f1 - f3, f - f2) - (float)Math.atan2(f5 - f7, f4 - f6)) % 360F;
        f = f1;
        if (f1 < -180F)
        {
            f = f1 + 360F;
        }
        f1 = f;
        if (f > 180F)
        {
            f1 = f - 360F;
        }
        return f1;
    }

    public float getAngle()
    {
        return mAngle;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        motionevent.getActionMasked();
        ptrID1 = motionevent.getPointerId(motionevent.getActionIndex());
        ptrID2 = motionevent.getPointerId(motionevent.getActionIndex());
        int i = motionevent.findPointerIndex(ptrID1);
        int k = motionevent.findPointerIndex(ptrID2);
        int i1 = motionevent.getPointerCount();
        if (i >= 0 && i < i1 && k >= 0 && k < i1)
        {
            sX = motionevent.getX(i);
            sY = motionevent.getY(i);
            fX = motionevent.getX(k);
            fY = motionevent.getY(k);
        }
        if (ptrID1 != -1 && ptrID2 != -1)
        {
            int j = motionevent.findPointerIndex(ptrID1);
            int l = motionevent.findPointerIndex(ptrID2);
            int j1 = motionevent.getPointerCount();
            if (j >= 0 && j < j1 && l >= 0 && l < j1)
            {
                float f = motionevent.getX(motionevent.findPointerIndex(ptrID1));
                float f1 = motionevent.getY(motionevent.findPointerIndex(ptrID1));
                float f2 = motionevent.getX(motionevent.findPointerIndex(ptrID2));
                float f3 = motionevent.getY(motionevent.findPointerIndex(ptrID2));
                mAngle = angleBetweenLines(fX, fY, sX, sY, f2, f3, f, f1);
                if (mListener != null)
                {
                    mListener.OnRotation(this);
                }
            }
        }

        ptrID1 = -1;
        ptrID2 = -1;
        return true;

    }
}