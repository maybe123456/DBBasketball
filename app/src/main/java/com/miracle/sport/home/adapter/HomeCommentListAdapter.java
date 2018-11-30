package com.miracle.sport.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.TimeUtils;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.home.bean.HomeCommentBean;

import java.text.ParseException;

public class HomeCommentListAdapter extends RecyclerViewAdapter<HomeCommentBean> {
    private Context context;
    // 回调监听
    private CallBackListener mCallBackListener;

    public HomeCommentListAdapter(Context context) {
        super(R.layout.item_home_comment);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCommentBean item) {
        helper.setText(R.id.tvTitle, item.getNickname());
        try {
            Long longTime = TimeUtils.stringToLong(item.getAdd_time(),"yyyy-MM-dd HH:mm:ss");
            helper.setText(R.id.tvTime, TimeUtils.getShortTime(longTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        helper.setText(R.id.tvAuthor,item.getContent());
        helper.setText(R.id.im_click_num,item.getClick_num()+"");
        if(1 == item.getClick()){
            Drawable img = mContext.getResources().getDrawable(R.mipmap.good_checked);
// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            ((TextView)helper.getView(R.id.im_click_num)).setCompoundDrawables(img, null, null, null); //设置左图标
        }else{
            Drawable img = mContext.getResources().getDrawable(R.mipmap.good);
// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            ((TextView)helper.getView(R.id.im_click_num)).setCompoundDrawables(img, null, null, null); //设置左图标
        }
//        Glide.with(context)
//                .load(item.getThumb())
//                .into((ImageView) helper.getView(R.id.iv));
        String thumb = item.getImg();
             GlideApp.with(context).load(thumb)
                     .placeholder(R.mipmap.defaule_img)
                     .error(R.mipmap.empty)
                     .into((ImageView) helper.getView(R.id.iv));
         helper.addOnClickListener(R.id.im_click_num);
    }

    /**
     * 设置回调监听
     *
     * @param mCallBackListener 回调监听
     * @return
     * @author leibing
     */
    public void setCallBackListener(CallBackListener mCallBackListener) {
        this.mCallBackListener = mCallBackListener;
    }

    /**
     * @interfaceName: CallBackListener
     * @interfaceDescription: 回调监听
     * @author: leibing
     */
    public interface CallBackListener {
        void callBackImg(int position,int goodsPosition);
    }


}
