package com.jiuri.stydyapplication.audio.audiodemo;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiuri.stydyapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2018\6\12 0012.
 */

public class AudioListAdapter extends BaseQuickAdapter<AudioBean,BaseViewHolder> {
        private  int position;
    public AudioListAdapter(@LayoutRes int layoutResId, @Nullable List<AudioBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AudioBean item) {
        helper.setText(R.id.audio_name,item.getAudioName());
        helper.setChecked(R.id.cb_bell,item.isCheck());
        if (item.isCheck()) {
            helper.getView(R.id.cb_bell).setVisibility(View.VISIBLE);

        }
        else {
            helper.getView(R.id.cb_bell).setVisibility(View.GONE);
        }

    }


}
