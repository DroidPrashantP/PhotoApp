package com.app.camera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.camera.R;
import com.app.camera.activities.BlurActivity;
import com.app.camera.utils.Constants;


public class MaskLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int[] mShapeImages = {
            R.drawable.effect_0_thumb,
            R.drawable.shape_heart,
            R.drawable.shape_circle,
            R.drawable.shape_diagonal,
            R.drawable.shape_foot,
            R.drawable.shape_hexagon,
            R.drawable.shape_leg,
            R.drawable.shape_twitter
    };

//    private static int[] mShapeImages = {
//            R.drawable.effect_0_thumb,
//            R.drawable.collage_1_0,
//            R.drawable.collage_1_1,
//            R.drawable.collage_1_2,
//            R.drawable.collage_1_3,
//            R.drawable.collage_1_4,
//            R.drawable.collage_1_5,
//            R.drawable.collage_1_6
//    };

    private Context mContext;
    private String mCategoryType;
    private String Mode;

    public MaskLayoutAdapter(Context mContext, String mode) {
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
            frameHolder.mBackgroundImage.setBackgroundResource(mShapeImages[position]);
        }

    }

    @Override
    public int getItemCount() {
        return mShapeImages.length;
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
                ((BlurActivity) mContext).ShapeClick(mShapeImages[getAdapterPosition()], getAdapterPosition());
            }
        }
    }

}
