package com.app.paddycameraeditior;

import android.app.Application;
import android.graphics.Bitmap;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Prashant on 24/12/15.
 */
public class AppController extends Application{

    private static final String TAG = AppController.class.getSimpleName();
    private static final String PROPERTY_ID = "UA-75899562-2";
    public Bitmap MainBitmap = null;
    public Bitmap cropped = null;
    public Bitmap shareBitmap = null;
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();



    @Override
    public void onCreate(){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        // load custom font

    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                    : analytics.newTracker(PROPERTY_ID);// if trackerId == TrackerName.GLOBAL_TRACKER
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

}

