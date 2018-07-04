package com.jiuri.stydyapplication.audio.audiodemo;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018\6\12 0012.
 *  音频工具类
 */

public class AudioUtil {
    public static final String AUDIO_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/JiuRi/audio";
    private String recordingName;
    private String recordingPath;
    private AudioUtil mAudioUtil;
    private boolean recording=false;  //是否真在录音
    private boolean playing=false;  //是否真在播放
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mPlay;


    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }


    public boolean isRecording() {
        return recording;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    /**
     * 开始录音
     */
    public void startRecord(){
        //创建音频对象
        mMediaRecorder = new MediaRecorder();
        // 设置音频录入源
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置录制音频的输出格式
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // 设置音频的编码格式
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //设置最长录音时间
       //mMediaRecorder.setMaxDuration(50000);
        File file=new File(AUDIO_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        recordingName=DataUtil.getData();
        recordingPath=AUDIO_DIR+"/"+recordingName+".amr";
        mMediaRecorder.setOutputFile(recordingPath);
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            recording=true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 停止录音
     */
    public  void stopRecord(){
        if (mMediaRecorder != null && recording) {
            //设置异常时不崩溃
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            recording = false;
        }
    }


    /**
     *  获取音量值，只是针对录音音量
     * @return
     */
    public int getVolume() {
        int volume = 0;
        // 录音
        if (mMediaRecorder != null && recording) {
            volume = mMediaRecorder.getMaxAmplitude() / 650;
            Log.d("sdfgasd", volume + "");
            if (volume != 0)
                // volume = (int) (10 * Math.log(volume) / Math.log(10)) / 7;
                volume = (int) (10 * Math.log10(volume)) / 3;
            Log.d("db", volume + "");
        }
        return volume;
    }

    // �?��播放
    public void startPlay(String path, boolean isLooping) {
        Log.e(TAG, "startPlay: _______________________"+path );
        if (!path.equals("")) {
            mPlay = new MediaPlayer();
            try {
                mPlay.setDataSource(path);
                mPlay.prepare();
                mPlay.start();
                mPlay.setLooping(isLooping);
                playing = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // 停止播放
    public void stopPlay() {
        if (mPlay != null && mPlay.isPlaying()) {
            mPlay.stop();
            mPlay.release();
            mPlay = null;
            playing = false;
        }
    }
}
