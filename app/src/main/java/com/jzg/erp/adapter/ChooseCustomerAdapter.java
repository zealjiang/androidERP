package com.jzg.erp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jzg.erp.R;
import com.jzg.erp.model.CustomerData;
import com.jzg.erp.utils.DateTimeUtils;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by JZG on 2016/6/23.
 */
public class ChooseCustomerAdapter extends CommonAdapter<CustomerData.DataBean> {

    private List<CustomerData.DataBean> datas;
    public  HashMap<Integer, Boolean> isSelected;
    public  HashMap<Integer, Boolean> isMoreSelected;
    private CheckBox checkBox;
    public  List<Integer> selectList = new ArrayList<>();


    public ChooseCustomerAdapter(Context context, int layoutId, List<CustomerData.DataBean> datas, XRecyclerView xRecyclerView) {
        super(context, layoutId, datas, xRecyclerView);
        this.datas = datas;
        isSelected = new HashMap<Integer, Boolean>();
        isMoreSelected = new HashMap<Integer, Boolean>();
        initCheckDate();
    }

    public void initCheckDate() {
        for (int i = 0; i < datas.size(); i++) {
            getIsSelected().put(i, false);
        }
    }
    public void initMoreCheckDate(List<CustomerData.DataBean> moreData) {
        for (int i = 0; i < moreData.size(); i++) {
            getMoreIsSelected().put(isSelected.size()+i, false);
        }
        isSelected.putAll(isMoreSelected);
    }

    @Override
    public void convert(final ViewHolder holder, CustomerData.DataBean customerListEntity) {
        SimpleDraweeView simpleDraweeView = holder.getView(R.id.ctvState);
        checkBox = holder.getView(R.id.checkbox);
        //联系人+客户名称
        String linkMan = customerListEntity.getLinkMan();
        String customerName = customerListEntity.getCustomerName();
        String result = "";
        if(!TextUtils.isEmpty(linkMan)){
            result = linkMan;
            if(!TextUtils.isEmpty(customerName)){
                if(TextUtils.equals(linkMan,customerName)){
                    result = linkMan;
                }else{
                    result+="("+customerName+")";
                }
            }
        }else{
            result = customerName;
        }
        holder.setText(R.id.customer_name, result);

        holder.setText(R.id.customer_time, "最后跟进  " + (TextUtils.isEmpty(customerListEntity.getFollowUpTime())?"无": DateTimeUtils.formatMillsStr(customerListEntity.getFollowUpTime(),DateTimeUtils.YYYYMMDDHHMM)));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected.get(holder.getAdapterPosition()-1)) {
                    isSelected.put(holder.getAdapterPosition()-1, false);
                    setIsSelected(isSelected);
                } else {
                    isSelected.put(holder.getAdapterPosition()-1, true);
                    setIsSelected(isSelected);
                }

            }
        });
        Boolean bb = getIsSelected().get(holder.getAdapterPosition()-1);
        checkBox.setChecked(bb);

        switch (customerListEntity.getCustomerStatus()) {
            case 1:
                // return "意向";
                simpleDraweeView.setImageURI("res:/"+R.mipmap.yixiang_tx);
                return;
            case 2:
                // return "预定";
                simpleDraweeView.setImageURI("res:/"+R.mipmap.yuding_tx);
                return;
            case 3:
                //return "成交";
                simpleDraweeView.setImageURI("res:/"+R.mipmap.chengjiao_tx);
                return;
            case 4:
                // return "战败";
                simpleDraweeView.setImageURI("res:/"+R.mipmap.zhanbai_tx);
                return;

            default:

        }
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
    public HashMap<Integer, Boolean> getMoreIsSelected() {
        return isMoreSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }
    public List<Integer> getChooseItemPosition(){
        selectList.clear();
        Iterator<Map.Entry<Integer, Boolean>> it = isSelected.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Boolean> entry = it.next();
            if(entry.getValue()){
                selectList.add(entry.getKey());
            }
        }
        return selectList;
    }
}
