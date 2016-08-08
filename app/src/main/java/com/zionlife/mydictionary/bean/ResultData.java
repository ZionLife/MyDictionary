package com.zionlife.mydictionary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class ResultData implements Serializable{
    private String name;
    private String pinyin;
    private String bihua;
    private String bushou;
    private String jiegou;
    private String bishun;
    private String wubi;
    private List<String> english;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getBihua() {
        return bihua;
    }

    public void setBihua(String bihua) {
        this.bihua = bihua;
    }

    public String getBushou() {
        return bushou;
    }

    public void setBushou(String bushou) {
        this.bushou = bushou;
    }

    public String getJiegou() {
        return jiegou;
    }

    public void setJiegou(String jiegou) {
        this.jiegou = jiegou;
    }

    public String getBishun() {
        return bishun;
    }

    public void setBishun(String bishun) {
        this.bishun = bishun;
    }

    public String getWubi() {
        return wubi;
    }

    public void setWubi(String wubi) {
        this.wubi = wubi;
    }

    public List<String> getEnglish() {
        return english;
    }

    public void setEnglish(List<String> english) {
        this.english = english;
    }

    public List<Explain> getExplain() {
        return explain;
    }

    public void setExplain(List<Explain> explain) {
        this.explain = explain;
    }

    private List<Explain> explain;
}
