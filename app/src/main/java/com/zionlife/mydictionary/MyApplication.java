package com.zionlife.mydictionary;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2016/7/31 0031.
 */
public class MyApplication extends Application{
    public static Context context = null;
    public static Handler handler = null;
    public static Thread mainThread = null;
    public static int mainThreadId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();

    }
}
