package com.miracle.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.miracle.base.bean.UserBean;
import com.miracle.base.bean.UserInfoBean;
import com.miracle.base.im.ui.ChatActivity;
import com.miracle.base.switcher.WelcomeActivity;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.base.util.sqlite.SQLiteKey;
import com.miracle.base.util.sqlite.SQLiteUtil;
import com.miracle.michael.common.activity.AboutUsActivity;
import com.miracle.michael.common.activity.CircleTurntableActivity;
import com.miracle.michael.common.activity.CustomerServiceActivity;
import com.miracle.michael.common.activity.LoginActivity;
import com.miracle.michael.common.activity.MeInfoActivity;
import com.miracle.michael.common.activity.ModifyPasswordActivity;
import com.miracle.michael.common.activity.RegisterActivity;
import com.miracle.michael.common.activity.SettingActivity;
import com.miracle.michael.common.activity.TestActivity;
import com.miracle.michael.common.bean.ArticleDetailBean;
import com.miracle.michael.football.activity.FootballMeActivity;
import com.miracle.michael.football.activity.FootballMyCollectionsActivity;
import com.miracle.michael.football.activity.FootballSaiShiFenXiActivity;
import com.miracle.michael.lottery.activity.LotteryMyCollectionsActivity;
import com.miracle.sport.community.activity.PostCommentListActivity;
import com.miracle.sport.community.activity.PostDetailActivity;
import com.miracle.sport.home.activity.CommentListActivity;

public class GOTO {
    public static void WelcomeActivity(Context context) {
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    public static void MainActivity(Context context) {
        App.getApp().finishAllActivity();
        context.startActivity(new Intent(context, AppConfig.mainClass));
        ((Activity) context).overridePendingTransition(0, 0);
    }

    public static void RegisterActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    public static void TestActivity(Context context) {
        context.startActivity(new Intent(context, TestActivity.class));
    }

    public static void LoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void LotteryMyCollectionsActivity(Context context) {
        context.startActivity(new Intent(context, LotteryMyCollectionsActivity.class));
    }

    public static void FootballMyCollectionsActivity(Context context) {
        context.startActivity(new Intent(context, FootballMyCollectionsActivity.class));
    }

    public static void SettingActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    public static void CustomerServiceActivity(Context context) {
        context.startActivity(new Intent(context, CustomerServiceActivity.class));
    }

    public static void CircleTurntableActivity(Context context) {
        context.startActivity(new Intent(context, CircleTurntableActivity.class));
    }

    public static void MeInfoActivity(Context context, UserInfoBean userInfo) {
        context.startActivity(new Intent(context, MeInfoActivity.class).putExtra(Constant.USER_INFO, userInfo));
    }

    public static void FootballMeActivity(Context context, UserInfoBean userInfo) {
        context.startActivity(new Intent(context, FootballMeActivity.class).putExtra(Constant.USER_INFO, userInfo));
    }

    public static void CommentListActivity(Context context, ArticleDetailBean newsDetailBean) {
        context.startActivity(new Intent(context, CommentListActivity.class).putExtra(Constant.COMMENT_LIST, newsDetailBean));
    }

    public static void ModifyPasswordActivity(Context context) {
        context.startActivity(new Intent(context, ModifyPasswordActivity.class));
    }

    public static void AboutUsActivity(Context context) {
        context.startActivity(new Intent(context, AboutUsActivity.class));
    }

    public static void FootballSaiShiFenXiActivity(Context context) {
        context.startActivity(new Intent(context, FootballSaiShiFenXiActivity.class));
    }

    public static void ChatActivity(Context context) {
        if (CommonUtils.getUser() == null) {
            LoginActivity(context);
        } else if (TextUtils.isEmpty(AppConfig.groupId)) {
            ToastUtil.toast("聊天室登录中,请稍后！");
            UserBean user = CommonUtils.getUser();
            if (user == null)
                return;
            CommonUtils.EMLogin(user.getUsername(), SQLiteUtil.getEncryptedString(SQLiteKey.PASSWORD));
        } else {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("chatType", com.miracle.base.im.Constant.CHATTYPE_GROUP);
            intent.putExtra("userId", AppConfig.groupId);
            context.startActivity(intent);
        }
    }

    public static void PostDetailActivity(Context context, int id) {
        context.startActivity(new Intent(context, PostDetailActivity.class).putExtra(Constant.POST_ID, id));
    }

    public static void PostCommentListActivity(Context context, int id) {
        context.startActivity(new Intent(context, PostCommentListActivity.class).putExtra(Constant.POST_ID, id));
    }

}
