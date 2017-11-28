package com.jzg.erp.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.event.RecordAddOkEvent;
import com.jzg.erp.global.Constant;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.presenter.AddFollowUpPresenter;
import com.jzg.erp.utils.DateTimeUtils;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IAddFollowup;
import com.jzg.erp.widget.MActionSheet;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 添加跟进记录
 */
public class AddFollowUpRecActivity extends BaseActivity implements IAddFollowup{
    private static final String PREBUY_TIME[] = {"无等级", "H", "A", "B", "C"};
    private static final String DURATION[] = {"随时", "上午", "下午"};
    @Bind(R.id.tvPreBuyTime)
    AppCompatTextView tvPreBuyTime;
    @Bind(R.id.etFollowUpRec)
    AppCompatEditText etFollowUpRec;
    @Bind(R.id.tvNextFollowupTime)
    AppCompatTextView tvNextFollowupTime;
    @Bind(R.id.tvTimeDuration)
    AppCompatTextView tvTimeDuration;
    @Bind(R.id.tvExtraInfo)
    AppCompatTextView tvExtraInfo;
    @Bind(R.id.llExtraInfo)
    LinearLayout llExtraInfo;
    @Bind(R.id.tvVisitDate)
    AppCompatTextView tvVisitDate;
    @Bind(R.id.tvVisitDuration)
    AppCompatTextView tvVisitDuration;
    @Bind(R.id.llVisitContainer)
    LinearLayout llVisitContainer;
    @Bind(R.id.tvFailedReason)
    AppCompatTextView tvFailedReason;
    @Bind(R.id.llFailedContainer)
    LinearLayout llFailedContainer;
    @Bind(R.id.llPreBuyTimeContainer)
    LinearLayout llPreBuyTimeContainer;
    @Bind(R.id.llNextFollowup)
    LinearLayout llNextFollowup;
    @Bind(R.id.tvFailedReasonKey)
    AppCompatTextView tvFailedReasonKey;
    @Bind(R.id.tvFollowUpRecKey)
    AppCompatTextView tvFollowUpRecKey;
    @Bind(R.id.tvVisitDateKey)
    AppCompatTextView tvVisitDateKey;
    @Bind(R.id.tvNextFollowupTimeKey)
    AppCompatTextView tvNextFollowupTimeKey;
    private int type = 0;
    private Calendar calendar;
    private DatePickerDialog dialog;
    private AddFollowUpPresenter presenter;
    private String customerID;
    private String PreBuyTime;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_followup_rec);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        presenter = new AddFollowUpPresenter(this);
        customerID = getIntent().getStringExtra("customerID");
        PreBuyTime = getIntent().getStringExtra("PreBuyTime");
    }

    @Override
    protected void setData() {
        setTitle("填写跟进记录");
        setTextRight("完成");
        type = getIntent().getIntExtra(IntentKey.KEY_REC_TYPE, IntentKey.REC_TYPE_NEW);
        if (type == IntentKey.REC_TYPE_NEW) {
            llVisitContainer.setVisibility(View.VISIBLE);
            llNextFollowup.setVisibility(View.GONE);
        } else if (type == IntentKey.REC_TYPE_FAILED) {
            llFailedContainer.setVisibility(View.VISIBLE);
            llPreBuyTimeContainer.setVisibility(View.GONE);
            llNextFollowup.setVisibility(View.GONE);
        }else{
            tvNextFollowupTimeKey.setText("下次跟进日期*");
            setTextViewNeccessary(tvNextFollowupTimeKey,6);
            llNextFollowup.setVisibility(View.VISIBLE);
        }
        setTextViewNeccessary(tvFailedReasonKey,4);
        setTextViewNeccessary(tvFollowUpRecKey,4);
        setTextViewNeccessary(tvVisitDateKey,6);
        tvPreBuyTime.setText(PREBUY_TIME[0]);
        tvTimeDuration.setText(DURATION[0]);
        tvVisitDuration.setText(DURATION[0]);
        if(TextUtils.isEmpty(PreBuyTime)){
            tvPreBuyTime.setText(PREBUY_TIME[0]);
        }else{
            tvPreBuyTime.setText(PreBuyTime);
        }
    }

    private void setTextViewNeccessary(TextView tv, int index) {
        CharSequence text = tv.getText();
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(Color.RED), index, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }


    @OnClick({ R.id.ivLeft,R.id.llPreBuyTime, R.id.llNextFollowupTime, R.id.llTimeDuration, R.id.llExtraInfo, R.id.llVisitDate, R.id.llVisitDuration, R.id.llFailedReason})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.llPreBuyTime:
                MActionSheet.createBuilder(AddFollowUpRecActivity.this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles(PREBUY_TIME)
                        .setCancelableOnTouchOutside(true)
                        .setListener(new MActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(MActionSheet actionSheet, boolean isCancel) {
                            }

                            @Override
                            public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                                tvPreBuyTime.setText(PREBUY_TIME[index]);
                            }
                        }).show();
                break;
            case R.id.llNextFollowupTime:
                dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        boolean flag = DateTimeUtils.laterThanNow(year, monthOfYear, dayOfMonth);
                        if (flag) {
                            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            dialog.dismiss();
                            tvNextFollowupTime.setText(date);
                            LogUtil.e(TAG, date);
                        } else {
                            MyToast.showLong(getString(R.string.selected_date_cannot_before_than_today));
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

                break;
            case R.id.llTimeDuration:
                MActionSheet.createBuilder(AddFollowUpRecActivity.this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles(DURATION)
                        .setCancelableOnTouchOutside(true)
                        .setListener(new MActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(MActionSheet actionSheet, boolean isCancel) {
                            }

                            @Override
                            public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                                tvTimeDuration.setText(DURATION[index]);
                            }
                        }).show();
                break;
            case R.id.llExtraInfo:
                startInputTextActivity(0,"编辑特殊说明", "最多可输入20个字",tvExtraInfo.getText().toString().trim(),  20,-1);
                break;
            case R.id.llVisitDate:
                dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        boolean flag = DateTimeUtils.laterThanNow(year, monthOfYear, dayOfMonth);
                        if (flag) {
                            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            dialog.dismiss();
                            tvVisitDate.setText(date);
                            LogUtil.e(TAG, date);
                        } else {
                            MyToast.showLong(R.string.selected_date_cannot_before_than_today);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case R.id.llVisitDuration:
                MActionSheet.createBuilder(AddFollowUpRecActivity.this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles(DURATION)
                        .setCancelableOnTouchOutside(true)
                        .setListener(new MActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(MActionSheet actionSheet, boolean isCancel) {
                            }

                            @Override
                            public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                                tvVisitDuration.setText(DURATION[index]);
                            }
                        }).show();
                break;
            case R.id.llFailedReason:
                Intent intent = new Intent(this, SelectFailedReasonActivity.class);
