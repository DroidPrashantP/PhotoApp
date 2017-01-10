package com.app.paddycameraeditior.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.paddycameraeditior.Domain.Sticker;
import com.app.paddycameraeditior.R;

import java.util.ArrayList;

/**
 * Created by Prashant on 15/12/15.
 */
public class StickerCustomAdapter extends RecyclerView.Adapter<StickerCustomAdapter.StickerHolder> {

    private Context mContext;
    private ArrayList<Sticker> mStickerList;
    private StickerFilterListeners StickerFilterListeners;

    public StickerCustomAdapter(Context mContext, StickerFilterListeners StickerFilterListeners, ArrayList<Sticker> Stickerlist) {
        this.mContext = mContext;
        this.StickerFilterListeners = StickerFilterListeners;
        this.mStickerList = Stickerlist;

    }

    @Override
    public StickerHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_sticker_layout, parent, false);

        return new StickerHolder(rootView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(StickerHolder holder, int position) {
        Sticker sticker = mStickerList.get(position);

        if (sticker.isSelected == true) {
            holder.mStickerWrapper.setBackground(mContext.getResources().getDrawable(R.drawable.border_background));
        } else {
            holder.mStickerWrapper.setBackground(null);

        }
        holder.selectedPosition = position;
        holder.mStickerImageView.setImageResource(sticker.id);
    }

    @Override
    public int getItemCount() {
        return mStickerList.size();
    }

    public class StickerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout mStickerWrapper;
        private ImageView mStickerImageView;
        private int selectedPosition;

        public StickerHolder(View itemView) {
            super(itemView);
            mStickerWrapper = (RelativeLayout) itemView.findViewById(R.id.stickerWrapper);
            mStickerImageView= (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                Sticker tab = mStickerList.get(selectedPosition);
                if (tab.isSelected == true) {
                    tab.isSelected = false;
                    StickerFilterListeners.RemoveSticker(tab.id);
                    notifyDataSetChanged();
                } else {
                    tab.isSelected = true;
                    StickerFilterListeners.AddSticker(tab.id);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public interface StickerFilterListeners {
        void AddSticker(int id);

        void RemoveSticker(int id);
    }

}




