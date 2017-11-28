package com.jzg.erp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jzg.erp.R;

import java.util.List;


/**
 * author: guochen
 * date: 2016/6/21 16:05
 * email: 
 */
public class CarSourcePopAdapter extends BaseAdapter{

    private List<String>  datas;
    private Context context;
    private int checkItemPosition = 0;

    public CarSourcePopAdapter(Context context,List<String> datas) {
        this.datas = datas;
        this.context = context;
    }
    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_pop,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(datas.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.mipmap.drop_down_checked), null);
            } else {
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }else{
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView textView;
    }
}
