package com.zionlife.mydictionary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class ReturnInfo implements Serializable{
    private String status;
    private String msg;
    private ResultData result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultData getResult() {
        return result;
    }

    public void setResult(ResultData result) {
        this.result = result;
    }
}
