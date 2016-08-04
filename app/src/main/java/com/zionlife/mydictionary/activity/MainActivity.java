package com.zionlife.mydictionary.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.fragment.BookFragment;
import com.zionlife.mydictionary.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.iv_book)
    ImageView ivBook;
    @Bind(R.id.tv_book)
    TextView tvBook;
    @Bind(R.id.iv_jielong)
    ImageView ivJielong;
    @Bind(R.id.tv_jielong)
    TextView tvJielong;
    @Bind(R.id.iv_news)
    ImageView ivNews;
    @Bind(R.id.tv_news)
    TextView tvNews;

    private Fragment bookFragment;
    private Fragment jielongFragment;
    private Fragment newsFragment;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        setSelect(0);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (i){
            case 0:
                if(bookFragment == null){
                    bookFragment = new BookFragment();
                    ft.add(R.id.content, bookFragment);
                }
                ivBook.setImageResource(R.drawable.book_pressed);
                tvBook.setTextColor(Utils.getColor(R.color.colorSelectText));
                ft.show(bookFragment);
                break;
        }
        ft.commit();
    }


}
