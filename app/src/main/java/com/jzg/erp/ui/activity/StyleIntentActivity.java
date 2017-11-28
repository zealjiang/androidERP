package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.model.Make;
import com.jzg.erp.model.Model;
import com.jzg.erp.model.Tag;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.widget.TagCloudLinkView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改意向车型界面
 * StyleIntentActivity
 *
 * @author sunbl
 *         created at 2016/6/24 18:39
 */
public class StyleIntentActivity extends BaseActivity {
    @Bind(R.id.tag_text)
    TagCloudLinkView tagText;

    private Make make;
    private Model model;
    private List<String> idList;
    private List<String> nameList;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_style_intent);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        setTitle("修改意向车型");
        setTextRight("完成");
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        if (TextUtils.isEmpty(name)) {
            nameList = new ArrayList<>(5);
            idList = new ArrayList<>(5);
        } else {
            nameList = Arrays.asList(name.split(","));
            if (nameList == null) {
                nameList = new ArrayList<>(5);
            } else {
                nameList = new ArrayList<>(nameList);
            }
            idList = Arrays.asList(id.split(","));
            if (idList == null) {
                idList = new ArrayList<>(5);
            } else {
                idList = new ArrayList<>(idList);
            }
        }
        for (int i = 0; i < nameList.size(); i++) {
            tagText.add(new Tag(i, nameList.get(i)));
        }

        tagText.drawTags();
        tagText.setOnTagDeleteListener(new TagCloudLinkView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {

                idList.remove(i);
                nameList.remove(i);

            }
        });

    }

    @OnClick({R.id.btn_intent, R.id.tvRight})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_intent:
                if (nameList.size() > 4) {
                    MyToast.showShort("最多选择5个意向车型");
                    return;
                }
                Intent intent = new Intent(this, BrandActivity.class);
                startActivityForResult(intent, IntentKey.FOR_BRAND);

                break;
            case R.id.tvRight:
                if (nameList.size() <= 0) {
                    MyToast.showShort("至少选择1个意向车型");
                    return;
                }
                StringBuffer sbId = new StringBuffer();
                for(int i=0;i<idList.size();i++){
                    String id = idList.get(i);
                    if(i==0){
                        sbId.append(id);
                    }else{
                        sbId.append(",").append(id);
                    }
                }
                StringBuffer sbName = new StringBuffer();
                for(int i=0;i<nameList.size();i++){
                    String id = nameList.get(i);
                    if(i==0){
                        sbName.append(id);
                    }else{
                        sbName.append(",").append(id);
                    }
                }

                String ids = sbId.toString();
                String names = sbName.toString();
                Intent intent1 = new Intent();
                intent1.putExtra("id", ids);
                intent1.putExtra("name", names);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentKey.FOR_BRAND:
                if (data != null) {
                    make = data.getParcelableExtra("make");
                    model = data.getParcelableExtra("model");
                    String name = make.getMakeName() + " " + model.getName();
                    String id = make.getMakeId() + "_" + model.getId();
                    if (idList.contains(id)) {
                        MyToast.showShort("该车系已存在");
                    } else {
                        nameList.add(name);
                        idList.add(id);
                        tagText.add(new Tag(nameList.size(), nameList.get(nameList.size() - 1)));

                        tagText.drawTags();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * set tag delete icon is visible by tag list size
     * if tag list size == 1 delete icon gone
     * else visible
     *
     * 有三个地方要调用这个方法：1、第一次展示tag之前 2、删除一个tag之后 3、新增一个tag之后
     * by zealjiang
     */
    private void setTagDeleteVisible(){
        if(nameList.size()==1){
            tagText.setIsDeletable(false);
        }else{
            tagText.setIsDeletable(true);
        }
    }
}
