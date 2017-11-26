package com.byl.qrobotor.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.byl.qrobotor.R;
import com.byl.qrobotor.base.BaseActivity;
import com.byl.qrobotor.fragment.HomeFragment;
import com.byl.qrobotor.fragment.LivePlayFragment;
import com.byl.qrobotor.fragment.VideoFragment;
import com.byl.qrobotor.presenter.BasePresenter;


public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        LivePlayFragment.onFullScreenListener {
    private BottomNavigationView mBottomNavView;
    private Fragment mCurrentFragment;
    private HomeFragment mHomeFragment;
    private VideoFragment mVideoFragment;
    //private Fragment mHomeFragment;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        Banner banner = (Banner) findViewById(R.id.banner);
//        List<Integer> list = new ArrayList<>(2);
//        list.add(R.mipmap.ic_launcher);
//        list.add(R.mipmap.ic_launcher);
//        banner.setImages(list);
//        banner.setImageLoader(new ImageLoader() {
//            @Override
//            public void displayImage(Context mAppContext, Object path, ImageView imageView) {
//                Glide.with(mAppContext).load(path).into(imageView);
//            }
//        });
//        banner.start();
//        //http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8
//        // http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8
//        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
//        jcVideoPlayerStandard.setUp("http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8"
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
//        Glide.with(this)
//                .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
//                .into(jcVideoPlayerStandard.thumbImageView);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        initView();

    }

    private void initView() {
        if (null == mHomeFragment) {
            mHomeFragment = HomeFragment.newInstance();
        }
        if (null == mVideoFragment) {
            mVideoFragment = VideoFragment.newInstance();
        }
        mBottomNavView = (BottomNavigationView) findViewById(R.id.bottom_tab);
        mBottomNavView.setOnNavigationItemSelectedListener(this);
        mBottomNavView.setSelectedItemId(R.id.bottom_home);
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
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    // 使用当前Fragment的布局替代id_content的控件
    public void showFragment(@NonNull Fragment fragment) {
        if (fragment == mCurrentFragment) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (null != mCurrentFragment) {
            transaction.hide(mCurrentFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.main_container, fragment);
        } else {
            transaction.show(fragment);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commitAllowingStateLoss();
        mCurrentFragment = fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCurrentFragment) {
            mCurrentFragment = null;
        }
        if (null != mHomeFragment) {
            mHomeFragment = null;
        }
        if (null != mVideoFragment) {
            mVideoFragment = null;
        }
        if (null != mBottomNavView) {
            mBottomNavView.removeAllViews();
            mBottomNavView = null;
        }
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    /**
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //newConfig.orientation获得当前屏幕状态是横向或者竖向
        //Configuration.ORIENTATION_PORTRAIT 表示竖向
        //Configuration.ORIENTATION_LANDSCAPE 表示横屏
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(MainActivity.this, "现在是竖屏", Toast.LENGTH_SHORT).show();
        }
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(MainActivity.this, "现在是横屏", Toast.LENGTH_SHORT).show();
        }
    }
    //底部导航
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.bottom_home) {
            showFragment(mHomeFragment);
        }
        if (item.getItemId() == R.id.bottom_video) {
            showFragment(mVideoFragment);
        }
        if (item.getItemId() == R.id.bottom_chat) {
            Toast.makeText(MainActivity.this, "456", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void keepScreenOn(boolean keep) {
        if (keep) getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
