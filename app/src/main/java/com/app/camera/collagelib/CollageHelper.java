package com.app.camera.collagelib;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.app.camera.gallerylib.GalleryFragment;
import com.google.android.gms.ads.InterstitialAd;

public class CollageHelper {
    protected static final String TAG = "CollageHelper";

    static class C09481 implements GalleryFragment.GalleryListener {
        private final FragmentActivity val$activity;
        private final GalleryFragment val$galleryFragment;
        private final InterstitialAd val$interstitial;
        private final boolean val$showInter;

        C09481(FragmentActivity fragmentActivity, GalleryFragment galleryFragment, boolean z, InterstitialAd interstitialAd) {
            this.val$activity = fragmentActivity;
            this.val$galleryFragment = galleryFragment;
            this.val$showInter = z;
            this.val$interstitial = interstitialAd;
        }

        public void onGalleryOkSingleImage(long ImageIdList, int orientationList, boolean isScrapBook) {
        }

        public void onGalleryOkImageArrayRemoveFragment(long[] ImageIdList, int[] orientationList, boolean isScrapBook) {
        }

        public void onGalleryOkImageArray(long[] ImageIdList, int[] orientationList, boolean isScrapBook) {
            Log.e(CollageHelper.TAG, "onGalleryOkImageArray");
            Intent intent = new Intent(this.val$activity, CollageActivity.class);
            intent.putExtra("photo_id_list", ImageIdList);
            intent.putExtra("photo_orientation_list", orientationList);
            intent.putExtra("is_scrap_book", isScrapBook);
            this.val$activity.startActivity(intent);
        }

        public void onGalleryCancel() {
            this.val$activity.getSupportFragmentManager().beginTransaction().hide(this.val$galleryFragment).commitAllowingStateLoss();
            if (this.val$showInter && this.val$interstitial != null) {
              //  ImageUtility.displayInterStitialWithSplashScreen(this.val$interstitial, this.val$activity, ImageUtility.SPLASH_TIME_OUT_DEFAULT, "COLLAGE_GALLERY_CLOSED");
            }
        }
    }

    public static GalleryFragment getGalleryFragment(FragmentActivity activity) {
        return (GalleryFragment) activity.getSupportFragmentManager().findFragmentByTag("myFragmentTag");
    }

    public static GalleryFragment addGalleryFragment(FragmentActivity activity, int gallery_fragment_container, InterstitialAd interstitial, boolean showInter) {
        FragmentManager fm = activity.getSupportFragmentManager();
        GalleryFragment galleryFragment = (GalleryFragment) fm.findFragmentByTag("myFragmentTag");
        if (galleryFragment == null) {
            galleryFragment = new GalleryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(gallery_fragment_container, galleryFragment, "myFragmentTag");
            ft.commitAllowingStateLoss();
            galleryFragment.setGalleryListener(createGalleryListener(activity, galleryFragment, interstitial, showInter));
            activity.findViewById(gallery_fragment_container).bringToFront();
            return galleryFragment;
        }
        activity.getSupportFragmentManager().beginTransaction().show(galleryFragment).commitAllowingStateLoss();
        return galleryFragment;
    }

    public static GalleryFragment.GalleryListener createGalleryListener(FragmentActivity activity, GalleryFragment galleryFragment, InterstitialAd interstitial, boolean showInter) {
        return new C09481(activity, galleryFragment, showInter, interstitial);
    }
}
