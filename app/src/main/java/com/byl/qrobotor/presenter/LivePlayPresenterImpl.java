package com.byl.qrobotor.presenter;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.DanmuBean;
import com.byl.qrobotor.bean.LiveDetailBean;
import com.byl.qrobotor.bean.LivePandaBean;
import com.byl.qrobotor.model.LivePlayModelImpl;
import com.byl.qrobotor.view.ILivePlayView;

/**
 * author: lw
 * date: 2016/9/20
 */

public class LivePlayPresenterImpl extends BasePresenter<ILivePlayView> implements ILivePlayPresenter {
    private ILivePlayView mView;
    private LivePlayModelImpl mModel;

    @Override
    public void getLiveDetail(String live_type, String live_id, String game_type) {
        mView = getView();
        if (mView != null) {
            mModel = new LivePlayModelImpl();
            mModel.getLiveDetail(live_type, live_id, game_type, new BaseOnLoadingListener<LiveDetailBean>() {
                @Override
                public void onSuccessed(LiveDetailBean liveDetailBean) {
                    mView.updateLiveDetail(liveDetailBean);
                }

                @Override
                public void onFailed(Object object) {
                    mView.showError(object.toString());
                }
            });
        }

    }

    @Override
    public void getDanmuDetail(String roomid, String live_type) {
        if (mView != null) {
            mModel.getDanmuDetail(roomid, live_type, new BaseOnLoadingListener<LivePandaBean>() {
                @Override
                public void onSuccessed(LivePandaBean livePandaBean) {
                    mView.updateChatDetail(livePandaBean);
                }

                @Override
                public void onFailed(Object object) {
                    mView.showError(object.toString());
                }
            });
        }
    }

    @Override
    public void connectToChatRoom(String live_id, LivePandaBean pandaBean) {
        if (mView != null) {
            mModel.connectToChatRoom(live_id, pandaBean, new BaseOnLoadingListener<DanmuBean>() {
                @Override
                public void onSuccessed(DanmuBean danmuBean) {
                    mView.receiveDanmu(danmuBean);
                }

                @Override
                public void onFailed(Object object) {
                    mView.showError(object.toString());
                }
            });
        }
    }

    @Override
    public void closeConnection() {
        if (mView != null && mModel != null) mModel.closeConnection();
    }
}
