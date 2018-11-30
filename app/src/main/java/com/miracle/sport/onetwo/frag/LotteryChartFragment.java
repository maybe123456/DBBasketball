package com.miracle.sport.onetwo.frag;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.sport.onetwo.lotterychart.data.TrendData;
import com.miracle.sport.onetwo.lotterychart.ui.DDTrendChart;
import com.miracle.sport.onetwo.lotterychart.ui.LottoTrendView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//FragmentLottoTrendBinding
public class LotteryChartFragment extends BaseFragment implements DDTrendChart.ISelectedChangeListener {


    private LottoTrendView mTrendView;
    final int maxSignleNum = 9;
    private DDTrendChart mTrendChart;
    Handler uiHandler = new Handler();

    @Override
    public int getLayout() {
        return R.layout.fragment_lotto_trend;
    }

    @Override
    public void initView() {
        setShowTitle(true);
        setTitle("走势图");
        initViews();
        loadData();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            mTrendChart.updateData("01", (ArrayList) paramMessage.obj);
        }
    };

    private void initViews() {
        this.mTrendView = (LottoTrendView) binding.getRoot().findViewById(R.id.ltv_trendView);
        this.mTrendChart = new DDTrendChart(mContext, this.mTrendView);
        this.mTrendView.setChart(this.mTrendChart);
        this.mTrendChart.setShowYilou(true);
        this.mTrendChart.setDrawLine(true);
        this.mTrendChart.setSelectedChangeListener(this);
    }

    private final OkHttpClient client = new OkHttpClient();

    public void loadData() {
//        setShowProgress(true);
        // 根据01/30.xml 或者是01/50.xm可以调整数字
        String url = "http://mobile.9188.com/data/app/zst/01/30.xml";
//       try{
//           HttpUrl.parse(url).
//           InputStream inputStream = getAssets().open("lotdata.xml");
//           try {
//               setParser(inputStream);
//           } catch (XmlPullParserException e) {
//               e.printStackTrace();
//           } finally {
//               if (inputStream != null) {
//                   inputStream.close();
//               }
//           }
//       }catch (Exception e){
//           e.printStackTrace();
//       }

        setUIStatus(ShowStat.LOADING);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println();
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setUIStatus(ShowStat.ERR);
//                        setShowProgress(false);
//                        ToastUtil.toast("加载数据失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                try {
                    setParser(inputStream);
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setUIStatus(ShowStat.NORMAL);
//                        setShowProgress(false);
                        }
                    });
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setUIStatus(ShowStat.ERR);
                        }
                    });
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        });
    }

    /**
     * @param 解析
     */
    protected void setParser(InputStream inputStream) throws XmlPullParserException, IOException {
        ArrayList<TrendData> arrayList = new ArrayList();
        Collection<TrendData> arrayList2 = new ArrayList();
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
//        TrendData trendData = new TrendData();
//        trendData.setType("row");
//        trendData.setPid("1111");
//        trendData.setRed("0,1,9,2,0,3,1,3,4,0,13,3,7,5,8,0,1,0,15,5,1,3,1,3,2,1,13,22,7,2,0,4,2");
//        trendData.setBlue("19,38,0,36,20,2,26,15,5,10,12,9,8,35,52,1");
//        arrayList.add(trendData);

//        TrendData trendData2 = new TrendData();
//        trendData2.setPid("2222");
//        trendData2.setRed("19,38,0,36,20,2,26,15,5,10,12,9,8,35,52,1");
//        trendData2.setBlue("19,38,0,36,20,2,26,15,5,10");
//        arrayList.add(trendData2);

        arrayList.addAll(arrayList2);
        mHandler.sendMessage(mHandler.obtainMessage(120, arrayList));
    }


    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSelectedChange(TreeSet<Integer> treeSet, TreeSet<Integer> treeSet2) {

    }
}
