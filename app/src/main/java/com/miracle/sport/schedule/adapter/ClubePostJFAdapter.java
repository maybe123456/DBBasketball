package com.miracle.sport.schedule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.sport.schedule.bean.post.ClubePostJF;

//赛程视图
public class ClubePostJFAdapter extends RecyclerViewAdapter<ClubePostJF> {
    Context context;

    public ClubePostJFAdapter(Context context) {
        super(R.layout.item_clube_post_jf);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClubePostJF item) {
//        GlideApp.with(context).load(item.get);
//        helper.setText(R.id.item_clube_time1, item.getAmount());
//        GlideApp.with(mContext).load(item.getPlayer_pic()).into((ImageView) helper.getView(R.id.item_clube_iv1));
//        GlideApp.with(mContext).load(item.getClub_pic()).into((ImageView) helper.getView(R.id.item_clube_iv2));

//        helper.setText(R.id.item_clube_tv2, "getLeague:"+item.getLeague());
//        helper.setText(R.id.item_clube_tv3, "getLeague:"+item.getClub_name());
//        helper.setText(R.id.item_clube_tv4, "getLeague:"+item.getLeague());
//        helper.setText(R.id.item_clube_tv5, "getLeague:"+item.getLeague());

        if(helper.getAdapterPosition() <= 3){
            switch (helper.getAdapterPosition()){
                case 1:
                    ((TextView)helper.getView(R.id.item_clube_tv1)).setTextColor(Color.RED);
                    break;
                case 2:
                case 3:
                    ((TextView)helper.getView(R.id.item_clube_tv1)).setTextColor(Color.BLUE);
                    break;
                default:
                    break;
            }
            helper.getView(R.id.item_clube_post_jf_ll).setBackgroundColor(Color.parseColor("#ddddff"));
        }else{
            ((TextView)helper.getView(R.id.item_clube_tv1)).setTextColor(Color.BLACK);
            helper.getView(R.id.item_clube_post_jf_ll).setBackgroundColor(Color.WHITE);
        }


        helper.setText(R.id.item_clube_tv1, ""+item.getRanking());
//        item.getRanking();

        GlideApp.with(mContext).load(item.getClub_pic()).into((ImageView) helper.getView(R.id.item_clube_iv1));
        helper.setText(R.id.item_clube_tv2, item.getClub_name());
//        item.getClub_pic();
//        item.getClub_name();


        helper.setText(R.id.item_clube_tv3, ""+item.getFields());
        helper.setText(R.id.item_clube_tv4, ""+item.getWin());
        helper.setText(R.id.item_clube_tv5, ""+item.getDraw());
        helper.setText(R.id.item_clube_tv6, ""+item.getLoss());

        helper.setText(R.id.item_clube_tv7, ""+item.getGoal());
        helper.setText(R.id.item_clube_tv8, ""+item.getFumble());

        helper.setText(R.id.item_clube_tv9, ""+item.getGoal_diff());
        helper.setText(R.id.item_clube_tv10, ""+item.getIntegral());

//        item.getFields();
//        item.getWin();
//        item.getDraw();
//        item.getLoss();
//
//        item.getGoal();//进球
//        item.getFumble();//失球
//
//        item.getGoal_diff();//净胜球
//        item.getIntegral();//积分


//        helper.setText(R.id.item_clube_tv6, ""+item.getAmount());
//        item.getAmount();

//        item.getLeague();
//        item.getList_type();

    }
}
