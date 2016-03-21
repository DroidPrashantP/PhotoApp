package com.app.camera.Domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by prashant on 23/1/16.
 */
public class StickerTabSelection {
    public static StickerTabSelection instance;
    public String currentVisibleFragment;
    private ArrayList<Integer> selectedCategoryList = new ArrayList<Integer>();

    public static StickerTabSelection getInstance() {
        if (instance == null) {
            instance = new StickerTabSelection();
        }
        return instance;
    }

    public String getCurrentVisibleFragment() {
        return currentVisibleFragment;
    }

    public void setCurrentVisibleFragment(String currentVisibleFragment) {
        this.currentVisibleFragment = currentVisibleFragment;
    }

    /**
     * add category to arraylist
     */
    public void addSticker(int id) {
        selectedCategoryList.add(id);
        // remove duplicates
        HashSet hs = new HashSet();
        hs.addAll(selectedCategoryList);
        selectedCategoryList.clear();
        selectedCategoryList.addAll(hs);
    }

    /**
     * remove categories from list
     */
    public void removeSticker(int id) {
        selectedCategoryList.remove(""+id);
    }

    /**
     * get filter categories list
     */
    public ArrayList<Integer> getSelectedStickerList() {
        return selectedCategoryList;
    }
}
