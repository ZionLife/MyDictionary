package com.zionlife.mydictionary.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.bean.ReturnInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    static String httpUrl = "http://apis.baidu.com/netpopo/zidian/word";
    static String httpArg = "word=轻";
    String jsonResult = "000";
    ReturnInfo ri = null;


    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvInfo.setText(Html.fromHtml(ri.result.explain.get(0).content));
                    break;
                default:
                    break;
            }
        }
    };
    @Bind(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                jsonResult = request(httpUrl, httpArg);
                parseJson(jsonResult);
                mhandler.sendEmptyMessage(1);
            }
        }).start();

    }

    private ReturnInfo parseJson(String jsonResult) {
        ri = null;
        Gson gson = new Gson();
        ri = gson.fromJson(jsonResult, ReturnInfo.class);
        Log.e("result", ri.msg + ri.status + ri.result.bishun + ri.result.english.get(2) + ri.result.explain.get(0).content);
        return ri;
    }

    //获取字的信息
    private String request(String httpUrl, String httpArg) {
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
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
