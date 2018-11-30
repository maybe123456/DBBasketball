package com.miracle.sport.home.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.GOTO;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.base.view.CommonDialog;
import com.miracle.databinding.SwipeRecyclerCommentBinding;
import com.miracle.michael.common.bean.ArticleCommentBean;
import com.miracle.michael.common.bean.ArticleDetailBean;
import com.miracle.sport.SportService;
import com.miracle.sport.home.adapter.ArticleListAdapter;
import com.wx.goodview.GoodView;

import java.util.List;

import retrofit2.Call;

public class CommentListActivity extends BaseActivity<SwipeRecyclerCommentBinding> {

    private GoodView goodView;

    private ArticleListAdapter mAdapter;
    private ZPageLoadCallback callBack;
    private ArticleDetailBean articleDetailBean;

    private int commentId;
    private String toUser = "";
    private CommonDialog commonDialog;
    private int deletePosition;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler_comment;
    }

    @Override
    public void initView() {
        setTitle("评论列表");
        initDialog();
        articleDetailBean = (ArticleDetailBean) getIntent().getSerializableExtra(Constant.COMMENT_LIST);
        binding.recyclerView.setAdapter(mAdapter = new ArticleListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        binding.tvCommentCount.setText(articleDetailBean.getComment_num()+"");

        goodView = new GoodView(this);

//        showLoadingDialog();
        initCallback();
    }

    private void initCallback() {
        callBack = new ZPageLoadCallback<ZResponse<List<ArticleCommentBean>>>(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
//                ZClient.getService(SportService.class).getMycollections(page,limit).enqueue(callBack);
                RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getCommetList(articleDetailBean.getId(),page,limit),callBack);

            }
        };
        callBack.setDialog(loadingDialog);
        callBack.setNetStatusUI(this);
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);

        if(1 == articleDetailBean.getClick()){
            binding.commentClick.setImageResource(R.mipmap.good_checked_big);
        }else{
            binding.commentClick.setImageResource(R.mipmap.good_big);
        }

        binding.imgSend.setOnClickListener(this);
    }

    private void initDialog() {
        commonDialog = new CommonDialog(this);
        commonDialog.setMessage("您确定删除这条评论吗?");
        commonDialog.setBtListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                reqClickDelete(deletePosition);
                commonDialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    /**对评论点赞*/
    private void reqClick(final int position){
        ZClient.getService(SportService.class).setClickClass(mAdapter.getItem(position).getComment_id(),1,"0").enqueue(new ZCallback<ZResponse<String>>() {
            @Override
            public void onSuccess(ZResponse<String> data) {
                int clickNum = mAdapter.getItem(position).getClick_num();
                mAdapter.getItem(position).setClick_num(clickNum+1);
                mAdapter.getItem(position).setClick(1);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    /**对文章点赞*/
    private void  reqClassClick(){
        ZClient.getService(SportService.class).setClickClass(articleDetailBean.getId(),1,"1").enqueue(new ZCallback<ZResponse<String>>() {
            @Override
            public void onSuccess(ZResponse<String> data) {
                goodView.setImage(getResources().getDrawable(R.mipmap.good_checked_big));
//                goodView.setText("+1");
                goodView.show(binding.commentClick);
                articleDetailBean.setClick(1);
                binding.commentClick.setImageResource(R.mipmap.good_checked_big);
            }
        });
    }

    /**对评论点赞*/
    private void reqClickDelete(final int position){
        ZClient.getService(SportService.class).setClickDelete(articleDetailBean.getId(),mAdapter.getItem(position).getComment_id(),"1").enqueue(new ZCallback<ZResponse<String>>(this) {
            @Override
            public void onSuccess(ZResponse<String> data) {
                mAdapter.remove(position);
                binding.tvCommentCount.setText(mAdapter.getItemCount()+"");
                mAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFinish(Call<ZResponse<String>> call) {
                super.onFinish(call);
                dismissLoadingDialog();
            }
        });
    }


    @Override
    public void initListener() {

        binding.commentClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                    return;
                }

                if(1 == articleDetailBean.getClick()){
                    ToastUtil.toast("已给文章点过赞");
                }else{
                    reqClassClick();
                }
            }
        });

        mAdapter.setOnChildItemListener(new ArticleListAdapter.OnChildItemListener() {
            @Override
            public void onItemClick(int id, String toUserid) {
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                    return;
                }
                CommonUtils.showSoftInput(binding.etCommentContent.getContext(),binding.etCommentContent);
                commentId = articleDetailBean.getId();
                toUser = toUserid;
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.im_click_num:
                        if (CommonUtils.getUser() == null) {
                            GOTO.LoginActivity(mContext);
                            return;
                        }
                        if(0 == mAdapter.getItem(position).getClick()){
                            goodView.setImage(getResources().getDrawable(R.mipmap.good_checked));
//                goodView.setText("+1");
                            goodView.show(view);
                            reqClick(position);
                        }else{
                            ToastUtil.toast("已给评论点过赞");
                        }
                        break;
                    case R.id.im_comment_num:
                    case R.id.tvAuthor:
                        if (CommonUtils.getUser() == null) {
                            GOTO.LoginActivity(mContext);
                            return;
                        }
                        commentId = mAdapter.getItem(position).getComment_id();
//                        final EditText editText = binding.includeSendComment.etCommentContent;
                        CommonUtils.showSoftInput(binding.etCommentContent.getContext(),binding.etCommentContent);
//                        ZClient.getService(SportService.class).sendCommentCommet(mAdapter.getItem(position).getComment_id(),editText.getText().toString(),"","0").enqueue(new ZCallback<ZResponse<String>>() {
//                            @Override
//                            public void onSuccess(ZResponse<String> data) {
//                                ToastUtil.toast("评论成功");
//                                editText.setText("");
//                                CommonUtils.hideSoftInput(editText.getContext(),binding.includeSendComment.etCommentContent);
//                                reqData();
//                            }
//                        });

//                        ToastUtil.toast("评论");
                        break;

                    case R.id.im_delete:
                        deletePosition = position;
                        if(null != commonDialog){
                            commonDialog.show();
                        }
                        break;
                }

            }
        });

    }

    @Override
    public void loadData() {
        mAdapter.setNewData(null);
        callBack.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_good:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                }else{
                    goodView.setTextInfo("+1", Color.parseColor("#f66467"), 14);
                    goodView.show(v);
                }
                break;
            case R.id.img_send:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                    return;
                }
                int sendId = 0;
                String type = "";
                if(0 == commentId){
                    sendId = articleDetailBean.getId();
                    type = "1";
                }else{
                    sendId = commentId;
                    type = "";
                }

                if(TextUtils.isEmpty(binding.etCommentContent.getText())){
                    ToastUtil.toast("内容不能空");
                }else{
                    showLoadingDialog();
//                    ZClient.getService(SportService.class).sendHomeCommet(id , binding.includeSendComment.etCommentContent.getText().toString()).enqueue(new ZCallback<ZResponse<String>>(loadingDialog) {
//                        @Override
//                        public void onSuccess(ZResponse<String> data) {
//                            ToastUtil.toast("评论成功");
//                            binding.includeSendComment.etCommentContent.setText("");
//                            CommonUtils.hideSoftInput(binding.includeSendComment.etCommentContent.getContext(),binding.includeSendComment.etCommentContent);
////                            reqCommentData();
//                        }
//                    });
                    final EditText editText = binding.etCommentContent;
                    ZClient.getService(SportService.class).sendCommentCommet(sendId,editText.getText().toString(),toUser,type).enqueue(new ZCallback<ZResponse<ArticleCommentBean>>() {
                        @Override
                        public void onSuccess(ZResponse<ArticleCommentBean> data) {
                            ToastUtil.toast("评论成功");
                            binding.tvCommentCount.setText(mAdapter.getItemCount()+"");
                            commentId = 0;
                            toUser = "";
                            editText.setText("");
                            CommonUtils.hideSoftInput(editText.getContext(),binding.etCommentContent);
                            loadData();
                        }
                    });
                }

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != commonDialog){
            commonDialog.cancel();
            commonDialog =null;
        }
    }
}
