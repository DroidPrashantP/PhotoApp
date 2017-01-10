package com.app.paddycameraeditior.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.paddycameraeditior.R;
import com.app.paddycameraeditior.activities.BlurActivity;
import com.app.paddycameraeditior.activities.PhotoActivity;
import com.app.paddycameraeditior.utils.Constants;

/**
 * Created by Prashant on 6/1/16.
 */
public class FilterLayoutAdapter extends RecyclerView.Adapter<FilterLayoutAdapter.MainLayoutHolder> {

    private static int[] mImageList = {
            R.drawable.brightness_48x48,
            R.drawable.contrast_48x48,
            R.drawable.saturation_48x48,
            R.drawable.filter_tint_48x48,
            R.drawable.blur_48x48

    };

    private static int[] mBlurImageList = {
            R.drawable.brightness_48x48,
            R.drawable.contrast_48x48,
            R.drawable.saturation_48x48,
            R.drawable.filter_tint_48x48,
            R.drawable.blur_48x48,
            R.drawable.frame_48x48

    };
    private static String[] mBtnNames = {"Brightness","Contrast", "Saturation", "Tine", "Blur", "Frame"};

    private Context mContext;
    String Mode;
    boolean[] Index = new boolean[]{false, false, false, false, false, false};
//    boolean[] BlurIndex = new boolean[]{false, false, false, false, false, false, false, false, false, false, false};
    boolean[] BlurIndex = new boolean[]{false, false, false, false, false,false};

    public FilterLayoutAdapter(Context mContext, String mode) {
        this.mContext = mContext;
        this.Mode = mode;
    }

    @Override
    public MainLayoutHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View rootView;
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tool_custom_view, parent, false);
        return new MainLayoutHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MainLayoutHolder holder, int position) {
        if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
            holder.layouName.setText(mBtnNames[position]);
            holder.layoutImage.setImageResource(mBlurImageList[position]);
            holder.layoutImage.setColorFilter(Color.argb(255, 255, 255, 255));
            if (BlurIndex[position] == true) {
                holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.primary_dark));
            } else {
                holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
            }
        }else {
                holder.layouName.setText(mBtnNames[position]);
                holder.layoutImage.setImageResource(mImageList[position]);
                holder.layoutImage.setColorFilter(Color.argb(255, 255, 255, 255));
            if (Index[position] == true) {
                holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.primary_dark));
            } else {
                holder.mMainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
            }
        }
        holder.selectedPosition = position;
    }

    @Override
    public int getItemCount() {
        if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
            return mBlurImageList.length;
        }else {
            return mImageList.length;
        }

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
                for (int i = 0; i < Index.length; i++) {
                    if (selectedPosition == i) {
                        Index[i] = true;
                    } else {
                        Index[i] = false;
                    }
                }
                notifyDataSetChanged();
                if (Mode.equalsIgnoreCase(Constants.CurrentFunction.BLUR)) {
                    ((BlurActivity) mContext).FilterSelection(selectedPosition);
                }
                else {
                    ((PhotoActivity) mContext).FilterSelection(selectedPosition);
                }
            }
        }
    }
}