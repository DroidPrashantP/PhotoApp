// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.pointlist;

import com.app.camera.R;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package pointlist:
//            Collage1, Collage2, Collage3, Collage4, 
//            Collage5, Collage6, Collage7, Collage8, 
//            Collage9, CollageScrapBook, CollageLayout

public class Collage
{

    public static int collageIconArray[][];
    public static float scrapBookShapeScale = 1.0F;
    public List collageLayoutList;

    public Collage()
    {
    }

    public static Collage CreateCollage(int i, int j, int k, boolean flag)
    {
        if (flag)
        {
            return createScrapLaout(i, j, k);
        }
        if (i == 1)
        {
            return new Collage1(j, k);
        }
        if (i == 2)
        {
            return new Collage2(j, k);
        }
        if (i == 3)
        {
            return new Collage3(j, k);
        }
        if (i == 4)
        {
            return new Collage4(j, k);
        }
        if (i == 5)
        {
            return new Collage5(j, k);
        }
        if (i == 6)
        {
            return new Collage6(j, k);
        }
        if (i == 7)
        {
            return new Collage7(j, k);
        }
        if (i == 8)
        {
            return new Collage8(j, k);
        }
        if (i == 9)
        {
            return new Collage9(j, k);
        } else
        {
            return null;
        }
    }

