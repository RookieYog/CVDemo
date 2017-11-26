package com.byl.qrobotor.view;


import com.byl.qrobotor.bean.NewsListItemBean;
import com.byl.qrobotor.bean.jokebean.InnerDataTextGroupBean;
import com.byl.qrobotor.http.exception.IErrorView;

import java.util.List;

/**
 * author: lw
 * date: 2016/9/9
 */
public interface INewsListView extends IErrorView {
    void updateNewsRecyclerView(List<NewsListItemBean> roomBeanList);//更新列表
    void updateJokeRecyclerView(List<InnerDataTextGroupBean> jokeBeanList);//更新列表
}
