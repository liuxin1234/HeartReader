package com.liux.heartreader.application;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by
 * 项目名称：com.liux.heartreader.application
 * 项目日期：2017/9/30
 * 作者：liux
 * 功能：
 */

public class HeartReaderApplication extends Application {

    private static HeartReaderApplication heardReaderApplication;

    public static HeartReaderApplication getInstance(){
        return heardReaderApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        heardReaderApplication = this;

        initTextSize();
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}
