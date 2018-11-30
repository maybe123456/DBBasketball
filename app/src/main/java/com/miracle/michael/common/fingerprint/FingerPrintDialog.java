package com.miracle.michael.common.fingerprint;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;

import com.miracle.R;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.FingerprintDialogBinding;

import java.lang.ref.WeakReference;

import static com.miracle.michael.common.fingerprint.AuthCallback.MSG_AUTH_ERROR;
import static com.miracle.michael.common.fingerprint.AuthCallback.MSG_AUTH_FAILED;
import static com.miracle.michael.common.fingerprint.AuthCallback.MSG_AUTH_HELP;
import static com.miracle.michael.common.fingerprint.AuthCallback.MSG_AUTH_SUCCESS;


/**
 * Created by zWX281801 on 2017/4/12.
 */

public class FingerPrintDialog extends Dialog implements View.OnClickListener {
    private ZHandler handler;
    private ObjectAnimator shakeAnim;
    private AuthCallback myAuthCallback;
    private CancellationSignal cancellationSignal;
    private FingerprintDialogBinding binding;
    private boolean success;


    public FingerPrintDialog(Context context) {
        super(context, R.style.commondialog);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fingerprint_dialog, null, false);
        binding.btCancel.setOnClickListener(this);
        handler = new ZHandler(this);
        myAuthCallback = new AuthCallback(handler);
        initShakeAnim();
        setContentView(binding.getRoot());
    }

    private void initShakeAnim() {
        shakeAnim = ObjectAnimator.ofFloat(binding.tvTryAgain, "translationX", -20, 20, -15, 15, -10, 10, -5, 5, 0);
        shakeAnim.setDuration(1000);
    }


    private static final class ZHandler extends Handler {
        private WeakReference<FingerPrintDialog> weakReference;

        public ZHandler(FingerPrintDialog dialog) {
            weakReference = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            FingerPrintDialog dialog = weakReference.get();
            switch (msg.what) {
                case MSG_AUTH_SUCCESS:
                    dialog.success = true;
                    ToastUtil.toast("验证成功！");
                    dialog.dismiss();
                    break;
                case MSG_AUTH_FAILED:
                    dialog.success = false;
                    dialog.binding.tvTryAgain.setText("再试一次");
                    dialog.shakeAnim.start();
                    return;
                case MSG_AUTH_ERROR:
                    dialog.success = false;
                    dialog.handleErrorCode(msg.arg1);
                    break;
                case MSG_AUTH_HELP:
                    dialog.success = false;
                    break;
                default:
                    dialog.success = false;
                    break;
            }
        }
    }


    private void handleErrorCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                binding.tvTryAgain.setText("已锁定");
                setCancelable(true);
                return;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                ToastUtil.toast("超时");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public void dismiss() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
        super.dismiss();
    }

    @Override
    public void show() {
        try {
            startRecognizeFingerprint();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.toast("指纹初始化失败");
            return;
        }
        setCancelable(false);
        super.show();
    }

    private void startRecognizeFingerprint() throws Exception {
        CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
        if (cancellationSignal == null) {
            cancellationSignal = new CancellationSignal();
        }
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(getContext());
        fingerprintManager.authenticate(cryptoObjectHelper.buildCryptoObject(), 0, cancellationSignal, myAuthCallback, null);
    }
}
