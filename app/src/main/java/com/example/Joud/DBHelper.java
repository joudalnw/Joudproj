package com.example.Joud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(fname TEXT,lname TEXT,email TEXT, phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    public Boolean insertuserdata(String fname,String lname ,String email,String phone)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname", fname);
        contentValues.put("lname", lname);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        long result=DB.insert("Userdetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }


    public Boolean updateuserdata(String fname,String lname ,String email,String phone) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname", fname);
        contentValues.put("lname", lname);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where phone = ?", new String[]{phone});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "phone=?", new String[]{phone});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}


    public Boolean deletedata (String phone)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where phone = ?", new String[]{phone});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "phone=?", new String[]{phone});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;

    }
}