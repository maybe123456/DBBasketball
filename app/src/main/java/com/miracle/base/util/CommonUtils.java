package com.miracle.base.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.exceptions.HyphenateException;
import com.miracle.R;
import com.miracle.base.App;
import com.miracle.base.AppConfig;
import com.miracle.base.Constant;
import com.miracle.base.bean.UserBean;
import com.miracle.base.im.DemoHelper;
import com.miracle.base.util.sqlite.SQLiteKey;
import com.miracle.base.util.sqlite.SQLiteUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TreeSet;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.hyphenate.chat.EMClient.TAG;
import static java.util.regex.Pattern.matches;

/**
 * 工具类
 */
public class CommonUtils {

    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,199
     **/
    private static final String CHINA_TELECOM_PATTERN = "(?:^(?:\\+86)?1(?:33|53|7[37]|8[019]|99)\\d{8}$)|(?:^(?:\\+86)?1700\\d{7}$)";
    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1707,1708,1709,175,166
     **/
    private static final String CHINA_UNICOM_PATTERN = "(?:^(?:\\+86)?1(?:3[0-2]|4[5]|5[56]|6[6]|7[56]|8[56])\\d{8}$)|(?:^(?:\\+86)?170[7-9]\\d{7}$)";
    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705,198
     **/
    private static final String CHINA_MOBILE_PATTERN = "(?:^(?:\\+86)?1(?:3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478]|9[8])\\d{8}$)|(?:^(?:\\+86)?1705\\d{7}$)";

    /**
     * 仅手机号格式校验
     */
    private static final String PHONE_PATTERN = new StringBuilder(300)
            .append(CHINA_MOBILE_PATTERN).append("|")
            .append(CHINA_TELECOM_PATTERN).append("|")
            .append(CHINA_UNICOM_PATTERN).toString();

    /**
     * 正则表达式：验证密码
     */
    private static final String REGEX_PASSWORD = "^([`~!@#$%^&*()_\\-+=|{}':;'\\\\,\\[\\].<>]|[a-zA-Z0-9]){6,18}$";


    public static boolean checkPhone(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            return Pattern.matches(PHONE_PATTERN, phone);
        }
        return false;
    }

    public static boolean checkPassword(CharSequence password) {
        if (!TextUtils.isEmpty(password)) {
            return matches(REGEX_PASSWORD, password);
        }
        return false;
    }

    /**
     * 获取 ticket
     *
     * @param context
     * @return
     */
