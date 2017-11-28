package com.jzg.erp.adapter;/**
 * author: gcc
 * date: 2016/6/30 20:15
 * email:
 */

import android.content.Context;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jzg.erp.R;
import com.jzg.erp.model.MatchCustomerData;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.ViewHolder;

import java.util.List;

/**
 * author: guochen
 * date: 2016/6/30 20:15
 * email: 
 */
public class MatchCustomerAdapter extends CommonAdapter<MatchCustomerData.DataBean> {

    public MatchCustomerAdapter(Context context, int layoutId, List<MatchCustomerData.DataBean> datas, XRecyclerView xrv) {
        super(context, layoutId, datas, xrv);
    }

    @Override
    public void convert(ViewHolder holder,MatchCustomerData.DataBean customerData) {
        SimpleDraweeView simpleDraweeView = holder.getView(R.id.ctvState);
        holder.setText(R.id.customer_name,customerData.getCustomerName());

        //联系人+客户名称
        String linkMan = customerData.getLinkMan();
        String customerName = customerData.getCustomerName();
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
        holder.setText(R.id.customer_time,"意向客户  "+ (TextUtils.isEmpty(customerData.getCustomerLevel())?"无等级":customerData.getCustomerLevel()));
        switch (customerData.getCustomerStatus()) {
            case 1:
                simpleDraweeView.setImageURI("res:/"+R.mipmap.yixiang_tx);
                return;
            case 2:
                simpleDraweeView.setImageURI("res:/"+R.mipmap.yuding_tx);
                return;
            case 3:
                simpleDraweeView.setImageURI("res:/"+R.mipmap.chengjiao_tx);
                return;
            case 4:
                simpleDraweeView.setImageURI("res:/"+R.mipmap.zhanbai_tx);
                return;

            default:

        }
    }

}
