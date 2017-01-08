package com.app.camera.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.camera.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 22/3/16.
 */
public class SquareColorPickerAdapter extends MyRecylceAdapterBase<SquareColorPickerAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "Adapter";
    int colorCount;
    int colorDefault;
    private List<Integer> colorList;
    int colorSelected;
    String[] colors;
    BackgroundPatternAdapter.CurrentSquareIndexChangedListener listener;
    RecyclerView recylceView;
    View selectedListItem;
    int selectedPosition;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        public View colorPickerView;
        private int item;
        BackgroundPatternAdapter.CurrentSquareIndexChangedListener viewHolderListener;

        public void setCurrentCollageIndexChangedListener(BackgroundPatternAdapter.CurrentSquareIndexChangedListener l) {
            this.viewHolderListener = l;
        }

        public void setItem(int items) {
            Log.e("ColorCode", ""+items);
            this.item = items;
            this.colorPickerView.setBackgroundColor(item);
        }

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            this.colorPickerView = itemLayoutView.findViewById(R.id.color_picker_view);
        }
    }

    public SquareColorPickerAdapter(BackgroundPatternAdapter.CurrentSquareIndexChangedListener l, int cDefault, int cSelected) {
        this.colorCount = 29;
        this.colorList = new ArrayList();
//        this.colors = new String[]{"#c41915", "#ca4318", "#ec6c20","#fd8a26", "#9aca28","#c8b654", "#c79c38", "#ca7620","#87af43","#52a526","#66961A", "#13874B",
//                "#20AA66","#66B393", "#1897A6","#6FB2B8","#3cb6e3","#199ACA","#1892e6","#1688cd","#8266C9","#754CB2","#6568C9","#1A56B6", "#A868C9","#754CB2","#9D44B6","#FB454B","#E52464"};
        this.colors = new String[]{"#FFFFFF", "#EFDECD", "#CD4A4A", "#CC6666", "#BC5D58", "#FF5349", "#FD5E53", "#FD7C6E", "#FDBCB4", "#FF6E4A", "#FFA089", "#EA7E5D", "#B4674D", "#A5694F", "#FF7538", "#FF7F49", "#DD9475", "#FF8243", "#FFA474", "#9F8170", "#CD9575", "#EFCDB8", "#D68A59", "#DEAA88", "#FAA76C", "#FFCFAB", "#FFBD88", "#FDD9B5", "#FFA343", "#EFDBC5", "#FFB653", "#E7C697", "#8A795D", "#FAE7B5", "#FFCF48", "#FCD975", "#FDDB6D", "#FCE883", "#F0E891", "#ECEABE", "#BAB86C", "#FDFC74", "#FDFC74", "#FFFF99", "#C5E384", "#B2EC5D", "#87A96B", "#A8E4A0", "#1DF914", "#76FF7A", "#71BC78", "#6DAE81", "#9FE2BF", "#1CAC78", "#30BA8F", "#45CEA2", "#3BB08F", "#1CD3A2", "#17806D", "#158078", "#1FCECB", "#78DBE2", "#77DDE7", "#80DAEB", "#414A4C", "#199EBD", "#1CA9C9", "#1DACD6", "#9ACEEB", "#1A4876", "#1974D2", "#2B6CC4", "#1F75FE", "#C5D0E6", "#B0B7C6", "#5D76CB", "#A2ADD0", "#979AAA", "#ADADD6", "#7366BD", "#7442C8", "#7851A9", "#9D81BA", "#926EAE", "#CDA4DE", "#8F509D", "#C364C5", "#FB7EFD", "#FC74FD", "#8E4585", "#FF1DCE", "#FF1DCE", "#FF48D0", "#E6A8D7", "#C0448F", "#6E5160", "#DD4492", "#FF43A4", "#F664AF", "#FCB4D5", "#FFBCD9", "#F75394", "#FFAACC", "#E3256B", "#FDD7E4", "#CA3767", "#DE5D83", "#FC89AC", "#F780A1", "#C8385A", "#EE204D", "#FF496C", "#EF98AA", "#FC6C85", "#FC2847", "#FF9BAA", "#CB4154", "#EDEDED", "#DBD7D2", "#CDC5C2", "#95918C", "#232323"};

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

    @Override
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
