package com.miracle.sport.me.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.network.GlideApp;
import com.miracle.sport.community.bean.MyCircleBean;

/**
 * Created by Michael on 2018/10/27 15:44 (星期六)
 */
public class MyCircleAdapter extends BaseQuickAdapter<MyCircleBean, BaseViewHolder> {

    public MyCircleAdapter() {
        super(R.layout.item_circle2);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCircleBean bean) {
        helper.setText(R.id.tvName, bean.getName());
        GlideApp.with(mContext).load(bean.getPic()).placeholder(R.mipmap.defaule_img).into((ImageView) helper.getView(R.id.ivCircleLogo));

    }
}
