// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.app.camera.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
public class MyRecylceAdapterBase<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public int getItemCount() {
        return 0;
    }

    public void onBindViewHolder(VH vh, int arg1) {
    }

    public VH onCreateViewHolder(ViewGroup arg0, int arg1) {
        return null;
    }

    public void setSelectedPositinVoid() {
    }
}
