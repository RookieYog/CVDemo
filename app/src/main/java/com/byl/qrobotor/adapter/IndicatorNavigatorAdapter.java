package com.byl.qrobotor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 * Created by acer on 2017/6/3.
 */

public class IndicatorNavigatorAdapter extends CommonNavigatorAdapter {
    private List<String> mTitle;
    private ViewPager mViewPager;
    public IndicatorNavigatorAdapter(ViewPager viewPager, List<String> title) {
        this.mViewPager = viewPager;
        this.mTitle = title;
    }

    @Override
    public int getCount() {
        return null == mTitle ? 0 : mTitle.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int i) {
        final int tempIndex = i;
        SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
        simplePagerTitleView.setNormalColor(Color.GRAY);
        simplePagerTitleView.setSelectedColor(Color.WHITE);
        simplePagerTitleView.setTextSize(12);
        simplePagerTitleView.setText(mTitle.get(tempIndex));
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(tempIndex);
            }
        });
        return simplePagerTitleView;
    }


    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
        linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(1.0f));
        linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3));
        linePagerIndicator.setColors(Color.WHITE);
        return linePagerIndicator;
    }

}
