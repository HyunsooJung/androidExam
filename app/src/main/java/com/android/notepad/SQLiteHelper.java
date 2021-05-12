package com.android.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteHelper {

    private static final String DATABASE_NAME = "Note";
    private static final String TABLE_NAME = "note_table";
    private static final int DBVERSION = 1;

    private OpenHelper openHelper;
    private SQLiteDatabase db;

    private Context context;

    public SQLiteHelper(Context context){
        this.context = context;
        this.openHelper = new OpenHelper(context, DATABASE_NAME, null, DBVERSION);
        db = openHelper.getWritableDatabase();
    }

    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String create = "CREATE TABLE " + TABLE_NAME + "("+
                    "seq integer PRIMARY KEY AUTOINCREMENT, "+
                    "mtitle text,"+
                    "mcontent text,"+
                    "mdate text,"+
                    "isdone integer)";
            db.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    //메모작성
    public void InsertMemo(Memo memo){
        String sql = "INSERT INTO "+TABLE_NAME+" VALUES(NULL, '"+memo.mtitle+"','"+memo.mcontent+"','"+memo.mdate+"',"+memo.getIsdone()+");";
        db.execSQL(sql);
    }

    //메모삭제
    public void deleteMemo(int position){
        String sql = "DELETE FROM "+TABLE_NAME+" WHERE seq = "+position+";";
        db.execSQL(sql);
    }

    //메모업데이트
    public void updateMemo(Memo memo){
        String sql = "UPDATE "+TABLE_NAME+" SET mtitle = '"+memo.mtitle+"',mcontent = '"+memo.mcontent+"',mdate = '"+memo.mdate+"' WHERE seq = '"+memo.seq+"';";
        db.execSQL(sql);
    }

    //메모리스트
    public ArrayList<Memo> selectAll(){
        String sql = "SELECT * FROM " +TABLE_NAME;

        ArrayList<Memo> list = new ArrayList<>();

        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();

        while(!result.isAfterLast()){

            Memo memo = new Memo(result.getInt(0), result.getString(1), result.getString(2), result.getString(3), result.getInt(4));
            list.add(memo);
            result.moveToNext();
        }
        result.close();

        return list;
    }

    //메모한거 하나
    public void selectOne(int position){
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE seq = "+position+";";
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            int seq = result.getInt(0);
            String mtitle = result.getString(1);
            String mcontent = result.getString(2);
            String mdate = result.getString(3);
            result.moveToNext();
        }
        result.close();
    }

    //검색
    public ArrayList<Memo> search(String text){
        String sql = "SELECT * FROM "+TABLE_NAME+ " WHERE mtitle "+"LIKE '%"+text+"%'"+" OR mcontent "+"LIKE '%"+text+"%'";
        ArrayList<Memo> list = new ArrayList<>();

        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        while(!result.isAfterLast()){
            Memo memo = new Memo(result.getInt(0), result.getString(1), result.getString(2), result.getString(3), result.getInt(4));
            list.add(memo);
            Log.v("list : ","list: "+list.toString());
            result.moveToNext();
        }
        result.close();

        return list;
    }
}
