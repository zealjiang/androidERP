package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.adapter.SearchAdapter;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.presenter.SearchViewPresenter;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.view.IsearchView;
import com.jzg.erp.widget.ClearableEditText;
import com.jzg.erp.widget.SpecifiedTextView;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: guochen
 * date: 2016/6/24 16:40
 * email:
 */
public class SearchActivity extends BaseActivity implements IsearchView, XRecyclerView.LoadingListener {
    @Bind(R.id.etSearch)
    ClearableEditText etSearch;
    @Bind(R.id.rlSearchFrameDelete)
    RelativeLayout rlSearchFrameDelete;
    @Bind(R.id.top)
    RelativeLayout top;
    @Bind(R.id.mrecyclerView)
    XRecyclerView mrecyclerView;
    @Bind(R.id.text_tishi)
    SpecifiedTextView textTishi;

    private SearchViewPresenter searchViewPresenter;
    private List<CarSourceData.DataBean> carsourceDatas = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private int pageIndex = 1;
    private String Sort = "0"; //排序方式


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_searchview);
        ButterKnife.bind(this);
        mrecyclerView.setPullRefreshEnabled(true);
        mrecyclerView.setLoadingMoreEnabled(true);

    }

    @Override
    protected void setData() {
        setTitle("车源");
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setLoadingListener(this);
        searchViewPresenter = new SearchViewPresenter(this);
        searchAdapter = new SearchAdapter(this, R.layout.carsource_item, carsourceDatas, mrecyclerView);
        mrecyclerView.setAdapter(searchAdapter);
        mrecyclerView.setVisibility(View.GONE);
        searchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Intent intent = new Intent(SearchActivity.this, CarDetailActivity.class);
                intent.putExtra(Constant.MAKE_NAME, carsourceDatas.get(position).getMakeName());
                intent.putExtra(Constant.MODE_NAME, carsourceDatas.get(position).getModelName());
                intent.putExtra(Constant.CARSTATUS, carsourceDatas.get(position).getCarStatus2()+"");
                intent.putExtra(Constant.CARSOURCEID, carsourceDatas.get(position).getCarSourceID()+"");
                intent.putExtra(Constant.CARID, carsourceDatas.get(position).getID()+"");
                intent.putExtra(Constant.CARDETAILPATH, carsourceDatas.get(position).getCarDetailPath());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                    if (TextUtils.isEmpty(etSearch.getText().toString())) {
                        mrecyclerView.setVisibility(View.GONE);
                        textTishi.setVisibility(View.VISIBLE);
                        textTishi.setText("");
                    } else {
                        textTishi.setText("");
                        pageIndex = 1;
                        searchViewPresenter.loadData(getSearchParams());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void SearchSuccess(CarSourceData carSourceData) {
        mrecyclerView.loadMoreComplete();//关闭加载
        mrecyclerView.refreshComplete();//关闭刷新
        carsourceDatas.clear();
        if (mrecyclerView.getVisibility() == View.GONE) {
            mrecyclerView.setVisibility(View.VISIBLE);
        }
        if (carSourceData.getStatus() == Constant.SUCCESS) {
            if (carSourceData.getData() != null && carSourceData.getData().size() > 0) {
                carsourceDatas.addAll(carSourceData.getData());
            } else {
                mrecyclerView.setVisibility(View.GONE);
                textTishi.setVisibility(View.VISIBLE);
                String message = etSearch.getText().toString();
                textTishi.setSpecifiedTextsColor("没有搜索到包含“" + message + "”的结果", message, Color.parseColor("#FF9F3F"));
            }
        } else {
            MyToast.showShort(carSourceData.getMessage());
        }
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchMoreSuccess(CarSourceData data) {
        mrecyclerView.loadMoreComplete();//关闭加载
        mrecyclerView.refreshComplete();//关闭刷新
        if (data.getStatus() == Constant.SUCCESS) {
            if (data.getData() != null) {
                carsourceDatas.addAll(data.getData());
            }
        } else {
            MyToast.showShort(data.getMessage());
        }
        searchAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String error) {
        mrecyclerView.loadMoreComplete();//关闭加载
        mrecyclerView.refreshComplete();//关闭刷新
        mrecyclerView.setVisibility(View.GONE);
        textTishi.setVisibility(View.VISIBLE);
        String message = etSearch.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            textTishi.setSpecifiedTextsColor("没有搜索到包含“" + message + "”的结果", message, Color.parseColor("#FF9F3F"));
        } else {
            textTishi.setText(getResources().getText(R.string.search_message));
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    public Map<String, String> getSearchParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("op", "getCarList");
        if (!TextUtils.isEmpty(etSearch.getText().toString()) && !TextUtils.isEmpty(etSearch.getText().toString().trim())) {
            params.put("condition", etSearch.getText().toString().trim());
        } else {
            params.put("condition", "");
        }
        params.put("pageIndex", pageIndex + "");
        params.put("pageCount", String.valueOf(Constant.PAGECOUNT));
        params.put("Sort", Sort);
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap));
        return params;
    }

    //刷新
    @Override
    public void onRefresh() {
        pageIndex = 1;
        searchViewPresenter.loadData(getSearchParams());
    }

    //加载
    @Override
    public void onLoadMore() {
        if(carsourceDatas.size()>0&& (carsourceDatas.size()%Constant.PAGECOUNT!=0)){
            mrecyclerView.loadMoreComplete();
            return;
        }
        if(carsourceDatas.size()==0){
            mrecyclerView.loadMoreComplete();
            return;
        }
        pageIndex++;
        searchViewPresenter.loadMoreData(getSearchParams());
    }

    @OnClick(R.id.tv_cancle)
    public void onClick() {
        if (TextUtils.isEmpty(etSearch.getText().toString())) {
            mrecyclerView.setVisibility(View.GONE);
            textTishi.setVisibility(View.VISIBLE);
            textTishi.setText("");

        } else {
            textTishi.setText("");
            pageIndex = 1;
            searchViewPresenter.loadData(getSearchParams());
        }
    }
}
