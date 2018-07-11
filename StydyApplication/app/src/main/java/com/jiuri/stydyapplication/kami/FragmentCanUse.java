package com.jiuri.stydyapplication.kami;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiuri.stydyapplication.R;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

public class FragmentCanUse extends Fragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    private ArrayList<String> mArrayList = new ArrayList<>();
    private ArrayList<String> mAddList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private KaimiAdapter mKaimiAdapter;
    private KaimiAdapter mKaimiAdapterAdd;
    private RecyclerViewSpacesItemDecoration mRecyclerViewSpacesItemDecoration;
    private int[] arr = {zg, mg, xg, tw, rb, hg, yg
            , adly, xjp, jnd, flb, xxl};
    private String[] strings = {"zg", "mg", "xg", "tw", "rb", "hg", "yg"
            , "adly", "xjp", "jnd", "flb", "xxl"};
    private int mPosition = 0;
    private View mInflate;
    private TextView mAddKami;
    private View view;
    private EditText mEditText;
    private BottomSheetBehavior behavior;
    /**
     * 取消
     */
    private Button mQuxiao;
    /**
     * 添加
     */
    private Button mAdd;
    private ButtomDialogView mButtomDialogView;
    private RecyclerView mRecyclerViewAdd;
    private View mTe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflate = inflater.inflate(R.layout.fragment_canuse, container, false);
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
    }

    public void readFromSdCard(String name) {
        String path0 = Environment.getExternalStorageDirectory().getPath() + "/kami/canuse/";
        File file = new File(path0 + name + ".txt");
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line = "";

                mArrayList.clear();
                while ((line = bufferedReader.readLine()) != null) {
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

    private void initView() {
        mRecyclerView = (RecyclerView) mInflate.findViewById(R.id.recyclerView);
        mAddKami = (TextView) mInflate.findViewById(R.id.add);
        View bottomSheet =mInflate.findViewById(R.id.bottom_sheet);

        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, 30);
        mRecyclerViewSpacesItemDecoration = new RecyclerViewSpacesItemDecoration(map);

        mRecyclerViewAdd = bottomSheet.findViewById(R.id.recyclerView_Add);
        mTe = bottomSheet.findViewById(R.id.tv);
        mTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                mArrayList.addAll(mAddList);
                writeCanUseAgain();
                mAddList.clear();
                readFromSdCard(strings[mPosition]);
            }
        });
        mRecyclerViewAdd.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewAdd.setLayoutManager(linearLayoutManager);
        mKaimiAdapterAdd = new KaimiAdapter(R.layout.item_kami, mAddList);
        mRecyclerViewAdd.addItemDecoration(mRecyclerViewSpacesItemDecoration);
        mRecyclerViewAdd.setAdapter(mKaimiAdapterAdd);


        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager1);
        mKaimiAdapter = new KaimiAdapter(R.layout.item_kami, mArrayList);
        mRecyclerView.setAdapter(mKaimiAdapter);
        mRecyclerView.addItemDecoration(mRecyclerViewSpacesItemDecoration);

        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState)
            {
//这里是bottomSheet状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset)
            {
//这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });


        mKaimiAdapter.setOnItemClickListener(this);
        mAddKami.setOnClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        showDialogMe(position);
    }


    public void showDialogMe(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
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
                        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        cm.setText(mArrayList.get(position));
                        // 将ClipData内容放到系统剪贴板里。
                        dialog.dismiss();
                        writeCanNotUseAgain(position);
                        mArrayList.remove(position);
                        mKaimiAdapter.notifyDataSetChanged();
                        writeCanUseAgain();
                        EventBus.getDefault().post(new Bean(true));

                    }
                }).create();
        dialog.show();
    }

    private void writeCanNotUseAgain(int position) {

        String path0 = Environment.getExternalStorageDirectory().getPath() + "/kami/haveuse/"+strings[mPosition] + ".txt";
        File file = new File(path0);
        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
            bufw.write(mArrayList.get(position));
            bufw.newLine();
            bufw.flush();
            outputStream.close();
            bufw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeCanUseAgain() {
        String path0 = Environment.getExternalStorageDirectory().getPath() + "/kami/canuse/";
        File file = new File(path0 + strings[mPosition] + ".txt");
        try {
            FileWriter outputStream = new FileWriter(file);
            BufferedWriter bufw = new BufferedWriter(outputStream);
            for (int i = 0; i < mArrayList.size(); i++) {
                bufw.write(mArrayList.get(i));
                bufw.newLine();
                bufw.flush();
            }
        } catch (Exception e) {
            Log.e("mmmmm", "writeCanUseAgain: ______________________________" + e.getMessage().toString());
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        View inflate = View.inflate(getContext(), R.layout.pop_add, null);
        mEditText = (EditText) inflate.findViewById(R.id.edit_text);
        mQuxiao = (Button) inflate.findViewById(R.id.quxiao);
        mAdd = (Button) inflate.findViewById(R.id.add);
        mButtomDialogView = new ButtomDialogView(getContext(), inflate, false, true);
        mButtomDialogView.show();
        mQuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                mButtomDialogView.dismiss();
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = mEditText.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    return;
                }
                String[] split = trim.split("\\\n");
                List<String> strings = Arrays.asList(split);
                mAddList.clear();
                mAddList.addAll(strings);
                mKaimiAdapterAdd.notifyDataSetChanged();
                mButtomDialogView.dismiss();
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

    }
}
