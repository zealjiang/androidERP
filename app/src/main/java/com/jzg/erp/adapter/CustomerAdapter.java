package com.jzg.erp.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jzg.erp.R;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.utils.DateTimeUtils;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.ViewHolder;

import java.util.List;

/**
 * Created by JZG on 2016/6/23.
 */
public class CustomerAdapter extends CommonAdapter<CustomerInfo.CustomerListEntity> {



    public CustomerAdapter(Context context, int layoutId, List<CustomerInfo.CustomerListEntity> datas, XRecyclerView xRecyclerView) {
        super(context, layoutId, datas, xRecyclerView);
    }
    @Override
    public void convert(ViewHolder holder, CustomerInfo.CustomerListEntity customerInfo) {
        SimpleDraweeView  ctvState = holder.getView(R.id.ctvState);
        if (customerInfo.getCustomerStatus() == 1) {
            ctvState.setImageURI(Uri.parse("res:/"+R.mipmap.yixiang_tx));
        } else if (customerInfo.getCustomerStatus() == 2) {
            ctvState.setImageURI(Uri.parse("res:/"+R.mipmap.yuding_tx));
        } else if (customerInfo.getCustomerStatus() == 4) {
            ctvState.setImageURI(Uri.parse("res:/"+R.mipmap.zhanbai_tx));
        } else {
            ctvState.setImageURI(Uri.parse("res:/"+R.mipmap.chengjiao_tx));
        }

        String linkMan = customerInfo.getLinkMan();
        String customerName = customerInfo.getCustomerName();
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
        String time = customerInfo.getFollowUpTime();
        holder.setText(R.id.customer_time, "最后跟进 " + (TextUtils.isEmpty(DateTimeUtils.formatMillsStr(time,DateTimeUtils.YYYYMMDDHHMM)) ? "无" : DateTimeUtils.formatMillsStr(time,DateTimeUtils.YYYYMMDDHHMM)));
    }
}
