package com.miracle.michael.chess.fragment;

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
import com.miracle.databinding.F2ChessBinding;
import com.miracle.michael.chess.bean.ChessTypeBean;

import java.util.ArrayList;
import java.util.List;


public class ChessF2 extends BaseFragment<F2ChessBinding> {

    private LinearLayout.LayoutParams params;
    private List<ChessF2Child> fragments = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.f2_chess;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(0, 0, 10, 0);
        reqIndex();
    }

    private void reqIndex() {
        ZClient.getService(ZService.class).getQipaiTypes().enqueue(new ZCallback<ZResponse<List<ChessTypeBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<ChessTypeBean>> data) {
                for (int i = 0; i < data.getData().size() - 1; i++) {
                    ChessTypeBean qipaiType = data.getData().get(i);
                    ZRadioButton zRadioButton = new ZRadioButton(mContext);
                    zRadioButton.setLayoutParams(params);
                    zRadioButton.setfocusZoom(true);
                    zRadioButton.setText(qipaiType.getName());
                    zRadioButton.setIndicatorPosition(ZRadioButton.INDICATOR_BOTTOM);
                    binding.zRadiogroup2.addView(zRadioButton);
                    fragments.add(new ChessF2Child().setReqKey(qipaiType));
                }
                binding.zRadiogroup2.setupWithContainerAndFragments(R.id.container2, fragments.toArray(new ChessF2Child[0]));
            }
        });
    }

    @Override
    public void initListener() {
        binding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null)
                    drawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private DrawerLayout drawerLayout;

    public ChessF2 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
