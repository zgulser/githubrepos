package com.payconiq.zekigu.githubrepos.core.app;

import android.app.Application;
import android.content.Context;

import com.orm.SugarApp;
import com.orm.SugarDb;
import com.payconiq.zekigu.githubrepos.core.imageloader.ImageLoaderImpl;

/**
 * Created by zekigu on 15.09.2017
 */
public class GithubRepoApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationManager.getInstance().initApp(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarApp.getSugarContext().onTerminate();
    }
}
