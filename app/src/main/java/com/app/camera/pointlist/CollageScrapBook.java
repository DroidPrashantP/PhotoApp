// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.pointlist;

import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package pointlist:
//            Collage, CollageLayout

public class CollageScrapBook extends Collage
{

    public CollageScrapBook(int i, int j)
    {
        collageLayoutList = new ArrayList();
        Object obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 0.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF(0.2F * (float)i, 0.6F * (float)j), new PointF(0.8F * (float)i, 0.6F * (float)j), new PointF(0.8F * (float)i, (float)j * 0.0F), new PointF(0.2F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, 0.4F * (float)j), new PointF((float)i * 0.0F, 0.4F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.4F * (float)j), new PointF((float)i * 0.5F, 0.4F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.0F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 0.5F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.25F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        ((CollageLayout) (obj)).setClearIndex(4);
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.5F, 0.3333333F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, 0.3333333F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF(0.6666667F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, (float)j * 1.0F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.5F, 0.3333333F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.5F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 0.5F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 0.5F, (float)j * 0.0F), new PointF((float)i * 0.5F, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.25F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 0.5F), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F), new PointF(0.3333333F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF(0.6666667F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 1.0F, (float)j * 0.5F), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 0.5F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.25F * (float)i, 0.25F * (float)j), new PointF(0.25F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.75F * (float)j), new PointF(0.75F * (float)i, 0.25F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.4F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.4F * (float)i, (float)j * 0.0F), new PointF(0.4F * (float)i, 0.4F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3F * (float)i, 0.45F * (float)j), new PointF(0.7F * (float)i, 0.45F * (float)j), new PointF(0.7F * (float)i, (float)j * 0.0F), new PointF(0.3F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.4F * (float)j), new PointF(0.6F * (float)i, 0.4F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.7F * (float)j), new PointF((float)i * 0.0F, 0.3F * (float)j), new PointF(0.45F * (float)i, 0.3F * (float)j), new PointF(0.45F * (float)i, 0.7F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.6F * (float)j), new PointF(0.4F * (float)i, 0.6F * (float)j), new PointF(0.4F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3F * (float)i, 0.55F * (float)j), new PointF(0.7F * (float)i, 0.55F * (float)j), new PointF(0.7F * (float)i, (float)j * 1.0F), new PointF(0.3F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6F * (float)i, 0.6F * (float)j), new PointF((float)i * 1.0F, 0.6F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.6F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.55F * (float)i, 0.7F * (float)j), new PointF(0.55F * (float)i, 0.3F * (float)j), new PointF((float)i * 1.0F, 0.3F * (float)j), new PointF((float)i * 1.0F, 0.7F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
        obj = new ArrayList();
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF((float)i * 0.0F, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, 0.3333333F * (float)j), new PointF(0.6666667F * (float)i, 0.3333333F * (float)j), new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF(0.3333333F * (float)i, (float)j * 0.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, (float)j * 0.0F), new PointF((float)i * 1.0F, (float)j * 0.0F), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF(0.6666667F * (float)i, 0.3333333F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF((float)i * 0.0F, 0.3333333F * (float)j), new PointF(0.3333333F * (float)i, 0.3333333F * (float)j), new PointF(0.3333333F * (float)i, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF((float)i * 0.0F, (float)j * 1.0F), new PointF((float)i * 0.0F, 0.6666667F * (float)j), new PointF(0.3333333F * (float)i, 0.6666667F * (float)j), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.3333333F * (float)i, 0.6666667F * (float)j), new PointF(0.6666667F * (float)i, 0.6666667F * (float)j), new PointF(0.6666667F * (float)i, (float)j * 1.0F), new PointF(0.3333333F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, 0.6666667F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j), new PointF((float)i * 1.0F, (float)j * 1.0F), new PointF(0.6666667F * (float)i, (float)j * 1.0F)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, 0.6666667F * (float)j), new PointF(0.6666667F * (float)i, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.3333333F * (float)j), new PointF((float)i * 1.0F, 0.6666667F * (float)j)
        });
        ((List) (obj)).add(new PointF[] {
            new PointF(0.6666667F * (float)i, 0.6666667F * (float)j), new PointF(0.6666667F * (float)i, 0.3333333F * (float)j), new PointF(0.3333333F * (float)i, 0.3333333F * (float)j), new PointF(0.3333333F * (float)i, 0.6666667F * (float)j)
        });
        obj = new CollageLayout(((List) (obj)));
        collageLayoutList.add(obj);
    }
}
