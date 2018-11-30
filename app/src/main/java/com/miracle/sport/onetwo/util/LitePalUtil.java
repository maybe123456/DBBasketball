package com.miracle.sport.onetwo.util;

import org.litepal.LitePal;
import org.litepal.util.BaseUtility;
import org.litepal.util.DBUtility;

public class LitePalUtil {
    public static boolean isTableExist(Class clz){
        return DBUtility.isTableExists(BaseUtility.changeCase(DBUtility.getTableNameByClassName(clz.getName())), LitePal.getDatabase());
    }
}
