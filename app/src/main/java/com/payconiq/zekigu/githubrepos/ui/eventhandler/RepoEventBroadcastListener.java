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

public class RepoEventBroadcastListener {

    private BaseActivity baseActivity;

    public RepoEventBroadcastListener(BaseActivity baseActivity){
        this.baseActivity = baseActivity;
    }

    private BroadcastReceiver repoEventListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BroadcastConstants.REPO_ADDED)){
                baseActivity.onRepoAdded();
            } else if(action.equals(BroadcastConstants.REPO_REMOVED)){
                baseActivity.onRepoRemoved();
            } else if(action.equals(BroadcastConstants.REPO_SEARCH_DONE)){
                baseActivity.onRepoSearchCompleted();
            }

            baseActivity.tuneVisibilities();
        }
    };

    public void registerReceiver() {
        LocalBroadcastManager.getInstance(baseActivity).registerReceiver(repoEventListener,
                new IntentFilter(BroadcastConstants.REPO_ADDED));
        LocalBroadcastManager.getInstance(baseActivity).registerReceiver(repoEventListener,
                new IntentFilter(BroadcastConstants.REPO_REMOVED));
        LocalBroadcastManager.getInstance(baseActivity).registerReceiver(repoEventListener,
                new IntentFilter(BroadcastConstants.REPO_SEARCH_DONE));
    }

    public void unregisterReceiver() {
        LocalBroadcastManager.getInstance(baseActivity).unregisterReceiver(repoEventListener);
    }
}
