package com.byl.qrobotor.bean.jokebean;

/**
 * Created by acer on 2017/6/6.
 */

public class UserBean {
    private int user_id;
    private String name;//用户昵称
    private int followings;
    private boolean user_verified;
    private String avatar_url;//用户头像链接
    private int followers;
    private boolean is_following;
    private boolean is_pro_user;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public boolean isUser_verified() {
        return user_verified;
    }

    public void setUser_verified(boolean user_verified) {
        this.user_verified = user_verified;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean is_following() {
        return is_following;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }

    public boolean is_pro_user() {
        return is_pro_user;
    }

    public void setIs_pro_user(boolean is_pro_user) {
        this.is_pro_user = is_pro_user;
    }
}
