package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.utils.BitmapUtils;
import com.jzg.erp.widget.ClipImageLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TouXiangActivity extends BaseActivity {

    TouXiangActivity mActivity;

    /**
     * 头像处理完成后所在的地址
     */
    private String mSavePicPath = Environment.getExternalStorageDirectory() + Constant.TEMP_PATH + "temp.jpg";

    private String mTemp1PicPath = Environment.getExternalStorageDirectory() + Constant.TEMP_PATH+"temp1.jpg";

    @Bind(R.id.clipImageLayout)
    ClipImageLayout clipImageLayout;

    private String path;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tou_xiang);
        mActivity = this;
        ButterKnife.bind(this);
        //可能是从相册中选取的某个图片的地址也可能是手机拍照的后保存的地址
        path = getIntent().getStringExtra("path");
        if(null==path){
            Toast.makeText(this,"请重新拍照",Toast.LENGTH_SHORT).show();
            finish();
        }
        //Log.e("TouXiangActivity",path+"");
    }

    @Override
    protected void setData() {
        setTitle("裁剪");
        setTextRight("确定");

        // 有的系统返回的图片是旋转了，有的没有旋转，所以处理
        BitmapUtils utils = new BitmapUtils();
        Bitmap bitmap = null;
        //int degree = utils.readBitmapDegree(path);
        if (utils.saveImage(path,mTemp1PicPath)) {
            //得到是的一张旋转后的图片
            bitmap = createBitmap(mTemp1PicPath);
        }
        if (bitmap != null) {
            clipImageLayout.setImageBitmap(bitmap);

            //path 可能是相册某张图片的地址
            if(path.equals(mSavePicPath)){
                File f = new File(path);
                if (f.exists()) {
                    f.delete();
                }
            }

        } else {
            finish();
        }

        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = clipImageLayout.clip();
                saveBitmap(bitmap, mSavePicPath);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    private void saveBitmap(Bitmap bitmap, String path) {
        String tempFile = Environment.getExternalStorageDirectory() + Constant.TEMP_PATH+"temp2.jpg";
        File f = new File(tempFile);
        if (f.exists()) {
            f.delete();
        }

        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, fOut);
            fOut.flush();


            BitmapUtils utils = new BitmapUtils();
            boolean boo = utils.saveImage(tempFile, path);
            if(boo){
                f.delete();

                File f1 = new File(mTemp1PicPath);
                if (f1.exists()) {
                    f1.delete();
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (fOut != null)
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 创建图片
     *
     * @param path
     * @return
     */
    private Bitmap createBitmap(String path) {
        if (path == null) {
            return null;
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        FileInputStream is = null;
        Bitmap bitmap = null;
        try {
            is = new FileInputStream(path);
            bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }


}
