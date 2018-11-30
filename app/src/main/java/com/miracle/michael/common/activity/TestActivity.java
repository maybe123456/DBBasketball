package com.miracle.michael.common.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivityTestBinding;
import com.miracle.michael.common.bean.LabelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2018/10/26 15:31 (星期五)
 */
public class TestActivity extends BaseActivity<ActivityTestBinding> {

    private List<LabelBean> data1 = new ArrayList<>();
    private List<LabelBean> data2 = new ArrayList<>();

    private LabelAdapter adapter1;
    private LabelAdapter adapter2;

    @Override
    public int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        binding.recyclerView1.setAdapter(adapter1 = new LabelAdapter());
        binding.recyclerView2.setAdapter(adapter2 = new LabelAdapter());

        initData();

    }

    private void initData() {
        data2.add(new LabelBean("AAAAA"));
        data2.add(new LabelBean("BBBBB"));
        data2.add(new LabelBean("CCCCC"));
        data2.add(new LabelBean("DDDDD"));
        data2.add(new LabelBean("EEEEE"));
        data2.add(new LabelBean("FFFFF"));
        data2.add(new LabelBean("GGGGG"));
        data2.add(new LabelBean("HHHHH"));
        data2.add(new LabelBean("IIIII"));
        data2.add(new LabelBean("JJJJJ"));
        data2.add(new LabelBean("KKKKK"));
        data2.add(new LabelBean("LLLLL"));
        data2.add(new LabelBean("MMMMM"));
        data2.add(new LabelBean("NNNNN"));
        adapter2.setNewData(data2);

    }

    private final class LabelAdapter extends RecyclerViewAdapter<LabelBean> {

        public LabelAdapter() {
            super(R.layout.item_label);
        }

        @Override
        protected void convert(BaseViewHolder helper, LabelBean item) {
            helper.addOnClickListener(R.id.tvClose);
            helper.setText(R.id.tvLabel, item.getText());
            helper.setGone(R.id.tvClose, item.isShow());
        }

        public void showX() {
            for (LabelBean labelBean : getData()) {
                labelBean.setShow(true);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void initListener() {

        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                adapter1.addData(adapter2.getItem(position));
            }
        });
        adapter1.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                adapter1.showX();
                return false;
            }
        });

        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.toast(adapter1.getItem(position).getText());
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {

    }
}
