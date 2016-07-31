package com.zionlife.mydictionary.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class ReturnInfo {
    public String status;
    public String msg;
    public ResultData result;

    public static class ResultData {
        public String name;
        public String pinyin;
        public String bihua;
        public String bushou;
        public String jiegou;
        public String bishun;
        public String wubi;
        public List<String> english;
        public List<Explain> explain;

        public static class Explain {
            public String pinyin;
            public String content;
        }
    }
}
