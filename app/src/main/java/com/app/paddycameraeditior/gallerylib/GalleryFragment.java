package com.app.paddycameraeditior.gallerylib;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.support.v7.appcompat.BuildConfig;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.paddycameraeditior.R;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment implements OnItemClickListener {
    public static final int COLLAGE_IMAGE_LIMIT = 9;
    private static final String TAG = "GalleryActivity";
    Activity activity;
    AdView adWhirlLayout;
    MyGridAdapter adapter;
    List<Album> albumList;
    Button buttonFooterCounter;
    boolean collageSingleMode;
    Context context;
    LinearLayout footer;
    GalleryListener galleryListener;
    GridView gridView;
    TextView headerText;
    boolean isOnBucket;
    public boolean isScrapBook;
    OnClickListener onClickListener;
    int selectedBucketId;
    List<Long> selectedImageIdList;
    List<Integer> selectedImageOrientationList;

    class C05811 implements OnClickListener {
        C05811() {
        }

        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.textView_header) {
                GalleryFragment.this.backtrace();
            }
            if (id == R.id.imageView_delete) {
                View parent = (View) view.getParent();
                if (parent != null && parent.getParent() != null) {
                    int location = ((ViewGroup) parent.getParent()).indexOfChild(parent);
                    GalleryFragment.this.footer.removeView(parent);
                    GalleryFragment.this.buttonFooterCounter.setText(" "+footer.getChildCount());
                    long imageid = ((Long) GalleryFragment.this.selectedImageIdList.remove(location)).longValue();
                    GalleryFragment.this.selectedImageOrientationList.remove(location);
                    Point index = GalleryFragment.this.findItemById(imageid);
                    if (index != null) {
                        GridViewItem gridViewItem = (GridViewItem) ((Album) GalleryFragment.this.albumList.get(index.x)).gridItems.get(index.y);
                        gridViewItem.selectedItemCount--;
                        int value = ((GridViewItem) ((Album) GalleryFragment.this.albumList.get(index.x)).gridItems.get(index.y)).selectedItemCount;
                        if (((Album) GalleryFragment.this.albumList.get(index.x)).gridItems == GalleryFragment.this.adapter.items && GalleryFragment.this.gridView.getFirstVisiblePosition() <= index.y && index.y <= GalleryFragment.this.gridView.getLastVisiblePosition() && GalleryFragment.this.gridView.getChildAt(index.y) != null) {
                            TextView text = (TextView) GalleryFragment.this.gridView.getChildAt(index.y).findViewById(R.id.textViewSelectedItemCount);
                            text.setText(""+value);
                            if (value <= 0 && text.getVisibility() == View.VISIBLE) {
                                text.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
            if (id == R.id.button_footer_count) {
                GalleryFragment.this.photosSelectFinished();
            }
        }
    }

    public interface GalleryListener {
        void onGalleryCancel();

        void onGalleryOkImageArray(long[] jArr, int[] iArr, boolean z);

        void onGalleryOkImageArrayRemoveFragment(long[] jArr, int[] iArr, boolean z);

        void onGalleryOkSingleImage(long j, int i, boolean z);
    }

    public GalleryFragment() {
        this.isOnBucket = true;
        this.isScrapBook = false;
        this.collageSingleMode = false;
        this.selectedImageIdList = new ArrayList();
        this.selectedImageOrientationList = new ArrayList();
        this.onClickListener = new C05811();
    }

    public void setGalleryListener(GalleryListener l) {
        this.galleryListener = l;
    }

    public void setCollageSingleMode(boolean mode) {
        this.collageSingleMode = mode;
        if (mode) {
            if (this.selectedImageIdList != null) {
                for (int i = this.selectedImageIdList.size() - 1; i >= 0; i--) {
                    Point index = findItemById(((Long) this.selectedImageIdList.remove(i)).longValue());
                    if (index != null) {
                        GridViewItem gridViewItem = (GridViewItem) ((Album) this.albumList.get(index.x)).gridItems.get(index.y);
                        gridViewItem.selectedItemCount--;
                        int value = ((GridViewItem) ((Album) this.albumList.get(index.x)).gridItems.get(index.y)).selectedItemCount;
                        if (((Album) this.albumList.get(index.x)).gridItems == this.adapter.items && this.gridView.getFirstVisiblePosition() <= index.y && index.y <= this.gridView.getLastVisiblePosition() && this.gridView.getChildAt(index.y) != null) {
                            TextView text = (TextView) this.gridView.getChildAt(index.y).findViewById(R.id.textViewSelectedItemCount);
                            text.setText(""+value);
                            if (value <= 0 && text.getVisibility() == View.VISIBLE) {
                                text.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            }
            if (this.selectedImageOrientationList != null) {
                this.selectedImageOrientationList.clear();
            }
            if (this.footer != null) {
                this.footer.removeAllViews();
            }
            if (this.buttonFooterCounter != null) {
                this.buttonFooterCounter.setText(""+0);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_gallery, container, false);
        this.footer = (LinearLayout) fragmentView.findViewById(R.id.selected_image_linear);
        this.headerText = (TextView) fragmentView.findViewById(R.id.textView_header);
        this.headerText.setOnClickListener(this.onClickListener);
        this.buttonFooterCounter = (Button) fragmentView.findViewById(R.id.button_footer_count);
        this.buttonFooterCounter.setOnClickListener(this.onClickListener);
        return fragmentView;
    }

    public void onDestroyView() {
        if (this.adWhirlLayout != null) {
            this.adWhirlLayout.removeAllViews();
            this.adWhirlLayout.destroy();
        }
        super.onDestroyView();
    }

    public void onAttach(Activity act) {
        super.onAttach(act);
        this.context = getActivity();
        this.activity = getActivity();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logGalleryFolders();
        setGridAdapter();
    }

    private void setGridAdapter() {
        this.gridView = (GridView) getView().findViewById(R.id.gridView);
        this.adapter = new MyGridAdapter(context, (albumList.get(albumList.size() - 1)).gridItems, this.gridView);
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);
    }

    private List<GridViewItem> createGridItemsOnClick(int position) {
        List<GridViewItem> items = new ArrayList();
        Album album = (Album) this.albumList.get(position);
        List<Long> imageIdList = album.imageIdList;
        List<Integer> orientList = album.orientationList;
        for (int i = 0; i < imageIdList.size(); i++) {
            items.add(new GridViewItem(this.activity, BuildConfig.FLAVOR, BuildConfig.FLAVOR, false, ((Long) imageIdList.get(i)).longValue(), ((Integer) orientList.get(i)).intValue()));
        }
        return items;
    }

    private boolean logGalleryFolders() {
        this.albumList = new ArrayList();
        List<Integer> bucketIdList = new ArrayList();
        ContentResolver contentResolver = this.context.getContentResolver();
        Cursor cur = contentResolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "bucket_display_name", "bucket_id", "_id", "orientation"}, "1) GROUP BY 1,(2", null, "date_modified DESC");
        List<GridViewItem> items;
        int i;
        if (cur == null || !cur.moveToFirst()) {
            items = new ArrayList();
            for (i = 0; i < this.albumList.size(); i++) {
                items.add(new GridViewItem(this.activity, ((Album) this.albumList.get(i)).name, "" + ((Album) this.albumList.get(i)).imageIdList.size(), true, ((Album) this.albumList.get(i)).imageIdForThumb, ((Integer) ((Album) this.albumList.get(i)).orientationList.get(0)).intValue()));
            }
            this.albumList.add(new Album());
            ((Album) this.albumList.get(this.albumList.size() - 1)).gridItems = items;
            for (i = 0; i < this.albumList.size() - 1; i++) {
                ((Album) this.albumList.get(i)).gridItems = createGridItemsOnClick(i);
            }
            return true;
        }
        int bucketColumn = cur.getColumnIndex("bucket_display_name");
        int bucketId = cur.getColumnIndex("bucket_id");
        int imageId = cur.getColumnIndex("_id");
        int orientationColumnIndex = cur.getColumnIndex("orientation");
        do {
            Album album = new Album();
            int id = cur.getInt(bucketId);
            album.ID = id;
            if (bucketIdList.contains(Integer.valueOf(id))) {
                Album albumFromList = (Album) this.albumList.get(bucketIdList.indexOf(Integer.valueOf(album.ID)));
                albumFromList.imageIdList.add(Long.valueOf(cur.getLong(imageId)));
                albumFromList.orientationList.add(Integer.valueOf(cur.getInt(orientationColumnIndex)));
            } else {
                String bucket = cur.getString(bucketColumn);
                bucketIdList.add(Integer.valueOf(id));
                album.name = bucket;
                album.imageIdForThumb = cur.getLong(imageId);
                album.imageIdList.add(Long.valueOf(album.imageIdForThumb));
                this.albumList.add(album);
                album.orientationList.add(Integer.valueOf(cur.getInt(orientationColumnIndex)));
            }
        } while (cur.moveToNext());
        items = new ArrayList();
        for (i = 0; i < this.albumList.size(); i++) {
            items.add(new GridViewItem(this.activity, ((Album) this.albumList.get(i)).name, "" + ((Album) this.albumList.get(i)).imageIdList.size(), true, ((Album) this.albumList.get(i)).imageIdForThumb, ((Integer) ((Album) this.albumList.get(i)).orientationList.get(0)).intValue()));
        }
        this.albumList.add(new Album());
        ((Album) this.albumList.get(this.albumList.size() - 1)).gridItems = items;
        for (i = 0; i < this.albumList.size() - 1; i++) {
            ((Album) this.albumList.get(i)).gridItems = createGridItemsOnClick(i);
        }
        return true;
    }

    public boolean onBackPressed() {
        return backtrace();
    }

    boolean backtrace() {
        if (this.isOnBucket) {
            this.galleryListener.onGalleryCancel();
            return true;
        }
        this.adapter.items = ((Album) this.albumList.get(this.albumList.size() - 1)).gridItems;
        this.adapter.notifyDataSetChanged();
        this.gridView.smoothScrollToPosition(0);
        this.isOnBucket = true;
        this.headerText.setText("  "+getString(R.string.gallery_select_an_album));
        return false;
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int location, long arg3) {
        if (this.isOnBucket) {
            this.adapter.items = ((Album) this.albumList.get(location)).gridItems;
            this.adapter.notifyDataSetChanged();
            this.gridView.smoothScrollToPosition(0);
            this.isOnBucket = false;
            this.selectedBucketId = location;
            this.headerText.setText(" "+((Album) this.albumList.get(location)).name);
        } else if (this.footer.getChildCount() >= COLLAGE_IMAGE_LIMIT) {
            Toast msg = Toast.makeText(this.context, String.format(getString(R.string.gallery_no_more), new Object[]{Integer.valueOf(COLLAGE_IMAGE_LIMIT)}), Toast.LENGTH_SHORT);
            msg.setGravity(17, msg.getXOffset() / 2, msg.getYOffset() / 2);
            msg.show();
        } else {
            View retval = LayoutInflater.from(this.context).inflate(R.layout.footer_item, null);
            ((ImageView) retval.findViewById(R.id.imageView_delete)).setOnClickListener(this.onClickListener);
            ImageView im = (ImageView) retval.findViewById(R.id.imageView);
            long id = ((Long) ((Album) this.albumList.get(this.selectedBucketId)).imageIdList.get(location)).longValue();
            this.selectedImageIdList.add(Long.valueOf(id));
            this.selectedImageOrientationList.add((Integer) ((Album) this.albumList.get(this.selectedBucketId)).orientationList.get(location));
            im.setImageBitmap(GalleryUtility.getThumbnailBitmap(this.context, id, ((Integer) ((Album) this.albumList.get(this.selectedBucketId)).orientationList.get(location)).intValue()));
            this.footer.addView(retval);
            this.buttonFooterCounter.setText(" "+this.footer.getChildCount());
            GridViewItem gridViewItem = (GridViewItem) this.adapter.items.get(location);
            gridViewItem.selectedItemCount++;
            TextView text = (TextView) arg1.findViewById(R.id.textViewSelectedItemCount);
            text.setText(" "+(adapter.items.get(location)).selectedItemCount);
            if (text.getVisibility() == View.INVISIBLE) {
                text.setVisibility(View.VISIBLE);
            }
            if (this.collageSingleMode) {
                photosSelectFinished();
                this.collageSingleMode = false;
            }
        }
    }

    Point findItemById(long id) {
        for (int i = 0; i < this.albumList.size() - 1; i++) {
            List<GridViewItem> list = ((Album) this.albumList.get(i)).gridItems;
            for (int j = 0; j < list.size(); j++) {
                if (((GridViewItem) list.get(j)).imageIdForThumb == id) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    void photosSelectFinished() {
        int size = this.selectedImageIdList.size();
        if (size == 0) {
            Toast msg = Toast.makeText(this.context, getString(R.string.gallery_select_one), Toast.LENGTH_SHORT);
            msg.setGravity(17, msg.getXOffset() / 2, msg.getYOffset() / 2);
            msg.show();
            return;
        }
        int i;
        long[] arrr = new long[size];
        for (i = 0; i < size; i++) {
            arrr[i] = ((Long) this.selectedImageIdList.get(i)).longValue();
        }
        int[] orientationArr = new int[size];
        for (i = 0; i < size; i++) {
            orientationArr[i] = ((Integer) this.selectedImageOrientationList.get(i)).intValue();
        }
        this.galleryListener.onGalleryOkImageArray(arrr, orientationArr, this.isScrapBook);
    }
}
