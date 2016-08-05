package com.zionlife.mydictionary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.fragment.BookFragment;
import com.zionlife.mydictionary.fragment.ShoucangFragment;
import com.zionlife.mydictionary.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @Bind(R.id.iv_shoucang)
    ImageView ivShoucang;
    @Bind(R.id.tv_shoucang)
    TextView tvShoucang;
    @Bind(R.id.ll_book)
    LinearLayout llBook;
    @Bind(R.id.ll_jielong)
    LinearLayout llJielong;
    @Bind(R.id.ll_shoucang)
    LinearLayout llShoucang;

    private Fragment bookFragment;
    private Fragment jielongFragment;
    private Fragment shoucangFragment;
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

    @OnClick({R.id.ll_book, R.id.ll_jielong, R.id.ll_shoucang})
    public void changeTab(View view) {
        switch (view.getId()){
            case R.id.ll_book:
                setSelect(0);
                break;
            case R.id.ll_jielong:
                setSelect(1);
                break;
            case R.id.ll_shoucang:
                setSelect(2);
                break;
        }
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        hideFragment();
        resetTab();
        switch (i) {
            case 0:
                if (bookFragment == null) {
                    bookFragment = new BookFragment();
                    ft.add(R.id.content, bookFragment);
                }
                ivBook.setImageResource(R.drawable.book_pressed);
                tvBook.setTextColor(Utils.getColor(R.color.colorSelectText));
                ft.show(bookFragment);
                break;
            case 1:
                break;
            case 2:
                if (shoucangFragment == null) {
                    shoucangFragment = new ShoucangFragment();
                    ft.add(R.id.content, shoucangFragment);
                }
                ivShoucang.setImageResource(R.drawable.shoucang_pressed);
                tvShoucang.setTextColor(Utils.getColor(R.color.colorSelectText));
                ft.show(shoucangFragment);
                break;
        }
        ft.commit();
    }

    private void resetTab() {
        ivBook.setImageResource(R.drawable.book_normal);
        ivJielong.setImageResource(R.drawable.jielong_normal);
        ivShoucang.setImageResource(R.drawable.shoucang_normal);

        tvBook.setTextColor(Color.WHITE);
        tvJielong.setTextColor(Color.WHITE);
        tvShoucang.setTextColor(Color.WHITE);

    }

    private void hideFragment() {
        if (bookFragment != null) {
            ft.hide(bookFragment);
        }
        if (shoucangFragment != null) {
            ft.hide(shoucangFragment);
        }
    }


}
