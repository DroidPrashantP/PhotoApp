package com.app.paddycameraeditior.sticker;

public class StickerGridItem {
    int resId;
    int selectedItemCount;

    public StickerGridItem(int resId, int count) {
        this.selectedItemCount = 0;
        this.resId = resId;
        this.selectedItemCount = count;
    }

    public StickerGridItem(int resId) {
        this.selectedItemCount = 0;
        this.resId = resId;
        this.selectedItemCount = 0;
    }
}
