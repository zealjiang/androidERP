package com.jzg.erp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.widget.ErrorView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 违章查询
 * @author zealjiang
 * @time 2016/6/24 17:00
 */
public class BreakRuleActivity extends BaseActivity{


    @Bind(R.id.webview)
    WebView mWebview;

    @Bind(R.id.myProgressBar)
    ProgressBar myProgressBar;

    @Bind(R.id.err_layout)
    ErrorView mErrorView;

    String url = "http://m.jingzhengu.com/v5/Illegal.ashx";

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_break_rules);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        setTitle("违章查询");
        initWebview();

        mErrorView.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                mWebview.loadUrl(url);
            }
        });
    }

    private void initWebview(){


        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl(url);
        mWebview.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //此时当前Activity可能已经退出ButterKnife.unbind(this);
                if(null==mErrorView){
                    return;
                }
                mErrorView.setVisibility(View.VISIBLE);
                //清空页面显示的出错内容
                mWebview.loadUrl("about:blank");
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                //此时当前Activity可能已经退出ButterKnife.unbind(this);
                if(null==mErrorView){
                    return;
                }
                mErrorView.setVisibility(View.GONE);
            }

        });


        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //此时当前Activity可能已经退出ButterKnife.unbind(this);
                if(null==myProgressBar){
                    return;
                }

                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
