package com.byl.qrobotor.presenter;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.NewsListItemBean;
import com.byl.qrobotor.bean.jokebean.InnerDataTextGroupBean;
import com.byl.qrobotor.model.NewsListModelImp;
import com.byl.qrobotor.view.INewsListView;

import java.util.List;

/**
 * author: lw
 * date: 2016/9/9
 */
public class NewsListPresenterImpl extends BasePresenter<INewsListView> {

    private INewsListView mView;
    private NewsListModelImp mModel;

    public void toDo(String news_id, int offset) {
        mView = getView();
        if (null != mView) {
            mModel = new NewsListModelImp();
            if (!news_id.startsWith("T")) {
                mModel.onLoadJokeListData(new BaseOnLoadingListener<List<InnerDataTextGroupBean>>() {
                    @Override
                    public void onSuccessed(List<InnerDataTextGroupBean> jokeTextListBeen) {
                        mView.updateJokeRecyclerView(jokeTextListBeen);
                    }

                    @Override
                    public void onFailed(Object object) {
                        mView.showError(object.toString());
                    }
                });
                return;
            }
            mModel.onLoadNewsListData(news_id, offset, new BaseOnLoadingListener<List<NewsListItemBean>>() {
                @Override
                public void onSuccessed(List<NewsListItemBean> newsListItemBeen) {
                    mView.updateNewsRecyclerView(newsListItemBeen);
                }

                @Override
                public void onFailed(Object object) {
                    mView.showError(object.toString());
                }
            });

        }
    }
}
