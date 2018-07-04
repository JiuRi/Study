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

public class KaimiAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public KaimiAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        String[] mbs = item.split("   ");
        helper.setText(R.id.zhanghao,mbs[0]);
        helper.setText(R.id.mima,mbs[1]);
        helper.setText(R.id.mibao,mbs[2]);
    }
}
