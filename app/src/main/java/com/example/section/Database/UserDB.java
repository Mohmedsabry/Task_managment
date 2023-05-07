package com.example.section.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class UserDB extends SQLiteAssetHelper {
    public static final String NAME ="task.db";
    public static final String USERNAME ="username";
    public static final String PASSWORD ="password";
    public static final String TABEL ="user";
    public static final int VERSION = 1;

    public UserDB(Context context) {
        super(context, NAME, null,null, VERSION);
    }
    public  Boolean insert(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME,user.getUsername());
        cv.put(PASSWORD,user.getPassword());
        return sqLiteDatabase.insert(TABEL,null,cv)>-1;
    }
    @SuppressLint("Range")
    public ArrayList<User> getUsers(){
        ArrayList<User>arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABEL+"",null);
        if (cursor.moveToFirst()){
            do {
                arrayList.add(new User(cursor.getString(cursor.getColumnIndex(USERNAME)), cursor.getString(cursor.getColumnIndex(PASSWORD)) ));
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
}
