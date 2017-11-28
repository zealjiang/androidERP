package com.jzg.erp.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.event.RecordAddOkEvent;
import com.jzg.erp.global.Constant;
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
 * 添加跟进事项页面
 */
public class AddFollowupItemActivity extends BaseActivity implements IAddFollowup{
    @Bind(R.id.tvNextFollowupTime)
    AppCompatTextView tvNextFollowupTime;
    @Bind(R.id.tvTimeDuration)
    AppCompatTextView tvTimeDuration;
    @Bind(R.id.tvExtraInfo)
    AppCompatTextView tvExtraInfo;
    @Bind(R.id.tvNextFollowupTimeKey)
    AppCompatTextView tvNextFollowupTimeKey;
    private static final String DURATION[] = {"随时", "上午", "下午"};
    private Calendar calendar;
    private DatePickerDialog dialog;
    private AddFollowUpPresenter presenter;
    private String customerID;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_followup_item);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        presenter = new AddFollowUpPresenter(this);
        customerID = getIntent().getStringExtra("customerID");
    }

    @Override
    protected void setData() {
        setTitle("填写跟进事项");
        setTextRight("完成");
        tvNextFollowupTimeKey.setText("下次跟进日期*");
        setTextViewNeccessary(tvNextFollowupTimeKey,6);
    }

    private void setTextViewNeccessary(TextView tv, int index) {
        CharSequence text = tv.getText();
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(Color.RED), index, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }
    private void checkAndSubmit(){
        String nextFollowupTime = tvNextFollowupTime.getText().toString().trim();//下次跟进时间
        String timeDuration = tvTimeDuration.getText().toString().trim();//下次跟进时间段
        String extraInfo = tvExtraInfo.getText().toString().trim();//特殊说明
        if(TextUtils.isEmpty(nextFollowupTime)){
            MyToast.showShort("请选下次跟进日期");
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("CustomerId",customerID);
        params.put("CreateUser", JzgApp.getUser().getTrueName());//创建跟进记录者用户名
        params.put("CreateUserID", String.valueOf(JzgApp.getUser().getUserID()));//创建跟进记录者UserId
        params.put("userID", String.valueOf(JzgApp.getUser().getUserID()));//创建跟进记录者UserId
        if(!TextUtils.isEmpty(extraInfo))
            params.put("SpecialInstruction",extraInfo);//特殊说明
        params.put("NextShopDate",nextFollowupTime);//下次跟进时间
        params.put("NextShopTime",timeDuration);
        params.put("FollowUpItemState","0");

        Map<String, Object> maps = new HashMap<>();
        maps.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(maps));
        String url = UIUtils.getUrl(params);
        LogUtil.e(TAG,url);
        presenter.addFollowUpLog(params);
    }

    @OnClick({R.id.ivLeft,R.id.tvRight, R.id.llNextFollowupTime, R.id.llTimeDuration, R.id.llExtraInfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                checkAndSubmit();
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
                MActionSheet.createBuilder(AddFollowupItemActivity.this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles(DURATION)
                        .setCancelableOnTouchOutside(true)
                        .setListener(new MActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(MActionSheet actionSheet, boolean isCancel) {}
                            @Override
                            public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                                tvTimeDuration.setText(DURATION[index]);
                            }
                        }).show();
                break;
            case R.id.llExtraInfo:
                startInputTextActivity(0,"编辑特殊说明", "最多可输入20个字","",  20, -1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode==0) {
            String result = data.getStringExtra(Constant.ACTIVITY_INPUT);
            if (!TextUtils.isEmpty(result)) {
                tvExtraInfo.setText(result);
            }
        }
    }

    @Override
    public void succeed() {
        EventBus.getDefault().post(new RecordAddOkEvent(RecordAddOkEvent.EVENT_TYPE_ITEM,"添加跟进事项成功"));
        finish();
    }
}
