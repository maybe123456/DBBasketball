package com.miracle.sport.onetwo.operation;

import android.content.ContentValues;

import com.miracle.sport.onetwo.entity.CollecableAbs;
import com.miracle.sport.onetwo.netbean.CpTitleItem;
import com.miracle.sport.onetwo.util.LitePalUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
    /**
     * @param newDatas
     * @param outMcList        收藏的
     * @param outMoList        沒收藏的
     * @param processCollected false=网上新拉取的数据(newDatas,不考虑LOCAL_isCollected字段)，true 本地合并，考虑 LOCAL_isCollected 字段
     */
    public static void meargData(List<CpTitleItem> newDatas, List<CpTitleItem> outMcList, List<CpTitleItem> outMoList, boolean processCollected) {
        //数据规整及保存
        if (!LitePalUtil.isTableExist(CpTitleItem.class)) {
            DataSupport.saveAll(newDatas);//1.本地没有 直接存
            if (outMcList != null)
                outMcList.addAll(newDatas);
        } else {
            List<CpTitleItem> needAdd = new ArrayList<CpTitleItem>();
            for (CpTitleItem item : newDatas) {//2.更新本地已有的(根据type字段)
                ContentValues cv = new ContentValues();
                cv.put("name", item.getName());
                if (processCollected)
                    cv.put("LOCAL_isCollected", item.isLOCAL_isCollected());
                int count = DataSupport.updateAll(CpTitleItem.class, cv, "type = ?",item.getType());
                if (count > 0) {
                    List<CpTitleItem> findedList = DataSupport.where("type = ?", item.getType()).find(CpTitleItem.class);
                    if (findedList.size() > 0)
                        if (findedList.get(0).isLOCAL_isCollected()) {
                            if (outMcList != null)
                                outMcList.add(findedList.get(0));
                        } else {
                            if (outMoList != null)
                                outMoList.add(findedList.get(0));
                        }
                    continue; //本地存在
                }
                //3.插入本地没有的数据
                needAdd.add(item);
                if (outMcList != null)
                    outMcList.add(item);
            }
            DataSupport.saveAll(needAdd);
        }
    }


    public static void meargData(List<CpTitleItem> newDatas, List<CpTitleItem> outMcList, List<CpTitleItem> outMoList) {
        meargData(newDatas, outMcList, outMoList, false);
    }

    public static <T extends CollecableAbs> void meargCollecableAbsData(Class<T> clazz , List<T> newDatas, List<T> outMcList, List<T> outMoList) {
        meargCollecableAbsData(clazz, newDatas, outMcList, outMoList, false);
    }

    /**
     * @param newDatas
     * @param outMcList        收藏的
     * @param outMoList        沒收藏的
     * @param processCollected false=网上新拉取的数据(newDatas,不考虑LOCAL_isCollected字段)，true 本地合并，考虑 LOCAL_isCollected 字段
     */
    public static <T extends CollecableAbs> void meargCollecableAbsData(Class<T> clazz, List<T> newDatas, List<T> outMcList, List<T> outMoList, boolean processCollected) {
        //数据规整及保存
        if (!LitePalUtil.isTableExist(clazz)) {
            DataSupport.saveAll(newDatas);//1.本地没有 直接存
            if (outMcList != null)
                outMcList.addAll(newDatas);
        } else {
            List<T> needAdd = new ArrayList<T>();
            for (T item : newDatas) {//2.更新本地已有的(根据type字段)
                ContentValues cv = new ContentValues();
                cv.put("name", item.getName());
                if (processCollected)
                    cv.put("LOCAL_isCollected", item.isLOCAL_isCollected());
                int count = DataSupport.updateAll(clazz, cv, "key = ?",item.getKey());
                if (count > 0) {
                    List<T> findedList = DataSupport.where("key = ?", item.getKey()).find(clazz);
                    if (findedList.size() > 0)
                        if (findedList.get(0).isLOCAL_isCollected()) {
                            if (outMcList != null)
                                outMcList.add(findedList.get(0));
                        } else {
                            if (outMoList != null)
                                outMoList.add(findedList.get(0));
                        }
                    continue; //本地存在
                }
                //3.插入本地没有的数据
                needAdd.add(item);
                if (outMcList != null)
                    outMcList.add(item);
            }
            DataSupport.saveAll(needAdd);
        }
    }


}
