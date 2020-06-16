package com.swufe.reader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelpter extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "mybook.db";
    public static final String TB_NAME = "reader";
    public DBHelpter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelpter(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+"(Reader_Name VARCHAR(20) PRIMARY KEY,Password VARCHAR(20),Books TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
