package com.mj.holley.ims.service.dto;

import java.security.PrivateKey;

/**
 * Created by Wanghui on 2018/2/5.
 */
public class MesReturnDto {

    private Boolean Success;

    private String Message;

    private String Result;

    @Override
    public String toString() {
        return "{" +
            "Success:" + Success +
            ", Message:'" + Message + '\'' +
            ", Result:'" + Result + '\'' +
            '}';
    }

    public MesReturnDto(Boolean success, String message, String result) {
        Success = success;
        Message = message;
        Result = result;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
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
}
