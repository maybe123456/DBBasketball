package com.miracle.base.http.view;

/**
 * Created by Dengbocheng on 2017/4/22.
 */

public interface IBaseView<T> {

    void onSuccess(String tag, Object object);//执行完成某个操作调用
    void onCacheSuccess(String tag, Object object);
    void onFail(String tag, String msg);

}
