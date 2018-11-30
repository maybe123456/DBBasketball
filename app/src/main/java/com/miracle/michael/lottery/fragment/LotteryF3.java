package com.miracle.michael.lottery.fragment;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.view.zradiogroup.ZRadioButton;
import com.miracle.databinding.F3LotteryBinding;
import com.miracle.michael.lottery.bean.LotteryBean;

import java.util.ArrayList;
import java.util.List;


public class LotteryF3 extends BaseFragment<F3LotteryBinding> {

    private LinearLayout.LayoutParams params;
    private List<LotteryF3Child> fragments = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.f3_lottery;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 10, 0);
        reqIndex();
    }

    private void reqIndex() {
        ZClient.getService(ZService.class).getLotteryList().enqueue(new ZCallback<ZResponse<List<LotteryBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<LotteryBean>> data) {
                for (LotteryBean footballKey : data.getData()) {
                    ZRadioButton zRadioButton = new ZRadioButton(mContext);
                    zRadioButton.setLayoutParams(params);
                    zRadioButton.setMinimumWidth(150);
                    zRadioButton.setText(footballKey.getName());
                    zRadioButton.setIndicatorPosition(ZRadioButton.INDICATOR_BOTTOM);
                    binding.zRadiogroup2.addView(zRadioButton);
                    fragments.add(new LotteryF3Child().setReqKey2(footballKey));
                }
                binding.zRadiogroup2.setupWithContainerAndFragments(R.id.container2, fragments.toArray(new LotteryF3Child[0]));
            }
        });
    }

    @Override
    public void initListener() {
        binding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null)
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private DrawerLayout drawerLayout;

    public LotteryF3 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
