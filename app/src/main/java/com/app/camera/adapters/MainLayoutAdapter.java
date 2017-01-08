package com.app.camera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.camera.R;
import com.app.camera.activities.BlurActivity;
import com.app.camera.activities.PhotoActivity;
import com.app.camera.utils.Constants;

/**
 * Created by Prashant on 5/1/16.
 */
public class MainLayoutAdapter extends RecyclerView.Adapter<MainLayoutAdapter.MainLayoutHolder> {

    private static int[] mImageList = {
            R.drawable.tool_48x48,
            R.drawable.blur_48x48,
            R.drawable.crop_48x48,
            R.drawable.background_48x48,
            R.drawable.frame_48x48,
            R.drawable.stickers_48x48,
            R.drawable.filtter_48x48,
            R.drawable.square_fx,
            R.drawable.text_48x48,

    };
    private static int[] mBlurImageList = {
            R.drawable.collage_icon_48x48,
            R.drawable.blur_48x48,
            R.drawable.background_48x48,
            R.drawable.layout_48x48,
            R.drawable.stickers_48x48,
            R.drawable.filtter_48x48,
            R.drawable.square_fx,
            R.drawable.text_48x48

    };
    private static String[] mBtnNames ;
    private Context mContext;
    boolean[] Index = new boolean[]{false,false,false,false,false,false,false,false,false};
    String Mode;

    public MainLayoutAdapter(Context mContext, String mode) {
        this.mContext = mContext;
        this.Mode = mode;
        if (Mode.equalsIgnoreCase(Constants.CurrentFunction.CAMERA) || Mode.equalsIgnoreCase(Constants.CurrentFunction.GALLERY)) {
            mBtnNames = mContext.getResources().getStringArray(R.array.photo_main_layout_array);
        }else if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
            mBtnNames = mContext.getResources().getStringArray(R.array.blur_main_layout_array);
        }

    }

    @Override
    public MainLayoutHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View rootView;
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filter_custom_view, parent, false);
        return new MainLayoutHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MainLayoutHolder holder, int position) {
        if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
            holder.layouName.setText(mBtnNames[position]);
            holder.layoutImage.setImageResource(mBlurImageList[position]);
        }else {
            holder.layouName.setText(mBtnNames[position]);
            holder.layoutImage.setImageResource(mImageList[position]);
        }

        if (Index[position] == true){
            holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.primary_dark));
        }else {
            holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
        }
        holder.selectedPosition = position;
    }

    @Override
    public int getItemCount() {
        if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
           return mBlurImageList.length;
        }
        return mImageList.length;
    }

    /**
     * class to hold top categories view
     */
    class MainLayoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mMainLayout;
        private TextView layouName;
        private ImageView layoutImage;
        private int selectedPosition;

        public MainLayoutHolder(View itemView) {
            super(itemView);
            mMainLayout = (LinearLayout) itemView.findViewById(R.id.layoutWrapper);
            layouName = (TextView) itemView.findViewById(R.id.layoutText);
            layoutImage = (ImageView) itemView.findViewById(R.id.layoutImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                for (int i =0 ; i< Index.length;i++){
                    if (selectedPosition == i){
                        Index[i] = true;
                    }else {
                        Index[i] = false;
                    }
                }
                notifyDataSetChanged();
                if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
                    ((BlurActivity) mContext).MainLayoutContainer(selectedPosition);
                }else {
                    ((PhotoActivity) mContext).MainLayoutContainer(selectedPosition);

                }
            }
        }
    }
}