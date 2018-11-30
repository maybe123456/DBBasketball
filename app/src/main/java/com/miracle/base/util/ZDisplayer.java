package com.miracle.base.util;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.miracle.R;
import com.miracle.base.network.GlideApp;

import java.io.File;

public class ZDisplayer implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideApp.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.defaule_img)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);


    }

    @Override
    public void clearMemoryCache() {
//        GlideApp.get(ContextHolder.getContext()).clearMemory();
//        GlideApp.get(ContextHolder.getContext()).clearDiskCache();
    }
}
