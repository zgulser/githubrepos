package com.payconiq.zekigu.githubrepos.core.permissions;

import android.app.Activity;

/**
 * Created by zekigu on 16.09.2017
 */

public class GetWriteExternalStorageGrant implements Permission {
    Grant grant;
    Activity context;

    public GetWriteExternalStorageGrant(Grant grant, Activity context){
        this.grant = grant;
        this.context = context;
    }

    @Override
    public void request() {
        grant.getWriteExternalStoragePermission(context);
    }
}
