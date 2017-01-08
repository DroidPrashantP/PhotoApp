package com.app.camera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.camera.R;
import com.app.camera.activities.BlurActivity;
import com.app.camera.activities.PhotoActivity;
import com.app.camera.utils.Constants;


public class BackgroundLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int[] mBackgroundColors = {
            R.drawable.effect_0_thumb,
            R.drawable.background_effect_1,
            R.drawable.background_effect_2,
            R.drawable.background_effect_3,
            R.drawable.background_effect_4,
            R.drawable.background_effect_5,
            R.drawable.background_effect_6,
            R.drawable.background_effect_7,
            R.drawable.background_effect_8,
            R.drawable.background_effect_9,
            R.drawable.background_effect_10
    };
    private Context mContext;
    private String mCategoryType;
    private String Mode;

    public BackgroundLayoutAdapter(Context mContext, String mode) {
        this.mContext = mContext;
        this.Mode = mode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View rootView;
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_background_custom_row, parent, false);
        return new FrameHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FrameHolder) {
            FrameHolder frameHolder = (FrameHolder)holder;
            frameHolder.mBackgroundImage.setBackgroundResource(mBackgroundColors[position]);
        }

    }

    @Override
    public int getItemCount() {
        return mBackgroundColors.length;
    }

    /**
     * class to hold top categories view
     */
    class FrameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mBackgroundImage;

        public FrameHolder(View itemView) {
            super(itemView);
            mBackgroundImage = (ImageView) itemView.findViewById(R.id.background_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
                ((BlurActivity) mContext).BackgroundClick(mBackgroundColors[getAdapterPosition()], getAdapterPosition());
            }else {
                ((PhotoActivity) mContext).BackgroundClick(mBackgroundColors[getAdapterPosition()], getAdapterPosition());
            }
        }
    }

}
