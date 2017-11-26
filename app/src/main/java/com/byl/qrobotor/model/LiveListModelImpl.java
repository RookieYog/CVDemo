package com.byl.qrobotor.model;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.LiveListItemBean;
import com.byl.qrobotor.http.HttpSubscriber;
import com.byl.qrobotor.http.RetrofitHelper;

import java.util.List;

/**
 * Created by acer on 2017/6/30.
 */

public class LiveListModelImpl implements ILiveListModel {
    @Override
    public void onLoadNewsListData(int offset, int limit, String live_type, String game_type, final BaseOnLoadingListener<List<LiveListItemBean>> listener) {
        RetrofitHelper.getLiveHelper()
                .getLiveList(offset, limit, live_type, game_type)
                .compose(RetrofitHelper.<List<LiveListItemBean>>handleLiveResult())
                .subscribe(new HttpSubscriber<List<LiveListItemBean>>() {

                    @Override
                    public void _onNext(List<LiveListItemBean> liveListItemBeen) {

                        listener.onSuccessed(liveListItemBeen);
                    }

                    @Override
                    public void _onError(String message) {
                        listener.onFailed(message);
                    }
                });
    }
}
