package com.jzg.erp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.jzg.erp.R;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import java.util.List;

/**
 * author: guochen
 * date: 2016/6/23 15:33
 * email: 
 */
public class PopAdapter extends CommonAdapter<String> {
    private int checkItemPosition = 0;
    private Context context;
    public PopAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, String s) {
       TextView textView =  (TextView) holder.getView(R.id.tv_text);
        holder.setText(R.id.tv_text,s);
        if (checkItemPosition != -1) {
            if (checkItemPosition == holder.getAdapterPosition()) {
                textView.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.mipmap.drop_down_checked), null);
            } else {
                textView.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }
}
