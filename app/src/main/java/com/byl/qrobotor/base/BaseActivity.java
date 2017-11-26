package com.byl.qrobotor.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.byl.qrobotor.presenter.BasePresenter;


/**
 * Created by acer on 2017/4/10.
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    public P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = initPresenter();
        if (null != presenter) {
            presenter.attachView((V) this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (null != presenter) {
            presenter.detachView();
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected abstract P initPresenter();

}
