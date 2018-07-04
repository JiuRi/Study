package com.jiuri.stydyapplication.App;

import android.app.Application;

/**
 * Created by Administrator on 2018\6\29 0029.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Density.setDensity(this, 375f);
    }
}
