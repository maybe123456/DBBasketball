package com.miracle.michael.doudizhu.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZService;
import com.miracle.databinding.F2ChildFootballBinding;
import com.miracle.michael.doudizhu.activity.DDZNewsDetailActivity;
import com.miracle.michael.doudizhu.adapter.DDZNewsListAdapter;

/**
 * Created by Administrator on 2018/3/5.
 */

public class DDZF2Child extends BaseFragment<F2ChildFootballBinding> {


    private DDZNewsListAdapter mAdapter;
    private PageLoadCallback callBack;

    private int reqKey=12;

    private String yc = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538805900&di=9369b240439a4e1e34d283a644935eb3&imgtype=jpg&er=1&src=http%3A%2F%2F06.imgmini.eastday.com%2Fmobile%2F20170926%2F20170926001602_d41d8cd98f00b204e9800998ecf8427e_1.jpeg";
    private String zc = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538211525579&di=918acd40c2eeefa906f82c984af04ff1&imgtype=0&src=http%3A%2F%2Fres.caibb.com%2Fcaibb%2Fupload%2Fuploadimages%2F20170210142630149.jpg";
    private String xj = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538211562005&di=dfdb9bde189714516bc46b191df2b6e9&imgtype=0&src=http%3A%2F%2Fimgsports.eastday.com%2Fsports%2Fimg%2F201710310610034614.jpeg";
    private String yj = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4215450442,1745086655&fm=26&gp=0.jpg";
    private String dj = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538212405328&di=bd5991749f68dd7155b497bbb546efb8&imgtype=0&src=http%3A%2F%2Fs1.sinaimg.cn%2Fmw690%2F004lDxWVgy6KoHekfLi90%26690";
    private String og = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538806532&di=44516b2e13d0b494d94490a147c27037&imgtype=jpg&er=1&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D994338d65dafa40f3c93c6d99e542f79%2Fd058ccbf6c81800af36b06eab63533fa838b474c.jpg";

    @Override
    public int getLayout() {
        return R.layout.f2_child_football;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new DDZNewsListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        GlideApp.with(mContext).load(yc)
                .placeholder(R.mipmap.defaule_img)
                .error(R.mipmap.empty)
                .into(binding.ivPoster);
        binding.tvCategoryTitle.setText("英超");
        initCallback();
    }

    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(ZService.class).getDDZNewsList(reqKey, page, limit).enqueue(callBack);
            }
        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, DDZNewsDetailActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    public void setReqKey(int reqKey) {
        this.reqKey = reqKey;
        String logoUrl = null;
        String title = null;
        switch (reqKey) {
            case 12:
                logoUrl = yc;
                title = "斗地主实战技巧";
                break;
            case 13:
                logoUrl = zc;
                title = "斗地主攻略";
                break;
            case 14:
                logoUrl = xj;
                title = "斗地主技巧";
                break;
            case 15:
                logoUrl = yj;
                title = "残局专家模式";
                break;
            case 16:
                logoUrl = dj;
                title = "残局普通模式";
                break;
            case 17:
                logoUrl = og;
                title = "残局困难模式";
                break;
        }
        GlideApp.with(mContext).load(logoUrl)
                .placeholder(R.mipmap.defaule_img)
                .error(R.mipmap.empty)
                .into(binding.ivPoster);
        binding.tvCategoryTitle.setText(title);
        callBack.onRefresh();
    }
}
