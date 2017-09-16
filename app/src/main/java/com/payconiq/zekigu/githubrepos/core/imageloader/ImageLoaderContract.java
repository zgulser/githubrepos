package com.payconiq.zekigu.githubrepos.core.imageloader;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by zekigu on 15.09.2017
 */
public interface ImageLoaderContract {
    void loadImageByUrl(ImageLoad imageLoad);
    void loadImageByFileName(ImageLoad imageLoad);
}
