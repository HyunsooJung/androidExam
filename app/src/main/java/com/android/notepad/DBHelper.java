package com.android.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Note";
    public static final String TABLE_NAME = "note_table";
    public static final String COL_1 = "TITLE";
    public static final String COL_2 = "CONTENT";
    public static final String COL_3 = "DATE";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(TITLE TEXT PRIMARY KEY, CONTENT TEXT, DATE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //데이터 생성
    public boolean insertData (String title, String content, String date){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, title);
        contentValues.put(COL_2, content);
        contentValues.put(COL_3, date);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //데이터 삭제
    public int deleteData(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "TITLE = ?", new String[] { title });
    }

    //업데이트
    public boolean updateData(String title, String content, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, title);
        contentValues.put(COL_2, content);
        contentValues.put(COL_3, date);
        db.update(TABLE_NAME, contentValues, "TITLE = ?", new String[] {title});
        return true;
    }

    //리스트
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

}
