package com.byl.qrobotor.bean.jokebean;

import java.util.List;

/**
 * Created by acer on 2017/6/6.
 */

public class InnerDataTextGroupBean {
    private JokeTextGroupBean group;
    private List<CommentsBean> comments;
    private int type;
    private double display_time;
    private double online_time;

    public JokeTextGroupBean getGroup() {
        return group;
    }

    public void setGroup(JokeTextGroupBean group) {
        this.group = group;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(double display_time) {
        this.display_time = display_time;
    }

    public double getOnline_time() {
        return online_time;
    }

    public void setOnline_time(double online_time) {
        this.online_time = online_time;
    }
}
