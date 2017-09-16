package com.payconiq.zekigu.githubrepos.core.imageloader;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by Backbase R&D B.V on 16/09/2017.
 */

public class ImageLoad {

    private Uri uri;
    private String fileName;
    private ImageView imageView;
    private int width;
    private int height;
    private boolean hasPlaceHolder;

    public ImageLoad(ImageLoadBuilder builder){
        this.uri = builder.uri;
        this.fileName = builder.fileName;
        this.imageView = builder.imageView;
        this.width = builder.width;
        this.height = builder.height;
        this.hasPlaceHolder = builder.hasPlaceHolder;
    }

    public Uri getUri() {
        return uri;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasPlaceHolder() {
        return hasPlaceHolder;
    }

    public static class ImageLoadBuilder {

        private Uri uri;
        private String fileName;
        private ImageView imageView;
        private int width = 200;
        private int height = 200;
        private boolean hasPlaceHolder;

        public ImageLoadBuilder(Uri uri, ImageView imageView) {
            this.uri = uri;
            this.imageView = imageView;
        }

        public ImageLoad build() {
            return new ImageLoad(this);
        }

        public ImageLoadBuilder width(int width){
            this.width = width;
            return this;
        }

        public ImageLoadBuilder height(int height){
            this.height = height;
            return this;
        }

        public ImageLoadBuilder name(String name){
            this.fileName = name;
            return this;
        }

        public ImageLoadBuilder hasPlaceHolder(boolean hasPlaceHolder){
            this.hasPlaceHolder = hasPlaceHolder;
            return this;
        }
    }
}
