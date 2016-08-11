package com.zionlife.mydictionary.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.bean.Explain;
import com.zionlife.mydictionary.bean.ReturnInfo;
import com.zionlife.mydictionary.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class ShoucangActivity extends AppCompatActivity {
    @Bind(R.id.tv_word_shoucang)
    TextView tvWordShoucang;
    @Bind(R.id.ll_pinyin_shoucang)
    LinearLayout llPinyinShoucang;
    @Bind(R.id.tv_bushou_shoucang)
    TextView tvBushouShoucang;
    @Bind(R.id.tv_bihua_shoucang)
    TextView tvBihuaShoucang;
    @Bind(R.id.ll_bb_shoucang)
    LinearLayout llBbShoucang;
    @Bind(R.id.tv_wubi_shoucang)
    TextView tvWubiShoucang;
    @Bind(R.id.tv_jiegou_shoucang)
    TextView tvJiegouShoucang;
    @Bind(R.id.ll_jw_shoucang)
    LinearLayout llJwShoucang;
    @Bind(R.id.tv_english_shoucang)
    TextView tvEnglishShoucang;
    @Bind(R.id.ll_content_shoucang)
    LinearLayout llContentShoucang;
    @Bind(R.id.ll_result_shoucang)
    LinearLayout llResultShoucang;

    ReturnInfo ri;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Bundle bundle = this.getIntent().getBundleExtra("ReturnInfo");
        ri = (ReturnInfo) bundle.getSerializable("ri");
        show();
    }

    private void show() {
        String pinyin[] = ri.getResult().getPinyin().split(",");
        String content = "";
        String english = "";
        tvWordShoucang.setText(ri.getResult().getName());
        llPinyinShoucang.removeAllViews();
        llContentShoucang.removeAllViews();
        for (int i = 0; i < pinyin.length; i++) {
            Log.e("content", "" + pinyin.length + pinyin[i]);
            TextView py = new TextView(llPinyinShoucang.getContext());
            py.setText("[" + pinyin[i] + "]");
            py.setId(i);
            py.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            py.setTextSize(15);
            llPinyinShoucang.addView(py);
        }
        tvBushouShoucang.setText("部首：" + ri.getResult().getBushou());
        tvBihuaShoucang.setText("笔画：" + ri.getResult().getBihua());
        tvJiegouShoucang.setText("结构：" + ri.getResult().getJiegou().replace("结构", ""));
        tvWubiShoucang.setText("五笔：" + ri.getResult().getWubi());

        for (int i = 0; i < ri.getResult().getExplain().size(); i++) {
            Explain ep = ri.getResult().getExplain().get(i);
            content = ep.getContent();
            TextView tvTitle = new TextView(llContentShoucang.getContext());
            tvTitle.setText(ri.getResult().getName() + "[" + ep.getPinyin() + "]:");
            tvTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tvTitle.setTextColor(Color.GRAY);
            tvTitle.setTextSize(15);
            tvTitle.setPadding(20, 20, 20, 20);

            TextView tvContent = new TextView(llContentShoucang.getContext());
            tvContent.setText(Html.fromHtml(content));
            tvContent.setTextSize(14);
            tvContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tvContent.setTextColor(Color.BLACK);

            llContentShoucang.addView(tvTitle);
            llContentShoucang.addView(tvContent);
        }
        english += "[";
        for (int i = 0; i < ri.getResult().getEnglish().size(); i++) {
            english += ri.getResult().getEnglish().get(i);
            if (i != ri.getResult().getEnglish().size() - 1) {
                english += "、";
            }
        }
        english += "]";
        tvEnglishShoucang.setText("英语：" + english);
    }
}
