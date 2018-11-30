package com.miracle.sport.onetwo.frag;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.miracle.base.BaseFragment;

public abstract class HandleFragment<T extends ViewDataBinding> extends BaseFragment<T> {
    public static int MSG_WHAT_SET_TITLE = 100;

    public Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            onHandleMessage(msg);
        }
    };

    public abstract void onHandleMessage(Message msg);

    /////////////////////////
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(fragLifeListner != null){
            fragLifeListner.onViewCreated();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(fragLifeListner != null){
            fragLifeListner.onDestoryView();
        }
    }

    public interface FragLifeListner{
        void onViewCreated();
        void onDestoryView();
    }
    FragLifeListner fragLifeListner;

    public FragLifeListner getFragLifeListner() {
        return fragLifeListner;
    }

    public void setFragLifeListner(FragLifeListner fragLifeListner) {
        this.fragLifeListner = fragLifeListner;
    }

    public interface AddSubFragListener<X extends Fragment>{
        void onFindFromTag(X x);
        void onNewInstance(X x);
        void onCommit();
    }

    public <X extends Fragment> X addSubFragment(int id, Class<X> fragmentClass){
        return addSubFragment(id, fragmentClass, null);
    }

//    public class AddSubFragBuilder{
//        //add().
//    }

    public <X extends Fragment> X addSubFragment(int id, Class<X> fragmentClass, final AddSubFragListener addSubFragListener){
        X subfragment = null;

        Fragment fragment = getChildFragmentManager().findFragmentByTag(fragmentClass.toString());

        if(fragment != null)
        {
            subfragment = (X) fragment;
            if(addSubFragListener != null)
                addSubFragListener.onFindFromTag(subfragment);
        }else{
            try {
                subfragment = (X) fragmentClass.newInstance();
                if(addSubFragListener != null)
                    addSubFragListener.onNewInstance(subfragment);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(id, subfragment, fragmentClass.toString());
            if(addSubFragListener != null){
                ft.runOnCommit(new Runnable() {
                    @Override
                    public void run() {
                        addSubFragListener.onCommit();
                    }
                });
            }
            ft.commitNow();
        }
        return subfragment;
    }
}

