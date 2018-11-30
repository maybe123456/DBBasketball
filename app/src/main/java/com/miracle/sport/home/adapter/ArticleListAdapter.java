package com.miracle.sport.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.TimeUtils;
import com.miracle.michael.common.bean.ArticleCommentBean;
import com.miracle.michael.common.bean.ArticleDetailBean;
import com.miracle.michael.common.bean.CommentChildBean;
import com.miracle.sport.home.bean.CommentItem;
import com.miracle.sport.home.bean.HomeCommentBean;
import com.miracle.sport.home.view.ChildListView;
import com.miracle.sport.home.view.CommentListView;

import java.text.ParseException;

public class ArticleListAdapter extends RecyclerViewAdapter<ArticleCommentBean> {
    private Context context;
    // 回调监听
    private CallBackListener mCallBackListener;
    private OnChildItemListener onChildItemListener;

    public OnChildItemListener getOnChildItemListener() {
        return onChildItemListener;
    }

    public void setOnChildItemListener(OnChildItemListener onChildItemListener) {
        this.onChildItemListener = onChildItemListener;
    }

    public ArticleListAdapter(Context context) {
        super(R.layout.item_home_comment_article);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ArticleCommentBean item) {
        helper.setText(R.id.tvTitle, item.getNickname());
        try {
            Long longTime = TimeUtils.stringToLong(item.getAdd_time(),"yyyy-MM-dd HH:mm:ss");
            helper.setText(R.id.tvTime, TimeUtils.getShortTime(longTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(0 == item.getChild_count()){
            helper.setGone(R.id.digCommentBody,false);
        }else{
            helper.setGone(R.id.digCommentBody,true);
            ((ChildListView)helper.getView(R.id.commentList)).setDatas(item.getChild());
        }

        if(CommonUtils.getUserId().equals(item.getUser_id()+"")){
            helper.setGone(R.id.im_delete,true);
        }else{
            helper.setGone(R.id.im_delete,false);
        }


        helper.setText(R.id.tvAuthor,item.getContent());
        helper.setText(R.id.im_click_num,item.getClick_num()+"");
        helper.setText(R.id.im_comment_num,item.getChild_count()+"");
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
         helper.addOnClickListener(R.id.im_delete);
         helper.addOnClickListener(R.id.tvAuthor);
         helper.addOnClickListener(R.id.im_comment_num);

        ((ChildListView)helper.getView(R.id.commentList)).setOnChildItemListener(new ChildListView.OnChildItemListener() {
            @Override
            public void onItemClick(String id) {
                if(null != onChildItemListener){
                    onChildItemListener.onItemClick(item.getComment_id(),id);
                }
            }
        });


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
        void callBackImg(int position, int goodsPosition);
    }

    public static interface OnChildItemListener{
        public void onItemClick(int id,String toUserid);
    }


}
