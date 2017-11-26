package com.byl.qrobotor.view;


import com.byl.qrobotor.bean.LiveListItemBean;
import com.byl.qrobotor.http.exception.IErrorView;

import java.util.List;

/**
 * author: lw
 * date: 2016/9/9
 */
public interface ILiveListView extends IErrorView {
    void updateRecyclerView(List<LiveListItemBean> liveListBeanList);//更新列表
}
