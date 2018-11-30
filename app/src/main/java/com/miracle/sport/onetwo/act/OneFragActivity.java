package com.miracle.sport.onetwo.act;

import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.BaseFragment;
import com.miracle.databinding.ActivityOneFragLayoutBinding;
import com.miracle.sport.onetwo.frag.HandleFragment;

import java.io.Serializable;
import java.util.List;

public class OneFragActivity extends BaseActivity<ActivityOneFragLayoutBinding> {
    public static String EXTRA_KEY_FRAG_CLASS = "EXTRA_KEY_FRAG_CLASS";
    public static String EXTRA_KEY_SEARCH = "EXTRA_KEY_SEARCH";
    public static String EXTRA_KEY_ACT_TITLE = "EXTRA_KEY_ACT_TITLE";
    public static String EXTRA_KEY_MSG = "EXTRA_KEY_MSG";
    public static String EXTRA_KEY_MSG_LIST = "EXTRA_KEY_MSG_LIST";

    BaseFragment fragment;
    String search;
    Message message;
    List<Message> msgs;
    String title;

    public static interface OneFragCallBack extends Serializable {
        public void onFragPreInit();
        public void onFragPostInit(BaseFragment baseFragment);
    }

    OneFragCallBack oneFragCallBack;

    @Override
    public int getLayout() {
        return R.layout.activity_one_frag_layout;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void initView() {
        hideTitle();//zqbfyc
        if(getIntent() != null){
            if(getIntent().getExtras().containsKey(EXTRA_KEY_SEARCH))
            {
                search = getIntent().getStringExtra(EXTRA_KEY_SEARCH);
            }
            if(getIntent().getExtras().containsKey(EXTRA_KEY_MSG))
            {
                message = getIntent().getParcelableExtra(EXTRA_KEY_MSG);
            }
            if(getIntent().getExtras().containsKey(EXTRA_KEY_MSG_LIST))
            {
                msgs = getIntent().getParcelableArrayListExtra(EXTRA_KEY_MSG_LIST);
            }
            if(getIntent().getExtras().containsKey(EXTRA_KEY_ACT_TITLE))
            {
                title = getIntent().getStringExtra(EXTRA_KEY_ACT_TITLE);
            }


            if(getIntent().getExtras().containsKey(EXTRA_KEY_FRAG_CLASS)) {
                try {
                    fragment = (BaseFragment) ((Class)getIntent().getSerializableExtra(EXTRA_KEY_FRAG_CLASS)).newInstance();
                    fragment.showTitle();
                    fragment.setShowTitleBack(true);
                    if(!TextUtils.isEmpty(title))
                        fragment.setTitle(title);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

            FragmentManager manager = getSupportFragmentManager();
            if(null != manager.findFragmentById(R.id.frag_continer))
                fragment = (BaseFragment) manager.findFragmentById(R.id.frag_continer);
            else {
                FragmentTransaction tran = manager.beginTransaction();
                if(fragment != null)
                    tran.add(R.id.frag_continer, fragment);
                tran.commit();
            }

            //spec
//            if(fragment instanceof FragFootNewsItemList)
//            {
//                FragFootNewsItemList ffnil = (FragFootNewsItemList) fragment;
//                ffnil.setReqKey(search);
//            }
        }
        if(fragment instanceof HandleFragment){
            if(message != null){
                HandleFragment hf = (HandleFragment) fragment;
                hf.uiHandler.sendMessage(message);
            }if(msgs != null){
                for(Message msg : msgs){
                    HandleFragment hf = (HandleFragment) fragment;
                    hf.uiHandler.sendMessage(msg);
                }
            }
        }
        showContent();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {

    }
}
