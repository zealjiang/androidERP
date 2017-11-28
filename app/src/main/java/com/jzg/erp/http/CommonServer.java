package com.jzg.erp.http;/**
 * Created by voiceofnet on 2016/6/27.
 */

import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.MakeList;
import com.jzg.erp.model.ModelList;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/27 15:40
 * @desc: 调用其他项目公共接口数据服务
 */
public interface CommonServer {
    /**
     * 监测平台
     * @author zealjiang
     * @time 2016/7/14 11:43
     */  
    //public static final String BASE_URL = "http://ptvapi.guchewang.com";

    /**
     * 精真估
     * @author zealjiang
     * @time 2016/7/14 11:44
     */
    //public static final String BASE_URL = "http://api.jingzhengu.com";

    /**
     * 后台
     * @author zealjiang
     * @time 2016/7/14 11:44
     */
    public static final String BASE_URL = "http://ptvapi.guchewang.com";

    //车源品牌
    //监测平台
     //http://ptvapi.guchewang.com/APP/GetMakeModelStyleAll.ashx?op=GetMake&InSale=1&userId=53&sign=B682C112421037AF32D84A5B9ABB4B45
     //http://ptvapi.guchewang.com/APP/GetMakeModelStyleAll.ashx?op=GetModel&makeId=9&InSale=1&userId=53&sign=C6DD8F8A4EC189555D1BF6876183F25C
    //精真估
//    http://api.jingzhengu.com/APP/PublicInt/GetMakeAndModelAndStyle.ashx?op=GetMake&InSale=0
//    http://api.jingzhengu.com/APP/PublicInt/GetMakeAndModelAndStyle.ashx?op=GetModel&MakeId=175&InSale=0

    //后台服务器
    //品牌
    //http://testptvapi.guchewang.com/APP/GetMakeModelStyleAllWeb.ashx?op=GetMake&InSale=1
    //车系
    //http://testptvapi.guchewang.com/APP/GetMakeModelStyleAllWeb.ashx?op=GetModel&makeId=2&InSale=1


    @FormUrlEncoded
    @POST("/customer/todolist.ashx")//车源模糊搜索
    public Observable<CarSourceData> loadSearchDatas(@FieldMap Map<String, String> params);

    //精真估
    @FormUrlEncoded
    @POST("/APP/PublicInt/GetMakeAndModelAndStyle.ashx")
    public Observable<MakeList> getBrand(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/APP/PublicInt/GetMakeAndModelAndStyle.ashx")
    public Observable<ModelList> getModel(@FieldMap Map<String, String> params);


    //后台
    @FormUrlEncoded
    @POST("/APP/GetMakeModelStyleAllWeb.ashx")
    public Observable<MakeList> getBrandFromService(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/APP/GetMakeModelStyleAllWeb.ashx")
    public Observable<ModelList> getModelFromService(@FieldMap Map<String, String> params);

}
