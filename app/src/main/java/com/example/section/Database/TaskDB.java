package com.example.section.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.section.Task;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class TaskDB extends SQLiteAssetHelper {

    private static final String NAME="task.db";
    private static final String TABLE="task";
    private static final String ID="id";
    private static final String TITLE="title";
    private static final String Desc="desc";
    private static final String DATE="date";
    private static final String CHECKED="state";
    private  static final int VERSION =1;

    public TaskDB(Context context) {
        super(context, NAME, null, null, VERSION);
    }
    public Boolean insert(Task task){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        task.setChecked(0);
        cv.put(TITLE,task.getTitle());
        cv.put(Desc,task.getDesc());
        cv.put(DATE,task.getDate());
        cv.put(CHECKED,task.getChecked());
        return database.insert(TABLE,null,cv)>-1;
    }
    @SuppressLint("Range")
    public ArrayList<Task> getTasks(){
        ArrayList<Task> arrayList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("select * from "+TABLE+"",null);
        if (c.moveToFirst()){
            do {
                arrayList.add(new Task(c.getInt(c.getColumnIndex(ID)),c.getInt(c.getColumnIndex(CHECKED)),c.getString(c.getColumnIndex(TITLE)),
                                       c.getString(c.getColumnIndex(DATE)),c.getString(c.getColumnIndex(Desc)) ));
            }while (c.moveToNext());
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public Task get_task(int id){
        Task task = new Task();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor= database.rawQuery("select * from "+TABLE+" where "+ID+"=?",new String[]{id+""});
        if (cursor.moveToFirst()){
            task = new Task(cursor.getString(cursor.getColumnIndex(TITLE)), cursor.getString(cursor.getColumnIndex(DATE)), cursor.getString(cursor.getColumnIndex(Desc)));
        }
        return task;
    }
    public Boolean Modify(Task task){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE,task.getTitle());
        contentValues.put(DATE,task.getDate());
        contentValues.put(Desc,task.getDesc());
        contentValues.put(CHECKED,task.getChecked());
        return database.update(TABLE,contentValues,""+ID+"=?",new String[]{task.getId()+""})>0;
    }
    public void Delete(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE,""+ID+"=?",new String[]{id+""});
    }
}
