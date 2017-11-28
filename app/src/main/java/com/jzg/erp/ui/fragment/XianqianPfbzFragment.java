package com.jzg.erp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jzg.erp.R;
import com.jzg.erp.widget.ErrorView;

/**
 * 不继承NewBaseFragment的原因是：NewBaseFragment布局里带了公共标题栏
 * @author zealjiang
 * @time 2016/6/25 11:41
 */
public class XianqianPfbzFragment extends Fragment {


	private static final String RESULT_URL= "http://m.jingzhengu.com/ershouche/EnvironmentalStandards.aspx";
	private WebView mWebView;

	private ErrorView mErrorView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tools_xianqian_pfbz,null);
		init(view);

		mErrorView.setOnErrorListener(new ErrorView.OnErrorListener() {
			@Override
			public void onError() {
				mWebView.loadUrl(RESULT_URL);
			}
		});

		return view;
	}
	private void init(View view){
		mErrorView = (ErrorView) view.findViewById(R.id.err_layout);
		mWebView = (WebView) view.findViewById(R.id.tools_xianqian_pfbz_web);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(RESULT_URL);

		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				mErrorView.setVisibility(View.VISIBLE);
				//清空页面显示的出错内容
				mWebView.loadUrl("about:blank");
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
				mErrorView.setVisibility(View.GONE);
			}

		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
