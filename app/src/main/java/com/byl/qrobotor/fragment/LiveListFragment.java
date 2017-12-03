package com.byl.qrobotor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byl.qrobotor.R;
import com.byl.qrobotor.activity.LivePlayActivity;
import com.byl.qrobotor.adapter.LiveListAdapter;
import com.byl.qrobotor.base.BaseFragment;
import com.byl.qrobotor.bean.LiveListItemBean;
import com.byl.qrobotor.http.LiveAPI;
import com.byl.qrobotor.http.NewsAPI;
import com.byl.qrobotor.presenter.LiveListPresenterImpl;
import com.byl.qrobotor.util.LogUtil;
import com.byl.qrobotor.util.ToastUtil;
import com.byl.qrobotor.view.ILiveListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LiveListFragment} interface
 * to handle interaction events..OnFragmentInteractionListener
 * Use the {@link LiveListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveListFragment extends BaseFragment<ILiveListView, LiveListPresenterImpl> implements ILiveListView {

    private static final String CONTENTFRAGMENT_PARAM = "param";
    private String mGameType;
    private View mView;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;
    private RecyclerView mRecyclerView;
    private LiveListAdapter mAdapter;

    private int offset = 0;
    private List<LiveListItemBean> mLiveListBean;
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
            mGameType = getArguments().getString(CONTENTFRAGMENT_PARAM);
        }
        mLiveListBean = new ArrayList<>();
        mHandlerThread = new HandlerThread(getClass().getSimpleName());
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mAdapter = new LiveListAdapter(mLiveListBean);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.content_layout, container, false);
        mTwinklingRefreshLayout = (TwinklingRefreshLayout) mView.findViewById(R.id.refreshlayout);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mAdapter.setEmptyView(R.layout.empty_layout, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
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
                if (mLiveListBean.size() > 0) {
                    mLiveListBean.clear();

                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getLiveList(offset, LiveAPI.LIMIT, "", mGameType);
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
                        presenter.getLiveList(offset, LiveAPI.LIMIT, "", mGameType);
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
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveListItemBean liveItemBean = (LiveListItemBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), LivePlayActivity.class);
                intent.putExtra(LivePlayActivity.LIVE_TYPE, liveItemBean.getLive_type());
                intent.putExtra(LivePlayActivity.LIVE_RoomID, liveItemBean.getLive_id());
                intent.putExtra(LivePlayActivity.GAME_TYPE, liveItemBean.getGame_type());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
//                FragmentManager fm =getParentFragment().getFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                Fragment mLivePlayFragment = LivePlayFragment.newInstance(
//                        liveItemBean.getLive_type(),
//                        liveItemBean.getLive_id(),
//                        liveItemBean.getGame_type());
//                if (!mLivePlayFragment.isAdded()) {
//                    transaction.add(R.id.relativeLayout_container,mLivePlayFragment);
//                }
//                transaction.addToBackStack(null);
//                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (null != mGameType) {
            mGameType = null;
        }
        if (null != mLiveListBean) {
            mLiveListBean.clear();
            mLiveListBean = null;
        }
        if (null != mView) {
            mView = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public LiveListFragment() {
        // Required empty public constructor
    }

    @Override
    protected LiveListPresenterImpl initPresenter() {
        return new LiveListPresenterImpl();
    }

    public static LiveListFragment newInstance(String param) {
        LiveListFragment fragment = new LiveListFragment();
        Bundle args = new Bundle();
        args.putString(CONTENTFRAGMENT_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showError(String message) {
        //在加载失败的时候调用showLoadMoreFailedView()就能显示加载失败的footer了，点击footer会重新加载
        ToastUtil.showToast(message);
    }

    @Override
    public void updateRecyclerView(List<LiveListItemBean> liveListBeanList) {
        mAdapter.addData(liveListBeanList);
        offset = mAdapter.getData().size();
        if (offset < NewsAPI.LIMIT) {//分页数据size比每页数据的limit小，说明已全部加载数据
            mTwinklingRefreshLayout.finishLoadmore();
        }
    }
}
