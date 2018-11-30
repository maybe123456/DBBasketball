package com.miracle.sport.common.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.CommentbarBinding;

import java.text.MessageFormat;

/**
 * Created by Michael on 2018/10/30 22:45 (星期二)
 */
public class CommentBar extends LinearLayout implements View.OnClickListener {

    private CommentbarBinding binding;
    private int colorLike;
    private int colorDislike;

    public CommentBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        colorLike = CommonUtils.getColor(R.color.red_ball);
        colorDislike = CommonUtils.getColor(R.color.white);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.commentbar, this, true);
        binding.tvSend.setOnClickListener(this);
        binding.tvLike.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
    }


    public void setCommentNum(int commentNum) {
        binding.tvCommentCount.setText(MessageFormat.format("{0}", commentNum));
    }

    public void setLikeNum(int likeNum) {
        binding.tvLikeCount.setText(MessageFormat.format("{0}", likeNum));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSend:
                String content = binding.etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.toast("您未填写评论");
                    return;
                }
                if (listener != null) listener.send(content);
                break;

            case R.id.tvComment:
                if (listener != null) listener.onCommentClick();
                break;

            case R.id.tvLike:
                if (listener != null) listener.onLikeClick();
                break;
        }
    }

    private CBListener listener;

    public void setCBListener(CBListener listener) {
        this.listener = listener;
    }

    public void setLike(boolean like) {
        binding.tvLike.setTextColor(like ? colorLike : colorDislike);
    }

    public void clearContent() {
        binding.etContent.setText("");
    }

    public interface CBListener {
        void send(String content);

        void onLikeClick();

        void onCommentClick();
    }
}
