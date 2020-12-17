package com.launcher.samsung.galaxy.galaxys8launcher.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Trung Tran Thanh on 10/16/2017.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_INSTALL_PACKAGE)  || intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)){
            String packageName = intent.getData().getEncodedSchemeSpecificPart();
            context.sendBroadcast(new Intent("new_app"));
        }
    }
}
