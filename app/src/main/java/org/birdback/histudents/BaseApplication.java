package org.birdback.histudents;

import android.app.Application;
import android.content.Context;

/**
 *
 * Created by meixin.song on 2018/4/6.
 */

public class BaseApplication extends Application {
    private static Context mApplication;

    public static Context getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
