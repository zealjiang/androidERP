package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.ShowDialogTool;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: guochen
 * date: 2016/6/28 14:30
 * email:
 */
public class CarDetailActivity extends BaseActivity {
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.psb)
    ProgressBar psb;
    @Bind(R.id.ll_pipei)
    LinearLayout llPipei;
    @Bind(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @Bind(R.id.ll_button)
    LinearLayout llButton;
    private String makeName;
    private String carSourceId;
    private String carId;
    private Boolean isShowBtn;  //true显示button false 显示
    private String modeName;
    private String carDetailPath;
    private String carStatus;

    private String sharePic;
    private String shareText;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cardetail);
        ButterKnife.bind(this);
        makeName = getIntent().getStringExtra(Constant.MAKE_NAME);
        modeName = getIntent().getStringExtra(Constant.MODE_NAME);
        isShowBtn = getIntent().getBooleanExtra(Constant.ISSHOWBUTTON, true);
        carStatus = getIntent().getStringExtra(Constant.CARSTATUS);
        carSourceId = getIntent().getStringExtra(Constant.CARSOURCEID);
        carId = getIntent().getStringExtra(Constant.CARID);
        carDetailPath = getIntent().getStringExtra(Constant.CARDETAILPATH);
        if (isShowBtn) {
            llButton.setVisibility(View.VISIBLE);
        } else {
            llButton.setVisibility(View.GONE);
        }

        sharePic = getIntent().getStringExtra(Constant.SHARE_PIC);
        shareText = getIntent().getStringExtra(Constant.SHARE_TEXT);
    }

    @Override
    protected void setData() {

        setImageRight(R.drawable.fenxiang);

        setTitle(Constant.CARDETAIL);
        //设置javascript支持
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(carDetailPath);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    //直接调出界面，不需要权限
                    Intent sendIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(sendIntent);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (psb == null)
                    return;
                psb.setVisibility(View.VISIBLE);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (psb == null)
                    return;

                psb.setProgress(newProgress);

                if (newProgress == 100) {
                    psb.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回webView的上一页面
        } else {
            super.onBackPressed();
        }
    }


    @OnClick({R.id.ll_pipei, R.id.ll_guanzhu, R.id.ivRight})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_pipei:
                if (!TextUtils.isEmpty(carStatus) && TextUtils.equals("3", carStatus)) {
                    MyToast.showShort("已售出车辆无法匹配客户!");
                    return;
                }
                Intent intent = new Intent(this, MatchCustomerActivity.class);
                if (!TextUtils.isEmpty(makeName)) {
                    intent.putExtra(Constant.MAKE_NAME, makeName);
                    intent.putExtra(Constant.MODE_NAME, modeName);
                }
                startActivity(intent);
                break;
            case R.id.ll_guanzhu:
                if (!TextUtils.isEmpty(carStatus) && TextUtils.equals("3", carStatus)) {
                    MyToast.showShort("已售出车辆无法关注!");
                    return;
                }
                Intent intent1 = new Intent(this, ChooseCustomerActivity.class);//关注
                intent1.putExtra(Constant.CARSOURCEID, carSourceId);
                intent1.putExtra(Constant.CARID, carId);
                intent1.putExtra(Constant.MAKE_NAME, makeName);
                startActivity(intent1);
                break;
            case R.id.ivRight:

                try {
                    Log.e(TAG, "shareText: " + shareText);
                    Log.e(TAG, "sharePic: " + sharePic);
                    observable.subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean boo) {
                            if (!TextUtils.isEmpty(sharePic)&&boo) {
                                new ShareAction(CarDetailActivity.this)
                                        .withText(shareText)
                                        .withTitle(shareText)
                                        .withTargetUrl(carDetailPath)
                                        .withMedia(new UMImage(CarDetailActivity.this,sharePic))
                                        .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN)
                                        .setCallback(umShareListener).open();
                            } else {
                                new ShareAction(CarDetailActivity.this)
                                        .withText(shareText)
                                        .withTitle(shareText)
                                        .withTargetUrl(carDetailPath)
                                        .withMedia(new UMImage(CarDetailActivity.this, R.mipmap.default_share))
                                        .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN)
                                        .setCallback(umShareListener).open();
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    MyToast.showShort("分享失败");
                }

                break;
        }
    }

    // 使用IO线程处理, 主线程响应
    final Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
        @Override
        public void call(Subscriber<? super Boolean> subscriber) {
            subscriber.onNext(checkURL(sharePic));
            subscriber.onCompleted();
        }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


    //检查连接的有效xìng
    public boolean checkURL(String urlPath) {

        try{
            LogUtil.e(TAG,"url: "+urlPath);
            URL url = new URL(urlPath);
            // 3、获取连接对象、此时还没有建立连接  
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            // 4、初始化连接对象  
            // 设置请求的方法，注意大写  
            connection.setRequestMethod("GET");
            // 5、建立连接  
            connection.connect();

            // 6、获取成功判断,获取响应码  
            if(connection.getResponseCode() == 200){
                LogUtil.e(TAG,"responseCode: "+connection.getResponseCode());
                InputStream is = connection.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                if(bm == null){
                    return false;
                }
                bm.recycle();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform == SHARE_MEDIA.SINA
                    || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                ShowDialogTool.showCenterToast(CarDetailActivity.this, "分享成功！");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ShowDialogTool.showCenterToast(CarDetailActivity.this, "分享失败！");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ShowDialogTool.showCenterToast(CarDetailActivity.this, "分享取消！");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
