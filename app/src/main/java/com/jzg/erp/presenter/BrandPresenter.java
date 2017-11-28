package com.jzg.erp.presenter;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.Make;
import com.jzg.erp.model.MakeList;
import com.jzg.erp.model.Model;
import com.jzg.erp.model.ModelCategory;
import com.jzg.erp.model.ModelList;
import com.jzg.erp.utils.ChineseUtil;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IBrandInterface;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyd on 15/12/23.
 * 品牌列表逻辑
 */
public class BrandPresenter extends BasePresenter<IBrandInterface> {


    public BrandPresenter(IBrandInterface iBrandInterface) {
        super(iBrandInterface);
    }

    /**
     * 获取汽车品牌数据
     * @author zealjiang
     * @time 2016/7/8 16:15
     */
    public void getBrand(Map<String, String> params) {

        JzgApp.getCommonServer().getBrandFromService(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<MakeList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                            baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                        }
                    }

                    @Override
                    public void onNext(MakeList makeList) {
                        baseView.dismissDialog();
                        if (makeList.getStatus() == 100) {
                            ArrayList<Make> makes = makeList.getMakeList();
                            if (makes != null) {
                                //设置品牌列表数据用于ItemClick
                                if (!baseView.readFromCache(makeList)) {  //如果缓存与网络不一致，更新列表
                                    showMake(makes);
                                }

                            }

                        } else {
                            baseView.showError(makeList.getMsg());
                        }
                    }
                });
    }


    public void showMake(ArrayList<Make> makes) {
        baseView.setBrands(makes);
        ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        Map<String, Integer> indexData = new HashMap<String, Integer>();
        //显示分组数据用于分组adapter
        getItems(makes, items);
        baseView.showBrand(items);

        //设置index索引数据用于index索引监听
        getIndexData(items, indexData);
        baseView.setIndexData(indexData);
    }

    private void getIndexData(ArrayList<Map<String, Object>> items, Map<String, Integer> indexData) {
        for (int i = 0; i < items.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = items.get(i).get("Sort").toString();
            // 上一个汉语拼音首字母，如果不存在为“ ”
            String previewStr = (i - 1) >= 0 ? items.get(i - 1).get("Sort")
                    .toString() : " ";
            if (!previewStr.equals(currentStr)) {
                String name = items.get(i).get("Sort").toString();
                indexData.put(name, i);
            }
        }
    }

    private void getItems(ArrayList<Make> makes, ArrayList<Map<String, Object>> items) {
        if(makes==null || makes.size()==0)
            return;
        Map<String, Object> map = null;
        for (int i = 0; i < makes.size(); i++) {
            map = new HashMap<String, Object>();
            map.put("name", makes.get(i).getMakeName());
            map.put("fontColor", baseView.getDefaultFontColor());
            map.put("logo", makes.get(i).getMakeLogo());
            String contactSort = ChineseUtil
                    .getFullSpell(map.get("name").toString())
                    .toUpperCase().substring(0, 1);
            map.put("Sort", contactSort);
            items.add(map);
        }
        Comparator comp = new Mycomparator();
        Collections.sort(items, comp);
    }

    /**
     * 从精真估加载指定汽车品牌下的所有车系
     * @author zealjiang
     * @time 2016/7/8 16:51
     */
    public void getModel(Map<String, String> params) {
        JzgApp.getCommonServer().getModelFromService(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<ModelList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.dismissDialog();
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                            baseView.showError(e.toString());
                        }


                    }

                    @Override
                    public void onNext(ModelList modelList) {
                        baseView.dismissDialog();
                        if (modelList.getStatus() == 100) {
                            ArrayList<ModelCategory> modelCategorys = modelList.getManufacturerList();
                            ArrayList<Model> mModels = new ArrayList<>();
                            ArrayList<String> mModelsGroupNames = new ArrayList<>();
                            for (ModelCategory category : modelCategorys) {
                                Model group = new Model();
                                String groupName = category.getManufacturerName();
                                mModelsGroupNames.add(groupName);
                                group.setName(groupName);
                                group.setManufacturerName(Constant.IS_TITLE);
                                group.setFontColor(R.color.grey3);
                                //增加厂商
                                mModels.add(group);
                                //增加厂商下关联的车系
                                for (Model item : category.getModelList()) {
                                    item.setFontColor(R.color.grey3);
                                    mModels.add(item);
                                }
                            }

                            baseView.setModels(mModels);
                            baseView.setModelsGroupNames(mModelsGroupNames);
                            baseView.showModel();
                        } else {
                            baseView.showError(modelList.getMsg());
                        }
                    }
                });
    }

    // 按中文拼音排序
    public class Mycomparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Map<String, Object> c1 = (Map<String, Object>) o1;
            Map<String, Object> c2 = (Map<String, Object>) o2;
            Comparator cmp = Collator.getInstance(java.util.Locale.ENGLISH);
            return cmp.compare(c1.get("Sort"), c2.get("Sort"));
        }
    }

    /**
     * 加载车源数据
     * @param params
     */
    public void loadCarSourceData(Map<String, String> params) {
        System.out.println("loadCarSourceData:  "+ UIUtils.getUrl(params));
        JzgApp.getApiServer().loadCarSource(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.dismissDialog();
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                            baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                        }
                    }

                    @Override
                    public void onNext(CarSourceData carSourceData) {
                        baseView.dismissDialog();
                        baseView.ShowCarSourceData(carSourceData);
                    }
                });
    }
    public void loadMoreCarSourceData(Map<String, String> params) {
        JzgApp.getApiServer().loadCarSource(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.dismissDialog();
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                            baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                        }
                    }

                    @Override
                    public void onNext(CarSourceData carSourceData) {
                        baseView.dismissDialog();
                        baseView.ShowMoreCarSourceData(carSourceData);
                    }
                });
    }
}

