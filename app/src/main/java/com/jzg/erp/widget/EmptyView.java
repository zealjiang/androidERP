package com.jzg.erp.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzg.erp.R;

/**
 * Created by JZG on 2016/6/28.
 */
public class EmptyView extends RelativeLayout{
    private TextView tv1;

    public EmptyView(Context context) {
        super(context);
        initView(context);
    }
    public EmptyView(Context context,String emptyStr) {
        super(context);
        initView(context);
        setEmptyText(emptyStr);
    }


    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, null);
        tv1 = (TextView) view.findViewById(R.id.tv_empty_view);
        this.addView(view);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.setGravity(Gravity.CENTER);
        this.setLayoutParams(params);
    }

    public void setEmptyText(String string){
        if(TextUtils.isEmpty(string)){
            return;
        }
        tv1.setText(string);
    }
}
