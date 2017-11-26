package com.byl.qrobotor.util;

import android.widget.Toast;

import com.byl.qrobotor.common.App;


/**
 * Created by acer on 2017/4/9.
 */

public class ToastUtil {

    public static void showToast(String message) {
        Toast.makeText(App.mAppContext, message, Toast.LENGTH_SHORT).show();
    }
}
