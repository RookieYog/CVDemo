package com.byl.qrobotor.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.byl.qrobotor.R;
import com.byl.qrobotor.bean.NewsListItemBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/4.
 */

public class NewsListAdapter extends BaseMultiItemQuickAdapter<NewsListItemBean, BaseViewHolder> {

    public NewsListAdapter(List<NewsListItemBean> data) {
        super(data);
        addItemType(NewsListItemBean.ADS, R.layout.item_news_list_ads_layout);
        addItemType(NewsListItemBean.NORMAL, R.layout.item_news_list_normal_layout);
    }

    @Override
    protected void convert(final BaseViewHolder viewHolder, final NewsListItemBean bean) {

        switch (viewHolder.getItemViewType()) {
            case NewsListItemBean.ADS:
                Banner banner = viewHolder.getView(R.id.card_banner);
                List<String> viewList = new ArrayList<>();
                List<String> titleList = new ArrayList<>();
                viewList.add(bean.getImgsrc());
                if (bean.getAds() != null) {
                    for (NewsListItemBean.AdsBean ads : bean.getAds()) {
                        viewList.add(ads.getImgsrc());
                    }
                }
                for (int i = 0; i < viewList.size(); i++) {
                    titleList.add(bean.getTitle());
                }
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                banner.setIndicatorGravity(BannerConfig.RIGHT);
                banner.setImages(viewList);
                banner.setBannerTitles(titleList);
                banner.setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        //用其它图片作为缩略图
                        imageView.setBackgroundResource(R.drawable.ic_img_default);
                        Glide.with(mContext)
                                .load(path)
                                .crossFade()
                                .centerCrop()
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .error(null)
                                .into(imageView);
                    }
                });
                banner.start();
                break;
            case NewsListItemBean.NORMAL:
                viewHolder.setText(R.id.tv_title, bean.getTitle())
                        .setText(R.id.tv_source, bean.getSource());
                ImageView imageView = viewHolder.getView(R.id.iv_img);
                imageView.setBackgroundResource(R.drawable.ic_img_default);
                Glide.with(mContext)
                        .load(bean.getImgsrc())
                        .crossFade()
                        .centerCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
