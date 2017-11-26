package com.byl.qrobotor.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.byl.qrobotor.R;
import com.byl.qrobotor.bean.DanmuBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author: lw 17/7/7
 * 直播弹幕聊天室的Adapter
 */
public class LivePlayChatAdapter extends BaseQuickAdapter<DanmuBean, BaseViewHolder> {
    public LivePlayChatAdapter(List<DanmuBean> data) {
        super(R.layout.item_live_play_chat, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DanmuBean bean) {

        String name = TextUtils.isEmpty(bean.getData().getFrom().getNickName()) ? bean.getData().getFrom().getUserName() : bean.getData().getFrom().getNickName();
        if (TextUtils.isEmpty(name)) {
            name = "游客";
        }
        String content = bean.getData().getContent();
        viewHolder.setText(R.id.tv_nickname, name)//昵称
                .setText(R.id.tv_content, content);//弹幕内容
        if (!TextUtils.isEmpty(name) && mContext != null) {
            if (name.equals("我")) {
                viewHolder.setTextColor(R.id.tv_nickname, ContextCompat.getColor(mContext, R.color.colorAccent));
            }
        }
    }
}
