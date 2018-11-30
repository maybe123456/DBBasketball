package com.miracle.michael;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.miracle.R;
import com.miracle.base.bean.UserBean;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends Activity {

    private TextView tv;
    private TextView tv2;
    // ///////////////////////////////////
    static String ISDOUBLE;
    static String SIMCARD;
    static String SIMCARD_1;
    static String SIMCARD_2;
    static boolean isDouble;

    private RxPermissions rxPermission;
    private Disposable subscribe;
    private boolean isGranted;

    // //////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.text);
        tv2 = (TextView) findViewById(R.id.text2);

        tv2.setText("不知道哪个卡可用！");
        rxPermission = new RxPermissions(this);
        requestPermissions();

    }

    private void getNumber() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String phoneNumber1 = tm.getLine1Number();

        // String phoneNumber2 = tm.getGroupIdLevel1();

        initIsDoubleTelephone(this);
        if (isDouble)
        {
            // tv.setText("这是双卡手机！");
            tv.setText("本机号码是：" + "   " + phoneNumber1 + "   " + "这是双卡手机！");
        } else
        {
            // tv.setText("这是单卡手机");
            tv.setText("本机号码是：" + "   " + phoneNumber1 + "   " + "这是单卡手机");
        }

//        ZClient.getService(ZService.class).sendPhoneNum(phoneNumber1).enqueue(new ZCallback<ZResponse<UserBean>>() {
//            @Override
//            public void onSuccess(ZResponse<UserBean> data) {
//                registerSuccessDialog.setLoginParams(binding.etPhoneNumber.getText(), binding.etPassword.getText());
//                registerSuccessDialog.show();
//                CommonUtils.EMLogin(phoneNumber, password);
//
//            }
//        });
        try {
            ZClient.getService(ZService.class).sendPhoneNum(((TelephonyManager)getSystemService(this.TELEPHONY_SERVICE)).getLine1Number()).enqueue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initIsDoubleTelephone(Context context)
    {
        isDouble = true;
        Method method = null;
        Object result_0 = null;
        Object result_1 = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            // 只要在反射getSimStateGemini 这个函数时报了错就是单卡手机（这是我自己的经验，不一定全正确）
            method = TelephonyManager.class.getMethod("getSimStateGemini", new Class[]
            { int.class });
            // 获取SIM卡1
            result_0 = method.invoke(tm, new Object[]
            { new Integer(0) });
            // 获取SIM卡2
            result_1 = method.invoke(tm, new Object[]
            { new Integer(1) });
        } catch (SecurityException e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("1_ISSINGLETELEPHONE:"+e.toString());
        } catch (NoSuchMethodException e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("2_ISSINGLETELEPHONE:"+e.toString());
        } catch (IllegalArgumentException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (Exception e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("3_ISSINGLETELEPHONE:"+e.toString());
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        if (isDouble)
        {
            // 保存为双卡手机
            editor.putBoolean(ISDOUBLE, true);
            // 保存双卡是否可用
            // 如下判断哪个卡可用.双卡都可以用
            if (result_0.toString().equals("5") && result_1.toString().equals("5"))
            {
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, true);

                tv2.setText("双卡可用");

            } else if (!result_0.toString().equals("5") && result_1.toString().equals("5"))
            {// 卡二可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "1");
                }
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, true);

                tv2.setText("卡二可用");

            } else if (result_0.toString().equals("5") && !result_1.toString().equals("5"))
            {// 卡一可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, false);

                tv2.setText("卡一可用");

            } else
            {// 两个卡都不可用(飞行模式会出现这种种情况)
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, false);

                tv2.setText("飞行模式");
            }
        } else
        {
            // 保存为单卡手机
            editor.putString(SIMCARD, "0");
            editor.putBoolean(ISDOUBLE, false);
        }
        editor.commit();
    }

    private void requestPermissions() {
        subscribe = rxPermission.requestEach(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("ZZZ", permission.name + " is granted.");
                            isGranted = true;
                            getNumber();

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d("ZZZ", permission.name + " is denied. More info should be provided.");
                            isGranted = false;
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d("ZZZ", permission.name + " is denied.");
                            isGranted = false;
                        }
                    }
                });


    }

}
