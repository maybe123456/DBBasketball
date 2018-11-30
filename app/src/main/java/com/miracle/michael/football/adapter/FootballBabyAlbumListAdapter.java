package com.miracle.michael.football.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.michael.football.bean.FootballBabyAlbumListBean;

/**
 * Created by Michael on 2018/10/25 19:52 (星期四)
 */
public class FootballBabyAlbumListAdapter extends RecyclerViewAdapter<FootballBabyAlbumListBean> {
    private Context context;

    public FootballBabyAlbumListAdapter(Context context) {
        super(R.layout.item_football_album);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FootballBabyAlbumListBean item) {
        helper.setText(R.id.tvAlbumName, item.getTitle());
        String thumb = item.getThumb();
        if (TextUtils.isEmpty(thumb)) {
            helper.setGone(R.id.ivAlbum, false);
//            helper.setImageResource(R.id.iv, R.mipmap.default_image);
        } else {
            GlideApp.with(context).load(thumb)
                    .placeholder(R.mipmap.defaule_img)
                    .error(R.mipmap.empty)
                    .into((ImageView) helper.getView(R.id.ivAlbum));
            helper.setGone(R.id.ivAlbum, true);
        }
    }
}
