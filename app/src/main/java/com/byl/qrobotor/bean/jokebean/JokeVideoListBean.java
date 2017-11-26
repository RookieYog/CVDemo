package com.byl.qrobotor.bean.jokebean;

/**
 * Created by acer on 2017/6/5.
 */

public class JokeVideoListBean {
    private String message;
    private DataVideoBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataVideoBean getData() {
        return data;
    }

    public void setData(DataVideoBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JokeVideoListBean{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
