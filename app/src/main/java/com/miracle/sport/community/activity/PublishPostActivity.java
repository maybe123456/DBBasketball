package com.miracle.sport.community.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivityPublishPostBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.bean.CircleBean;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Michael on 2018/10/29 21:57 (星期一)
 */
public class PublishPostActivity extends BaseActivity<ActivityPublishPostBinding> {
    private static final int IMAGE_PICKER = 0x123;
    private static final int GET_CIRCLE = 0x124;
    private RxPermissions rxPermission;
    private Disposable subscribe;
    private boolean isGranted;


    private PreViewAdapter mAdapter;
    private boolean hasAdd;
    private CircleBean.ChildBean circle;

    @Override
    public int getLayout() {
        return R.layout.activity_publish_post;
    }

    @Override
    public void initView() {
        showContent();
        setTitle("发帖子");
        setRight("完成", Color.WHITE, 16);
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishPost();
            }
        });
        rxPermission = new RxPermissions(this);
        requestPermissions();
        binding.recyclerView.setAdapter(mAdapter = new PreViewAdapter(R.layout.comment_picture_view));
        mAdapter.addData("");
        hasAdd = true;

        ImagePicker.getInstance().setSelectLimit(3);
    }

    private void requestPermissions() {
        subscribe = rxPermission.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        isGranted = permission.granted;
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.dispose();
    }

    @Override
    public void initListener() {
        binding.llCircle.setOnClickListener(this);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rlAdd:
                        if (isGranted) {
                            ImagePicker.getInstance().setSelectLimit(4 - mAdapter.getItemCount());
                            startActivityForResult(new Intent(mContext, ImageGridActivity.class), IMAGE_PICKER);
                        } else {
                            requestPermissions();
                        }
                        break;
                    case R.id.tvDelete:
                        mAdapter.remove(position);
                        break;
                }
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCircle:
                startActivityForResult(new Intent(mContext, CircleActivity.class).putExtra("isFromPublishPostActivity", true), GET_CIRCLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_PICKER:
                if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                    if (data != null) {
                        ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        if (images != null && !images.isEmpty()) {
                            for (ImageItem imageItem : images) {
                                mAdapter.addData(mAdapter.getItemCount() - 1, imageItem.path);
                            }
                        }
                    } else {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case GET_CIRCLE:
                if (resultCode == RESULT_OK) {
                    circle = (CircleBean.ChildBean) data.getSerializableExtra("ChildBean");
                    binding.tvCircle.setText(circle.getName());
                }
                break;
        }

    }

    private void publishPost() {
        if (circle == null) {
            ToastUtil.toast("请选择您要发布的圈子");
            return;
        }
        loadingDialog.show();
        if (mAdapter.getData().size() <= 1) {
            ZClient.getService(SportService.class).publishPost(circle.getId(), binding.etTitle.getText().toString(), binding.etContent.getText().toString()).enqueue(new ZCallback<ZResponse>(loadingDialog) {
                @Override
                public void onSuccess(ZResponse data) {
                    ToastUtil.toast(data.getMessage());
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            List<MultipartBody.Part> parts = new ArrayList<>();
            for (String path : mAdapter.getData()) {
                if (!TextUtils.isEmpty(path)) {
                    File file = new File(path);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("image[]", file.getName(), requestFile);
                    parts.add(part);
                }
            }
            ZClient.getService(SportService.class).publishPost(circle.getId(), binding.etTitle.getText().toString(), binding.etContent.getText().toString(), parts).enqueue(new ZCallback<ZResponse>(loadingDialog) {
                @Override
                public void onSuccess(ZResponse data) {
                    ToastUtil.toast(data.getMessage());
                    setResult(RESULT_OK);
                    finish();
                }
            });

        }
    }

    private final class PreViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        PreViewAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String path) {
            helper.addOnClickListener(R.id.rlAdd);
            helper.addOnClickListener(R.id.tvDelete);
            Bitmap bitmap = CommonUtils.getBitmap(path);
            if (bitmap == null) {
                helper.setGone(R.id.rlItem, false);
                helper.setGone(R.id.rlAdd, true);

            } else {
                helper.setGone(R.id.rlItem, true);
                helper.setGone(R.id.rlAdd, false);
                helper.setImageBitmap(R.id.ivPreView, bitmap);
            }
        }

        @Override
        public void addData(int position, @NonNull String data) {
            super.addData(position, data);
            if (getItemCount() == 4) {
                remove(3);
                hasAdd = false;
            }
        }

        @Override
        public void remove(int position) {
            super.remove(position);
            if (getItemCount() == 2 && !hasAdd) {
                addData("");
                hasAdd = true;
            }
        }
    }
}
