package com.miracle.base.view.zradiogroup;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.apache.commons.logging.impl.LogFactoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ZRadioGroup extends LinearLayout implements ZRadioButton.OnCheckedChangeListener {
    private Context context;
    private ZRadioButton[] mChildren = new ZRadioButton[0];
    private Fragment[] mFragments;
    private FragmentManager fragmentManager;

    public ZRadioGroup(Context context) {
        this(context, null);
    }

    public ZRadioGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        List<ZRadioButton> childrenList = new ArrayList<>();
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof ZRadioButton) {
                childrenList.add((ZRadioButton) view);
            }
        }
        mChildren = childrenList.toArray(mChildren);
        for (int i = 0; i < mChildren.length; i++) {
            ZRadioButton zRadioButton = mChildren[i];
            zRadioButton.setOncheckedChangeListener(this);
            zRadioButton.setPosition(i);

        }
        for (ZRadioButton zRadioButton : mChildren) {
            zRadioButton.setOncheckedChangeListener(this);
        }
    }


    /**
     * @param containerId
     * @param fragments
     * @Deprecated Use {@link #setUp(FragmentManager, int, Fragment...)} instead.
     */
    @Deprecated
    public void setupWithContainerAndFragments(int containerId, Fragment... fragments) {
        if (context instanceof AppCompatActivity) {
            fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        } else {
            throw new RuntimeException("For better performance,ZRadioGroup only support used in AppCompatActivity");
        }
        setUp(fragmentManager, containerId, fragments);
    }

    public void setUp(FragmentManager manager, int containerId, Fragment... fragments) {
        this.fragmentManager = manager;
        mFragments = fragments;

//        Log.i("XX1","fragmentManager.getFragments(); :  " + fragmentManager.getFragments());
        FragmentTransaction tran = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            Fragment fragment = fragments[i];
            Fragment cacheFrag = getIfExsist(containerId, fragment.getClass());

//            Log.i("XXX", "XXX getRecycleFragment " + cacheFrag);
            if(cacheFrag != null) {
                mFragments[i] = cacheFrag;
            }else{
                tran.add(containerId, fragment, fragment.getClass().toString());
            }

            cacheFrag = fragment;
            tran.hide(cacheFrag);
        }
        tran.commit();
        //代码添加的Fragment，需调用该方法
        onFinishInflate();
        if (mChildren.length > 0) {
            mChildren[0].performClick();
            if (another != null) {
                another.getmChildren()[0].performClick();
            }
        }

//        Log.i("XX2","fragmentManager.getFragments(); :  " + fragmentManager.getFragments());
    }

    public <X> X getIfExsist(int containerId, Class<X> clazz) {
        if(fragmentManager == null) return null;

        Fragment fragment = fragmentManager.findFragmentByTag(clazz.toString());
//        Log.i("XXX","XXX  findedFrag " + containerId + " class " + clazz + "  - res :" +fragment);
        if(fragment != null){
            if(fragment.getClass() == clazz) {
                X x = (X) fragment;
                return x;
            }
        }
        return null;
    }

    public void setCheck(int index) {
        mChildren[index].performClick();
    }

    private ZRadioButton lastCheckedZRadioButton;

    @Override
    public void onCheckedChange(ZRadioButton zRadioButton, int position, boolean isChecked) {
        if (isChecked) {
            if (another != null) another.getmChildren()[position].performClick();
            if (fragmentManager != null) {
                FragmentTransaction tran = fragmentManager.beginTransaction();
                for (int i = 0; i < mFragments.length; i++) {
                    if (i == position) {
                        tran.show(mFragments[i]);
                    } else {
                        tran.hide(mFragments[i]);
                    }
                }
                tran.commit();
            }
            if (lastCheckedZRadioButton != null) {
                lastCheckedZRadioButton.toggle();
            }
            lastCheckedZRadioButton = zRadioButton;
        }
    }

    public ZRadioButton[] getmChildren() {
        return mChildren;
    }

    public void setmChildren(ZRadioButton[] mChildren) {
        this.mChildren = mChildren;
    }


    private ZRadioGroup another;

    public void combineAnother(ZRadioGroup another) {
        this.another = another;
    }
}
