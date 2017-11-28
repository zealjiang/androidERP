package com.jzg.erp.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.jzg.erp.R;
import com.jzg.erp.model.TaskItemGroupWrapper;
import com.jzg.erp.utils.LogUtil;
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
public class RecordAdapter extends CommonAdapter<TaskItemGroupWrapper.TaskGroup.TaskItem> {
    public RecordAdapter(Context context, int layoutId, List<TaskItemGroupWrapper.TaskGroup.TaskItem> datas, XRecyclerView xrv) {
        super(context, layoutId, datas, xrv);
    }
    @Override
    public void convert(ViewHolder holder, TaskItemGroupWrapper.TaskGroup.TaskItem taskItem) {
        View tagView = holder.getView(R.id.rlGroup);
        if (!TextUtils.isEmpty(taskItem.getTag())) {
            holder.setText(R.id.tvGroupTitle, taskItem.getTag());
            tagView.setVisibility(View.VISIBLE);
        } else {
            tagView.setVisibility(View.GONE);
        }
        holder.setText(R.id.tvCreateTime, taskItem.getCreateDateStrHM());
        String type = taskItem.getFollowUpStatus();
        LogUtil.e("pk","type="+type);
        AppCompatTextView tvFollowupContent = holder.getView(R.id.tvFollowupContent);
        if ("继续跟进".equals(type) || "预约到店".equals(type) || "战败".equals(type)) {
            holder.setImageResource(R.id.ivState, R.mipmap.genjin);
            holder.setText(R.id.tvFollowupTitle, "跟进记录");
            tvFollowupContent.setText("跟进结果:" + taskItem.getFollowUpStatus() + "\r\n" + "跟进记录:" + taskItem.getFollowingRecord());
            tvFollowupContent.setVisibility(View.VISIBLE);
        } else if ("打电话".equals(type)) {
            holder.setImageResource(R.id.ivState, R.mipmap.icon002);
            holder.setText(R.id.tvFollowupTitle, "电话");
            tvFollowupContent.setVisibility(View.GONE);
        } else if ("分配客户".equals(type)) {
            holder.setImageResource(R.id.ivState, R.mipmap.icon003);
            holder.setText(R.id.tvFollowupTitle, taskItem.getCreateUser()+"分配客户");
            tvFollowupContent.setVisibility(View.GONE);
        }else{
            tvFollowupContent.setVisibility(View.GONE);
        }
    }
}
