package com.byl.qrobotor.view;


import com.byl.qrobotor.bean.DanmuBean;
import com.byl.qrobotor.bean.LiveDetailBean;
import com.byl.qrobotor.bean.LivePandaBean;
import com.byl.qrobotor.http.exception.IErrorView;

/**
 * author: lw
 * date: 2016/9/20
 */
public interface ILivePlayView extends IErrorView {
    void updateLiveDetail(LiveDetailBean detailBean);//更新直播详情

    void updateChatDetail(LivePandaBean detailPandaBean);//更新熊猫弹幕聊天室详情

    void receiveDanmu(DanmuBean danmuBean);//添加弹幕
}
