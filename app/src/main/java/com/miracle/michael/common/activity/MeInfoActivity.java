package com.miracle.michael.common.activity;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.GOTO;
import com.miracle.base.bean.UserInfoBean;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivityMeInfoBinding;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MeInfoActivity extends BaseActivity<ActivityMeInfoBinding> {
    private static final int IMAGE_PICKER = 0x123;
    private RxPermissions rxPermission;
    private Disposable subscribe;
    private boolean isGranted;
    private UserInfoBean userInfo;

    @Override
    public int getLayout() {
        return R.layout.activity_me_info;
    }

    @Override
    public void initView() {
        setTitle("修改头像");
        showContent();
        userInfo = (UserInfoBean) getIntent().getSerializableExtra(Constant.USER_INFO);
        GlideApp.with(this).load(userInfo.getImg()).placeholder(R.mipmap.default_head).into(binding.ivHeadImg);
        binding.ibNickName.setText(userInfo.getNickname());
        binding.ibPhone.setText(userInfo.getUsername());
        binding.ibEmail.setText(userInfo.getEmall());
        rxPermission = new RxPermissions(this);
        requestPermissions();

        ImagePicker.getInstance().setSelectLimit(1);
    }

    private void requestPermissions() {
        subscribe = rxPermission.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("ZZZ", permission.name + " is granted.");
                            isGranted = true;

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
    public void initListener() {
        binding.ivHeadImg.setOnClickListener(this);
        binding.ibModifyPassword.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivHeadImg:
                if (isGranted) {
                    startActivityForResult(new Intent(mContext, ImageGridActivity.class), IMAGE_PICKER);
                } else {
                    requestPermissions();
                }

                //如果你想直接调用相机Intent intent = new Intent(this, ImageGridActivity.class);
                //intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                //      startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;

            case R.id.ibModifyPassword:
                GOTO.ModifyPasswordActivity(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.dispose();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && !images.isEmpty()) {
                    String path = images.get(0).path;
                    GlideApp.with(this).load(path).into(binding.ivHeadImg);
                    uploadHeadimg(path);
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadHeadimg(String path) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        ZClient.getService(ZService.class).uploadHeadImg(userInfo.getUsername(), part).enqueue(new ZCallback<ZResponse>() {
            @Override
            public void onSuccess(ZResponse data) {
                ToastUtil.toast(data.getMessage());
            }
        });

    }
}
