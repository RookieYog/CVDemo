package com.byl.qrobotor.model;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.DanmuBean;
import com.byl.qrobotor.bean.LiveDetailBean;
import com.byl.qrobotor.bean.LiveListItemBean;
import com.byl.qrobotor.bean.LivePandaBean;

import java.util.List;

/**
 * Created by acer on 2017/6/30.
 */

public interface ILivePlayModel {
    void getLiveDetail(String live_type, String live_id, String game_type,BaseOnLoadingListener<LiveDetailBean> listener);//请求直播详情

    void getDanmuDetail(String roomid, String live_type,BaseOnLoadingListener<LivePandaBean> listener);//请求弹幕聊天细节参数

    void connectToChatRoom(String live_id, LivePandaBean pandaBean,BaseOnLoadingListener<DanmuBean> listener);//连接弹幕聊天室

    void closeConnection();//断开连接

//    public interface IPaserDanmuku{
//        void receiveDanmu(DanmuBean danmuBean, boolean withBorder);//添加弹幕
//    }
}
