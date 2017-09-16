package com.payconiq.zekigu.githubrepos.core.imageloader.reqcommand;

/**
 * Created by zekigu on 15.09.2017
 */
public class LoadImage implements Load{

    private ImageRequest request;

    LoadImage(ImageRequest request){
        this.request = request;
    }

    @Override
    public void load() {
    }
}
