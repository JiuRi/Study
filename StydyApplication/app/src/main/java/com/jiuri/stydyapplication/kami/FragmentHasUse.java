package com.jiuri.stydyapplication.kami;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuri.stydyapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jiuri.stydyapplication.R.mipmap.adly;
import static com.jiuri.stydyapplication.R.mipmap.flb;
import static com.jiuri.stydyapplication.R.mipmap.hg;
import static com.jiuri.stydyapplication.R.mipmap.jnd;
import static com.jiuri.stydyapplication.R.mipmap.mg;
import static com.jiuri.stydyapplication.R.mipmap.rb;
import static com.jiuri.stydyapplication.R.mipmap.tw;
import static com.jiuri.stydyapplication.R.mipmap.xg;
import static com.jiuri.stydyapplication.R.mipmap.xjp;
import static com.jiuri.stydyapplication.R.mipmap.xxl;
import static com.jiuri.stydyapplication.R.mipmap.yg;
import static com.jiuri.stydyapplication.R.mipmap.zg;

/**
 * Created by Administrator on 2018\7\5 0005.
 */

public class FragmentHasUse extends Fragment {
    private ArrayList<String> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private KaimiAdapter mKaimiAdapter;
    private RecyclerViewSpacesItemDecoration mRecyclerViewSpacesItemDecoration;
    private int[] arr = {zg, mg, xg, tw, rb, hg, yg
            , adly, xjp, jnd, flb, xxl};
    private String[] strings = {"zg", "mg", "xg", "tw", "rb", "hg", "yg"
            , "adly", "xjp", "jnd", "flb", "xxl"};
    private int mPosition = 0;
    private View mInflate;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflate = inflater.inflate(R.layout.fragment_hasuse, container, false);
        return mInflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }


    private void initData() {
        mPosition = getArguments().getInt("position", 0);
        readFromSdCard(strings[mPosition]);
        mTextView.setBackgroundDrawable(getResources().getDrawable(arr[mPosition]));
    }

    public void readFromSdCard(String name) {
        String path0 = Environment.getExternalStorageDirectory().getPath() + "/kami/haveuse/";
        File file = new File(path0 + name + ".txt");
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream,"utf-8"));
                String line = "";

                mArrayList.clear();
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.length()>10) {
                        mArrayList.add(line);
                    }
                }
                fileInputStream.close();
                bufferedReader.close();
                mKaimiAdapter.notifyDataSetChanged();
                mTextView.setText(mArrayList.size() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void initView() {
        EventBus.getDefault().register(this);
        mRecyclerView = (RecyclerView) mInflate.findViewById(R.id.recyclerView);
        mTextView = (TextView) mInflate.findViewById(R.id.number);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mKaimiAdapter = new KaimiAdapter(R.layout.item_kami, mArrayList);
        mRecyclerView.setAdapter(mKaimiAdapter);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, 30);
        mRecyclerViewSpacesItemDecoration = new RecyclerViewSpacesItemDecoration(map);
        mRecyclerView.addItemDecoration(mRecyclerViewSpacesItemDecoration);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Bean messageEvent) {
        if (messageEvent.isBoolean()) {
            readFromSdCard(strings[mPosition]);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
