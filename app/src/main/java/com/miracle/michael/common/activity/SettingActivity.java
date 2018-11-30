package com.miracle.michael.common.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;

import com.hyphenate.chat.EMClient;
import com.miracle.R;
import com.miracle.base.App;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.base.util.sqlite.SQLiteKey;
import com.miracle.base.util.sqlite.SQLiteUtil;
import com.miracle.base.view.CommonDialog;
import com.miracle.databinding.ActivitySettingBinding;
import com.miracle.michael.common.fingerprint.FingerPrintDialog;

import java.lang.ref.WeakReference;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    private SHandler handler;
    private ProgressDialog dialogProgress;
    private FingerPrintDialog fingerPrintDialog;
    private CommonDialog openSettingsDialog;
    private boolean autoLogin;
    private boolean fingerprintLogin;

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setTitle("设置");
        showContent();
        binding.swAutoLogin.setChecked(autoLogin = SQLiteUtil.getBoolean(SQLiteKey.AUTOLOGIN + CommonUtils.getUserId()));
        binding.swFingerprintLogin.setChecked(fingerprintLogin = SQLiteUtil.getBoolean(SQLiteKey.FINGERPRINT_LOGIN + CommonUtils.getUserId()));
        handler = new SHandler(this);
        initDialogs();
    }

    private void initDialogs() {
        dialogProgress = new ProgressDialog(mContext);
        openSettingsDialog = new CommonDialog(mContext);
        openSettingsDialog.setImg(R.mipmap.fingerprint);
        openSettingsDialog.setMessage("您尚未录入指纹，立即录入指纹?");
        openSettingsDialog.setBtListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                openSettingsDialog.dismiss();
            }
        });

        fingerPrintDialog = new FingerPrintDialog(mContext);
        fingerPrintDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.swFingerprintLogin.setChecked(fingerPrintDialog.isSuccess());
            }
        });
    }

    @Override
    public void initListener() {
        binding.btExit.setOnClickListener(this);
        binding.ibClearCache.setOnClickListener(this);
        binding.ibCheckUpdate.setOnClickListener(this);

        binding.swAutoLogin.setOnClickListener(this);
        binding.swAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoLogin = isChecked;
            }
        });

        binding.swFingerprintLogin.setOnClickListener(this);
        binding.swFingerprintLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fingerprintLogin = isChecked;
            }
        });
    }

    @Override
    public void loadData() {

    }

    private static final class SHandler extends Handler {
        private WeakReference<SettingActivity> weakReference;

        public SHandler(SettingActivity settingActivity) {
            weakReference = new WeakReference<>(settingActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            weakReference.get().dialogProgress.dismiss();
            ToastUtil.toast(msg.what == 0 ? "缓存清理完成!" : "当前已是最新版本");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btExit:
                SQLiteUtil.saveBoolean(SQLiteKey.FINGERPRINT_LOGIN + CommonUtils.getUserId(), fingerprintLogin);
                SQLiteUtil.saveBoolean(SQLiteKey.AUTOLOGIN + CommonUtils.getUserId(), autoLogin);
                if (!autoLogin && !fingerprintLogin) {
                    SQLiteUtil.saveString(SQLiteKey.USER, "");
                }
                EMClient.getInstance().logout(false);
                App.getApp().finishAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
            case R.id.swAutoLogin:
                if (CommonUtils.getUser() == null) {
                    ToastUtil.toast("您尚未登录，请登录后再进行操作");
                    GOTO.LoginActivity(mContext);
                    binding.swAutoLogin.setChecked(false);
                    return;
                }
                break;
            case R.id.swFingerprintLogin:
                if (CommonUtils.getUser() == null) {
                    ToastUtil.toast("您尚未登录，请登录后再进行操作");
                    GOTO.LoginActivity(mContext);
                    binding.swFingerprintLogin.setChecked(false);
                    return;
                }
                if (!fingerprintLogin)
                    return;
                if (!CommonUtils.isHardwareDetected(mContext)) {
                    ToastUtil.toast("很抱歉，您手机不支持指纹识别");
                    return;
                }
                if (CommonUtils.isKeyguardSecure(mContext) && CommonUtils.hasEnrolledFingerprints(mContext)) {
                    fingerPrintDialog.show();
                } else {
                    openSettingsDialog.show();
                }
                break;
            case R.id.ibClearCache:
                dialogProgress.setMessage("清理中...");
                dialogProgress.show();
                handler.sendEmptyMessageDelayed(0, 1000);
                break;
            case R.id.ibCheckUpdate:
                dialogProgress.setMessage("检查中...");
                dialogProgress.show();
                handler.sendEmptyMessageDelayed(1, 1000);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogProgress != null) {
            dialogProgress.dismiss();
            dialogProgress = null;
        }
        if (openSettingsDialog != null) {
            openSettingsDialog.dismiss();
            openSettingsDialog = null;
        }
        if (fingerPrintDialog != null) {
            fingerPrintDialog.dismiss();
            fingerPrintDialog = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String userId = CommonUtils.getUserId();
        SQLiteUtil.saveBoolean(SQLiteKey.AUTOLOGIN + userId, autoLogin);
        SQLiteUtil.saveBoolean(SQLiteKey.FINGERPRINT_LOGIN + userId, fingerprintLogin);
        if (!autoLogin && !fingerprintLogin) {
            SQLiteUtil.saveString(SQLiteKey.USER, "");
        }
    }
}
