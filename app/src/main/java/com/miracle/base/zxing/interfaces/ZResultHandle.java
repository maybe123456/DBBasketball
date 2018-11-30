package com.miracle.base.zxing.interfaces;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;

import com.google.zxing.Result;
import com.miracle.base.zxing.camera.CameraManager;

/**
 * Created by Administrator on 2017/9/4.
 */

public interface ZResultHandle {
    void handleDecode(Result result, Bundle bundle);

    Activity getActivity();

    CameraManager getCameraManager();

    Handler getHandler();

    Rect getCropRect();
}
