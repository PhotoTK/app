package com.cs495.phototk.ui.celestial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context, "celestial.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table celestial (id integer primary key, time char(30), location char(30), pathWidth integer, centralDuration char(30))";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println(newVersion);
        String sql1 = "DROP TABLE IF EXISTS celestial";
        db.execSQL(sql1);
        onCreate(db);
    }
}