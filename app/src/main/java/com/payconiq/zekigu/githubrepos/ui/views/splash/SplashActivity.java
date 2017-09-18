package com.payconiq.zekigu.githubrepos.ui.views.splash;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.payconiq.zekigu.githubrepos.R;
import com.payconiq.zekigu.githubrepos.databinding.ActivitySplashBinding;
import com.payconiq.zekigu.githubrepos.ui.views.RepoListActivity;
import com.payconiq.zekigu.githubrepos.ui.utils.UIUtils;

/**
 * Created by zekigu on 15.09.2017
 */
public class SplashActivity extends AppCompatActivity {

    private final int ROTATE_ANIMATION_DURATION_IN_MS = 3000;
    private final int FADE_ANIMATION_ANGLE_IN_DEGREES = 360;
    private final int FADE_ANIMATION_DURATION_IN_MS = 2000;
    private ActivitySplashBinding activitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        activitySplashBinding.appLabel.setAlpha(0.0f);
        startAnimations();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startAnimations(){
        activitySplashBinding.appIcon.animate().rotation(FADE_ANIMATION_ANGLE_IN_DEGREES).setDuration(ROTATE_ANIMATION_DURATION_IN_MS)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        activitySplashBinding.appLabel.setVisibility(View.VISIBLE);
                        activitySplashBinding.appLabel.animate().alpha(1.0f).setDuration(FADE_ANIMATION_DURATION_IN_MS)
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {}

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        UIUtils.startAnyActivity(SplashActivity.this, RepoListActivity.class);
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {}

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {}
                                }).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                }).start();
    }
}
