package com.jzg.erp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jzg.erp.R;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.utils.ScreenUtils;
import com.jzg.erp.widget.CustomTextView;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.ViewHolder;

import java.util.List;

/**
 * author: guochen
 * date: 2016/6/24 14:28
 * email:
 */
public class CarSourceAdapter extends CommonAdapter<CarSourceData.DataBean> {
    private List<CarSourceData.DataBean> datas;
    private Context context;

    public CarSourceAdapter(Context context, int layoutId, List<CarSourceData.DataBean> datas, XRecyclerView xrv) {
        super(context, layoutId, datas, xrv);
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void convert(ViewHolder holder, CarSourceData.DataBean dataBean) {
        SimpleDraweeView simpleDraweeView = holder.getView(R.id.sv_img);
        CustomTextView customTextView1 = holder.getView(R.id.sv_status);
        CustomTextView customTextView2 = holder.getView(R.id.sv_status1);
        CustomTextView customTextView3 = holder.getView(R.id.sv_status2);
        TextView tv_money = holder.getView(R.id.tv_money);
        holder.setText(R.id.tv_brand, dataBean.getFullName());
        String regdateStr = dataBean.getRegdateStr();
        if(TextUtils.isEmpty(regdateStr)|| !regdateStr.startsWith("2")){
            holder.setVisible(R.id.tv_time,false);
            holder.setText(R.id.tv_time, "");
        }else{
            holder.setVisible(R.id.tv_time,true);
            holder.setText(R.id.tv_time, regdateStr);
        }
        holder.setText(R.id.tv_mileage,dataBean.getMileageMKM()+"万公里");

        if (!TextUtils.isEmpty(dataBean.getPicPath())) {
            Uri uri = Uri.parse(dataBean.getPicPath());
            simpleDraweeView.setImageURI(uri);
        }else{
            simpleDraweeView.setImageURI("");
        }
        try {
            Float buyPrice = Float.parseFloat(dataBean.getBuyPriceFormat());
            if (buyPrice <= 0) {
                tv_money.setTextColor(Color.parseColor(Constant.NOMONEYTEXTCOLOR));
                holder.setText(R.id.tv_money, "价格待定");
            } else {
                tv_money.setTextColor(Color.parseColor(Constant.MONEYTEXTCOLOR));
                holder.setText(R.id.tv_money, dataBean.getBuyPriceFormat() + "万");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (!dataBean.isShowState()) {    //不显示子状态  已售出
            holder.setText(R.id.sv_status, dataBean.getCarStatus2Name());
            customTextView1.setTextColor(Color.parseColor(Constant.STATUSTEXTCOLOR1));
            customTextView1.setStrokeColorAndWidth(ScreenUtils.dip2px(context,1),R.color.yishouchubg);
            customTextView1.setSolidColor(Color.parseColor(Constant.STATUSYISHOUCHCOLOR));
            customTextView2.setVisibility(View.INVISIBLE);
            customTextView3.setVisibility(View.INVISIBLE);
        } else {  //显示子状态
            if (dataBean.getCarStatus2() == 1) {  //在售
                holder.setText(R.id.sv_status, dataBean.getCarStatus2Name());
                customTextView1.setStrokeColorAndWidth(ScreenUtils.dip2px(context,0),R.color.yishouchubg);
                customTextView1.setTextColor(Color.parseColor(Constant.STATUSTEXTCOLOR1));
                customTextView1.setSolidColor(Color.parseColor(Constant.STATUSZAISHOUCOLOR));
            } else if (dataBean.getCarStatus2() == 2) {   //已预订
                holder.setText(R.id.sv_status, dataBean.getCarStatus2Name());
                customTextView1.setStrokeColorAndWidth(ScreenUtils.dip2px(context,0),R.color.yishouchubg);
                customTextView1.setTextColor(Color.parseColor(Constant.STATUSTEXTCOLOR1));
                customTextView1.setSolidColor(Color.parseColor(Constant.STATUSYIYUDINGCOLOR));
            }



            if (dataBean.getIsPreparation() == 1) { //已整备
                customTextView2.setTextColor(Color.parseColor(Constant.STATUSTEXTCOLOR2));
                customTextView2.setStrokeColorAndWidth(ScreenUtils.dip2px(context,1),R.color.yishangjiastroke);
            } else if (dataBean.getIsPreparation() == 2) {  //未整备
                customTextView2.setTextColor(Color.parseColor(Constant.STATUSTEXTCOLORNO2));
                customTextView2.setStrokeColorAndWidth(ScreenUtils.dip2px(context,1),R.color.yixiajiastroke);
            }

            if (dataBean.getIsUpshelf() == 1) {  //已上架
                customTextView3.setTextColor(Color.parseColor(Constant.STATUSTEXTCOLOR2));
                customTextView3.setStrokeColorAndWidth(ScreenUtils.dip2px(context,1),R.color.yishangjiastroke);
            } else {  //未上架  已下架两种
                customTextView3.setTextColor(Color.parseColor(Constant.STATUSTEXTCOLORNO2));
                customTextView3.setStrokeColorAndWidth(ScreenUtils.dip2px(context,1),R.color.yixiajiastroke);
            }


            if (!TextUtils.isEmpty(dataBean.getIsPreparationName())) {  //整备
                customTextView2.setVisibility(View.VISIBLE);
                holder.setText(R.id.sv_status1, dataBean.getIsPreparationName());
            } else {
                customTextView2.setVisibility(View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(dataBean.getIsUpshelfName())) {   //上架
                holder.getView(R.id.sv_status2).setVisibility(View.VISIBLE);
                holder.setText(R.id.sv_status2, dataBean.getIsUpshelfName());
            } else {
                holder.getView(R.id.sv_status2).setVisibility(View.INVISIBLE);
            }

        }

    }


}
