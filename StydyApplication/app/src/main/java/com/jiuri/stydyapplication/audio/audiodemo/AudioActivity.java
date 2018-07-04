package com.jiuri.stydyapplication.audio.audiodemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiuri.stydyapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2018\6\12 0012.
 */

public class AudioActivity extends AppCompatActivity implements View.OnTouchListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {
    private Button mAudioSwichBtn;
    private RecyclerView mRecyclerView;
    private AudioUtil mAudioUtil;
    private ImageView mIvVolume;
    private LinearLayout mRecordingContent;
    private Handler mHandler;
    private AudioListAdapter mAudioListAdapter;
    private List<AudioBean> mCustomAudioBean=new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        initView();
        initData();
    }

    private void initData() {
        mHandler = new Handler();
        nitifalData();
    }


    private void initView() {
        mAudioSwichBtn = findViewById(R.id.audioSwich_btn);
        mRecyclerView = findViewById(R.id.recyclerView);
        mIvVolume = (ImageView) findViewById(R.id.iv_volume);
        mRecordingContent = (LinearLayout) findViewById(R.id.recording_Content);
        mAudioUtil = new AudioUtil();
        mAudioSwichBtn.setOnTouchListener(this);
        mAudioListAdapter = new AudioListAdapter(R.layout.item_audiobean, mCustomAudioBean);
        mAudioListAdapter.setOnItemClickListener(this);
        mAudioListAdapter.setOnItemLongClickListener(this);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);
        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(map));
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAudioListAdapter);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {

            //按下开始 开始录音
            case MotionEvent.ACTION_DOWN:
                mAudioSwichBtn.setText("松开结束");
                startAudio();
                //抬起手指  停止录音
                break;
            case MotionEvent.ACTION_UP:
                mAudioSwichBtn.setText("按住录音");
               stopAudio();
                break;
        }
        //需要事件
        return true;
    }

    private void startAudio() {
        mAudioUtil.startRecord();
        mRecordingContent.setVisibility(View.VISIBLE);
        new Thread(mRunnable).start();
    }

    private void stopAudio() {
        mAudioUtil.stopRecord();
        mRecordingContent.setVisibility(View.GONE);
        mHandler.removeCallbacksAndMessages(mRunnable);
        nitifalData();
    }
    private Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            int volume = mAudioUtil.getVolume();
            updateVolume(volume);
            mHandler.postDelayed(mRunnable,150);
        }
    };



    private void nitifalData() {
        List<AudioBean> customAudioBean = FileUtil.getCustomAudioBean(AudioUtil.AUDIO_DIR);
        mCustomAudioBean.clear();
        mCustomAudioBean.addAll(customAudioBean);
        mAudioListAdapter.notifyDataSetChanged();

    }

    /**
     * /更新音量图
     *
     * @param volume
     */
    private void updateVolume(int volume) {
        switch (volume) {
            case 1:
                mIvVolume.setImageResource(R.mipmap.p1);
                break;
            case 2:
                mIvVolume.setImageResource(R.mipmap.p2);
                break;
            case 3:
                mIvVolume.setImageResource(R.mipmap.p3);
                break;
            case 4:
                mIvVolume.setImageResource(R.mipmap.p4);
                break;
            case 5:
                mIvVolume.setImageResource(R.mipmap.p5);
                break;
            case 6:
                mIvVolume.setImageResource(R.mipmap.p6);
                break;
            case 7:
                mIvVolume.setImageResource(R.mipmap.p7);
                break;
            default:
                break;
        }
    }

    /**
     * 条目点击 播放录音
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (int i = 0; i < mCustomAudioBean.size(); i++) {
           if (i==position){
               mCustomAudioBean.get(position).setCheck(true);
           }
           else {
               mCustomAudioBean.get(i).setCheck(false);
           }
        }
        adapter.notifyDataSetChanged();
        if (mAudioUtil.isPlaying()) {
            mAudioUtil.stopPlay();

        }

        String audioPath = mCustomAudioBean.get(position).getAudioPath();
        mAudioUtil.startPlay(audioPath,false);



    }

    /**
     * 条目长按住弹出删除文件的对话框
     * @param adapter
     * @param view
     * @param position
     * @return
     */
    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
          builder.setMessage("是否删除选中的录音文件");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String audioPath = mCustomAudioBean.get(position).getAudioPath();
                File file=new File(audioPath);
                file.delete();
                nitifalData();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
        return true;
    }
}