package com.payconiq.zekigu.githubrepos.ui.views.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.payconiq.zekigu.githubrepos.R;
import com.payconiq.zekigu.githubrepos.ui.views.RepoListActivity;
import com.payconiq.zekigu.githubrepos.ui.utils.UIUtils;

/**
 * Created by zekigu on 15.09.2017
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                UIUtils.startAnyActivity(SplashActivity.this, RepoListActivity.class);
                finish();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
