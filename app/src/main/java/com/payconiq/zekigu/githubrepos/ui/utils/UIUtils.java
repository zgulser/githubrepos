package com.payconiq.zekigu.githubrepos.ui.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.payconiq.zekigu.githubrepos.R;
import com.payconiq.zekigu.githubrepos.core.permissions.GetWriteExternalStorageGrant;
import com.payconiq.zekigu.githubrepos.core.permissions.Grant;
import com.payconiq.zekigu.githubrepos.core.permissions.PermissionBroker;

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

    /**
     *
     * Desc: Sample method to show how you can use permission module
     *
     * @param context
     */
    public static void handleWriteExternalStoragePermission(Activity context) {
        Grant grant = new Grant(Manifest.permission.WRITE_EXTERNAL_STORAGE, context.getResources().getString(R.string.permission_exp_write_to_external));
        GetWriteExternalStorageGrant callGrant = new GetWriteExternalStorageGrant(grant, context);

        PermissionBroker broker = new PermissionBroker();
        broker.takePermission(callGrant);
        broker.executePermissions();
    }
}
