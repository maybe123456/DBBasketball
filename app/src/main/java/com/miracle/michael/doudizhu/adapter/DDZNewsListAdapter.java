package com.miracle.michael.doudizhu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.michael.common.bean.NewsBean;

public class DDZNewsListAdapter extends RecyclerViewAdapter<NewsBean> {
    private Context context;

    public DDZNewsListAdapter(Context context) {
        super(R.layout.item_football);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvTime, item.getTime());
//        Glide.with(context)
//                .load(item.getThumb())
//                .into((ImageView) helper.getView(R.id.iv));
        String thumb = item.getThumb();
        if (TextUtils.isEmpty(thumb)) {
            helper.setGone(R.id.iv, false);
//            helper.setImageResource(R.id.iv, R.mipmap.default_image);
        } else {
            GlideApp.with(context).load(thumb)
                    .placeholder(R.mipmap.defaule_img)
                    .error(R.mipmap.empty)
                    .into((ImageView) helper.getView(R.id.iv));
            helper.setGone(R.id.iv, true);
        }
    }
}
