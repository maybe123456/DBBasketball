package com.miracle.base.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.databinding.ImageDialogBinding;

import java.util.List;

/**
 * Created by Michael on 2018/11/5 20:08 (星期一)
 */
public class ImageDialog extends Dialog {
    private ImageDialogBinding binding;
    private ImageAdapter mAdapter;

    private static ImageDialog instance;

    public static ImageDialog getInstance(Context context) {
        if (instance == null) {
            instance = new ImageDialog(context);
        }
        return instance;
    }

    public ImageDialog(@NonNull Context context) {
        super(context, R.style.commondialog);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.image_dialog, null, false);
        binding.recyclerView.setAdapter(mAdapter = new ImageAdapter());
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public void show(List<String> urls, int position) {
        mAdapter.setNewData(urls);
        binding.recyclerView.scrollToPosition(position);
        show();
    }


    private final class ImageAdapter extends RecyclerViewAdapter<String> {
        public ImageAdapter() {
            super(R.layout.item_iv);
        }

        @Override
        protected void convert(BaseViewHolder helper, String url) {
            GlideApp.with(getContext()).load(url).placeholder(R.mipmap.defaule_img).into((ImageView) helper.getView(R.id.iv));
        }
    }

}
