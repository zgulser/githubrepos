package com.payconiq.zekigu.githubrepos.core.model.data;

import com.orm.SugarRecord;

/**
 * Created by zekigu on 15.09.2017
 */
public abstract class BaseRepo extends SugarRecord<BaseRepo>{

    protected String repoId;
    protected String name;
    protected String fullName;
    protected String desc;
    protected String url;
    protected boolean isPrivate;

    public BaseRepo(){
    }

    BaseRepo(String id, String name, String fullName, String desc, String url, boolean isPrivate){
        this.repoId = id;
        this.name = name;
        this.fullName = fullName;
        this.desc = desc;
        this.url = url;
        this.isPrivate = isPrivate;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setId(String repoId) {
        this.repoId = repoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public abstract String getOwnerAvatarUrl();
    public abstract String getOwnerLoginName();
    public abstract String getOwnerType();
    public abstract boolean isOwnerAdmin();
}
