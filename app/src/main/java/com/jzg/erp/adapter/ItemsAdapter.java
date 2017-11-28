package com.jzg.erp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.jzg.erp.R;
import com.jzg.erp.model.TaskItemGroupWrapper;
import com.jzg.erp.utils.DateTimeUtils;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.ViewHolder;

import java.util.List;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/7 11:25
 * @desc:
 */
public class ItemsAdapter extends CommonAdapter<TaskItemGroupWrapper.TaskGroup.TaskItem> {
    public ItemsAdapter(Context context, int layoutId, List<TaskItemGroupWrapper.TaskGroup.TaskItem> datas, XRecyclerView xrv) {
        super(context, layoutId, datas, xrv);
    }
    @Override
    public void convert(ViewHolder holder, TaskItemGroupWrapper.TaskGroup.TaskItem item) {
        View tagView = holder.getView(R.id.rlGroup);
        if (!TextUtils.isEmpty(item.getTag())) {
            holder.setText(R.id.tvGroupTitle, item.getTag());
            tagView.setVisibility(View.VISIBLE);
        } else {
            tagView.setVisibility(View.GONE);
        }
        holder.setText(R.id.tvCreateTime, item.getCreateDateStrHM());
        String type = item.getFollowType();
        String nextShopDate = DateTimeUtils.formatMillsStr(item.getNextShopDate(), DateTimeUtils.YYYYMMDD_CN);
        if("4".equals(type)){
            holder.setImageResource(R.id.ivState,R.mipmap.genjin);
            holder.setText(R.id.tvNextTitle,"跟进");
            holder.setText(R.id.tvContent, nextShopDate + " " + item.getNextShopTime() + "\r\n" + (TextUtils.isEmpty(item.getSpecialInstruction()) ? "" : item.getSpecialInstruction()));
        }else if("5".equals(type)){
            holder.setImageResource(R.id.ivState,R.mipmap.daodian);
            holder.setText(R.id.tvNextTitle,"客户到店");
            holder.setText(R.id.tvContent, DateTimeUtils.formatMillsStr(item.getOnShopDate(),DateTimeUtils.YYYYMMDD_CN)+" " +(TextUtils.isEmpty(item.getOnShopTime())?"":item.getOnShopTime()));
        }else if("6".equals(type)){
            holder.setImageResource(R.id.ivState,R.mipmap.anpai);
            holder.setText(R.id.tvNextTitle,"安排跟进");
            holder.setText(R.id.tvContent, nextShopDate +" " +(TextUtils.isEmpty(item.getNextShopTime())?"":item.getNextShopTime()));
        }
    }
}
