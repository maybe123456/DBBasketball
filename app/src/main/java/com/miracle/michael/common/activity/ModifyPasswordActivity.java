package com.miracle.michael.common.activity;

import android.text.TextUtils;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.ToastUtil;
import com.miracle.base.util.sqlite.SQLiteKey;
import com.miracle.base.util.sqlite.SQLiteUtil;
import com.miracle.databinding.ActivityModifypasswordBinding;


/**
 * Created by Administrator on 2017/11/15.
 */

public class ModifyPasswordActivity extends BaseActivity<ActivityModifypasswordBinding> {


    @Override
    public int getLayout() {
        return R.layout.activity_modifypassword;
    }

    @Override
    public void initView() {
        setTitle("修改密码");
    }

    @Override
    public void initListener() {
        binding.btModifyPassword.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btModifyPassword:
                String oldPassword = binding.etOldPassword.getText();
                final String newPassword = binding.etNewPassword.getText();

                if (TextUtils.isEmpty(oldPassword)) {
                    ToastUtil.toast("请输入旧密码！");
                    return;
                }
                if (TextUtils.isEmpty(newPassword)) {
                    ToastUtil.toast("请输入新密码！");
                    return;
                }
//                if (!CommonUtils.loginInputCheck(oldPassword, 8, 16)) {
//                    ToastUtil.toast("旧密码格式错误！");
//                    return;
//                }
//                if (!CommonUtils.loginInputCheck(newPassword, 8, 16)) {
//                    ToastUtil.toast("新密码格式错误！");
//                    return;
//                }
                ZClient.getService(ZService.class).modifyPassword(oldPassword, newPassword).enqueue(new ZCallback<ZResponse>() {
                    @Override
                    public void onSuccess(ZResponse data) {
                        SQLiteUtil.saveEncryptedString(SQLiteKey.PASSWORD, newPassword);
                        ToastUtil.toast(data.getMessage());
                        finish();
                    }
                });
                break;
        }
    }
}
