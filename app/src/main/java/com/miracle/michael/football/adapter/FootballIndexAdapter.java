package com.miracle.michael.football.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;

import com.miracle.R;
import com.miracle.base.adapter.AbsListViewAdapter;
import com.miracle.michael.football.bean.FootballMatchIndexBean;


/**
 * Created by Administrator on 2018/3/5.
 */

public class FootballIndexAdapter extends AbsListViewAdapter<FootballMatchIndexBean, FootballIndexAdapter.ViewHolder> {

    private int selectedPosition;

    public FootballIndexAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.item_index;
    }

    @Override
    protected void bindView(int position, FootballMatchIndexBean object, ViewHolder viewHolder) {
        viewHolder.tvIndex.setText(object.getName());
        viewHolder.tvIndex.setChecked(selectedPosition == position);
    }

    @Override
    protected ViewHolder loadHolder(View v) {
        return new ViewHolder(v);
    }

    public void setSelectPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    final class ViewHolder {

        CheckedTextView tvIndex;

        public ViewHolder(View v) {
            tvIndex = v.findViewById(R.id.tvIndex);
        }
    }
}
