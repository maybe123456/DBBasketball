package com.miracle.base.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/13.
 */

public class ThreadUtil {
    private static Handler handler = null;

    static {
        handler = new Handler(Looper.getMainLooper());//初始化handle，用于切换到主线程
    }

    private static ExecutorService mCacheExcutor = Executors.newCachedThreadPool();//初始化线程池，用于切换到线程

    /**
     * 执行在ui线程
     *
     * @param r
     */
    public static void runInUiThread(Runnable r) {
        if (handler != null) {
            handler.post(r);
        }
    }

    /**
     * 执行在线程
     *
     * @param r
     */
    public static void runInBackGroundThread(Runnable r) {
        if (mCacheExcutor != null) {
            mCacheExcutor.execute(r);
        }
    }


    public static void ondestroy() {
        if (mCacheExcutor != null) {
            mCacheExcutor.shutdown();
        }
    }

}
