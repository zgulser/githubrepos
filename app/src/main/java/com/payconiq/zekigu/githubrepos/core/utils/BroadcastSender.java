package com.payconiq.zekigu.githubrepos.core.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by zekigu on 15.09.2017.
 */
public class BroadcastSender {

    public static void sendBroadcast(Context pContext, String pAction){
        LocalBroadcastManager.getInstance(pContext).sendBroadcast(new Intent(pAction));
    }
}
