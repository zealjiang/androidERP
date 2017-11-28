package com.jzg.erp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.receiver.ExampleUtil;
import com.jzg.erp.ui.fragment.CarSourceFragment;
import com.jzg.erp.ui.fragment.CustomerTabFragment;
import com.jzg.erp.ui.fragment.MineFragment;
import com.jzg.erp.ui.fragment.TaskTabFragment;
import com.jzg.erp.update.UpdateManager;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.widget.bottombar.BottomTab;
import com.jzg.erp.widget.bottombar.BottomTabGroup;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class HomeActivity extends BaseActivity implements BottomTabGroup.OnCheckedChangeListener {
    @Bind(R.id.bottom_bar_root)
    BottomTabGroup bottomBarRoot;
    private FragmentTransaction transaction;
    private TaskTabFragment taskFragment;
    private CustomerTabFragment customerFragment;
    private CarSourceFragment carSourceFragment;
    private MineFragment mineFragment;
    private Fragment fg = taskFragment;
    private static final int REQUEST_EXTERNAL_STORAGE = 999;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        BottomTab tab01 = (BottomTab) bottomBarRoot.getChildAt(0);
        BottomTab tab04 = (BottomTab) bottomBarRoot.getChildAt(3);
        tab04.setHint("3"); // 设置提示红点部分的文字
        tab01.setChecked(true);
        bottomBarRoot.setOnCheckedChangeListener(this);

        //检查更新
        if (Build.VERSION.SDK_INT >= 23) {
            applyPermission();
        } else {
            if (JzgApp.networkAvailable) {
                UpdateManager.getUpdateManager().checkAppUpdate(this, false);
            }
        }

    }

    @Override
    protected void setData() {
        transaction =getSupportFragmentManager().beginTransaction();
        if(taskFragment==null){
            taskFragment = new TaskTabFragment();
        }
        transaction.add(R.id.rlContainer,taskFragment);
        transaction.commit();

        //设置JPush别名
        setAlias();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String from = intent.getStringExtra("from");
        if("daiban".equals(from)){
            if(taskFragment==null){
                taskFragment = new TaskTabFragment();
            }
            changeTab(taskFragment);
            fg = taskFragment;
            BottomTab tab01 = (BottomTab) bottomBarRoot.getChildAt(0);
            tab01.setChecked(true);
        }
    }

    private void changeTab(Fragment fragment){
        transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rlContainer,fragment);
        transaction.commit();
    }
    @Override
    public void onCheckedChanged(BottomTabGroup root, int checkedId) {
        switch (checkedId) {
//            代办
            case R.id.tab_01:
                if(taskFragment==null){
                    taskFragment = new TaskTabFragment();
                }
                changeTab(taskFragment);
                fg = taskFragment;
                break;
//            客户
            case R.id.tab_02:
                if(customerFragment==null){
                    customerFragment = new CustomerTabFragment();
                }
                changeTab(customerFragment);
                fg = customerFragment;
                break;
//            车源
            case R.id.tab_03:
                if(carSourceFragment==null){
                    carSourceFragment = new CarSourceFragment();
                }
                changeTab(carSourceFragment);
                fg = carSourceFragment;
                break;
//            我
            case R.id.tab_04:
                if(mineFragment==null){
                    mineFragment = new MineFragment();
                }
                changeTab(mineFragment);
                fg = mineFragment;
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(fg instanceof CarSourceFragment){
            if(carSourceFragment.onKeyDown(keyCode, event)){
                return true;
            }
        }

             exitBy2Click();
            return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * 极光推送设置别名
     * @author zealjiang
     * @time 2016/7/11 18:10
     */
    private void setAlias(){
        String alias = JzgApp.getAppContext().getUser().getUserID()+"";
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            Toast.makeText(this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    //JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;
                default:
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            //ExampleUtil.showToast(logs, getApplicationContext());
        }

    };


    private void applyPermission() {
        //动态申请权限
        int checkCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (JzgApp.networkAvailable) {
                UpdateManager.getUpdateManager().checkAppUpdate(this, false);
            }
        } else {
            MyToast.showShort("SD卡读写授权被拒");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
