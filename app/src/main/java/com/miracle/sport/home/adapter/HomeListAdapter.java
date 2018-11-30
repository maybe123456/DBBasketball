package com.miracle.sport.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.TimeUtils;
import com.miracle.sport.home.bean.Football;

import java.text.ParseException;

public class HomeListAdapter extends RecyclerViewAdapter<Football> {
    private Context context;

    public HomeListAdapter(Context context) {
        super(R.layout.item_home);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Football item) {
        helper.setText(R.id.tvTitle, item.getTitle());
//        try {
//            Long longTime = TimeUtils.stringToLong(item.getTime(),"yyyy-MM-dd HH:mm:ss");
//            helper.setText(R.id.tvTime, TimeUtils.getShortTime(longTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        helper.setText(R.id.tvTime, "");
//        ((TextView)helper.getView(R.id.tvTime)).getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
//        setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        helper.setText(R.id.tvAuthor,"");
        helper.setText(R.id.im_comment_num ,item.getComment_num()+"");
        helper.setText(R.id.im_click_num ,item.getClick_num()+"");
//        Glide.with(context)
//                .load(item.getThumb())
//                .into((ImageView) helper.getView(R.id.iv));
        String thumb = item.getThumb();
        if(null == item.getImages()){
            if(TextUtils.isEmpty(thumb)){

                helper.setGone(R.id.iv1_1, false);
            }else{
                GlideApp.with(context).load(thumb)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv1_1));
                helper.setGone(R.id.iv1_1, true);

            }
            helper.setGone(R.id.iv1_2, false);
            helper.setGone(R.id.iv2_2, false);
            helper.setGone(R.id.iv1, false);
            helper.setGone(R.id.iv2, false);
            helper.setGone(R.id.iv3, false);
        }else {
            if (1 == item.getImages().length) {
                String urlLoad = "";
                if (!TextUtils.isEmpty(thumb)) {
                    urlLoad = thumb;
                } else {
                    urlLoad = item.getImages()[0];
                }
                GlideApp.with(context).load(urlLoad)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv1_1));
                helper.setGone(R.id.iv1_1, true);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            } else if (2 == item.getImages().length) {
                String urlLoad = "";
                if (!TextUtils.isEmpty(thumb)) {
                    urlLoad = thumb;
                } else {
                    urlLoad = item.getImages()[0];
                }
                GlideApp.with(context).load(urlLoad)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv1_2));
                GlideApp.with(context).load(item.getImages()[1])
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv2_2));
                helper.setGone(R.id.iv1_1, false);
                helper.setGone(R.id.iv1_2, true);
                helper.setGone(R.id.iv2_2, true);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            } else if (3 <= item.getImages().length) {

                String urlLoad = "";
                if (!TextUtils.isEmpty(thumb)) {
                    urlLoad = thumb;
                } else {
                    urlLoad = item.getImages()[0];
                }

                GlideApp.with(context).load(urlLoad)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv1));
                GlideApp.with(context).load(item.getImages()[1])
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv2));
                GlideApp.with(context).load(item.getImages()[2])
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv3));
                helper.setGone(R.id.iv1_1, false);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, true);
                helper.setGone(R.id.iv2, true);
                helper.setGone(R.id.iv3, true);
            } else if (0 == item.getImages().length && !TextUtils.isEmpty(item.getThumb())) {
                GlideApp.with(context).load(item.getThumb())
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into((ImageView) helper.getView(R.id.iv1_1));
                helper.setGone(R.id.iv1_1, true);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            } else {
                helper.setGone(R.id.iv1_1, false);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            }
        }

    }
}
