package com.jzg.erp.http;


import com.jzg.erp.base.BaseObject;
import com.jzg.erp.model.AccountInf;
import com.jzg.erp.model.BuyCarIntent;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.CarSourceTagData;
import com.jzg.erp.model.CustomerData;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.model.MatchCustomerData;
import com.jzg.erp.model.TaskItemGroupWrapper;
import com.jzg.erp.model.UserWrapper;
import com.jzg.erp.model.WaitingItemWrapper;
import com.jzg.erp.update.UpdateApp;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;


/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/7 10:26
 * @desc:ERP本App相关接口
 */
public interface ApiServer {

    //测试地址
//    public  static final String BASE_URL="http://carapp.jetonda.com:100";
//    public  static final String BASE_URL="http://jtdapp.sandbox.guchewang.com";
    //线上地址
    public  static final String BASE_URL="http://carapp.jetonda.com:94";


    @FormUrlEncoded
    @POST("/Account/UserAccount.ashx") //登录
    public Observable<UserWrapper> login(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/Account/UserAccount.ashx") //帐户信息
    public Observable<AccountInf> getAccountInf(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("/customer/todolist.ashx")//车源列表
    public Observable<CarSourceData> loadCarSource(@FieldMap Map<String, String> params);

    @Multipart
    @POST("/Account/UserAccount.ashx?") //上传头像
    public Observable<AccountInf> updateImage(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("/Account/UserAccount.ashx") //修改密码
    public Observable<BaseObject> changePwd(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //客户信息详情
    public Observable<CustomerDetail> getCustomerDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //修改购车意向
    public Observable<BuyCarIntent> modifyBuyCarIntent(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //获取关注车辆列表
    public Observable<CarSourceData> getFocusCarList(@FieldMap Map<String, String> params);
//    /customer/todolist.ashx?op=getIntendCustomerBySellerIDAndCarID&carSourceID=1&pageIndex=1&pagecount=15&userId=129

    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //获取未关注车辆的用户
    public Observable<CarSourceData> getIntendCustomerBySellerIDAndCarID(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //获取带关注车辆列表
    public Observable<CarSourceTagData> getFocusTagCarList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //客户列表
    public Observable<CustomerInfo> getCustomerList(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //获取关注此车辆的用户列表
    public Observable<CustomerData> getChooseCustomer(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/Customer/ToDoList.ashx") //批量添加关注此车辆的客户
    public Observable<CustomerData> addChooseCustomer(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/customer/todolist.ashx")//车源模糊搜索
    public Observable<CarSourceData> loadSearchDatas(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/followup/AddFollowUpLog.ashx")//添加跟进记录
    public Observable<BaseObject> addFollowUpLog(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/followup/AddFollowUpItem.ashx")//添加跟进记录
    public Observable<BaseObject> addFollowUpItem(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/followup/GetCarConcernUserList.ashx")//匹配客户
    public Observable<MatchCustomerData> loadMatchCustomer(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/followup/GetFollowUpList.ashx")//获取跟进记录
    public Observable<TaskItemGroupWrapper> getFollowUpList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/followup/GetFollowUpItemList.ashx")//获取跟进事项
    public Observable<TaskItemGroupWrapper> getFollowUpItemList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/customer/todolist.ashx")//插入通话记录
    public Observable<BaseObject> insertDialRecord(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/followup/GetWaitDoneItems.ashx")//获取待办事项
    public Observable<WaitingItemWrapper> getWaitDoneItems(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/followup/GetWaitDoneItems.ashx")//改变待办事项的查看状态
    public Observable<BaseObject> changeTaskItemStatus(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("/APPUpdate/APPUpdateVersion.ashx")//改变待办事项的查看状态
    public Observable<UpdateApp> isUpdate(@FieldMap Map<String, String> params);


}
