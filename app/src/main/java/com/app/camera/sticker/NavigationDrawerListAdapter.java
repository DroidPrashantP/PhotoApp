package com.app.camera.sticker;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.camera.R;

public class NavigationDrawerListAdapter extends BaseAdapter {
    Context context;
    int[] imageResList;
    String[] nameList;

    static class ViewHolderItem {
        ImageView imageViewItem;
        TextView textViewItem;

        ViewHolderItem() {
        }
    }

    public NavigationDrawerListAdapter(Context context) {
        this.imageResList = new int[]{R.drawable.emoji_a, R.drawable.emoji_b, R.drawable.emoji_c, R.drawable.emoji_d};
        this.nameList = new String[]{"Emoji 1", "Emoji 2", "Emoji 3", "Love"};
        this.context = context;
    }

    public int getCount() {
        return this.imageResList.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = ((Activity) this.context).getLayoutInflater().inflate(R.layout.sticker_nav_list_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.nav_list_text);
            viewHolder.imageViewItem = (ImageView) convertView.findViewById(R.id.nav_list_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        viewHolder.textViewItem.setText(this.nameList[position]);
        viewHolder.imageViewItem.setImageResource(this.imageResList[position]);
        return convertView;
    }
}
