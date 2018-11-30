package com.miracle.michael.football.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.michael.football.bean.FootballClubBean;

public class FootballCubListAdapter extends RecyclerViewAdapter<FootballClubBean> {
    public FootballCubListAdapter() {
        super(R.layout.item_club_key);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootballClubBean item) {
        helper.setText(R.id.tvIndex, item.getName().replace("比分", ""));
    }
}
