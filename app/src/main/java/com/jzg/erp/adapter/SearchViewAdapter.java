package com.jzg.erp.adapter;/**
 * author: gcc
 * date: 2016/6/29 20:13
 * email:
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.model.CarSourceData;

import java.util.List;

/**
 * author: guochen
 * date: 2016/6/29 20:13
 * email:
 */
public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.ViewHolder> {

    public List<CarSourceData.DataBean> datas;
    public Context context;

    public SearchViewAdapter(Context context, List<CarSourceData.DataBean> datas) {
        this.context = context;
        this.datas = datas;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.carsource_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position).getFullName());
        holder.mTextView1.setText(datas.get(position).getRegdate().substring(0, 9));
        holder.mTextView3.setText(datas.get(position).getBuyPrice() + "万元");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_text);
            mTextView1 = (TextView) view.findViewById(R.id.tv_time);
            mTextView2 = (TextView) view.findViewById(R.id.tv_mileage);
            mTextView3 = (TextView) view.findViewById(R.id.tv_money);

        }


    }
}
