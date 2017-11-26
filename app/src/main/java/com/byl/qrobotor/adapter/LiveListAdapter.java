package com.byl.qrobotor.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.byl.qrobotor.R;
import com.byl.qrobotor.bean.LiveListItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * author:lw
 * date: 2016/9/12
 * 直播房间列表的Adapter
 */
public class LiveListAdapter extends BaseQuickAdapter<LiveListItemBean, BaseViewHolder> {
    public LiveListAdapter(List<LiveListItemBean> data) {
        super(R.layout.item_live_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, LiveListItemBean bean) {
        viewHolder.setText(R.id.tv_roomname, bean.getLive_title())//房间名称
                .setText(R.id.tv_nickname, bean.getLive_nickname())//主播昵称
                .setText(R.id.tv_online, String.valueOf(bean.getLive_online()));//在线人数

        Glide.with(mContext)//直播房间截图
                .load(bean.getLive_img())
                .crossFade()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) viewHolder.getView(R.id.iv_roomsrc));

        Glide.with(mContext)//主播头像
                .load(bean.getLive_userimg())
                .crossFade()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) viewHolder.getView(R.id.iv_avatar));
    }
}
