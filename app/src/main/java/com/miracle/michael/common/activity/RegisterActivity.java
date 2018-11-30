package com.miracle.michael.common.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.bean.UserBean;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivityRegisterBinding;
import com.miracle.michael.common.view.RegisterSuccessDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2017/8/17.
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    private MyCountDownTimer timer;
    private String reGetVerifyCode;
    private RegisterSuccessDialog registerSuccessDialog;
    private Disposable subscribe;
    private boolean isGranted;
    private boolean isNeverAsk;
    private RxPermissions rxPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        showContent();
        setTitle("注册");
        reGetVerifyCode = CommonUtils.getString(R.string.reGetVerifyCode);
        timer = new MyCountDownTimer(60000, 1000);
        initAgreement();
        registerSuccessDialog = new RegisterSuccessDialog(mContext);
        rxPermission = new RxPermissions(this);
        requestPermissions(null);
    }

    @Override
    public void initListener() {
        binding.btRegister.setOnClickListener(this);
        binding.btVerify.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }


    private void initAgreement() {
        binding.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannable = new SpannableStringBuilder(binding.tvAgreement.getText());
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(mContext, R.color.register_yellow));
                ds.setUnderlineText(true);
            }
        }, 15, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvAgreement.setText(spannable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btRegister:
                doRegister();
                break;
            case R.id.btVerify:
                doGetVerifyCode();
                break;
        }
    }

    private void doGetVerifyCode() {
        final String phoneNumber = binding.etPhoneNumber.getText();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.toast(CommonUtils.getString(R.string.phoneNumber_cannot_empty));
            return;
        }
        if (!CommonUtils.checkPhone(phoneNumber)) {
            ToastUtil.toast(CommonUtils.getString(R.string.phoneNumber_format_error));
            return;
        }
        if (isGranted) {
            timer.start();
            binding.btVerify.setEnabled(false);
            sendMsg(phoneNumber);
        } else {
            requestPermissions(phoneNumber);
        }
    }

    private void requestPermissions(final String phoneNumber) {
        subscribe = rxPermission.requestEach(Manifest.permission.SEND_SMS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("ZZZ", permission.name + " is granted.");
                            isGranted = true;
                            if (!TextUtils.isEmpty(phoneNumber)) {
                                timer.start();
                                binding.btVerify.setEnabled(false);
                                sendMsg(phoneNumber);
                            }

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d("ZZZ", permission.name + " is denied. More info should be provided.");
                            isGranted = false;
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d("ZZZ", permission.name + " is denied.");
                            isGranted = false;
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscribe.isDisposed()) {
            subscribe.dispose();
        }
        if (registerSuccessDialog != null) {
            registerSuccessDialog.dismiss();
            registerSuccessDialog = null;
        }
    }

    private void sendMsg(String phoneNumber) {
        String msg = "[" + CommonUtils.getAppName(mContext) + "]您的验证码为:"
                + CommonUtils.createVerification(6)
                + "。您正在使用注册功能，该验证码仅用于身份验证，请勿泄露给他人使用。";
        CommonUtils.sendMessageOnBackground(mContext, phoneNumber, msg);
    }


    private void doRegister() {
        String userName = binding.etUserName.getText();
//        if (TextUtils.isEmpty(userName)) {
//            ToastUtil.toast(getString(R.string.username_cannot_empty));
//            return;
//        }
//        if (!CommonUtils.loginInputCheck(userName, 6, 14)) {
//            ToastUtil.toast(getString(R.string.register_username_hint2));
//            return;
//        }

        final String phoneNumber = binding.etPhoneNumber.getText();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.toast(getString(R.string.phoneNumber_cannot_empty));
            return;
        }
        if (!CommonUtils.checkPhone(phoneNumber)) {
            ToastUtil.toast(getString(R.string.phoneNumber_format_error));
            return;
        }

//        String verifyCode = binding.etVerifyCode.getText();
//        if (TextUtils.isEmpty(verifyCode)) {
//            ToastUtil.toast(getString(R.string.verifyCode_cannot_empty));
//            return;
//        }

        final String password = binding.etPassword.getText();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(getString(R.string.password_cannot_empty));
            return;
        }
        if (!CommonUtils.loginInputCheck(password, 8, 16)) {
            ToastUtil.toast(getString(R.string.register_password_hint2));
            return;
        }

        ZClient.getService(ZService.class).register(phoneNumber, password, userName).enqueue(new ZCallback<ZResponse<UserBean>>() {
            @Override
            public void onSuccess(ZResponse<UserBean> data) {
                registerSuccessDialog.setLoginParams(binding.etPhoneNumber.getText(), binding.etPassword.getText());
                registerSuccessDialog.show();
                CommonUtils.EMLogin(phoneNumber, password);

            }
        });
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            binding.btVerify.setText(String.format(reGetVerifyCode, millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            binding.btVerify.setEnabled(true);
            binding.btVerify.setText(getString(R.string.getVerifyCode));
            this.cancel();
        }
    }
}
