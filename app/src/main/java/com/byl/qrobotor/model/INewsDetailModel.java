package com.byl.qrobotor.model;

import com.byl.qrobotor.base.BaseOnLoadingListener;

/**
 * Created by acer on 2017/6/28.
 */

public interface INewsDetailModel {
    void onLoadNewsDetail(String doc_id,BaseOnLoadingListener<String> listener);//请求不同栏目的新闻列表
}
