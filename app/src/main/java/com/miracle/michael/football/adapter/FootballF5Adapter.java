package com.miracle.michael.football.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.michael.football.bean.FootballF5ItemBean;

/**
 * Created by Michael on 2018/10/21 20:15 (星期日)
 */
public class FootballF5Adapter extends RecyclerViewAdapter<FootballF5ItemBean> {
    public FootballF5Adapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootballF5ItemBean item) {
        helper.setImageResource(R.id.ivImg, item.getImg());
        helper.setText(R.id.tvName, item.getName());
    }
}
