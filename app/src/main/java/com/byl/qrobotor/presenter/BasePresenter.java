package com.byl.qrobotor.presenter;

import java.lang.ref.WeakReference;

/**
 * presenter层接口
 * Created by acer on 2017/4/10.
 */

public class BasePresenter<V> {
    //View层引用
    protected WeakReference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }


    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public void toDo() {
    }
}
