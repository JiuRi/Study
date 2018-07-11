package com.jiuri.stydyapplication.kami;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018\7\5 0005.
 */

public class CamiPagerAdapter extends FragmentPagerAdapter {
    private int mPosition=0;

    public void setPosition(int position) {
        mPosition = position;
    }

    public CamiPagerAdapter(FragmentManager fm, int position) {
        super(fm);
        mPosition=position;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            FragmentCanUse fragmentCanUse = new FragmentCanUse();
            Bundle bundle=new Bundle();
            bundle.putInt("position",mPosition);
            fragmentCanUse.setArguments(bundle);
            return fragmentCanUse;
        }
        else {
            FragmentHasUse fragmentHasUse = new FragmentHasUse();
            Bundle bundle=new Bundle();
            bundle.putInt("position",mPosition);
            fragmentHasUse.setArguments(bundle);
            return fragmentHasUse;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
