package com.jzg.erp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jzg.erp.R;
import com.jzg.erp.adapter.AddFocusCarAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.presenter.AddFoucsCarPresenter;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IAddFoucsCar;
import com.jzg.erp.widget.ClearableEditText;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.SpecifiedTextView;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加关注车辆界面
 */
public class AddFocusCarActivity extends BaseActivity implements IAddFoucsCar, AddFocusCarAdapter.OnCheckedCountChangedListener {
    @Bind(R.id.list)
    XRecyclerView xrv;
    @Bind(R.id.err_layout)
    ErrorView errorView;
    @Bind(R.id.content)
    ClearableEditText content;
    @Bind(R.id.text_tishi)
    SpecifiedTextView textTishi;
    @Bind(R.id.tvRight)
    TextView tvRight;

    private AddFoucsCarPresenter presenter;
    private List<CarSourceData.DataBean> carsourceDatas = new ArrayList<>();
    private AddFocusCarAdapter addFocusCarAdapter;
    private int pageIndex = 1;
    private String id;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_focus_car);
        ButterKnife.bind(this);
        content.clearFocus();
        id = getIntent().getStringExtra("id");
        presenter = new AddFoucsCarPresenter(this);
    }


    @Override
    protected void setData() {
        setTitle("添加关注车");
        setTextRight("完成");
        tvRight.setVisibility(View.GONE);
        //内容显示区域
        xrv.setLayoutManager(new LinearLayoutManager(this));
        xrv.setRefreshProgressStyle(ProgressStyle.BallScaleMultiple);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.BallScaleMultiple);
        xrv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 2, R.color.common_gray_dark));
        errorView.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                pageIndex = 1;
                presenter.getFocusCarList(getSearchParams(pageIndex), pageIndex);
            }
        });

        addFocusCarAdapter = new AddFocusCarAdapter(this, R.layout.choose_carsource_item, carsourceDatas, xrv,this);
        xrv.setAdapter(addFocusCarAdapter);
        addFocusCarAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                CheckBox CheckBoxButton = (CheckBox) view.findViewById(R.id.radio);
                CheckBoxButton.toggle();
                addFocusCarAdapter.maps.put(position, CheckBoxButton.isChecked());
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                presenter.getFocusCarList(getSearchParams(pageIndex), pageIndex);
            }

            @Override
            public void onLoadMore() {
                if(carsourceDatas.size()>0 && (carsourceDatas.size()%Constant.PAGECOUNT!=0)){
                    xrv.loadMoreComplete();
                    return;
                }
                if(carsourceDatas.size()==0){
                    xrv.loadMoreComplete();
                    return;
                }
                pageIndex++;
                presenter.getFocusCarList(getSearchParams(pageIndex), pageIndex);
            }
        });

        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                    presenter.getFocusCarList(getSearchParams(pageIndex), pageIndex);
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void showFocusCar(CarSourceData carSourceData, int pageIndex) {
        if (carSourceData.getStatus() == Constant.SUCCESS) {
            errorView.setVisibility(View.GONE);
            if (pageIndex == 1) {
                xrv.refreshComplete();
                carsourceDatas.clear();
            } else {
                xrv.loadMoreComplete();
            }
            List<CarSourceData.DataBean> resultList = carSourceData.getData();
            if(resultList !=null && resultList.size()>0){
                textTishi.setText("");
                xrv.setVisibility(View.VISIBLE);
                carsourceDatas.addAll(resultList);
                addFocusCarAdapter.clearSelectedItems();
                addFocusCarAdapter.updateView(carsourceDatas);
                addFocusCarAdapter.notifyDataSetChanged();
            }else{
                xrv.setVisibility(View.GONE);
                addFocusCarAdapter.updateView(carsourceDatas);
                addFocusCarAdapter.notifyDataSetChanged();
                String message = content.getText().toString();
                textTishi.setSpecifiedTextsColor("没有搜索到包含“" + message + "”的结果", message, Color.parseColor("#FF9F3F"));
            }
        } else {
            MyToast.showShort(carSourceData.getMessage());
        }
    }

    @Override
    public void submitFocusCar(CarSourceData carSourceData) {
        EventBus.getDefault().post("0");
        Toast.makeText(this,"添加关注车成功",Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void showError(String error) {
        super.showError(error);
        xrv.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tvRight, R.id.search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.search:
                pageIndex = 1;
                presenter.getFocusCarList(getSearchParams(pageIndex), pageIndex);
                break;
            case R.id.tvRight:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(content.getWindowToken(),0);
                ArrayList<Integer> arrayList = addFocusCarAdapter.getDataMap();
                if(arrayList.size()==0){
                    MyToast.showShort("请至少选择一个车型");
                    return;
                }
                presenter.submit(getSubmitParams());
                break;
            default:
                break;
        }
    }

    public Map<String, String> getSubmitParams() {
        Map<String, String> map = new HashMap<>();
        map.put("op", "insertFocusCar");
        map.put("customerId", id);
        map.put("userID", String.valueOf(JzgApp.getUser().getUserID()));
        map.put("currentUserName", String.valueOf(JzgApp.getUser().getTrueName()));
        ArrayList<Integer> arrayList = addFocusCarAdapter.getDataMap();
        String carsourceIdArr = "";
        String carIdArr = "";
        for (Integer integer : arrayList) {
            if (TextUtils.isEmpty(carsourceIdArr)) {
                carIdArr = carsourceDatas.get(integer).getID() + "";
                carsourceIdArr = carsourceDatas.get(integer).getCarSourceID() + "";
            } else {
                carIdArr = carIdArr + "_" + carsourceDatas.get(integer).getID();
                carsourceIdArr = carsourceIdArr + "_" + carsourceDatas.get(integer).getCarSourceID();
            }
        }
        map.put("carsourceIdArr", carsourceIdArr);
        map.put("carIdArr", carIdArr);
        LogUtil.e(TAG, UIUtils.getUrl(map));
        return map;
    }


    public Map<String, String> getSearchParams(int pageIndex) {
        Map<String, String> map = new HashMap<>();
        map.put("op", "focusCarLsitByCondition");
        map.put("customerId", id);
        map.put("userID", String.valueOf(JzgApp.getUser().getUserID()));
        map.put("condition", content.getText().toString());
        map.put("pageIndex", pageIndex + "");
        map.put("pageCount", Constant.PAGECOUNT + "");
        LogUtil.e(TAG, UIUtils.getUrl(map));
        return map;
    }

    @Override
    public void onSelectedCountChanged() {
        Collection<Boolean> values = addFocusCarAdapter.getMap().values();
        if(values.contains(Boolean.TRUE)){
            tvRight.setVisibility(View.VISIBLE);
        }else{
            tvRight.setVisibility(View.GONE);
        }
    }
}
