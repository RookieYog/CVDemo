package com.byl.qrobotor.model;

import com.byl.qrobotor.base.BaseOnLoadingListener;
import com.byl.qrobotor.bean.NewsDetailBean;
import com.byl.qrobotor.http.GsonHelper;
import com.byl.qrobotor.http.HttpSubscriber;
import com.byl.qrobotor.http.RetrofitHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by acer on 2017/6/28.
 */

public class NewsDetailModelImpl implements INewsDetailModel{

    @Override
    public void onLoadNewsDetail(String doc_id, final BaseOnLoadingListener<String> listener) {
        RetrofitHelper.getNewsHelper()
                .getNewsDetail(doc_id)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<JsonObject, Publisher<NewsDetailBean>>() {
                    @Override
                    public Publisher<NewsDetailBean> apply(JsonObject jsonObject) throws Exception {
                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            JsonElement element = entry.getValue();
                            NewsDetailBean detailBean = (NewsDetailBean) GsonHelper.parseJson(element, NewsDetailBean.class);
                            return Flowable.just(detailBean);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<NewsDetailBean>() {
                    /*detailBean -> {
                    //成功得到新闻详情后，先保存bean，并刷新相关新闻列表
                    view.updateExploreDetail(detailBean);
                    view.updateRelativeSys(detailBean.getRelative_sys());
                }*/
                    @Override
                    public void accept(@NonNull NewsDetailBean newsDetailBean) throws Exception {
                        //成功得到新闻详情后，先保存bean，并刷新相关新闻列表
                    }
                }).flatMap(new Function<NewsDetailBean, Publisher<String>>() {
            @Override
            public Publisher<String> apply(NewsDetailBean detailBean) throws Exception {
                //在这里处理html代码
                String html = detailBean.getBody();
                //添加Javascript脚本
                html = "<script type=\"application/javascript\" language=\"javascript\"> \n" +
                        "    function startGallary(e){\n" +
                        "        window.jsObj.startGallaryOnAndroid(e);\n" +
                        "    }\n" +
                        "</script>\n" + "\n" + html;

                List<NewsDetailBean.ImgBean> imgList = detailBean.getImg();

                for (int i = 0; i < imgList.size(); i++) {
                    NewsDetailBean.ImgBean imgBean = imgList.get(i);
                    String ref = imgBean.getRef();//标志
                    String imgCode = "<img src=\"" + imgBean.getSrc() + "\" id=\"img" + i + "\" width=100% onclick=\"startGallary(" + i + ")\"/><p style=color:757575;font-size:12px align=center>" + imgBean.getAlt() + "</p>";
                    //查找到需要插入的位置，并添加图片url
                    StringBuilder buffer = new StringBuilder(html);
                    int pos = buffer.indexOf(ref);
                    buffer.insert(pos, imgCode);
                    html = buffer.toString();
                }

                return Flowable.just(html);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<String>() {
                    @Override
                    public void _onNext(String html) {
                        //最终刷新详情内容页面
                        listener.onSuccessed(html);
                    }

                    @Override
                    public void _onError(String message) {
                        listener.onFailed(message);
                    }
                });
    }
}
