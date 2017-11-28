package com.jzg.erp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.widget.MyViewPager;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.relex.photodraweeview.PhotoDraweeView;


/**
 * 浏览图片(多张)
 * Created by pengkun on 2015/12/25.
 */
public class BrowsePicsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.pager)
    MyViewPager pager;
    private List<String> mDataList;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_browse_pics);
        ButterKnife.bind(this);
        pager.addOnPageChangeListener(this);
    }

    @Override
    protected void setData() {
        Intent intent = getIntent();
        mDataList = intent.getStringArrayListExtra("data") == null ? Collections.<String>emptyList() : intent.getStringArrayListExtra("data");
        int index = intent.getIntExtra("index", 0);
        pager.setAdapter(new ImagePagerAdapter(this));
        pager.setCurrentItem(index);
        setTitle((index + 1) + "/" + mDataList.size());
    }

    private class ImagePagerAdapter extends PagerAdapter {
        private LayoutInflater mInflater;

        public ImagePagerAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View itemView = mInflater.inflate(R.layout.item_browse_pic, view, false);
            PhotoDraweeView imageView = (PhotoDraweeView) itemView.findViewById(R.id.imageView);
            imageView.setImageURI(Uri.parse(mDataList.get(position)));
            view.addView(itemView, 0);
            return itemView;
        }


        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.e(TAG, "position:" + position);
        setTitle((position + 1) + "/" + mDataList.size());
        pager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
