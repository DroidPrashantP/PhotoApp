package com.app.camera.gallerylib;

import java.util.ArrayList;
import java.util.List;

public class Album {
    int ID;
    long coverID;
    List<GridViewItem> gridItems;
    String id;
    long imageIdForThumb;
    List<Long> imageIdList;
    String name;
    List<Integer> orientationList;

    public Album() {
        this.imageIdList = new ArrayList();
        this.orientationList = new ArrayList();
    }
}
