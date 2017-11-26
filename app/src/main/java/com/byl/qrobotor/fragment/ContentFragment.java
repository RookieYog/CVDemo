package com.byl.qrobotor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byl.qrobotor.R;
import com.byl.qrobotor.adapter.JokeTextAdapter;
import com.byl.qrobotor.adapter.NewsListAdapter;
import com.byl.qrobotor.base.BaseFragment;
import com.byl.qrobotor.bean.NewsListItemBean;
import com.byl.qrobotor.bean.jokebean.InnerDataTextGroupBean;
import com.byl.qrobotor.http.NewsAPI;
import com.byl.qrobotor.presenter.NewsListPresenterImpl;
import com.byl.qrobotor.util.ToastUtil;
import com.byl.qrobotor.view.INewsListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContentFragment} interface
 * to handle interaction events..OnFragmentInteractionListener
 * Use the {@link ContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentFragment extends BaseFragment<INewsListView, NewsListPresenterImpl> implements INewsListView {

    private static final String CONTENTFRAGMENT_PARAM = "param";
    private String mNewsTypeId;
    private View mView;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private JokeTextAdapter adapter;

    private int offset = 0;
    private List<NewsListItemBean> mNewsListBean;
    private List<InnerDataTextGroupBean> mJokeTextGroupBean;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Context mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mActivity = context;
        } else {
            mActivity = getActivity();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsTypeId = getArguments().getString(CONTENTFRAGMENT_PARAM);
        }
        mNewsListBean = new ArrayList<>();
        mJokeTextGroupBean = new ArrayList<>();
        mHandlerThread = new HandlerThread(getClass().getSimpleName());
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        adapter = new JokeTextAdapter(mJokeTextGroupBean);
        mAdapter = new NewsListAdapter(mNewsListBean);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.content_layout, container, false);
        mTwinklingRefreshLayout = (TwinklingRefreshLayout) mView.findViewById(R.id.refreshlayout);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        if (!mNewsTypeId.startsWith("T")) {
            adapter.setEmptyView(R.layout.empty_layout, mRecyclerView);
            mRecyclerView.setAdapter(adapter);
        } else {
            mAdapter.setEmptyView(R.layout.empty_layout, mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
        }
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            /**
             * 设置一共请求多少次数据
             */
            int ALLSUM = 0;

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                offset = 0;//重置偏移量
                //根据explore_type_id请求更多新闻数据
                if (mNewsListBean.size() > 0) {
                    mNewsListBean.clear();

                }
                if (mJokeTextGroupBean.size() > 0) {
                    mJokeTextGroupBean.clear();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.toDo(mNewsTypeId, offset);
                    }
                });
                ALLSUM = 0;
                mTwinklingRefreshLayout.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.toDo(mNewsTypeId, offset);
                    }
                });
                ALLSUM++;
                mTwinklingRefreshLayout.finishLoadmore();
            }
        });
        mTwinklingRefreshLayout.startRefresh();

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getData().get(0) instanceof NewsListItemBean) {
                    NewsListItemBean newsListItemBean = mAdapter.getData().get(position);
                    switch (newsListItemBean.getItemType()) {
                        case NewsListItemBean.NORMAL:
                            FragmentManager fm = getParentFragment().getFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            BaseFragment mDetailFragment = DetailFragment.newInstance(newsListItemBean.getDocid(),
                                    newsListItemBean.getTitle());
                            if (!mDetailFragment.isAdded()) {
                                transaction.add(R.id.relativeLayout_container, mDetailFragment);
                            }
                            transaction.addToBackStack(null);
                            transaction.commitAllowingStateLoss();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mNewsTypeId) {
            mNewsTypeId = null;
        }

        if (null != mJokeTextGroupBean) {
            mJokeTextGroupBean.clear();
            mJokeTextGroupBean = null;
        }
        if (null != mNewsListBean) {
            mNewsListBean.clear();
            mNewsListBean = null;
        }
        if (null != mView) {
            mView = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    protected NewsListPresenterImpl initPresenter() {
        return new NewsListPresenterImpl();
    }

    public static ContentFragment newInstance(String param) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(CONTENTFRAGMENT_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void updateNewsRecyclerView(final List<NewsListItemBean> newsBeanList) {
        mAdapter.addData(newsBeanList);
        offset = mAdapter.getData().size();
        if (offset < NewsAPI.LIMIT) {//分页数据size比每页数据的limit小，说明已全部加载数据
            mTwinklingRefreshLayout.finishLoadmore();
        }
    }

    @Override
    public void updateJokeRecyclerView(final List<InnerDataTextGroupBean> jokeBeanList) {

        adapter.addData(jokeBeanList);
        offset = adapter.getData().size();
        if (offset < NewsAPI.LIMIT) {//分页数据size比每页数据的limit小，说明已全部加载数据
            mTwinklingRefreshLayout.finishLoadmore();
        }
    }


    @Override
    public void showError(String message) {
        //在加载失败的时候调用showLoadMoreFailedView()就能显示加载失败的footer了，点击footer会重新加载
        ToastUtil.showToast(message);
    }

}
