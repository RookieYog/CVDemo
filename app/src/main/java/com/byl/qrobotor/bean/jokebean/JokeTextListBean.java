package com.byl.qrobotor.bean.jokebean;

/**
 * Created by acer on 2017/6/5.
 */

public class JokeTextListBean {
    private String message;
    private DataTextBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataTextBean getData() {
        return data;
    }

    public void setData(DataTextBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JokeTextListBean{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
