package com.jiuri.stydyapplication.audio.a音频采集的api;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Android SDK 提供了两套音频采集的API，分别是：MediaRecorder 和 AudioRecord；
 *
 * MediaRecorder：上层一点的API，它可以直接把手机麦克风录入的音频数据进行编码压缩（如AMR、MP3等）并存成文件；
 *   如果想简单地做一个录音机，录制成音频文件，则推荐使用 MediaRecorder。
 *
 * AudioRecord：能够更加自由灵活地控制，可以得到原始的一帧帧PCM音频数据；
 * 如果需要对音频做进一步的算法处理、或者采用第三方的编码库进行压缩、以及网络传输等应用，
 * 则建议使用 AudioRecord，其实 MediaRecorder 底层也是调用了 AudioRecord 与 Android Framework 层的 AudioFlinger 进行交互的。
 */


public class Api介绍 {

    private MediaRecorder recorder;

    public Api介绍() {
        File file = new File("/sdcard/mediarecorder.amr");
        //创建音频对象
        recorder = new MediaRecorder();
        // 设置音频录入源
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置录制音频的输出格式
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // 设置音频的编码格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // 设置录制音频文件输出文件路径
        recorder.setOutputFile(file.getAbsolutePath());
        //设置最大录入时间
        recorder.setMaxDuration(5000);
        //设置入炉错误监听
        recorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                // 发生错误，停止录制
                recorder.stop();
                recorder.release();
                recorder = null;
               // Toast.makeText(RecordActivity.this, "录音发生错误", Toast.LENGTH_SHORT).show();
            }
        });
        //用于监听入录入状态 当我们设置了最大录入时间的时候
        recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mediaRecorder, int what, int i1) {
                if(what==MediaRecorder.MEDIA_RECORDER_INFO_NEXT_OUTPUT_FILE_STARTED){
                    Log.v("VIDEOCAPTURE", "Maximum Filesize Reached");
                    mediaRecorder.stop();
                }
            }
        });



        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //一切准备就绪 开始录入
        recorder.start();

        //下面都是结束音频录入的
        recorder.stop();

        recorder.reset();

        recorder.release();


    //最后在activity 或者fragmenth里面销毁之前都要先判断一下 是否真在录入

        /*@Override
        protected void onDestroy() {
            if (isRecording) {
                // 如果正在录音，停止并释放资源
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            }
            super.onDestroy();
        }*/


    }



}
