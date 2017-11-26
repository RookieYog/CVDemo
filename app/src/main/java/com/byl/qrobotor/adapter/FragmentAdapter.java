package com.byl.qrobotor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * author: lw
 * date: 2017/2/27
 * desc: FragmentAdapter用于管理FragmentViewPager
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentArray;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentArray) {
        super(fm);
        this.mFragmentArray = fragmentArray;
    }

    public void addFragment(Fragment fragment) {
        mFragmentArray.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mFragmentArray ? 0 : mFragmentArray.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArray.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

   }
