package com.mifengkong.frtools.util;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.util.Util;
import com.mifengkong.frtools.transformation.RotateTransformation;

import java.io.File;

/**
 * Created by jack on 2017/8/22
 */

public class FRImageLoad {

    private FRImageLoad() {

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //加载图片地址
    public static void loadImage(String imageUrl, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }

    //加载图片地址(旋转)
    public static void loadImageTransForm(String imageUrl, ImageView imageView, float rotate) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl).transform(new RotateTransformation(imageView.getContext(), rotate)).into(imageView);
        }
    }

    //加载图片地址(去掉动画)
    public static void loadImageDoNotAnim(String imageUrl, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl).dontAnimate().into(imageView);
        }
    }

    //加载图片地址(带占位图)
    public static void loadImage(String imageUrl, ImageView imageView, int placeholder) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl).placeholder(placeholder).error(placeholder).into(imageView);
        }
    }

    //加载本地图片
    public static void loadImage(Integer imageRes, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageRes).into(imageView);
        }
    }

    //加载图片Uri
    public static void loadImage(Uri imageUri, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUri).into(imageView);
        }
    }

    //加载图片文件
    public static void loadImage(File imageFile, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageFile).into(imageView);
        }
    }

    //加载图片字节
    public static void loadImage(byte[] imageByte, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageByte).into(imageView);
        }
    }

    //加载Gif图片地址
    public static void loadImageGif(String imageUrl, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl).asGif().into(imageView);
        }
    }

    //监听图片加载
    public static void loadImage(String imageUrl, ImageView imageView, GlideDrawableImageViewTarget glideDrawableImageViewTarget) {
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl).into(glideDrawableImageViewTarget);
        }
    }
}