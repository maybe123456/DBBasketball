package com.miracle.michael.doudizhu.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;

import com.miracle.R;
import com.miracle.base.adapter.AbsListViewAdapter;
import com.miracle.michael.doudizhu.bean.DDZIndexBean;


/**
 * Created by Administrator on 2018/3/5.
 */

public class DDZIndexAdapter extends AbsListViewAdapter<DDZIndexBean, DDZIndexAdapter.ViewHolder> {

    private int selectedPosition;

    public DDZIndexAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.item_index;
    }

    @Override
    protected void bindView(int position, DDZIndexBean object, ViewHolder viewHolder) {
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
