package com.byl.qrobotor.base;

/**
 * Created by acer on 2017/6/5.
 */

public interface BaseOnLoadingListener<T> {
    void onSuccessed(T t);
    void onFailed(Object object);
}

