package com.cs495.phototk.ui.celestial;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String name = "celestial";
    private static final int factory = 0;

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table celestial (id integer primary key, time char(30), location char(30), pathWidth integer, centralDuration char(30))";
        db.execSQL(sql);
        //DataBase helper = new DataBase(, "celestial", null, 1);
        //SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        ContentValues val = new ContentValues();
        val.put("id","120191226");
        val.put("time","2019/December/26 05:18:53");
        val.put("location","1.0°N 102.3°E");
        val.put("pathWidth",73);
        val.put("centralDuration","3:40");
        db.insert("celestial","null",val);

        ContentValues val1 = new ContentValues();
        val1.put("id","120200621");
        val1.put("time","2020/June/21 06:41:15");
        val1.put("location","30.5°N 79.7°E");
        val1.put("pathWidth",13);
        val1.put("centralDuration","0:38");
        db.insert("celestial","null",val1);

        ContentValues val2 = new ContentValues();
        val2.put("id","120201214");
        val2.put("time","2020/December/14 16:14:39");
        val2.put("location","40.3°S 67.9°W");
        val2.put("pathWidth",56);
        val2.put("centralDuration","2:10");
        db.insert("celestial","null",val2);

        ContentValues val3 = new ContentValues();
        val3.put("id","120210610");
        val3.put("time","2021/June/10 10:43:07");
        val3.put("location","80.8°N 66.8°W");
        val3.put("pathWidth",327);
        val3.put("centralDuration","3:51");
        db.insert("celestial","null",val3);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println(newVersion);
        String sql1 = "DROP TABLE IF EXISTS celestial";
        db.execSQL(sql1);
        onCreate(db);
    }
}