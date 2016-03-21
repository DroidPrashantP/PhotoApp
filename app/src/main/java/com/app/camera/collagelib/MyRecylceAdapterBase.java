package com.app.camera.collagelib;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

public class MyRecylceAdapterBase<VH extends ViewHolder> extends Adapter<VH> {
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
