package com.miracle.base.util.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.miracle.base.App;
import com.miracle.base.util.Encryptor;

/**
 * Created by Administrator on 2017/6/9.
 */

public class SQLiteUtil {

    /**
     * 保存String数据
     *
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new SQLiteHelper(App.getApp()).getReadableDatabase();
            cursor = db.rawQuery("select * from temp where key=? ", new String[]{key});
            ContentValues contentValues = new ContentValues();
            contentValues.put("key", key);
            contentValues.put("value", value);
            if (cursor.moveToFirst()) {
                db.update("temp", contentValues, "key=?", new String[]{key});
            } else {
                db.insert("temp", null, contentValues);
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    /**
     * 获取String数据
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new SQLiteHelper(App.getApp()).getReadableDatabase();
            cursor = db.rawQuery("select * from temp where key=? ", new String[]{key});
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex("value"));
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return null;
    }

    /**
     * 保存加密后的String
     *
     * @param key
     * @param value
     */
    public static void saveEncryptedString(String key, String value) {
        saveString(key, Encryptor.encryptString(value));
    }

    /**
     * 获取加密String，并解密返回
     *
     * @param key
     * @return
     */
    public static String getEncryptedString(String key) {
        return Encryptor.decryptString(getString(key));
    }

    /**
     * 保存布尔类型数据
     *
     * @param key
     * @param value
     */
    public static void saveBoolean(String key, boolean value) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new SQLiteHelper(App.getApp()).getReadableDatabase();
            cursor = db.rawQuery("select * from temp where key=? ", new String[]{key});
            ContentValues contentValues = new ContentValues();
            contentValues.put("key", key);
            contentValues.put("value", value);
            if (cursor.moveToFirst()) {
                db.update("temp", contentValues, "key=?", new String[]{key});
            } else {
                db.insert("temp", null, contentValues);
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    /**
     * 获取布尔类型数据
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new SQLiteHelper(App.getApp()).getReadableDatabase();
            cursor = db.rawQuery("select * from temp where key=? ", new String[]{key});
            if (cursor.moveToFirst()) {
                return "1".equals(cursor.getString(cursor.getColumnIndex("value")));
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return false;
    }
}
