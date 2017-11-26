package com.byl.qrobotor.presenter;


import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.LiveListItemBean;
import com.byl.qrobotor.model.LiveListModelImpl;
import com.byl.qrobotor.view.ILiveListView;

import java.util.List;

/**
 * author: lw
 * date: 2016/9/9
 */
public class LiveListPresenterImpl extends BasePresenter<ILiveListView> implements ILiveListPresenter {

    private ILiveListView mView;
    private LiveListModelImpl mModel;

    @Override
    public void getLiveList(int offset, int limit, String live_type, String game_type) {
        mView = getView();
        if (mView != null) {
            mModel=new LiveListModelImpl();
            mModel.onLoadNewsListData(offset, limit, live_type, game_type, new BaseOnLoadingListener<List<LiveListItemBean>>() {
                @Override
                public void onSuccessed(List<LiveListItemBean> liveListItemBeen) {
                    mView.updateRecyclerView(liveListItemBeen);
                }

                @Override
                public void onFailed(Object object) {
                    mView.showError(object.toString());
                }
            });
        }
    }

}
