package com.byl.qrobotor.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.byl.qrobotor.R;
import com.byl.qrobotor.base.BaseFragment;
import com.byl.qrobotor.presenter.NewsDetailPresenterImpl;
import com.byl.qrobotor.util.ToastUtil;
import com.byl.qrobotor.view.INewsDetailView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.byl.qrobotor.R.id.webview;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment} interface
 * to handle interaction events..OnFragmentInteractionListener
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends BaseFragment<INewsDetailView, NewsDetailPresenterImpl> implements INewsDetailView{

    private static final String DOC_ID = "doc_id";
    private static final String TITLE = "title";
    private View mView;
    private Toolbar mToolBar;
    private TextView tvToolBarTitle;
    private WebView mWebView;
    private String mDOC_ID;
    private String mTITLE;
    private Context mActivity;
    private ProgressBar mProgressBar;
    private View mErrorView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mActivity = context;
        } else {
            mActivity = getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDOC_ID = getArguments().getString(DOC_ID);
            mTITLE = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.detail_layout, container, false);
        mToolBar = (Toolbar) mView.findViewById(R.id.toolbar);
        ((AppCompatActivity) mActivity).setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.ic_back_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getFragmentManager().popBackStackImmediate();
            }
        });
        tvToolBarTitle = (TextView) mView.findViewById(R.id.toolbar_title);
        mWebView = (WebView) mView.findViewById(webview);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        mErrorView=mView.findViewById(R.id.weberror_layout);
        initView();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDOC_ID != null) {
            mDOC_ID = null;
        }
        if (mTITLE != null) {
            mTITLE = null;
        }
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        if (mView != null) {
            mView = null;
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

    @SuppressLint("JavascriptInterface")
    private void initView() {
        //让WebView支持播放插件
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上
        mWebView.getSettings().setDisplayZoomControls(false);
        // 设置编码格式
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //设置脚本是否允许自动打开弹窗
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        presenter.getNewsDetail(mDOC_ID);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为fase调用系统浏览器或第三方浏览器
                mProgressBar.setVisibility(View.VISIBLE);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    view.stopLoading();
                    mProgressBar.setVisibility(View.GONE);
                } else if (url.startsWith("mailto:")) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                    startActivity(intent);
                    view.stopLoading();
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            //网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            //网页加载完毕
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                },500);
            }

            // 加载错误时要做的工作
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        //判断页面加载过程
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                tvToolBarTitle.setText("加载中，请稍候..." + newProgress + "%");
                if (newProgress < 100) {
                    // 网页加载中
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    // 网页加载完成
                    tvToolBarTitle.setText(""+mTITLE);
                    mProgressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            //设置标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    /**
     * //获取完整的域名
     *
     * @param text 获取浏览器分享出来的htmltext文本
     */
    public static String getCompleteUrl(String text) {
        Boolean flag = true;
        Matcher matcher;
        Pattern p;
        if (flag) {
            p = Pattern.compile("((http|ftp|https)://)" +
                    "(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))" +
                    "(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE);
            matcher = p.matcher(text);
            matcher.find();
            text = matcher.group();
            return text;
        } else {
            p = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))",
                    Pattern.CASE_INSENSITIVE);
            matcher = p.matcher(text);
            if (matcher != null && matcher.find()) {
                text = matcher.group();
                System.out.println(text);

                text = text.replaceAll("href\\s*=\\s*(['|\"]*)", "");
                System.out.println("--" + text);

                text = text.replaceAll("['|\"]", "");
                System.out.println("---" + text);
            }
            return text;
        }
    }

    /**
     * 返回获取的url和tittle信息
     *
     * @param intent
     * @return
     */
    public static Map<String, String> getContent(Intent intent) {
        String urlStr = "";
        String titleStr = "";
        Map<String, String> shareContent = new HashMap<>();
        String text = intent.getExtras().getString(Intent.EXTRA_TEXT);
        String subject = intent.getExtras().getString(Intent.EXTRA_SUBJECT);
        if (text != null && subject != null) {
            urlStr = getCompleteUrl(text);
            titleStr = subject;
        } else if (text != null && subject == null) {
            urlStr = getCompleteUrl(text);
            titleStr = text.replace(urlStr, "");
        } else if (text == null && subject == null) {
            return null;
        }
        shareContent.put("tittle", titleStr);
        shareContent.put("url", urlStr);
        return shareContent;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);//base_slide_right_in
        } else {
            return AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_out_right);//base_slide_right_out
        }
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    protected NewsDetailPresenterImpl initPresenter() {
        return new NewsDetailPresenterImpl();
    }

    public static DetailFragment newInstance(String doc_id, String title) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(DOC_ID, doc_id);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showError(String message) {
        ToastUtil.showToast(message);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateWebView(String html) {
        if (mErrorView.getVisibility()==View.VISIBLE){
            mErrorView.setVisibility(View.GONE);
        }
        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }
}
