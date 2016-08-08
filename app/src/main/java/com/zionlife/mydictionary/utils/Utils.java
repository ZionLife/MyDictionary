package com.zionlife.mydictionary.utils;


import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zionlife.mydictionary.MyApplication;
import com.zionlife.mydictionary.bean.ReturnInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/31 0031.
 */
public class Utils {

    public static String DB_NAME = "myClass.db";
    public static String TABLE_NAME = "mClass";
    public Context context;
    public Handler handler;
    public Utils(Context context, Handler handler){
        this.context = context;
        this.handler = handler;
    }
    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    public static Context getContext(){
        return MyApplication.context;
    }



    public static ReturnInfo parseJson(String jsonResult) {
        ReturnInfo ri = null;
        Gson gson = new Gson();
        ri = gson.fromJson(jsonResult, ReturnInfo.class);
        return ri;
    }

    //获取字的信息
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = "";
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("apikey", "391f34fe57a3c7936eb5b12a226f996c");
            conn.connect();
            if(conn.getResponseCode() == 200){
                InputStream is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf.append(strRead);
                    sbf.append("\r\n");
                }
                reader.close();
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
