package com.miracle.sport.me.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.Constant;
import com.miracle.base.GOTO;
import com.miracle.base.bean.UserInfoBean;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.F4Ddz2Binding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.bean.MyCircleBean;
import com.miracle.sport.community.bean.PostBean;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.me.activity.DDZMyCircleActivity;
import com.miracle.sport.me.activity.DDZMyPostActivity;
import com.miracle.sport.me.activity.DDZMyReplyActivity;
import com.miracle.sport.me.activity.MyCollectionsActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wx.goodview.GoodView;

import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

public class MeFragment extends BaseFragment<F4Ddz2Binding> {
    private SHandler handler;

    private UserInfoBean userInfo;
    private RxPermissions rxPermission;
    private Disposable subscribe;
    private boolean isGranted;
    private ProgressDialog dialogProgress;

    @Override
    public int getLayout() {
        return R.layout.f4_ddz2;
    }

    @Override
    public void initView() {
        dialogProgress = new ProgressDialog(mContext);
        handler = new SHandler(this);
        rxPermission = new RxPermissions(getActivity());
    }

    private void requestCameraPermission() {
        subscribe = rxPermission.requestEach(Manifest.permission.CAMERA)
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


    private void reqData() {
        if (CommonUtils.getUser() != null)
            ZClient.getService(ZService.class).getUserInfo().enqueue(new ZCallback<ZResponse<UserInfoBean>>(binding.swipeLayout) {
                @Override
                public void onSuccess(ZResponse<UserInfoBean> data) {
                    userInfo = data.getData();
                    binding.tvName.setText(userInfo.getNickname());
                    binding.tvPhone.setText(userInfo.getUsername());
                    GlideApp.with(mContext).load(userInfo.getImg()).placeholder(R.mipmap.default_head).into(binding.ivHeadImg);
                }
            });

        ZClient.getService(SportService.class).getMyPostList(1, 10).enqueue(new ZCallback<ZResponse<List<PostBean>>>() {
            @Override
            protected void onSuccess(ZResponse<List<PostBean>> zResponse) {
                if (zResponse != null) {
                    binding.ibmyPost.setText(MessageFormat.format("我的发帖{0}", zResponse.getTotal()));
                }
            }
        });
        ZClient.getService(SportService.class).getMyCircleList().enqueue(new ZCallback<ZResponse<List<MyCircleBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<MyCircleBean>> zResponse) {
                if (zResponse != null) {
                    List<MyCircleBean> data = zResponse.getData();
                    if (data != null && !data.isEmpty()) {
                        binding.ibmyCircle.setText(MessageFormat.format("我的圈子{0}", data.size()));
                    }
                }
            }
        });

        ZClient.getService(SportService.class).getMycollections(1, 10).enqueue(new ZCallback<ZResponse<List<Football>>>() {
            @Override
            protected void onSuccess(ZResponse<List<Football>> zResponse) {
                if (zResponse != null && zResponse.getTotal() > 0) {
                    binding.ibBailManage.setText(MessageFormat.format("我的收藏{0}", zResponse.getTotal()));
                }
            }
        });

    }

    @Override
    public void initListener() {
        binding.llMe.setOnClickListener(this);
        binding.ibOrderManage.setOnClickListener(this);
        binding.ibmyCircle.setOnClickListener(this);
        binding.ibmyPost.setOnClickListener(this);
        binding.ibBailManage.setOnClickListener(this);
        binding.ibSettings.setOnClickListener(this);
        binding.ibGroupChat.setOnClickListener(this);
        binding.ibCustomerService.setOnClickListener(this);
        binding.ibShare.setOnClickListener(this);
        binding.ibAboutUs.setOnClickListener(this);
        binding.appUpdate.setOnClickListener(this);
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqData();
            }
        });
        binding.appUpdate.setOnClickListener(this);
        binding.ibClearCache.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        reqData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMe:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    GOTO.MeInfoActivity(mContext, userInfo);
                }
                break;
            case R.id.ibOrderManage:
                GOTO.FootballSaiShiFenXiActivity(mContext);
                break;
            case R.id.ibBailManage:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
//                    GOTO.LotteryMyCollectionsActivity();
                    startActivity(new Intent(mContext, MyCollectionsActivity.class));
                }
                break;
            case R.id.ibmyCircle:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, DDZMyCircleActivity.class));
                }
                break;
            case R.id.ibmyPost:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, DDZMyPostActivity.class));
                }
                break;
            case R.id.ibmyReply:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
//                    GOTO.LotteryMyCollectionsActivity();
                    startActivity(new Intent(mContext, DDZMyReplyActivity.class));
                }
                break;
            case R.id.ibSettings:
                GOTO.SettingActivity(mContext);
                break;
            case R.id.ibGroupChat:
                GOTO.ChatActivity(mContext);
                break;

            case R.id.ibCustomerService:
                GOTO.CustomerServiceActivity(mContext);
                break;
            case R.id.ibShare:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, userInfo.getNickname() + "邀请你加入" + CommonUtils.getAppName(mContext));
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "分享"));
                }
                break;

            case R.id.ibAboutUs:
                GOTO.AboutUsActivity(mContext);
                break;

//            case R.id.ibScan:
//                if (isGranted) {
//                    startActivityForResult(new Intent(mContext, CaptureActivity.class), Constant.REQUSET_CODE_SCAN);
//                } else {
//                    requestCameraPermission();
//                }
//                break;
            case R.id.ibClearCache:
                dialogProgress.setMessage("清理中...");
                dialogProgress.show();
                handler.sendEmptyMessageDelayed(0, 1000);
                break;
            case R.id.appUpdate:
                dialogProgress.setMessage("检查中...");
                dialogProgress.show();
                handler.sendEmptyMessageDelayed(1, 1000);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUSET_CODE_SCAN && resultCode == RESULT_OK) {
            ToastUtil.toast(data.getStringExtra(Constant.INTENT_KEY_SCAN_RESULT));
        }


    }


    private static final class SHandler extends Handler {
        private WeakReference<MeFragment> weakReference;

        public SHandler(MeFragment meFragment) {
            weakReference = new WeakReference<>(meFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            weakReference.get().dialogProgress.dismiss();
            ToastUtil.toast(msg.what == 0 ? "缓存清理完成!" : "当前已是最新版本");
        }
    }
}
