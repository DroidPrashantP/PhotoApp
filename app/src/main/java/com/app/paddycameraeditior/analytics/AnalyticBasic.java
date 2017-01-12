package com.app.paddycameraeditior.analytics;

import android.app.Activity;

import com.app.paddycameraeditior.AppController;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by prashant on 1/12/17.
 */

public class AnalyticBasic {

    /**
     * Send google analytics hit event and sets the screen name that will be visible in analytics
     *
     * @param activity
     */
    public static void hitGoogleAnalytics(Activity activity, String screenName) {
        // Get tracker.
        Tracker t = ((AppController) activity.getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName(screenName);
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
        t.setScreenName(null); // // Clear the screen name field when we're done.
    }
}
