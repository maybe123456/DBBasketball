package com.miracle.sport.community.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ContextHolder;
import com.miracle.sport.community.bean.PostDetailBean;

/**
 * Created by Michael on 2018/10/30 22:16 (星期二)
 */
public class PostCommentAdapter extends RecyclerViewAdapter<PostDetailBean.CommentBean> {

    private int colorLike;
    private int colorDislike;

    public PostCommentAdapter() {
        super(R.layout.item_post_comment);
        colorLike = CommonUtils.getColor(R.color.red_ball);
        colorDislike = CommonUtils.getColor(R.color.main_color_grey);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostDetailBean.CommentBean item) {
        helper.addOnClickListener(R.id.llLike);
        helper.setText(R.id.tvName, item.getNickname());
        helper.setText(R.id.tvContent, item.getContent());
        helper.setText(R.id.tvTime, item.getAdd_time());
        helper.setText(R.id.tvLikeCount, item.getComment_click_num() + "");
        helper.setTextColor(R.id.tvLike, item.getClick() == 1 ? colorLike : colorDislike);
        helper.setTextColor(R.id.tvLikeCount, item.getClick() == 1 ? colorLike : colorDislike);

        GlideApp.with(ContextHolder.getContext())
                .load(item.getImg())
                .placeholder(R.mipmap.default_head)
                .into((ImageView) helper.getView(R.id.ivHead));
    }
}
