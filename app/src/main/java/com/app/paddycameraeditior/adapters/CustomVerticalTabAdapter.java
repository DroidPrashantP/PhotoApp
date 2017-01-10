package com.app.paddycameraeditior.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.paddycameraeditior.Domain.StickerTab;
import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.activities.StickerActivity;

import java.util.ArrayList;

/**
 * Created by Prashant on 15/12/15.
 */
public class CustomVerticalTabAdapter extends RecyclerView.Adapter<CustomVerticalTabAdapter.TabHolder> {

    private Context mContext;
    private ArrayList<StickerTab> tabArrayList;
    private int[] mImageIdList = new int[]{R.drawable.couple_a, R.drawable.emoji_a, R.drawable.love_one_a, R.drawable.love_two_a};

    public CustomVerticalTabAdapter(Context mContext, ArrayList<StickerTab> tabArrayList) {
        this.mContext = mContext;
        this.tabArrayList = tabArrayList;
    }

    @Override
    public TabHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View rootView = null;
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vertical_tab_layout, parent, false);
        return new TabHolder(rootView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(TabHolder holder, int position) {
        StickerTab storefiltertab = null;
        if (holder!=null){
            storefiltertab = tabArrayList.get(position);
            holder.mTabText.setText(storefiltertab.Title);
            holder.mTabposition = position;
            holder.mTabImage.setImageResource(mImageIdList[position]);
        }

        if (storefiltertab.isSelected == true){
            holder.mTabText.setTextColor(mContext.getResources().getColor(R.color.accent));
            holder.tabWrapper.setBackground(mContext.getResources().getDrawable(R.drawable.border_background));
        }else {
            holder.mTabText.setTextColor(mContext.getResources().getColor(R.color.text_white));
            holder.tabWrapper.setBackground(null);

        }
    }

    @Override
    public int getItemCount() {
        return tabArrayList.size();
    }

    class TabHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mTabImage;
        private TextView mTabText;
        private int mTabposition;
        private RelativeLayout tabWrapper;

        public TabHolder(View itemView) {
            super(itemView);
            mTabImage = (ImageView) itemView.findViewById(R.id.tabImg);
            mTabText = (TextView) itemView.findViewById(R.id.tabText);
            tabWrapper = (RelativeLayout) itemView.findViewById(R.id.tabWrapper);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            StickerTab stickerTab = tabArrayList.get(mTabposition);
            ((StickerActivity)mContext).SelectedTab(mTabposition);

        }
    }
}
