package com.payconiq.zekigu.githubrepos.core.model.data;

/**
 * Created by zekigu on 15.09.2017.
 */
public abstract class BaseRepo {

    protected String id;
    protected String name;
    protected String fullName;
    protected String desc;
    protected String url;
    protected boolean isPrivate;

    BaseRepo(String id, String name, String fullName, String desc, String url, boolean isPrivate){
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.desc = desc;
        this.url = url;
        this.isPrivate = isPrivate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public abstract String getLogin();
    public abstract String getType();
    public abstract boolean isOwnerAdmin();
}
