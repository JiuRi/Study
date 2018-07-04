package com.jiuri.stydyapplication.audio.audiodemo;

/**
 * audioName  录音文件存储时候的文件名字
 * audioPath  录音文件存储时候的文件路径
 */

public class AudioBean {
    private String audioName;
    private String audioPath;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
}
