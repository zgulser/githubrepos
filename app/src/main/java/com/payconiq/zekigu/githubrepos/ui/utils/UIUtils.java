package com.payconiq.zekigu.githubrepos.ui.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by zekigu on 07.06.2016.
 */
public class UIUtils {

    public static void startAnyActivity(Context pContext, Class pClass) {
        Intent intent = new Intent(pContext, pClass);
        if(intent.resolveActivity(pContext.getPackageManager()) != null){
            pContext.startActivity(intent);
        }
    }

    public static void startAnyActivityWithRepoIndex(Context pContext, Class pClass, int pIndex) {
        Intent intent = new Intent(pContext, pClass);
        intent.putExtra(UIConstants.REPO_INDEX_KEY, pIndex);
        if(intent.resolveActivity(pContext.getPackageManager()) != null){
            pContext.startActivity(intent);
        }
    }
}
