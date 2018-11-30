package com.miracle.sport.schedule.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.sport.schedule.bean.post.ClubePostSS;

//赛程视图
public class ClubePostSSAdapter extends RecyclerViewAdapter<ClubePostSS> {
    Context context;

    public ClubePostSSAdapter(Context context) {
        super(R.layout.item_clube_post_ss);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClubePostSS item) {
        helper.setText(R.id.item_clube_time1, ""+item.getRanking());
//        item.getRanking();

        GlideApp.with(mContext).load(item.getPlayer_pic()).into((ImageView) helper.getView(R.id.item_clube_iv1));
        helper.setText(R.id.item_clube_tv2, item.getPlayer_name());
//        item.getPlayer_pic();
//        item.getPlayer_name();

        GlideApp.with(mContext).load(item.getClub_pic()).into((ImageView) helper.getView(R.id.item_clube_iv2));
        helper.setText(R.id.item_clube_tv3, item.getClub_name());
//        item.getClub_pic();
//        item.getClub_name();

        helper.setText(R.id.item_clube_tv6, ""+item.getAmount());
//        item.getAmount();

//        item.getLeague();
//        item.getList_type();
    }
}
