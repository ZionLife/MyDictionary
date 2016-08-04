package com.zionlife.mydictionary.ui;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zionlife.mydictionary.MyApplication;
import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.utils.Utils;

import butterknife.OnTouch;

/**
 * Created by Administrator on 2016/7/31 0031.
 */
public class SearchEditText extends AppCompatEditText {
    public Drawable mSearchBtn = null;
    public Drawable mEditBtn = null;

    public SearchEditText(Context context) {
        super(context);
        init(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Drawable drawableSear = ContextCompat.getDrawable(context, R.drawable.sousuo);
        Drawable wrappedDrawableSear = DrawableCompat.wrap(drawableSear);
//        DrawableCompat.setTint(wrappedDrawableSear, getCurrentHintTextColor());
        mSearchBtn = wrappedDrawableSear;
        mSearchBtn.setBounds(0, 0, 90, 90);
        mSearchBtn.setVisible(true, false);

        Drawable drawableEdit = ContextCompat.getDrawable(context, R.drawable.edit);
        Drawable wrappedDrawableEdit = DrawableCompat.wrap(drawableEdit);
        //      DrawableCompat.setTint(wrappedDrawableEdit, getCurrentHintTextColor());
        mEditBtn = wrappedDrawableEdit;
        mEditBtn.setBounds(0, 0, 90, 90);
        mEditBtn.setVisible(true, false);

        setCompoundDrawables(mEditBtn, null, mSearchBtn, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mSearchBtn != null && event.getAction() == 1 ){
            Rect rBounds = mSearchBtn.getBounds();
            int i = (int) event.getRawX();
            if(i>getRight() - 2*rBounds.width()){
                String content = this.getText().toString();
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(MyApplication.context, "请输入要查找的内容", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MyApplication.context, "正在查找", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onTouchEvent(event);
    }

}
