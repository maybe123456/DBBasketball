package com.miracle.sport.onetwo.netbean;

import com.miracle.sport.onetwo.entity.CollecableAbs;

import java.util.Arrays;
import java.util.Objects;

public class CpTitleItem extends CollecableAbs {

    /**
     * type : fc3d
     * name : 福彩3D
     */

    private String type;
    private String name;
    private boolean LOCAL_isCollected = true;
    public boolean isLOCAL_isCollected() {
        return LOCAL_isCollected;
    }
    private String key;


    @Override
    public void setKey(String key) {
        this.key = key;
        this.type = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setLOCAL_isCollected(boolean LOCAL_isCollected) {
        this.LOCAL_isCollected = LOCAL_isCollected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.key = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CpTitleItem that = (CpTitleItem) o;
        return LOCAL_isCollected == that.LOCAL_isCollected &&
                equals(type, that.type) &&
                equals(name, that.name) &&
                equals(key, that.key);
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    @Override
    public int hashCode() {
        int result = 1;

        Object[] a = new Object[]{type,name,LOCAL_isCollected,key};
        for (Object element : a)
            result = 31 * result + (element == null ? 0 : element.hashCode());

        return result;
    }
}
