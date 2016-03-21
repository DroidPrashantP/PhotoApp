// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.pointlist;

import android.graphics.PointF;

import com.app.camera.R;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package pointlist:
//            Collage, CollageLayout, MaskPair

public class Collage3 extends Collage
{

    public static int shapeCount = 3;

    public Collage3(int i, int j)
    {
        collageLayoutList = new ArrayList();
        PointF apointf[] = new PointF[4];
        Object obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, 0.2F * (float)j), new PointF(0.2F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.2F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(2, R.drawable.mask_circle));
        ((CollageLayout) (obj)).setClearIndex(2);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF(0.58F * (float)i, (float)j * 0.5F), new PointF(0.58F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.58F * (float)i, (float)j * 1.0F), new PointF(0.58F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.42F * (float)i, 0.25F * (float)j), new PointF(0.42F * (float)i, 0.75F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j), new PointF((float)i * 1.0F, 0.25F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(0, R.drawable.mask_hexagon));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(1, R.drawable.mask_hexagon));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(2, R.drawable.mask_hexagon));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, (float)j * 0.5F), new PointF((float)i * 0.5F, 0.75F * (float)j), new PointF(0.75F * (float)i, (float)j * 0.5F), new PointF((float)i * 0.5F, 0.25F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).setClearIndex(2);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, 0.2F * (float)j), new PointF(0.2F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.2F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(2, R.drawable.mask_heart));
        ((CollageLayout) (obj)).setClearIndex(2);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, 0.2F * (float)j), new PointF(0.2F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.2F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(2, R.drawable.mask_hexagon));
        ((CollageLayout) (obj)).setClearIndex(2);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, 0.2F * (float)j), new PointF(0.2F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.8F * (float)j), new PointF(0.8F * (float)i, 0.2F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).maskPairList.add(new MaskPair(2, R.drawable.mask_circle));
        ((CollageLayout) (obj)).setClearIndex(2);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, 0.25F * (float)j), new PointF(0.2F * (float)i, 0.75F * (float)j), new PointF(0.8F * (float)i, 0.75F * (float)j), new PointF(0.8F * (float)i, 0.25F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).setClearIndex(2);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, 0.25F * (float)j), new PointF(0.2F * (float)i, 0.75F * (float)j), new PointF(0.8F * (float)i, 0.75F * (float)j), new PointF(0.8F * (float)i, 0.25F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).setClearIndex(2);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 0.5F), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 0.5F, 0.3333333F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 0.5F, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 0.5F, 0.3333333F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
    }

}
