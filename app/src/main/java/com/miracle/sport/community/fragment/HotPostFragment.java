package com.miracle.sport.community.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.GOTO;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.FragmentHotpostBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.activity.CommunityActivity;
import com.miracle.sport.community.adapter.PostListAdapter;
import com.miracle.sport.community.bean.PostBean;

import java.util.List;

/**
 * Created by Michael on 2018/10/29 14:07 (星期一)
 */
public class HotPostFragment extends BaseFragment<FragmentHotpostBinding> {


    private PostListAdapter mAdapter;
    private ZPageLoadCallback callBack;

    private Integer circleId;

    private boolean isCommunityActivity;

    public HotPostFragment setParent(boolean isCommunityActivity) {
        this.isCommunityActivity = isCommunityActivity;
        return this;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_hotpost;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new PostListAdapter(mContext));
        initCallback();
    }

    private void initCallback() {
//        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
//            @Override
//            public void requestAction(int page, int pageSize) {
//                ZClient.getService(SportService.class).getPostList("rm", circleId, page, pageSize).enqueue(callBack);
//            }
//        };
        callBack = new ZPageLoadCallback<ZResponse<List<PostBean>>>(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int pageSize) {
                callBack.setCachKey("HotPostFragment" + circleId + page);
                if (isCommunityActivity) {
                    ZClient.getService(SportService.class).getPostList("rm", circleId, page, pageSize).enqueue(this);
                } else {
                    RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getPostList("rm", circleId, page, pageSize), callBack);
                }
            }

        };
        if (isCommunityActivity) {
            callBack.setSwipeRefreshLayout(((CommunityActivity) getActivity()).getSwipeRefreshLayout());
            callBack.setNetStatusUI((CommunityActivity) getActivity());
        } else {
            callBack.setSwipeRefreshLayout(((CommunityFragment) getParentFragment()).getSwipeRefreshLayout());
            callBack.setNetStatusUI(this);

        }
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GOTO.PostDetailActivity(getActivity(), mAdapter.getItem(position).getId());
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final PostBean item = mAdapter.getItem(position);
                ZClient.getService(SportService.class).likePost(item.getId(), item.getClick() == 1 ? 0 : 1, 1).enqueue(new ZCallback<ZResponse>() {
                    @Override
                    public void onSuccess(ZResponse data) {
                        ToastUtil.toast(data.getMessage());
                        PostBean postBean = mAdapter.getItem(position);
                        postBean.setClick(postBean.getClick() == 1 ? 0 : 1);
                        postBean.setClick_num(postBean.getClick() == 1 ? postBean.getClick_num() + 1 : postBean.getClick_num() - 1);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void loadData() {
        callBack.onRefresh();
    }

    public void setCircleId(int id) {
        circleId = id;
    }

    public void switchCircleId(int id) {
        circleId = id;
        mAdapter.setNewData(null);
        loadData();
    }
}
