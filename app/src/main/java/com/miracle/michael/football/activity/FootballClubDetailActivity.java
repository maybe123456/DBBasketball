package com.miracle.michael.football.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.view.zradiogroup.ZRadioButton;
import com.miracle.databinding.ActivityClubDataBinding;
import com.miracle.michael.football.bean.FootballClubBean;
import com.miracle.michael.football.bean.FootballRankTypeBean;
import com.miracle.michael.football.fragment.FootballWebFragment;

import java.util.ArrayList;
import java.util.List;

public class FootballClubDetailActivity extends BaseActivity<ActivityClubDataBinding> {
    private FootballClubBean clubKey;

    private List<FootballWebFragment> fragments = new ArrayList<>();
    private LinearLayout.LayoutParams params;

    @Override
    public int getLayout() {
        return R.layout.activity_club_data;
    }

    @Override
    public void initView() {
        clubKey = (FootballClubBean) getIntent().getSerializableExtra("clubkey");
        setTitle(clubKey.getName().replace("比分", ""));
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reqData();
    }

    private void reqData() {
        ZClient.getService(ZService.class).getFootballRankTypes(clubKey.getId()).enqueue(new ZCallback<ZResponse<List<FootballRankTypeBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<FootballRankTypeBean>> data) {
                for (FootballRankTypeBean dataType : data.getData()) {
                    ZRadioButton zRadioButton = new ZRadioButton(mContext);
                    zRadioButton.setLayoutParams(params);
                    zRadioButton.setText(dataType.getTitle().replace(clubKey.getName().replace("比分", ""), ""));
                    zRadioButton.setIndicatorPosition(ZRadioButton.INDICATOR_BOTTOM);
                    binding.zRadiogroup.addView(zRadioButton);
                    fragments.add(new FootballWebFragment().setType(dataType.getType()));
                }
                binding.zRadiogroup.setupWithContainerAndFragments(R.id.container, fragments.toArray(new FootballWebFragment[0]));
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {

    }
}
