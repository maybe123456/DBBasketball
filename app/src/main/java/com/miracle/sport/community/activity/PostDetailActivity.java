package com.miracle.sport.community.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.GOTO;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivityPostDetailBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.common.view.CommentBar;
import com.miracle.sport.community.adapter.PostCommentAdapter;
import com.miracle.sport.community.adapter.PostDetailImagesAdapter;
import com.miracle.sport.community.bean.PostDetailBean;

import java.util.List;

/**
 * Created by Michael on 2018/10/30 21:22 (星期二)
 */
public class PostDetailActivity extends BaseActivity<ActivityPostDetailBinding> {
    private int id;

    private PostDetailImagesAdapter mAdater;

    private PostCommentAdapter pAdapter;
    private PostDetailBean mData;

    @Override
    public int getLayout() {
        return R.layout.activity_post_detail;
    }

    @Override
    public void initView() {
        setTitle("详情");
        id = getIntent().getIntExtra(Constant.POST_ID, 0);
        binding.recyclerView.setAdapter(mAdater = new PostDetailImagesAdapter());
        binding.rvComment.setAdapter(pAdapter = new PostCommentAdapter());
    }

    private void setData(PostDetailBean data) {
        binding.tvCircle.setText(data.getName());
        binding.tvTitle.setText(data.getTitle());
        binding.tvContent.setText(data.getContent());
        binding.commentBar.setCommentNum(data.getComment_num());
        binding.commentBar.setLikeNum(data.getClick_num());
        binding.commentBar.setLike(data.getClick() == 1);
        List<String> thumb = data.getThumb();
        if (thumb != null && !thumb.isEmpty()) {
            mAdater.setNewData(thumb);
        }
        pAdapter.setNewData(data.getComment());

        binding.tvCheckMore.setVisibility(data.getComment_num() > 10 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initListener() {
        binding.tvCheckMore.setOnClickListener(this);
        binding.commentBar.setCBListener(new CommentBar.CBListener() {
            @Override
            public void send(String content) {
                ZClient.getService(SportService.class).sendPostComment(id, 0, 1, content).enqueue(new ZCallback<ZResponse>() {
                    @Override
                    public void onSuccess(ZResponse data) {
                        ToastUtil.toast(data.getMessage());
                        binding.commentBar.clearContent();
                        reqData();
                        CommonUtils.hideSoftInput(mContext, binding.getRoot());
                    }
                });
            }

            @Override
            public void onLikeClick() {
                if (mData == null)
                    return;
                ZClient.getService(SportService.class).likePost(id, mData.getClick() == 1 ? 0 : 1, 1).enqueue(new ZCallback<ZResponse>() {
                    @Override
                    public void onSuccess(ZResponse data) {
                        ToastUtil.toast(data.getMessage());
                        reqData();
                    }
                });
            }

            @Override
            public void onCommentClick() {
                GOTO.PostCommentListActivity(mContext, id);
            }
        });
        pAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.llLike) {
                    PostDetailBean.CommentBean item = pAdapter.getItem(position);
                    ZClient.getService(SportService.class).likePost(item.getComment_id(), item.getClick() == 1 ? 0 : 1, 0).enqueue(likeCallback);
                }
            }
        });
    }

    @Override
    public void loadData() {
    }

    private void reqData() {
        ZClient.getService(SportService.class).getPostDetail(id).enqueue(new ZCallback<ZResponse<PostDetailBean>>(this) {
            @Override
            public void onSuccess(ZResponse<PostDetailBean> data) {
                setData(mData = data.getData());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reqData();
    }

    private ZCallback<ZResponse> likeCallback = new ZCallback<ZResponse>() {
        @Override
        public void onSuccess(ZResponse data) {
            ToastUtil.toast(data.getMessage());
            reqData();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCheckMore:
                GOTO.PostCommentListActivity(this, id);
                break;
        }
    }
}
