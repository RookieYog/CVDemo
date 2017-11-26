package com.byl.qrobotor.http;


import com.byl.qrobotor.bean.PhotoSetBean;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface NewsAPI {

    int LIMIT = 20;
    @Headers({
            "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Language:zh-CN,zh;q=0.8",
            "Cache-Control:max-age=0",
            "Connection:keep-alive",
            "Host:c.m.163.com",
            "Upgrade-Insecure-Requests:1",
            "User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36"
            })
    @GET("/nc/article/list/{explore_id}/{offset}-{limit}.html")
    Flowable<JsonObject> getNewsList(
            @Path("explore_id") String explore_id,
            @Path("offset") int page,
            @Path("limit") int limit

    );

    @Headers({
            "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Language:zh-CN,zh;q=0.8",
            "Cache-Control:max-age=0",
            "Connection:keep-alive",
            "Host:c.m.163.com",
            "Upgrade-Insecure-Requests:1",
            "User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36"
            })
    @GET("/nc/article/{docid}/full.html")
    Flowable<JsonObject> getNewsDetail(@Path("docid") String docid);



    @Headers({
            "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Language:zh-CN,zh;q=0.8",
            "Cache-Control:max-age=0",
            "Connection:keep-alive",
            "Host:c.m.163.com",
            "Upgrade-Insecure-Requests:1",
            "User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36"
    })
    @GET("/photo/api/set/{typeid}/{setid}.json")
    Flowable<PhotoSetBean> getNewsSet(
            @Path("typeid") String typeid,
            @Path("setid") String setid
    );
}
