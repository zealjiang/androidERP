package com.jzg.erp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.utils.AppUtils;
import com.jzg.erp.utils.DeviceTools;
import com.jzg.erp.widget.CropImageView;

import java.io.FileOutputStream;
import java.io.OutputStream;

import butterknife.ButterKnife;

/**
 * @author zhangchen
 * 
 *         裁剪图片
 */
public class ImageCropActivity extends BaseActivity {
	private static final int Crop_Width = 300;	//裁剪边框长度

	private CropImageView img;
	private String path;

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_crop_image);
		ButterKnife.bind(this);
		path = getIntent().getStringExtra("path");
	}

	@Override
	protected void setData() {
		setTitle("裁剪");
		setTextRight("确定");

		Log.e("ImageCropActivity path",path);
		Bitmap bitmap = DeviceTools.getCompressBitmapFromFile(path);
		if (bitmap == null) {
			Toast.makeText(this,"选择的图片不合适，请重新选择",Toast.LENGTH_SHORT).show();
			this.finish();
		}

		mTvRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 调用该方法得到剪裁好的图片
				Bitmap bmp = img.getCropImage();
				CompressFormat format = CompressFormat.JPEG;
				OutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(path);
					bmp.compress(format, 100, outputStream);
					DeviceTools.releaseBitmap(bmp);
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ImageCropActivity.this.setResult(RESULT_OK);
				ImageCropActivity.this.finish();
			}
		});

		// init img view
		img = (CropImageView) findViewById(R.id.imgCrop);
		Drawable drawable = AppUtils.bitmapToDrawble(bitmap, JzgApp.getAppContext());

		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		rlTitle.measure(w, h);
		int titleHeight = rlTitle.getMeasuredHeight();

		//有些手机(三星A7，魅族mx4 pro)，拍照后到裁剪页面，图片没有铺满，使用此方法放大drawable
		int image_drawable_width = getWindowManager().getDefaultDisplay().getWidth();
		int image_drawable_height = getWindowManager().getDefaultDisplay().getHeight() - titleHeight;
		drawable = zoomDrawable(drawable, image_drawable_width, image_drawable_height);

		img.setDrawable(drawable, Crop_Width, Crop_Width, titleHeight);
		img.setScaleType(ScaleType.FIT_XY);
		img.setAdjustViewBounds(true);
	}

	
	private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();  
        int height = drawable.getIntrinsicHeight();  
        Bitmap oldbmp = drawableToBitmap(drawable);  
        Matrix matrix = new Matrix();  
        float scaleWidth = ((float) w / width);  
        float scaleHeight = ((float) h / height);  
        matrix.postScale(scaleWidth, scaleHeight);  
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,matrix, true);  
        return new BitmapDrawable(null, newbmp);  
    } 
	
	private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();  
        int height = drawable.getIntrinsicHeight();  
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;  
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);  
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0, 0, width, height);  
        drawable.draw(canvas);  
        return bitmap;  
    }  
}
