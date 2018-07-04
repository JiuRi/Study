package com.jiuri.stydyapplication.audio.audiodemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *  读取问价夹下面的音频文件 获取音频文件的名字
 *  和获取音频文件的路径 用于播放音频文件！
 *
 * Created by Administrator on 2018\6\12 0012.
 */

public class FileUtil {
    public  static List<AudioBean> getCustomAudioBean(String path){
        List<AudioBean> audioBeenList = new ArrayList<>();
        File file=new File(path);
        if (file.exists()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                AudioBean audioBean = new AudioBean();
                audioBean.setAudioName(files[i].getName());
                audioBean.setAudioPath(files[i].getAbsolutePath());
                audioBeenList.add(audioBean);
            }
        }
        return audioBeenList;
    }
}
