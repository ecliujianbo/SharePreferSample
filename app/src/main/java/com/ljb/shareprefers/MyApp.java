package com.ljb.shareprefers;

import android.app.Application;

import com.jianbo.sharepreferlib.LSharePreferUtils;

/**
 * Created by jianbo on 2016/12/24.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LSharePreferUtils.init(this, "liu");
    }
}
