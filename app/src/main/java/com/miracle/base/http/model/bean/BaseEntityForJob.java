package com.miracle.base.http.model.bean;

import java.io.Serializable;

public class BaseEntityForJob implements Serializable {

    private String code;

    private String msg;

    private int totalPage;

//    public String responseCode;
//    public String responseMsg;
//    public String operateCode ;
//    public String operateMsg;
//    public String data;
//    public T responseResult;

//    public String getResponseCode() {
//        return responseCode;
//    }
//
//    public void setResponseCode(String responseCode) {
//        this.responseCode = responseCode;
//    }
//
//    public String getResponseMsg() {
//        return responseMsg;
//    }
//
//    public void setResponseMsg(String responseMsg) {
//        this.responseMsg = responseMsg;
//    }
//
//    public String getOperateCode() {
//        return operateCode;
//    }
//
//    public void setOperateCode(String operateCode) {
//        this.operateCode = operateCode;
//    }
//
//    public String getOperateMsg() {
//        return operateMsg;
//    }
//
//    public void setOperateMsg(String operateMsg) {
//        this.operateMsg = operateMsg;
//    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
