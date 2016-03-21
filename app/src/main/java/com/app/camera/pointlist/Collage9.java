// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.pointlist;

import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package pointlist:
//            Collage, CollageLayout

public class Collage9 extends Collage
{

    public static int shapeCount = 9;

    public Collage9(int i, int j)
    {
        collageLayoutList = new ArrayList();
        PointF apointf[] = new PointF[4];
        Object obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j), new PointF(0.75F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.25F * (float)i, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF(0.25F * (float)i, 0.75F * (float)j), new PointF(0.25F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, (float)j * 1.0F), new PointF(0.25F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.75F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j), new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.25F * (float)i, 0.75F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, (float)j * 0.3333333F), new PointF((float)i * 0.6666667F, (float)j * 0.3333333F), new PointF((float)i * 0.6666667F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF((float)i * 0.6666667F, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.6666667F), new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF((float)i * 0.3333333F, (float)j * 0.3333333F), new PointF((float)i * 0.3333333F, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.6666667F), new PointF((float)i * 0.3333333F, (float)j * 0.6666667F), new PointF((float)i * 0.3333333F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, (float)j * 0.6666667F), new PointF((float)i * 0.6666667F, (float)j * 0.6666667F), new PointF((float)i * 0.6666667F, (float)j * 1.0F), new PointF((float)i * 0.3333333F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 0.6666667F), new PointF((float)i * 1.0F, (float)j * 0.6666667F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 0.6666667F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 0.6666667F), new PointF((float)i * 0.6666667F, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 0.6666667F), new PointF((float)i * 0.6666667F, (float)j * 0.3333333F), new PointF((float)i * 0.3333333F, (float)j * 0.3333333F), new PointF((float)i * 0.3333333F, (float)j * 0.6666667F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.5F * (float)i, (float)j * 0.0F), new PointF(0.5F * (float)i, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF(0.5F * (float)i, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF(0.5F * (float)i, 0.25F * (float)j), new PointF(0.5F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, 0.5F * (float)j), new PointF(0.5F * (float)i, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF(0.5F * (float)i, 0.5F * (float)j), new PointF(0.5F * (float)i, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, 0.75F * (float)j), new PointF(0.5F * (float)i, 0.5F * (float)j), new PointF((float)i * 1.0F, 0.5F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF((float)i * 0.3333333F, 0.75F * (float)j), new PointF((float)i * 0.3333333F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, (float)j * 1.0F), new PointF((float)i * 0.6666667F, (float)j * 1.0F), new PointF((float)i * 0.6666667F, 0.75F * (float)j), new PointF((float)i * 0.3333333F, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 1.0F), new PointF((float)i * 0.6666667F, 0.75F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.375F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, 0.375F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF((float)i * 0.0F, 0.375F * (float)j), new PointF(0.25F * (float)i, 0.375F * (float)j), new PointF(0.25F * (float)i, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.75F * (float)j), new PointF(0.375F * (float)i, 0.75F * (float)j), new PointF(0.375F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.625F * (float)i, 0.25F * (float)j), new PointF(0.625F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.625F * (float)i, 0.25F * (float)j), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF(0.625F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.375F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, (float)j * 1.0F), new PointF(0.375F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, 0.75F * (float)j), new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j), new PointF(0.75F * (float)i, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j), new PointF(0.75F * (float)i, 0.625F * (float)j), new PointF((float)i * 1.0F, 0.625F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.625F * (float)j), new PointF(0.75F * (float)i, 0.625F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF(0.25F * (float)i, (float)j * 0.3333333F), new PointF(0.25F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.6666667F), new PointF(0.25F * (float)i, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 0.6666667F), new PointF(0.5F * (float)i, (float)j * 0.3333333F), new PointF(0.75F * (float)i, (float)j * 0.3333333F), new PointF(0.75F * (float)i, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, (float)j * 0.6666667F), new PointF(0.75F * (float)i, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, (float)j * 1.0F), new PointF(0.5F * (float)i, (float)j * 1.0F), new PointF(0.5F * (float)i, (float)j * 0.6666667F), new PointF(0.25F * (float)i, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 1.0F), new PointF(0.75F * (float)i, (float)j * 1.0F), new PointF(0.75F * (float)i, (float)j * 0.6666667F), new PointF(0.5F * (float)i, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.6666667F), new PointF(0.75F * (float)i, (float)j * 0.6666667F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, (float)j * 0.0F), new PointF((float)i * 0.3333333F, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, (float)j * 0.0F), new PointF((float)i * 0.6666667F, (float)j * 0.0F), new PointF((float)i * 0.6666667F, 0.25F * (float)j), new PointF((float)i * 0.3333333F, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, 0.25F * (float)j), new PointF((float)i * 0.6666667F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF((float)i * 0.0F, 0.25F * (float)j), new PointF((float)i * 0.3333333F, 0.25F * (float)j), new PointF((float)i * 0.3333333F, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, 0.5F * (float)j), new PointF((float)i * 0.6666667F, 0.5F * (float)j), new PointF((float)i * 0.6666667F, 0.25F * (float)j), new PointF((float)i * 0.3333333F, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, 0.5F * (float)j), new PointF((float)i * 1.0F, 0.5F * (float)j), new PointF((float)i * 1.0F, 0.25F * (float)j), new PointF((float)i * 0.6666667F, 0.25F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.5F * (float)j), new PointF((float)i * 0.6666667F, 0.5F * (float)j), new PointF((float)i * 0.6666667F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 1.0F, 0.5F * (float)j), new PointF((float)i * 0.6666667F, 0.5F * (float)j), new PointF((float)i * 0.6666667F, 0.75F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 1.0F), new PointF((float)i * 0.6666667F, 0.75F * (float)j), new PointF((float)i * 1.0F, 0.75F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF(0.2F * (float)i, (float)j * 1.0F), new PointF(0.2F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.4F * (float)i, (float)j * 0.0F), new PointF(0.2F * (float)i, (float)j * 0.0F), new PointF(0.2F * (float)i, 0.5F * (float)j), new PointF(0.4F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6F * (float)i, (float)j * 0.0F), new PointF(0.4F * (float)i, (float)j * 0.0F), new PointF(0.4F * (float)i, 0.5F * (float)j), new PointF(0.6F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.8F * (float)i, (float)j * 0.0F), new PointF(0.6F * (float)i, (float)j * 0.0F), new PointF(0.6F * (float)i, 0.5F * (float)j), new PointF(0.8F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.8F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.5F * (float)j), new PointF(0.8F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, (float)j * 1.0F), new PointF(0.4F * (float)i, (float)j * 1.0F), new PointF(0.4F * (float)i, 0.5F * (float)j), new PointF(0.2F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.4F * (float)i, (float)j * 1.0F), new PointF(0.6F * (float)i, (float)j * 1.0F), new PointF(0.6F * (float)i, 0.5F * (float)j), new PointF(0.4F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6F * (float)i, (float)j * 1.0F), new PointF(0.8F * (float)i, (float)j * 1.0F), new PointF(0.8F * (float)i, 0.5F * (float)j), new PointF(0.6F * (float)i, 0.5F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.8F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.5F * (float)j), new PointF(0.8F * (float)i, 0.5F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 0.3333333F), new PointF(0.75F * (float)i, (float)j * 0.3333333F), new PointF(0.75F * (float)i, (float)j * 0.0F), new PointF(0.5F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF(0.75F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.6666667F), new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 0.6666667F), new PointF((float)i * 1.0F, (float)j * 0.6666667F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.6666667F), new PointF((float)i * 0.3333333F, (float)j * 0.6666667F), new PointF((float)i * 0.3333333F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, (float)j * 1.0F), new PointF((float)i * 0.6666667F, (float)j * 1.0F), new PointF((float)i * 0.6666667F, (float)j * 0.6666667F), new PointF((float)i * 0.3333333F, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.6666667F), new PointF((float)i * 0.6666667F, (float)j * 0.6666667F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.3333333F), new PointF(0.5F * (float)i, (float)j * 0.0F), new PointF(0.25F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 0.3333333F), new PointF(0.75F * (float)i, (float)j * 0.3333333F), new PointF(0.75F * (float)i, (float)j * 0.0F), new PointF(0.5F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.75F * (float)i, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF(0.75F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.6666667F), new PointF((float)i * 0.0F, (float)j * 0.3333333F), new PointF((float)i * 0.3333333F, (float)j * 0.3333333F), new PointF((float)i * 0.3333333F, (float)j * 0.6666667F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.3333333F, (float)j * 0.6666667F), new PointF((float)i * 0.6666667F, (float)j * 0.6666667F), new PointF((float)i * 0.6666667F, (float)j * 0.3333333F), new PointF((float)i * 0.3333333F, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.6666667F, (float)j * 0.6666667F), new PointF((float)i * 1.0F, (float)j * 0.6666667F), new PointF((float)i * 1.0F, (float)j * 0.3333333F), new PointF((float)i * 0.6666667F, (float)j * 0.3333333F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.6666667F), new PointF(0.5F * (float)i, (float)j * 0.6666667F), new PointF(0.5F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.5F * (float)i, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.6666667F), new PointF(0.5F * (float)i, (float)j * 0.6666667F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
    }

}
