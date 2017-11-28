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
public class ErrorView extends RelativeLayout{

    private TextView tv1;
    private CustomRippleButton btn;
    private OnErrorListener errorListener;
    public void setOnErrorListener(OnErrorListener errorListener){
        this.errorListener = errorListener;
    }

    public ErrorView(Context context,String emptyStr) {
        super(context);
        initView(context);
        setErrorText(emptyStr);
    }
    public ErrorView(Context context) {
        super(context);
        initView(context);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.error_layout, null);
        tv1 = (TextView) view.findViewById(R.id.tv_empty_view);
        btn = (CustomRippleButton) view.findViewById(R.id.btnError);
        this.addView(view);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.setGravity(Gravity.CENTER);
        this.setLayoutParams(params);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                errorListener.onError();
            }
        });
    }
    public void setErrorText(String string){
        if(TextUtils.isEmpty(string)){
            return;
        }
        tv1.setText(string);
    }
    public interface OnErrorListener{
        void onError();
    }

}
