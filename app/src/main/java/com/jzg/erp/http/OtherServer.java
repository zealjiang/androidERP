package com.jzg.erp.http;/**
 * Created by voiceofnet on 2016/6/27.
 */

import com.jzg.erp.model.City;
import com.jzg.erp.model.Province;
import com.jzg.erp.model.XianqianCity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 需要在使用时指定baseUrl
 * @author zealjiang
 * @time 2016/6/28 14:34
 */
public interface OtherServer {
    
    @FormUrlEncoded
    @POST("/app/area/GetProv.ashx") //获取省数据 by zealjiang
    public Observable<Province> getProvince(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/app/area/GetAreaList.ashx") //获取市数据 by zealjiang
    public Observable<City> getCity(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/app/bbs/CarSource/GetCityStandard.ashx") //限迁城市查询 by zealjiang
    public Observable<XianqianCity> getXianqianCity(@FieldMap Map<String, String> params);
}
