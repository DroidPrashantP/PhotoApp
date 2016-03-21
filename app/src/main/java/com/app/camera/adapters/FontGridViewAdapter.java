package com.app.camera.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.camera.R;

import java.util.Locale;

/**
 * Created by Prashant on 17/1/16.
 */
public class FontGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private  AssetManager assetManager;
    private String[] fontArray = {"arabicsans.ttf","arabictwo.ttf","arabicnaskh_ssk.ttf","arabickufi_ssk.ttf","arabic.ttf","droidserif.ttf", "droidsans_bold.ttf","droidsans.ttf", "droidserif_bold.ttf","droidserif_bold_Italic.ttf", "droidserif_Italic.ttf"};

    public FontGridViewAdapter(Context context) {
        this.mContext = context;
        assetManager = context.getApplicationContext().getAssets();
    }

    @Override
    public int getCount() {
        return fontArray.length;
    }

    @Override
    public Object getItem(int position) {
        return fontArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Typeface typeface = Typeface.createFromAsset(assetManager,
                String.format(Locale.US, "fonts/%s", fontArray[position]));

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.font_item_view, null);
            TextView textView = (TextView) grid.findViewById(R.id.fontTextView);
            textView.setText("ABC");
            textView.setTypeface(typeface);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
