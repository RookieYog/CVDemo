package com.byl.qrobotor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byl.qrobotor.R;
import com.byl.qrobotor.adapter.FragmentAdapter;
import com.byl.qrobotor.adapter.IndicatorNavigatorAdapter;
import com.byl.qrobotor.base.BaseFragment;
import com.byl.qrobotor.presenter.BasePresenter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFragment } interface
 * to handle interaction events.OnFragmentInteractionListener
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends BaseFragment {
    private View mView;
    private MagicIndicator mMagicIndicator;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;
    private CommonNavigator mCommonNavigator;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context!=null){
            mContext=context;
        }else{
            mContext=getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.comment_layout, container, false);
        mMagicIndicator = (MagicIndicator) mView.findViewById(R.id.magic_indicator);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);

        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        String[] titleArray = mContext.getResources().getStringArray(R.array.live_game_name);
        for (String id : titleArray) {
            mFragmentTitleList.add(id);
        }
        String[] gameIdArray = mContext.getResources().getStringArray(R.array.live_game_id);
        for (String gameId : gameIdArray) {
            mFragmentList.add(LiveListFragment.newInstance(gameId));
        }
        mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mCommonNavigator = new CommonNavigator(mContext);
        mCommonNavigator.setSmoothScroll(true);
        mCommonNavigator.setFollowTouch(true);
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setAdapter(new IndicatorNavigatorAdapter(mViewPager, mFragmentTitleList));
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void showError(String message) {

    }
}
