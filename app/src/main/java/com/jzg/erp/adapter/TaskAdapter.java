package com.jzg.erp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.jzg.erp.R;
import com.jzg.erp.model.WaitingItemWrapper;
import com.jzg.erp.utils.DateTimeUtils;
import com.jzg.erp.widget.CustomTextView;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.ViewHolder;

import java.util.List;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/22 19:29
 * @desc:
 */
public class TaskAdapter extends CommonAdapter<WaitingItemWrapper.WaitingItem> {
    public TaskAdapter(Context context, int layoutId, List<WaitingItemWrapper.WaitingItem> datas, XRecyclerView xrv) {
        super(context, layoutId, datas,xrv);
    }

    @Override
    public void convert(ViewHolder holder, WaitingItemWrapper.WaitingItem task) {
        holder.setText(R.id.tvTaskName,task.getFollowUpStatus());
        String followStep = "跟进";
        String followType = task.getFollowType();
        if("4".equals(followType)){
            holder.setImageResource(R.id.ivState,R.mipmap.genjin);
            followStep = "跟进";
            holder.setText(R.id.tvTime,DateTimeUtils.formatMillsStr(task.getNextShopDate(),DateTimeUtils.YYYYMMDD));
        }else if("5".equals(followType)){
            holder.setImageResource(R.id.ivState,R.mipmap.daodian);
            followStep = "客户到店";
            holder.setText(R.id.tvTime,DateTimeUtils.formatMillsStr(task.getOnShopDate(),DateTimeUtils.YYYYMMDD));
        }else if("6".equals(followType)){
            holder.setImageResource(R.id.ivState,R.mipmap.anpai);
            followStep = "安排跟进";
            holder.setText(R.id.tvTime,DateTimeUtils.formatMillsStr(task.getNextShopDate(),DateTimeUtils.YYYYMMDD));
        }
        if(task.getIsRead()==0){
            holder.getView(R.id.ivDot).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.ivDot).setVisibility(View.GONE);
        }
        holder.setText(R.id.tvTaskName,followStep);
        int followUpItemState = task.getFollowUpItemState();
        CustomTextView ctvStatus = holder.getView(R.id.ctvStatus);
        if(followUpItemState==0){
            ctvStatus.setStrokeColorAndWidth(1,R.color.colorPrimary);
            ctvStatus.setSelectedTextColor(R.color.colorPrimary,R.color.colorPrimary);
            holder.setText(R.id.ctvStatus,"待处理");
        }else{
            holder.setText(R.id.ctvStatus,"已完成");
            ctvStatus.setStrokeColorAndWidth(1,R.color.common_gray_light);
            ctvStatus.setSelectedTextColor(R.color.common_gray_light,R.color.common_gray_light);
        }
        String name = task.getCustomerName();
        if(!TextUtils.isEmpty(task.getLinkMan()))
            name = task.getLinkMan();
        if(TextUtils.isEmpty(name))
            name = "";
        holder.setText(R.id.tvCustomer,name+" "+task.getCustomerStatus() );
    }
}
