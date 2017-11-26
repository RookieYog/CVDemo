package com.byl.qrobotor.common;

import android.app.Application;
import android.content.Context;


/**
 * author: lw
 * date: 2017/2/27
 * desc: MyApplication自定义
 */
public class App extends Application {

    public static volatile Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
    }


}
