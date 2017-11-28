package com.jzg.erp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.ShowDialogTool;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IBaseView;


public abstract class NewBaseFragment extends Fragment implements View.OnClickListener, IBaseView {
    protected String TAG = NewBaseFragment.this.getClass().getSimpleName();
    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    protected ImageView ivRight;
    private ImageView ivLeft;
    private RelativeLayout rlTitle;
    protected BaseActivity context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initViews(inflater, container, savedInstanceState);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvRight = (TextView) view.findViewById(R.id.tvRight);
        ivRight = (ImageView) view.findViewById(R.id.ivRight);
        ivLeft = (ImageView) view.findViewById(R.id.ivLeft);
        tvLeft = (TextView) view.findViewById(R.id.tvLeft);
        rlTitle = (RelativeLayout) view.findViewById(R.id.rlTitle);
        if (tvRight != null) tvRight.setOnClickListener(this);
        if (ivRight != null) ivRight.setOnClickListener(this);
        if (ivLeft != null) ivLeft.setOnClickListener(this);
        if (tvLeft != null) tvLeft.setOnClickListener(this);
        return view;
    }

    protected void hideTitle() {
        if (rlTitle != null)
            rlTitle.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
    }

    /***
     * run in onCreateView
     */
    protected abstract void setView();

    /***
     * run in onCreateView
     */
    protected abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /***
     * run in onCreate
     */
    protected abstract void initData();

    protected void showBack(boolean isShow) {
        if (isShow) {
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.GONE);
        }
    }

    /***
     * 设置并显示title左边文字，隐藏左边图标
     *
     * @param text
     */
    protected void setTextLeft(String text) {
        if (ivLeft != null) {
            ivLeft.setVisibility(View.GONE);
        }
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
    }

    /***
     * 设置并显示title左边文字，隐藏左边图标
     *
     * @param res 文字资源id
     */
    protected void setTextLeft(int res) {
        setTextLeft(UIUtils.getString(res));
    }

    /***
     * 设置并显示title右边文字，隐藏右边图标
     *
     * @param text
     */
    protected void setTextRightt(String text) {
        if (ivLeft != null) {
            ivLeft.setVisibility(View.GONE);
        }
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
    }

    /***
     * 设置并显示title右边文字，隐藏右边图标
     *
     * @param res 文字资源id
     */
    protected void setTextRightt(int res) {
        setTextLeft(UIUtils.getString(res));
    }

    /***
     * 设置并显示title左边图标，隐藏左边文字
     *
     * @param res 图标资源id
     */
    public void setImageLeft(int res) {
        if (tvLeft != null) {
            tvLeft.setVisibility(View.GONE);
        }
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(res);
    }

    /***
     * 设置并显示title右边图标，隐藏右边文字
     *
     * @param res 图标资源id
     */
    public void setImageRight(int res) {
        if (tvRight != null) {
            tvRight.setVisibility(View.GONE);
        }

        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(res);
    }

    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置标题
     */
    public void setTitle(int textId) {
        tvTitle.setText(textId);
    }


    protected void jump(Class dest) {
        Intent intent = new Intent(getActivity(), dest);
        startActivity(intent);
    }

    protected void jump(Intent intent) {
        startActivity(intent);
    }

    public void showLoading() {
        ShowDialogTool.showLoadingDialog(context);
    }

    public void hideLoading() {
        ShowDialogTool.dismissLoadingDialog();
    }

    /**
     * 显示错误信息
     *
     * @param error 错误信息
     */
    @Override
    public void showError(String error) {
        LogUtil.e(TAG,error);
    }

    /**
     * 显示加载
     */
    @Override
    public void showDialog() {
        ShowDialogTool.showLoadingDialog(context);
    }

    /**
     * 关闭加载
     */
    @Override
    public void dismissDialog() {
        ShowDialogTool.dismissLoadingDialog();
    }

    @Override
    public void onClick(View v) {

    }
}
