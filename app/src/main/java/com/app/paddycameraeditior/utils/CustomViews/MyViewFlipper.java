// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.paddycameraeditior.utils.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class MyViewFlipper extends ViewFlipper
{

    public MyViewFlipper(Context context)
    {
        super(context);
    }

    public MyViewFlipper(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    protected void onDetachedFromWindow()
    {
        try
        {
            super.onDetachedFromWindow();
            return;
        }
        catch (IllegalArgumentException illegalargumentexception)
        {
            stopFlipping();
        }
    }
}
