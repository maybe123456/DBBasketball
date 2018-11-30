package com.miracle.sport.community.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.sport.community.bean.CircleBean;

/**
 * Created by Michael on 2018/10/30 16:27 (星期二)
 */
public class CircleChildAdapter extends RecyclerViewAdapter<CircleBean.ChildBean> {

    private boolean isFromPublishPostActivity;
    private String selectOn, selectOff;

    public CircleChildAdapter() {
        super(R.layout.item_circle);
        selectOn = CommonUtils.getString(R.string.icon_select_on);
        selectOff = CommonUtils.getString(R.string.icon_select_off);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleBean.ChildBean item) {
        helper.addOnClickListener(R.id.tvCheck);
        helper.setText(R.id.tvName, item.getName());
        if (isFromPublishPostActivity) {
            helper.setGone(R.id.tvCheck, false);
        } else {
            helper.setGone(R.id.tvCheck, true);
            helper.setText(R.id.tvCheck, item.getFollow() == 1 ? selectOn : selectOff);
        }
        GlideApp.with(mContext).load(item.getPic()).placeholder(R.mipmap.defaule_img).into((ImageView) helper.getView(R.id.ivCircleLogo));
    }

    public void setBoolean(boolean isFromPublishPostActivity) {
        this.isFromPublishPostActivity = isFromPublishPostActivity;
    }
}
