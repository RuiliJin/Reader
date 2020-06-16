package com.swufe.reader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReaderManager {
    private DBHelpter dbHelpter;
    private String TBNAME;
    private String TAG = "ReaderManager";

    public ReaderManager(Context context){
        dbHelpter = new DBHelpter(context);
        TBNAME = DBHelpter.TB_NAME;
    }
    public void register(ReaderItem item){
        SQLiteDatabase db = dbHelpter.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Reader_Name",item.getReader_Name());
        values.put("Password",item.getPassword());

        db.insert(TBNAME,null,values);
        db.close();
    }
    public boolean login(String readerName,String password){
        SQLiteDatabase db = dbHelpter.getReadableDatabase();
        String sql="select * from "+TBNAME+" where Reader_Name=? and Password=?";
        Cursor cursor=db.rawQuery(sql, new String[]{readerName,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    public boolean query(String readerName){
        SQLiteDatabase db = dbHelpter.getReadableDatabase();
        String sql="select * from "+TBNAME+" where Reader_Name=?";
        Cursor cursor=db.rawQuery(sql, new String[]{readerName});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    public String queryBooks(String readerName){
        SQLiteDatabase db = dbHelpter.getReadableDatabase();
        String sql="select * from "+TBNAME+" where Reader_Name=?";
        Cursor cursor=db.rawQuery(sql, new String[]{readerName});
        String bookstr=" ";
        if(cursor.moveToFirst()==true){
            bookstr = cursor.getString(cursor.getColumnIndex("Books"));
            cursor.close();

        }
        if(bookstr==null){
            return "我的书单： ";
        }
        else{return bookstr;}
    }
    public void updateBook(String readerName,String books){
        SQLiteDatabase db = dbHelpter.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Books",books);

        db.update(TBNAME,values,"Reader_Name=?",new String[]{readerName});
        Log.i(TAG,"更新数据成功");
        db.close();
    }
}
