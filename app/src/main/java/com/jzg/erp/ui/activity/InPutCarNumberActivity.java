package com.jzg.erp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by jzg on 2015/12/23.
 * vin码、车牌号等输入界面
 */
public class InPutCarNumberActivity extends BaseActivity {

    @Bind(R.id.input_edit)
    EditText input_edit;
    @Bind(R.id.input_text_show)
    TextView input_text_show;

    int requestCode = 0;
    String title = "";
    String hint = "";
    /**
     * -1默认，0代表VIN，1手机号,
     */
    int input_type = -1; //
    int input_length = 5000;
    String showText = "";


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        init();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input_edit.getWindowToken(), 0);
        finish();
    }

    public void init() {
        requestCode = getIntent().getIntExtra(Constant.INPUT_REQUESTCODE, 0);
        title = getIntent().getStringExtra(Constant.INPUT_TITLE);
        hint = getIntent().getStringExtra(Constant.INPUT_HINT);
        showText = getIntent().getStringExtra(Constant.SHOWTEXT);
        input_type = getIntent().getIntExtra(Constant.INPUT_TYPE, -1);
        input_length = getIntent().getIntExtra(Constant.INPUT_LENGTH, 5000);
        setTitle(title);
        input_edit.setText(showText);
        input_text_show.setText(hint);
        int inputType = 1;
        if (input_type == 0) {
            //vin,设置只能输入的字符


            input_edit.setKeyListener(new NumberKeyListener() {
                @Override
                protected char[] getAcceptedChars() {
                    // TODO Auto-generated method stub
                    return new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
                }

                @Override
                public int getInputType() {
                    // TODO Auto-generated method stub
                    return 1;
                }
            });
            input_edit.setTransformationMethod(new InputLowerToUpper());

        } else if (input_type == 1 || input_type == 5 || input_type == 6) {
            //数字
            inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL;
            input_edit.setInputType(inputType);
        } else if (input_type == 2) {
            inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
            input_edit.setInputType(inputType);
            priceTextWatcher(input_edit);

        } else if (input_type == 7) {
            input_edit.setTransformationMethod(new InputLowerToUpper());
        } else {
           /* inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
            etContent.setInputType(inputType);*/
        }
        //限制输入的长度
        if (input_length >= 0) {
            input_edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(input_length)});
        }

        //光标移动到最后面
        Editable etext = input_edit.getText();
        Selection.setSelection(etext, etext.length());
//        etContent.setFocusable(true);
//        etContent.setFocusableInTouchMode(true);

        Observable.timer(100, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
//                getMvpView().toTop();
                        input_edit.requestFocus();
                        InputMethodManager imm = (InputMethodManager) input_edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }
                });
    }

    public class InputLowerToUpper extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] lower = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return lower;
        }

        @Override
        protected char[] getReplacement() {
            char[] upper = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return upper;
        }

    }



    /**
     * 保存
     * vin_submit
     *
     * @param
     * @return void
     * @throws
     * @Title: vin_submit
     * @Description: TODO
     */
    public void input_submit() {

        String intput = input_edit.getText().toString();
        if (TextUtils.isEmpty(intput)) {

            Toast.makeText(InPutCarNumberActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent();
            if (input_type == 1) {
                if (isPhoneNumberValid(intput)) {
                    intent.putExtra(Constant.ACTIVITY_INPUT, intput);
                } else {
                    Toast.makeText(InPutCarNumberActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (input_type == 0) {
                if (intput.length() != 17) {

                    Toast.makeText(InPutCarNumberActivity.this, "Vin只能为17位", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent.putExtra(Constant.ACTIVITY_INPUT, intput.toUpperCase());
                }
            } else if (input_type == 6) {
                intent.putExtra(Constant.ACTIVITY_INPUT, intput.toUpperCase());
            } else if (input_type == 7) {
                intput = intput.toUpperCase();
                if (isChePai(intput)) {
                    intent.putExtra(Constant.ACTIVITY_INPUT, intput);
                } else {
                    Toast.makeText(InPutCarNumberActivity.this, "车牌号码不合法！", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                intent.putExtra(Constant.ACTIVITY_INPUT, intput);
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(input_edit.getWindowToken(), 0);
            setResult(requestCode, intent);
            finish();
        }
    }

    @OnClick({R.id.vin_submit})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.vin_submit:
                input_submit();
                break;
            default:
                break;
        }
    }
    //验证小数点

    public void priceTextWatcher(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() == 0) {
                    return;
                }

                if (s.length() > 4 && !s.toString().contains(".")) {
                    editText.setText(s.toString().subSequence(0,
                            s.length() - 1));
                    editText.setSelection(s.length() - 1);
                    Toast.makeText(InPutCarNumberActivity.this, "请输入小数点", Toast.LENGTH_LONG).show();
                    return;
                } else if (s.toString().contains(".")
                        && s.toString()
                        .substring(s.toString().lastIndexOf("."))
                        .length() > 3) {
                    editText.setText(s.toString().substring(0,
                            s.toString().lastIndexOf("."))
                            + s.toString()
                            .substring(s.toString().lastIndexOf("."))
                            .subSequence(0, 3));
                    editText.setSelection(s.length() - 1);
                    Toast.makeText(InPutCarNumberActivity.this, "只能输入小数点后两位", Toast.LENGTH_LONG).show();
                    return;

                } else if ("0".equals(String.valueOf(s.charAt(0)))
                        && !s.toString().contains(".")) {
                    if (s.length() == 2
                            && "0".equals(String.valueOf(s.charAt(0)))) {
                        editText.setText("0");
                        editText.setSelection(1);
                        Toast.makeText(InPutCarNumberActivity.this, "请输入小数点", Toast.LENGTH_LONG).show();
                        return;
                    }

                } else if (".".equals(String.valueOf(s.charAt(0)))) {
                    editText.setText("");
                    Toast.makeText(InPutCarNumberActivity.this, "第一位不能为小数点", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }



   /* public boolean isEmail(String str) {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(str);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 判断车牌是否合法
     *
     * @param str
     * @return
     */
    public boolean isChePai(String str) {
        Pattern pattern = Pattern
                .compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断是否是手机号码
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        CharSequence inputStr = phoneNumber;
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[0,7])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputStr);
        if (m.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
