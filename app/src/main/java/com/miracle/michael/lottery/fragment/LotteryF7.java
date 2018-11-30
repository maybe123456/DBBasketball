package com.miracle.michael.lottery.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.databinding.F7LotteryBinding;
import com.miracle.michael.lottery.bean.LotteryPrizeInfoBean;
import com.miracle.michael.lottery.bean.LotteryTrendBean;
import com.miracle.michael.lottery.util.DDTrendChart;
import com.miracle.michael.lottery.util.TrendData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 走势图
 * Created by asus on 2018/4/11.
 */

public class LotteryF7 extends BaseFragment<F7LotteryBinding> implements DDTrendChart.ISelectedChangeListener {

    private String title = "数据";
    private String hist_type = "HF_CQSSC";

    private boolean isOpen = false;

    private List<List<LotteryTrendBean>> trendBean = new ArrayList<>();
    private int width = 0;

    private boolean isPlus = false;
    private int forNum = 10;
    private int topNum = 10;


    final int maxSignleNum = 9;
    private DDTrendChart mTrendChart;

//    @Override
//    protected int getLayoutResourceId() {
//        return R.layout.fragment6;
//    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        binding.trendTitle.setText(title);
        initViews();
        loadData();
//        setView();
//        trendCqssc.performClick();
//        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//        mTrendChart = new TrendChart(getActivity(), trendTrendView);
//        trendTrendView.setChart(mTrendChart);
//        mTrendChart.setShowYilou(true);
//        mTrendChart.setDrawLine(true);
    }

    @Override
    public void initListener() {
        binding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null)
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }


    private void setView() {
        if (isOpen) {
            binding.trendTextll.setVisibility(View.VISIBLE);
        } else {
            binding.trendTextll.setVisibility(View.GONE);
        }
    }

    private final Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            LotteryF7.this.mTrendChart.updateData("01", (ArrayList) paramMessage.obj);
        }
    };

    private void initViews() {
//        @BindView(R.id.ltv_trendView)
//        LottoTrendView mTrendView;
        this.mTrendChart = new DDTrendChart(mContext, binding.ltvTrendView);
        this.binding.ltvTrendView.setChart(mTrendChart);
        this.mTrendChart.setShowYilou(true);
        this.mTrendChart.setDrawLine(true);
        this.mTrendChart.setSelectedChangeListener(this);
    }

    private final OkHttpClient client = new OkHttpClient();

    public void loadData() {
        // 根据01/30.xml 或者是01/50.xm可以调整数字
        String url = "http://mobile.9188.com/data/app/zst/01/30.xml";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                try {
                    setParser(inputStream);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        });
    }

    /**
     *
     */
    protected void setParser(InputStream inputStream) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        Collection arrayList2 = new ArrayList();
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        newPullParser.setInput(inputStream, "utf-8");
        TrendData r0;
        for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
            String name = newPullParser.getName();
            if (eventType == 2) {
                if ("row".equals(name)) {
                    TrendData trendData = new TrendData();
                    trendData.setType("row");
                    String attributeValue = newPullParser.getAttributeValue(null, "pid");
                    if (!(TextUtils.isEmpty(attributeValue) || attributeValue.length() <= 4)) {
                        attributeValue = attributeValue.substring(4);
                    }
                    trendData.setPid(attributeValue);
                    trendData.setRed(newPullParser.getAttributeValue(null, "red"));
                    trendData.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    trendData.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    trendData.setOes(newPullParser.getAttributeValue(null, "oe"));
                    trendData.setBss(newPullParser.getAttributeValue(null, "bs"));
                    trendData.setOne(newPullParser.getAttributeValue(null, "one"));
                    trendData.setTwo(newPullParser.getAttributeValue(null, "two"));
                    trendData.setThree(newPullParser.getAttributeValue(null, "three"));
                    trendData.setCodes(newPullParser.getAttributeValue(null, "codes"));
                    trendData.setSum(newPullParser.getAttributeValue(null, "sum"));
                    trendData.setSpace(newPullParser.getAttributeValue(null, "space"));
                    trendData.setNum(newPullParser.getAttributeValue(null, "num"));
                    trendData.setTimes(newPullParser.getAttributeValue(null, "times"));
                    trendData.setForm(newPullParser.getAttributeValue(null, "form"));
                    arrayList.add(trendData);
                } else if ("dis".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("dis");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                } else if ("avg".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("avg");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                } else if ("mmv".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("mmv");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                } else if ("mlv".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("mlv");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                }
            }
        }
        arrayList.addAll(arrayList2);
        mHandler.sendMessage(mHandler.obtainMessage(120, arrayList));
    }


    public void onSelectedChange(TreeSet<Integer> treeSet, TreeSet<Integer> treeSet2) {

    }


    private void getData(String hist_type) {
//        CommentModel.getInstant().getPrizeInfo(hist_type, new CallBack() {
//            @Override
//            public void onSuccess(Object o, boolean isSuccess, String msg) {
//                trendBean.clear();
//                List<PrizeInfoBean> bean = (List<PrizeInfoBean>) o;
//                editData(bean);
//                mTrendChart.setBlueCount(topNum, width, isPlus);
//                mTrendChart.updateData(trendBean.get(0));
//            }
//
//            @Override
//            public void onFailure(String fail) {
//
//            }
//        });
    }

    private void editData(List<LotteryPrizeInfoBean> bean) {
        List<String> listNum = getListNum(bean.get(0).getOpenCode());
        for (int i = 0; i < listNum.size(); i++) {
            List<LotteryTrendBean> arrayList = new ArrayList();
            for (int list = 0; list < bean.size(); list++) {
                List<String> listNums = getListNum(bean.get(list).getOpenCode());
                LotteryTrendBean trend = new LotteryTrendBean();
                StringBuffer sb = new StringBuffer();
                for (int ten = 0; ten < forNum; ten++) {
                    String num = listNums.get(i);
                    if (num.length() == 2) {  // 判断返回数据是不是01类型  是：是删除0
                        if (num.startsWith("0")) {
                            num = num.substring(1);
                        }
                    }
                    if (isPlus) {
                        if (num.equals((ten + 1) + "")) {
                            sb.append(",").append("00");
                        } else {
                            sb.append(",").append(ten + 1);
                        }
                    } else {
                        if (num.equals(ten + "")) {
                            sb.append(",").append("00");
                        } else {
                            sb.append(",").append(ten);
                        }
                    }
                }
                trend.setBlue(sb.toString().substring(1));
                trend.setPid(bean.get(list).getPlanNo());
                arrayList.add(trend);
            }
            trendBean.add(arrayList);
        }
    }

    private List<String> getListNum(String num) {
        String[] array = num.split(",");
        List<String> abcList = new ArrayList<String>();
        for (String str : array) {
            abcList.add(str);
        }
        return abcList;
    }


    @Override
    public int getLayout() {
        return R.layout.f7_lottery;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    @OnClick({R.id.trend_title, R.id.trend_cqssc, R.id.trend_lfssc, R.id.trend_xjssc, R.id.trend_tjssc, R.id.trend_ffssc, R.id.trend_fc3d, R.id.trend_bjpk10, R.id.trend_ffpk10, R.id.trend_jsk3, R.id.trend_gxk3, R.id.trend_ahk3, R.id.trend_ffk3, R.id.trend_bjk3, R.id.trend_xyft, R.id.trend_xysm, R.id.trend_1_type1, R.id.trend_1_type2, R.id.trend_1_type3, R.id.trend_2_type1, R.id.trend_2_type2, R.id.trend_2_type3, R.id.trend_2_type4, R.id.trend_2_type5, R.id.trend_3_type1, R.id.trend_3_type2, R.id.trend_3_type3, R.id.trend_3_type4, R.id.trend_3_type5, R.id.trend_3_type6, R.id.trend_3_type7, R.id.trend_3_type8, R.id.trend_3_type9, R.id.trend_3_type10})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.trend_title:
//                isOpen = !isOpen;
//                setView();
//                break;
//            case R.id.trend_cqssc:
//                trendTitle.setText(trendCqssc.getText().toString());
//                setSelect(view);
//                hist_type = "HF_CQSSC";
//                isPlus = false;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend2Type1);
//                trendLl2.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_lfssc:
//                trendTitle.setText(trendLfssc.getText().toString());
//                setSelect(view);
//                hist_type = "HF_LFSSC";
//                isPlus = false;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend2Type1);
//                trendLl2.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_xjssc:
//                trendTitle.setText(trendXjssc.getText().toString());
//                setSelect(view);
//                hist_type = "HF_XJSSC";
//                isPlus = false;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend2Type1);
//                trendLl2.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_tjssc:
//                trendTitle.setText(trendTjssc.getText().toString());
//                setSelect(view);
//                hist_type = "HF_TJSSC";
//                isPlus = false;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend2Type1);
//                trendLl2.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_ffssc:
//                trendTitle.setText(trendFfssc.getText().toString());
//                setSelect(view);
//                hist_type = "HF_FFSSC";
//                isPlus = false;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend2Type1);
//                trendLl2.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_fc3d:
//                trendTitle.setText(trendFc3d.getText().toString());
//                setSelect(view);
//                hist_type = "X3D";
//                isPlus = false;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend1Type1);
//                trendLl1.setVisibility(View.VISIBLE);
//                trendLl2.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_bjpk10:
//                trendTitle.setText(trendBjpk10.getText().toString());
//                setSelect(view);
//                hist_type = "HF_BJPK10";
//                isPlus = true;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend3Type1);
//                trendLl3.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl2.setVisibility(View.GONE);
//                break;
//            case R.id.trend_ffpk10:
//                trendTitle.setText(trendFfpk10.getText().toString());
//                setSelect(view);
//                hist_type = "HF_FFPK10";
//                isPlus = true;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend3Type1);
//                trendLl3.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl2.setVisibility(View.GONE);
//                break;
//            case R.id.trend_jsk3:
//                trendTitle.setText(trendJsk3.getText().toString());
//                setSelect(view);
//                hist_type = "HF_JSK3";
//                isPlus = true;
//                forNum = 6;
//                topNum = 6;
//                getData(hist_type);
//                setll(trend1Type1);
//                trendLl1.setVisibility(View.VISIBLE);
//                trendLl2.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_gxk3:
//                trendTitle.setText(trendGxk3.getText().toString());
//                setSelect(view);
//                hist_type = "HF_GXK3";
//                isPlus = true;
//                forNum = 6;
//                topNum = 6;
//                getData(hist_type);
//                setll(trend1Type1);
//                trendLl1.setVisibility(View.VISIBLE);
//                trendLl2.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_ahk3:
//                trendTitle.setText(trendAhk3.getText().toString());
//                setSelect(view);
//                hist_type = "HF_AHK3";
//                isPlus = true;
//                forNum = 6;
//                topNum = 6;
//                getData(hist_type);
//                setll(trend1Type1);
//                trendLl1.setVisibility(View.VISIBLE);
//                trendLl2.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_ffk3:
//                trendTitle.setText(trendFfk3.getText().toString());
//                setSelect(view);
//                hist_type = "HF_FFK3";
//                isPlus = true;
//                forNum = 6;
//                topNum = 6;
//                getData(hist_type);
//                setll(trend1Type1);
//                trendLl1.setVisibility(View.VISIBLE);
//                trendLl2.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_bjk3:
//                trendTitle.setText(trendBjk3.getText().toString());
//                setSelect(view);
//                hist_type = "HF_BJK3";
//                isPlus = true;
//                forNum = 6;
//                topNum = 6;
//                getData(hist_type);
//                setll(trend1Type1);
//                trendLl1.setVisibility(View.VISIBLE);
//                trendLl2.setVisibility(View.GONE);
//                trendLl3.setVisibility(View.GONE);
//                break;
//            case R.id.trend_xyft:
//                trendTitle.setText(trendXyft.getText().toString());
//                setSelect(view);
//                hist_type = "HF_XYFT";
//                isPlus = true;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend3Type1);
//                trendLl3.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl2.setVisibility(View.GONE);
//                break;
//            case R.id.trend_xysm:
//                trendTitle.setText(trendXysm.getText().toString());
//                setSelect(view);
//                hist_type = "HF_XYSM";
//                isPlus = true;
//                forNum = 10;
//                topNum = 10;
//                getData(hist_type);
//                setll(trend3Type1);
//                trendLl3.setVisibility(View.VISIBLE);
//                trendLl1.setVisibility(View.GONE);
//                trendLl2.setVisibility(View.GONE);
//                break;
//            case R.id.trend_1_type1:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(0));
//                break;
//            case R.id.trend_1_type2:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(1));
//                break;
//            case R.id.trend_1_type3:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(2));
//                break;
//            case R.id.trend_2_type1:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(0));
//                break;
//            case R.id.trend_2_type2:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(1));
//                break;
//            case R.id.trend_2_type3:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(2));
//                break;
//            case R.id.trend_2_type4:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(3));
//                break;
//            case R.id.trend_2_type5:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(4));
//                break;
//            case R.id.trend_3_type1:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(0));
//                break;
//            case R.id.trend_3_type2:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(1));
//                break;
//            case R.id.trend_3_type3:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(2));
//                break;
//            case R.id.trend_3_type4:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(3));
//                break;
//            case R.id.trend_3_type5:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(4));
//                break;
//            case R.id.trend_3_type6:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(5));
//                break;
//            case R.id.trend_3_type7:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(6));
//                break;
//            case R.id.trend_3_type8:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(7));
//                break;
//            case R.id.trend_3_type9:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(8));
//                break;
//            case R.id.trend_3_type10:
//                setll(view);
//                mTrendChart.updateData(trendBean.get(9));
//                break;
//        }
//    }

    private void setll(View view) {
        binding.trend1Type1.setSelected(false);
        binding.trend1Type2.setSelected(false);
        binding.trend1Type3.setSelected(false);
        binding.trend2Type1.setSelected(false);
        binding.trend2Type2.setSelected(false);
        binding.trend2Type3.setSelected(false);
        binding.trend2Type4.setSelected(false);
        binding.trend2Type5.setSelected(false);
        binding.trend3Type1.setSelected(false);
        binding.trend3Type2.setSelected(false);
        binding.trend3Type3.setSelected(false);
        binding.trend3Type4.setSelected(false);
        binding.trend3Type5.setSelected(false);
        binding.trend3Type6.setSelected(false);
        binding.trend3Type7.setSelected(false);
        binding.trend3Type8.setSelected(false);
        binding.trend3Type9.setSelected(false);
        binding.trend3Type10.setSelected(false);
        view.setSelected(true);
    }

    private void setSelect(View view) {
        binding.trendCqssc.setSelected(false);
        binding.trendLfssc.setSelected(false);
        binding.trendXjssc.setSelected(false);
        binding.trendTjssc.setSelected(false);
        binding.trendFfssc.setSelected(false);
        binding.trendFc3d.setSelected(false);
        binding.trendBjpk10.setSelected(false);
        binding.trendFfpk10.setSelected(false);
        binding.trendJsk3.setSelected(false);
        binding.trendGxk3.setSelected(false);
        binding.trendAhk3.setSelected(false);
        binding.trendFfk3.setSelected(false);
        binding.trendBjk3.setSelected(false);
        binding.trendXyft.setSelected(false);
        binding.trendXysm.setSelected(false);
        view.setSelected(true);
        isOpen = false;
        setView();
    }

    @Override
    public void onStop() {
        super.onStop();
        isOpen = false;
        setView();
    }

    @Override
    public void onClick(View v) {

    }

    private DrawerLayout drawerLayout;

    public LotteryF7 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
