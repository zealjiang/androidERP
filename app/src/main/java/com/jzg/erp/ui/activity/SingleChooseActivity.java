package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleChooseActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.lv)
    ListView lv;


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_single_choose);
        ButterKnife.bind(this);

    }

    @Override
    protected void setData() {

        String[] arr = getIntent().getStringArrayExtra(Constant.KEY_ARR);
        String title = getIntent().getStringExtra(Constant.KEY_TITLE);
        setTitle(title);
        setTextRight("完成");
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.item_simple_text, R.id.tv, Arrays.asList(arr)));
        lv.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String text = (String) lv.getAdapter().getItem(i);
        Intent intent = new Intent();
        intent.putExtra("result", text);
        setResult(RESULT_OK, intent);
        finish();
    }
}
