package com.miracle.sport.schedule.fragment;

import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;

import com.miracle.base.BaseFragment;

public abstract class HandleFragment<T extends ViewDataBinding> extends BaseFragment<T> {
    public Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            onHandleMessage(msg);
        }
    };

    public abstract void onHandleMessage(Message msg);
}

