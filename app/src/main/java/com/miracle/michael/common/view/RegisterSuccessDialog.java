package com.miracle.michael.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.miracle.R;
import com.miracle.base.App;
import com.miracle.base.AppConfig;
import com.miracle.base.GOTO;
import com.miracle.base.bean.UserBean;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.GsonUtil;
import com.miracle.base.util.sqlite.SQLiteKey;
import com.miracle.base.util.sqlite.SQLiteUtil;


/**
 * Created by Administrator on 2017/8/24.
 */

public class RegisterSuccessDialog extends Dialog {

    private String userName;
    private String password;
    private Context context;

    public RegisterSuccessDialog(@NonNull final Context context) {
        super(context, R.style.commondialog);
        this.context = context;
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_register_success);
        findViewById(R.id.btNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZClient.getService(ZService.class).login(userName, password).enqueue(new ZCallback<ZResponse<UserBean>>() {
                    @Override
                    public void onSuccess(ZResponse<UserBean> data) {
                        UserBean user = data.getData();
                        AppConfig.USER_ID = String.valueOf(user.getUserId());
                        SQLiteUtil.saveString(SQLiteKey.USER, GsonUtil.obj2Json(user));
                        SQLiteUtil.saveEncryptedString(SQLiteKey.PASSWORD, password);
                        SQLiteUtil.saveBoolean(SQLiteKey.AUTOLOGIN+CommonUtils.getUserId(), true);
                        GOTO.MainActivity(context);
                        App.getApp().finishAllActivity();
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                });
            }
        });
    }

    public void setLoginParams(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
