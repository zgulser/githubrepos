package com.payconiq.zekigu.githubrepos.ui.eventhandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.payconiq.zekigu.githubrepos.core.utils.BroadcastConstants;
import com.payconiq.zekigu.githubrepos.ui.views.BaseActivity;

/**
 * Created by zekigu on 15.09.2017
 */

public class ErrorEventBroadcastListener {

    private BaseActivity baseActivity;

    public ErrorEventBroadcastListener(BaseActivity baseActivity){
        this.baseActivity = baseActivity;
    }

    private BroadcastReceiver errorEventListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BroadcastConstants.REPO_REQUEST_FAILED)){
                baseActivity.onRepoRequestFailed();
            } else if(action.equals(BroadcastConstants.NO_INTERNET_CONN)){
                baseActivity.onNoInternetConnection();
            }

            baseActivity.tuneVisibilities();
        }
    };

    public void registerReceiver() {
        LocalBroadcastManager.getInstance(baseActivity).registerReceiver(errorEventListener,
                new IntentFilter(BroadcastConstants.REPO_REQUEST_FAILED));
        LocalBroadcastManager.getInstance(baseActivity).registerReceiver(errorEventListener,
                new IntentFilter(BroadcastConstants.NO_INTERNET_CONN));
    }

    public void unregisterReceiver() {
        LocalBroadcastManager.getInstance(baseActivity).unregisterReceiver(errorEventListener);
    }
}
