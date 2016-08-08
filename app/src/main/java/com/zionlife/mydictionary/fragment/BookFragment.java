package com.zionlife.mydictionary.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zionlife.mydictionary.MyApplication;
import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.bean.Explain;
import com.zionlife.mydictionary.bean.ReturnInfo;
import com.zionlife.mydictionary.db.DbManager;
import com.zionlife.mydictionary.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/31 0031.
 */
public class BookFragment extends Fragment {
    public static ReturnInfo ri = null;
    static String httpArg = "word=";
    static String httpUrl = "http://apis.baidu.com/netpopo/zidian/word";
    static String word = "";
    public static String jsonResult = "";
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pbProgress.setVisibility(View.GONE);
                    showResult();
                    break;
                case 1:
                    pbProgress.setVisibility(View.GONE);
                    Toast.makeText(MyApplication.context, "查询出错，请检查网络连接或稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    pbProgress.setVisibility(View.GONE);
                    Toast.makeText(MyApplication.context, "请输入一个要查询的汉字", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    pbProgress.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "查询失败", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };
    @Bind(R.id.ll_pinyin)
    LinearLayout llPinyin;
    @Bind(R.id.ll_bb)
    LinearLayout llBb;
    @Bind(R.id.ll_jw)
    LinearLayout llJw;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.ll_result)
    LinearLayout llResult;
    @Bind(R.id.sv_result)
    ScrollView svResult;
    @Bind(R.id.tv_word)
    TextView tvWord;
    @Bind(R.id.tv_bushou)
    TextView tvBushou;
    @Bind(R.id.tv_bihua)
    TextView tvBihua;
    @Bind(R.id.tv_jiegou)
    TextView tvJiegou;
    @Bind(R.id.tv_wubi)
    TextView tvWubi;
    //    @Bind(R.id.tv_bishun)
//    TextView tvBishun;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.tv_bg)
    TextView tvBg;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.tv_english)
    TextView tvEnglish;
    @Bind(R.id.et_word)
    EditText etWord;
    @Bind(R.id.btn_add)
    Button btnAdd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        etWord.setText("好");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbProgress.setVisibility(View.VISIBLE);
                word = etWord.getText().toString();
                word = word.replaceAll(" ", "");
                etWord.setText("");
                if (TextUtils.isEmpty(word) != true && word.length() == 1) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            jsonResult = Utils.request(httpUrl, httpArg + word);
                            if (!TextUtils.isEmpty(jsonResult)) {
                                Gson gson = new Gson();
                                Map<String ,String> map = gson.fromJson(jsonResult, Map.class);
                                if(map.get("status").equals("203")){
                                    mhandler.sendEmptyMessage(3);
                                }else {
                                    ri = Utils.parseJson(jsonResult);
                                    mhandler.sendEmptyMessage(0);
                                }
                            } else {
                                mhandler.sendEmptyMessage(1);
                            }
                        }
                    }).start();
                } else {
                    mhandler.sendEmptyMessage(2);
                }
            }
        });
    }


    private void showResult() {
        ivBg.setVisibility(View.GONE);
        tvBg.setVisibility(View.GONE);
        svResult.setVisibility(View.VISIBLE);
        String pinyin[] = ri.getResult().getPinyin().split(",");
        String content = "";
        String english = "";
        tvWord.setText(word);
        llPinyin.removeAllViews();
        llContent.removeAllViews();
        for (int i = 0; i < pinyin.length; i++) {
            Log.e("content", "" + pinyin.length + pinyin[i]);
            TextView py = new TextView(llPinyin.getContext());
            py.setText("[" + pinyin[i] + "]");
            py.setId(i);
            py.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            py.setTextSize(15);
            llPinyin.addView(py);
        }
        tvBushou.setText("部首：" + ri.getResult().getBushou());
        tvBihua.setText("笔画：" + ri.getResult().getBihua());
        tvJiegou.setText("结构：" + ri.getResult().getJiegou().replace("结构", ""));
        tvWubi.setText("五笔：" + ri.getResult().getWubi());

        for (int i = 0; i < ri.getResult().getExplain().size(); i++) {
            Explain ep = ri.getResult().getExplain().get(i);
            content = ep.getContent();
            TextView tvTitle = new TextView(llContent.getContext());
            tvTitle.setText(word + "[" + ep.getPinyin() + "]:");
            tvTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tvTitle.setTextColor(Color.GRAY);
            tvTitle.setTextSize(15);
            tvTitle.setPadding(20, 20, 20, 20);

            TextView tvContent = new TextView(llContent.getContext());
            tvContent.setText(Html.fromHtml(content));
            tvContent.setTextSize(14);
            tvContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tvContent.setTextColor(Color.BLACK);

            llContent.addView(tvTitle);
            llContent.addView(tvContent);
        }

        english += "[";
        for (int i = 0; i < ri.getResult().getEnglish().size(); i++) {
            english += ri.getResult().getEnglish().get(i);
            if (i != ri.getResult().getEnglish().size() - 1) {
                english += "、";
            }
        }
        english += "]";
        tvEnglish.setText("英语：" + english);
    }

    @OnClick({R.id.btn_add})
    public void addWord(View view) {
        DbManager dm = new DbManager(this.getContext());
        if(dm.add(ri)){
            Toast.makeText(this.getContext(), "已加入收藏", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
