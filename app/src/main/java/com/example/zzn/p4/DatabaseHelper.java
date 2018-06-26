package com.example.zzn.p4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zzn on 2018/6/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "test", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists zzncost(id integer primary key, costTitle varchar,costDate varchar,costMoney varchar)");
    }

    public void insertCost(CostBean costBean) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("costTitle", costBean.costTitle);
        cv.put("costDate", costBean.costDate);
        cv.put("costMoney", costBean.costMoney);
        db.insert("zzncost", null, cv);
    }

    public Cursor getAllCostData() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query("zzncost", null, null, null, null, null, "costDate asc", null);
    }

    public void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("zzncost",null,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
