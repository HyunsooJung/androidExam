package com.android.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.android.R;

public class NotPadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noactivity_main);

        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(NotPadActivity.this, "Note.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

    }

}