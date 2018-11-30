package com.miracle.base.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public abstract class AbsListViewAdapter<T, Holder> extends BaseAdapter {

    protected final List<T> datas;
    protected Context context;
    private LayoutInflater inflater;

    public AbsListViewAdapter(Context context) {
        super();
        datas = new ArrayList<T>();
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    public void setDatas(List<T> datas) {
        if (datas == null) {
            return;
        }

        this.datas.clear();
        this.datas.addAll(datas);
    }

    @Override
    public T getItem(int position) {
        if (position > datas.size() - 1) {
            position = datas.size() - 1;
        }
        if (position < 0) {
            position = 0;
        }
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 更新数据
     */
    public void update(List<T> datas) {
        if (datas == null) {
            return;
        }
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 增加数据
     */
    public void append(List<T> datas) {
        if (datas == null) {
            return;
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        if (datas == null) {
            return;
        }
        this.datas.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除某条数据
     */
    public void delete(int id) {
        if (datas == null || datas.size() < (id + 1)) {
            return;
        }
        this.datas.remove(id);
        notifyDataSetChanged();
    }

    /**
     * 删除某条数据
     */
    public void delete(T data) {
        if (datas == null || datas.size() <= 0) {
            return;
        }
        this.datas.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 添加一个数据到指定位置
     */
    public void add(int position, T data) {
        if (data == null) {
            return;
        }
        if (position < 0) {
            position = 0;
        }
        this.datas.add(position, data);
        notifyDataSetChanged();
    }

    /**
     * 添加一个数据
     *
     * @param data
     */
    public void add(T data) {
        if (data == null) {
            return;
        }
        if (datas.contains(data)) {
            Toast toast = Toast.makeText(context, "运单重复！", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        this.datas.add(data);
        notifyDataSetChanged();
    }
    /**
     * 将一条消息置顶
     *
     * @param position
     */
    public void setTop(int position) {
        datas.add(0, datas.remove(position));
        notifyDataSetChanged();

//		T data = datas.remove(position);
//		datas.add(0,data);
//		notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (null == convertView) {
            convertView = getInflater().inflate(getItemViewLayoutId(position),
                    null);
            holder = loadHolder(convertView);
            convertView.setTag(holder);
        } else {
            try {
                holder = (Holder) convertView.getTag();
            } catch (ClassCastException e) {
//                LogUtils.error(e.getMessage());
            }
        }
        T object = getItem(position);
        if (null != object) {
            bindView(position, object, holder);
        }
        return convertView;
    }

    // abstract 方法，留给子类实现

    /**
     * 获取返回Item的View
     *
     * @return item layout layout id
     */
    protected abstract int getItemViewLayoutId();

    /**
     * 如果 getItemViewLayoutId的时候，需要position,则需要重写 getItemViewLayoutId(int position) 这个方法。
     *
     * @param position
     * @return
     */
    protected int getItemViewLayoutId(int position) {
        return getItemViewLayoutId();
    }

    /**
     * 绑定数据到ItemView 上面
     *
     * @param object
     * @param holder
     */
    protected abstract void bindView(int position, T object, Holder holder);

    /**
     * 初始化 Holder
     *
     * @param v item View 对象
     * @return 实例化的对象
     */
    protected abstract Holder loadHolder(View v);

    public List<T> getDatas() {
        return datas;
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

}