//                intent.putExtra("FailedReason",tvFailedReason.getText().toString().trim());
                startActivityForResult(intent, IntentKey.REQ_CODE_FAILED_REASON);
                break;
            case R.id.tvRight:
                checkAndSubmit();
                break;
        }
    }

    private void checkAndSubmit(){
        String preBuyTime = tvPreBuyTime.getText().toString().trim();//预购时间
        String nextFollowupTime = tvNextFollowupTime.getText().toString().trim();//下次跟进时间
        String timeDuration = tvTimeDuration.getText().toString().trim();//下次跟进时间段
        String followUpRec = etFollowUpRec.getText().toString().trim();//跟进记录
        String failedReason = tvFailedReason.getText().toString().trim();//战败原因
        String visitDate = tvVisitDate.getText().toString().trim();//预约到店日期
        String visitDuration = tvVisitDuration.getText().toString().trim();//预约到店日期时间段
        String extraInfo = tvExtraInfo.getText().toString().trim();//特殊说明

        if(TextUtils.isEmpty(followUpRec)){
            MyToast.showShort("请填写跟进记录!");
            return;
        }
        if(followUpRec.length()>300){
            MyToast.showShort("跟进记录控制在300字内!");
            return;
        }
        if (type == IntentKey.REC_TYPE_NEW) {//预约到店
            if(TextUtils.isEmpty(visitDate)){
                MyToast.showShort("请选择预约到店日期!");
                return;
            }
        } else if (type == IntentKey.REC_TYPE_FAILED) {
            if(TextUtils.isEmpty(failedReason)){
                MyToast.showShort("请选择战败原因");
                return;
            }
        }else{
            if(TextUtils.isEmpty(nextFollowupTime)){
                MyToast.showShort("请选下次跟进日期");
                return;
            }
        }

        Map<String,String> params = new HashMap<>();
        params.put("CustomerId",customerID);
        params.put("CreateUser", JzgApp.getUser().getTrueName());//创建跟进记录者用户名
        params.put("CreateUserID", String.valueOf(JzgApp.getUser().getUserID()));//创建跟进记录者UserId
        params.put("userID", String.valueOf(JzgApp.getUser().getUserID()));//创建跟进记录者UserId
        if (type == IntentKey.REC_TYPE_NEW) {
            params.put("FollowUpStatus","预约到店");
        } else if (type == IntentKey.REC_TYPE_FAILED) {
            params.put("FollowUpStatus","战败");
        }else{
            params.put("FollowUpStatus","继续跟进");
        }
        params.put("FollowingRecord",followUpRec);//跟进记录
        if(!TextUtils.isEmpty(failedReason)){
            params.put("FaliureReason",failedReason);//战败原因
        }
        if(!TextUtils.isEmpty(visitDate)){//如果预约到店日期不为空，则当前跟进记录的类型是预约到店
            params.put("OnShopDate",visitDate);//预约到店日期
            params.put("OnShopTime",visitDuration);//预约到店时间段
        }
        if(!TextUtils.isEmpty(nextFollowupTime)){//如果下次跟进日期不为空，则当前跟进记录的类型是继续跟进，继续跟进和预约到店是互斥的，只可能是其中一种情况
            params.put("NextShopDate",nextFollowupTime);//下次跟进日期
            params.put("NextShopTime",timeDuration);
            params.put("SpecialInstruction",extraInfo);//特殊说明
        }
        params.put("CustomerLevel",preBuyTime);

        Map<String, Object> maps = new HashMap<>();
        maps.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(maps));
        String url = UIUtils.getUrl(params);
        LogUtil.e(TAG,url);
        presenter.addFollowUpLog(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if(requestCode == IntentKey.REQ_CODE_FAILED_REASON){
                String result = data.getStringExtra(IntentKey.KEY_RESULT);
                if (!TextUtils.isEmpty(result)) {
                    tvFailedReason.setText(result);
                }
            }else if(requestCode==0){
                String result = data.getStringExtra(Constant.ACTIVITY_INPUT);
                if (!TextUtils.isEmpty(result)) {
                    tvExtraInfo.setText(result);
                }
            }
        }
    }

    @Override
    public void succeed() {
        EventBus.getDefault().post(new RecordAddOkEvent(RecordAddOkEvent.EVENT_TYPE_REC,tvPreBuyTime.getText().toString().trim()));
        finish();
    }
}
