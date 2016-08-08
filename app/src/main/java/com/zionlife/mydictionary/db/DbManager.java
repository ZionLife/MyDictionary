package com.zionlife.mydictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.zionlife.mydictionary.bean.ReturnInfo;
import com.zionlife.mydictionary.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class DbManager {
    DbHelper dh = null;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        dh = DbHelper.getInstance(context);
    }

    public boolean add(ReturnInfo ri) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            db = dh.getWritableDatabase();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(ri);
            oos.flush();
            byte[] data = bos.toByteArray();
            bos.close();
            oos.close();
            String sql = "INSERT OR IGNORE INTO mClass (classData) VALUES(?)";
            SQLiteStatement insertStmt = db.compileStatement(sql);
            insertStmt.bindBlob(1, data);
            insertStmt.executeInsert();
            db.close();
            Log.e("db", "insert succeeded");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("db", "insert failed");
            return false;
        }
    }

    public List<ReturnInfo> getAll(){
        List<ReturnInfo> riList = new ArrayList<ReturnInfo>();
        ReturnInfo ri = null;
        db = dh.getWritableDatabase();
        Cursor cursor = db.rawQuery("select *from " +Utils.TABLE_NAME, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                byte[] data = cursor.getBlob(cursor.getColumnIndex("classData"));
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    riList.add((ReturnInfo) ois.readObject());
                    bis.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        cursor.close();
        return riList;
    }
}
