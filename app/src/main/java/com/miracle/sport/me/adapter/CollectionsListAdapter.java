package com.miracle.sport.me.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.sport.home.bean.Football;

public class CollectionsListAdapter extends RecyclerViewAdapter<Football> {
    private Context context;

    public CollectionsListAdapter(Context context) {
        super(R.layout.item_collections);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Football item) {
        helper.setText(R.id.tvTitle, item.getTitle().replace("微信",""));
//        try {
//            Long longTime = TimeUtils.stringToLong(item.getTime(),"yyyy-MM-dd HH:mm:ss");
//            helper.setText(R.id.tvTime, TimeUtils.getShortTime(longTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        helper.setText(R.id.tvTime, item.getAdd_time());
        helper.setText(R.id.tvAuthor,CommonUtils.getAppName(context));
        helper.setText(R.id.im_comment_num ,item.getComment_num()+"");
        helper.setText(R.id.im_click_num ,item.getClick_num()+"");
//        Glide.with(context)
//                .load(item.getThumb())
//                .into((ImageView) helper.getView(R.id.iv));
        String thumb = item.getThumb();
        if(!TextUtils.isEmpty(thumb)){
            GlideApp.with(context).load(thumb)
                    .placeholder(R.mipmap.defaule_img)
                    .error(R.mipmap.empty)
                    .into((ImageView) helper.getView(R.id.iv1_1));
            helper.setGone(R.id.iv1_1,true);
        }else{
            helper.setGone(R.id.iv1_1,false);

        }


    }
}
