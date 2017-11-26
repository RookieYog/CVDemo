package com.byl.qrobotor.view;


import com.byl.qrobotor.http.exception.IErrorView;

/**
 * Created by Administrator on 2016/11/30.
 */

public interface INewsDetailView extends IErrorView {

    void updateWebView(String html);

    //void updateNewsDetail(NewsDetailBean detailBean);
}
