/*******************************************************************************
 *      ____.____  __.____ ___     _____
 *     |    |    |/ _|    |   \   /  _  \ ______ ______
 *     |    |      < |    |   /  /  /_\  \\____ \\____ \
 * /\__|    |    |  \|    |  /  /    |    \  |_> >  |_> >
 * \________|____|__ \______/   \____|__  /   __/|   __/
 *                  \/                  \/|__|   |__|
 *
 * Copyright (c) 2014-2015 Paul "Marunjar" Pretsch
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 ******************************************************************************/

package org.voidsink.anewjkuapp.analytics;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import org.voidsink.anewjkuapp.BuildConfig;

import java.util.HashMap;

public class AnalyticsFlavor implements IAnalytics {

    private static final String TAG = AnalyticsFlavor.class.getSimpleName();
    private Application mApp = null;

    public enum TrackerName {
        APP_TRACKER
    }

    private static String PROPERTY_ID = "UA-51633871-1";

    private static final String GA_EVENT_CATEGORY_UI = "ui_action";

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    private synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(mApp);
            switch (trackerId) {
                case APP_TRACKER:
                    Tracker t = analytics.newTracker(PROPERTY_ID);
                    mTrackers.put(trackerId, t);

                    Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(t,
                            Thread.getDefaultUncaughtExceptionHandler(), mApp);
                    // Make myHandler the new default uncaught exception handler.
                    Thread.setDefaultUncaughtExceptionHandler(myHandler);

                    // disable auto activity tracking
                    t.enableAutoActivityTracking(false);

                    // try to initialize screen size
                    try {
                        WindowManager wm = (WindowManager) mApp.getSystemService(Context.WINDOW_SERVICE);
                        Display display = wm.getDefaultDisplay();

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            Point size = new Point();
                            display.getSize(size);

                            t.setScreenResolution(size.x, size.y);
                        } else {
                            t.setScreenResolution(display.getWidth(), display.getHeight());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "get sceen size", e);
                    }

                    break;
            }
        }
        return mTrackers.get(trackerId);
    }

    private Tracker getAppTracker() {
        return getTracker(TrackerName.APP_TRACKER);
    }

    @Override
    public void init(Application app) {
        if (mApp == null) {
            mApp = app;

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(mApp);
            if (BuildConfig.DEBUG) {
                //analytics.setDryRun(true);
                analytics.setAppOptOut(false);
                analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
                Log.i(TAG, "debug enabled");
            } else {
                analytics.enableAutoActivityReports(mApp);
                analytics.setAppOptOut(false); // TODO: get option from shared preferences
                Log.i(TAG, "debug disabled");
            }
        } else {
            throw new UnknownError("Analytics already initialized");
        }
    }

    @Override
    public void sendException(Context c, Exception e, boolean fatal, String additionalData) {

    }

    @Override
    public void sendScreen(Context c, String screenName) {
        Tracker t = getAppTracker();
        if (t != null) {
            // output some debug info
            if (BuildConfig.DEBUG) {
                Log.d(TAG, String.format("sendScreen: %s", screenName));
            }

            t.setScreenName(screenName);
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    @Override
    public void sendButtonEvent(String label) {
        Tracker t = getAppTracker();
        if (t != null && TextUtils.isEmpty(label)) {
            t.send(new HitBuilders.EventBuilder()
                    .setCategory(GA_EVENT_CATEGORY_UI)
                    .setAction("button_press")
                    .setLabel(label)
                    .build());
        }
    }
}