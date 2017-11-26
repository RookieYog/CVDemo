package com.byl.qrobotor.presenter;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.model.NewsDetailModelImpl;
import com.byl.qrobotor.view.INewsDetailView;

/**
 * Created by Administrator on 2016/11/30.
 */

public class NewsDetailPresenterImpl extends BasePresenter<INewsDetailView> implements INewsDetailPresenter {

    private INewsDetailView mView;
    private NewsDetailModelImpl mModel;

    @Override
    public void getNewsDetail(String doc_id) {
        mView = getView();
        if (mView != null) {
            mModel=new NewsDetailModelImpl();
            mModel.onLoadNewsDetail(doc_id, new BaseOnLoadingListener<String>() {
                @Override
                public void onSuccessed(String s) {
                    mView.updateWebView(s);
                }

                @Override
                public void onFailed(Object object) {
                    mView.showError(object.toString());
                }
            });
        }
    }
}
