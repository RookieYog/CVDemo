package com.byl.qrobotor.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * author: lw
 * date: 2017/2/27
 * desc: GsonHelper工具类
 */

public class GsonHelper {

    private static Object obj;
    private static Gson gson;
    //序列化所有注解的字段
    public static Gson getInstance() {
        synchronized (GsonHelper.class) {
            if (gson == null) {
                gson = new Gson();
            }
        }
        return gson;
    }

    /**
     * @param jsonStr
     * @param clazz
     * @return
     * @throws
     * @Title: parseJson
     * @Description: json 转成对象
     */
    public static Object parseJson(String jsonStr, Class clazz) {
        gson = getInstance();
        obj = gson.fromJson(jsonStr, clazz);
        return obj;
    }

    /**
     * @param jsonElement
     * @param clazz
     * @return
     * @throws
     * @Title: parseJson
     * @Description: json 转成对象
     */
    public static Object parseJson(JsonElement jsonElement, Class clazz) {
        gson = getInstance();
        obj = gson.fromJson(jsonElement, clazz);
        return obj;
    }

    /**
     * @param obj
     * @return
     * @throws
     * @Title: toJson
     * @Description: 对象转成json
     */
    public static String toJson(Object obj) {
        String strJson;
        gson = getInstance();
        strJson = gson.toJson(obj);
        return strJson;
    }

}
