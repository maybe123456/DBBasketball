package com.miracle.michael.common.fingerprint;

import android.os.Handler;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Created by admin on 2017/4/11.
 */
public class AuthCallback extends FingerprintManagerCompat.AuthenticationCallback {

    public static final int MSG_AUTH_SUCCESS = 0;
    public static final int MSG_AUTH_FAILED = 1;
    public static final int MSG_AUTH_ERROR = 2;
    public static final int MSG_AUTH_HELP = 3;

    private Handler handler;

    public AuthCallback(Handler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);

        if (handler != null) {
            handler.obtainMessage(AuthCallback.MSG_AUTH_ERROR, errMsgId, 0).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);

        if (handler != null) {
            handler.obtainMessage(AuthCallback.MSG_AUTH_HELP, helpMsgId, 0).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);

        if (handler != null) {
            handler.obtainMessage(AuthCallback.MSG_AUTH_SUCCESS).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

        if (handler != null) {
            handler.obtainMessage(AuthCallback.MSG_AUTH_FAILED).sendToTarget();
        }
    }
}