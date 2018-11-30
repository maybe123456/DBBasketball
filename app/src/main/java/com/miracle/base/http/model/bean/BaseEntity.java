package com.miracle.base.http.model.bean;


import java.io.Serializable;

public class BaseEntity implements Serializable{

    private String code;

    private String msg;
//
//    private String data;

//    public String responseCode;
//    public String responseMsg;
////    public String operateCode ;
////    public String operateMsg;
//    public String responseResult;

//    [size=155 text={"responseCode":"200","responseMsg":"success","responseResult":"…]

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
//    public String getResponseResult() {
//        return responseResult;
//    }
//
//    public void setResponseResult(String responseResult) {
//        this.responseResult = responseResult;
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
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
}
