package com.byl.qrobotor.http;

import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by acer on 2017/6/5.
 */

public interface JokeAPI {
    //段子
    @GET("?mpic=1&webp=1&essence=1&content_type=-102")
    Flowable<JsonObject> getJokeTextList();
    //视频
    @GET("?mpic=1&webp=1&essence=1&content_type=-104")
    Flowable<JsonObject> getJokeVideoList();
}
