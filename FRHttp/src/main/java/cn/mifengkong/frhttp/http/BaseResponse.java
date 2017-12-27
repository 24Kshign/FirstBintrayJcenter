package cn.mifengkong.frhttp.http;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jack on 17/2/23
 */

public class BaseResponse<T> implements Serializable {

    private static final int SUCCESS_CODE = 1;

    private static final int FAILURE_CODE = 0;

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private T data;

    @SerializedName("message")
    private String message;

    @SerializedName("msg")
    private String msg;

    @SerializedName("url")
    private String url;

    @SerializedName("count")
    private String count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public boolean isCodeSuccess() {
        return status == SUCCESS_CODE;
    }

}