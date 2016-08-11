package com.zionlife.mydictionary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.fragment.BookFragment;
import com.zionlife.mydictionary.fragment.MeFragment;
import com.zionlife.mydictionary.fragment.ShoucangFragment;
import com.zionlife.mydictionary.utils.Utils;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

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
    @Bind(R.id.iv_me)
    ImageView ivMe;
    @Bind(R.id.tv_me)
    TextView tvMe;
    @Bind(R.id.iv_shoucang)
    ImageView ivShoucang;
    @Bind(R.id.tv_shoucang)
    TextView tvShoucang;
    @Bind(R.id.ll_book)
    LinearLayout llBook;
    @Bind(R.id.ll_me)
    LinearLayout llMe;
    @Bind(R.id.ll_shoucang)
    LinearLayout llShoucang;

    private Fragment bookFragment;
    private Fragment meFragment;
    private Fragment shoucangFragment;
    private FragmentTransaction ft;
    private long eTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdManager.getInstance(this).init("3e094295c6bd9fa6", "135e4a53880f32ae", true);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        setSelect(0);
        SpotManager.getInstance(this).loadSpotAds();
        SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
        SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_SIMPLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - eTime >2000){
                SpotManager.getInstance(this).showSpotAds(this);
                eTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else{
                finish();
            }

        }
        return true;
    }

    @OnClick({R.id.ll_book, R.id.ll_me, R.id.ll_shoucang})
    public void changeTab(View view) {
        switch (view.getId()) {
            case R.id.ll_book:
                setSelect(0);
                break;
            case R.id.ll_me:
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
                if(meFragment == null){
                    meFragment = new MeFragment();
                    ft.add(R.id.content, meFragment);
                }
                ivMe.setImageResource(R.drawable.me_pressed);
                tvMe.setTextColor(Utils.getColor(R.color.colorSelectText));
                ft.show(meFragment);
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
        ivMe.setImageResource(R.drawable.me_normal);
        ivShoucang.setImageResource(R.drawable.shoucang_normal);

        tvBook.setTextColor(Color.WHITE);
        tvMe.setTextColor(Color.WHITE);
        tvShoucang.setTextColor(Color.WHITE);

    }

    private void hideFragment() {
        if (bookFragment != null) {
            ft.hide(bookFragment);
        }
        if (shoucangFragment != null) {
            ft.hide(shoucangFragment);
        }
        if(meFragment != null){
            ft.hide(meFragment);
        }
    }


}
