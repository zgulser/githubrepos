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
    public void loadImageByUrl(Uri uri, ImageView imageView, int width, int height, boolean hasPlaceHolder) {
        Glide.with(ApplicationManager.getInstance().getContext())
                .load(uri)
                .override(width, height)
                .centerCrop().fitCenter().crossFade()
                .skipMemoryCache(false)
                .placeholder(hasPlaceHolder ? R.drawable.repo_default_avatar : R.color.colorPrimaryDark)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadImageByFileName(String fileName, ImageView imageView,
                                    int width, int height, boolean hasPlaceHolder) {
        // TODO: Implement image loading by the filename
    }
}
