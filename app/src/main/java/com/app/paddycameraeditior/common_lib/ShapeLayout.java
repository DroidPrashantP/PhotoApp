// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.paddycameraeditior.common_lib;


// Referenced classes of package com.lyrebirdstudio.collagelib:
//            Shape

public class ShapeLayout
{

    boolean isScalable;
    int porterDuffClearBorderIntex;
    public Shape shapeArr[];

    public ShapeLayout()
    {
        isScalable = false;
        porterDuffClearBorderIntex = -1;
    }

    public ShapeLayout(Shape ashape[])
    {
        isScalable = false;
        porterDuffClearBorderIntex = -1;
        shapeArr = ashape;
    }

    public int getClearIndex()
    {
        return porterDuffClearBorderIntex;
    }

    public boolean getScalibility()
    {
        return isScalable;
    }

    public void setClearIndex(int i)
    {
        if (i >= 0 && i < shapeArr.length)
        {
            porterDuffClearBorderIntex = i;
        }
    }

    public void setScalibility(boolean flag)
    {
        isScalable = flag;
    }
}
