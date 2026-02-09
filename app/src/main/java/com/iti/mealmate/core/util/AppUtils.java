package com.iti.mealmate.core.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtils {

    private  AppUtils() {}

    public static String getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);

            String versionName = packageInfo.versionName;
            long versionCode;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                versionCode = packageInfo.getLongVersionCode();
            } else {
                versionCode = packageInfo.versionCode;
            }
            return "Version " + versionName + " (Build " + versionCode + ")";
        } catch (PackageManager.NameNotFoundException e) {
            return "Version info unavailable";
        }
    }
}
