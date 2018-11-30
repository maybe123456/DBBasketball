package com.miracle.base.network;

import android.text.TextUtils;

import com.miracle.base.util.GsonUtil;
import com.miracle.base.util.sqlite.SQLiteUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * Created by Michael on 2018/11/2 18:43 (星期五)
 */
public class RequestUtil {

    /**
     * 先取缓存，后请求数据覆盖
     *
     * @param call
     * @param callback
     */
    public static void cacheUpdate(Call call, ZCallback callback) {
        String key = callback.getCachKey();
        if (TextUtils.isEmpty(key)) {
            call.enqueue(callback);
        } else {
            Object body = parseJson(SQLiteUtil.getString(key), callback);
            if (body != null) {
                callback.onCacheSuccess(body);
            }
            call.enqueue(callback);
        }
    }

    /**
     * 有缓存，用缓存，不请求数据
     * 无缓存，请求数据
     *
     * @param call
     * @param callback
     */
    public static void cachePrior(Call call, ZCallback callback) {
        String key = callback.getCachKey();
        if (TextUtils.isEmpty(key)) {
            call.enqueue(callback);
        } else {
            Object body = parseJson(SQLiteUtil.getString(key), callback);
            if (body != null) {
                callback.onCacheSuccess(body);
            } else {
                call.enqueue(callback);
            }
        }
    }

    private static Object parseJson(String json, ZCallback callback) {
        Type mGenericSuperclass;
        Type genericSuperclass = callback.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            mGenericSuperclass = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            mGenericSuperclass = Object.class;
        }
        return GsonUtil.json2Obj(json, mGenericSuperclass);
    }
}
