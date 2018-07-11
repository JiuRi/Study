package com.jiuri.stydyapplication.kami;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jiuri.stydyapplication.R;

/**
 * Created by Administrator on 2018\7\4 0004.
 */

public class KaMiDetailActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private int mPosition = 0;
    private ViewPager mViewPager;

    /**
     * 可用的ID
     */
    private TextView mCanuse;
    /**
     * 已用的ID
     */
    private TextView mHasuse;
    private CamiPagerAdapter mCamiPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainkami);
        initView();
        initData();
    }

    private void initData() {
        mPosition = getIntent().getIntExtra("position", 0);
        mCamiPagerAdapter = new CamiPagerAdapter(getSupportFragmentManager(), mPosition);
        mViewPager.setAdapter(mCamiPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_Pager);
        mCanuse = (TextView) findViewById(R.id.canuse);
        mCanuse.setOnClickListener(this);
        mHasuse = (TextView) findViewById(R.id.hasuse);
        mHasuse.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.canuse:
                mViewPager.setCurrentItem(0);
                chooseCanuse();
                break;
            case R.id.hasuse:
                mViewPager.setCurrentItem(1);
                chooseHasuse();
                break;
        }
    }

    private void chooseHasuse() {
        mHasuse.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mHasuse.setTextColor(Color.WHITE);
        mCanuse.setBackgroundColor(Color.WHITE);
        mCanuse.setTextColor(Color.BLACK);
    }

    private void chooseCanuse() {
        mCanuse.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mCanuse.setTextColor(Color.WHITE);
        mHasuse.setBackgroundColor(Color.WHITE);
        mHasuse.setTextColor(Color.BLACK);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position==0){
            chooseCanuse();
        }
        else {
            chooseHasuse();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
