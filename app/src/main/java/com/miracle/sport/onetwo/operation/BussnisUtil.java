package com.miracle.sport.onetwo.operation;

import android.os.Handler;
import android.util.Log;

import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.sport.onetwo.netbean.CPServer;
import com.miracle.sport.onetwo.netbean.CpListItem;
import com.miracle.sport.onetwo.netbean.CpTitleItem;
import com.miracle.sport.onetwo.netbean.LotteryCatListItem;
import com.miracle.sport.onetwo.netbean.LotteryCatTitleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;

public class BussnisUtil {
    public static void getAllNewNumber(final Handler handler, final int what){
        final List<LotteryCatListItem> list = new ArrayList<>();
        Log.i("TAGXXXXX","XXXXX0 " + Thread.currentThread().toString());
        ZClient.getService(CPServer.class).lotteryCategory().enqueue(new ZCallback<ZResponse<List<LotteryCatTitleItem>>>() {
            @Override
            public void onSuccess(ZResponse<List<LotteryCatTitleItem>> data) {
                Log.i("TAGXXXXX","XXXXX1 " + Thread.currentThread().toString());
                //数据规整及保存
                List<LotteryCatTitleItem> mcList = data.getData();

                final AtomicInteger atomicInteger = new AtomicInteger(mcList.size());
                for(LotteryCatTitleItem cpTitleItem : mcList){
                    ZClient.getService(CPServer.class).lotteryCategoryList(1, 1, cpTitleItem.getId()).enqueue(new ZCallback<ZResponse<List<LotteryCatListItem>>>() {
                        @Override
                        protected void onSuccess(ZResponse<List<LotteryCatListItem>> zResponse) {
                            list.add(zResponse.getData().get(0));
                        }

                        @Override
                        protected void onFinish(Call<ZResponse<List<LotteryCatListItem>>> call) {
                            super.onFinish(call);
                            if(atomicInteger.decrementAndGet() == 0)
                                handler.obtainMessage(what, list).sendToTarget();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ZResponse<List<LotteryCatTitleItem>>> call, Throwable t) {
                super.onFailure(call, t);
                handler.sendEmptyMessage(what);
            }

        });
        Log.i("TAGXXXXX","XXXXX2 " + Thread.currentThread().toString());
    }
}
