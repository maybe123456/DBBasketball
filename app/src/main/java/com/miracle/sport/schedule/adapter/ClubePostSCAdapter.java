package com.miracle.sport.schedule.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.sport.schedule.bean.post.ClubePostJF;
import com.miracle.sport.schedule.bean.post.ClubePostSC;

//赛程视图
public class ClubePostSCAdapter extends RecyclerViewAdapter<ClubePostSC> {
    Context context;

    public ClubePostSCAdapter(Context context) {
        super(R.layout.item_clube_post_sc);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClubePostSC item) {
//        item.getFixture(); //比赛时间
//        item.getHome_pic(); //主场照片
//        item.getHome(); //主场
//
//        item.getGuest_pic();
//        item.getGuest();
//
//        item.getRotation();

        helper.setText(R.id.item_clube_tv1, item.getFixture());

        GlideApp.with(mContext).load(item.getHome_pic()).into((ImageView) helper.getView(R.id.item_clube_iv1));
        helper.setText(R.id.item_clube_tv2, item.getHome());

        GlideApp.with(mContext).load(item.getGuest_pic()).into((ImageView) helper.getView(R.id.item_clube_iv2));
        helper.setText(R.id.item_clube_tv4, item.getGuest());

        if(item.getRotation().isEmpty() || item.getRotation().length() < 2)
            helper.setText(R.id.item_clube_tv3, "");
        else
            helper.setText(R.id.item_clube_tv3, item.getRotation());
//        GlideApp.with(context).load(item.get);
//        helper.setText(R.id.item_clube_time1, item.getAmount());
//        helper.setText(R.id.tvIndex, item.getClub_name()+"XXXX");
//        helper.setText(R.id.tvIndex, item.getClub_name()+"XXXX");
//        helper.setText(R.id.tvIndex, item.getClub_name()+"XXXX");
//        helper.setText(R.id.tvIndex, item.getClub_name()+"XXXX");
//        helper.setText(R.id.tvIndex, item.getClub_name()+"XXXX");
    }
}
