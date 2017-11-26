package com.byl.qrobotor.model;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.NewsListItemBean;
import com.byl.qrobotor.bean.jokebean.InnerDataTextGroupBean;
import com.byl.qrobotor.http.GsonHelper;
import com.byl.qrobotor.http.HttpSubscriber;
import com.byl.qrobotor.http.NewsAPI;
import com.byl.qrobotor.http.RetrofitHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by acer on 2017/6/5.
 */

public class NewsListModelImp implements INewsListModel {

    @Override
    public void onLoadNewsListData(String news_id, int offset, final BaseOnLoadingListener<List<NewsListItemBean>> listener) {
        RetrofitHelper.getNewsHelper()
                .getNewsList(news_id, offset, NewsAPI.LIMIT)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<JsonObject, Publisher<List<NewsListItemBean>>>() {
                    @Override
                    public Publisher<List<NewsListItemBean>> apply(JsonObject jsonObject) throws Exception {
                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            if (entry.getValue().isJsonArray()) {
                                JsonArray array = entry.getValue().getAsJsonArray();

                                List<NewsListItemBean> list = new ArrayList<>();
                                for (JsonElement element : array) {
                                    list.add((NewsListItemBean) GsonHelper.parseJson(element, NewsListItemBean.class));
                                }
                                return Flowable.just(list);
                            }
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<List<NewsListItemBean>>() {

                    @Override
                    public void _onNext(List<NewsListItemBean> exploreListItemBeen) {
                        listener.onSuccessed(exploreListItemBeen);
                    }

                    @Override
                    public void _onError(String message) {
                        listener.onFailed(message);
                    }
                });
    }

    @Override
    public void onLoadJokeListData(final BaseOnLoadingListener<List<InnerDataTextGroupBean>> listener) {
        RetrofitHelper.getJokeHelper()
                .getJokeTextList()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<JsonObject, Publisher<List<InnerDataTextGroupBean>>>() {
                    @Override
                    public Publisher<List<InnerDataTextGroupBean>> apply(JsonObject jsonObject) throws Exception {

                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            if (entry.getValue().isJsonObject()) {
                                JsonObject object = entry.getValue().getAsJsonObject();
                                JsonArray data=object.get("data").getAsJsonArray();
                                List<InnerDataTextGroupBean> list = new ArrayList<>();
                                for (JsonElement element : data) {
                                    list.add((InnerDataTextGroupBean) GsonHelper.parseJson(element, InnerDataTextGroupBean.class));
                                }
                                return Flowable.just(list);
                            }
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<List<InnerDataTextGroupBean>>() {

                    @Override
                    public void _onNext(List<InnerDataTextGroupBean> exploreListItemBeen) {
                        listener.onSuccessed(exploreListItemBeen);
                    }

                    @Override
                    public void _onError(String message) {
                        listener.onFailed(message);
                    }
                });
    }
}
