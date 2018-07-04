package com.jiuri.stydyapplication.audio.audiodemo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018\6\12 0012.
 */

public class DataUtil {
    public static String getData(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hhmmss");
        String dateNowStr = sdf.format(d);
        return  dateNowStr;
    }
}
