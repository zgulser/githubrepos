package com.payconiq.zekigu.githubrepos.core.imageloader;

import android.net.Uri;
import android.widget.ImageView;

import com.payconiq.zekigu.githubrepos.R;
import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by zekigu on 15.09.2017
 */
public final class ImageLoaderImpl implements ImageLoaderContract {

    public ImageLoaderImpl(){}

    @Override
    public void loadImageByUrl(ImageLoad imageLoad) {
        Glide.with(ApplicationManager.getInstance().getContext())
                .load(imageLoad.getUri())
                .override(imageLoad.getWidth(), imageLoad.getHeight())
                .centerCrop().fitCenter().crossFade()
                .skipMemoryCache(false)
                .placeholder(imageLoad.hasPlaceHolder() ? R.drawable.repo_default_avatar : R.color.colorPrimaryDark)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageLoad.getImageView());
    }

    @Override
    public void loadImageByFileName(ImageLoad imageLoad) {
        // TODO: Implement image loading by the filename
    }
}
