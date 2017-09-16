package com.payconiq.zekigu.githubrepos.core.app;

import android.app.Application;
import android.content.Context;

import com.payconiq.zekigu.githubrepos.core.imageloader.ImageLoaderImpl;

/**
 * Created by zekigu on 15.09.2017
 */
public class BaseApplication extends Application {

    private Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        ApplicationManager.getInstance().initApp(mAppContext);
        ApplicationManager.getInstance().addImageLoader(new ImageLoaderImpl());
    }
}
