package com.jiuri.stydyapplication.kami;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiuri.stydyapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
 * Created by Administrator on 2018\7\4 0004.
 */

public class KaMiDetailActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    private ArrayList<String> mArrayList=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private KaimiAdapter mKaimiAdapter;
    private RecyclerViewSpacesItemDecoration mRecyclerViewSpacesItemDecoration;
    private int [] arr={zg, mg, xg, tw, rb, hg, yg
            , adly, xjp, jnd, flb, xxl};
    private String [] strings={"zg","mg","xg","tw","rb","hg","yg"
            ,"adly","xjp","jnd","flb","xxl"};
    private int mPosition=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainkami);
        initView();
        initData();
    }

    private void initData() {
        mPosition = getIntent().getIntExtra("position",0);
        readFromSdCard(strings[mPosition]);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mKaimiAdapter = new KaimiAdapter(R.layout.item_kami, mArrayList);
        mRecyclerView.setAdapter(mKaimiAdapter);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,30);
        mRecyclerViewSpacesItemDecoration = new RecyclerViewSpacesItemDecoration(map);
        mRecyclerView.addItemDecoration(mRecyclerViewSpacesItemDecoration);
        mKaimiAdapter.setOnItemClickListener(this);
    }
    public void readFromSdCard(String name){
        String path0 = Environment.getExternalStorageDirectory().getPath()+"/kami/canuse/";
        File file = new File(path0+name+".txt");
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line = "";

                    mArrayList.clear();
                    while ((line = bufferedReader.readLine()) !=null){
                        mArrayList.add(line);
                    }
                fileInputStream.close();
                bufferedReader.close();
                mKaimiAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        showDialogMe(position);
    }

    public void showDialogMe(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(arr[mPosition])//设置标题的图片
                .setTitle("确定使用此Apple ID?")//设置对话框的标题
                .setMessage(mArrayList.get(position))//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                          cm.setText(mArrayList.get(position));
                        // 将ClipData内容放到系统剪贴板里。
                        dialog.dismiss();
                        mArrayList.remove(position);
                        mKaimiAdapter.notifyDataSetChanged();
                        writeCanUseAgain();
                        writeCanNotUseAgain();
                    }
                }).create();
        dialog.show();
    }
private void writeCanNotUseAgain() {

    String path0 = Environment.getExternalStorageDirectory().getPath()+"/kami/cannotuse/";
    File file = new File(path0+strings[mPosition]+".txt");
    if (!file.exists()){
        file.mkdirs();
    }
    try {
        FileOutputStream outputStream = new FileOutputStream(file,true);
        BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outputStream));
        for (int i = 0; i < mArrayList.size(); i++) {
            String line = null;
            bufw.write(line);
            bufw.newLine();
            bufw.flush();

        }


    } catch (Exception e) {
        e.printStackTrace();
    }

    }


    public void writeCanUseAgain(){
        String path0 = Environment.getExternalStorageDirectory().getPath()+"/kami/canuse/";
        File file = new File(path0+strings[mPosition]+".txt");
        try {
            FileWriter outputStream = new FileWriter(file);
            BufferedWriter bufw = new BufferedWriter(outputStream);
            for (int i = 0; i < mArrayList.size(); i++) {
                String line = null;

                    bufw.write(line);
                    bufw.newLine();
                    bufw.flush();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
