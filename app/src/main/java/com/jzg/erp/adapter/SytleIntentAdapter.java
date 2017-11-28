package com.jzg.erp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jzg.erp.R;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by JZG on 2016/6/27.
 */
public class SytleIntentAdapter extends CommonAdapter<String>{

    public List<String> datas;

    private SytleIntentAdapter adapter;
    public SytleIntentAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        adapter = this;
    }

    public void update( List<String> datas){
        this.datas = datas;
        adapter.notifyItemInserted(0);
    }

    @Override
    public void convert(final ViewHolder holder, String s) {
        holder.setText(R.id.masonry_item_title,s);
        ImageView img = holder.getView(R.id.masonry_item_img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(holder.getAdapterPosition());
                adapter.notifyItemRemoved(holder.getAdapterPosition());
            }
        });

    }
}
