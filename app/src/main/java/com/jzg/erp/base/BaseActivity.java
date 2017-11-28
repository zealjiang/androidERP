package com.jzg.erp.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.ui.activity.InputTextActivity;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.ShowDialogTool;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IBaseView;


/**
 * 所有Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements OnClickListener,IBaseView {
    public final String TAG = BaseActivity.this.getClass().getName();
    private TextView mTvTitle;
    protected TextView mTvBack;
    protected TextView mTvRight;
    protected ImageView mIvRight;
    private ImageView mIvBack;
    protected RelativeLayout rlTitle;
    public DisplayMetrics mDisplayMetrics;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivLeft || v.getId() == R.id.tvLeft) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JzgApp.getAppContext().add(this);
        mDisplayMetrics = getResources().getDisplayMetrics();
        initViews(savedInstanceState);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mTvRight = (TextView) findViewById(R.id.tvRight);
        mIvRight = (ImageView) findViewById(R.id.ivRight);
        mIvBack = (ImageView) findViewById(R.id.ivLeft);
        mTvBack = (TextView) findViewById(R.id.tvLeft);
        rlTitle = (RelativeLayout) findViewById(R.id.rlTitle);

        if (mTvRight != null) mTvRight.setOnClickListener(this);
        if (mIvRight != null) mIvRight.setOnClickListener(this);
        if (mIvBack != null) mIvBack.setOnClickListener(this);
        if (mTvBack != null) mTvBack.setOnClickListener(this);

        setData();
    }

    protected void hideTitle() {
        rlTitle.setVisibility(View.GONE);
    }


    /**
     * 初始化布局和控件
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 设置相关数据
     */
    protected abstract void setData();

    protected void showBack(boolean isShow) {
        if (isShow) {
            mIvBack.setVisibility(View.VISIBLE);
        } else {
            mIvBack.setVisibility(View.GONE);
        }
    }


    protected void setTextLeft(String text) {
        if (mIvBack != null) {
            mIvBack.setVisibility(View.GONE);
        }

        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setText(text);
    }

    protected void setTextLeft(int res) {
        setTextLeft(UIUtils.getString(res));
    }

    public void showLoading() {
        ShowDialogTool.showLoadingDialog(this);
    }

    public void hideLoading() {
        ShowDialogTool.dismissLoadingDialog();
    }


    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        mTvTitle.setText(title);
    }

    /**
     * 设置标题
     */
    @Override
    public void setTitle(int textId) {
        mTvTitle.setText(textId);
    }

    protected void setImageRight(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageDrawable(drawable);
    }


    protected void setTextRight(int resId) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(resId);
    }


    protected void setTextRight(String text) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(text);
    }

    protected void setTextRightColor(int color) {
        mTvRight.setTextColor(UIUtils.getColor(color));
    }

    /**
     * 不带Extra跳转activity
     *
     * @param clazz
     * @param needFinish 是否finish当前activity
     */
    protected void jump(Class<?> clazz, boolean needFinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (needFinish) {
            finish();
        }
    }

    /**
     * 带Extra跳转activity
     *
     * @param intent
     * @param needFinish 是否finish当前activity
     */
    protected void jump(Intent intent, boolean needFinish) {
        startActivity(intent);
        if (needFinish) {
            finish();
        }
    }


    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        JzgApp.getAppContext().remove(this);
        super.onDestroy();

    }

    public Intent newIntent(Class clazz) {
        return new Intent(this, clazz);
    }



    /**
     * 显示错误信息
     *
     * @param error 错误信息
     */
    @Override
    public void showError(String error) {
        if(!TextUtils.isEmpty(error))
            MyToast.showLong(error);
    }

    /**
     * 显示加载
     */
    @Override
    public void showDialog() {
        ShowDialogTool.showLoadingDialog(this);
    }

    /**
     * 关闭加载
     */
    @Override
    public void dismissDialog() {
        ShowDialogTool.dismissLoadingDialog();
    }

    protected void startInputTextActivity(int requestCode,String title, String hint,String oldData, int length,int inputType) {
        Intent intent = new Intent(this, InputTextActivity.class);
        intent.putExtra(Constant.INPUT_TITLE, title);
        intent.putExtra(Constant.INPUT_TIPS, hint);
        intent.putExtra(Constant.OLD_DATA, oldData);
        intent.putExtra(Constant.INPUT_LENGTH, length);
        intent.putExtra(Constant.INPUT_TYPE, inputType);
        startActivityForResult(intent, requestCode);
    }

}
