package com.byl.qrobotor.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.byl.qrobotor.R;
import com.byl.qrobotor.bean.jokebean.InnerDataTextGroupBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author:lw
 * date: 2016/12/5
 */
public class JokeTextAdapter extends BaseQuickAdapter<InnerDataTextGroupBean, BaseViewHolder> {

    public JokeTextAdapter(List<InnerDataTextGroupBean> data) {
        super(R.layout.item_joke_list_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InnerDataTextGroupBean item) {
        Glide.with(helper.getConvertView().getContext())
                .load(item.getGroup().getUser().getAvatar_url())
                .crossFade()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) helper.getView(R.id.joke_circleImage));
        helper.setText(R.id.joke_username, item.getGroup().getUser().getName());
        helper.setText(R.id.joke_content, item.getGroup().getContent());
        helper.setText(R.id.joke_category_name, item.getGroup().getCategory_name());
    }
}
