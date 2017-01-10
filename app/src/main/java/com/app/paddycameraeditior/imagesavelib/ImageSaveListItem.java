package com.app.paddycameraeditior.imagesavelib;

public class ImageSaveListItem {
    SAVE_IMAGE_ID id;
    int imageResId;
    String text;

    public enum SAVE_IMAGE_ID {
        SAVE_IMAGE,
        INSTAGRAM,
        WHATSAPP,
        FACEBOOK,
        TWITTER,
        EMAIL,
        SHARE,
        REMOVE_ADS,
        TITLE_1,
        INSTAGRAM_FOLLOW,
        FB_LIKE,
        TWITTER_FOLLOW,
        CONTACT,
        TITLE_2,
        RECOMMAND_1,
        RECOMMAND_2,
        MORE_APPS,
        RATE
    }

    public ImageSaveListItem(SAVE_IMAGE_ID id, String text, int resId) {
        this.id = id;
        this.text = text;
        this.imageResId = resId;
    }
}
