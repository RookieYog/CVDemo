package com.byl.qrobotor.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.byl.qrobotor.common.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by acer on 2017/4/16.
 */

public class NetWorkUtil {

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean checkNetWorkConnected() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) App.mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接可用
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                flag = networkInfo.isConnected();
                return flag;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                flag = ping();
                return flag;
            }

        }

        return flag;
    }

    /*
     @author liuwei
     *@category
     判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     *@return
     */
    private static boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 2 -w 100 " + ip);// ping网址2次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            LogUtil.showLog(("------ping-----result content : " + stringBuffer.toString()));
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            LogUtil.showLog("----result---result = " + result);
        }
        return false;
    }

}
