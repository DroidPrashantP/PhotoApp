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
import com.squareup.picasso.Picasso;


public class FramesLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int[] mBackgroundFramesList = {
            R.drawable.effect_0_thumb,
            R.drawable.frame_one,
            R.drawable.frame_two,
            R.drawable.frame_three,
            R.drawable.frame_four,
            R.drawable.frame_five,
            R.drawable.frame_six,
            R.drawable.frame_seven,
            R.drawable.frame_eight
    };

    private static int[] mFxList = {
            R.drawable.effect_0_thumb,
            R.drawable.effect_2_thumb,
            R.drawable.effect_3_thumb,
            R.drawable.effect_4_thumb,
            R.drawable.effect_5_thumb,
            R.drawable.effect_6_thumb,
            R.drawable.effect_7_thumb,
            R.drawable.effect_8_thumb,
            R.drawable.effect_9_thumb,
            R.drawable.effect_10_thumb,
            R.drawable.effect_11_thumb,
            R.drawable.effect_12_thumb,
            R.drawable.effect_13_thumb
    };
    private Context mContext;
    private String Mode;
    private String mFilterType;


    public FramesLayoutAdapter(Context context, String mode, String filterType) {
        this.mContext = context;
        this.Mode = mode;
        this.mFilterType = filterType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View rootView;
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_frame_custom_row, parent, false);
        return new FrameHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FrameHolder) {
            if (mFilterType.equalsIgnoreCase(Constants.FilterFunction.FRAME)) {
                Picasso.with(mContext).load(mBackgroundFramesList[position]).into(((FrameHolder) holder).mFrameImage);
            }else {
                Picasso.with(mContext).load(mFxList[position]).into(((FrameHolder) holder).mFrameImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mFilterType.equalsIgnoreCase(Constants.FilterFunction.FRAME)) {
            return mBackgroundFramesList.length;
        }else {
            return mFxList.length;
        }
    }

    /**
     * class to hold top categories view
     */
    class FrameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mFrameImage;

        public FrameHolder(View itemView) {
            super(itemView);
            mFrameImage = (ImageView) itemView.findViewById(R.id.frameImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
                if (mFilterType.equalsIgnoreCase(Constants.FilterFunction.FRAME)) {
                    ((BlurActivity) mContext).FrameClick(mBackgroundFramesList[getAdapterPosition()], getAdapterPosition());
                }else {
                    ((BlurActivity) mContext).FxClick(mFxList[getAdapterPosition()], getAdapterPosition());
                }
            }else {
                if (mFilterType.equalsIgnoreCase(Constants.FilterFunction.FRAME)) {
                    ((PhotoActivity) mContext).FrameClick(mBackgroundFramesList[getAdapterPosition()], getAdapterPosition());
                }else {
                    ((PhotoActivity) mContext).FxClick(mFxList[getAdapterPosition()], getAdapterPosition());
                }
            }
        }
    }

}
