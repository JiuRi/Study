package com.jiuri.stydyapplication.baidulocation;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

import org.greenrobot.eventbus.EventBus;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018\6\26 0026.
 */

public class MyLocationListener extends BDAbstractLocationListener {
    @Override
    public void onReceiveLocation(BDLocation location){
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        double latitude = location.getLatitude();    //获取纬度信息
        double longitude = location.getLongitude();    //获取经度信息
        float radius = location.getRadius();    //获取定位精度，默认值为0.0f

        String addr = location.getAddrStr();    //获取详细地址信息
        String country = location.getCountry();    //获取国家
        String province = location.getProvince();    //获取省份
        String city = location.getCity();    //获取城市
        String district = location.getDistrict();    //获取区县
        String street = location.getStreet();    //获取街道信息

        Log.e(TAG, "onReceiveLocation: _______________获取纬度信息   " +latitude);
        Log.e(TAG, "onReceiveLocation: _______________获取经度信息   " +longitude);
        Log.e(TAG, "onReceiveLocation: ______________获取定位精度   " +radius);

        Log.e(TAG, "onReceiveLocation: _______________获取详细地址信息   " +addr);
        Log.e(TAG, "onReceiveLocation: _______________获取国家   " +country);
        Log.e(TAG, "onReceiveLocation: ______________获取省份   " +province);
        Log.e(TAG, "onReceiveLocation: ______________获取城市  " +city);
        Log.e(TAG, "onReceiveLocation: ______________获取区县   " +district);
        Log.e(TAG, "onReceiveLocation: ______________获取街道信息   " +street);
        String coorType = location.getCoorType();
        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

        int errorCode = location.getLocType();
        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        EventBus.getDefault().post(location);
    }
}