package com.byl.qrobotor.bean.jokebean;

import java.util.List;

/**
 * Created by acer on 2017/6/6.
 */

public class JokeTextGroupBean {
    private String text;
    private List<DisLikeReasonBean> dislike_reason;
    private double create_time;
    private int id;
    private int favorite_count;
    private int go_detail_count;
    private int user_favorite;
    private int share_type;
    private UserBean user;//用户信息
    private int is_can_share;
    private int category_type;
    private String share_url;//分享链接
    private int label;
    private String content;//段子内容
    private int comment_count;
    private String id_str;
    private int media_type;
    private int share_count;
    private int type;
    private int status;
    private int has_comments;
    private int user_bury;
    private String status_desc;
    private int display_type;
    private int user_digg;
    private double online_time;
    private String category_name;//段子类型名称
    private boolean category_visible;
    private int bury_count;
    private boolean is_anonymous;
    private int repin_count;
    private int digg_count;
    private int has_hot_comments;
    private boolean allow_dislike;
    private int user_repin;
    private JokeActivity jokeActivity;
    private double group_id;
    private int category_id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<DisLikeReasonBean> getDislike_reason() {
        return dislike_reason;
    }

    public void setDislike_reason(List<DisLikeReasonBean> dislike_reason) {
        this.dislike_reason = dislike_reason;
    }

    public double getCreate_time() {
        return create_time;
    }

    public void setCreate_time(double create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getGo_detail_count() {
        return go_detail_count;
    }

    public void setGo_detail_count(int go_detail_count) {
        this.go_detail_count = go_detail_count;
    }

    public int getUser_favorite() {
        return user_favorite;
    }

    public void setUser_favorite(int user_favorite) {
        this.user_favorite = user_favorite;
    }

    public int getShare_type() {
        return share_type;
    }

    public void setShare_type(int share_type) {
        this.share_type = share_type;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getIs_can_share() {
        return is_can_share;
    }

    public void setIs_can_share(int is_can_share) {
        this.is_can_share = is_can_share;
    }

    public int getCategory_type() {
        return category_type;
    }

    public void setCategory_type(int category_type) {
        this.category_type = category_type;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHas_comments() {
        return has_comments;
    }

    public void setHas_comments(int has_comments) {
        this.has_comments = has_comments;
    }

    public int getUser_bury() {
        return user_bury;
    }

    public void setUser_bury(int user_bury) {
        this.user_bury = user_bury;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public int getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(int display_type) {
        this.display_type = display_type;
    }

    public int getUser_digg() {
        return user_digg;
    }

    public void setUser_digg(int user_digg) {
        this.user_digg = user_digg;
    }

    public double getOnline_time() {
        return online_time;
    }

    public void setOnline_time(double online_time) {
        this.online_time = online_time;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isCategory_visible() {
        return category_visible;
    }

    public void setCategory_visible(boolean category_visible) {
        this.category_visible = category_visible;
    }

    public int getBury_count() {
        return bury_count;
    }

    public void setBury_count(int bury_count) {
        this.bury_count = bury_count;
    }

    public boolean is_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public int getRepin_count() {
        return repin_count;
    }

    public void setRepin_count(int repin_count) {
        this.repin_count = repin_count;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public int getHas_hot_comments() {
        return has_hot_comments;
    }

    public void setHas_hot_comments(int has_hot_comments) {
        this.has_hot_comments = has_hot_comments;
    }

    public boolean isAllow_dislike() {
        return allow_dislike;
    }

    public void setAllow_dislike(boolean allow_dislike) {
        this.allow_dislike = allow_dislike;
    }

    public int getUser_repin() {
        return user_repin;
    }

    public void setUser_repin(int user_repin) {
        this.user_repin = user_repin;
    }

    public JokeActivity getJokeActivity() {
        return jokeActivity;
    }

    public void setJokeActivity(JokeActivity jokeActivity) {
        this.jokeActivity = jokeActivity;
    }

    public double getGroup_id() {
        return group_id;
    }

    public void setGroup_id(double group_id) {
        this.group_id = group_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
