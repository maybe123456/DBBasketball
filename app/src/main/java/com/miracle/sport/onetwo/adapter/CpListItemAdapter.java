package com.miracle.sport.onetwo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.sport.onetwo.netbean.CpListItem;

public class CpListItemAdapter extends RecyclerViewAdapter<CpListItem> {
    private Context context;

    public CpListItemAdapter(Context context) {
        super(R.layout.item_bc);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CpListItem item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvTime, item.getTime());
//        helper.setText(R.id.tvauthor, item.getAuthor());
        helper.setText(R.id.tvauthor, context.getString(R.string.app_name));
        if(!TextUtils.isEmpty(item.getThumb()))
            GlideApp.with(context).load(item.getThumb()).placeholder(R.mipmap.defaule_img).into((ImageView) helper.getView(R.id.ivThumb));
    }
}
