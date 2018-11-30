package com.miracle.sport.schedule.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.sport.schedule.bean.ClubeType;

public class ClubeTypeAdapter extends RecyclerViewAdapter<ClubeType> {
    Context context;

    public ClubeTypeAdapter(Context context) {
        super(R.layout.item_club_main_key);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClubeType item) {
//        GlideApp.with(context).load(item.get);
        helper.setText(R.id.tvIndex, item.getName().replace("比分", ""));
        GlideApp.with(mContext).load(item.getPic()).placeholder(R.mipmap.defaule_img).into((ImageView) helper.getView(R.id.img_img));
    }
}
