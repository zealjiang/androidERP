package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jzg.erp.R;
import com.jzg.erp.adapter.CarSourceAdapter;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.CarSourceTagData;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.Tag;
import com.jzg.erp.presenter.FocusCarPresenter;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IFocusCar;
import com.jzg.erp.widget.EmptyView;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.TagCloudLinkView;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
*
* 匹配车源
 * MatchCarSourceActivity
* @author sunbl
* created at 2016/7/2 13:59
*/
public class MatchCarSourceActivity extends BaseActivity implements IFocusCar {
    @Bind(R.id.carsource_xrc)
    XRecyclerView xrv;
    @Bind(R.id.err_layout)
    ErrorView errorView;
    @Bind(R.id.tag_text)
    TagCloudLinkView tagText;
    private FocusCarPresenter presenter;

    private List<CarSourceData.DataBean> carsourceDatas = new ArrayList<>();
    private CarSourceAdapter carSourceAdapter;
    private CustomerDetail.DataBean detail;
    private List<String> carIdList;
    private List<String> carNameList;
    private String ids = "";
    private int pageIndex = 1;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_match_car_source);
        ButterKnife.bind(this);
        detail = getIntent().getParcelableExtra(IntentKey.FOR_DETIAL);

        ids = detail.getIntendCar();
        presenter = new FocusCarPresenter(this);
        showDialog();
        presenter.getFocusCarList(getParams(), pageIndex);

        carNameList = Arrays.asList(detail.getIntendCarName().split(","));
        if (carNameList == null) {
            carNameList = new ArrayList<>(5);
        } else {
            carNameList = new ArrayList<>(carNameList);
        }
        carIdList = Arrays.asList(detail.getIntendCar().split(","));
        if (carIdList == null) {
            carIdList = new ArrayList<>(5);
        } else {
            carIdList = new ArrayList<>(carIdList);
        }
        for (int i = 0; i < carNameList.size(); i++) {
            tagText.add(new Tag(i, carNameList.get(i)));
        }
        tagText.drawTags();


        tagText.setOnTagDeleteListener(new TagCloudLinkView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                if(carIdList.size()>1){
                    carIdList.remove(i);
                    carNameList.remove(i);
                    ids=null;
                    for (String string : carIdList) {
                        if (TextUtils.isEmpty(ids)) {
                            ids = string;
                        } else {
                            ids = ids + "," + string;
                        }
                    }
                    presenter.getFocusCarList(getParams(), pageIndex);
                }else{
                    tagText.add(new Tag(i, carNameList.get(i)));
                    tagText.drawTags();
                }

            }
        });
    }

    @Override
    protected void setData() {
        setTitle("匹配车源");
        xrv.setLayoutManager(new LinearLayoutManager(this));
        xrv.setRefreshProgressStyle(ProgressStyle.CubeTransition);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, R.color.common_gray_line));
        EmptyView emptyView = new EmptyView(this, "暂无匹配车源");
        ((ViewGroup) xrv.getParent()).addView(emptyView);
        xrv.setEmptyView(emptyView);
        errorView.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                presenter.getFocusCarList(getParams(), pageIndex);
            }
        });
        xrv.setPullRefreshEnabled(false);
        xrv.setLoadingMoreEnabled(false);
        carSourceAdapter = new CarSourceAdapter(this, R.layout.carsource_item, carsourceDatas, xrv);
        xrv.setAdapter(carSourceAdapter);
        carSourceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Intent intent = new Intent(MatchCarSourceActivity.this, CarDetailActivity.class);
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

    }

    private Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("op", "matchUserIntendCar");
        map.put("intendCar", ids);
        map.put("sort", "");
        map.put("makeName", "");
        map.put("carStatus", "");
        LogUtil.e(TAG, UIUtils.getUrl(map));
        return map;
    }


    @Override
    public void showError(String error) {
        super.showError(error);
        xrv.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.setErrorText(error);
    }

    @Override
    public void showFocusCar(CarSourceData carSourceData, int pageIndex) {
        if (carSourceData.getStatus() == Constant.SUCCESS) {
            if (carSourceData.getData() != null) {
                carsourceDatas.clear();
                carsourceDatas.addAll(carSourceData.getData());
            }
            xrv.setVisibility(View.VISIBLE);
            if (errorView.getVisibility() == View.VISIBLE) {
                errorView.setVisibility(View.GONE);
            }
            carSourceAdapter.notifyDataSetChanged();
        } else {
            MyToast.showShort(carSourceData.getMessage());
        }
    }

    /**
     */
    @Override
    public void showFocusCarWithTag(CarSourceTagData carSourceData){

    }

}
