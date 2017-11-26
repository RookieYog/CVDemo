package com.byl.qrobotor.bean.jokebean;

import java.util.List;

/**
 * Created by acer on 2017/6/6.
 */

public class DataVideoBean {
    private boolean has_more;
    private String tip;
    private boolean has_new_message;
    private double max_time;
    private double min_time;
    private List<InnerDataVideoGroupBean> data;

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isHas_new_message() {
        return has_new_message;
    }

    public void setHas_new_message(boolean has_new_message) {
        this.has_new_message = has_new_message;
    }

    public double getMax_time() {
        return max_time;
    }

    public void setMax_time(double max_time) {
        this.max_time = max_time;
    }

    public double getMin_time() {
        return min_time;
    }

    public void setMin_time(double min_time) {
        this.min_time = min_time;
    }

    public List<InnerDataVideoGroupBean> getData() {
        return data;
    }

    public void setData(List<InnerDataVideoGroupBean> data) {
        this.data = data;
    }
}
