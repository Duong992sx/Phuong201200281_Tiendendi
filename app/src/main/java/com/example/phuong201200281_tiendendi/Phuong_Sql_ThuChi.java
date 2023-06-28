package com.example.phuong201200281_tiendendi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Phuong_Sql_ThuChi extends SQLiteOpenHelper {

  public static  final String TableName = "Broad_Phuong";
    public  static final String Id = "Id";
    public  static final String tennguoi = "tennguoi";
    public  static final String ngaythang = "date";
      public  static final String noidung = "noidung";
    public  static final String sotien = "sotien";
    public  static final String phuchi = "phuchi";
    public Phuong_Sql_ThuChi(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "Create table if not exists " + TableName + "(" + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tennguoi + " Text, "
                + ngaythang + " Text, "
                 + noidung + " Text, "
                + sotien + " bit, "
                + phuchi + " Text)";
        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }
    public ArrayList<ThuChi> GetAllContact(){
        ArrayList<ThuChi> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                ThuChi contact = new ThuChi(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
                list.add(contact);
            }
        }
        return list;
    }
    public void addContact(ThuChi contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tennguoi,contact.getTennguoi());
        values.put(noidung,contact.getTennguoi());
        values.put(ngaythang,contact.getNgaythang());
        values.put(sotien,contact.getSotien());
        if(contact.isPhuchi()){
            values.put(phuchi,1);
        }else {
            values.put(phuchi,0);
        }
        db.insert(TableName,null,values);
        db.close();
    }
    public void UpdateContact(int id, ThuChi contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id,contact.getId());
        values.put(tennguoi,contact.getTennguoi());
        values.put(noidung,contact.getTennguoi());
        values.put(ngaythang,contact.getNgaythang());
        values.put(sotien,contact.getSotien());
        if(contact.isPhuchi()){
            values.put(phuchi,1);
        }else {
            values.put(phuchi,0);
        }
        db.update(TableName,values, Id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from " + TableName + " Where ID=" + id;
        db.execSQL(sql);
        db.close();
    }
}