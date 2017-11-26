package com.byl.qrobotor.model;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.LiveListItemBean;

import java.util.List;

/**
 * Created by acer on 2017/6/30.
 */

public interface ILiveListModel {
    void onLoadNewsListData(int offset, int limit, String live_type, String game_type, BaseOnLoadingListener<List<LiveListItemBean>> listener);//请求不同栏目的Live列表
}
