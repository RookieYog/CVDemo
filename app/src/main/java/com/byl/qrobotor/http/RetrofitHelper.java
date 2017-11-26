package com.byl.qrobotor.http;


import com.byl.qrobotor.bean.LiveBaseBean;
import com.byl.qrobotor.common.App;
import com.byl.qrobotor.http.exception.RetrofitException;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.byl.qrobotor.common.Constant.BASE_JOKES_URL;
import static com.byl.qrobotor.common.Constant.BASE_LIVE_URL;
import static com.byl.qrobotor.common.Constant.BASE_NEWS_URL;
import static com.byl.qrobotor.common.Constant.BASE_PANDA_URL;

/**
 * author: lw
 * date: 2016/9/1
 */
public class RetrofitHelper {
    //    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
//
//        Request request = chain.request();
//        if (!NetworkUtils.isAvailableByPing()) {
//            CacheControl cacheControl = new CacheControl.Builder()
//                    .maxStale(30, TimeUnit.SECONDS)
//                    .build();
//            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build();
//        }
//
//        Response response = chain.proceed(request);
//
//        if (NetworkUtils.isAvailableByPing()) {
//            /**
//             * If you have problems in testing on which side is problem (server or app).
//             * You can use such feauture to set headers received from server.
//             */
//            int maxAge = 60 * 60; // 有网络时,设置缓存超时时间1个小时
//            response = response.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, max-age=" + maxAge)//设置缓存超时时间
//                    .build();
//        } else {
//            int maxStale = 60 * 60;//无网络时，设置超时为4周
//            response = response.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
//        return response;
//    };
    private static Retrofit news;
    private static Retrofit live;
    private static Retrofit joke;
    private static Retrofit panda;
    public static NewsAPI mNewsAPI;
    public static LiveAPI mLiveAPI;
    public static JokeAPI mJokeAPI;
    private static OkHttpClient.Builder builder = null;

    public static NewsAPI getNewsHelper() {
        if (news == null) {
            try {
                synchronized (RetrofitHelper.class) {
                    if (news == null) {
                        news = new Retrofit.Builder()
                                .client(builder().build())
                                .baseUrl(BASE_NEWS_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build();
                        mNewsAPI = news.create(NewsAPI.class);
                        return mNewsAPI;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mNewsAPI;
    }

    public static JokeAPI getJokeHelper() {
        if (joke == null) {
            synchronized (RetrofitHelper.class) {
                if (joke == null) {
                    joke = new Retrofit.Builder()
                            .client(builder().build())
                            .baseUrl(BASE_JOKES_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    if (null == mJokeAPI) {
                        mJokeAPI = joke.create(JokeAPI.class);
                    }
                    return mJokeAPI;
                }
            }
        }
        return mJokeAPI;
    }

    public static LiveAPI getLiveHelper() {
        if (live == null) {
            synchronized (RetrofitHelper.class) {
                if (live == null) {
                    live = new Retrofit.Builder()
                            .client(builder().build())
                            .baseUrl(BASE_LIVE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    if (null == mLiveAPI) {
                        mLiveAPI = live.create(LiveAPI.class);
                    }
                    return mLiveAPI;
                }
            }
        }
        return mLiveAPI;
    }

    public static LiveAPI getPandaHelper() {
        if (panda == null) {
            synchronized (RetrofitHelper.class) {
                panda = new Retrofit.Builder()
                        .client(builder().build())
                        .baseUrl(BASE_PANDA_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                if (null == mLiveAPI) {
                    mLiveAPI = panda.create(LiveAPI.class);
                }
                return mLiveAPI;
            }
        }
        return mLiveAPI;
    }

    public static OkHttpClient.Builder builder() {
        //OkHttp初始化
        //File httpCacheDirectory = new File(App.mAppContext.getExternalCacheDir(), "CVDemoCache");
        //Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);//缓存10MB
        //OkHttpClient.Builder httpBuidler = new OkHttpClient().newBuilder();
        //httpBuidler.cache(cache)
        //        .connectTimeout(10, TimeUnit.SECONDS)//连接超时限制5秒
        //        .writeTimeout(10, TimeUnit.SECONDS)
        //        .readTimeout(10, TimeUnit.SECONDS);
        //添加拦截器
        //.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)//离线缓存
        //.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        if (builder == null) {
            synchronized (RetrofitHelper.class) {
                File httpCacheDirectory = new File(App.mAppContext.getExternalCacheDir(), "CVDemoCache");
                Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);//缓存10MB
                builder = new OkHttpClient.Builder();
                builder.cache(cache);
                builder.connectTimeout(5, TimeUnit.SECONDS);
                builder.readTimeout(5, TimeUnit.SECONDS);
                builder.writeTimeout(5, TimeUnit.SECONDS);
                builder.cookieJar(new CookieJar() {
                    List<Cookie> cookies;

                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        this.cookies = list;
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        if (cookies != null)
                            return cookies;
                        return new ArrayList<Cookie>();
                    }
                });
            }
        }
        return builder;
    }

    public static <T> FlowableTransformer<LiveBaseBean<T>, T> handleLiveResult() {

        FlowableTransformer<LiveBaseBean<T>, T> flowableTransformer = new FlowableTransformer<LiveBaseBean<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<LiveBaseBean<T>> upstream) {
                upstream= (Flowable<LiveBaseBean<T>>) upstream.flatMap(new Function<LiveBaseBean<T>, Publisher<T>>() {
                        @Override
                        public Publisher<T> apply(@NonNull final LiveBaseBean<T> tLiveBaseBean) throws Exception {
                            Publisher<T> publisher = new Publisher<T>() {
                                @Override
                                public void subscribe(Subscriber<? super T> s) {
                                    if (tLiveBaseBean.getStatus().equals("ok")) {
                                        s.onNext(tLiveBaseBean.getResult());
                                    } else {
                                        s.onError(new RetrofitException(tLiveBaseBean.getMsg()));
                                    }
                                }
                            };
                            return publisher;
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                return (Publisher<T>) upstream;
            }
        };
        return flowableTransformer;
    }
}
