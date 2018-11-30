package com.miracle.base.http.callback;

/**
 * Created by Administrator on 2017/10/31.
 */

public abstract class ResultCallback{

    public void onSuccess(String tag,Object data){

    }

    public void onCacheSuccess(String tag, Object data){

    }

    public void onFailure(String tag,String exception){

    }

}
