package com.miracle.sport.community.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.SwipeRecyclerBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.adapter.PostCommentAdapter;
import com.miracle.sport.community.bean.PostDetailBean;

/**
 * Created by Michael on 2018/11/3 21:07 (星期六)
 */
public class PostCommentListActivity extends BaseActivity<SwipeRecyclerBinding> {
    private PostCommentAdapter mAdapter;

    private ZPageLoadCallback callback;

    private int id;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler;
    }

    @Override
    public void initView() {
        setTitle("评论列表");
        id = getIntent().getIntExtra(Constant.POST_ID, 0);
        binding.recyclerView.setAdapter(mAdapter = new PostCommentAdapter());
        initCallback();
    }

    private void initCallback() {
        callback = new ZPageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int pageSize) {
                ZClient.getService(SportService.class).getPostCommentList(id, page, pageSize).enqueue(this);
            }
        };
        callback.setNetStatusUI(this);
        callback.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.llLike) {
                    PostDetailBean.CommentBean item = mAdapter.getItem(position);
                    ZClient.getService(SportService.class).likePost(item.getComment_id(), item.getClick() == 1 ? 0 : 1, 0).enqueue(new ZCallback<ZResponse>() {
                        @Override
                        protected void onSuccess(ZResponse zResponse) {
                            ToastUtil.toast(zResponse.getMessage());
                            PostDetailBean.CommentBean item = mAdapter.getItem(position);
                            item.setComment_click_num(item.getClick() == 1 ? item.getComment_click_num() - 1 : item.getComment_click_num() + 1);
                            item.setClick(item.getClick() == 1 ? 0 : 1);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void loadData() {
        callback.onRefresh();
    }

    @Override
    public void onClick(View v) {

    }
}
