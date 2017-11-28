package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.utils.FileUtil;
import com.jzg.erp.widget.MActionSheet;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 *
 * @author zealjiang
 * @time 2016/6/24 17:00
 */
public class SettingActivity extends BaseActivity {

//    private static final String CLEAR_CACHE[] = {"确定清除缓存吗？", "确定"};
//    private static final String EXIT_LOGIN[] = {"确定退出登录吗？", "确定"};
    private static final String CLEAR_CACHE[] = { "确定"};
    private static final String EXIT_LOGIN[] = { "确定"};

    @Bind(R.id.tv_cache_size)
    TextView mTvCacheSize;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        setTitle("设置");

        int size = Fresco.getImagePipelineFactory().getBitmapCountingMemoryCache().getSizeInBytes();
        //Log.e("SettingActivity size: ",size+"");
        String fileSize = FileUtil.FormetFileSize(size);
        mTvCacheSize.setText(fileSize+"");
    }

    @OnClick(R.id.rl_change_pwd)
    void toChangePwdActivity(View view) {
        jump(new Intent(this, ChangePwdActivity.class), false);
    }

    @OnClick(R.id.rl_clear_cache)
    void clearCache(View view) {
        //弹出清除缓存框
        MActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setTitleText("确定清除缓存吗？")
                .setOtherButtonTitles(CLEAR_CACHE)
                .setCancelableOnTouchOutside(true)
                .setListener(new MActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(MActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                        clearCacheOperation(CLEAR_CACHE[index]);
                    }
                }).show();
    }

    @OnClick(R.id.btnExit)
    void exit(View view) {
        //弹出退出登录框
        MActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(EXIT_LOGIN)
                .setTitleText("确定退出登录吗？")
                .setCancelableOnTouchOutside(true)
                .setListener(new MActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(MActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                        exitLoginOperation(EXIT_LOGIN[index]);
                    }
                }).show();
    }

    private void clearCacheOperation(String itemName) {
        if (itemName.equals("确定")) {
            clearCache();

        }
    }

    /**
     * 清除Fresco中的图片缓存
     * @author zealjiang
     * @time 2016/7/4 10:02
     */
    private void clearCache() {
        Fresco.getImagePipelineFactory().getMainDiskStorageCache().clearAll();
        Fresco.getImagePipelineFactory().getSmallImageDiskStorageCache().clearAll();
        Fresco.getImagePipeline().clearCaches();
        Fresco.getImagePipeline().clearDiskCaches();
        Fresco.getImagePipeline().clearMemoryCaches();

        //刷新view
        mTvCacheSize.setText("0KB");
    }

    /**
     * 退出登录
     * @author zealjiang
     * @time 2016/7/4 10:06
     */
    private void exitLoginOperation(String itemName) {
        if (itemName.equals("确定")) {
            //退出
            exit();
        }
    }

    /**
     * 退出登录
     * @author zealjiang
     * @time 2016/7/4 10:07
     */
    private void exit() {
        //将SettingActivity从要Stack中移除，防止finishAll()时程序退出
        JzgApp.getAppContext().remove(this);
        //关掉Stack中的Activity
        JzgApp.getAppContext().finishAll();
        //跳转到登录页
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("login_out",true);
        startActivity(intent);
        //关掉本Activity
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
