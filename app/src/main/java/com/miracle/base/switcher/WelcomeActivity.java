package com.miracle.base.switcher;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.GOTO;
import com.miracle.base.bean.UserBean;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.Encryptor;
import com.miracle.base.util.GsonUtil;
import com.miracle.base.util.sqlite.SQLiteKey;
import com.miracle.base.util.sqlite.SQLiteUtil;
import com.miracle.michael.common.fingerprint.FingerPrintDialog;
import com.yanzhenjie.sofia.Sofia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Administrator on 2018/5/10.
 */

public class WelcomeActivity extends Activity {
    private Context context;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Sofia.with(this).invasionStatusBar();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundResource(R.mipmap.astart);
        setContentView(linearLayout);
        SharedPreferences setting = getSharedPreferences("FIRST", 0);
        mUrl = setting.getString("URL", "");
        if (hasNetwork()) {
            reqData();
        } else {
            nextActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private boolean hasNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private void reqData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://860790.com/v1/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(QService.class).reqSwitcher(getPackageName()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String s = response.body();
                    DBBean body = GsonUtil.json2Obj(s, DBBean.class);
                    AppConfig.DBENTITY = body.getData();

                    if (body.getCode() == 1) {
                        DBBean.DataBean data = body.getData();
                        if (data.getRflag() == 1) {
                            mUrl = data.getRurl();
                            if (data.getUflag() == 1) {
                                mUrl = data.getUurl();
                            }
                            getSharedPreferences("FIRST", 0).edit().putString("URL", mUrl).apply();
                            startActivity(new Intent(WelcomeActivity.this, GameActivity.class).putExtra("url", mUrl));
                            finish();
                        } else {
                            nextActivity();
                        }
                    } else {
                        nextActivity();
                    }
                } catch (Exception e) {
                    nextActivity();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                nextActivity();
            }
        });
    }

    private void nextActivity() {
        if (TextUtils.isEmpty(mUrl)) {
            goNative();
        } else {
            startActivity(new Intent(this, GameActivity.class).putExtra("url", mUrl));
            finish();
        }
    }

    private void goNative() {
        if (SQLiteUtil.getBoolean(SQLiteKey.FINGERPRINT_LOGIN + CommonUtils.getUserId())) {
            final FingerPrintDialog fingerPrintDialog = new FingerPrintDialog(this);
            fingerPrintDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (fingerPrintDialog.isSuccess()) {
                        GOTO.MainActivity(context);
                        autoLogin();
                    } else {
                        GOTO.LoginActivity(context);
                    }
                    finish();
                }
            });
            fingerPrintDialog.show();
            return;
        }
        GOTO.MainActivity(context);
        if (SQLiteUtil.getBoolean(SQLiteKey.AUTOLOGIN + CommonUtils.getUserId())) {
            autoLogin();
        }
        finish();
    }

    private void autoLogin() {
        UserBean user = CommonUtils.getUser();
        if (user == null)
            return;
        final String userName = user.getUsername();
        final String pwd = SQLiteUtil.getEncryptedString(SQLiteKey.PASSWORD);
        ZClient.getService(ZService.class).login(userName, pwd).enqueue(new ZCallback<ZResponse<UserBean>>() {
            @Override
            public void onSuccess(ZResponse<UserBean> data) {
                CommonUtils.EMLogin(userName, pwd);
                UserBean user = data.getData();
                AppConfig.USER_ID = String.valueOf(user.getUserId());
                SQLiteUtil.saveString(SQLiteKey.TOKEN, Encryptor.encryptString(user.getToken()));
            }
        });
    }

    private interface QService {
        @POST("{packageName}")
        Call<String> reqSwitcher(@Path("packageName") String packageName);
    }
}