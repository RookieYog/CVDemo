package com.byl.qrobotor.util;

import android.util.Log;

import com.byl.qrobotor.common.App;

public class LogUtil {


    public static void showLog(String message) {
        Log.e(App.mAppContext.getPackageName(), message);
    }

}
