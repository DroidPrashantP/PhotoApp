// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.paddycameraeditior.pointlist;

import android.graphics.PointF;

import com.app.paddycameraeditior.R;

import java.util.ArrayList;
import java.util.List;


public class Collage2 extends Collage
{

    public static int shapeCount = 2;

    public Collage2(int i, int j)
    {
        collageLayoutList = new ArrayList();
        Object obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.5F * (float)i, (float)j * 1.0F), new PointF(0.5F * (float)i, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.5F * (float)i, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF((float)i * 1.0F, 0.5F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.5F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.6F * (float)i, (float)j * 1.0F), new PointF(0.6F * (float)i, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.4F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.4F * (float)i, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(0, R.drawable.mask_heart));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(1, R.drawable.mask_heart));
        ((CollageLayout) (obj)).setClearIndex(1);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.13F * (float)i, 0.13F * (float)j), new PointF(0.13F * (float)i, 0.87F * (float)j), new PointF(0.87F * (float)i, 0.87F * (float)j), new PointF(0.87F * (float)i, 0.13F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(1, R.drawable.mask_heart));
        ((CollageLayout) (obj)).setClearIndex(1);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.3333333F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.13F * (float)i, 0.13F * (float)j), new PointF(0.13F * (float)i, 0.87F * (float)j), new PointF(0.87F * (float)i, 0.87F * (float)j), new PointF(0.87F * (float)i, 0.13F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(1, R.drawable.mask_cloud));
        ((CollageLayout) (obj)).setClearIndex(1);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.5833F * (float)j), new PointF(0.1667F * (float)i, 0.41667F * (float)j), new PointF(0.333F * (float)i, 0.5833F * (float)j), new PointF(0.5F * (float)i, 0.41667F * (float)j), new PointF(0.6667F * (float)i, 0.5833F * (float)j), new PointF(0.8333F * (float)i, 0.41667F * (float)j), new PointF(i * 1, 0.5833F * (float)j), new PointF(i * 1, j * 1)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(i * 0, 0.5833F * (float)j), new PointF(i * 0, j * 0), new PointF(i * 1, j * 0), new PointF(i * 1, 0.5833F * (float)j), new PointF(0.8333F * (float)i, 0.41667F * (float)j), new PointF(0.6667F * (float)i, 0.5833F * (float)j), new PointF(0.5F * (float)i, 0.41667F * (float)j), new PointF(0.333F * (float)i, 0.5833F * (float)j), new PointF(0.1667F * (float)i, 0.41667F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.13F * (float)i, 0.13F * (float)j), new PointF(0.13F * (float)i, 0.87F * (float)j), new PointF(0.87F * (float)i, 0.87F * (float)j), new PointF(0.87F * (float)i, 0.13F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(1, R.drawable.mask_hexagon));
        ((CollageLayout) (obj)).setClearIndex(1);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.3333333F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.6666667F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.6666667F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6F * (float)i, 0.6F * (float)j), new PointF(0.6F * (float)i, 0.9333333F * (float)j), new PointF(0.9333333F * (float)i, 0.9333333F * (float)j), new PointF(0.9333333F * (float)i, 0.6F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).setClearIndex(1);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
    }

}
