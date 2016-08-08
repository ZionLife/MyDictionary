package com.zionlife.mydictionary.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class Explain implements Serializable{
    private String pinyin;
    private String content;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