    public static Collage createScrapLaout(int i, int j, int k)
    {
        Collage collage = new Collage();
        collage.collageLayoutList = new ArrayList();
        CollageScrapBook collagescrapbook = new CollageScrapBook(j, k);
        if (i == 1)
        {
            collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(0));
            scrapBookShapeScale = 0.7F;
        }
        if (i == 2)
        {
            if (k > j)
            {
                collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(2));
            } else
            {
                collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(1));
            }
            scrapBookShapeScale = 1.0F;
        } else
        {
            if (i == 3)
            {
                collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(3));
                scrapBookShapeScale = 0.95F;
                return collage;
            }
            if (i == 4)
            {
                collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(4));
                scrapBookShapeScale = 1.0F;
                return collage;
            }
            if (i == 5)
            {
                collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(5));
                scrapBookShapeScale = 0.95F;
                return collage;
            }
            if (i == 6)
            {
                if (k > j)
                {
                    collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(6));
                } else
                {
                    collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(7));
                }
                scrapBookShapeScale = 1.0F;
                return collage;
            }
            if (i == 7)
            {
                if (k > j)
                {
                    collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(8));
                } else
                {
                    collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(9));
                }
                scrapBookShapeScale = 1.0F;
                return collage;
            }
            if (i == 8)
            {
                collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(10));
                scrapBookShapeScale = 1.0F;
                return collage;
            }
            if (i == 9)
            {
                collage.collageLayoutList.add((CollageLayout)collagescrapbook.collageLayoutList.get(11));
                scrapBookShapeScale = 1.0F;
                return collage;
            }
        }
        return collage;
    }

    static 
    {
        int ai[] = {
            R.drawable.collage_1_0, R.drawable.collage_1_1, R.drawable.collage_1_2, R.drawable.collage_1_3, R.drawable.collage_1_4, R.drawable.collage_1_5, R.drawable.collage_1_6, R.drawable.collage_1_7, R.drawable.collage_1_8, R.drawable.collage_1_9,
            R.drawable.collage_1_10, R.drawable.collage_1_11
        };
        int i = R.drawable.collage_2_0;
        int j = R.drawable.collage_2_1;
        int k = R.drawable.collage_2_2;
        int l = R.drawable.collage_2_3;
        int i1 = R.drawable.collage_2_4;
        int j1 = R.drawable.collage_2_5;
        int k1 = R.drawable.collage_2_6;
        int l1 = R.drawable.collage_2_7;
        int i2 = R.drawable.collage_2_8;
        int j2 = R.drawable.collage_2_9;
        int k2 = R.drawable.collage_2_10;
        int l2 = R.drawable.collage_2_11;
        int i3 = R.drawable.collage_2_12;
        int j3 = R.drawable.collage_2_13;
        int k3 = R.drawable.collage_2_14;
        int l3 = R.drawable.collage_2_15;
        int ai1[] = {
            R.drawable.collage_3_0, R.drawable.collage_3_1, R.drawable.collage_3_2, R.drawable.collage_3_3, R.drawable.collage_3_4, R.drawable.collage_3_5, R.drawable.collage_3_6, R.drawable.collage_3_7, R.drawable.collage_3_8, R.drawable.collage_3_9, 
            R.drawable.collage_3_10, R.drawable.collage_3_11, R.drawable.collage_3_12, R.drawable.collage_3_13, R.drawable.collage_3_14, R.drawable.collage_3_15, R.drawable.collage_3_16, R.drawable.collage_3_17, R.drawable.collage_3_18, R.drawable.collage_3_19, 
            R.drawable.collage_3_20, R.drawable.collage_3_21
        };
        int i4 = R.drawable.collage_4_0;
        int j4 = R.drawable.collage_4_1;
        int k4 = R.drawable.collage_4_2;
        int l4 = R.drawable.collage_4_3;
        int i5 = R.drawable.collage_4_4;
        int j5 = R.drawable.collage_4_5;
        int k5 = R.drawable.collage_4_6;
        int l5 = R.drawable.collage_4_7;
        int i6 = R.drawable.collage_4_8;
        int j6 = R.drawable.collage_4_9;
        int k6 = R.drawable.collage_4_10;
        int l6 = R.drawable.collage_4_11;
        int i7 = R.drawable.collage_4_12;
        int j7 = R.drawable.collage_4_13;
        int k7 = R.drawable.collage_4_14;
        int l7 = R.drawable.collage_4_15;
        int i8 = R.drawable.collage_4_16;
        int j8 = R.drawable.collage_4_17;
        int ai2[] = {
            R.drawable.collage_5_0, R.drawable.collage_5_1, R.drawable.collage_5_2, R.drawable.collage_5_3, R.drawable.collage_5_4, R.drawable.collage_5_5, R.drawable.collage_5_6, R.drawable.collage_5_7, R.drawable.collage_5_8, R.drawable.collage_5_9, 
            R.drawable.collage_5_10, R.drawable.collage_5_11, R.drawable.collage_5_12, R.drawable.collage_5_13, R.drawable.collage_5_14, R.drawable.collage_5_15, R.drawable.collage_5_16, R.drawable.collage_5_17, R.drawable.collage_5_18, R.drawable.collage_5_19, 
            R.drawable.collage_5_20
        };
        int k8 = R.drawable.collage_6_0;
        int l8 = R.drawable.collage_6_1;
        int i9 = R.drawable.collage_6_2;
        int j9 = R.drawable.collage_6_3;
        int k9 = R.drawable.collage_6_4;
        int l9 = R.drawable.collage_6_5;
        int i10 = R.drawable.collage_6_6;
        int j10 = R.drawable.collage_6_7;
        int k10 = R.drawable.collage_6_8;
        int l10 = R.drawable.collage_6_9;
        int i11 = R.drawable.collage_6_10;
        int j11 = R.drawable.collage_6_11;
        int k11 = R.drawable.collage_6_12;
        int ai3[] = {
            R.drawable.collage_7_0, R.drawable.collage_7_1, R.drawable.collage_7_2, R.drawable.collage_7_3, R.drawable.collage_7_4, R.drawable.collage_7_5, R.drawable.collage_7_6, R.drawable.collage_7_7, R.drawable.collage_7_8, R.drawable.collage_7_9
        };
        int ai4[] = {
            R.drawable.collage_8_0, R.drawable.collage_8_1, R.drawable.collage_8_2, R.drawable.collage_8_3, R.drawable.collage_8_4, R.drawable.collage_8_5, R.drawable.collage_8_6, R.drawable.collage_8_7, R.drawable.collage_8_8, R.drawable.collage_8_9, 
            R.drawable.collage_8_10, R.drawable.collage_8_11, R.drawable.collage_8_12, R.drawable.collage_8_13, R.drawable.collage_8_14, R.drawable.collage_8_15
        };
        int l11 = R.drawable.collage_9_0;
        int i12 = R.drawable.collage_9_1;
        int j12 = R.drawable.collage_9_2;
        int k12 = R.drawable.collage_9_3;
        int l12 = R.drawable.collage_9_4;
        int i13 = R.drawable.collage_9_5;
        int j13 = R.drawable.collage_9_6;
        int k13 = R.drawable.collage_9_7;
        int l13 = R.drawable.collage_9_8;
        collageIconArray = (new int[][] {
            ai, new int[] {
                i, j, k, l, i1, j1, k1, l1, i2, j2, 
                k2, l2, i3, j3, k3, l3
            }, ai1, new int[] {
                i4, j4, k4, l4, i5, j5, k5, l5, i6, j6, 
                k6, l6, i7, j7, k7, l7, i8, j8
            }, ai2, new int[] {
                k8, l8, i9, j9, k9, l9, i10, j10, k10, l10, 
                i11, j11, k11
            }, ai3, ai4, new int[] {
                l11, i12, j12, k12, l12, i13, j13, k13, l13
            }
        });
    }
}