//    public static String getTicket(Context context) {
//        String ticket = SharedPreferencesUtils.getString(context, "ticket");
//        return ticket;
//    }

    /**
     * 显示键盘
     *
     * @param context
     * @param view
     */
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制隐藏键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 光标置于文末
     *
     * @param edit
     * @param str
     */
    public static void editTextSelection(EditText edit, String str) {
        if (!TextUtils.isEmpty(str)) {
            edit.setText(str);
            edit.setSelection(edit.getText().length());
        }
    }

    /**
     * editText 输入框 内容比对
     *
     * @param edit
     * @return
     */
    public static boolean editInputComparison(EditText edit) {
        String str = edit.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {
            return false;
        } else {
            if ("0".equals(str) || "0.0".equals(str) || "0.00".equals(str)) {
                return false;
            }

            return true;
        }
    }


    /**
     * 获取屏幕px
     *
     * @return 分辨率
     */
    public static int getScreenWidthPixels(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 状态栏高度的view
     *
     * @param view
     * @param context
     */
    public static void bindStatusHeightView(View view, Context context) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = CommonUtils.getStatusHeight(context);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 关闭popupWindow
     * 监听判断获取触屏位置如果在选择框外面则销毁弹出框
     *
     * @param view
     * @return
     */
    public static void dismissPopupWindow(View view, final View target, final PopupWindow window) {
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = target.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        window.dismiss();
                    }
                }
                return true;
            }
        });
    }


    public static Drawable getDrawable(int resid) {
        return getResoure().getDrawable(resid);
    }

    public static int getColor(int resid) {
        return getResoure().getColor(resid);
    }

    public static String getString(int resid) {
        return getResoure().getString(resid);
    }


    public static Resources getResoure() {
        return App.getApp().getResources();
    }

    public static String[] getStringArray(int resid) {
        return getResoure().getStringArray(resid);
    }


    public static float getDimens(int resId) {
        return getResoure().getDimension(resId);
    }

    public static void removeSelfFromParent(View child) {

        if (child != null) {

            ViewParent parent = child.getParent();

            if (parent instanceof ViewGroup) {

                ViewGroup group = (ViewGroup) parent;

                group.removeView(child);
            }
        }
    }

    /**
     * 利用系统CallLog获取通话历史记录
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCallHistoryList(Context context, ContentResolver cr) {


        if (ActivityCompat.checkSelfPermission(App.getApp(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        Cursor cs = cr.query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
                new String[]{
                        CallLog.Calls.CACHED_NAME,  //姓名
                        CallLog.Calls.NUMBER,    //号码
                        CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                        CallLog.Calls.DATE,  //拨打时间
                        CallLog.Calls.DURATION   //通话时长
                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        String callHistoryListStr = "";
        int i = 0;
        if (cs != null && cs.getCount() > 0) {
            for (cs.moveToFirst(); !cs.isAfterLast() & i < 50; cs.moveToNext()) {
                String callName = cs.getString(0);
                String callNumber = cs.getString(1);
                //通话类型
                int callType = Integer.parseInt(cs.getString(2));
                String callTypeStr = "";
                switch (callType) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callTypeStr = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callTypeStr = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callTypeStr = "未接";
                        break;
                }
                //拨打时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date callDate = new Date(Long.parseLong(cs.getString(3)));
                String callDateStr = sdf.format(callDate);
                //通话时长
                int callDuration = Integer.parseInt(cs.getString(4));
                int min = callDuration / 60;
                int sec = callDuration % 60;
                String callDurationStr = min + "分" + sec + "秒";
                String callOne = "类型：" + callTypeStr + ", 称呼：" + callName + ", 号码："
                        + callNumber + ", 通话时长：" + callDurationStr + ", 时间:" + callDateStr
                        + "\n---------------------\n";

                callHistoryListStr += callOne;
                i++;
            }
        }
        return callHistoryListStr;
    }

    public static String getJsonFromAssets(String fileName) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            AssetManager assetManager = App.getApp().getAssets();
            br = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(br);
        }
        return sb.toString();
    }

    private static void closeResource(Closeable... res) {
        for (Closeable c : res) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("ZZZ", e.getMessage());
                }
            }

        }
    }


    public static StateListDrawable addStateDrawable(Context context, int idNormal, int idPressed, int idFocused) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focus = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        sd.addState(new int[]{android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);
        sd.addState(new int[]{}, normal);
        return sd;
    }

    /**
     * 中日韩统一表意文字（CJK Unified Ideographs），
     * 目的是要把分别来自中文、日文、韩文、越文中，本质、意义相同、形状一样或稍异的表意文字（主要为汉字，但也有仿汉字如日本国字、韩国独有汉字、越南的喃字）
     * 于ISO 10646及Unicode标准内赋予相同编码。CJK 是中文（Chinese）、日文（Japanese）、韩文（Korean）三国文字的缩写。
     * CJK统一汉字的编码区间：0x4e00–0x9fbb
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 文件存储
     *
     * @param object   要存储的对象
     * @param fileName 存储文件名，后缀任意
     */
    public static void writeObj(Object object, String parent, String fileName) {
        ObjectOutputStream oos = null;
        try {
            File target = new File(parent, fileName);
            File parentFile = target.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            oos = new ObjectOutputStream(new FileOutputStream(target));
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(oos);
        }
    }

    /**
     * 从文件中读取对象，父目录为Constant.DIR_PARENT
     *
     * @param fileName 要读取的文件名
     * @return 读取到的对象
     */
    public static Object readObj(String parent, String fileName) {
        File target = new File(parent, fileName);
        ObjectInputStream ois = null;
        Object object = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(target));
            object = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(ois);
        }
        return object;
    }

    public static Object readObj(File file) {
        ObjectInputStream ois = null;
        Object object = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            object = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(ois);
        }
        return object;
    }


    /**
     * 获取时间，包括时区
     * Letter  Date or Time Component  Presentation  Examples
     * G  Era designator  Text  AD
     * y  Year  Year  1996; 96
     * M  Month in year  Month  July; Jul; 07
     * w  Week in year  Number  27
     * W  Week in month  Number  2
     * D  Day in year  Number  189
     * d  Day in month  Number  10
     * F  Day of week in month  Number  2
     * E  Day in week  Text  Tuesday; Tue
     * a  Am/pm marker  Text  PM
     * H  Hour in day (0-23)  Number  0
     * k  Hour in day (1-24)  Number  24
     * K  Hour in am/pm (0-11)  Number  0
     * h  Hour in am/pm (1-12)  Number  12
     * m  Minute in hour  Number  30
     * s  Second in minute  Number  55
     * S  Millisecond  Number  978
     * z  Time zone  General time zone  Pacific Standard Time; PST; GMT-08:00
     * Z  Time zone  RFC 822 time zone  -0800
     *
     * @return String
     */
    public static String queryDate() {
        final String FORMAT = "yyyy-MM-dd HH:mm:ss";
        return queryDate(FORMAT);
    }

    /**
     * 获取时间，包括时区
     *
     * @param format 格式参数，如yyyy-MM-dd HH:mm:ss z
     * @return String
     */

    @SuppressLint("SimpleDateFormat")
    public static String queryDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        Calendar cld = Calendar.getInstance();
        sdf.setTimeZone(new SimpleTimeZone(cld.get(Calendar.ZONE_OFFSET), ""));
        String sTime = sdf.format(date);
        if (null != sTime) {
            return sTime;
        }
        return "";
    }


    /**
     * 获取权限信息
     *
     * @param c
     * @return
     */
    public static String[] queryPermissions(Context c) {
        try {
            PackageInfo info = c.getPackageManager().getPackageInfo(
                    c.getPackageName(),
                    PackageManager.GET_UNINSTALLED_PACKAGES
                            | PackageManager.GET_PERMISSIONS);
            return info.requestedPermissions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除文件
     */
    public static void deleteFile(File f) {
        if (f != null && f.exists()) {
            if (f.isFile()) {
                f.delete();
            }
            File[] files = f.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFile(file);
                    file.delete();
                } else if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 删除文件及文件夹
     *
     * @param f
     */
    public static void deleteFileAndDir(File f) {
        if (f.exists()) {
            if (f.isFile()) {
                f.delete();
                return;
            }
            File[] files = f.listFiles();
            if (files == null) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteFileAndDir(files[i]);
                    files[i].delete();
                } else if (files[i].isFile()) {
                    files[i].delete();
                }
            }
            if (f.isDirectory()) {
                f.delete();
            }
        }
    }

    /**
     * 删除文件,但不删除文件夹
     */
    public static void deleteFileNoDir(File f) {
        if (f.exists()) {
            if (f.isFile()) {
                f.delete();
            }
            File[] files = f.listFiles();
            if (files == null) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteFile(files[i]);
                } else if (files[i].isFile()) {
                    files[i].delete();
                }
            }
        }
    }


    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断手机是否为root, true 则为已root
     *
     * @return
     */
    public static boolean isRooted() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath)) {
            return true;
        }

        if (new File(xBinPath).exists() && isExecutable(xBinPath)) {
            return true;
        }
        return false;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x') {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }

    /**
     * 播放音频
     *
     * @param fileName
     */
    public static void playMusic(Context context, String fileName) {
        try {
            final MediaPlayer player = new MediaPlayer();
            AssetManager am = context.getAssets();
            AssetFileDescriptor descriptor = am.openFd(fileName);
            player.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getDeclaredLength());
            player.prepare();
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.stop();
                    player.release();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static long lastClickTime;

    /**
     * 是否快速点击
     *
     * @return
     */
    public static boolean isFastClick() {
        long clickTime = System.currentTimeMillis();
        long intervalTime = clickTime - lastClickTime;
        lastClickTime = clickTime;
        return intervalTime < 800;
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param telephone
     */
    public static void telDial(Context context, String telephone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + telephone);
            intent.setData(data);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "NO Intent FoundException",
                    Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 发邮件
     *
     * @param emailAdd
     */
    public static void sendEmail(Context context, String emailAdd, String title, String content) {
        try {
            Intent data = new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse("mailto:" + emailAdd));
            data.putExtra(Intent.EXTRA_SUBJECT, title);
            data.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(data);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "NO Intent FoundException",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统的短信界面
     *
     * @param message
     */
    public static void sendMessage(Context context, String number,
                                   String message) {
        try {
            Intent sendIntent;
            if (TextUtils.isEmpty(number)) {
                sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", message);
                sendIntent.setType("vnd.android-dir/mms-sms");
            } else {
                Uri uri = Uri.parse("smsto:" + number);
                sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                sendIntent.putExtra("sms_body", message);
            }
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "NO Intent FoundException",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统编辑添加联系人
     *
     * @param context
     * @param phoneNumber 电话
     * @param name        姓名
     * @param email
     */
    public static void addContact(Context context, String[] phoneNumber,
                                  String name, String email) {
        try {
            Intent it = new Intent(Intent.ACTION_INSERT);
            it.setType("vnd.android.cursor.dir/person");
            it.setType("vnd.android.cursor.dir/contact");
            it.setType("vnd.android.cursor.dir/raw_contact");
            it.putExtra(ContactsContract.Intents.Insert.NAME, name);
            it.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            if (null == phoneNumber) {
                it.putExtra(ContactsContract.Intents.Insert.PHONE, "");
            } else {
                int phoneLength = phoneNumber.length;
                for (int i = 0; i < phoneLength; i++) {
                    if (i == 0) {
                        it.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber[i]);
                        it.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                    } else if (i == 1) {
                        it.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, phoneNumber[i]);
                        it.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
                    } else if (i == 2) {
                        it.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, phoneNumber[i]);
                        it.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    }
                }
            }
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "NO Intent FoundException",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 判断是不是邮箱
     *
     * @param email
     * @return
     */

    public static boolean isEmail(String email) {
        return Pattern.matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
    }

    /**
     * 获取验证码
     *
     * @param n 验证码的位数
     * @return n位验证码
     */
    public static String createVerification(int n) {
        int[] arr = new int[n];
        Random random = new Random();
        for (int x = 0; x < n; x++) {
            arr[x] = random.nextInt(10);
        }
        StringBuilder s = new StringBuilder();
        for (int i : arr) {
            s.append(i);
        }
        return s.toString();
    }

    /**
     * 后台调用系统发短信功能，不打开短信应用界面。
     *
     * @param context
     * @param phoneNumber 指定接收短信的手机号。
     * @param message     发送
     */
    public static void sendMessageOnBackground(Context context, String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }

    /**
     * 监测是否安装该某应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> infos = manager.getInstalledPackages(0);
        for (PackageInfo info : infos) {
            if (info.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取屏幕宽度
     */
    public static int getWidthPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeightPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
//        FileInputStream fis = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 只读边，不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(url, options);
        int w = options.outWidth;
        int h = options.outHeight;
        float hh = 1280f;
        float ww = 720f;
        int be = 2;
        if (w > h && w > ww) {
            be = (int) (options.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (options.outHeight / hh);
        }
        if (be <= 0) {
            be = 2;
        }
        options.inSampleSize = be;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(url, options);
        return bitmap;
    }


    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static void pickPictureFromMedia(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, Constant.REQUSET_CODE_MEDIA);
    }

    public static Bitmap getMediaBitmapOnActivityResult(Context context, Intent data) {
        try {
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(data.getData()));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * 手机拍照，缩略图
     *
     * @param activity
     * @param requestCode 在onActivityResult中根据requestCode处理数据
     */
    public static void takePicture_Thumbnail(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 缩略图，拍照之后的图片获取
     *
     * @param data
     * @return
     */
    public static Bitmap getThumbnailBitmapOnActivityResult(Intent data) {
        return (Bitmap) data.getExtras().get("data");
    }

    /**
     * 手机拍照，原图
     *
     * @param activity
     * @param requestCode 在onActivityResult中根据requestCode处理数据
     * @param tempPath    original
     */
    public static void takePicture_Original(Activity activity, int requestCode, String tempPath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(tempPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 原图，拍照之后的图片获取
     *
     * @param tempPath
     * @return
     */
    public static Bitmap getOriginalBitmapOnActivityResult(String tempPath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tempPath);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            deleteFile(new File(tempPath));
            closeResource(fis);
        }
        return null;
    }

    /**
     * 原图，拍照之后的图片获取
     *
     * @param file
     * @return
     */
    public static Bitmap getOriginalBitmapOnActivityResult(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            deleteFile(file);
            closeResource(fis);
        }
        return null;
    }

    /**
     * 原图，拍照之后的图片获取,不删除缓存
     *
     * @param tempPath
     * @return
     */
    public static Bitmap getOriginalBitmapOnActivityResultNotDelete(String tempPath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tempPath);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResource(fis);
        }
        return null;
    }

    /**
     * 原图，拍照之后的图片获取,不删除缓存
     *
     * @param file
     * @return
     */
    public static Bitmap getOriginalBitmapOnActivityResultNotDelete(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResource(fis);
        }
        return null;
    }


    public static byte[] image2byte(String path) {
        FileInputStream input = null;
        ByteArrayOutputStream output = null;
        try {
            input = new FileInputStream(new File(path));
            output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(output, input);
        }
        return null;
    }

    public static Bitmap byte2Bitmap(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    public static Bitmap getBitmap(File file) {
        if (file == null) return null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResource(is);
        }
    }

    public static Bitmap getBitmap(String path) {
        if (path == null) return null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(path));
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResource(is);
        }
    }

    /**
     * 手机内存使用情况
     */
    public static void displayBriefMemory(Context context) {

        final ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();

        activityManager.getMemoryInfo(info);

        Log.e("ZZZ", "系统剩余内存:" + (info.availMem >> 10) + "k");

        Log.e("ZZZ", "系统是否处于低内存运行：" + info.lowMemory);

        Log.e("ZZZ", "当系统剩余内存低于" + info.threshold + "时就看成低内存运行");
    }


    /**
     * 绘制水印
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                               int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newBitmap);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
        // 存储
        canvas.restore();
        return newBitmap;
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File saveImage(String fileName, Bitmap bmp) {
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(fos);
        }
        return file;
    }

    public static int pxToSp(final Context context, final float px) {
        return Math.round(px / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int spToPx(final Context context, final float sp) {
        return Math.round(sp * context.getResources().getDisplayMetrics().scaledDensity);
    }


    private static Map<String, Typeface> cachedFontMap = new HashMap<String, Typeface>();


    public static Typeface findFont(Context context, String fontPath, String defaultFontPath) {

        if (fontPath == null) {
            return Typeface.DEFAULT;
        }

        String fontName = new File(fontPath).getName();
        String defaultFontName = "";
        if (!TextUtils.isEmpty(defaultFontPath)) {
            defaultFontName = new File(defaultFontPath).getName();
        }

        if (cachedFontMap.containsKey(fontName)) {
            return cachedFontMap.get(fontName);
        } else {
            try {
                AssetManager assets = context.getResources().getAssets();

                if (Arrays.asList(assets.list("")).contains(fontPath)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
                    cachedFontMap.put(fontName, typeface);
                    return typeface;
                } else if (Arrays.asList(assets.list("fonts")).contains(fontName)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", fontName));
                    cachedFontMap.put(fontName, typeface);
                    return typeface;
                } else if (Arrays.asList(assets.list("iconfonts")).contains(fontName)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("iconfonts/%s", fontName));
                    cachedFontMap.put(fontName, typeface);
                    return typeface;
                } else if (!TextUtils.isEmpty(defaultFontPath) && Arrays.asList(assets.list("")).contains(defaultFontPath)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), defaultFontPath);
                    cachedFontMap.put(defaultFontName, typeface);
                    return typeface;
                } else {
                    throw new Exception("Font not Found");
                }

            } catch (Exception e) {
                cachedFontMap.put(fontName, Typeface.DEFAULT);
                return Typeface.DEFAULT;
            }
        }
    }

    public static void darkenBackground(Activity activity, Float bgcolor) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    public static void makeIndicatorWidthWithMatchTextView(final TabLayout tabLayout) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);

                    int dp10 = (int) DisplayUtil.dip2px(App.getApp(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void loadUrl(final WebView webView, String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new WebViewClient());
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
        webView.loadUrl(url);
    }

    public static String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]     * @param context     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]     * @param context     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * [获取应用程序版本名称信息]     * @param context     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取图标 bitmap     * @param context
     */
    public static synchronized Bitmap getBitmap(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    /**
     * @param s   待检查的字符串
     * @param min 字符串最小长度
     * @param max 字符串最大长度
     * @return true:符合规则，false：不符合规则
     */
    public static boolean loginInputCheck(String s, int min, int max) {
        if (Pattern.matches("[a-zA-Z]", s.substring(0, 1))) {
            String regex = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`~!@#$%^&*()_+|}{\":?><,./;'[]\\-=";
            if (s.length() >= min && s.length() <= max) {
                for (char c : s.toCharArray()) {
                    if (!regex.contains(c + "")) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static UserBean getUser() {
        return GsonUtil.json2Obj(SQLiteUtil.getString(SQLiteKey.USER), UserBean.class);
    }

    public static String getUserId() {
        UserBean user = getUser();
        return user == null ? "" : user.getUserId() + "";
    }

    public static void login(String emUserName, String currentPassword) {
        EMClient.getInstance().login(emUserName, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        App.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                ToastUtil.toast(CommonUtils.getString(R.string.Login_failed) + message);
            }
        });
    }

    public static void EMLogin(final String username, final String pwd) {
        ThreadUtil.runInBackGroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, pwd);
                    DemoHelper.getInstance().setCurrentUserName(username);
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                } finally {
                    EMClient.getInstance().login(username, pwd, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.e("ZZZ", "onSuccess:");
                            getGroupId();
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.e("ZZZ", "onError:" + s);
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            Log.e("ZZZ", "onProgress:" + s);
                        }
                    });
                }
            }
        });
    }

    public static void getGroupId() {
        ThreadUtil.runInBackGroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMCursorResult<EMGroupInfo> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(10, "");
                    List<EMGroupInfo> returnGroups = result.getData();
                    if (returnGroups.size() > 0) {
                        EMClient.getInstance().groupManager().joinGroup(AppConfig.groupId = returnGroups.get(0).getGroupId());
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setMargins(View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }


    //判断是否有指纹录入
    public static boolean hasEnrolledFingerprints(Context context) {
        return FingerprintManagerCompat.from(context).hasEnrolledFingerprints();
    }

    //判断是否开启锁屏密码
    public static boolean isKeyguardSecure(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardSecure();
    }

    //判断硬件是否支持指纹识别
    public static boolean isHardwareDetected(Context context) {
        return FingerprintManagerCompat.from(context).isHardwareDetected();
    }


    public static void setFullScrseen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static Set<String> getPhoneNum(Context context) {
        Set<String> nums = new TreeSet<>();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String phoneNumber1 = tm.getLine1Number();
        nums.add(phoneNumber1);

        //PERMISSION PHONE STATE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager sm = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> silist = sm.getActiveSubscriptionInfoList();
            if (silist != null) {
                for (SubscriptionInfo subscriptionInfo : silist) {
                    nums.add(subscriptionInfo.getNumber());
                }
            }
        }
        return nums;
    }
}
