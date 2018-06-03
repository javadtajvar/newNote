package com.example.developer.newnote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Developer on 4/18/2018.
 */

public class MyDB extends SQLiteOpenHelper {
    public MyDB(Context context) {
        super(context, "tbl_notesssse", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE tbl_note(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , title NVARCHAR , desc NVARCHAR ,date VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("CREATE TABLE tbl_note(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , title NVARCHAR , desc NVARCHAR ,date VARCHAR)");

    }
}
