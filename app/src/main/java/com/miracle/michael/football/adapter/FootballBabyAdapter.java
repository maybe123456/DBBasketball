package com.miracle.michael.football.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.michael.football.bean.FootballBabyBean;

/**
 * Created by Michael on 2018/10/25 21:37 (星期四)
 */
public class FootballBabyAdapter extends RecyclerViewAdapter<FootballBabyBean> {
    private Context context;

    public FootballBabyAdapter(Context context) {
        super(R.layout.item_footballbaby);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FootballBabyBean item) {
        GlideApp.with(context).load(item.getThumb())
                .placeholder(R.mipmap.defaule_img)
                .error(R.mipmap.empty)
                .into((ImageView) helper.getView(R.id.ivBaby));
    }
}
