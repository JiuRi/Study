package com.jiuri.stydyapplication.kami;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiuri.stydyapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2018\7\4 0004.
 */

public class MainKamiActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList <String> mArrayList=new ArrayList<>();
    private ContoryAdapter mContoryAdapter;
    private RecyclerViewSpacesItemDecoration mItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
/*        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainkamicontry);
        initView();
        initData();














    }

    private void initData() {
        String[] contorys = getResources().getStringArray(R.array.contrys);
        List<String> strings = Arrays.asList(contorys);
        mArrayList.clear();
        mArrayList.addAll(strings);
        mContoryAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mContoryAdapter = new ContoryAdapter(R.layout.item_contry, mArrayList);
        mRecyclerView.setAdapter(mContoryAdapter);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,20);
        map.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,20);
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,55);
        mItemDecoration = new RecyclerViewSpacesItemDecoration(map);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mContoryAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(MainKamiActivity.this, KaMiDetailActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
