package com.byl.qrobotor.model;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.NewsListItemBean;
import com.byl.qrobotor.bean.jokebean.InnerDataTextGroupBean;

import java.util.List;

/**
 * Created by acer on 2017/6/5.
 */

public interface INewsListModel {
    void onLoadNewsListData(String news_id, int offset, BaseOnLoadingListener<List<NewsListItemBean>> listener);//请求不同栏目的新闻列表
    void onLoadJokeListData(BaseOnLoadingListener<List<InnerDataTextGroupBean>> listener);//请求段子列表

}
