package com.app.camera.gallerylib;

import android.app.Activity;
import android.graphics.Bitmap;

public class GridViewItem {
    Activity context;
    String count;
    private String folderName;
    long imageIdForThumb;
    private boolean isDirectory;
    int orientation;
    int selectedItemCount;

    public GridViewItem(Activity context, String path, String count, boolean isDirectory, long imageId, int orientation) {
        this.selectedItemCount = 0;
        this.folderName = path;
        this.isDirectory = isDirectory;
        this.count = count;
        this.context = context;
        this.imageIdForThumb = imageId;
        this.orientation = orientation;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public Bitmap getImage() {
        return GalleryUtility.getThumbnailBitmap(this.context, this.imageIdForThumb, this.orientation);
    }
}
