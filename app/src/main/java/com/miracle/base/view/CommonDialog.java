package com.miracle.base.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.miracle.R;
import com.miracle.databinding.DialogCommonBinding;


/**
 * Created by Administrator on 2017/8/23.
 */

public class CommonDialog extends Dialog implements View.OnClickListener {

    private final DialogCommonBinding binding;

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.commondialog);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_common, null, false);
//        setCanceledOnTouchOutside(false);
        setContentView(binding.getRoot());
//        getWindow().setBackgroundDrawable(new BitmapDrawable());
//        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setListener();

    }

    private void setListener() {
        binding.btNext.setOnClickListener(this);
    }

    public void setMessage(String message) {
        binding.tvMessage.setText(message);
    }

    public void setBtListener(View.OnClickListener onClickListener) {
        binding.btNext.setOnClickListener(onClickListener);
    }

    public void setImg(int resId) {
        binding.ivImg.setImageResource(resId);
    }

    public void setBtText(String text) {
        binding.btNext.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNext:
                dismiss();
                break;
        }
    }
}
