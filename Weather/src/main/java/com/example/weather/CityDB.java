package com.example.weather;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyCity.db";
    private static final String TABLE_NAME = "cities";
    private static final String DB_CREATE = "create table cities(name text)";
    public CityDB(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long addCity(String city){
        if(queryOne((city)) == 0){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("name", city);

            return db.insert(TABLE_NAME,null, cv);
        }
        return 0;


    }
    public void deleteCities(List<String> cities){
        SQLiteDatabase db = getWritableDatabase();
        for (String city: cities) {
            if(queryOne(city) == 1){
                db.delete(TABLE_NAME, "name = ?", new String[]{city});
            }
        }
    }
    public String getFirstCity(){
        String city = "";
        List<String> lc = queryAll();
        String[] c = lc.get(0).split(" ");
        city = c[c.length-1];
        return city;
    }
    public long queryOne(String city){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.query(TABLE_NAME, null, "name = ?", new String[]{city}, null, null, null);
        if(cs!=null && cs.getCount()==1){
            return 1;
        }else{
            return 0;
        }
    }
    @SuppressLint("Range")
    public List<String> queryAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.query(TABLE_NAME, new String[]{"name"}, null, null, null, null, null);
        List<String> result = new ArrayList<>();
        if(cs!=null&&cs.getCount()>0) {
            while (cs.moveToNext()) {
                result.add(cs.getString(cs.getColumnIndex("name")));
            }
            cs.close();
            return result;
        }
        return null;
    }
//    @SuppressLint("Range")
//    public List<Map<String, String>> queryAllWithCheck(){
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cs = db.query(TABLE_NAME, new String[]{"name", "checked"}, null, null, null, null, null);
//        List<Map<String, String>> result = new ArrayList<>();
//        if(cs!=null&&cs.getCount()>0) {
//            while (cs.moveToNext()) {
//                Map map = new HashMap();
//                map.put(cs.getString(cs.getColumnIndex("name")), cs.getString(cs.getColumnIndex("checked")));
//                result.add(map);
//            }
//            cs.close();
//            return result;
//        }
//        return null;
//    }
//    public void setCheck(String ){
//
//    }
}
