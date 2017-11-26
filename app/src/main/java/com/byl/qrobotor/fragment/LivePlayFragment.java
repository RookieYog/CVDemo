package com.byl.qrobotor.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.byl.qrobotor.R;
import com.byl.qrobotor.adapter.LivePlayChatAdapter;
import com.byl.qrobotor.base.BaseFragment;
import com.byl.qrobotor.bean.DanmuBean;
import com.byl.qrobotor.bean.LiveDetailBean;
import com.byl.qrobotor.http.exception.MediaException;
import com.byl.qrobotor.presenter.BasePresenter;
import com.byl.qrobotor.util.ScreenUtils;
import com.byl.qrobotor.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

import static com.byl.qrobotor.R.id.et_danmu_landscape;
import static com.byl.qrobotor.R.id.iv_danmu_visible_landscape;
import static com.byl.qrobotor.R.id.iv_danmu_visible_portrait;
import static com.byl.qrobotor.R.id.iv_play_pause_landscape;

/**
 * Created by acer on 2017/7/23.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFragment } interface
 * to handle interaction events.OnFragmentInteractionListener
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LivePlayFragment extends BaseFragment implements View.OnClickListener, Handler.Callback,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnErrorListener {
    private static final String LIVE_TYPE = "live_type"; //直播平台
    private static final String LIVE_RoomID = "live_id";     //直播房间ID
    private static final String GAME_TYPE = "game_type"; //直播游戏类型
    private String mLIVE_TYPE; //直播平台
    private String mLIVE_RoomID;  //直播房间号ID
    private String mGameType; //直播游戏类型
    private String mUrl; //直播url

    private View mView;
    private View mViewPortrait;//竖屏
    private View mViewLandscape;//横屏
    private View mSendViewPortrait;//横屏发送弹幕布局
    private Context mContext;
    private JCVideoPlayerStandard mJCVideo;
    private DanmakuView mDanmakuView;
    private DanmakuContext mDanmakuContext;
    private ProgressBar mProgressBar;
    private CircleImageView mBackPortrait;
    private CircleImageView mDanmuVisiblePortrait;
    private CircleImageView mFullScreenPortrait;
    private EditText mEditDanmaku;
    private Button mSendDanmaku;

    private CircleImageView mBackLandscape;
    private CircleImageView mDanmuVisibleLandscape;
    private CircleImageView mQuitScreenLandscape;
    private CircleImageView mPauseLandscape;
    private CircleImageView mRefreshLandscape;
    private EditText mEditDanmakuLandscape;
    private Button mSendDanmakuLandscape;

    private ImageView thumbImageView;
    private RecyclerView mRecyclerView;
    private SurfaceView mSurfaceView;

    private CircleImageView mUserHeaderPic;
    private TextView mUserNameTv;
    private TextView mLiveTypeTv;
    private TextView mGameTypeTv;

    private boolean DanmaKuFlag = false;
    private boolean isSurfaceViewInit = false;         //SurfaceView初始化标志位
    private boolean isVideoPrepared = false;         //Video加载标志位，用于显示隐藏ProgreeBar
    private boolean isPause = false;         //直播暂停标志位
    private boolean isFullscreen = false;   //全屏标志位
    private boolean isControllerHiden = false;   //MediaController显示隐藏标志位
    private int surfacePortraitWidth;
    private int surfacePortraitHeight;
    private int videoWidth;
    private int videoHeight;
    private int playWidth;
    private int playHeight;
    private List<LiveDetailBean.StreamListBean> streamList = new ArrayList<>();//直播流列表
    private int current;
    private MediaPlayer mMediaPlayer;  //媒体控制器
    private Handler controllerHandler;
    private static final int HANDLER_HIDE_CONTROLLER = 100;//隐藏MediaController
    private static final int HANDLER_CONTROLLER_DURATION = 5 * 1000;//MediaController显示时间
    private CardView mExCachangeLayout;
    private View mUerRelativeLayout;
    private View mLiveBottom;
    private onFullScreenListener mListener;
    private static final String KEY = "key";
    private static final int DANMU_LIMIT = 30;
    private List<DanmuBean> danmuList = new ArrayList<>();
    private LivePlayChatAdapter adapter;


    public LivePlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mContext = context;
        } else {
            mContext = getActivity();
        }
        mListener = (onFullScreenListener) mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLIVE_TYPE = getArguments().getString(LIVE_TYPE);
            mLIVE_RoomID = getArguments().getString(LIVE_RoomID);
            mGameType = getArguments().getString(GAME_TYPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.item_live_paly, container, false);
        mExCachangeLayout = (CardView) mView.findViewById(R.id.view_top);
        mSurfaceView = (SurfaceView) mView.findViewById(R.id.surfaceview);
        mDanmakuView = (DanmakuView) mView.findViewById(R.id.danmuview);
//        thumbImageView = (ImageView) mView.findViewById(R.id.thumbImageView);

        //竖屏
        mViewPortrait = mView.findViewById(R.id.view_portrait);
        mBackPortrait = (CircleImageView) mViewPortrait.findViewById(R.id.iv_back_portrait);
        mDanmuVisiblePortrait = (CircleImageView) mViewPortrait.findViewById(iv_danmu_visible_portrait);
        mFullScreenPortrait = (CircleImageView) mViewPortrait.findViewById(R.id.iv_fullscreen_portrait);

        //横屏
        mViewLandscape = mView.findViewById(R.id.view_landscape);
        mBackLandscape = (CircleImageView) mViewLandscape.findViewById(R.id.iv_back_landscape);
        mDanmuVisibleLandscape = (CircleImageView) mViewLandscape.findViewById(iv_danmu_visible_landscape);
        mQuitScreenLandscape = (CircleImageView) mViewLandscape.findViewById(R.id.iv_fullscreen_exit_landscape);
        mPauseLandscape = (CircleImageView) mViewLandscape.findViewById(iv_play_pause_landscape);
        mRefreshLandscape = (CircleImageView) mViewLandscape.findViewById(R.id.iv_refresh_landscape);
        mEditDanmakuLandscape = (EditText) mViewLandscape.findViewById(et_danmu_landscape);
        mSendDanmakuLandscape = (Button) mViewLandscape.findViewById(R.id.btn_send_landscape);

        //主播资料
        mUerRelativeLayout = mView.findViewById(R.id.userinfolayout);
        mUserHeaderPic = (CircleImageView) mView.findViewById(R.id.userpic);
        mUserNameTv = (TextView) mView.findViewById(R.id.username);
        mLiveTypeTv = (TextView) mView.findViewById(R.id.livetype);
        mGameTypeTv = (TextView) mView.findViewById(R.id.gametype);

        mSendViewPortrait = mView.findViewById(R.id.send_danmu_portrait);
        mEditDanmaku = (EditText) mSendViewPortrait.findViewById(R.id.edit_danmu);
        mSendDanmaku = (Button) mSendViewPortrait.findViewById(R.id.btn_send);

//        mJCVideo = (JCVideoPlayerStandard) mView.findViewById(R.id.videoplayer);
//        mCircleView = (CircleImageView) mView.findViewById(R.id.danmu_visible);
        //mViewLandscape=mView.findViewById(R.id.danmu_landscape);
//        mLiveAnchor= (TextView) mView.findViewById(R.id.live_anchor);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.live_progressbar);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview_danmu);

        mLiveBottom = mView.findViewById(R.id.live_bottom);
//        mJCVideo.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
//        Glide.with(mContext)
//                .load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
//                .into(mJCVideo.thumbImageView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        controllerHandler=new Handler(this);
        //SurfaceView监听回调
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mProgressBar.setVisibility(View.VISIBLE);
                preparemMediaPlayer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (!isSurfaceViewInit) {
                    //竖屏
                    surfacePortraitWidth = width;
                    surfacePortraitHeight = height;

                    isSurfaceViewInit = true;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.setDisplay(null);
                }
            }
        });

        //MediaController
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        mDanmakuView.enableDanmakuDrawingCache(true);
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                try {
                    mDanmakuView.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        mDanmakuContext = DanmakuContext.create();
        mDanmakuView.prepare(new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        }, mDanmakuContext);
        mDanmakuContext.setDuplicateMergingEnabled(true);
        controllerHandler = new Handler(this);
        //设置RecyclerView
        adapter = new LivePlayChatAdapter(danmuList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 配置mMediaPlayer相关参数
     */
    private void preparemMediaPlayer() {

        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            return;
        }

        try {
//            AVOptions avOptions = new AVOptions();
//            avOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);  //直播流：1->是 0->否
//            avOptions.setInteger(AVOptions.KEY_MEDIACODEC, 0);      //解码类型 1->硬解 0->软解
//            avOptions.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);//缓冲结束后自动播放
//            avOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
//            avOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
//            avOptions.setInteger(AVOptions.KEY_BUFFER_TIME, 10 * 1000);
//            avOptions.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
//            avOptions.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 10 * 1000);
//            avOptions.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 15 * 1000);

//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            mMediaPlayer = new MediaPlayer();

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnInfoListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);
//            mMediaPlayer.setDataSource(mUrl);
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateLiveDetail(LiveDetailBean detailBean) {
        try {
            mUserNameTv.setText(detailBean.getLive_title());
            streamList = detailBean.getStream_list();
            LiveDetailBean.StreamListBean stream = streamList.get(streamList.size() - 1);
            mUrl = stream.getUrl();
        } catch (NullPointerException e) {
            e.printStackTrace();
            mUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        }
        try {
            mMediaPlayer.setDataSource(mUrl);//加载直播链接进行播放
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把LiveDetail传入主播信息
//        updateLiveDetail(detailBean);
    }

//    @Override
//    public void updateChatDetail(LivePandaBean pandaBean) {
//        presenter.connectToChatRoom(live_id, pandaBean);
//    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        //弹幕烈焰使
        if (mViewLandscape.getVisibility() == View.VISIBLE) {
            mDanmuVisibleLandscape.setSelected(DanmaKuFlag);
        }
        if (mViewPortrait.getVisibility() == View.VISIBLE) {
            mDanmuVisiblePortrait.setSelected(DanmaKuFlag);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.keepScreenOn(true);
        }
        initListener();
        if (mMediaPlayer != null && isPause && !TextUtils.isEmpty(mUrl)) {
            try {
//                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(mUrl);
                mMediaPlayer.prepareAsync();
                isPause = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    private void initListener() {
        mBackPortrait.setOnClickListener(this);
        mDanmuVisiblePortrait.setOnClickListener(this);
        mFullScreenPortrait.setOnClickListener(this);

        mBackLandscape.setOnClickListener(this);
        mDanmuVisibleLandscape.setOnClickListener(this);
        mQuitScreenLandscape.setOnClickListener(this);
        mPauseLandscape.setOnClickListener(this);
        mRefreshLandscape.setOnClickListener(this);
        mEditDanmakuLandscape.setOnClickListener(this);
        mSendDanmakuLandscape.setOnClickListener(this);
        mEditDanmaku.setOnClickListener(this);
        mSendDanmaku.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.abandonAudioFocus(null);
        }
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }

        if (mListener != null) {
            mListener.keepScreenOn(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.abandonAudioFocus(null);
        }
        //断开弹幕服务器连接
//        presenter.closeConnection();

        //关闭弹幕
        DanmaKuFlag = false;
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public static LivePlayFragment newInstance(String liveType, String liveRoomID, String gameType) {
        LivePlayFragment fragment = new LivePlayFragment();
        Bundle args = new Bundle();
        args.putString(LIVE_TYPE, liveType);
        args.putString(LIVE_RoomID, liveRoomID);
        args.putString(GAME_TYPE, gameType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.iv_back_portrait) {
            //竖屏时退出操作
            getFragmentManager().popBackStackImmediate();

        } else if (id == iv_danmu_visible_portrait) {
            //判断竖屏弹幕是否显示
            showDanmu(view);

        } else if (id == R.id.iv_fullscreen_portrait) {
            //判断竖屏切换横屏
            enterFullscreen();

        } else if (id == R.id.iv_back_landscape) {
            //判断横屏退出操作
            exitFullscreen();

        } else if (id == iv_play_pause_landscape) {
            //判断横屏时是否暂停
            if (isPause) {
                onResume();
            } else if (!isPause) {
                onPause();
            }
            mPauseLandscape.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
            controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
            controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        } else if (id == iv_danmu_visible_landscape) {
            //判断横屏时弹幕是否显示
            showDanmu(view);

        } else if (id == R.id.iv_fullscreen_exit_landscape) {
            //判断横屏时是退出全屏
            exitFullscreen();

        } else if (id == R.id.iv_refresh_landscape) {
            //判断横屏时刷新
            try {
                mMediaPlayer.reset();

                isVideoPrepared = false;
                mProgressBar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);

                mMediaPlayer.setDataSource(mUrl);//加载直播链接进行播放
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
            controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
            controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);


        } else if (id == et_danmu_landscape) {
            //判断横屏时编辑弹幕
            //点击弹幕编辑时取消隐藏Controller
            controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);

        } else if (id == R.id.btn_send_landscape) {
            //判断横屏时发送弹幕
            controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
            controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
            if (!DanmaKuFlag) {
                ToastUtil.showToast("弹幕已关闭，无法发送弹幕");
                return;
            }
            if (TextUtils.isEmpty(mEditDanmakuLandscape.getText())) {
                ToastUtil.showToast("发送弹幕内容不能为空");
                return;
            }
            DanmuBean danmuBean = new DanmuBean();
            DanmuBean.DataBean dataBean = new DanmuBean.DataBean();
            DanmuBean.DataBean.FromBean fromBean = new DanmuBean.DataBean.FromBean();
            fromBean.setNickName("我");
            fromBean.setUserName("我");
            dataBean.setFrom(fromBean);
            dataBean.setContent(mEditDanmakuLandscape.getText().toString());
            danmuBean.setData(dataBean);
            addDanmu(danmuBean);
            mEditDanmakuLandscape.setText(null);

        } else if (id == R.id.edit_danmu) {
            //判断竖屏时编辑弹幕

        } else if (id == R.id.btn_send) {
            //判断竖屏时发送弹幕
            if (!DanmaKuFlag) {
                ToastUtil.showToast("弹幕已关闭，无法发送弹幕");
                return;
            }
            if (TextUtils.isEmpty(mEditDanmaku.getText())) {
                ToastUtil.showToast("发送弹幕内容不能为空");
                return;
            }
            DanmuBean danmuBean = new DanmuBean();
            DanmuBean.DataBean dataBean = new DanmuBean.DataBean();
            DanmuBean.DataBean.FromBean fromBean = new DanmuBean.DataBean.FromBean();
            fromBean.setNickName("我");
            fromBean.setUserName("我");
            dataBean.setFrom(fromBean);
            dataBean.setContent(mEditDanmaku.getText().toString());
            danmuBean.setData(dataBean);
            addDanmu(danmuBean);
            mEditDanmaku.setText(null);

        }
    }

    //控制弹幕状态
    private void showDanmu(View view) {
        if (DanmaKuFlag) {
            view.setSelected(DanmaKuFlag);
            DanmaKuFlag = false;
            ToastUtil.showToast("弹幕已开启");
        } else if (!DanmaKuFlag) {
            view.setSelected(DanmaKuFlag);
            DanmaKuFlag = true;
            ToastUtil.showToast("弹幕已关闭");
        }
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
    }

    //控制弹幕状态
    private void addDanmu(DanmuBean danmuBean) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = danmuBean.getData().getContent();
        danmaku.padding = 5;
        danmaku.textSize = 18.0F;
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(mDanmakuView.getCurrentTime());
        if (true) {
            danmaku.borderColor = Color.GREEN;
        }
        mDanmakuView.addDanmaku(danmaku);
        addDanmuOnRecyclerView(danmuBean);
    }

    public void addDanmuOnRecyclerView(DanmuBean danmuBean) {

        if (adapter == null) {
            return;
        }
        if (adapter.getData().size() >= DANMU_LIMIT) {
            adapter.remove(0);
        }
        adapter.add(adapter.getData().size(), danmuBean);
        mRecyclerView.scrollToPosition(adapter.getData().size() - 1);
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == HANDLER_HIDE_CONTROLLER) {
            //hide controller
            mViewLandscape.setVisibility(View.GONE);
            mViewPortrait.setVisibility(View.GONE);
            isControllerHiden = true;
        }
        return true;
    }

    //退出全屏
    private void exitFullscreen() {
        mExCachangeLayout.removeView(mSurfaceView);
        mExCachangeLayout.removeView(mProgressBar);
        mExCachangeLayout.removeView(mDanmakuView);
        mExCachangeLayout.removeView(mViewPortrait);
        mExCachangeLayout.removeView(mViewLandscape);

//        ScreenUtils.setPortrait(this);
        ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        isFullscreen = false;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        mExCachangeLayout.addView(mSurfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mProgressBar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mDanmakuView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mViewPortrait, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mViewLandscape, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        onVideoSizeChanged(mMediaPlayer, videoWidth, videoHeight);
        if (mListener != null) mListener.setFullScreen(false);
        mLiveBottom.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
        mViewLandscape.setVisibility(View.GONE);
        mViewPortrait.setVisibility(View.VISIBLE);
    }

    //进入全屏
    private void enterFullscreen() {

        mExCachangeLayout.removeView(mSurfaceView);
        mExCachangeLayout.removeView(mProgressBar);
        mExCachangeLayout.removeView(mDanmakuView);
        mExCachangeLayout.removeView(mViewPortrait);
        mExCachangeLayout.removeView(mViewLandscape);

//        ScreenUtils.setLandscape(this);
        ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        isFullscreen = true;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        mExCachangeLayout.addView(mSurfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mProgressBar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mDanmakuView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mViewPortrait, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExCachangeLayout.addView(mViewLandscape, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (mListener != null) mListener.setFullScreen(true);
        mLiveBottom.setVisibility(View.GONE);
//        mProgressBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
        mViewLandscape.setVisibility(View.VISIBLE);
        mViewPortrait.setVisibility(View.GONE);
        mPauseLandscape.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        showError(new MediaException(i).getMessage());
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        switch (i) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START://开始缓冲
//                isVideoPrepared = false;
                Log.d("MediaPlayer", "onInfo: MEDIA_INFO_BUFFERING_START");
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END://缓冲结束
                Log.d("MediaPlayer", "onInfo: MEDIA_INFO_BUFFERING_END");
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE://.MEDIA_INFO_BUFFERING_BYTES_UPDATE
                Log.d("MediaPlayer", "onInfo: MEDIA_INFO_BUFFERING_BYTES_UPDATE");
                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                Log.d("MediaPlayer", "onInfo: MEDIA_INFO_NOT_SEEKABLE");
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START://视频缓冲完成可播放
                mProgressBar.setVisibility(View.GONE);
                isVideoPrepared = true;
                isPause = false;
                mPauseLandscape.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
                Log.d("MediaPlayer", "onInfo: MEDIA_INFO_VIDEO_RENDERING_START");
                break;
            default:
                Log.d("MediaPlayer", "onInfo: " + i);
                break;
        }
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mProgressBar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
        mediaPlayer.start();
    }


    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
        videoWidth = mediaPlayer.getVideoWidth();
        videoHeight = mediaPlayer.getVideoHeight();

        if (videoWidth != 0 && videoHeight != 0) {
            float ratioW = (float) videoWidth / (float) (isFullscreen ? ScreenUtils.getScreenWidth() : surfacePortraitWidth);
            float ratioH = (float) videoHeight / (float) (isFullscreen ? ScreenUtils.getScreenHeight() : surfacePortraitHeight);
            float ratio = Math.max(ratioW, ratioH);
            playWidth = (int) Math.ceil((float) videoWidth / ratio);
            playHeight = (int) Math.ceil((float) videoHeight / ratio);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(playWidth, playHeight);
            lp.gravity = Gravity.CENTER;
            mSurfaceView.setLayoutParams(lp);
        }
    }

    public   interface onFullScreenListener {

        void setFullScreen(boolean fullScreen);

        void keepScreenOn(boolean keep);
    }
}
