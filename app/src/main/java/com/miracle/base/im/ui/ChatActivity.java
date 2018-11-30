package com.miracle.base.im.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.im.runtimepermissions.PermissionsManager;
import com.miracle.databinding.EmActivityChatBinding;

/**
 * chat activity，EaseChatFragment was used {@link #}
 */
public class ChatActivity extends BaseActivity<EmActivityChatBinding> {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

//    @Override
//    protected void onCreate(Bundle arg0) {
//        super.onCreate(arg0);
//        setContentView(R.layout.em_activity_chat);
//        getWindow().getDecorView().setBackgroundColor(getResources().getColor(com.hyphenate.easeui.R.color.main_bg_color));
////        Sofia.with(this).statusBarBackground(getResources().getColor(com.hyphenate.easeui.R.color.titlebar_color)).statusBarDarkFont();
//
//        activityInstance = this;
//        //get user id or group id
//        toChatUsername = getIntent().getExtras().getString("userId");
//        //use EaseChatFratFragment
//        chatFragment = new ChatFragment();
//        //pass parameters to chat fragment
//        chatFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
//
//    }

    @Override
    public int getLayout() {
        return R.layout.em_activity_chat;
    }

    @Override
    public void initView() {
        hideTitle();
        showContent();
        activityInstance = this;
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
//        chatFragment.onBackPressed();
//        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
        finish();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public void onClick(View v) {

    }
}
