package com.byl.qrobotor.http;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.subscribers.DefaultSubscriber;

/**
 * author: lw
 * date: 2016/9/1
 */
public abstract class HttpSubscriber<T> extends DefaultSubscriber<T> {

    public abstract void _onNext(T t);

    public abstract void _onError(String message);

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof JSONException || e instanceof ParseException){
            _onError("数据解析错误.");
        }else if (e instanceof ConnectException ||e instanceof SocketTimeoutException){
            _onError("网络请求超时,请检查网络稍后再试.");
        }else if (e instanceof UnknownHostException){
            _onError("无法解析域名,请检查网络稍后再试.");
        }else{
            _onError(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }
}
