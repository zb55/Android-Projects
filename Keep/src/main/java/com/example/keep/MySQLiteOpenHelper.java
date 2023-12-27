package com.example.keep;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Keep.db";
    private static final String TABLE_NAME = "Users";
    private static final String CREATE = "create table " + TABLE_NAME + "(number text, password text, course text)";



    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @SuppressLint("Range")
    public int login(User user){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cs = db.query(TABLE_NAME,null, "number like ?", new String[]{user.getNumber()}, null, null, null);
        if(cs!=null){
            while(cs.moveToNext()){
                String passwordHaved = cs.getString(cs.getColumnIndex("password"));
                if(passwordHaved.equals(user.getPassword())){
                    return Result.isSuccessful;
                }else{
                    return Result.isFailed;
                }
            }
            cs.close();
        }
        return Result.isFailed;

    }
    @SuppressLint("Range")
    public int register(User user){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cs = db.query(TABLE_NAME,new String[]{"number"}, "number = ?", new String[]{user.getNumber()}, null, null, null);
        if(cs!=null && cs.getCount() > 0){
            cs.close();
            return Result.isHaved;
        }
        ContentValues cv = new ContentValues();
        cv.put("number", user.getNumber());
        cv.put("password", user.getPassword());
        cv.put("course", "");

        long isOk = db.insert(TABLE_NAME, null, cv);

        if(isOk>=0){
            return Result.isSuccessful;
        }
        return Result.isFailed;
    }
    @SuppressLint("Range")
    public void addCourse(String number, Course course) {
        String password = "";
        String courseNew = "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cs = db.query(TABLE_NAME, null, "number = ?", new String[]{number}, null, null, null);
        if (cs != null) {
            if (cs.moveToFirst()) {
                password = cs.getString(cs.getColumnIndex("password"));
                courseNew = cs.getString(cs.getColumnIndex("course"));
                if (courseNew != null && !courseNew.isEmpty()) {
                    courseNew += course.toString();
                } else {
                    courseNew = course.toString();
                }

                ContentValues cv = new ContentValues();
                cv.put("number", number);
                cv.put("password", password);
                cv.put("course", courseNew);
                db.update(TABLE_NAME, cv, "number = ?", new String[]{number});
            }
            cs.close();
        }
    }
    public void deleteCourse(String number, Course course) {
        String password = "";
        String courseMassage = "";
        String courseNew = "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cs = db.query(TABLE_NAME, null, "number = ?", new String[]{number}, null, null, null);
        if (cs != null) {
            if (cs.moveToFirst()) {
                password = cs.getString(cs.getColumnIndex("password"));
                courseMassage = cs.getString(cs.getColumnIndex("course"));
                courseNew = courseMassage.replace(course.toString(), "");
                ContentValues cv = new ContentValues();
                cv.put("number", number);
                cv.put("password", password);
                cv.put("course", courseNew);
                db.update(TABLE_NAME, cv, "number = ?", new String[]{number});
            }
            cs.close();
        }
    }
    @SuppressLint("Range")
    public String getCourse(String number){
        String courseMassage = new String();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.query(TABLE_NAME, null, "number = ?", new String[]{number}, null, null, null);
        if (cs != null) {
            if(cs.moveToFirst()){

                courseMassage = cs.getString(cs.getColumnIndex("course"));
                cs.close();
            }

        }
        return courseMassage;
    }
    @SuppressLint("Range")
    public Boolean courseExist(String number, Course course){
        SQLiteDatabase db = getReadableDatabase();
        String courseMassages = new String();
        Cursor cs = db.query(TABLE_NAME, null, "number = ?", new String[]{number}, null, null, null);
        if(cs != null){
            while(cs.moveToNext()){
                courseMassages = cs.getString(cs.getColumnIndex("course"));
                cs.close();
            }
        }
        String[] cm = courseMassages.split(";");
        for (int i = 1; i < cm.length; i+=3) {
            if (cm[i].equals(course.getName()))
                return true;
        }
        return false;
    }
}
