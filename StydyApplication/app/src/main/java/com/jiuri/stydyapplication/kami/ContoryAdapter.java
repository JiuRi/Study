package com.jiuri.stydyapplication.kami;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiuri.stydyapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2018\7\4 0004.
 */

public class ContoryAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private int [] arr={R.mipmap.zg,R.mipmap.mg,R.mipmap.xg,R.mipmap.tw,R.mipmap.rb,R.mipmap.hg,R.mipmap.yg
    ,R.mipmap.adly,R.mipmap.xjp,R.mipmap.jnd,R.mipmap.flb,R.mipmap.xxl};

    public ContoryAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.name,item);
        helper.setImageResource(R.id.pic,arr[helper.getPosition()]);
    }
}
