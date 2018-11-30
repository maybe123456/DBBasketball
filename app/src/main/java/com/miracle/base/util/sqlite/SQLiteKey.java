package com.miracle.base.util.sqlite;

/**
 * Created by Administrator on 2017/6/10.
 */

public interface SQLiteKey {
    String PASSWORD = "password";

    String AUTOLOGIN = "autologin";
    String FINGERPRINT_LOGIN = "fingerprint_login";
    String USER = "user";
    String TOKEN = "token";
    String FIRST_LOGIN = "first_login";

    //是否已抽奖
    String HAS_DRAWED = "has_drawed";
}
