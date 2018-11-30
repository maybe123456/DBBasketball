package com.miracle.base.network;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import com.miracle.base.BaseActivity;
import com.miracle.base.util.GsonUtil;
import com.miracle.base.util.ToastUtil;
import com.miracle.base.util.sqlite.SQLiteUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ZCallback<T> implements Callback<T> {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected Dialog mDialog;
    protected String mCachKey;

    protected INetStatusUI mNetStatusUI;

    public ZCallback() {
    }


    public ZCallback(INetStatusUI netStnatusUI) {
        mNetStatusUI = netStnatusUI;
    }

    public ZCallback(String cachKey) {
        mCachKey = cachKey;
    }

    public ZCallback(SwipeRefreshLayout swipeRefreshLayout) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
    }

    public ZCallback(SwipeRefreshLayout swipeRefreshLayout, INetStatusUI iNetStatusUI) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        mNetStatusUI = iNetStatusUI;
    }

    public ZCallback(Dialog dialog) {
        mDialog = dialog;
    }

    public INetStatusUI getNetStatusUI() {
        return mNetStatusUI;
    }

    public void setNetStatusUI(INetStatusUI mNetStatusUI) {
        this.mNetStatusUI = mNetStatusUI;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeRefreshLayout = swipeRefreshLayout;
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public void setDialog(Dialog dialog) {
        mDialog = dialog;
    }

    public String getCachKey() {
        return mCachKey;
    }

    public void setCachKey(String cachKey) {
        mCachKey = cachKey;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T body = response.body();
        if (body instanceof ZResponse) {
            ZResponse zResponse = (ZResponse) body;
            int code = zResponse.getCode();
            if (code != 200 && code != 0) {
                onFailure(call, new Throwable("DB" + zResponse.getMessage()));
            } else {
                handlePlaceHolder(code);
                saveCache(zResponse);
                onSuccess((T) zResponse);
                onFinish(call);
            }
        } else {
            onFailure(call, new Throwable("DB返回数据格式不正确！"));
        }
    }

    public void handlePlaceHolder(int code) {
        if (mNetStatusUI != null) {
            if (code == 200) {
                mNetStatusUI.showContent();
            } else {
                mNetStatusUI.showEmpty();
            }
        }
    }

    protected abstract void onSuccess(T zResponse);

    protected void onCacheSuccess(T zResponse) {
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t.getMessage() != null && t.getMessage().startsWith("DB")) {
            ToastUtil.toast(t.getMessage().substring(2));
        }
        onFinish(call);
        if (mNetStatusUI != null) {
            mNetStatusUI.showError();
        }
    }

    protected void onFinish(Call<T> call) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (call != null) {
            call.cancel();
        }
    }

    private void saveCache(ZResponse zResponse) {
        if (!TextUtils.isEmpty(mCachKey)) {
            SQLiteUtil.saveString(mCachKey, GsonUtil.obj2Json(zResponse));
        }
    }
}
