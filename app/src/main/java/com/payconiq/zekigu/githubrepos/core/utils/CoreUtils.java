package com.payconiq.zekigu.githubrepos.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;

/**
 * Created by zekigu on 15.09.2017.
 */
public class CoreUtils {
    public static boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) ApplicationManager.getInstance().getContext().
                        getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
