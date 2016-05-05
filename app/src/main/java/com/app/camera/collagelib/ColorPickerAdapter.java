package com.app.camera.collagelib;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.app.camera.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({"InflateParams"})
public class ColorPickerAdapter extends MyRecylceAdapterBase<ColorPickerAdapter.ViewHolder> implements OnClickListener {
    private static final String TAG = "Adapter";
    int colorCount;
    int colorDefault;
    private List<Integer> colorList;
    int colorSelected;
    String[] colors;
    MyAdapter.CurrentCollageIndexChangedListener listener;
    RecyclerView recylceView;
    View selectedListItem;
    int selectedPosition;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        public View colorPickerView;
        private int item;
        MyAdapter.CurrentCollageIndexChangedListener viewHolderListener;

        public void setCurrentCollageIndexChangedListener(MyAdapter.CurrentCollageIndexChangedListener l) {
            this.viewHolderListener = l;
        }

        public void setItem(int items) {
            this.item = items;
            this.colorPickerView.setBackgroundColor(this.item);
        }

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            this.colorPickerView = itemLayoutView.findViewById(R.id.color_picker_view);
        }
    }

    public ColorPickerAdapter(MyAdapter.CurrentCollageIndexChangedListener l, int cDefault, int cSelected) {
        this.colorCount = 29;
        this.colorList = new ArrayList();
        this.colors = new String[]{"#c41915", "#ca4318", "#ec6c20","#fd8a26", "#9aca28","#c8b654", "#c79c38", "#ca7620","#87af43","#52a526","#66961A", "#13874B",
                "#20AA66","#66B393", "#1897A6","#6FB2B8","#3cb6e3","#199ACA","#1892e6","#1688cd","#8266C9","#754CB2","#6568C9","#1A56B6", "#A868C9","#754CB2","#9D44B6","#FB454B","#E52464"};
        this.listener = l;
        this.colorDefault = cDefault;
        this.colorSelected = cSelected;
        createColorList();
    }

    private void createColorList() {
        for (String parseColor : this.colors) {
            this.colorList.add(Integer.valueOf(Color.parseColor(parseColor)));
        }
    }

    public void setSelectedPositinVoid() {
        this.selectedListItem = null;
        this.selectedPosition = -1;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_recycler_view_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        viewHolder.setCurrentCollageIndexChangedListener(this.listener);
        itemLayoutView.setOnClickListener(this);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setItem(((Integer) this.colorList.get(position)).intValue());
        if (this.selectedPosition == position) {
            Log.e("ColorAdapter", "Selected Color");
            viewHolder.itemView.setBackgroundColor(this.colorSelected);
        } else {
            Log.e("ColorAdapter", "Default Color");
            viewHolder.itemView.setBackgroundColor(this.colorDefault);
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recylceView) {
        this.recylceView = recylceView;
    }

    public void onClick(View view) {
        int position = this.recylceView.getChildPosition(view);
        RecyclerView.ViewHolder oldViewHolder = this.recylceView.findViewHolderForPosition(this.selectedPosition);
        if (oldViewHolder != null) {
            View oldView = oldViewHolder.itemView;
            if (oldView != null) {
                oldView.setBackgroundColor(this.colorDefault);
            }
        }
        if (this.selectedListItem != null) {
            Log.d(TAG, "selectedListItem " + position);
        }
        Log.d(TAG, "onClick " + position);
        this.listener.onIndexChanged(((Integer) this.colorList.get(position)).intValue());
        this.selectedPosition = position;
        view.setBackgroundColor(this.colorSelected);
        this.selectedListItem = view;
    }

    public int getItemCount() {
        return this.colorList.size();
    }
}
