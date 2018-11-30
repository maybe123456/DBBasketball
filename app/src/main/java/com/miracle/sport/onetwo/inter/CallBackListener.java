package com.miracle.sport.onetwo.inter;

public interface CallBackListener<T>{
    void onStart();
    void onFinish(T data);
}
