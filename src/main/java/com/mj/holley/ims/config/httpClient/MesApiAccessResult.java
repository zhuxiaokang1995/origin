package com.mj.holley.ims.config.httpClient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Wanghui on 2018/1/19.
 */
public class MesApiAccessResult<T> {

    @JsonProperty("Success")
    private boolean Success;

    @JsonProperty("Message")
    private String Message;

    @JsonProperty("Result")
    private String Result;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "MesApiAccessResult{" +
            "Success=" + Success +
            ", Message='" + Message + '\'' +
            ", Result='" + Result + '\'' +
            '}';
    }
}
