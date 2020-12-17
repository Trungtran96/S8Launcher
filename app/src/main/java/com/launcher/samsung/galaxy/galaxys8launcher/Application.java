package com.launcher.samsung.galaxy.galaxys8launcher;

import com.znanotech.ZAndroidSDK;

/**
 * Created by Trung Tran Thanh on 7/26/2017.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZAndroidSDK.initApplication(this, getApplicationContext().getPackageName());
    }

}
