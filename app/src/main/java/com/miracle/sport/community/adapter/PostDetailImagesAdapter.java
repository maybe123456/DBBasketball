package com.miracle.sport.community.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.ContextHolder;

/**
 * Created by Michael on 2018/10/30 21:34 (星期二)
 */
public class PostDetailImagesAdapter extends RecyclerViewAdapter<String> {

    public PostDetailImagesAdapter() {
        super(R.layout.item_post_detailimg);
    }

    @Override
    protected void convert(BaseViewHolder helper, String url) {
        GlideApp.with(ContextHolder.getContext()).load(url)
                .placeholder(R.mipmap.defaule_img)
                .error(R.mipmap.empty)
                .into((ImageView) helper.getView(R.id.iv));
    }
}
