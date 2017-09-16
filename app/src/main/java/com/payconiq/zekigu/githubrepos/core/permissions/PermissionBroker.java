package com.payconiq.zekigu.githubrepos.core.permissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zekigu on 15.09.2017
 */

public class PermissionBroker {
    private List<Permission> permissionList = new ArrayList<Permission>();

    public void takePermission(Permission permission){
        permissionList.add(permission);
    }

    public void executePermissions(){
        for (Permission permission : permissionList) {
            permission.request();
        }
        permissionList.clear();
    }
}
