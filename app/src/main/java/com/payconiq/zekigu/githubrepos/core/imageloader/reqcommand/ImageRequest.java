package com.payconiq.zekigu.githubrepos.core.imageloader.reqcommand;

/**
 * Created by zekigu on 15.09.2017
 */
public class ImageRequest {

    private String url;
    private String tag;
    private int priority;

    ImageRequest(String url, String tag, int priority) {
        this.url = url;
        this.tag = tag;
        this.priority = priority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String pUrl) {
        url = pUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String pTag) {
        this.tag = pTag;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int pPriority) {
        this.priority = pPriority;
    }


}
