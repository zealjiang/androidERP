package com.jzg.erp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.AccountInf;
import com.jzg.erp.presenter.AccountInfPresenter;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.view.IAccountInf;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.MActionSheet;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jzg.erp.R.id.err_layout;

public class AccountActivity extends BaseActivity implements IAccountInf {

    private String mPicPath;//拍照或从相册选择图片后图片的地址

    /**
     * 头像处理完成后所在的地址
     */
    private String mSavePicPath = Environment.getExternalStorageDirectory() + Constant.TEMP_PATH + "temp.jpg";

    private String mUserId;

    @Bind(R.id.iv_head)
    SimpleDraweeView mIvHead;

    @Bind(R.id.tv_name_value)
    TextView mTvName;


    @Bind(R.id.tv_phone_value)
    TextView mTvPhone;

    @Bind(R.id.tv_department_value)
    TextView mTvDepartment;

    @Bind(R.id.tv_job_value)
    TextView mTvJob;

    @Bind(R.id.tv_shop_value)
    TextView mTvShop;

    @Bind(err_layout)
    ErrorView errLayout;

    private String mStrTakePhoto;

    private AccountInfPresenter mAccountPresenter;

    private static final String HEAD_PHOTO[] = {"拍照", "从相册选择"};

    private static final int MY_PERMISSIONS_1 = 10;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        //获取userId
        mUserId = JzgApp.getUser().getUserID() + "";

        mStrTakePhoto = this.getResources().getString(R.string.take_photo);

        //获取用户信息
        mAccountPresenter = new AccountInfPresenter(this);
        mAccountPresenter.getInf(mUserId);
    }

    @Override
    protected void setData() {
        setTitle(R.string.account_info);

        //添加错误界面监听
        errLayout.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                //获取用户信息
                mAccountPresenter = new AccountInfPresenter(AccountActivity.this);
                mAccountPresenter.getInf(mUserId);
            }
        });

        //创建保存图片的文件夹 解决红米手机拍照时无法创建文件夹的问题
        String dir = new File(mSavePicPath).getParent();
        File fDir = new File(dir);
        if(fDir.exists()==false){
            fDir.mkdirs();
        }
    }

    @OnClick(R.id.rl_head)
    void showBottom(View view) {
        //弹出头像选择对话框
                MActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(HEAD_PHOTO)
                .setCancelableOnTouchOutside(true)
                .setListener(new MActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(MActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                        setOperation(HEAD_PHOTO[index]);
                    }
                }).show();
    }


    private void setOperation(String itemName) {

        if (mStrTakePhoto.equals(itemName)) {
            //照相获取图片
            selectPicFromCamera();
        } else {
            //从图库获取图片
            selectPicFromLocal();

        }
    }

    /**
     * 从图库获取图片
     */
    private void selectPicFromLocal() {

        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            Intent in = new Intent(Intent.ACTION_PICK, null);
            in.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            startActivityForResult(in, Constant.REQUEST_CODE_LOCAL);
        } else {
            Toast.makeText(this, "没有储存卡", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 照相获取图片
     */
    private void selectPicFromCamera() {

        MPermissions.requestPermissions(this, MY_PERMISSIONS_1,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    //动态申请权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //动态申请权限返回结果
    @PermissionGrant(MY_PERMISSIONS_1)
    public void requestSdcardSuccess() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mSavePicPath);
        if(file.exists()){
            file.delete();
        }
        Uri imageUri = Uri.fromFile(new File(mSavePicPath));
        // 指定照片保存路径（SD卡），temp.jpg为一个临时文件，每次拍照后这个图片都会被替换
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, Constant.REQUEST_CODE_CAMERA);
    }

    //动态申请权限返回结果
    @PermissionDenied(MY_PERMISSIONS_1)
    public void requestSdcardFailed() {
        Toast.makeText(this, "授权被拒!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_CAMERA) {
            Intent intent = new Intent(this, TouXiangActivity.class);
            intent.putExtra("path", mSavePicPath);
            startActivityForResult(intent, Constant.REQUEST_CODE_GETIMAGE_BYCROP);

        } else if (requestCode == Constant.REQUEST_CODE_LOCAL) { // 发送本地图片

            // 从相册传递
            if (data != null) {
                Uri uri = data.getData();
                Intent intent = new Intent(this,TouXiangActivity.class);
                //返回图片所在相册的地址
                mPicPath= uri2Path(uri);
                intent.putExtra("path",mPicPath);
                startActivityForResult(intent, Constant.REQUEST_CODE_GETIMAGE_BYCROP);
            }

        } else if (requestCode == Constant.REQUEST_CODE_GETIMAGE_BYCROP&&resultCode== Activity.RESULT_OK) {

            //上传头像
            if (mAccountPresenter == null) {
                mAccountPresenter = new AccountInfPresenter(this);
            }
            mAccountPresenter.uploadImage(mUserId, mSavePicPath);
        }

    }

    /**
     * 根据Uri获取图片绝对路径 uri2Path
     *
     * @param @param  uri
     * @param @return
     * @return String
     * @throws
     * @Title: uri2Path
     * @Description: TODO
     */
    public String uri2Path(Uri uri) {
        int actual_image_column_index;
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        actual_image_column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        img_path = cursor.getString(actual_image_column_index);
        return img_path;
    }

    @Override
    public void showError(String error) {
        MyToast.showLong(error);
        if(errLayout==null){
            return;
        }
        errLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorHead(String error) {
        MyToast.showLong(R.string.error_noConnect);
    }

    /**
     * 帐户信息反馈
     * @author zealjiang
     * @time 2016/7/4 10:29
     */
    @Override
    public void succeed(AccountInf.DataBean accountInf) {

        if(errLayout==null){
            return;
        }
        if (errLayout.getVisibility() == View.VISIBLE) {
            errLayout.setVisibility(View.GONE);
        }

        if (null != accountInf) {

            String headUrl = accountInf.getHeadImageUrl();
            if (null != headUrl && headUrl.length() > 0) {
                mIvHead.setImageURI(Uri.parse(headUrl));
            }

            String name = accountInf.getTrueName();
            if (null != name && name.length() > 0) {
                mTvName.setText(name);
            }

            String phone = accountInf.getTel();
            if (null != phone && phone.length() > 0) {
                mTvPhone.setText(phone);
            }
            String department = accountInf.getDicSubValue();
            if (null != department && department.length() > 0) {
                mTvDepartment.setText(department);
            }

            String job = accountInf.getDicSubValueZW();
            if (null != job && job.length() > 0) {
                mTvJob.setText(job);
            }

            String shop = accountInf.getStoreName();
            if (null != shop && shop.length() > 0) {
                mTvShop.setText(shop);
            }
        }

    }

    @Override
    public void succeedHead(AccountInf.DataBean accountInf) {
        mIvHead.setImageURI(Uri.parse(accountInf.getHeadImageUrl()));
        MyToast.showShort("头像上传成功");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
