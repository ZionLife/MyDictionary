package com.zionlife.mydictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zionlife.mydictionary.utils.Utils;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class DbHelper extends SQLiteOpenHelper{
    private static DbHelper dbHelper = null;

    public static DbHelper getInstance(Context context){
        if(dbHelper ==null){
            dbHelper = new DbHelper(context);
        }
        return dbHelper;
    }

    private DbHelper(Context context){
        super(context, Utils.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +Utils.TABLE_NAME  + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "classData TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
