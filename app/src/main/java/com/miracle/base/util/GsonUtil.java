package com.miracle.base.util;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public abstract class GsonUtil {

    /**
     * 解析Gson对象，
     *
     * @param json
     * @param strategy 替换字段
     * @param type     泛型
     * @return
     */
    public static <T> T json2Obj(String json, FieldNamingStrategy strategy,
                                 Type type) {
        GsonBuilder build = new GsonBuilder();
        if (null != strategy) {
            build.setFieldNamingStrategy(strategy);
        }
        return build.create().fromJson(json, type);
    }

    public static <T> T json2Obj(String json, Type type) {
        return new GsonBuilder().create().fromJson(json, type);
    }

    public static String obj2Json(Object obj) {
        String result = "";
        try {
            Gson gson = new Gson();
            result = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
