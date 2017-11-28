package com.jzg.erp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.jzg.erp.global.Constant;
import com.jzg.erp.http.ApiServer;
import com.jzg.erp.http.CommonServer;
import com.jzg.erp.http.CustomerOkHttpClient;
import com.jzg.erp.http.OtherServer;
import com.jzg.erp.model.UserWrapper;
import com.jzg.erp.utils.ACache;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/5/23 14:18
 * @desc:
 */
public class JzgApp extends Application {
    private static JzgApp app;
    private static Stack<Activity> actStack;
    /**
     * 网络连接
     */
    private static ApiServer apiServer;
    private static CommonServer commonServer;
    /**
     * 判断是否有网络
     */
    public static boolean networkAvailable = true;
    /**
     * 判断是否是wifi还是移动网络
     */
    public static NetStatus isWifi = NetStatus.WIFI;
    /**
     * 内存泄漏检测
     */
//    public RefWatcher refWatcher;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    /**
     * 网络状态 分别代表wifi、wifi无网络、运营商网络
     */
    public enum NetStatus {
        WIFI,
        WIFI_NO_INTERNET,
        MOBILE_INTERNET
    }

    //登录的用户对象，用get方法获取，如果返回为空，从Acache中获取
    private static UserWrapper.User mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();

        Logger.init("JzgApp").methodCount(3);

        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        initShare();

    }

    private void initShare(){
        UMShareAPI.get(this);
        //微信
        String wxAppId = "wxc699fd850ba68f44";
        String wxAppSecret = "970f53336346f0ffbc05130d210a92ca";
        PlatformConfig.setWeixin(wxAppId, wxAppSecret);
        //新浪微博
        String sinaAppId = "310300615";
        String sinaAppSecret = "12c82a9b7c83315d05bdf720a80998ea";
        PlatformConfig.setSinaWeibo(sinaAppId,sinaAppSecret);
    }


    public static JzgApp getAppContext() {
        return app;
    }

    public static ApiServer getApiServer() {
        return apiServer;
    }

    public static CommonServer getCommonServer() {
        return commonServer;
    }

    /**
     * 初始化网络连接
     */
    public void initApiServer() {
        OkHttpClient client = CustomerOkHttpClient.getClient();
        Retrofit retrofit = null;
        if (apiServer == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiServer.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServer = retrofit.create(ApiServer.class);
        }

    }

    public void initCommonServer() {
        OkHttpClient client = CustomerOkHttpClient.getClient();
        Retrofit retrofit = null;
        if (commonServer == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(CommonServer.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            commonServer = retrofit.create(CommonServer.class);
        }

    }

    /**
     * @author zealjiang
     * @time 2016/6/28 13:51
     */
    public OtherServer initOtherServer(String baseUrl) {
        OkHttpClient client = CustomerOkHttpClient.getClient();
        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(OtherServer.class);

    }

    /**
     * 初始化网络监听
     */
    public void initNetworkStatusDetector() {
        ReactiveNetwork reactiveNetwork = new ReactiveNetwork();
        reactiveNetwork.observeConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ConnectivityStatus>() {
                    @Override
                    public void call(ConnectivityStatus connectivityStatus) {
                        //判断当前网络状态是否有效
                        if (connectivityStatus.toString().equals(ConnectivityStatus.OFFLINE.toString()) || connectivityStatus.toString().equals(ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET.toString())) {
                            Toast.makeText(getApplicationContext(), "没有网络", Toast.LENGTH_SHORT).show();
                            networkAvailable = false;
                        } else {
                            networkAvailable = true;
                        }

                        //判断当前网络状态
                        if ((connectivityStatus.toString().equals(ConnectivityStatus.WIFI_CONNECTED.toString()) && connectivityStatus.toString().equals(ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET.toString()))
                                || connectivityStatus.toString().equals(ConnectivityStatus.OFFLINE.toString())) {
                            isWifi = NetStatus.WIFI_NO_INTERNET; //连接了wifi,且无线不能用 || 网络不可用
                        } else if (connectivityStatus.toString().equals(ConnectivityStatus.MOBILE_CONNECTED.toString())) {
                            isWifi = NetStatus.MOBILE_INTERNET; //连接了移动网络
                        } else {
                            isWifi = NetStatus.WIFI;//wifi网络
                        }
                    }
                });
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    public void add(Activity instance) {
        if (actStack == null)
            actStack = new Stack<Activity>();
        final int index = actStack.lastIndexOf(instance);
        if (index >= 0) {
            actStack.remove(index);
        }
        actStack.push(instance);
    }

    public void remove(Activity instance) {
        if (actStack == null)
            return;
        actStack.remove(instance);
    }

    /**
     * 推送用到
     *
     * @author zealjiang
     * @time 2016/7/12 17:43
     */
    public Activity getCurActivity() {
        if (actStack == null)
            return null;
        if (actStack.size() <= 0) {
            return null;
        }
        return actStack.pop();
    }

    public void finishAll() {
        if (actStack == null)
            return;
        for (int i = 0; i < actStack.size(); i++) {
            Activity activity = actStack.get(i);
            activity.finish();
        }
        actStack.clear();

    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    /**
     * 获取用户对象
     *
     * @author zealjiang
     * @time 2016/6/28 11:05
     */
    public static UserWrapper.User getUser() {
        if (mUser == null) {
            mUser = (UserWrapper.User) ACache.get(app).getAsObject(Constant.KEY_ACache_UserWrapper);
        }
        return mUser;
    }

    public static void setUser(UserWrapper.User mUserWrapper) {
        JzgApp.mUser = mUserWrapper;
    }


}
